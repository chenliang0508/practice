package cn.com.upcard.mgateway.util;

import java.util.Map;
import com.google.gson.Gson;

public class JsonUtils {

	public static Map<String, String> jsonToMap(String jsonStr) {
		Map<String, String> objMap = null;
		Gson gson = GsonBuilderUtil.create();
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}

}
