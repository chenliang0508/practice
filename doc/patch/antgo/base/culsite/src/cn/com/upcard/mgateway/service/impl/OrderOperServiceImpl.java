package cn.com.upcard.mgateway.service.impl;

import java.util.Date;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.BankCode;
import cn.com.upcard.mgateway.common.enums.BankOrderNoLength;
import cn.com.upcard.mgateway.common.enums.OrderOperStatus;
import cn.com.upcard.mgateway.common.enums.OrderStatus;
import cn.com.upcard.mgateway.common.enums.OrderType;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.controller.response.AlipayJsapiResponse;
import cn.com.upcard.mgateway.controller.response.AlipayNativeResponse;
import cn.com.upcard.mgateway.controller.response.MicroPayResponse;
import cn.com.upcard.mgateway.controller.response.RefundQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeCancelResponse;
import cn.com.upcard.mgateway.controller.response.TradeQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeRefundResponse;
import cn.com.upcard.mgateway.controller.response.TradecloseResponse;
import cn.com.upcard.mgateway.controller.response.WxJsResponse;
import cn.com.upcard.mgateway.controller.response.WxNativeResponse;
import cn.com.upcard.mgateway.dao.OrderOperDAO;
import cn.com.upcard.mgateway.dao.TpCommericalInfoDAO;
import cn.com.upcard.mgateway.dao.TradeOrderDAO;
import cn.com.upcard.mgateway.entity.OrderOper;
import cn.com.upcard.mgateway.entity.TpCommericalInfo;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.exception.OrderNoRepeatException;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.RandomStringCreator;

/**
 * 提供订单、订单操作DAO基本操作
 * 
 * @author chenliang
 * 
 */
@Service
public class OrderOperServiceImpl implements OrderOperService {
	private static Logger logger = LoggerFactory.getLogger(OrderOperServiceImpl.class);
	@Autowired
	private TradeOrderDAO tradeOrderDAO;
	@Autowired
	private OrderOperDAO orderOperDAO;
	@Autowired
	private TpCommericalInfoDAO tpCommericalInfoDAO;
	//	@Autowired
	//	private OrderDiscountInfoDAO orderDiscountInfoDAO;
	//	@Autowired
	//	private PushInfoDAO pushInfoDAO;
	
	@Override
	public TradeOrder findByOrderNoAndCulCommNoAndType(String orderNo, String culCommNo, String orderType) {
		logger.info("查询的orderNo:" + orderNo + ",culCommNo:" + culCommNo + ",orderType:" + orderType);
		if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(culCommNo)) {
			return null;
		}
		if(StringUtils.isEmpty(orderType)) {
			return tradeOrderDAO.findByOrderNoAndCulCommNo(orderNo, culCommNo);
		}
		if(OrderType.PAY.name().equals(orderType)) {
			return tradeOrderDAO.findByOrderNoAndCulCommNoAndOrderType(orderNo, culCommNo, OrderType.PAY.name());
		}
		if(OrderType.REFUND.name().equals(orderType)) {
			return tradeOrderDAO.findByOrderNoAndCulCommNoAndOrderType(orderNo, culCommNo, OrderType.REFUND.name());
		}
		logger.info("不支持的交易方式.");
		return null;
	}

	@Override
	public TradeOrder findOrderByTradeNo(String tradeNo, boolean lock) {
		logger.info("tradeNo:" + tradeNo + ",needLock:" + lock);
		if(lock) {
			return tradeOrderDAO.findByTradeNoForUpdate(tradeNo);
		}
		return tradeOrderDAO.findByTradeNo(tradeNo);
	}

	@Override
	public TradeOrder findOrderById(String orderId, boolean lock) {
		logger.info("orderId:" + orderId + ",needLock:" + lock);
		if(lock) {
			return tradeOrderDAO.findById(orderId);
		}
		return tradeOrderDAO.findOne(orderId);
	}

	@Override
	public TradeOrder createOrder(PreTradeOrderInfo preTradeOrderInfo) {
		logger.info("保存订单:" + preTradeOrderInfo.getOutTradeNo());
		TradeOrder preOrder = null;
		String tradeNo = null;
		do {
			tradeNo = createTradeNo(BankCode.toBankCode(preTradeOrderInfo.getBankCode()));
			preOrder = findOrderByTradeNo(tradeNo, false);
		} while (preOrder != null);

		TradeOrder tradeOrder = new TradeOrder();
		// 加上数据库的约束
		tradeOrder.setTradeNo(tradeNo);
		tradeOrder.setOrderNo(preTradeOrderInfo.getOutTradeNo()).setCulCommNo(preTradeOrderInfo.getCommUnionNo())
		.setChannelId(preTradeOrderInfo.getChannelId())
		.setOrderPoint(preTradeOrderInfo.getTotalAmount()).setOrderType(preTradeOrderInfo.getOrderType())
		.setTradeType(preTradeOrderInfo.getTradeType()).setStatus(OrderStatus.INIT.name())
		.setMerchantNo(preTradeOrderInfo.getMerchantNo()).setDeviceInfo(preTradeOrderInfo.getDeviceInfo())
		.setExtendInfo(preTradeOrderInfo.getExtendInfo()).setGoodTags(preTradeOrderInfo.getGoodTags())
		.setPayTypeLimit(preTradeOrderInfo.getPayTypeLimit());
		// 退款
		if (OrderType.REFUND.name().equals(tradeOrder.getOrderType())) {
			tradeOrder.setOrigOrderNo(preTradeOrderInfo.getOrigOrderNo());
			// tradeOrder.setRefundAuditStatus();
		}
		tradeOrder.setAuthCode(preTradeOrderInfo.getAuthCode())
		.setValidStartTime(preTradeOrderInfo.getTimeStart())
		.setExpireTime(preTradeOrderInfo.getTimeExpire())
		.setSubject(preTradeOrderInfo.getSubject())
		.setCommodityDesc(preTradeOrderInfo.getBody()).setNotifyUrl(preTradeOrderInfo.getNotifyUrl())
		.setCreateTime(DateUtil.now()).setCreateUser(preTradeOrderInfo.getOperUser())
		.setThirdBuyerAccount(preTradeOrderInfo.getThirdBuyerId() == null ? preTradeOrderInfo.getThirdBuyerAccount() : preTradeOrderInfo.getThirdBuyerId() );
		tradeOrderDAO.save(tradeOrder);
		return tradeOrder;
	}
	
	public String createOutTradeNo(String bankCode) {
		return createTradeNo(BankCode.toBankCode(bankCode));
	}
	
	@Override
	public OrderOper saveOrderOper(TradeOrder tradeOrder, String operType, String sellAccount, String buyerAccount, String reqIp) {
		OrderOper orderOper = new OrderOper();
		orderOper.setOrderId(tradeOrder.getId());
		orderOper.setOrderNo(tradeOrder.getOrderNo());
		orderOper.setCommUnionNo(tradeOrder.getCulCommNo());
		orderOper.setOrderPoint(tradeOrder.getOrderPoint());
		orderOper.setOperType(operType);
		orderOper.setStatus(OrderOperStatus.INIT.name());
		orderOper.setAuthCode(tradeOrder.getAuthCode());
		orderOper.setMerchantNo(tradeOrder.getMerchantNo());
		orderOper.setDeviceInfo(tradeOrder.getDeviceInfo());
		orderOper.setApplyType("API");
		orderOper.setCreateTime(DateUtil.now());
		orderOper.setCreateUser(tradeOrder.getCreateUser());
		orderOper.setReqIp(reqIp);
		return orderOperDAO.save(orderOper);
	}

	@Override
	public void updateTradeOrder(TradeOrder tradeOrder) {
		tradeOrderDAO.save(tradeOrder);
	}

	@Override
	public void updateOrderOper(OrderOper orderOper) {
		orderOperDAO.save(orderOper);
	}

	@Override
	public OrderOper findByOrderIdAndOperType(String tradeOrderId, String orderType) {
		return orderOperDAO.findByOrderIdAndOperType(tradeOrderId, orderType);
	}

	@Override
	public TradeOrder findByTradeNoAndOrderNoAndCulCommNoAndType(String tradeNo, String orderNo, String culCommNo, String orderType) {
		logger.info("tradeNo:" + tradeNo + ",orderNo:" + tradeNo + ",culCommNo:" + culCommNo);
		return tradeOrderDAO.findByTradeNoAndOrderNoAndCulCommNo(tradeNo, orderNo, culCommNo);
	}

	@Override
	public List<TradeOrder> findByOrigOrderNoAndCulCommNoAndStatus(String origOrderNo, String culCommNo, List<String> status) {
		return tradeOrderDAO.findByOrigOrderNoAndCulCommNoAndStatusInAndOrderType(origOrderNo, culCommNo, status, OrderType.REFUND.name());
	}

	@Override
	public AlipayNativeResponse updateAlipayNativePreOrder(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo) {
		AlipayNativeResponse resp = new AlipayNativeResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		logger.info("error code:" + channelReponseInfo.getCode() + ",error msg:" + channelReponseInfo.getMsg());
		// 调用失败
		Date updateTime = DateUtil.now();
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			order.setStatus(OrderStatus.FAILED.name()).setUpdateTime(updateTime);
			order.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(order);
			orderOper.setStatus(OrderOperStatus.RESP.name()).setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(channelReponseInfo.getCode());
			resp.setMsg(channelReponseInfo.getMsg());
			return resp;
		}
		// 检查订单状态，如果订单状态不是WAITING等待中，则不更新订单，返回信息为数据库中记录的订单状态。
		TradeOrder tradeOrder = findOrderByTradeNo(order.getTradeNo(), true);
		if (OrderStatus.CLOSED.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order .getId() + "已经被关闭.");
			resp.setCode(ResponseResult.ORDER_CLOSED.getCode());
			resp.setMsg(ResponseResult.ORDER_CLOSED.getMsg());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order .getId() + "已经被撤销.");
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order .getId() + "已经被关闭.");
			resp.setCode(ResponseResult.ORDER_STATUS_ERROR.getCode());
			resp.setMsg(ResponseResult.ORDER_STATUS_ERROR.getMsg());
			return resp;
		}
		// 更新操作和订单
		String tradeStatus = channelReponseInfo.getTradeStatus();
		logger.info("trade status:" + tradeStatus);
		if (OrderStatus.SUCCESS.name().equals(tradeStatus)) {
			logger.info("预下单成功");
			tradeOrder.setStatus(OrderStatus.WAITING.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setAuthCodeImgUrl(channelReponseInfo.getAuthCodeImgUrl());
			resp.setAuthCodeUrl(channelReponseInfo.getAuthCodeUrl());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeStatus)) {
			logger.info("订单号:" + order .getId() + "预下单失败");
			tradeOrder.setStatus(OrderStatus.FAILED.name());
			tradeOrder.setUpdateTime(updateTime);
			order.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			return resp;
		}
		if (OrderStatus.WAITING.name().equals(tradeStatus)) {
			logger.info("等待中");
			tradeOrder.setStatus(OrderStatus.WAITING.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.WAIT_PAYING.getCode());
			resp.setMsg(ResponseResult.WAIT_PAYING.getMsg());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeStatus)) {
			logger.info("订单号:" + order .getId() + "已撤销");
			tradeOrder.setStatus(OrderStatus.CANCEL.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		if (OrderStatus.CLOSED.name().equals(tradeStatus)) {
			logger.info("订单号:" + order .getId() + "已关闭");
			tradeOrder.setStatus(OrderStatus.CLOSED.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		return resp;
	}

	@Override
	public AlipayJsapiResponse updateAlipayJsapiPreOrder(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo) {
		logger.info("error code:" + channelReponseInfo.getCode() + ",error msg:" + channelReponseInfo.getMsg());
		// 调用失败
		Date updateTime = DateUtil.now();
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			order.setStatus(OrderStatus.FAILED.name()).setUpdateTime(updateTime);
			order.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(order);
			orderOper.setStatus(OrderOperStatus.RESP.name()).setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			return new AlipayJsapiResponse(channelReponseInfo.getCode(), channelReponseInfo.getMsg());
		}
		// 检查订单状态，如果订单状态不是WAITING等待中，则不更新订单，返回信息为数据库中记录的订单状态。
		TradeOrder tradeOrder = findOrderByTradeNo(order.getTradeNo(), true);
		if (OrderStatus.CLOSED.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order .getId() + "已经被关闭.");
			return new AlipayJsapiResponse(ResponseResult.ORDER_CLOSED);
		}
		if (OrderStatus.CANCEL.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order .getId() + "已经被撤销.");
			return new AlipayJsapiResponse(ResponseResult.ORDER_REVERSED);
		}
		if (OrderStatus.FAILED.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order .getId() + "已经被关闭.");
			return new AlipayJsapiResponse(ResponseResult.ORDER_STATUS_ERROR);
		}

		AlipayJsapiResponse resp = new AlipayJsapiResponse(ResponseResult.OK);
		String tradeStatus = channelReponseInfo.getTradeStatus();
		logger.info("trade status:" + tradeStatus);
		if (OrderStatus.SUCCESS.name().equals(tradeStatus)) {
			logger.info("预下单成功");
			tradeOrder.setStatus(OrderStatus.WAITING.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setPayInfo(channelReponseInfo.getPayInfo());
			resp.setPayUrl(channelReponseInfo.getPayUrl());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeStatus)) {
			logger.info("订单号:" + order .getId() + "预下单失败");
			tradeOrder.setStatus(OrderStatus.FAILED.name());
			tradeOrder.setUpdateTime(updateTime);
			order.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			return resp;
		}
		if (OrderStatus.WAITING.name().equals(tradeStatus)) {
			logger.info("等待中");
			tradeOrder.setStatus(OrderStatus.WAITING.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.WAIT_PAYING.getCode());
			resp.setMsg(ResponseResult.WAIT_PAYING.getMsg());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeStatus)) {
			logger.info("订单号:" + order .getId() + "已撤销");
			tradeOrder.setStatus(OrderStatus.CANCEL.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		if (OrderStatus.CLOSED.name().equals(tradeStatus)) {
			logger.info("订单号:" + order .getId() + "已关闭");
			tradeOrder.setStatus(OrderStatus.CLOSED.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		return resp;
	}

	@Override
	public TradeQueryResponse updateUnifiedPayQuery(TradeOrder tradeOrder, ChannelResponseInfo channelReponseInfo) {
		TradeQueryResponse resp = new TradeQueryResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		logger.info("error code:" + channelReponseInfo.getCode() + ",error msg:" + channelReponseInfo.getMsg());
		OrderOper orderOper = findByOrderIdAndOperType(tradeOrder.getId(), OrderType.PAY.name());
		Date updateTime = DateUtil.now();
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			logger.info("查询接口调用失败，原交易还是等待支付的状态");
			resp.setCode(ResponseResult.WAIT_PAYING.getCode());
			resp.setMsg(ResponseResult.WAIT_PAYING.getMsg());
			resp.setTradeState(OrderStatus.WAITING.name());
			return resp;
		}
		// 检查订单状态，如果订单状态不是WAITING等待中，则不更新订单，返回信息为数据库中记录的订单状态。
		TradeOrder databaseOrder = findOrderById(tradeOrder.getId(), true);
		if (OrderStatus.CLOSED.name().equals(databaseOrder.getStatus())) {
			logger.warn("订单已经被关闭.");
			resp.setCode(ResponseResult.ORDER_CLOSED.getCode());
			resp.setMsg(ResponseResult.ORDER_CLOSED.getMsg());
			resp.setTradeState(OrderStatus.CLOSED.name());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(databaseOrder.getStatus())) {
			logger.warn("订单已经被撤销.");
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			resp.setTradeState(OrderStatus.CANCEL.name());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(databaseOrder.getStatus())) {
			logger.warn("订单支付失败.");
			resp.setCode(ResponseResult.ORDER_STATUS_ERROR.getCode());
			resp.setMsg(ResponseResult.ORDER_STATUS_ERROR.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		// 更新操作和订单
		String tradeStatus = channelReponseInfo.getTradeStatus();
		logger.info("trade status:" + tradeStatus);
		if (OrderStatus.SUCCESS.name().equals(tradeStatus)) {
			logger.info("支付成功");
			databaseOrder.setStatus(OrderStatus.SUCCESS.name());
			Integer payPoint=channelReponseInfo.getDiscountFee()==null?channelReponseInfo.getTotalFee():(channelReponseInfo.getTotalFee() - channelReponseInfo.getDiscountFee());
			databaseOrder.setPayPoint(payPoint);
			databaseOrder.setDiscountPoint(channelReponseInfo.getDiscountFee());
			databaseOrder.setChannelTxnNo(channelReponseInfo.getChannelTxnNo());
			databaseOrder.setThirdPaymentTxnid(channelReponseInfo.getThirdPaymentTxnid());
			databaseOrder.setThirdBuyerAccount(channelReponseInfo.getThirdBuyerAccount());
			databaseOrder.setThirdSellerAccount(channelReponseInfo.getThirdSellerAccount());
			databaseOrder.setTradeDate(channelReponseInfo.getTradeEndDate());
			databaseOrder.setTradeTime(channelReponseInfo.getTradeEndTime());
			databaseOrder.setUpdateTime(updateTime);
			updateTradeOrder(databaseOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setPayPoint(payPoint);
			orderOper.setChannelTxnNo(channelReponseInfo.getChannelTxnNo());
			orderOper.setThirdPaymentTxnid(channelReponseInfo.getThirdPaymentTxnid());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setTradeState(OrderStatus.SUCCESS.name());
			resp.setMchId(databaseOrder.getCulCommNo());
			resp.setOutTradeNo(databaseOrder.getOrderNo());
			resp.setTransactionId(databaseOrder.getTradeNo());
			resp.setTimeEnd(databaseOrder.getTradeDate() + databaseOrder.getTradeTime());
			String disCountPoint = null;
			if (databaseOrder.getDiscountPoint() == null) {
				disCountPoint = "0";
			} else {
				disCountPoint = databaseOrder.getDiscountPoint() + "";
			}
			resp.setDiscountAmount(disCountPoint);
			resp.setTotalAmount(databaseOrder.getOrderPoint() + "");
			resp.setDeviceInfo(databaseOrder.getDeviceInfo());
			resp.setMerchantNo(databaseOrder.getMerchantNo());
			resp.setBuyerAccount(databaseOrder.getThirdBuyerAccount());
			resp.setExtendInfo(databaseOrder.getExtendInfo());
			resp.setSubIsSubscribe(channelReponseInfo.getSubIsSubscribe());
			resp.setOutTransactionId(tradeOrder.getThirdPaymentTxnid());
			resp.setTradeType(tradeOrder.getTradeType());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeStatus)) {
			logger.info("交易失败");
			databaseOrder.setStatus(OrderStatus.FAILED.name());
			databaseOrder.setUpdateTime(updateTime);
			databaseOrder.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(databaseOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		if (OrderStatus.WAITING.name().equals(tradeStatus)) {
			logger.info("等待中");
			databaseOrder.setStatus(OrderStatus.WAITING.name());
			databaseOrder.setUpdateTime(updateTime);
			updateTradeOrder(databaseOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.WAIT_PAYING.getCode());
			resp.setMsg(ResponseResult.WAIT_PAYING.getMsg());
			resp.setTradeState(OrderStatus.WAITING.name());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeStatus)) {
			logger.info("已撤销");
			databaseOrder.setStatus(OrderStatus.CANCEL.name());
			databaseOrder.setUpdateTime(updateTime);
			updateTradeOrder(databaseOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			resp.setTradeState(OrderStatus.CANCEL.name());
			return resp;
		}
		if (OrderStatus.CLOSED.name().equals(tradeStatus)) {
			logger.info("已关闭");
			databaseOrder.setStatus(OrderStatus.CLOSED.name());
			databaseOrder.setUpdateTime(updateTime);
			updateTradeOrder(databaseOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.ORDER_CLOSED.getCode());
			resp.setMsg(ResponseResult.ORDER_CLOSED.getMsg());
			resp.setTradeState(OrderStatus.CLOSED.name());
			return resp;
		}
		return resp;
	}

	@Override
	public TradeCancelResponse updateUnifiedCancel(TradeOrder order, OrderOper orderOper,	ChannelResponseInfo channelReponseInfo) {
		TradeCancelResponse resp = new TradeCancelResponse(ResponseResult.OK);
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(DateUtil.now());
			updateOrderOper(orderOper);
			resp = new TradeCancelResponse(channelReponseInfo.getCode(), channelReponseInfo.getMsg());
			//resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		
		if (OrderStatus.SUCCESS.name().equals(channelReponseInfo.getTradeStatus())) {
			order.setUpdateTime(DateUtil.now());
			order.setStatus(OrderStatus.CANCEL.name());
			order.setRemark(channelReponseInfo.getMsg());
			updateTradeOrder(order);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(DateUtil.now());
			orderOper.setChannelTxnNo(channelReponseInfo.getChannelTxnNo());
			updateOrderOper(orderOper);
			resp.setMchId(order.getCulCommNo());
			resp.setOutTradeNo(order.getOrderNo());
			resp.setTransactionId(order.getTradeNo());
			resp.setTradeState(OrderStatus.CANCEL.name());
			return resp;
		} else {
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(DateUtil.now());
			orderOper.setChannelTxnNo(channelReponseInfo.getChannelTxnNo());
			updateOrderOper(orderOper);
			resp = new TradeCancelResponse(channelReponseInfo.getCode(), "撤销失败");
			return resp;
		}
	}

	@Override
	public TradecloseResponse updateUnifiedClose(TradeOrder order, OrderOper orderOper,
			ChannelResponseInfo channelReponseInfo) {
		TradecloseResponse resp = new TradecloseResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			logger.info("接口调用失败");
			orderOper.setStatus(OrderOperStatus.RESP.name()).setUpdateTime(DateUtil.now());
			updateOrderOper(orderOper);
			resp.setCode(channelReponseInfo.getCode());
			resp.setMsg(channelReponseInfo.getMsg());
			//resp.setTradeState(OrderStatus.WAITING.name());
			return resp;
		}
		Date updateTime = DateUtil.now();
		order.setStatus(OrderStatus.CLOSED.name());
		order.setUpdateTime(updateTime);
		updateTradeOrder(order);
		orderOper.setStatus(OrderOperStatus.RESP.name()).setUpdateTime(DateUtil.now());
		updateOrderOper(orderOper);
		resp.setMchId(order.getCulCommNo());
		resp.setOutTradeNo(order.getOrderNo());
		resp.setTradeState(OrderStatus.CLOSED.name());
		return resp;
	}

	@Override
	public TradeRefundResponse updateUnifiedRefund(TradeOrder refundOrder, OrderOper refundOrderOper,
			ChannelResponseInfo channelReponseInfo) {
		TradeRefundResponse resp = new TradeRefundResponse(ResponseResult.OK);
		logger.info("error code:" + channelReponseInfo.getCode() + ",error msg:" + channelReponseInfo.getMsg());
		Date updateTime = DateUtil.now();
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			logger.info("接口调用失败");
			refundOrder.setStatus(OrderStatus.FAILED.name()).setUpdateTime(updateTime);
			refundOrder.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(refundOrder);
			refundOrderOper.setStatus(OrderOperStatus.RESP.name()).setUpdateTime(updateTime);
			updateOrderOper(refundOrderOper);
			resp.setCode(channelReponseInfo.getCode());
			resp.setMsg(channelReponseInfo.getMsg());
			return resp;
		}
		// 第三方返回信息
		// 更新操作和订单
		TradeOrder databaseOrder = findOrderById(refundOrder.getId(), true);
		if (OrderStatus.FAILED.name().equals(databaseOrder.getStatus())) {
			logger.warn("数据库订单状态已被修改成退款失败.");
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		if (OrderStatus.SUCCESS.name().equals(databaseOrder.getStatus())) {
			logger.warn("数据库订单状态已被修改成退款成功.");
			resp.setMchId(databaseOrder.getCulCommNo()).setOutTradeNo(databaseOrder.getOrigOrderNo())
			.setRefundNo(databaseOrder.getOrderNo()).setRefundId(databaseOrder.getTradeNo())
			.setRefundAmount(String.valueOf(databaseOrder.getOrderPoint()))
			.setTradeState(OrderStatus.SUCCESS.name())
			.setRefundTime(databaseOrder.getTradeDate() + databaseOrder.getTradeTime());
			resp.setMerchantNo(databaseOrder.getMerchantNo());
			resp.setDeviceInfo(databaseOrder.getDeviceInfo());
			return resp;
		}
		String tradeStatus = channelReponseInfo.getTradeStatus();
		logger.info("trade status:" + tradeStatus);
		if (OrderStatus.SUCCESS.name().equals(tradeStatus)) {
			logger.info("调用第三方退款成功");
			databaseOrder.setStatus(OrderStatus.SUCCESS.name());
			databaseOrder.setChannelTxnNo(channelReponseInfo.getChannelTxnNo());
			databaseOrder.setThirdPaymentTxnid(channelReponseInfo.getThirdPaymentTxnid());
			databaseOrder.setThirdBuyerAccount(channelReponseInfo.getThirdBuyerAccount());
			databaseOrder.setThirdSellerAccount(channelReponseInfo.getThirdSellerAccount());
			databaseOrder.setTradeDate(channelReponseInfo.getTradeEndDate());
			databaseOrder.setTradeTime(channelReponseInfo.getTradeEndTime());
			databaseOrder.setUpdateTime(updateTime);
			updateTradeOrder(databaseOrder);
			refundOrderOper.setStatus(OrderOperStatus.RESP.name());
			refundOrderOper.setUpdateTime(updateTime);
			updateOrderOper(refundOrderOper);
			resp.setOutTradeNo(databaseOrder.getOrigOrderNo()).setMchId(databaseOrder.getCulCommNo())
			.setRefundNo(databaseOrder.getOrderNo()).setRefundId(databaseOrder.getTradeNo())
			.setRefundAmount(String.valueOf(databaseOrder.getOrderPoint()))
			.setTradeState(OrderStatus.SUCCESS.name())
			.setRefundTime(databaseOrder.getTradeDate() + databaseOrder.getTradeTime())
		    .setMerchantNo(databaseOrder.getMerchantNo()).setDeviceInfo(databaseOrder.getDeviceInfo());			
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeStatus)) {
			logger.info("退款交易失败");
			databaseOrder.setStatus(OrderStatus.FAILED.name());
			databaseOrder.setUpdateTime(updateTime);
			databaseOrder.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(databaseOrder);
			refundOrderOper.setStatus(OrderOperStatus.RESP.name());
			refundOrderOper.setUpdateTime(updateTime);
			updateOrderOper(refundOrderOper);
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		if (OrderStatus.WAITING.name().equals(tradeStatus)) {
			logger.info("退款等待中");
			databaseOrder.setStatus(OrderStatus.WAITING.name());
			databaseOrder.setUpdateTime(updateTime);
			updateTradeOrder(databaseOrder);
			refundOrderOper.setStatus(OrderOperStatus.RESP.name());
			refundOrderOper.setUpdateTime(updateTime);
			updateOrderOper(refundOrderOper);
			resp.setCode(ResponseResult.WAIT_REFUND.getCode());
			resp.setMsg(ResponseResult.WAIT_REFUND.getMsg());
			resp.setTradeState(OrderStatus.WAITING.name());
			return resp;
		}
		return resp;
	}

	@Override
	public RefundQueryResponse updateUnifiedRefundQuery(TradeOrder tradeOrder, ChannelResponseInfo channelReponseInfo) {
		RefundQueryResponse resp = new RefundQueryResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			logger.info("接口调用失败");
			resp.setCode(ResponseResult.WAIT_REFUND.getCode());
			resp.setMsg(ResponseResult.WAIT_REFUND.getMsg());
			resp.setTradeState(OrderStatus.WAITING.name());
			return resp;
		}
		// 第三方返回信息
		// 更新操作和订单
		TradeOrder databaseOrder = findOrderById(tradeOrder.getId(), true);
		if (OrderStatus.FAILED.name().equals(databaseOrder.getStatus())) {
			logger.warn("数据库订单状态已被修改成退款失败.");
			resp.setCode(ResponseResult.ORDER_STATUS_ERROR.getCode());
			resp.setMsg(ResponseResult.ORDER_STATUS_ERROR.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		if (OrderStatus.SUCCESS.name().equals(databaseOrder.getStatus())) {
			logger.warn("数据库订单状态已被修改成退款成功.");
			resp.setMchId(databaseOrder.getCulCommNo())
			.setOutTradeNo(tradeOrder.getOrigOrderNo())
			.setRefundNo(tradeOrder.getOrderNo())
			.setRefundId(tradeOrder.getTradeNo())
			.setRefundAmount(String.valueOf(tradeOrder.getOrderPoint()))
			.setTradeState(OrderStatus.SUCCESS.name())
			.setRefundTime(tradeOrder.getTradeDate() + tradeOrder.getTradeTime());
			resp.setMerchantNo(tradeOrder.getMerchantNo());
			resp.setDeviceInfo(tradeOrder.getDeviceInfo());
			return resp;
		}
		// 更新操作和订单
		Date updateTime = DateUtil.now();
		String tradeStatus = channelReponseInfo.getTradeStatus();	
		logger.info("trade status:" + tradeStatus);
		OrderOper orderOper = findByOrderIdAndOperType(databaseOrder.getId(), OrderType.REFUND.name());
		if (OrderStatus.SUCCESS.name().equals(tradeStatus)) {
			logger.info("调用第三方退款成功");
			databaseOrder.setStatus(OrderStatus.SUCCESS.name());
			databaseOrder.setTradeDate(channelReponseInfo.getTradeEndDate());
			databaseOrder.setTradeTime(channelReponseInfo.getTradeEndTime());
			databaseOrder.setUpdateTime(updateTime);
			databaseOrder.setChannelTxnNo(channelReponseInfo.getChannelTxnNo());
			databaseOrder.setThirdPaymentTxnid(channelReponseInfo.getThirdPaymentTxnid());
			databaseOrder.setThirdBuyerAccount(channelReponseInfo.getThirdBuyerAccount());
			databaseOrder.setThirdSellerAccount(channelReponseInfo.getThirdSellerAccount());
			updateTradeOrder(databaseOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setChannelTxnNo(channelReponseInfo.getChannelTxnNo());
			orderOper.setThirdPaymentTxnid(channelReponseInfo.getThirdPaymentTxnid());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setOutTradeNo(tradeOrder.getOrigOrderNo())
			.setMchId(databaseOrder.getCulCommNo())
			.setRefundNo(tradeOrder.getOrderNo())
			.setRefundId(tradeOrder.getTradeNo())
			.setRefundAmount(String.valueOf(tradeOrder.getOrderPoint()))
			.setTradeState(OrderStatus.SUCCESS.name())
			.setRefundTime(tradeOrder.getTradeDate() + tradeOrder.getTradeTime());
			resp.setMerchantNo(tradeOrder.getMerchantNo());
			resp.setDeviceInfo(tradeOrder.getDeviceInfo());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeStatus)) {
			logger.info("退款交易失败");
			databaseOrder.setStatus(OrderStatus.FAILED.name());
			databaseOrder.setUpdateTime(updateTime);
			updateTradeOrder(databaseOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		if (OrderStatus.WAITING.name().equals(tradeStatus)) {
			logger.info("退款等待中");
			databaseOrder.setStatus(OrderStatus.WAITING.name());
			databaseOrder.setUpdateTime(updateTime);
			updateTradeOrder(databaseOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setOutTradeNo(tradeOrder.getOrigOrderNo())
			.setMchId(databaseOrder.getCulCommNo())
			.setRefundNo(tradeOrder.getOrderNo())
			.setRefundId(tradeOrder.getTradeNo())
			.setRefundAmount(String.valueOf(tradeOrder.getOrderPoint()))
			.setTradeState(OrderStatus.WAITING.name())
			.setRefundTime(tradeOrder.getTradeDate() + tradeOrder.getTradeTime());
			resp.setCode(ResponseResult.WAIT_REFUND.getCode());
			resp.setMsg(ResponseResult.WAIT_REFUND.getMsg());
			return resp;
		}
		return resp;
	}

	@Override
	public WxNativeResponse updateWxNativePreOrder(TradeOrder order, OrderOper orderOper,
			ChannelResponseInfo channelReponseInfo) {
		WxNativeResponse resp = new WxNativeResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		logger.info("error code:" + channelReponseInfo.getCode() + ",error msg:" + channelReponseInfo.getMsg());
		// 调用失败
		Date updateTime = DateUtil.now();
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			order.setStatus(OrderStatus.FAILED.name()).setUpdateTime(updateTime);
			order.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(order);
			orderOper.setStatus(OrderOperStatus.RESP.name()).setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(channelReponseInfo.getCode());
			resp.setMsg(channelReponseInfo.getMsg());
			return resp;
		}
		// 检查订单状态，如果订单状态不是WAITING等待中，则不更新订单，返回信息为数据库中记录的订单状态。
		TradeOrder tradeOrder = findOrderByTradeNo(order.getTradeNo(), false);
		if (OrderStatus.CLOSED.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order.getId() + "已经被关闭.");
			resp.setCode(ResponseResult.ORDER_CLOSED.getCode());
			resp.setMsg(ResponseResult.ORDER_CLOSED.getMsg());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order.getId() + "已经被撤销.");
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order.getId() + "已经被关闭.");
			resp.setCode(ResponseResult.ORDER_STATUS_ERROR.getCode());
			resp.setMsg(ResponseResult.ORDER_STATUS_ERROR.getMsg());
			return resp;
		}
		// 更新操作和订单
		String tradeStatus = channelReponseInfo.getTradeStatus();
		logger.info("trade status:" + tradeStatus);
		if (OrderStatus.SUCCESS.name().equals(tradeStatus)) {
			logger.info("预下单成功");
			tradeOrder.setStatus(OrderStatus.WAITING.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setAuthCodeImgUrl(channelReponseInfo.getAuthCodeImgUrl());
			resp.setAuthCodeUrl(channelReponseInfo.getAuthCodeUrl());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeStatus)) {
			logger.info("订单号:" + order.getId() + "预下单失败");
			tradeOrder.setStatus(OrderStatus.FAILED.name());
			tradeOrder.setUpdateTime(updateTime);
			order.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			return resp;
		}
		if (OrderStatus.WAITING.name().equals(tradeStatus)) {
			logger.info("等待中");
			tradeOrder.setStatus(OrderStatus.WAITING.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.WAIT_PAYING.getCode());
			resp.setMsg(ResponseResult.WAIT_PAYING.getMsg());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeStatus)) {
			logger.info("订单号:" + order.getId() + "已撤销");
			tradeOrder.setStatus(OrderStatus.CANCEL.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		if (OrderStatus.CLOSED.name().equals(tradeStatus)) {
			logger.info("订单号:" + order.getId() + "已关闭");
			tradeOrder.setStatus(OrderStatus.CLOSED.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		return resp;
	}

	@Override
	public WxJsResponse updateWxJsPreOrder(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo) {
		WxJsResponse resp = new WxJsResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		logger.info("error code:" + channelReponseInfo.getCode() + ",error msg:" + channelReponseInfo.getMsg());
		// 调用失败
		Date updateTime = DateUtil.now();
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			order.setStatus(OrderStatus.FAILED.name()).setUpdateTime(updateTime);
			order.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(order);
			orderOper.setStatus(OrderOperStatus.RESP.name()).setUpdateTime(updateTime);
			updateOrderOper(orderOper);

			resp.setCode(channelReponseInfo.getCode());
			resp.setMsg(channelReponseInfo.getMsg());
			return resp;
		}
		// 检查订单状态，如果订单状态不是WAITING等待中，则不更新订单，返回信息为数据库中记录的订单状态。
		TradeOrder tradeOrder = findOrderByTradeNo(order.getTradeNo(), true);
		if (OrderStatus.CLOSED.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order.getId() + "已经被关闭.");
			resp.setCode(ResponseResult.ORDER_CLOSED.getCode());
			resp.setMsg(ResponseResult.ORDER_CLOSED.getMsg());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order.getId() + "已经被撤销.");
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单:" + order.getId() + "已经被关闭.");
			resp.setCode(ResponseResult.ORDER_STATUS_ERROR.getCode());
			resp.setMsg(ResponseResult.ORDER_STATUS_ERROR.getMsg());
			return resp;
		}
		// 更新操作和订单
		String tradeStatus = channelReponseInfo.getTradeStatus();
		logger.info("trade status:" + tradeStatus);
		if (OrderStatus.SUCCESS.name().equals(tradeStatus)) {
			logger.info("预下单成功");
			tradeOrder.setStatus(OrderStatus.WAITING.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setMchId(order.getCulCommNo());
			resp.setPayInfo(channelReponseInfo.getPayInfo());
			resp.setDeviceInfo(channelReponseInfo.getDeviceInfo());
			resp.setPayInfo(channelReponseInfo.getPayInfo());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeStatus)) {
			logger.info("订单号:" + order.getId() + "预下单失败");
			tradeOrder.setStatus(OrderStatus.FAILED.name());
			tradeOrder.setUpdateTime(updateTime);
			order.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);

			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setDeviceInfo(channelReponseInfo.getDeviceInfo());
			return resp;
		}
		if (OrderStatus.WAITING.name().equals(tradeStatus)) {
			logger.info("等待中");
			tradeOrder.setStatus(OrderStatus.WAITING.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);

			resp.setCode(ResponseResult.WAIT_PAYING.getCode());
			resp.setMsg(ResponseResult.WAIT_PAYING.getMsg());
			resp.setDeviceInfo(channelReponseInfo.getDeviceInfo());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeStatus)) {
			logger.info("订单号:" + order.getId() + "已撤销");
			tradeOrder.setStatus(OrderStatus.CANCEL.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);

			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			resp.setDeviceInfo(channelReponseInfo.getDeviceInfo());
			return resp;
		}
		if (OrderStatus.CLOSED.name().equals(tradeStatus)) {
			logger.info("订单号:" + order.getId() + "已关闭");
			tradeOrder.setStatus(OrderStatus.CLOSED.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);

			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			resp.setDeviceInfo(channelReponseInfo.getDeviceInfo());
			return resp;
		}
		return resp;
	}

	@Override
	public MicroPayResponse updateMicropayOrder(TradeOrder order, OrderOper orderOper,
			ChannelResponseInfo channelReponseInfo) {
		MicroPayResponse resp = new MicroPayResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		logger.info("error code:" + channelReponseInfo.getCode() + ",error msg:" + channelReponseInfo.getMsg());
		Date updateTime = DateUtil.now();
		// 调用失败
		if (!Constants.OK.equals(channelReponseInfo.getCode())) {
			order.setStatus(OrderStatus.FAILED.name()).setUpdateTime(updateTime);
			order.setFailMsg(channelReponseInfo.getMsg());
			updateTradeOrder(order);
			orderOper.setStatus(OrderOperStatus.RESP.name()).setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(channelReponseInfo.getCode());
			resp.setMsg(channelReponseInfo.getMsg());
			return resp;
		}
		// 查询订单状态，支付接口响应时间较长，商户调起撤销，如果订单已经被撤销，此时直接返回订单已撤销
		TradeOrder tradeOrder = findOrderByTradeNo(order.getTradeNo(), true);
		if (OrderStatus.CANCEL.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单已经被撤销.");
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeOrder.getStatus())) {
			logger.warn("订单状态已被修改.");
			resp.setCode(ResponseResult.ORDER_STATUS_ERROR.getCode());
			resp.setMsg(ResponseResult.ORDER_STATUS_ERROR.getMsg());
			return resp;
		}
		// 更新操作和订单
		String tradeStatus = channelReponseInfo.getTradeStatus();
		if (OrderStatus.SUCCESS.name().equals(tradeStatus)) {
			logger.info("支付成功");
			Integer payPoint = channelReponseInfo.getDiscountFee() == null ? channelReponseInfo.getTotalFee()
					: (channelReponseInfo.getTotalFee() - channelReponseInfo.getDiscountFee());
			tradeOrder.setStatus(OrderStatus.SUCCESS.name());
			tradeOrder.setPayPoint(payPoint);
			tradeOrder.setDiscountPoint(channelReponseInfo.getDiscountFee());
			tradeOrder.setChannelTxnNo(channelReponseInfo.getChannelTxnNo());
			tradeOrder.setThirdPaymentTxnid(channelReponseInfo.getThirdPaymentTxnid());
			tradeOrder.setThirdBuyerAccount(channelReponseInfo.getThirdBuyerAccount());
			tradeOrder.setThirdSellerAccount(channelReponseInfo.getThirdSellerAccount());
			tradeOrder.setTradeDate(channelReponseInfo.getTradeEndDate());
			tradeOrder.setTradeTime(channelReponseInfo.getTradeEndTime());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setPayPoint(channelReponseInfo.getTotalFee());
			orderOper.setChannelTxnNo(channelReponseInfo.getChannelTxnNo());
			orderOper.setThirdPaymentTxnid(channelReponseInfo.getThirdPaymentTxnid());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(channelReponseInfo.getCode());
			resp.setTotalAmount(String.valueOf(channelReponseInfo.getTotalFee()));
			String disCountPoint = null;
			if (channelReponseInfo.getDiscountFee() == null) {
				disCountPoint = "0";
			} else {
				disCountPoint = channelReponseInfo.getDiscountFee() + "";
			}
			resp.setDiscountAmount(disCountPoint);
			resp.setSubIsSubscribe(channelReponseInfo.getSubIsSubscribe());
			resp.setBuyerAccount(channelReponseInfo.getThirdBuyerAccount());
			resp.setExtendInfo(tradeOrder.getExtendInfo());
			resp.setMchId(tradeOrder.getCulCommNo());
			resp.setOutTradeNo(tradeOrder.getOrderNo());
			resp.setSubAppid(channelReponseInfo.getSubAppid());
			resp.setTransactionId(tradeOrder.getTradeNo());
			resp.setTradeState(channelReponseInfo.getTradeStatus());
			resp.setTradeType(tradeOrder.getTradeType());
			resp.setOutTransactionId(channelReponseInfo.getThirdPaymentTxnid());
			resp.setTimeEnd(channelReponseInfo.getTradeEndDate() + channelReponseInfo.getTradeEndTime());
			resp.setDeviceInfo(tradeOrder.getDeviceInfo());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeStatus)) {
			logger.info("支付失败");
			tradeOrder.setStatus(OrderStatus.FAILED.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setMchId(tradeOrder.getCulCommNo());
			resp.setDeviceInfo(tradeOrder.getDeviceInfo());
			return resp;
		}
		if (OrderStatus.WAITING.name().equals(tradeStatus)) {
			logger.info("交易状态为等待中");
			tradeOrder.setStatus(OrderStatus.WAITING.name());
			tradeOrder.setUpdateTime(updateTime);
			updateTradeOrder(tradeOrder);
			orderOper.setStatus(OrderOperStatus.RESP.name());
			orderOper.setUpdateTime(updateTime);
			updateOrderOper(orderOper);
			resp.setCode(ResponseResult.WAIT_PAYING.getCode());
			resp.setMsg(ResponseResult.WAIT_PAYING.getMsg());
			resp.setTradeState(OrderStatus.WAITING.name());
			resp.setMchId(tradeOrder.getCulCommNo());
			resp.setDeviceInfo(tradeOrder.getDeviceInfo());
			return resp;
		}
		return resp;
	}

	@Override
	public String createTradeNo(BankCode bankCode) {
		// yyMMdd + 3-6位银行代码  + 6-18位随机数
		String date = DateUtil.formatDate(DateUtil.now(), "yyMMdd");
		String code = bankCode.getCode();
		String str = date + code;
		int randomLength = BankOrderNoLength.getOrderLength(bankCode) - str.length();
		return str + RandomStringCreator.generateString(randomLength);
	}

	@Override
	public List<TradeOrder> findByOrderTypeAndStatusAndCreateTimeBetween(String orderType, String status,
			Date startTime, Date endTime) {
		logger.info("orderType:" + orderType + ",orderStatus:" + status);
		logger.info("startTime:" + startTime + ",endTime:" + endTime);
		switch (OrderType.getType(orderType)) {
		case PAY:
			return tradeOrderDAO.findByOrderTypeAndStatusAndCreateTimeBetween(OrderType.PAY.name(), status, startTime,
					endTime);
		case REFUND:
			return tradeOrderDAO.findByOrderTypeAndStatusAndCreateTimeBetween(OrderType.REFUND.name(), status, startTime,
					endTime);
		default:
			logger.warn("orderType is a invalid argument.");
			break;
		}
		return null;
	}
	
	@Override
	public String findCommKeyByOutTradeNoAndBankMerNo(String outTradeNo, String bankMerNo) {
		TradeOrder order = this.findOrderByTradeNo(outTradeNo, false);
		if (order == null) {
			return null;
		}
		
		String mchId = order.getCulCommNo();
		TpCommericalInfo info = tpCommericalInfoDAO.findByCulCommNoAndBankMerNoAndCommStatus(mchId, bankMerNo, Constants.YES);
		if (info == null) {
			logger.info("culCommNo:{},bankMerNo{}的记录不存在", mchId, bankMerNo);
		}
		return info.getCommKey();
	}
}
