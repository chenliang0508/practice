package cn.com.upcard.mgateway.model;

import java.util.Date;

import cn.com.upcard.mgateway.channel.bank.mybank.common.ChannelType;
import cn.com.upcard.mgateway.util.systemparam.NotifyUrlInit;

public class ChannelRequestInfo {
	/**
	 * 调用通道接口的参数转换
	 * 
	 * 
	 */
	public ChannelRequestInfo() {

	}

	private String mchId; // 商户号
	private String outTradeNo; // 系统生成的订单号
	private String deviceInfo; // 设备号

	private String body; // 商品描述
	private String extendInfo; // 附加信息
	private String totalFee; // 总金额
	private String reqIp; // 终端ip
	private String notifyUrl; // 通知地址
	private String timeStart; // 订单生成时间
	private String timeExpire; // 订单超时时间
	private String expireExpress; // 订单有效期，单位分
	private String opUserId; // 操作员
	private String goodTags; // 商品标记
	private String productId; // 商品id
	private String limitCreditPay; // 是否限制使用信用卡
	private String payLimit; // 禁用的支付方式
	private String privateKey;
	private String authCode;//条码号

	private String transactionId; // 平台订单号，指支付中第三方返回的订单号
	private String outRefundNo; // 退款订单号
	private String refundFee; // 退款金额
	private String refundChannel; // 退款渠道

	private String refundId; // 第三方平台退款单号

	private String isRaw; // 是否原生，用于微信js
	private String isMinipg; // 是否小程序支付
	private String subOpenid; // 用户的openid
	private String subAppid; // 公众账号或小程序ID，在微信app支付时代表商户APP应用ID

	private String callbackUrl; // 前台地址，交易完成后跳转的URL，需给绝对路径

	private String signAgentno; // 授权渠道编号

	private String goodsDetail;//单品信息
	private String opShopId;//门店编号
	private String opDeviceId;//设备编号
	private String channelType;//支付渠道类型,微信， 支付宝，QQ
	private String payType; // 支付渠道 NATIVE, JSAPI, APP

	private String appId; // 商户app对应的微信开发平台移动应用APPID
	private String groupno; // 大客户编号
	private String thirdBuyerId;//第三方购买人id
	private String thirdBuyerAccount;//第三方购买人账户
	private String commPrivateKey;
	private Date tradeEndTime;//原交易订单时间，用于兴业银行判断撤销接口的时间
	

	public String getMchId() {
		return mchId;
	}

	public static ChannelRequestInfo getWxNativePayInstance(PreTradeOrderInfo preTradeOrderInfo) {
		ChannelRequestInfo requestInfo = new ChannelRequestInfo();
		requestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
		requestInfo.setOutTradeNo(preTradeOrderInfo.getTradeNo());
		requestInfo.setDeviceInfo(preTradeOrderInfo.getDeviceInfo());
		requestInfo.setBody(preTradeOrderInfo.getBody());
		requestInfo.setExtendInfo(preTradeOrderInfo.getExtendInfo());
		requestInfo.setTotalFee(String.valueOf(preTradeOrderInfo.getTotalAmount()));
		requestInfo.setReqIp(preTradeOrderInfo.getReqIp());
		requestInfo.setNotifyUrl(NotifyUrlInit.createNotifyUrl(preTradeOrderInfo.getBankCode()));
		requestInfo.setLimitCreditPay(preTradeOrderInfo.getPayTypeLimit()); // TODO 需要进行修改
		requestInfo.setTimeStart(preTradeOrderInfo.getTimeStart());
		requestInfo.setTimeExpire(preTradeOrderInfo.getTimeExpire());
		requestInfo.setOpUserId(preTradeOrderInfo.getOperUser());
		requestInfo.setGoodsTag(preTradeOrderInfo.getGoodTags());
		requestInfo.setProductId(preTradeOrderInfo.getGoodsDetail());
		requestInfo.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
		return requestInfo;
	}

	public static ChannelRequestInfo getWxJsPayInstance(PreTradeOrderInfo preTradeOrderInfo) {
		ChannelRequestInfo requestInfo = new ChannelRequestInfo();
		requestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
		requestInfo.setIsRaw(preTradeOrderInfo.getIsRaw());
		requestInfo.setIsMinipg(preTradeOrderInfo.getIsMinipg());
		requestInfo.setOutTradeNo(preTradeOrderInfo.getTradeNo());
		requestInfo.setDeviceInfo(preTradeOrderInfo.getDeviceInfo());
		requestInfo.setBody(preTradeOrderInfo.getBody());
		requestInfo.setSubOpenid(preTradeOrderInfo.getThirdBuyerId());
		requestInfo.setSubAppid(preTradeOrderInfo.getSubAppid());
		requestInfo.setExtendInfo(preTradeOrderInfo.getExtendInfo());
		requestInfo.setTotalFee(String.valueOf(preTradeOrderInfo.getTotalAmount()));
		requestInfo.setReqIp(preTradeOrderInfo.getReqIp());
		requestInfo.setNotifyUrl(NotifyUrlInit.createNotifyUrl(preTradeOrderInfo.getBankCode()));
		requestInfo.setCallbackUrl(preTradeOrderInfo.getReturnUrl());
		requestInfo.setTimeStart(preTradeOrderInfo.getTimeStart());
		requestInfo.setTimeExpire(preTradeOrderInfo.getTimeExpire());
		requestInfo.setGoodsTag(preTradeOrderInfo.getGoodTags());
		requestInfo.setLimitCreditPay(preTradeOrderInfo.getPayTypeLimit()); // TODO 进行转换
		requestInfo.setCallbackUrl(preTradeOrderInfo.getReturnUrl());
		requestInfo.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
		return requestInfo;
	}

	public static ChannelRequestInfo getMicropayReqInstance(PreTradeOrderInfo preTradeOrderInfo) {
		ChannelRequestInfo channelRequestInfo = new ChannelRequestInfo();
		channelRequestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
		channelRequestInfo.setDeviceInfo(preTradeOrderInfo.getDeviceInfo());
		channelRequestInfo.setBody(preTradeOrderInfo.getBody());
		channelRequestInfo.setExtendInfo(preTradeOrderInfo.getExtendInfo());
		channelRequestInfo.setGoodsDetail(preTradeOrderInfo.getGoodsDetail());
		channelRequestInfo.setTotalFee(String.valueOf(preTradeOrderInfo.getTotalAmount()));
		channelRequestInfo.setReqIp(preTradeOrderInfo.getReqIp());
		channelRequestInfo.setAuthCode(preTradeOrderInfo.getAuthCode());
		channelRequestInfo.setTimeStart(preTradeOrderInfo.getTimeStart());
		channelRequestInfo.setTimeExpire(preTradeOrderInfo.getTimeExpire());
		channelRequestInfo.setOpUserId(preTradeOrderInfo.getOperUser());
		channelRequestInfo.setOpShopId(preTradeOrderInfo.getMerchantNo());
//		channelRequestInfo.setOpDeviceId(preTradeOrderInfo.getOpDeviceId());
		channelRequestInfo.setGoodsTag(preTradeOrderInfo.getGoodTags());
		channelRequestInfo.setChannelType(ChannelType.getChannelType(preTradeOrderInfo.getTradeType()));
		channelRequestInfo.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
		return channelRequestInfo;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}


	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

//	public String getAttach() {
//		return attach;
//	}
//
//	public void setAttach(String attach) {
//		this.attach = attach;
//	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(String opUserId) {
		this.opUserId = opUserId;
	}

	public String getGoodsTag() {
		return goodTags;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodTags = goodsTag;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLimitCreditPay() {
		return limitCreditPay;
	}

	public void setLimitCreditPay(String limitCreditPay) {
		this.limitCreditPay = limitCreditPay;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	public String getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
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

	public String getSubOpenid() {
		return subOpenid;
	}

	public void setSubOpenid(String subOpenid) {
		this.subOpenid = subOpenid;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getSignAgentno() {
		return signAgentno;
	}

	public void setSignAgentno(String signAgentno) {
		this.signAgentno = signAgentno;
	}

//	public String getTradeNo() {
//		return tradeNo;
//	}
//
//	public void setTradeNo(String tradeNo) {
//		this.tradeNo = tradeNo;
//	}
//
//	public String getAutoCode() {
//		return autoCode;
//	}
//
//	public void setAutoCode(String autoCode) {
//		this.autoCode = autoCode;
//	}

	public String getOpShopId() {
		return opShopId;
	}

	public void setOpShopId(String opShopId) {
		this.opShopId = opShopId;
	}

	public String getOpDeviceId() {
		return opDeviceId;
	}

	public void setOpDeviceId(String opDeviceId) {
		this.opDeviceId = opDeviceId;
	}

	public String getPayLimit() {
		return payLimit;
	}

	public void setPayLimit(String payLimit) {
		this.payLimit = payLimit;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public static ChannelRequestInfo getAliNativePayInstance(PreTradeOrderInfo preTradeOrderInfo) {
		ChannelRequestInfo channelRequestInfo = new ChannelRequestInfo();
		channelRequestInfo.setMchId(preTradeOrderInfo.getBankMerNo());
		channelRequestInfo.setOutTradeNo(preTradeOrderInfo.getTradeNo());
		channelRequestInfo.setDeviceInfo(preTradeOrderInfo.getDeviceInfo());
		channelRequestInfo.setBody(preTradeOrderInfo.getBody());
		channelRequestInfo.setExtendInfo(preTradeOrderInfo.getExtendInfo());
		channelRequestInfo.setTotalFee(String.valueOf(preTradeOrderInfo.getTotalAmount()));
		channelRequestInfo.setReqIp(preTradeOrderInfo.getReqIp());
		channelRequestInfo.setNotifyUrl(NotifyUrlInit.createNotifyUrl(preTradeOrderInfo.getBankCode()));
		channelRequestInfo.setLimitCreditPay(preTradeOrderInfo.getDisCreditCard());
		channelRequestInfo.setTimeStart(preTradeOrderInfo.getTimeStart());
		channelRequestInfo.setTimeExpire(preTradeOrderInfo.getTimeExpire());
		channelRequestInfo.setOpUserId(preTradeOrderInfo.getOperUser());
		channelRequestInfo.setGoodsTag(preTradeOrderInfo.getGoodTags());
		channelRequestInfo.setProductId(preTradeOrderInfo.getGoodsDetail());
		channelRequestInfo.setCallbackUrl(preTradeOrderInfo.getReturnUrl());
		channelRequestInfo.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
		return channelRequestInfo;
	}

	public static ChannelRequestInfo getAliJsapiInstance(PreTradeOrderInfo preTradeOrderInfo) {
		ChannelRequestInfo info = new ChannelRequestInfo();
		info.setMchId(preTradeOrderInfo.getBankMerNo());
		info.setOutTradeNo(preTradeOrderInfo.getTradeNo());
		info.setDeviceInfo(preTradeOrderInfo.getDeviceInfo());
		info.setBody(preTradeOrderInfo.getBody());
		info.setExtendInfo(preTradeOrderInfo.getExtendInfo());
		info.setTotalFee(String.valueOf(preTradeOrderInfo.getTotalAmount()));
		info.setReqIp(preTradeOrderInfo.getReqIp());
		info.setNotifyUrl(NotifyUrlInit.createNotifyUrl(preTradeOrderInfo.getBankCode()));
		info.setLimitCreditPay(preTradeOrderInfo.getPayTypeLimit());
		info.setTimeStart(preTradeOrderInfo.getTimeStart());
		info.setTimeExpire(preTradeOrderInfo.getTimeExpire());
		info.setOpUserId(preTradeOrderInfo.getOperUser() == null ? preTradeOrderInfo.getCommUnionNo() : preTradeOrderInfo.getOperUser());
		info.setGoodsTag(preTradeOrderInfo.getGoodTags());
		info.setProductId(preTradeOrderInfo.getGoodsDetail());
		info.setThirdBuyerId(preTradeOrderInfo.getThirdBuyerId());
		info.setThirdBuyerAccount(preTradeOrderInfo.getThirdBuyerAccount());
		info.setCallbackUrl(preTradeOrderInfo.getReturnUrl());
		info.setCommPrivateKey(preTradeOrderInfo.getCommPrivateKey());
		return info;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getGroupno() {
		return groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

	public String getGoodTags() {
		return goodTags;
	}

	public void setGoodTags(String goodTags) {
		this.goodTags = goodTags;
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
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getExpireExpress() {
		return expireExpress;
	}

	public void setExpireExpress(String expireExpress) {
		this.expireExpress = expireExpress;
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

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	

	

	public Date getTradeEndTime() {
		return tradeEndTime;
	}

	public void setTradeEndTime(Date tradeEndTime) {
		this.tradeEndTime = tradeEndTime;
	}

	@Override
	public String toString() {
		return "ChannelRequestInfo [mchId=" + mchId + ", outTradeNo=" + outTradeNo + ", deviceInfo=" + deviceInfo
				+ ", body=" + body + ", extendInfo=" + extendInfo + ", totalFee=" + totalFee + ", reqIp=" + reqIp
				+ ", notifyUrl=" + notifyUrl + ", timeStart=" + timeStart + ", timeExpire=" + timeExpire
				+ ", expireExpress=" + expireExpress + ", opUserId=" + opUserId + ", goodTags=" + goodTags
				+ ", productId=" + productId + ", limitCreditPay=" + limitCreditPay + ", payLimit=" + payLimit
				+ ", privateKey=" + privateKey + ", authCode=" + authCode + ", transactionId=" + transactionId
				+ ", outRefundNo=" + outRefundNo + ", refundFee=" + refundFee + ", refundChannel=" + refundChannel
				+ ", refundId=" + refundId + ", isRaw=" + isRaw + ", isMinipg=" + isMinipg + ", subOpenid=" + subOpenid
				+ ", subAppid=" + subAppid + ", callbackUrl=" + callbackUrl + ", signAgentno=" + signAgentno
				+ ", goodsDetail=" + goodsDetail + ", opShopId=" + opShopId + ", opDeviceId=" + opDeviceId
				+ ", channelType=" + channelType + ", payType=" + payType + ", appId=" + appId + ", groupno=" + groupno
				+ ", thirdBuyerId=" + thirdBuyerId + ", thirdBuyerAccount=" + thirdBuyerAccount + "]";
	}
}
