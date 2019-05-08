package cn.com.upcard.mgateway.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.BaseTest;
import cn.com.upcard.mgateway.util.HttpClient;

@RunWith(Parameterized.class)
public class AlipayJsapiTest extends ControllerTest {
	private static Logger logger = LoggerFactory.getLogger(AlipayJsapiTest.class);
	
	private String out_trade_no;
	private String total_amount;
	private String device_info;
	private String buyerLoginId;
	
	public AlipayJsapiTest(String out_trade_no, String total_amount, String device_info, String buyerLoginId) {
		this.out_trade_no = out_trade_no;
		this.total_amount = total_amount;
		this.device_info = device_info;
		this.buyerLoginId = buyerLoginId;
	}
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] obj = new Object[][]{
//			{String.valueOf(System.currentTimeMillis()), "500", "T166056", "28565982988"},
			{String.valueOf(System.currentTimeMillis()), "100", "T166057", "2501585652@qq.com"}
		};
		return Arrays.asList(obj);
	}
	
	@Test
	public void testMchId() {
		String resultString = null;
		String requestUrl = "https://www.chinagiftcard.cn/mgateway/gateway";
//		String requestUrl = "http://127.0.0.1:8080/mgateway/gateway";
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		Map<String, String> data = new HashMap<String, String>(16);
		data.put("service", "pay.alipay.jspay");
		data.put("mchId", "17091311000000");
		data.put("signType", "md5");
		data.put("returnType", "json");
		data.put("outTradeNo", this.out_trade_no);
		data.put("totalAmount", this.total_amount);
		data.put("deviceInfo", this.device_info);
		data.put("subject", "LUCKLY COFFEE SHOP");
		data.put("body", "建工一楼咖啡店满减");
		data.put("timeStart", "20170816113222");
		data.put("timeExpire", "20170816113222");
		data.put("operUser", "chenlaing0508");
		data.put("merchantNo", "1200356565");
		data.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
		data.put("notifyUrl", "http://123.com");
		data.put("payTypeLimit", "");
		data.put("goodTags", "");
		data.put("reqIp", "127.0.0.1");
		data.put("buyerLoginId", this.buyerLoginId);
		
		send(requestUrl, data, "33B4E81507802BBAC1064AB43919B832");
//		data.put("sign", sign(data, "2138C1821C51C245DC2A39B9CC2C00E4"));
//		try {
//			resultString = "";
//			int status = hc.send(data, "utf-8");
//			if (200 == status) {
//				resultString = hc.getResult();
//			} else {
//				logger.info(String.valueOf(status));
//			}
//			logger.info(resultString);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	
	@Test
	public void testAlinative() {
		String resultString = null;
		String requestUrl = "https://www.chinagiftcard.cn/mgateway/gateway";
		Map<String, String> data = new HashMap<String, String>(16);
		data.put("service", "pay.alipay.native");
		data.put("mchId", "17091311000000");
		data.put("signType", "md5");
		data.put("returnType", "json");
		data.put("outTradeNo", "20170915BC001");
		data.put("totalAmount", "1");
		data.put("deviceInfo", "机具号005");
		data.put("subject", "LUCKLY COFFEE SHOP");
		data.put("body", "建工一楼咖啡店满减");
		data.put("timeStart", "20170816113222");
		data.put("timeExpire", "20170816113222");
		data.put("operUser", "chenlaing0508");
		data.put("merchantNo", "1200356565");
		data.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
		data.put("notifyUrl", "http://123.com");
		data.put("payTypeLimit", "credit_card");
		data.put("goodTags", "");
		data.put("reqIp", "127.0.0.1");
		
		send(requestUrl, data, "33B4E81507802BBAC1064AB43919B832");
//		try {
//			resultString = "";
//			int status = hc.send(data, "utf-8");
//			if (200 == status) {
//				resultString = hc.getResult();
//			} else {
//				logger.info(String.valueOf(status));
//			}
//			logger.info(resultString);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
