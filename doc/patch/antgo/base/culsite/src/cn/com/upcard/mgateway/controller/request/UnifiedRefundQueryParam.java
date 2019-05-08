package cn.com.upcard.mgateway.controller.request;

import cn.com.upcard.mgateway.annotion.HttpParamName;
import cn.com.upcard.mgateway.common.RequestParameter;
import cn.com.upcard.mgateway.common.Restrict;
import cn.com.upcard.mgateway.common.enums.Required;

public class UnifiedRefundQueryParam extends BaseParam {

	private static final long serialVersionUID = 7183464850172103108L;
	@HttpParamName(name = RequestParameter.REFUND_NO, param = Required.REQUIRE, maxLen = 32, restrict = Restrict.OUT_TRADE_NO)
	private String refundNo;
	@HttpParamName(name = RequestParameter.REFUND_ID, param = Required.OPTIONAL, maxLen = 40)
	private String refundId;
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
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
	@Override
	public String toString() {
		return "UnifiedRefundQueryParam [refundNo=" + refundNo + ", refundId=" + refundId + ", service=" + service
				+ ", signType=" + signType + ", returnType=" + returnType + ", sign=" + sign + ", mchId=" + mchId + "]";
	}
	
}
