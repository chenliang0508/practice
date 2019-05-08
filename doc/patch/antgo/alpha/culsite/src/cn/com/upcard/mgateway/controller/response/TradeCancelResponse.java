package cn.com.upcard.mgateway.controller.response;

import cn.com.upcard.mgateway.common.enums.ResponseResult;

public class TradeCancelResponse extends BaseResponse{
	private static final long serialVersionUID = -6417909034527358322L;
	private String outTradeNo;
	private String transactionId;
	private String mchId;
	private String tradeState;
	
	public TradeCancelResponse (ResponseResult responseResult) {
		super(responseResult);
	}
	
	public TradeCancelResponse (String code, String msg) {
		super(code, msg);
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

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}
}
