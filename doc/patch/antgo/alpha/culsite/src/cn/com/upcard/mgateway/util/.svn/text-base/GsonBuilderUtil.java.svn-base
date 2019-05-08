package cn.com.upcard.mgateway.util;

import java.text.DateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonBuilderUtil {

	public static Gson create() {
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
		gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
		Gson gson = gb.create();
		return gson;
	}

}
