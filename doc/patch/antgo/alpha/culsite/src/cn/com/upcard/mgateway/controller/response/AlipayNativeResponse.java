package cn.com.upcard.mgateway.controller.response;

import cn.com.upcard.mgateway.common.enums.ResponseResult;

public class AlipayNativeResponse extends BaseResponse {

	private static final long serialVersionUID = 5939312251275535805L;
	private String authCodeUrl;//二维码链接  此参数可直接生成二维码展示出来进行扫码支付
	private String authCodeImgUrl;//用此链接请求二维码图片
	
	public AlipayNativeResponse() {
		super();
	}
	public AlipayNativeResponse(String code, String msg) {
		super(code, msg);
	}
	public AlipayNativeResponse(ResponseResult responseResult) {
		super(responseResult.getCode(), responseResult.getMsg());
	}
	public String getAuthCodeUrl() {
		return authCodeUrl;
	}
	public void setAuthCodeUrl(String authCodeUrl) {
		this.authCodeUrl = authCodeUrl;
	}
	public String getAuthCodeImgUrl() {
		return authCodeImgUrl;
	}
	public void setAuthCodeImgUrl(String authCodeImgUrl) {
		this.authCodeImgUrl = authCodeImgUrl;
	}
}
