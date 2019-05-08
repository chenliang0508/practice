package cn.com.upcard.mgateway.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class ControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);
	protected static  String MGATEWAY_URL;
	protected static  String MCH_ID;
	protected static  String PRIVATE_KEY;
	
	static {
		Properties prop = new Properties();
		InputStream in =  ControllerTest.class.getResourceAsStream("/test.properties");
		try {
			prop.load(in);
			MGATEWAY_URL = prop.getProperty("MGATEWAY_URL");
			MCH_ID = prop.getProperty("MCH_ID");
			PRIVATE_KEY = prop.getProperty("PRIVATE_KEY");
			
			logger.info("MGATEWAY_URL--->{}", MGATEWAY_URL);
			logger.info("MCH_ID--->{}", MCH_ID);
			logger.info("PRIVATE_KEY--->{}", PRIVATE_KEY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected String formatBizQueryParaMap(Map<String, String> paraMap, boolean urlencode) {

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

	protected String sign(Map<String, String> paraMap, String privatekey) {
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
		System.out.println(sb.toString());
		String sign = DigestUtils.md5Hex(sb.toString());
		System.out.println(sign);
		return sign;
	}
	
	public String send(String requestUrl, Map<String, String> dataWithOutSign, String privateKey) {

		String resultString = null;
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		String sign = this.sign(dataWithOutSign, privateKey);
		dataWithOutSign.put("sign", sign);
		return requestUrl + "?" + this.formatBizQueryParaMap(dataWithOutSign, true);
//		try {
//			int status = hc.send(dataWithOutSign, "utf-8");
//			if (200 == status) {
//				resultString = hc.getResult();
//			} else {
//				logger.info(String.valueOf(status));
//			}
//			logger.info(resultString);
//			return resultString;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return resultString;
	}
	
	public String send(Map<String, String> dataWithOutSign) {
		return this.send(MGATEWAY_URL, dataWithOutSign, PRIVATE_KEY);
	}
	
	public String getRequestUrl(String requestUrl, Map<String, String> dataWithOutSign, String privateKey) {
		String sign = this.sign(dataWithOutSign, privateKey);
		dataWithOutSign.put("sign", sign);
		return requestUrl + "?" + this.formatBizQueryParaMap(dataWithOutSign, true);
	}
}
