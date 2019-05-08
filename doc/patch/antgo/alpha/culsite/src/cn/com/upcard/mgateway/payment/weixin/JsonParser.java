package cn.com.upcard.mgateway.payment.weixin;

import com.alibaba.fastjson.JSONObject;

public class JsonParser {

	public static AccessToken parseAccessToken(String json) {
		JSONObject obj = JSONObject.parseObject(json);
		AccessToken accessToken = new AccessToken();
		accessToken.setAccessToken(obj.getString("access_token"));
		accessToken.setExpiresIn(obj.getString("expires_in"));
		accessToken.setRefreshToken(obj.getString("refresh_token"));
		accessToken.setOpenid(obj.getString("openid"));
		accessToken.setScope(obj.getString("scope"));
		accessToken.setErrcode(obj.getString("errcode"));
		accessToken.setErrmsg(obj.getString("errmsg"));
		return accessToken;
	}

}
