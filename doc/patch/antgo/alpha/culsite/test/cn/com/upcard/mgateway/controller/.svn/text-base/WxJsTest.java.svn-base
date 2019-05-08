package cn.com.upcard.mgateway.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.Utils;

public class WxJsTest {

	private static Logger logger = LoggerFactory.getLogger(WxJsTest.class);

	private final String requestUrl = "https://www.chinagiftcard.cn/mgateway/gateway";
	private final String privateKey = "051E5FF91C293D673C974FEF67571056";
	private String currentTime = DateUtil.formatDate(new Date(), DateUtil.yyyyMMddHHmmss);

	@Test
	public void normalTest() throws ParseException {

		Map<String, String> data = new HashMap<String, String>(16);

		data.put("outTradeNo", "wxnativepay217");
		data.put("deviceInfo", "1205872584455");
		data.put("body", "超级大魔王");
		data.put("extendInfo", "智障小弱鸡");
		data.put("totalAmount", "1");
		data.put("reqIp", "127.0.0.1");
		data.put("notifyUrl", "http://123.com");
		data.put("timeStart", currentTime);
		data.put("timeExpire", DateUtil.addHours(currentTime, DateUtil.yyyyMMddHHmmss, 1));
		data.put("operUser", "zhoudi");
		data.put("isRaw", "1");
		data.put("isMinipg", "0");
		// data.put("pay_type_limit", null);
		 data.put("subOpenid", "o51ZSwsGWBVDnUu4n6ic47zn8T4k");
		 data.put("subAppid", "wxe1816bfc46792085");

		data.put("service", "pay.wx.js");
		data.put("mchId", "17091912000558");
		data.put("signType", "md5");
		data.put("returnType", "json");
		data.put("timestamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
		data.put("sign", Utils.sign(data, privateKey));

		logger.info(requestUrl + "?" + Utils.formatBizQueryParaMap(data, false));

		try {
			HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
			int status = hc.send(data, "utf-8");
			if (200 == status) {
				String resultString = hc.getResult();
				logger.info(resultString);
			} else {
				logger.info(String.valueOf(status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void queryNormalTest() throws ParseException {

		Map<String, String> data = new HashMap<String, String>(16);

		data.put("out_trade_no", "wxnativepay019");
		data.put("service", "unified.trade.query");
		data.put("mch_id", "17083012000043");
		data.put("sign_type", "md5");
		data.put("return_type", "json");
		data.put("timestamp", currentTime);
		data.put("sign", Utils.sign(data, privateKey));

		logger.info(requestUrl + "?" + Utils.formatBizQueryParaMap(data, false));

		try {
			HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
			int status = hc.send(data, "utf-8");
			if (200 == status) {
				String resultString = hc.getResult();
				logger.info(resultString);
			} else {
				logger.info(String.valueOf(status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void refundNormalTest() {
		Map<String, String> data = new HashMap<String, String>(16);

		data.put("service", "unified.trade.refund");
		data.put("sign_type", "MD5");
		data.put("return_type", "JSON");
		data.put("mch_id", "17091112000305");
		data.put("out_trade_no", "out_trade_no65875466");
		data.put("refund_no", UUID.randomUUID().toString().substring(15));
		data.put("refund_amount", "1");
		data.put("timestamp", currentTime);
		data.put("oper_user", "7551000001");

		logger.info(requestUrl + "?" + Utils.formatBizQueryParaMap(data, false));

		try {
			HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
			int status = hc.send(data, "utf-8");
			if (200 == status) {
				String resultString = hc.getResult();
				logger.info(resultString);
			} else {
				logger.info(String.valueOf(status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void refundQueryNormalTest() {
		Map<String, String> data = new HashMap<String, String>(16);

		data.put("service", "unified.trade.refundQuery");
		data.put("sign_type", "MD5");
		data.put("return_type", "JSON");
		data.put("timestamp", currentTime);
		data.put("mch_id", "17091112000305");
		data.put("refund_no", "3dd-84c1-6b093c5dd1a0");

		logger.info(requestUrl + "?" + Utils.formatBizQueryParaMap(data, false));

		try {
			HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
			int status = hc.send(data, "utf-8");
			if (200 == status) {
				String resultString = hc.getResult();
				logger.info(resultString);
			} else {
				logger.info(String.valueOf(status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void closeNormalTest() {

		Map<String, String> data = new HashMap<String, String>(16);

		data.put("service", "unified.trade.close");
		data.put("sign_type", "MD5");
		data.put("return_type", "JSON");
		data.put("timestamp", currentTime);
		data.put("mch_id", "17091112000305");
		data.put("outTradeNo", "3dd-84c1-6b093c5dd1a0");

		logger.info(requestUrl + "?" + Utils.formatBizQueryParaMap(data, false));

		try {
			HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
			int status = hc.send(data, "utf-8");
			if (200 == status) {
				String resultString = hc.getResult();
				logger.info(resultString);
			} else {
				logger.info(String.valueOf(status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
