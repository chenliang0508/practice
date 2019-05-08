package cn.com.upcard.mgateway.payment.alipay;

public class UserInfoRequest {
	//值为authorization_code时，代表用code换取；值为refresh_token时，代表用refresh_token换取
	private GrantType grantType;
	private String code;
	private String refreshToken;
	
	public UserInfoRequest() {
		
	}
	public UserInfoRequest(GrantType grantType, String code) {
		this.grantType = grantType;
		this.code = code;
	}
	
	public GrantType getGrantType() {
		return grantType;
	}
	public void setGrantType(GrantType grantType) {
		this.grantType = grantType;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public static enum GrantType{
		AUTHORIZATION_CODE,
		REFRESH_TOKEN;
	}
}
