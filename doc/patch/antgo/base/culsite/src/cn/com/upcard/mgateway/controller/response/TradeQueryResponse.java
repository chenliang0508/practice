package cn.com.upcard.mgateway.controller.response;

public class TradeQueryResponse extends BaseResponse {

	private static final long serialVersionUID = -1140768431421376436L;

	public TradeQueryResponse() {

	}

	public TradeQueryResponse(String code, String msg) {
		super(code, msg);
	}

	private String tradeState;// 交易状态
	private String mchId;// 商户号

	private String subAppid;// 子商户appid
	private String buyerAccount;//当微信支付时为openid，当支付宝时为用户id
	private String subIsSubscribe;// 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效（子商户公众账号）
	private String transactionId;// 平台交易号
	private String outTradeNo;// 商户系统内部的定单号，32个字符内、可包含字母
	private String totalAmount;// 以分为单位，不允许包含任何字、符号
	private String discountAmount;// 折扣金额
	private String extendInfo;// 商家数据包，原样返回
	private String timeEnd;// 支付完成时间，格式为yyyyMMddhhmmss，如2009年12月27日9点10分10秒表示为20091227091010。时区为GMT+8
							// beijing。该时间取自平台服务器
	private String merchantNo;
	private String deviceInfo;
	private String outTransactionId;
	private String tradeType;
	
	
	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getSubIsSubscribe() {
		return subIsSubscribe;
	}

	public void setSubIsSubscribe(String subIsSubscribe) {
		this.subIsSubscribe = subIsSubscribe;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
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

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getBuyerAccount() {
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	public String getOutTransactionId() {
		return outTransactionId;
	}

	public void setOutTransactionId(String outTransactionId) {
		this.outTransactionId = outTransactionId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
}
