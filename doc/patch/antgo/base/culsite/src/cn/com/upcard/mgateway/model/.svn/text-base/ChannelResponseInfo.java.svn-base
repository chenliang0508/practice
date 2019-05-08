package cn.com.upcard.mgateway.model;

import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;
import cn.com.upcard.mgateway.common.enums.ResponseResult;

/**
 * 通道接口返回的参数转换
 */
public class ChannelResponseInfo {

	private String code;// 00 表示接口调用成功,无异常出现,需要校验tradeStatus;非00标识调用失败，有errormsg
	private String msg;
	private ResponseResult responseResult; 
	// SUCCESS—成功CLOSED—已关闭FAILED失败WAITING等待中CANCEL已撤销
	private String tradeStatus;// 交易状态
	private String thirdPaymentTxnid;// 支付机构流水号；支付宝、微信流水号
	private String channelTxnNo;// 收单机构流水号，交易通道流水号
	private String tradeNo;// 系统订单号
	private String authCodeUrl; // 支付二维码链接
	private String authCodeImgUrl;// 支付二维码图片url
	private Integer totalFee;// 总金额，单位：分
	private Integer discountFee;// 优惠金额，单位：分
	private String tradeEndDate;// 交易完成日期 yyyyMMdd
	private String tradeEndTime;// 交易完成时间，格式为HHmmss
	private String subIsSubscribe;// 用户是否关注子公众账号
	private String appid;// 商户appid
	private String subAppid;// 子商户appid
	private String subOpenId;// 用户在子商户appid下的唯一标识
	private String isSubscribe;// 用户是否关注公众账号
	private String needQuery;// 是否需要查询
	private String payInfo; // 原生态js支付信息或小程序支付信息;支付结果信息
	private String payUrl;// 支付链接 pay_url
	private Integer couponFee; // 现金券金额

	private String refundNo; // 银商退款申请号
	private String refundId;// 平台退款单号
	private Integer refundFee; // 退款金额
	private String refundChannel; // 退款渠道
	private Integer couponRefundFee;// 现金券退款金额
	private Integer discountRefundFee;// 现金券退款金额
	private String refundCount; // 退款笔数
	private String refundReason;//退款理由
	private String refundDate; // 退款完成日期
	private String refundTime; // 退款完成时间

	private String mchId; // 商户号
	private String deviceInfo; // 终端号
	private String tradeType; // 交易类型
	private String feeType; // 币种类型
	private String attach; // 附加信息
	private String bankType; // 银行类型
	private String bankBillNo;// 银行订单号，若为支付宝支付则为空
	private String payResult;// 支付结果
	private String thirdBuyerAccount;// 第三方支付机构购买人
	private String thirdSellerAccount;// 第三方支付机构售卖方
	private String discountInfo;//优惠信息，不只是优惠金额
	private String tokenId; // 用于微信js非原生支付使用
	private String IsvOrgId;//	合作方机构号
	private String orderType;//支付单类型,包含主扫和被扫 
    private String channelType;//支付渠道类型
    private String buyerPayAmount;//用户实付金额
    private String receiptAmount;//实收金额,商户实际入账的金额
    private String settleType;//清算方式
	
	public ChannelResponseInfo() {

	}

	public ChannelResponseInfo(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public ChannelResponseInfo(ChannelResponseResult channelResponseResult, ChannelTradeStatus tradeStatus) {
		this.code = channelResponseResult.getCode();
		this.tradeStatus = tradeStatus.name();
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getThirdPaymentTxnid() {
		return thirdPaymentTxnid;
	}

	public void setThirdPaymentTxnid(String thirdPaymentTxnid) {
		this.thirdPaymentTxnid = thirdPaymentTxnid;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getAuthCodeUrl() {
		return authCodeUrl;
	}

	public void setAuthCodeUrl(String authCodeUrl) {
		this.authCodeUrl = authCodeUrl;
	}

	public String getAuthCodeImgUrl() {
		return authCodeImgUrl;
	}

	public void setAuthCodeImgUrl(String authCodeImgUrl) {
		this.authCodeImgUrl = authCodeImgUrl;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(Integer discountFee) {
		this.discountFee = discountFee;
	}

	public String getTradeEndDate() {
		return tradeEndDate;
	}

	public void setTradeEndDate(String tradeEndDate) {
		this.tradeEndDate = tradeEndDate;
	}

	public String getTradeEndTime() {
		return tradeEndTime;
	}

	public void setTradeEndTime(String tradeEndTime) {
		this.tradeEndTime = tradeEndTime;
	}

	public String getSubIsSubscribe() {
		return subIsSubscribe;
	}

	public void setSubIsSubscribe(String subIsSubscribe) {
		this.subIsSubscribe = subIsSubscribe;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getSubOpenId() {
		return subOpenId;
	}

	public void setSubOpenId(String subOpenId) {
		this.subOpenId = subOpenId;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getNeedQuery() {
		return needQuery;
	}

	public void setNeedQuery(String needQuery) {
		this.needQuery = needQuery;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public Integer getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public Integer getCouponRefundFee() {
		return couponRefundFee;
	}

	public void setCouponRefundFee(Integer couponRefundFee) {
		this.couponRefundFee = couponRefundFee;
	}

	public Integer getDiscountRefundFee() {
		return discountRefundFee;
	}

	public void setDiscountRefundFee(Integer discountRefundFee) {
		this.discountRefundFee = discountRefundFee;
	}

	public String getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(String refundCount) {
		this.refundCount = refundCount;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getMchId() {
		return mchId;
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

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getThirdBuyerAccount() {
		return thirdBuyerAccount;
	}

	public void setThirdBuyerAccount(String thirdBuyerAccount) {
		this.thirdBuyerAccount = thirdBuyerAccount;
	}

	public String getThirdSellerAccount() {
		return thirdSellerAccount;
	}

	public void setThirdSellerAccount(String thirdSellerAccount) {
		this.thirdSellerAccount = thirdSellerAccount;
	}

	public String getChannelTxnNo() {
		return channelTxnNo;
	}

	public void setChannelTxnNo(String channelTxnNo) {
		this.channelTxnNo = channelTxnNo;
	}

	public String getBankBillNo() {
		return bankBillNo;
	}

	public void setBankBillNo(String bankBillNo) {
		this.bankBillNo = bankBillNo;
	}

	public String getDiscountInfo() {
		return discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}
	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getIsvOrgId() {
		return IsvOrgId;
	}

	public void setIsvOrgId(String isvOrgId) {
		IsvOrgId = isvOrgId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		channelType = channelType;
	}

	public String getBuyerPayAmount() {
		return buyerPayAmount;
	}

	public void setBuyerPayAmount(String buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount;
	}

	public String getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public ResponseResult getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(ResponseResult responseResult) {
		this.responseResult = responseResult;
		if (responseResult != null) {
			this.code = responseResult.getCode();
			this.msg = responseResult.getMsg();
		}
	}

}
