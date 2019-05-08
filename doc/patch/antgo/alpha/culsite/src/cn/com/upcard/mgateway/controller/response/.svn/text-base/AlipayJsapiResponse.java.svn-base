package cn.com.upcard.mgateway.controller.response;

import cn.com.upcard.mgateway.common.enums.ResponseResult;

public class AlipayJsapiResponse extends BaseResponse {
	private static final long serialVersionUID = -8170262016918755495L;
	public AlipayJsapiResponse() {
		super();
	}
	public AlipayJsapiResponse(String code, String msg) {
		super(code, msg);
	}
	public AlipayJsapiResponse(ResponseResult responseResult) {
		super(responseResult.getCode(), responseResult.getMsg());
	}
	
	private String payInfo;//原生支付信息 String(64)	JSON字符串，自行唤起支付宝钱包支付
	private String payUrl;//直接使用此链接请求支付宝支付
	public String getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	@Override
	public String toString() {
		return "AlipayJsapiResponse [payInfo=" + payInfo + ", payUrl=" + payUrl + "]";
	}
}
