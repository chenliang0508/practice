package cn.com.upcard.mgateway.controller.response;

import cn.com.upcard.mgateway.common.enums.ResponseResult;

public class TradeRefundResponse extends BaseResponse {
	private static final long serialVersionUID = 2563060089175811305L;
	public TradeRefundResponse() {
		super();
	}
	public TradeRefundResponse(String code, String msg) {
		super(code, msg);
	}
	public TradeRefundResponse(ResponseResult responseResult) {
		super(responseResult.getCode(), responseResult.getMsg());
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
	public TradeRefundResponse setMchId(String mchId) {
		this.mchId = mchId;
		return this;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public TradeRefundResponse setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
		return this;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public TradeRefundResponse setRefundNo(String refundNo) {
		this.refundNo = refundNo;
		return this;
	}
	public String getRefundId() {
		return refundId;
	}
	public TradeRefundResponse setRefundId(String refundId) {
		this.refundId = refundId;
		return this;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public TradeRefundResponse setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
		return this;
	}
	public String getTradeState() {
		return tradeState;
	}
	public TradeRefundResponse setTradeState(String tradeState) {
		this.tradeState = tradeState;
		return this;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public TradeRefundResponse setRefundTime(String refundTime) {
		this.refundTime = refundTime;
		return this;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public TradeRefundResponse setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
		return this;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public TradeRefundResponse setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
		return this;
	}
}
