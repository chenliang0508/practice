package cn.com.upcard.mgateway.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class SignUtil {

	/**
	 * 产生响应结果签名
	 * 
	 * @param object
	 * @param commKey
	 * @return
	 */
	public static String generateResponseSign(Object object, String commKey) {
		String pre = JSONObject.toJSONString(object);
		String[] signParam = { pre, commKey };
		return DigestUtils.md5Hex(StringUtils.join(signParam));
	}

}
