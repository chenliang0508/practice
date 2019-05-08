package cn.com.upcard.mgateway.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.Utils;

public class WxNativeTest {

	private static Logger logger = LoggerFactory.getLogger(WxNativeTest.class);

	private final String requestUrl = "http://192.168.120.176:7522/mgateway/gateway";
	private final String privateKey = "33B4E81507802BBAC1064AB43919B832";
	private final String REQUEST_URL = "http://192.168.120.176:7522/mgateway/gateway";
	private final String RETURN_URL = "http://192.168.2.189:8080/mgateway/notifytest/analogreception";
	private final String PRIVATE_KEY = "52572076049A0AA542B977C8A1D7A3FC";
	private final String MCH_ID = "17101812000005";
	private String CURRENTTIME = DateUtil.formatDate(new Date(), DateUtil.yyyyMMddHHmmss);
	private String TIMESTAMP = String.valueOf(System.currentTimeMillis()).substring(0, 10);

	@Test
	public void payNormalTest() throws ParseException {

		Map<String, String> data = new HashMap<String, String>(16);

		data.put("outTradeNo", System.currentTimeMillis() / 1000 + "");
		data.put("deviceInfo", "1205872584455");
		data.put("body", "无敌大魔王");
		data.put("extendInfo", "智障小弱鸡");
		data.put("totalAmount", "1");
		data.put("reqIp", "127.0.0.1");
		data.put("notifyUrl", RETURN_URL);
		data.put("timeStart", CURRENTTIME);
		data.put("timeExpire", DateUtil.addHours(CURRENTTIME, DateUtil.yyyyMMddHHmmss, 1));
		data.put("operUser", "zhoudi");
//		data.put("pay_type_limit", null);

		data.put("service", "pay.wx.native");
		data.put("mchId", MCH_ID);
		data.put("signType", "md5");
		data.put("returnType", "json");
		data.put("timestamp", TIMESTAMP);
		data.put("sign", Utils.sign(data, PRIVATE_KEY));

		logger.info(REQUEST_URL + "?" + Utils.formatBizQueryParaMap(data, true));

//		try {
//			HttpClient hc = new HttpClient(REQUEST_URL, 30000, 30000);
//			int status = hc.send(data, "utf-8");
//			if (200 == status) {
//				String resultString = hc.getResult();
//				logger.info("\r\n" + resultString);
//			} else {
//				logger.info(String.valueOf(status));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Test
	public void queryNormalTest() throws ParseException {

		Map<String, String> data = new HashMap<String, String>(16);

		data.put("outTradeNo", "1508343642431");

		data.put("service", "unified.trade.query");
		data.put("mchId", MCH_ID);
		data.put("signType", "md5");
		data.put("returnType", "json");
		data.put("timestamp", TIMESTAMP);
		data.put("sign", Utils.sign(data, PRIVATE_KEY));

		logger.info(REQUEST_URL + "?" + Utils.formatBizQueryParaMap(data, true));

//		try {
//			HttpClient hc = new HttpClient(REQUEST_URL, 30000, 30000);
//			int status = hc.send(data, "utf-8");
//			if (200 == status) {
//				String resultString = hc.getResult();
//				logger.info("\r\n" + resultString);
//			} else {
//				logger.info(String.valueOf(status));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Test
	public void refundNormalTest() {

		Map<String, String> data = new HashMap<String, String>(16);

		data.put("outTrade_no", "1508343642431");
		data.put("refundNo", "refundnativepay001");
		data.put("refundAmount", "1");

		data.put("service", "unified.trade.refund");
		data.put("mchId", MCH_ID);
		data.put("operUser", MCH_ID);
		data.put("timestamp", TIMESTAMP);
		data.put("signType", "MD5");
		data.put("returnType", "JSON");
		data.put("sign", Utils.sign(data, PRIVATE_KEY));

		logger.info(REQUEST_URL + "?" + Utils.formatBizQueryParaMap(data, false));

//		try {
//			HttpClient hc = new HttpClient(REQUEST_URL, 30000, 30000);
//			int status = hc.send(data, "utf-8");
//			if (200 == status) {
//				String resultString = hc.getResult();
//				logger.info("\r\n" + resultString);
//			} else {
//				logger.info(String.valueOf(status));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Test
	public void refundQueryNormalTest() {
		Map<String, String> data = new HashMap<String, String>(16);

		data.put("refundNo", "refundnativepay001");

		data.put("mchId", MCH_ID);
		data.put("service", "unified.trade.refundQuery");
		data.put("signType", "MD5");
		data.put("returnType", "JSON");
		data.put("timestamp", TIMESTAMP);
		data.put("sign", Utils.sign(data, PRIVATE_KEY));

		logger.info(REQUEST_URL + "?" + Utils.formatBizQueryParaMap(data, false));

//		try {
//			HttpClient hc = new HttpClient(REQUEST_URL, 30000, 30000);
//			int status = hc.send(data, "utf-8");
//			if (200 == status) {
//				String resultString = hc.getResult();
//				logger.info("\r\n" + resultString);
//			} else {
//				logger.info(String.valueOf(status));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Test
	public void closeNormalTest() {

		Map<String, String> data = new HashMap<String, String>(16);

		data.put("outTradeNo", "1508341132");

		data.put("service", "unified.trade.close");
		data.put("mchId", MCH_ID);
		data.put("timestamp", TIMESTAMP);
		data.put("signType", "MD5");
		data.put("returnType", "JSON");
		data.put("sign", Utils.sign(data, PRIVATE_KEY));

		logger.info(REQUEST_URL + "?" + Utils.formatBizQueryParaMap(data, false));

//		try {
//			HttpClient hc = new HttpClient(REQUEST_URL, 30000, 30000);
//			int status = hc.send(data, "utf-8");
//			if (200 == status) {
//				String resultString = hc.getResult();
//				logger.info("\r\n" + resultString);
//			} else {
//				logger.info(String.valueOf(status));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
