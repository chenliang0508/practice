package cn.com.upcard.mgateway.controller.request;

import cn.com.upcard.mgateway.annotion.HttpParamName;
import cn.com.upcard.mgateway.common.RequestParameter;
import cn.com.upcard.mgateway.common.Restrict;
import cn.com.upcard.mgateway.common.enums.Required;

public class UnifiedRefundParam extends BaseParam {
	private static final long serialVersionUID = -1277101281662095100L;
	@HttpParamName(name = RequestParameter.OUT_TRADE_NO, param = Required.REQUIRE, maxLen = 32, restrict = Restrict.OUT_TRADE_NO)
	private String outTradeNo;
	@HttpParamName(name = RequestParameter.REFUND_NO, param = Required.REQUIRE, maxLen = 32)
	private String refundNo;
	@HttpParamName(name = RequestParameter.TRANSACTION_ID, param = Required.OPTIONAL, maxLen = 40)
	private String transactionId;
	@HttpParamName(name = RequestParameter.REFUND_AMOUNT, param = Required.REQUIRE, maxLen = 10,minValue = 1, maxValue = 99999999, unSignedInt = true)
	private String refundAmount;
	@HttpParamName(name = RequestParameter.OPER_USER, maxLen = 20)
	private String operUser;
	@HttpParamName(name = RequestParameter.REFUND_CHANNEL, maxLen = 20)
	private String refundChannel;//ORIGINAL-原路退款，默认
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
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getOperUser() {
		return operUser;
	}
	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}
	public String getRefundChannel() {
		return refundChannel;
	}
	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}
	@Override
	public String toString() {
		return "UnifiedRefundParam [outTradeNo=" + outTradeNo + ", refundNo=" + refundNo + ", transactionId="
				+ transactionId + ", refundAmount=" + refundAmount + ", operUser=" + operUser + ", refundChannel="
				+ refundChannel + ", service=" + service + ", signType=" + signType + ", returnType=" + returnType
				+ ", sign=" + sign + ", mchId=" + mchId + "]";
	}
}
