package cn.com.upcard.mgateway.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.com.upcard.mgateway.async.notify.model.CallbackPara;
import cn.com.upcard.mgateway.async.notify.model.CallbackRequestInfo;
import cn.com.upcard.mgateway.async.notify.model.NotifyPara;
import cn.com.upcard.mgateway.async.notify.model.OrderProcessingResult;
import cn.com.upcard.mgateway.async.notify.service.NotifyService;
import cn.com.upcard.mgateway.common.enums.OrderStatus;
import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.dao.PushInfoDAO;
import cn.com.upcard.mgateway.entity.PushInfo;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.service.CallbackService;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.util.DateUtil;

@Service
public class CallbackServiceImpl implements CallbackService {

	private static Logger logger = LoggerFactory.getLogger(CallbackServiceImpl.class);

	private static final String CHANNEL_PAY_SUCCESS = "0";
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private NotifyService notifyService;
	@Autowired
	private PushInfoDAO pushInfoDAO;
	@Autowired
	private OrderOperService orderOperService;

	@Override
	public OrderProcessingResult updateOrderProcessing(CallbackRequestInfo callbackRequestInfo) {

		logger.info("callbackRequestInfo={}", callbackRequestInfo.toString());
		OrderProcessingResult result = new OrderProcessingResult();

		String outTradeNo = callbackRequestInfo.getOutTradeNo();

		// 查询原交易
		TradeOrder tradeOrder = orderOperService.findOrderByTradeNo(outTradeNo, true);

		// 未查到订单信息，返回错误，要求再次发送
		if (null == tradeOrder) {
			logger.error("交易号不存在，outTradeNo={}", outTradeNo);
			result.setResult(FAIL);
			result.setNeedPush(false);
			return result;
		}
		result.setTradeOrder(tradeOrder);

		// 原订单非 WAITTING 直接返回成功
		if (!OrderStatus.WAITING.name().equals(tradeOrder.getStatus())) {
			logger.info("订单非等待状态，outTradeNo={}", outTradeNo);
			result.setResult(SUCCESS);
			result.setNeedPush(false);
			return result;
		}

		String payResult = callbackRequestInfo.getPayResult();
		String errMsg = callbackRequestInfo.getErrMsg();
		String tradeType = callbackRequestInfo.getTradeType();
		String timeEnd = callbackRequestInfo.getTimeEnd();
		String transactionId = callbackRequestInfo.getTransactionId();
		String outTransactionId = callbackRequestInfo.getOutTransactionId();
		Integer totalFee = callbackRequestInfo.getTotalFee();
		Integer discountFee = callbackRequestInfo.getDiscountFee();
		String subAppid = callbackRequestInfo.getSubAppid();
		String subOpenid = callbackRequestInfo.getSubOpenid();
		String aliOpenid = callbackRequestInfo.getAliOpenid();

		// 交易失败，更新订单表和操作表，不需要推送给商户
		if (!CHANNEL_PAY_SUCCESS.equals(payResult)) {
			logger.info("渠道支付失败，outTradeNo={}", outTradeNo);
			ChannelResponseInfo responseInfo = new ChannelResponseInfo();
			responseInfo.setCode("00");
			responseInfo.setMsg(errMsg);
			responseInfo.setTradeStatus(OrderStatus.FAILED.name());
			orderOperService.updateUnifiedPayQuery(tradeOrder, responseInfo);

			result.setResult(SUCCESS);
			result.setNeedPush(false);
			return result;
		}

		// 交易成功更新订单表和订单操作表
		ChannelResponseInfo responseInfo = new ChannelResponseInfo();
		responseInfo.setCode("00");
		responseInfo.setTradeStatus(OrderStatus.SUCCESS.name());
		responseInfo.setTotalFee(totalFee);
		responseInfo.setDiscountFee(discountFee);
		responseInfo.setChannelTxnNo(transactionId);
		responseInfo.setThirdPaymentTxnid(outTransactionId);
		responseInfo.setThirdSellerAccount(subAppid);

		if (TradeType.ALI_NATIVE.name().equals(tradeType) || TradeType.ALI_JSAPI.name().equals(tradeType) || TradeType.ALI_DIRECT.name().equals(tradeType)){

			responseInfo.setThirdBuyerAccount(aliOpenid);
		} else {
			responseInfo.setThirdBuyerAccount(subOpenid);
		}
		responseInfo.setTradeEndDate(timeEnd.substring(0, 8));
		responseInfo.setTradeEndTime(timeEnd.substring(8));
		orderOperService.updateUnifiedPayQuery(tradeOrder, responseInfo);

		result.setResult(SUCCESS);
		result.setChannelResponseInfo(responseInfo);

		if (StringUtils.isNotEmpty(tradeOrder.getNotifyUrl())) {
			result.setNeedPush(true);
		} else {
			// 目前静态二维码支付该参数为空，无需推送
			logger.info("该订单无需推送，outTradeNo={}", outTradeNo);
			result.setNeedPush(false);
		}

		logger.info("updateOrderProcessing end");
		return result;
	}

	@Override
	public void callbackHandle(CallbackPara callbackPara) {
		logger.info("callbackPara = {}", callbackPara);

		// 构造消息发送参数
		NotifyPara notifyPara = new NotifyPara();
		notifyPara.setUrl(callbackPara.getUrl());
		notifyPara.setContentMap(callbackPara.getContentMap());
		notifyPara.setOrderId(callbackPara.getOrderId());
		notifyPara.setPushedNumber(0); // 已推送的次数
		notifyPara.setPushTime(DateUtil.format2(new Date(), DateUtil.yyyyMMddHHmmss));
		notifyService.executeNotifyThread(notifyPara);
	}

	@Override
	public void batchNotify(String endTime) {
		logger.info("batchNotify begin");

		List<PushInfo> pushInfoList = pushInfoDAO.findByPushTimeBeforeAndStatusEquals(endTime, "W");
		logger.info("pushInfoList size= {}", pushInfoList.size());

		for (PushInfo bean : pushInfoList) {
			logger.info("order_id = {} , pushed_number={}", bean.getOrderId(), bean.getPushNumber());

			String url = bean.getNotifyUrl(); // 推送的url
			String content = bean.getContent(); // 推送的内容，json
			String pushedNumber = bean.getPushNumber(); // 已经推送的次数
			String pushTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 本次推送的时间

			@SuppressWarnings("unchecked")
			Map<String, String> contentMap = JSONObject.parseObject(content, Map.class);

			NotifyPara notifyPara = new NotifyPara();
			notifyPara.setUrl(url);
			notifyPara.setOrderId(bean.getOrderId());
			notifyPara.setContentMap(contentMap);
			notifyPara.setPushTime(pushTime);
			notifyPara.setPushedNumber(Integer.valueOf(pushedNumber));

			notifyService.executeNotifyThreadForTask(notifyPara);
		}

		logger.info("batchNotify end");
	}

}
