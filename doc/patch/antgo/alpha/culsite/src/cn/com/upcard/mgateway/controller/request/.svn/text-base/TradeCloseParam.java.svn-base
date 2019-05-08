package cn.com.upcard.mgateway.controller.request;

import cn.com.upcard.mgateway.annotion.HttpParamName;
import cn.com.upcard.mgateway.common.RequestParameter;
import cn.com.upcard.mgateway.common.enums.Required;

/**
 * <pre>
 * 通用交易撤销请求参数
 * 
 * </pre>
 * @author huatingzhou
 * @version 1.0 使用
 */
public class TradeCloseParam extends BaseParam {
	private static final long serialVersionUID = -6297227967533470675L;
	/**
	 * 商户订单流水号
	 */
	@HttpParamName(name = RequestParameter.OUT_TRADE_NO, param = Required.REQUIRE, maxLen = 32)
	private String outTradeNo;
	
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
	@Override
	public String toString() {
		return "TradeCloseParam [outTradeNo=" + outTradeNo + ", service=" + service + ", signType=" + signType
				+ ", returnType=" + returnType + ", sign=" + sign + ", mchId=" + mchId + "]";
	}
}
