package cn.com.upcard.mgateway.channel.bank.cmbc.util;

import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	/**
	 * 对map集合经行排序和除去空和sign
	 * @param sArray
	 * @return
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
		Map<String, String> map = new TreeMap<String, String>();

		if (null == sArray || sArray.size() <= 0) {
			return map;
		}
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (null == value || "" == value.trim() || key.equalsIgnoreCase("sign")) {
				continue;
			}
			map.put(key, value);
		}
		return map;
	}

	public static String mapToJson(Map<String, String> params) {
		return JSONObject.toJSONString(params);
	}

	public static JSONObject parseObject(String data) {
		return JSONObject.parseObject(data);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> jsonToMap(String dncryptContext) {
		Map<String, String> data = (Map<String, String>)JSONObject.parse(dncryptContext);
		String body= data.get("body");
		Map<String, String> map = (Map<String, String>)JSONObject.parse(body);
		return map; 
	}

}
