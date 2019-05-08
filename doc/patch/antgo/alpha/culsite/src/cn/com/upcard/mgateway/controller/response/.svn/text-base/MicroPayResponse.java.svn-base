package cn.com.upcard.mgateway.controller.response;

public class MicroPayResponse extends BaseResponse {

	private static final long serialVersionUID = -3363997570667277533L;
	// 商户号，由平台分配
	private String mchId;
	// 银商订单号
	private String transactionId;
	// 交易状态
	private String tradeState;
	// 用户标识,用户在子商户appid下的唯一标识
	private String buyerAccount;
	// 商户系统内部的定单号，32个字符内、可包含字母
	private String outTradeNo;
	// 总金额，以分为单位，不允许包含任何字、符号
	private String totalAmount;
	// 折扣金额
	private String discountAmount;
	// 附加信息 商家数据包，原样返回
	private String extendInfo;
	// 用户是否关注子公众账号，0-关注，1-未关注，仅在公众账号类型支付有效
	private String subIsSubscribe;
	// 支付完成时间，格式为yyyyMMddhhmmss，
	private String timeEnd;
	// 交易类型
	private String tradeType;
	// 第三方支付机构订单号
	private String outTransactionId;
	// 子商户appid
	private String subAppid;
	// 设备号
	private String deviceInfo;

	public MicroPayResponse(String code, String msg) {
		super(code, msg);
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getSubIsSubscribe() {
		return subIsSubscribe;
	}

	public void setSubIsSubscribe(String subIsSubscribe) {
		this.subIsSubscribe = subIsSubscribe;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Override
	public String toString() {
		return "MicroPayResponse [ mchId=" + mchId + ", buyerAccount=" + buyerAccount + ", outTradeNo=" + outTradeNo
				+ ", totalAmount=" + totalAmount + ", discountAmount=" + discountAmount + ", extendInfo=" + extendInfo
				+ ", subIsSubscribe=" + subIsSubscribe + ", timeEnd=" + timeEnd + ", tradeType=" + tradeType
				+ ", outTransactionId=" + outTransactionId + ", transactionId=" + transactionId + ",tradeState="
				+ tradeState + ",subAppid=" + subAppid + ",deviceInfo=" + deviceInfo + "]";
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getOutTransactionId() {
		return outTransactionId;
	}

	public void setOutTransactionId(String outTransactionId) {
		this.outTransactionId = outTransactionId;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getBuyerAccount() {
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

}
