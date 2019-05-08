package cn.com.upcard.mgateway.channel.bank.cmbc.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class CMBCToolUtils {
	private static final String VERTICAL_LINE = "\\|";
	private static final String EQUALS = "=";

	public static Map<String, String> analyzeCMBCMessageToMap(String message) {

		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(message)) {
			return map;
		}
		String[] datas = message.split(VERTICAL_LINE);
		if (null == datas) {
			return null;
		}
		for (int i = 0; i < datas.length; i++) {
			String[] data = datas[i].split(EQUALS);
			if (data.length != 2) {
				continue;
			}
			map.put(data[0], data[1]);
		}

		return map;
	}

}
