package cn.com.upcard.mgateway.async.notify.model;

public class CallbackRequestInfo {

	private String payResult;
	private String errMsg;

	private String tradeType;
	private String deviceInfo;
	private String attach;
	private String timeEnd;

	private String outTradeNo;
	private String transactionId;
	private String outTransactionId;

	private Integer totalFee;
	private Integer discountFee;
	private Integer payFee;
	private String feeType;

	private String subAppid;
	private String subOpenid;
	private String subIsSubscribe;
	private String aliOpenid;

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
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

	public Integer getPayFee() {
		return payFee;
	}

	public void setPayFee(Integer payFee) {
		this.payFee = payFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getAliOpenid() {
		return aliOpenid;
	}

	public void setAliOpenid(String aliOpenid) {
		this.aliOpenid = aliOpenid;
	}

	public String getSubOpenid() {
		return subOpenid;
	}

	public void setSubOpenid(String subOpenid) {
		this.subOpenid = subOpenid;
	}

	public String getSubIsSubscribe() {
		return subIsSubscribe;
	}

	public void setSubIsSubscribe(String subIsSubscribe) {
		this.subIsSubscribe = subIsSubscribe;
	}

	@Override
	public String toString() {
		return "CallbackRequestInfo [payResult=" + payResult + ", errMsg=" + errMsg + ", tradeType=" + tradeType
				+ ", deviceInfo=" + deviceInfo + ", attach=" + attach + ", timeEnd=" + timeEnd + ", outTradeNo="
				+ outTradeNo + ", transactionId=" + transactionId + ", outTransactionId=" + outTransactionId
				+ ", totalFee=" + totalFee + ", discountFee=" + discountFee + ", payFee=" + payFee + ", feeType="
				+ feeType + ", subAppid=" + subAppid + ", subOpenid=" + subOpenid + ", subIsSubscribe=" + subIsSubscribe
				+ ", aliOpenid=" + aliOpenid + "]";
	}

}
