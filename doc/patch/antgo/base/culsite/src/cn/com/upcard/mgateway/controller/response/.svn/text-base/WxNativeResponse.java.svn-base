package cn.com.upcard.mgateway.controller.response;

public class WxNativeResponse extends BaseResponse {

	private static final long serialVersionUID = 827141228603491565L;

	private String authCodeUrl;// 二维码链接 此参数可直接生成二维码展示出来进行扫码支付
	private String authCodeImgUrl;// 用此链接请求二维码图片

	public WxNativeResponse() {
		super();
	}

	public WxNativeResponse(String code, String msg) {
		super(code, msg);
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
