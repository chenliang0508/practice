package cn.com.upcard.mgateway.controller.response;

public class RefundQueryResponse extends BaseResponse {
	private static final long serialVersionUID = -333103189129066093L;
	
	public RefundQueryResponse() {

	}

	public RefundQueryResponse(String code, String msg) {
		super(code, msg);
	}
	private String mchId;
	private String outTradeNo;//交易订单订单号
	private String refundNo;//退款订单号
	private String refundId;//退款流水号
	private String refundAmount;//退款金额
	private String tradeState;//退款状态
	private String refundTime;
	private String merchantNo;
	private String deviceInfo;

	public String getMchId() {
		return mchId;
	}

	public RefundQueryResponse setMchId(String mchId) {
		this.mchId = mchId;
		return this;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public RefundQueryResponse setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
		return this;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public RefundQueryResponse setRefundNo(String refundNo) {
		this.refundNo = refundNo;
		return this;
	}

	public String getRefundId() {
		return refundId;
	}

	public RefundQueryResponse setRefundId(String refundId) {
		this.refundId = refundId;
		return this;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public RefundQueryResponse setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
		return this;
	}

	public String getTradeState() {
		return tradeState;
	}

	public RefundQueryResponse setTradeState(String tradeState) {
		this.tradeState = tradeState;
		return this;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public RefundQueryResponse setRefundTime(String refundTime) {
		this.refundTime = refundTime;
		return this;
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
}
