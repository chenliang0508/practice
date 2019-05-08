package cn.com.upcard.mgateway.model;

import java.math.BigDecimal;

import cn.com.upcard.mgateway.common.enums.OrderType;
import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.controller.request.AlipayJsapiParam;
import cn.com.upcard.mgateway.controller.request.AlipayNativePayParam;
import cn.com.upcard.mgateway.controller.request.MicroPayRequest;
import cn.com.upcard.mgateway.controller.request.QrCodeOrderPayParam;
import cn.com.upcard.mgateway.controller.request.TradeCancelParam;
import cn.com.upcard.mgateway.controller.request.TradeCloseParam;
import cn.com.upcard.mgateway.controller.request.UnifiedRefundParam;
import cn.com.upcard.mgateway.controller.request.UnifiedRefundQueryParam;
import cn.com.upcard.mgateway.controller.request.UnifiedTradeQueryParam;
import cn.com.upcard.mgateway.controller.request.WxJsPayParam;
import cn.com.upcard.mgateway.controller.request.WxNativePayParam;


/**
 * 作为商户调用的参数转换
 * 
 * @author chenliang
 * 
 */

public class PreTradeOrderInfo {
	private String outTradeNo;// 商户系统的订单编号
	private String commUnionNo; // 系统分配的商户编号
	private String body;// 对订单中商品描述
	private String subject;// 订单标题
	private String timeStart;// 订单有效期开始时间
	private String timeExpire;// 订单有效期结束时间
	private String orderType;// 订单类型： 支付：PAY；退款：REFUND;
	private Integer totalAmount;// 订单金额，分
	private String tradeType;// 交易类型
	private String disCreditCard;// 禁用信用卡
	private String returnUrl;
	private String notifyUrl;// 异步通知URl
	private String merchantNo;
	private String deviceInfo;//设备号
	private String operUser;// 订单操作员
	private String authCode;// 授权码、条码
	private String origOrderNo;// 原交易订单号
	private String goodsDetail;// 单品信息
	private String goodTags;//优惠商品信息
	private String extendInfo;// 附加信息
	private String reqIp;// 终端IP
	private String bankMerNo;//银行分配的商户号
	private String channelId;//通道
	private String bankCode;//通道代码
	private String tradeNo;//支付系统的流水订单号
	private String payTypeLimit;//不支持的支付方式
	private String refundChannel;//退款渠道
	private String isRaw; // 是否原生态，用于微信支付
	private String isMinipg; // 是否小程序支付
	private String subAppid; // 公众账号或小程序ID
	private String thirdBuyerId;//第三方购买人id
	private String thirdBuyerAccount;//第三方购买人账户号
	private String commPrivateKey;
	public PreTradeOrderInfo() {
	}

	public String getPayTypeLimit() {
		return payTypeLimit;
	}

	public PreTradeOrderInfo setPayTypeLimit(String payTypeLimit) {
		this.payTypeLimit = payTypeLimit;
		return this;
	}

	public static PreTradeOrderInfo alipayNativeInstance(AlipayNativePayParam param) {
		PreTradeOrderInfo preTradeOrderInfo = new PreTradeOrderInfo();
		preTradeOrderInfo.setCommUnionNo(param.getMchId()).setOutTradeNo(param.getOutTradeNo())
				.setTradeType(TradeType.ALI_NATIVE.name()).setOrderType(OrderType.PAY.name())
				.setTotalAmount(Integer.valueOf(param.getTotalAmount())).setSubject(param.getSubject())
				.setSubject(param.getSubject()).setBody(param.getBody())
				.setTimeStart(param.getTimeStart()).setTimeExpire(param.getTimeExpire())
				.setNotifyUrl(param.getNotifyUrl()).setOperUser(param.getOperUser())
				.setMerchantNo(param.getMerchantNo()).setDeviceInfo(param.getDeviceInfo())
				.setPayTypeLimit(param.getPayTypeLimit())
				.setExtendInfo(param.getExtendInfo())
				.setGoodTags(param.getGoodTags())
				.setReqIp(param.getReqIp());
		return preTradeOrderInfo;
	}
	
	public static PreTradeOrderInfo alipayJsapiInstance(AlipayJsapiParam param) {
		PreTradeOrderInfo preTradeOrderInfo = new PreTradeOrderInfo();
		preTradeOrderInfo.setCommUnionNo(param.getMchId())
				.setOutTradeNo(param.getOutTradeNo())
				.setTradeType(TradeType.ALI_JSAPI.name())
				.setOrderType(OrderType.PAY.name())
				.setTotalAmount(Integer.valueOf(param.getTotalAmount()))
				.setSubject(param.getSubject())
				.setBody(param.getBody())
				.setTimeStart(param.getTimeStart())
				.setTimeExpire(param.getTimeExpire())
				.setNotifyUrl(param.getNotifyUrl())
				.setOperUser(param.getOperUser())
				.setMerchantNo(param.getMerchantNo())
				.setDeviceInfo(param.getDeviceInfo())
				.setPayTypeLimit(param.getPayTypeLimit());
		preTradeOrderInfo.setThirdBuyerId(param.getBuyerId());
		preTradeOrderInfo.setThirdBuyerAccount(param.getBuyerLoginId());
		preTradeOrderInfo.setExtendInfo(param.getExtendInfo())
				.setGoodTags(param.getGoodTags())
				.setReqIp(param.getReqIp());
		return preTradeOrderInfo;
	}
	
	public static PreTradeOrderInfo qrCodeOrderPayInstance(QrCodeOrderPayParam param, TradeType type) {
		PreTradeOrderInfo preTradeOrderInfo = new PreTradeOrderInfo();
		preTradeOrderInfo.setCommUnionNo(param.getMchId());
		preTradeOrderInfo.setTradeType(type.name());
		preTradeOrderInfo.setOrderType(OrderType.PAY.name());
		int orderPoint = new BigDecimal(param.getTotalAmount()).multiply(new BigDecimal(100)).intValue();
		preTradeOrderInfo.setTotalAmount(orderPoint);
		preTradeOrderInfo.setSubject(param.getSubject());
		preTradeOrderInfo.setBody(param.getBody());
		preTradeOrderInfo.setTimeStart(param.getTimeStart());
		preTradeOrderInfo.setTimeExpire(param.getTimeExpire());
		preTradeOrderInfo.setReturnUrl(param.getReturnUrl());
		preTradeOrderInfo.setNotifyUrl(param.getNotifyUrl());
		preTradeOrderInfo.setOperUser(param.getOperUser());
		preTradeOrderInfo.setMerchantNo(param.getMerchantNo());
		preTradeOrderInfo.setDeviceInfo(param.getDeviceInfo());
		preTradeOrderInfo.setPayTypeLimit(param.getPayTypeLimit());
		preTradeOrderInfo.setDeviceInfo(param.getExtendInfo());
		preTradeOrderInfo.setGoodTags(param.getGoodTags());
		preTradeOrderInfo.setReqIp(param.getReqIp());
		preTradeOrderInfo.setThirdBuyerId(param.getThirdBuyerId());
		return preTradeOrderInfo;
	}
	
	public static PreTradeOrderInfo wxNativeInstance(WxNativePayParam param) {
		PreTradeOrderInfo preTradeOrderInfo = new PreTradeOrderInfo();
		preTradeOrderInfo.setCommUnionNo(param.getMchId()).setOutTradeNo(param.getOutTradeNo())
				.setTradeType(TradeType.WX_NATIVE.name()).setOrderType(OrderType.PAY.name())
				.setTotalAmount(Integer.valueOf(param.getTotalAmount())).setSubject(param.getSubject())
				.setSubject(param.getSubject()).setBody(param.getBody())
				.setTimeStart(param.getTimeStart()).setTimeExpire(param.getTimeExpire())
				.setNotifyUrl(param.getNotifyUrl()).setOperUser(param.getOperUser())
				.setMerchantNo(param.getMerchantNo()).setDeviceInfo(param.getDeviceInfo())
				.setPayTypeLimit(param.getPayTypeLimit())
				.setExtendInfo(param.getExtendInfo())
				.setGoodTags(param.getGoodTags())
				.setReqIp(param.getReqIp());
		return preTradeOrderInfo;
	}

	public static PreTradeOrderInfo wxJsInstance(WxJsPayParam param) {
		PreTradeOrderInfo orderInfo = new PreTradeOrderInfo();

		orderInfo.setCommUnionNo(param.getMchId());
		orderInfo.setTradeType(TradeType.WX_NATIVE.name());
		orderInfo.setOrderType(OrderType.PAY.name());
		orderInfo.setIsRaw(param.getIsRaw());
		orderInfo.setIsMinipg(param.getIsMinipg());
		orderInfo.setOutTradeNo(param.getOutTradeNo());
		orderInfo.setDeviceInfo(param.getDeviceInfo());
		orderInfo.setBody(param.getBody());
		orderInfo.setThirdBuyerId(param.getSubOpenid());;
		orderInfo.setSubAppid(param.getSubAppid());
		orderInfo.setExtendInfo(param.getExtendInfo());
		orderInfo.setTotalAmount(Integer.valueOf(param.getTotalAmount()));
		orderInfo.setReqIp(param.getReqIp());
		orderInfo.setNotifyUrl(param.getNotifyUrl());
		orderInfo.setReturnUrl(param.getReturnUrl());
		orderInfo.setTimeStart(param.getTimeStart());
		orderInfo.setTimeExpire(param.getTimeExpire());
		orderInfo.setGoodTags(param.getGoodTags());
		orderInfo.setPayTypeLimit(param.getPayTypeLimit());
		orderInfo.setMerchantNo(param.getMerchantNo());
		orderInfo.setOperUser(param.getOperUser());

		return orderInfo;
	}

	public static PreTradeOrderInfo micropayInstance(MicroPayRequest microPayRequest) {
		PreTradeOrderInfo bean = new PreTradeOrderInfo();
		bean.setCommUnionNo(microPayRequest.getMchId());
		bean.setOutTradeNo(microPayRequest.getOutTradeNo());
		bean.setDeviceInfo(microPayRequest.getDeviceInfo());
		bean.setBody(microPayRequest.getBody());
		bean.setGoodsDetail(microPayRequest.getGoodsDetail());
		bean.setGoodTags(microPayRequest.getGoodsTag());
		bean.setExtendInfo(microPayRequest.getExtendParam());
		bean.setTotalAmount(Integer.parseInt(microPayRequest.getTotalFee()));
		bean.setAuthCode(microPayRequest.getAuthCode());
		bean.setTimeStart(microPayRequest.getTimeStart());
		bean.setTimeExpire(microPayRequest.getTimeExpire());
		bean.setOperUser(microPayRequest.getOpUser());
		bean.setMerchantNo(microPayRequest.getMerchantNo());
		bean.setReqIp(microPayRequest.getMchCreateIp());
		bean.setPayTypeLimit(microPayRequest.getPayTypeLimit());
		bean.setOrderType(OrderType.PAY.name());
		return bean;
	}
    
	public static PreTradeOrderInfo tradeRefundInstance(UnifiedRefundParam param) {
		PreTradeOrderInfo bean = new PreTradeOrderInfo();
		bean.setCommUnionNo(param.getMchId());
		bean.setOutTradeNo(param.getRefundNo());
		bean.setOrigOrderNo(param.getOutTradeNo());
		bean.setTradeNo(param.getTransactionId());
		bean.setTotalAmount(Integer.parseInt(param.getRefundAmount()));
		bean.setOperUser(param.getOperUser());
		bean.setRefundChannel(param.getRefundChannel());
		bean.setOrderType(OrderType.REFUND.name());
		return bean;
	}
	public static PreTradeOrderInfo tradeQueryInstance(UnifiedTradeQueryParam param) {
		PreTradeOrderInfo bean = new PreTradeOrderInfo();
		bean.setCommUnionNo(param.getMchId());
		bean.setOutTradeNo(param.getOutTradeNo());
		bean.setTradeNo(param.getTransactionId());
		return bean;
	}
	
	public static PreTradeOrderInfo closeInstance(TradeCloseParam param) {
		PreTradeOrderInfo bean = new PreTradeOrderInfo();
		bean.setCommUnionNo(param.getMchId());
		bean.setOutTradeNo(param.getOutTradeNo());
		return bean;
	}
	
	public static PreTradeOrderInfo refundQueryInstance(UnifiedRefundQueryParam param) {
		PreTradeOrderInfo bean = new PreTradeOrderInfo();
		bean.setCommUnionNo(param.getMchId());
		bean.setOutTradeNo(param.getRefundNo());
		bean.setTradeNo(param.getRefundId());
		return bean;
	}
	
	public static PreTradeOrderInfo cancelInstance(TradeCancelParam cancelParam ) {
		PreTradeOrderInfo bean = new PreTradeOrderInfo();
		bean.setCommUnionNo(cancelParam.getMchId());
		bean.setOutTradeNo(cancelParam.getOutTradeNo());
		bean.setTradeNo(cancelParam.getTransactionId());
		return bean;
	}
	
	public String getCommUnionNo() {
		return commUnionNo;
	}

	public PreTradeOrderInfo setCommUnionNo(String commUnionNo) {
		this.commUnionNo = commUnionNo;
		return this;
	}

	public String getOrderType() {
		return orderType;
	}

	public PreTradeOrderInfo setOrderType(String orderType) {
		this.orderType = orderType;
		return this;
	}

	public String getTradeType() {
		return tradeType;
	}

	public PreTradeOrderInfo setTradeType(String tradeType) {
		this.tradeType = tradeType;
		return this;
	}

	public String getDisCreditCard() {
		return disCreditCard;
	}

	public PreTradeOrderInfo setDisCreditCard(String disCreditCard) {
		this.disCreditCard = disCreditCard;
		return this;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public PreTradeOrderInfo setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
		return this;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public PreTradeOrderInfo setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
		return this;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public PreTradeOrderInfo setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
		return this;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public PreTradeOrderInfo setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
		return this;
	}

	public String getOperUser() {
		return operUser;
	}

	public PreTradeOrderInfo setOperUser(String operUser) {
		this.operUser = operUser;
		return this;
	}

	public String getAuthCode() {
		return authCode;
	}

	public PreTradeOrderInfo setAuthCode(String authCode) {
		this.authCode = authCode;
		return this;
	}

	public String getOrigOrderNo() {
		return origOrderNo;
	}

	public PreTradeOrderInfo setOrigOrderNo(String origOrderNo) {
		this.origOrderNo = origOrderNo;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public PreTradeOrderInfo setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public String getBankMerNo() {
		return bankMerNo;
	}

	public void setBankMerNo(String bankMerNo) {
		this.bankMerNo = bankMerNo;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getGoodTags() {
		return goodTags;
	}

	public PreTradeOrderInfo setGoodTags(String goodTags) {
		this.goodTags = goodTags;
		return this;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public String getIsRaw() {
		return isRaw;
	}

	public void setIsRaw(String isRaw) {
		this.isRaw = isRaw;
	}

	public String getIsMinipg() {
		return isMinipg;
	}

	public void setIsMinipg(String isMinipg) {
		this.isMinipg = isMinipg;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getThirdBuyerId() {
		return thirdBuyerId;
	}

	public void setThirdBuyerId(String thirdBuyerId) {
		this.thirdBuyerId = thirdBuyerId;
	}

	public String getThirdBuyerAccount() {
		return thirdBuyerAccount;
	}

	public void setThirdBuyerAccount(String thirdBuyerAccount) {
		this.thirdBuyerAccount = thirdBuyerAccount;
	}

	public String getCommPrivateKey() {
		return commPrivateKey;
	}

	public void setCommPrivateKey(String commPrivateKey) {
		this.commPrivateKey = commPrivateKey;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public PreTradeOrderInfo setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
		return this;
	}

	public String getBody() {
		return body;
	}

	public PreTradeOrderInfo setBody(String body) {
		this.body = body;
		return this;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public PreTradeOrderInfo setTimeStart(String timeStart) {
		this.timeStart = timeStart;
		return this;
	}

	public String getTimeExpire() {
		return timeExpire;
	}

	public PreTradeOrderInfo setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
		return this;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public PreTradeOrderInfo setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
		return this;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public PreTradeOrderInfo setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
		return this;
	}

	public String getReqIp() {
		return reqIp;
	}

	public PreTradeOrderInfo setReqIp(String reqIp) {
		this.reqIp = reqIp;
		return this;
	}
}
