package cn.com.upcard.mgateway.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Utils {

	public static String sign(Map<String, String> paraMap, String privatekey) {
		Set<String> keySet = paraMap.keySet();
		List<String> values = new ArrayList<>();
		for (String str : keySet) {
			if (StringUtils.isEmpty(str) || "sign".equals(str)) {
				continue;
			}
			values.add(paraMap.get(str));
		}
		Collections.sort(values);
		StringBuilder sb = new StringBuilder();
		for (String str : values) {
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			sb.append(str);
		}
		sb.append(privatekey);
		System.out.println("签名前的："+ sb.toString());
		String sign = DigestUtils.md5Hex(sb.toString());
		System.out.println("签名：" + sign);
		return sign;
	}

	public static String formatBizQueryParaMap(Map<String, String> paraMap, boolean urlencode) {

		String buff = "";
		try {
			// 将Map转换为Set集合
			Set<Entry<String, String>> keyset = paraMap.entrySet();
			// 遍历Map信息
			for (Iterator<Entry<String, String>> iterator = keyset.iterator(); iterator.hasNext();) {
				Entry<String, String> entry = iterator.next();
				if (entry.getKey() != "") {
					String key = entry.getKey();
					String val = entry.getValue();
					if (urlencode) {
						// 将当前值进行转码，使用utf-8编码
						val = URLEncoder.encode(val, "utf-8");
					}
					buff += key + "=" + val + "&";
				}
			}

			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buff;
	}

	@Test
	public void testEquals() {
		if ("SUCCESS".equalsIgnoreCase("success")) {
			System.out.println("相等");
		}
	}
}
