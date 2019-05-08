package cn.com.upcard.mgateway.controller.request;

import cn.com.upcard.mgateway.annotion.HttpParamName;
import cn.com.upcard.mgateway.common.RequestParameter;
import cn.com.upcard.mgateway.common.Restrict;
import cn.com.upcard.mgateway.common.enums.Required;

public class TradeCancelParam extends BaseParam{
	private static final long serialVersionUID = -3921359642663745046L;
	
	@HttpParamName(name = RequestParameter.OUT_TRADE_NO, param = Required.REQUIRE, maxLen = 32, restrict = Restrict.OUT_TRADE_NO)
	private String outTradeNo;
	@HttpParamName(name = RequestParameter.TRANSACTION_ID, maxLen = 40)
	private String transactionId;
	
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
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
	@Override
	public String toString() {
		return "TradeCancelParam [outTradeNo=" + outTradeNo + ", transactionId=" + transactionId + ", service="
				+ service + ", signType=" + signType + ", returnType=" + returnType + ", sign=" + sign + ", mchId="
				+ mchId + "]";
	}
	
}
