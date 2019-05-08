package cn.com.upcard.mgateway.payment.weixin.model;

public class AccessToken extends WxBaseRespInfo{
	private String accessToken;
	private String expiresIn;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	@Override
	public String toString() {
		return "AccessToken [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", errcode=" + errcode
				+ ", errmsg=" + errmsg + "]";
	}
}
