package cn.com.upcard.mgateway.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.upcard.mgateway.channel.ThirdChannelTradeFactory;
import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.OrderOperStatus;
import cn.com.upcard.mgateway.common.enums.OrderOperType;
import cn.com.upcard.mgateway.common.enums.OrderStatus;
import cn.com.upcard.mgateway.common.enums.OrderType;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.controller.response.AlipayJsapiResponse;
import cn.com.upcard.mgateway.controller.response.AlipayNativeResponse;
import cn.com.upcard.mgateway.controller.response.MicroPayResponse;
import cn.com.upcard.mgateway.controller.response.QRCodePreResponse;
import cn.com.upcard.mgateway.controller.response.RefundQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeCancelResponse;
import cn.com.upcard.mgateway.controller.response.TradeQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeRefundResponse;
import cn.com.upcard.mgateway.controller.response.TradecloseResponse;
import cn.com.upcard.mgateway.controller.response.WxJsResponse;
import cn.com.upcard.mgateway.controller.response.WxNativeResponse;
import cn.com.upcard.mgateway.entity.OrderOper;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.exception.OrderNoRepeatException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.service.ApiLogService;
import cn.com.upcard.mgateway.service.GatewayService;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.util.DateUtil;

@Service
public class GatewayServiceImpl implements GatewayService {
	private static Logger logger = LoggerFactory.getLogger(GatewayServiceImpl.class);
	@Autowired
	private OrderOperService orderOperService;
	@Autowired
	private ApiLogService apiLogService;
	
	@Override
	public AlipayNativeResponse alipayNativePreOrder(PreTradeOrderInfo preTradeOrderInfo) {
		AlipayNativeResponse resp = new AlipayNativeResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		logger.info("enter alipayNativePay, orderNo:" + preTradeOrderInfo.getOutTradeNo());

		// 创建订单和操作
		TradeOrder order = unifiedCreateTradeOrder(preTradeOrderInfo);
		if (order == null) {
			// 订单重复
			resp.setCode(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getCode());
			resp.setMsg(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getMsg());
			return resp;
		}
		OrderOper orderOper = orderOperService.saveOrderOper(order, OrderOperType.PAY.name(), null, null, preTradeOrderInfo.getReqIp());
		// 更新订单和订单操作
		Date now = DateUtil.now();
		order.setStatus(OrderStatus.WAITING.name()).setUpdateTime(now);
		orderOperService.updateTradeOrder(order);
		orderOper.setStatus(OrderOperStatus.COMMIT.name()).setUpdateTime(now);
		orderOperService.updateOrderOper(orderOper);
		// 构造参数
		preTradeOrderInfo.setTradeNo(order.getTradeNo());
		ChannelRequestInfo channelRequestInfo = ChannelRequestInfo.getAliNativePayInstance(preTradeOrderInfo);
		// 调用第三方预下单
		ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory.produce(
				TradeType.ALI_NATIVE.name(), preTradeOrderInfo.getBankCode()).pay(channelRequestInfo);
		if (channelReponseInfo == null) {
			logger.info("调用第三方接口无应答");
			resp.setCode(ResponseResult.NON_RESP.getCode());
			resp.setMsg(ResponseResult.NON_RESP.getMsg());
			orderOper.setStatus(OrderOperStatus.NON_RESP.name()).setUpdateTime(DateUtil.now());
			orderOperService.updateOrderOper(orderOper);
			return resp;
		}
		//保存日志
		apiLogService.saveApiLog(order.getId(), OrderOperType.PAY, JSON.toJSONString(channelReponseInfo));
		//更新订单以及操作
		return orderOperService.updateAlipayNativePreOrder(order, orderOper, channelReponseInfo);
	}
	
	@Override
	public AlipayJsapiResponse alipayJsapiPreOrder(PreTradeOrderInfo preTradeOrderInfo) {
		AlipayJsapiResponse resp = new AlipayJsapiResponse(ResponseResult.OK);
		logger.info("enter alipayjsapi prepare order, orderNo:" + preTradeOrderInfo.getOutTradeNo());
		// 创建订单和操作
		TradeOrder order = unifiedCreateTradeOrder(preTradeOrderInfo);
		if (order == null) {
			// 订单重复
			return new AlipayJsapiResponse(ResponseResult.OUT_TRADE_NO_IS_EXISTS);
		}
		OrderOper orderOper = orderOperService.saveOrderOper(order, OrderOperType.PAY.name(), null,
				preTradeOrderInfo.getThirdBuyerId(), preTradeOrderInfo.getReqIp());
		// 更新订单和订单操作
		Date now = DateUtil.now();
		order.setStatus(OrderStatus.WAITING.name()).setUpdateTime(now);
		orderOperService.updateTradeOrder(order);
		orderOper.setStatus(OrderOperStatus.COMMIT.name()).setUpdateTime(now);
		orderOperService.updateOrderOper(orderOper);

		preTradeOrderInfo.setTradeNo(order.getTradeNo());
		ChannelRequestInfo channelRequestInfo = ChannelRequestInfo.getAliJsapiInstance(preTradeOrderInfo);

		ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory
				.produce(TradeType.ALI_JSAPI.name(), preTradeOrderInfo.getBankCode()).pay(channelRequestInfo);
		if (channelReponseInfo == null) {
			logger.info("调用第三方接口无应答");
			orderOper.setStatus(OrderOperStatus.NON_RESP.name()).setUpdateTime(DateUtil.now());
			orderOperService.updateOrderOper(orderOper);
			return new AlipayJsapiResponse(ResponseResult.NON_RESP);
		}
		// 保存日志
		apiLogService.saveApiLog(order.getId(), OrderOperType.PAY, JSON.toJSONString(channelReponseInfo));
		// 更新订单以及操作
		return orderOperService.updateAlipayJsapiPreOrder(order, orderOper, channelReponseInfo);
	}

	@Override
	public WxNativeResponse wxNativePreOrder(PreTradeOrderInfo preTradeOrderInfo) {

		logger.info("enter wxNativePreOrder, orderNo:" + preTradeOrderInfo.getOutTradeNo());

		WxNativeResponse resp = new WxNativeResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());

		// 创建订单和操作
		TradeOrder order = unifiedCreateTradeOrder(preTradeOrderInfo);
		if (order == null) {
			// 订单重复
			resp.setCode(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getCode());
			resp.setMsg(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getMsg());
			return resp;
		}

		// 保存订单操作
		OrderOper orderOper = orderOperService.saveOrderOper(order, OrderOperType.PAY.name(), null, null,
				preTradeOrderInfo.getReqIp());
		// 更新订单和订单操作
		Date now = DateUtil.now();
		order.setStatus(OrderStatus.WAITING.name()).setUpdateTime(now);
		orderOperService.updateTradeOrder(order);
		orderOper.setStatus(OrderOperStatus.COMMIT.name()).setUpdateTime(now);
		orderOperService.updateOrderOper(orderOper);

		preTradeOrderInfo.setTradeNo(order.getTradeNo());
		ChannelRequestInfo requestInfo = ChannelRequestInfo.getWxNativePayInstance(preTradeOrderInfo);
		ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory
				.produce(TradeType.WX_NATIVE.name(), preTradeOrderInfo.getBankCode()).pay(requestInfo);
		if (channelReponseInfo == null) {
			logger.info("调用第三方接口无应答");
			resp.setCode(ResponseResult.NON_RESP.getCode());
			resp.setMsg(ResponseResult.NON_RESP.getMsg());
			orderOper.setStatus(OrderOperStatus.NON_RESP.name()).setUpdateTime(DateUtil.now());
			orderOperService.updateOrderOper(orderOper);
			return resp;
		}

		// 保存日志
		apiLogService.saveApiLog(order.getId(), OrderOperType.PAY, JSON.toJSONString(channelReponseInfo));
		// 更新订单和操作
		return orderOperService.updateWxNativePreOrder(order, orderOper, channelReponseInfo);
	}
	
	@Override
	public WxJsResponse wxJsPreOrder(PreTradeOrderInfo preTradeOrderInfo) {
		logger.info("enter wxJsPayPreOrder, orderNo:" + preTradeOrderInfo.getOutTradeNo());

		WxJsResponse resp = new WxJsResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());

		// 创建订单和操作
		TradeOrder order = unifiedCreateTradeOrder(preTradeOrderInfo);
		if (order == null) {
			// 订单重复
			resp.setCode(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getCode());
			resp.setMsg(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getMsg());
			return resp;
		}

		// 保存订单操作
		OrderOper orderOper = orderOperService.saveOrderOper(order, OrderOperType.PAY.name(), null, null,
				preTradeOrderInfo.getReqIp());
		// 更新订单和订单操作
		Date now = DateUtil.now();
		order.setStatus(OrderStatus.WAITING.name()).setUpdateTime(now);
		orderOperService.updateTradeOrder(order);
		orderOper.setStatus(OrderOperStatus.COMMIT.name()).setUpdateTime(now);
		orderOperService.updateOrderOper(orderOper);

		// 生成调用参数
		preTradeOrderInfo.setTradeNo(order.getTradeNo());
		ChannelRequestInfo requestInfo = ChannelRequestInfo.getWxJsPayInstance(preTradeOrderInfo);
		ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory
				.produce(TradeType.WX_JSAPI.name(), preTradeOrderInfo.getBankCode()).pay(requestInfo);
		if (channelReponseInfo == null) {
			logger.info("调用第三方接口无应答");
			resp.setCode(ResponseResult.NON_RESP.getCode());
			resp.setMsg(ResponseResult.NON_RESP.getMsg());
			orderOper.setStatus(OrderOperStatus.NON_RESP.name()).setUpdateTime(DateUtil.now());
			orderOperService.updateOrderOper(orderOper);
			return resp;
		}

		// 保存日志
		apiLogService.saveApiLog(order.getId(), OrderOperType.PAY, JSON.toJSONString(channelReponseInfo));
		// 更新订单和操作
		return orderOperService.updateWxJsPreOrder(order, orderOper, channelReponseInfo);
	}

	@Override
	public TradeOrder createTradeOrder(PreTradeOrderInfo preTradeOrderInfo) {
		// 判断订单号是否重复
		TradeOrder order = orderOperService.findByOrderNoAndCulCommNoAndType(preTradeOrderInfo.getOutTradeNo(),
				preTradeOrderInfo.getCommUnionNo(), null);
		if (order != null) {
			logger.info("订单号" + preTradeOrderInfo.getOutTradeNo() + "重复");
			return null;
		}
		order = orderOperService.createOrder(preTradeOrderInfo);
		return order;
	}
	
	public TradeOrder unifiedCreateTradeOrder(PreTradeOrderInfo preTradeOrderInfo) {
		TradeOrder order = null;
		try {
			order = createTradeOrder(preTradeOrderInfo);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new OrderNoRepeatException("订单号：" + preTradeOrderInfo.getOutTradeNo() + " 重复");
			}
		}
		return order;
	}
	/**
	 * 交易订单信息查询
	 * 
	 * @return
	 */
	@Override
	public TradeQueryResponse unifiedPayQuery(PreTradeOrderInfo preTradeOrderInfo) {
		TradeQueryResponse resp = new TradeQueryResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		logger.info("查询交易订单信息");
		// 1、查询订单信息
		String orderNo = preTradeOrderInfo.getOutTradeNo();
		String culCommNo = preTradeOrderInfo.getCommUnionNo();
		String tradeNo = preTradeOrderInfo.getTradeNo();
		logger.info("tradeNo:" + tradeNo + ",orderNo:" + orderNo + ",culCommNo:" + culCommNo);
		TradeOrder tradeOrder = null;
		// 支付交易查询
		if (StringUtils.isNotEmpty(tradeNo)) {
			tradeOrder = orderOperService.findByTradeNoAndOrderNoAndCulCommNoAndType(tradeNo, orderNo, culCommNo,
					OrderType.PAY.name());
		} else {
			tradeOrder = orderOperService.findByOrderNoAndCulCommNoAndType(orderNo, culCommNo, OrderType.PAY.name());
		}
		if (tradeOrder == null) {
			logger.info("未查询到订单信息");
			resp.setCode(ResponseResult.ORDER_NOT_EXISTS.getCode());
			resp.setMsg(ResponseResult.ORDER_NOT_EXISTS.getMsg());
			return resp;
		}
		if (OrderStatus.CLOSED.name().equals(tradeOrder.getStatus())) {
			resp.setCode(ResponseResult.ORDER_CLOSED.getCode());
			resp.setMsg(ResponseResult.ORDER_CLOSED.getMsg());
			resp.setTradeState(OrderStatus.CLOSED.name());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeOrder.getStatus())) {
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			resp.setTradeState(OrderStatus.CANCEL.name());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(tradeOrder.getStatus())) {
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		// 2、1支付成功、失败，返回信息
		if (OrderStatus.SUCCESS.name().equals(tradeOrder.getStatus())) {
			// 组装返回信息
			resp.setTradeState(OrderStatus.SUCCESS.name());
			resp.setMchId(tradeOrder.getCulCommNo());
			resp.setOutTradeNo(tradeOrder.getOrderNo());
			resp.setTransactionId(tradeOrder.getTradeNo());
			resp.setTimeEnd(tradeOrder.getTradeDate() + tradeOrder.getTradeTime());
			String disCountPoint = null;
			if (tradeOrder.getDiscountPoint() == null) {
				disCountPoint = "0";
			} else {
				disCountPoint = tradeOrder.getDiscountPoint() + "";
			}
			resp.setDiscountAmount(disCountPoint);
			resp.setTotalAmount(String.valueOf(tradeOrder.getOrderPoint()));
			resp.setDeviceInfo(tradeOrder.getDeviceInfo());
			resp.setMerchantNo(tradeOrder.getMerchantNo());
			resp.setBuyerAccount(tradeOrder.getThirdBuyerAccount());
			resp.setOutTransactionId(tradeOrder.getThirdPaymentTxnid());
			resp.setTradeType(tradeOrder.getTradeType());
			return resp;
		}
		// 2、2等待中调用第三方查询接口
		if (OrderStatus.WAITING.name().equals(tradeOrder.getStatus())) {
			ChannelRequestInfo channelRequestInfo = new ChannelRequestInfo();
			channelRequestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
			channelRequestInfo.setOutTradeNo(tradeOrder.getTradeNo());
			channelRequestInfo.setTransactionId(tradeOrder.getChannelTxnNo());
			channelRequestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
			channelRequestInfo.setDeviceInfo(preTradeOrderInfo.getDeviceInfo());
			channelRequestInfo.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
			ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory.produce(tradeOrder.getTradeType(),
					preTradeOrderInfo.getBankCode()).tradeQuery(channelRequestInfo);
			// 2、2、1第三方返回成功、失败，更新订单，返回信息
			if (channelReponseInfo == null) {
				logger.info("无返回信息");
				resp.setCode(ResponseResult.NON_RESP.getCode());
				resp.setMsg(ResponseResult.NON_RESP.getMsg());
				resp.setTradeState(OrderStatus.WAITING.name());
				OrderOper orderOper = orderOperService.findByOrderIdAndOperType(tradeOrder.getId(), OrderType.PAY.name());
				orderOper.setStatus(OrderOperStatus.NON_RESP.name()).setUpdateTime(DateUtil.now());
				orderOperService.updateOrderOper(orderOper);
				return resp;
			}
			//保存日志
			apiLogService.saveApiLog(tradeOrder.getId(), OrderOperType.PAY, JSON.toJSONString(channelReponseInfo));
			//更新订单以及操作
			return orderOperService.updateUnifiedPayQuery(tradeOrder, channelReponseInfo);
		}
		return resp;
	}

	@Override
	public MicroPayResponse microPayOrder(PreTradeOrderInfo preTradeOrderInfo) {
		MicroPayResponse resp = new MicroPayResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		logger.info("enter microPay, autoCode:" + preTradeOrderInfo.getAuthCode());
		// 创建订单和操作
		TradeOrder order = unifiedCreateTradeOrder(preTradeOrderInfo);
		if (order == null) {
			// 订单重复
			resp.setCode(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getCode());
			resp.setMsg(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getMsg());
			return resp;
		}
		OrderOper orderOper = orderOperService.saveOrderOper(order, OrderOperType.PAY.name(), null, null,
				preTradeOrderInfo.getReqIp());
		// 更新订单和订单操作
		Date now = DateUtil.now();
		order.setStatus(OrderStatus.WAITING.name()).setUpdateTime(now);
		orderOperService.updateTradeOrder(order);
		orderOper.setStatus(OrderOperStatus.COMMIT.name()).setUpdateTime(now);
		orderOperService.updateOrderOper(orderOper);
		// 调用第三方支付
		// 构造参数
		ChannelRequestInfo channelRequestInfo = ChannelRequestInfo.getMicropayReqInstance(preTradeOrderInfo);
		channelRequestInfo.setOutTradeNo(order.getTradeNo());
		// 调用支付接口
		ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory.produce(preTradeOrderInfo.getTradeType(),
				preTradeOrderInfo.getBankCode()).pay(channelRequestInfo);
		Date updateTime = DateUtil.now();
		if (channelReponseInfo == null) {
			logger.info("调用第三方接口无应答");
			resp.setCode(ResponseResult.NON_RESP.getCode());
			resp.setMsg(ResponseResult.NON_RESP.getMsg());
			orderOper.setStatus(OrderOperStatus.NON_RESP.name());
			orderOper.setUpdateTime(updateTime);
			orderOperService.updateOrderOper(orderOper);
			return resp;
		}
		// 保存日志
		apiLogService.saveApiLog(order.getId(), OrderOperType.PAY, JSON.toJSONString(channelReponseInfo));
		// 更新订单以及操作
		return orderOperService.updateMicropayOrder(order, orderOper, channelReponseInfo);
	}

	@Override
	public TradeCancelResponse tradeCancel(PreTradeOrderInfo preTradeOrderInfo) {
		TradeCancelResponse resp = new TradeCancelResponse(ResponseResult.OK);
		String orderNo = preTradeOrderInfo.getOutTradeNo();// 商户订单号
		String tradeNo = preTradeOrderInfo.getTradeNo();// 系统订单号
		String culCommNo = preTradeOrderInfo.getCommUnionNo();
		logger.info("tradeNo:" + tradeNo + ",orderNo:" + orderNo + ",culCommNo:" + culCommNo);
		TradeOrder order = null;
		// 支付交易查询
		if (StringUtils.isNotEmpty(tradeNo)) {
			order = orderOperService.findByTradeNoAndOrderNoAndCulCommNoAndType(tradeNo, orderNo, culCommNo,
					OrderType.PAY.name());
		} else {
			order = orderOperService.findByOrderNoAndCulCommNoAndType(orderNo, culCommNo, OrderType.PAY.name());
		}
		if (order == null) {
			return new TradeCancelResponse(ResponseResult.ORDER_NOT_EXISTS);
		} 

		if (!(TradeType.ALI_DIRECT.name().equals(order.getTradeType())
				|| TradeType.WX_MICROPAY.name().equals(order.getTradeType()))) {
			return new TradeCancelResponse(ResponseResult.UNSUPPORTED_SERVICE);
		}

		if (OrderStatus.FAILED.name().equals(order.getStatus())) {
			resp = new TradeCancelResponse(ResponseResult.ORDER_FIAL);
			resp.setTradeState(OrderStatus.WAITING.name());
			return resp;
		} else if (OrderStatus.CLOSED.name().equals(order.getStatus())) {
			resp = new TradeCancelResponse(ResponseResult.ORDER_CLOSED);
			resp.setTradeState(OrderStatus.CLOSED.name());
			return resp;
		} else if (OrderStatus.CANCEL.name().equals(order.getStatus())) {
			resp = new TradeCancelResponse(ResponseResult.ORDER_REVERSED);
			resp.setTradeState(OrderStatus.CLOSED.name());
			return resp;
		} 
		ChannelRequestInfo channelRequestInfo = new ChannelRequestInfo();
		String tradeDate = order.getTradeDate();// yyyyMMdd
		String tradeTime = order.getTradeTime();// HHmmss
		Date tradeEndTime = null;
		if (StringUtils.isNotEmpty(tradeDate)) {
			tradeEndTime = DateUtil.parseCompactDateTime(tradeDate + tradeTime);
		} else {
			tradeEndTime = order.getCreateTime();
		}
		Date after5MinuteTime = DateUtil.afterMinute(tradeEndTime, 5);
		if (DateUtil.now().getTime() - after5MinuteTime.getTime() > 0) {
			return new TradeCancelResponse(ResponseResult.CANCEL_TIME_OUT);
		}
		// 记录订单撤销操作order_oper，调用第三方撤销接口
		OrderOper orderOper = orderOperService.saveOrderOper(order, OrderOperType.CANCEL.name(), null, null, null);
		channelRequestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
		channelRequestInfo.setOutTradeNo(order.getTradeNo());
		channelRequestInfo.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
		ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory.produce(order.getTradeType(), preTradeOrderInfo.getBankCode())
				.cancel(channelRequestInfo);	
		if (channelReponseInfo == null) {
			logger.info("无返回信息");
			orderOper.setStatus(OrderOperStatus.NON_RESP.name()).setUpdateTime(DateUtil.now());
			orderOperService.updateOrderOper(orderOper);
			resp = new TradeCancelResponse(ResponseResult.NON_RESP);
			resp.setTradeState(OrderStatus.SUCCESS.name());
			return resp;
		}
		apiLogService.saveApiLog(order.getId(), OrderOperType.CANCEL, JSON.toJSONString(channelReponseInfo));
		return orderOperService.updateUnifiedCancel(order, orderOper, channelReponseInfo);
	}

	@Override
	public TradeRefundResponse unifiedRefund(PreTradeOrderInfo preTradeOrderInfo) {
		TradeRefundResponse resp = new TradeRefundResponse(ResponseResult.OK);
		String orderNo = preTradeOrderInfo.getOrigOrderNo();// 商户支付订单号
		String tradeNo = preTradeOrderInfo.getTradeNo();// 系统订单号
		String outRefundNo = preTradeOrderInfo.getOutTradeNo();// 商户退款订单号
		String culCommNo = preTradeOrderInfo.getCommUnionNo();
		Integer refundAmount = preTradeOrderInfo.getTotalAmount();// 退款金额：单位：分
		logger.info("orderNo:" + orderNo + ",tradeNo:" + tradeNo + ",outRefundNo:" + outRefundNo + ",culCommNo:"
				+ culCommNo);
		TradeOrder tradeOrder = null;
		if (StringUtils.isNotEmpty(tradeNo)) {
			tradeOrder = orderOperService.findByTradeNoAndOrderNoAndCulCommNoAndType(tradeNo, orderNo, culCommNo,
					OrderType.PAY.name());
		} else {
			tradeOrder = orderOperService.findByOrderNoAndCulCommNoAndType(orderNo, culCommNo, OrderType.PAY.name());
		}
		if (tradeOrder == null) {
			logger.info("未查询到订单信息");
			resp.setCode(ResponseResult.ORDER_NOT_EXISTS.getCode());
			resp.setMsg(ResponseResult.ORDER_NOT_EXISTS.getMsg());
			return resp;
		}
		// 不允许退款的
		if (OrderStatus.FAILED.name().equals(tradeOrder.getStatus())) {
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		if (OrderStatus.CLOSED.name().equals(tradeOrder.getStatus())) {
			resp.setCode(ResponseResult.ORDER_CLOSED.getCode());
			resp.setMsg(ResponseResult.ORDER_CLOSED.getMsg());
			resp.setTradeState(OrderStatus.CLOSED.name());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(tradeOrder.getStatus())) {
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			resp.setTradeState(OrderStatus.CANCEL.name());
			return resp;
		}
		if (OrderStatus.INIT.name().equals(tradeOrder.getStatus())
				|| OrderStatus.WAITING.name().equals(tradeOrder.getStatus())) {
			// 发起退款,如果退款成功，更新原来的消费订单为success，在备注字段中标明。消费流水号？
			// TODO 暂时不允许退款，以后等待业务确定，可以加上时间的限制
			PreTradeOrderInfo query = new PreTradeOrderInfo();
			query.setBankMerNo(preTradeOrderInfo.getBankMerNo());
			query.setBankCode(preTradeOrderInfo.getBankCode());
			query.setChannelId(preTradeOrderInfo.getChannelId());
			query.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
			query.setCommUnionNo(preTradeOrderInfo.getCommUnionNo());
			query.setOutTradeNo(preTradeOrderInfo.getOutTradeNo());
			query.setTradeNo(preTradeOrderInfo.getTradeNo());
			TradeQueryResponse queryResponse = this.unifiedPayQuery(preTradeOrderInfo);
			if (queryResponse == null) {
				logger.info("查询原交易成功,调用退货接口");
				resp = new TradeRefundResponse(ResponseResult.WAIT_PAYING);
				resp.setTradeState(OrderStatus.WAITING.name());
				return resp;
			} else if (!Constants.OK.equals(queryResponse.getCode())) {
				logger.info("查询原交易接口调用成功,调用退货接口");
				resp = new TradeRefundResponse(ResponseResult.WAIT_PAYING);
				resp.setTradeState(OrderStatus.WAITING.name());
				return resp;
			} else if (OrderStatus.WAITING.name().equals(queryResponse.getTradeState())) {
				resp = new TradeRefundResponse(ResponseResult.WAIT_PAYING);
				resp.setTradeState(OrderStatus.WAITING.name());
				return resp;
			} else if (OrderStatus.SUCCESS.name().equals(queryResponse.getTradeState())) {
				logger.info("调用退货接口");
			} else {
				resp = new TradeRefundResponse(ResponseResult.ORDER_FIAL);
				resp.setMsg("原订单支付失败或已撤销，不允许退货");
				resp.setTradeState(queryResponse.getTradeState());
				return resp;
			}
		}

		// 退款单号是否重复
		TradeOrder refundTradeOrder = orderOperService.findByOrderNoAndCulCommNoAndType(outRefundNo, culCommNo, null);
		if (refundTradeOrder != null) {
			logger.info("退款单号重复");
			resp.setCode(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getCode());
			resp.setMsg(ResponseResult.OUT_TRADE_NO_IS_EXISTS.getMsg());
			return resp;
		}
		// 原订单成功
		// 查询成功的退款记录检查退款金额,判断此次退款金额是否在可退范围之内
		Integer retiredPoint = 0;
		List<String> refundOrderStatus = Arrays.asList(new String[]{OrderStatus.SUCCESS.name(), OrderStatus.WAITING.name()});
		List<TradeOrder> refundOrders = orderOperService.findByOrigOrderNoAndCulCommNoAndStatus(tradeOrder.getOrderNo(),
				culCommNo, refundOrderStatus);
		if (refundOrders != null && !refundOrders.isEmpty()) {
			for (TradeOrder refundOrder : refundOrders) {
				if (OrderStatus.SUCCESS.name().equals(refundOrder.getStatus())) {
					logger.info("{}退货成功,退货金额{}", refundOrder.getTradeNo(), refundOrder.getPayPoint());
				} else if (OrderStatus.WAITING.name().equals(refundOrder.getStatus())) {
					logger.info("{}退货等待中,退货金额{}", refundOrder.getTradeNo(), refundOrder.getPayPoint());
				}
				retiredPoint = retiredPoint + refundOrder.getOrderPoint();
			}
		}
		logger.info("已经退款的金额(分),retiredPoint:" + retiredPoint);
		// 已退金额 + 本次金额 > 订单金额
		if (null != retiredPoint && (retiredPoint + refundAmount) > tradeOrder.getOrderPoint()) {
			resp.setCode(ResponseResult.REFUND_AMOUNT_OUT_RANGE.getCode());
			resp.setMsg(ResponseResult.REFUND_AMOUNT_OUT_RANGE.getMsg());
			return resp;
		}
		// 记录退款订单和操作
		preTradeOrderInfo.setTradeType(tradeOrder.getTradeType());
		TradeOrder refundOrder = unifiedCreateTradeOrder(preTradeOrderInfo);
		OrderOper refundOrderOper = orderOperService.saveOrderOper(refundOrder, OrderOperType.REFUND.name(), null, null,
				null);
		// 更新订单和订单操作
		Date now = DateUtil.now();
		refundOrder.setStatus(OrderStatus.WAITING.name()).setUpdateTime(now);
		orderOperService.updateTradeOrder(refundOrder);
		refundOrderOper.setStatus(OrderOperStatus.COMMIT.name()).setUpdateTime(now);
		orderOperService.updateOrderOper(refundOrderOper);
		// 调用第三方退款
		ChannelRequestInfo channelRequestInfo = new ChannelRequestInfo();
		channelRequestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
		channelRequestInfo.setOutRefundNo(refundOrder.getTradeNo());
		channelRequestInfo.setOutTradeNo(tradeOrder.getTradeNo());
		channelRequestInfo.setOpUserId(preTradeOrderInfo.getOperUser());
		channelRequestInfo.setRefundChannel(preTradeOrderInfo.getRefundChannel());
		channelRequestInfo.setTotalFee(tradeOrder.getOrderPoint().toString());
		channelRequestInfo.setRefundFee(preTradeOrderInfo.getTotalAmount().toString());
		channelRequestInfo.setTransactionId(tradeOrder.getChannelTxnNo());
		channelRequestInfo.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
		ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory
				.produce(tradeOrder.getTradeType(), preTradeOrderInfo.getBankCode()).refund(channelRequestInfo);

		Date updateTime = DateUtil.now();
		// 更新操作和订单
		if (channelReponseInfo == null) {
			logger.info("无返回信息");
			resp.setCode(ResponseResult.NON_RESP.getCode());
			resp.setMsg(ResponseResult.NON_RESP.getMsg());
			refundOrderOper.setStatus(OrderOperStatus.NON_RESP.name()).setUpdateTime(updateTime);
			orderOperService.updateOrderOper(refundOrderOper);
			return resp;
		}
		apiLogService.saveApiLog(refundOrder.getId(), OrderOperType.REFUND, JSON.toJSONString(channelReponseInfo));
		return orderOperService.updateUnifiedRefund(refundOrder, refundOrderOper, channelReponseInfo);
	}

	@Override
	public RefundQueryResponse tradeRefundQuery(PreTradeOrderInfo preTradeOrderInfo) {
		logger.info("查询退款订单信息");
		RefundQueryResponse resp = new RefundQueryResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());		// 查询订单信息
		String outRefundNo = preTradeOrderInfo.getOutTradeNo();// 商户退款订单号
		String refundTradeNo = preTradeOrderInfo.getTradeNo();// 系统退款订单号
		String culCommNo = preTradeOrderInfo.getCommUnionNo();
		logger.info("outRefundNo:" + outRefundNo + ",refundTradeNo:" + refundTradeNo + ",culCommNo:" + culCommNo);
		TradeOrder tradeOrder = null;
		if (StringUtils.isNotEmpty(refundTradeNo)) {
			tradeOrder = orderOperService.findByTradeNoAndOrderNoAndCulCommNoAndType(refundTradeNo, outRefundNo, culCommNo,
					OrderType.REFUND.name());
		} else {
			tradeOrder = orderOperService.findByOrderNoAndCulCommNoAndType(outRefundNo, culCommNo, OrderType.REFUND.name());
		}
		if (tradeOrder == null) {
			logger.info("未查询到退款订单信息");
			resp.setCode(ResponseResult.ORDER_NOT_EXISTS.getCode());
			resp.setMsg(ResponseResult.ORDER_NOT_EXISTS.getMsg());
			return resp;
		}
		if (OrderStatus.SUCCESS.name().equals(tradeOrder.getStatus())) {
			// 退款成功
			resp.setMchId(tradeOrder.getCulCommNo())
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
		if (OrderStatus.FAILED.name().equals(tradeOrder.getStatus())) {
			// 退款失败
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		// 等待中调用第三方接口
		if (OrderStatus.WAITING.name().equals(tradeOrder.getStatus())) {
			ChannelRequestInfo channelRequestInfo = new ChannelRequestInfo();
			channelRequestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
			channelRequestInfo.setOutRefundNo(tradeOrder.getTradeNo());
			channelRequestInfo.setRefundId(tradeOrder.getChannelTxnNo());
			channelRequestInfo.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
			ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory.produce(tradeOrder.getTradeType(), preTradeOrderInfo.getBankCode())
					.refundQuery(channelRequestInfo);
			// 第三方返回成功、失败，更新订单，返回信息
			if (channelReponseInfo == null) {
				logger.info("无返回信息");
				resp.setCode(ResponseResult.NON_RESP.getCode());
				resp.setMsg(ResponseResult.NON_RESP.getMsg());
				resp.setTradeState(OrderStatus.WAITING.name());
				return resp;
			}
			apiLogService.saveApiLog(tradeOrder.getId(), OrderOperType.CANCEL, JSON.toJSONString(channelReponseInfo));
			return orderOperService.updateUnifiedRefundQuery(tradeOrder, channelReponseInfo);
		}
		return resp;
	}

	@Override
	public TradecloseResponse unifiedClose(PreTradeOrderInfo preTradeOrderInfo) {
		logger.info("enter close order.");
		TradecloseResponse resp = new TradecloseResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		String orderNo = preTradeOrderInfo.getOutTradeNo();// 商户订单号
		String culCommNo = preTradeOrderInfo.getCommUnionNo();
		logger.info("orderNo:" + orderNo + ",culCommNo:" + culCommNo);
		// 交易类型为支付
		TradeOrder order = orderOperService.findByOrderNoAndCulCommNoAndType(orderNo, culCommNo, OrderType.PAY.name());
		if (order == null) {
			resp.setCode(ResponseResult.ORDER_NOT_EXISTS.getCode());
			resp.setMsg(ResponseResult.ORDER_NOT_EXISTS.getMsg());
			return resp;
		}

		// 线下交易不支持关单操作
		if (TradeType.ALI_DIRECT.name().equals(order.getTradeType())
				|| TradeType.WX_MICROPAY.name().equals(order.getTradeType())) {
			resp.setCode(ResponseResult.UNSUPPORTED_SERVICE.getCode());
			resp.setMsg(ResponseResult.UNSUPPORTED_SERVICE.getMsg());
			return resp;
		}

		if (OrderStatus.SUCCESS.name().equals(order.getStatus())) {
			resp.setCode(ResponseResult.ORDER_HAVA_PAID.getCode());
			resp.setMsg(ResponseResult.ORDER_HAVA_PAID.getMsg());
			resp.setTradeState(OrderStatus.SUCCESS.name());
			return resp;
		}
		if (OrderStatus.FAILED.name().equals(order.getStatus())) {
			resp.setCode(ResponseResult.TRADE_FAILED.getCode());
			resp.setMsg(ResponseResult.TRADE_FAILED.getMsg());
			resp.setTradeState(OrderStatus.FAILED.name());
			return resp;
		}
		if (OrderStatus.CLOSED.name().equals(order.getStatus())) {
			resp.setCode(ResponseResult.ORDER_CLOSED.getCode());
			resp.setMsg(ResponseResult.ORDER_CLOSED.getMsg());
			resp.setTradeState(OrderStatus.CLOSED.name());
			return resp;
		}
		if (OrderStatus.CANCEL.name().equals(order.getStatus())) {
			resp.setCode(ResponseResult.ORDER_REVERSED.getCode());
			resp.setMsg(ResponseResult.ORDER_REVERSED.getMsg());
			resp.setTradeState(OrderStatus.CANCEL.name());
			return resp;
		}
		// 等待支付的订单才允许发起关闭操作
		// 最短调用时间间隔为5分钟
		Date tradeCreateTime = order.getCreateTime();
		Date after5MinuteTime = DateUtil.afterMinute(tradeCreateTime, 5);
		if (DateUtil.now().getTime() - after5MinuteTime.getTime() < 0) {
			logger.info("订单创建时间在5分钟内的不允许关闭.");
			resp.setCode(ResponseResult.ORDER_NOT_ALLOWED_CLOSE.getCode());
			resp.setMsg(ResponseResult.ORDER_NOT_ALLOWED_CLOSE.getMsg());
			resp.setTradeState(OrderStatus.WAITING.name());
			return resp;
		}
		// 记录订单关闭操作order_oper，调用第三方接口
		OrderOper orderOper = orderOperService.saveOrderOper(order, OrderOperType.CLOSE.name(), null, null, null);
		orderOper.setStatus(OrderOperStatus.COMMIT.name()).setUpdateTime(DateUtil.now());
		orderOperService.updateOrderOper(orderOper);

		ChannelRequestInfo channelRequestInfo = new ChannelRequestInfo();
		channelRequestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
		channelRequestInfo.setOutTradeNo(order.getTradeNo());
		channelRequestInfo.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
		ChannelResponseInfo channelReponseInfo = ThirdChannelTradeFactory.produce(order.getTradeType(), preTradeOrderInfo.getBankCode())
				.close(channelRequestInfo);

		if (channelReponseInfo == null) {
			logger.info("无返回信息");
			resp.setCode(ResponseResult.NON_RESP.getCode());
			resp.setMsg(ResponseResult.NON_RESP.getMsg());
			resp.setTradeState(OrderStatus.WAITING.name());
			orderOper.setStatus(OrderOperStatus.NON_RESP.name()).setUpdateTime(DateUtil.now());
			orderOperService.updateOrderOper(orderOper);
			return resp;
		}
		apiLogService.saveApiLog(order.getId(), OrderOperType.CANCEL, JSON.toJSONString(channelReponseInfo));
		return orderOperService.updateUnifiedClose(order, orderOper, channelReponseInfo);
	}
}
