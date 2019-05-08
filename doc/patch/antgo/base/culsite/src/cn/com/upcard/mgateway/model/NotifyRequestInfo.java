package cn.com.upcard.mgateway.model;

public class NotifyRequestInfo {

	private String mchId; // 商户号
	private String deviceInfo; // 设备号
	private String openid; // 用户在服务商下的openid
	private String tradeType; // 交易类型
	private String isSubscribe; // 用户是否关注服务商公众账号
	private String payResult; // 支付结果
	private String payInfo; // 支付结果信息（微信js相关）
	private String transactionId; // 银商订单号
	private String outTransactionId; // 微信订单号
	private String subIsSubscribe; // 是否关注商户公众号
	private String subAppid; // 商户appid
	private String subOpenid; // 用户openid
	private String outTradeNo; // 商户订单号
	private String totalFee; // 总金额
	private String couponFee; // 现金券金额
	private String payFee; // 实付金额
	private String discountFee; // 折扣金额
	private String feeType; // 货币种类
	private String attach; // 附加信息
	private String bankType; // 付款银行
	private String bankBillno; // 银行订单号
	private String timeEnd; // 支付完成时间 yyyyMMddHHmmss

	public NotifyRequestInfo() {
		super();
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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOutTransactionId() {
		return outTransactionId;
	}

	public void setOutTransactionId(String outTransactionId) {
		this.outTransactionId = outTransactionId;
	}

	public String getSubIsSubscribe() {
		return subIsSubscribe;
	}

	public void setSubIsSubscribe(String subIsSubscribe) {
		this.subIsSubscribe = subIsSubscribe;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getSubOpenid() {
		return subOpenid;
	}

	public void setSubOpenid(String subOpenid) {
		this.subOpenid = subOpenid;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(String couponFee) {
		this.couponFee = couponFee;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
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

	public String getBankBillno() {
		return bankBillno;
	}

	public void setBankBillno(String bankBillno) {
		this.bankBillno = bankBillno;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Override
	public String toString() {
		return "NotifyRequestInfo [mchId=" + mchId + ", deviceInfo=" + deviceInfo + ", openid=" + openid
				+ ", tradeType=" + tradeType + ", isSubscribe=" + isSubscribe + ", payResult=" + payResult
				+ ", payInfo=" + payInfo + ", transactionId=" + transactionId + ", outTransactionId=" + outTransactionId
				+ ", subIsSubscribe=" + subIsSubscribe + ", subAppid=" + subAppid + ", subOpenid=" + subOpenid
				+ ", outTradeNo=" + outTradeNo + ", totalFee=" + totalFee + ", couponFee=" + couponFee + ", payFee="
				+ payFee + ", discountFee=" + discountFee + ", feeType=" + feeType + ", attach=" + attach
				+ ", bankType=" + bankType + ", bankBillno=" + bankBillno + ", timeEnd=" + timeEnd + "]";
	}

}
