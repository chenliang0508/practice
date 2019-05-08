package cn.com.upcard.mgateway.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.upcard.mgateway.async.notify.thread.pool.NotifyTaskExcecutor;
import cn.com.upcard.mgateway.async.task.thread.pool.RefundQueryExcecutor;
import cn.com.upcard.mgateway.async.task.thread.pool.TradeQueryExcecutor;
import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.OrderStatus;
import cn.com.upcard.mgateway.common.enums.OrderType;
import cn.com.upcard.mgateway.common.enums.ResponseType;
import cn.com.upcard.mgateway.controller.response.RefundQueryResponse;
import cn.com.upcard.mgateway.dao.TpPaymentTypeDAO;
import cn.com.upcard.mgateway.dao.TradeOrderDAO;
import cn.com.upcard.mgateway.entity.TpCommPayTypes;
import cn.com.upcard.mgateway.entity.TpPaymentType;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.service.CallbackService;
import cn.com.upcard.mgateway.service.CommericalInfoService;
import cn.com.upcard.mgateway.service.GatewayService;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.util.DateUtil;

@Controller
@RequestMapping("timer")
public class TimerController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(TimerController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private CallbackService callbackService;
	@Autowired
	private OrderOperService orderOperService;
	@Autowired
	private TradeOrderDAO tradeOrderDAO;
	@Autowired
	private TpPaymentTypeDAO tpPaymentTypeDAO;
	@Autowired
	private GatewayService gatewayService;
	@Autowired
	private CommericalInfoService commericalInfoService;
	

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "awakennotifytask")
	public String awakenNotifyTask(HttpServletRequest request) {

		// String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		logger.info("endTime={}", endTime);

		if (!DateUtil.checkDate(endTime, DateUtil.yyyyMMddHHmmss)) {
			logger.error("时间格式错误");
			return response(ResponseType.JSON.name(), FAIL);
		}

		// 调用批量推送方法
		callbackService.batchNotify(endTime);

		return response(ResponseType.JSON.name(), SUCCESS);
	}

	/**
	 * 退款订单查询的定时器调用的接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "refundQuery")
	public String refundQueryTask(HttpServletRequest request) {

		logger.info("定时退货查询task开始");

		// 查询一周
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		logger.info("startTime={} , endTime={}", startTime, endTime);
		Date start = null;
		Date end = null;
		try {
			start = DateUtil.parseCompactDateTime(startTime);
			end = DateUtil.parseCompactDateTime(endTime);
		} catch (Exception e) {
			logger.error("时间格式错误");
			e.printStackTrace();
			return response(ResponseType.JSON.name(), FAIL);
		}

		// 查询等待中的退款订单
		List<TradeOrder> orders = orderOperService.findByOrderTypeAndStatusAndCreateTimeBetween(OrderType.REFUND.name(),
				OrderStatus.WAITING.name(), start, end);
		if (CollectionUtils.isEmpty(orders)) {
			logger.info("没有等待中的退款订单");
			return response(ResponseType.JSON.name(), SUCCESS);
		}
		logger.info("退货查询数量为={}", orders.size());

		
		Runnable refundQueryTask = new Runnable() {
			@Override
			public void run() {
				for (TradeOrder tradeOrder : orders) {

					String outTradeNo = tradeOrder.getOrderNo(); // 商户退款单号
					String tradeNo = tradeOrder.getTradeNo(); // 银商订单号
					String commUnionNo = tradeOrder.getCulCommNo(); // 商户在银商的商户号

					TpPaymentType paymentType = tpPaymentTypeDAO.findByChannelIdAndPayTypeCodeAndStatus(
							tradeOrder.getChannelId(), tradeOrder.getTradeType(), Constants.YES);
					if (null == paymentType) {
						logger.error("获取支付类型失败，tradeNo={}", tradeNo);
						continue;
					}

					// 获取通道信息
					TpCommPayTypes tpCommPayTypes = commericalInfoService.getCommPayTypesByTypeIdAndChannelIdAndCommNo(
							tradeOrder.getChannelId(), paymentType.getPayTypeId(), tradeOrder.getCulCommNo());
					if (null == tpCommPayTypes) {
						logger.error("获取通道信息失败，tradeNo={}", tradeNo);
						continue;
					}

					// 网商商户号、银商退款单号、bankmerno、CommPrivateKey、BankCode
					PreTradeOrderInfo preTradeOrderInfo = new PreTradeOrderInfo();
					preTradeOrderInfo.setOutTradeNo(outTradeNo);
					preTradeOrderInfo.setTradeNo(tradeNo);
					preTradeOrderInfo.setCommUnionNo(commUnionNo);
					preTradeOrderInfo.setBankMerNo(tpCommPayTypes.getTpCommericalInfo().getBankMerNo());
					preTradeOrderInfo.setCommPrivateKey(tpCommPayTypes.getTpCommericalInfo().getCommKey());
					preTradeOrderInfo.setBankCode(tpCommPayTypes.getTpAcquirer().getBankIdtCode());
					RefundQueryResponse refundQueryResponse = gatewayService.tradeRefundQuery(preTradeOrderInfo);
					logger.info("订单号：{}，code={},msg={}", tradeNo, refundQueryResponse.getCode(),
							refundQueryResponse.getMsg());
				}
				logger.info("定时退货查询task结束");
			}
		};
		
		RefundQueryExcecutor.getRefundQueryThreadPool().execute(refundQueryTask);
		return response(ResponseType.JSON.name(), SUCCESS);
	}

	/**
	 * 交易查询的定时器调用的接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "tradeQuery")
	public String tradeQueryTask(HttpServletRequest request) {
		// 查询当前时间的前半个小时交易
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		logger.info("startTime={} , endTime={}", startTime, endTime);
		
		//接口调用方不关心接口的处理结果，通过异步处理任务，直接返回结果，快速释放连接
		Runnable tradeQueryTask = new Runnable() {
			@Override
			public void run() {
				logger.info("开始查询在{}-{}范围内等待支付的订单", startTime, endTime);
				Date start = DateUtil.parseCompactDateTime(startTime);
				Date end = DateUtil.parseCompactDateTime(endTime);
				
				logger.info("定时查询交易状态为WAITING的订单");
				// 查询等待中的订单
				List<TradeOrder> list = tradeOrderDAO
						.findByOrderTypeAndStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(OrderType.PAY.name(),
								OrderStatus.WAITING.name(), start, end);
				if (CollectionUtils.isEmpty(list)) {
					logger.info("无等待中的订单");
					return;
				}
				logger.info("需要查询的订单数量:{}", list.size());
				for (TradeOrder tradeOrder : list) {
					try {
						logger.info("outTradeNo:{}, tradeNo:{}", tradeOrder.getOrderNo(), tradeOrder.getTradeNo());
						logger.info("channelId={},tradeType={}", tradeOrder.getChannelId(),tradeOrder.getTradeType());
						
						TpPaymentType tpPaymentType = tpPaymentTypeDAO.findByChannelIdAndPayTypeCodeAndStatus(
								tradeOrder.getChannelId(), tradeOrder.getTradeType(), Constants.YES);
						if (null == tpPaymentType) {
							logger.info("无效的支付渠道{},支付类型:{}", tradeOrder.getChannelId(), tradeOrder.getTradeType());
							continue;
						}
						logger.info("payTypeId:{}, payTypeCode:{}", tpPaymentType.getPayTypeId(), tpPaymentType.getPayTypeCode());
						// 通道
						TpCommPayTypes type = commericalInfoService.getCommPayTypesByTypeIdAndChannelIdAndCommNo(
								tradeOrder.getChannelId(), tpPaymentType.getPayTypeId(), tradeOrder.getCulCommNo());
						if (type == null) {
							logger.info("type is null");
							continue;
						}
						
						PreTradeOrderInfo preInfo = new PreTradeOrderInfo();
						// 调起第三方交易查询接口
						preInfo.setBankMerNo(type.getTpCommericalInfo().getBankMerNo());
						preInfo.setBankCode(type.getTpAcquirer().getBankIdtCode());
						preInfo.setChannelId(type.getChannelId());
						preInfo.setCommPrivateKey(type.getTpCommericalInfo().getCommKey());
						preInfo.setCommUnionNo(tradeOrder.getCulCommNo());
						preInfo.setTradeNo(tradeOrder.getTradeNo());
						preInfo.setOutTradeNo(tradeOrder.getOrderNo());
						gatewayService.unifiedPayQuery(preInfo);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				logger.info("查询等待支付订单结束");
			}
		};
		
		TradeQueryExcecutor.getTradeQueryThreadPool().execute(tradeQueryTask);
		return response(ResponseType.JSON.name(), SUCCESS);
	}
}
