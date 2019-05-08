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

@RunWith(Parameterized.class)
public class AlipayNativeTest extends ControllerTest {
	private static Logger logger = LoggerFactory.getLogger(AlipayNativeTest.class);
	private static final String service = "pay.alipay.native";
	private static final String signType = "md5";
	private static final String returnType = "json";

	private String outTradeNo;
	private String totalAmount;
	private String merchantNo;
	private String deviceInfo;
	private String subject;
	private String body;
	private String timeStart;
	private String timeExpire;
	private String operUser;
	private String payTypeLimit;
	private String goodTags;
	private String reqIp;

	public AlipayNativeTest(String outTradeNo, String totalAmount, String merchantNo, String deviceInfo, String subject,
			String body, String timeStart, String timeExpire, String operUser, String payTypeLimit, String goodTags,
			String reqIp) {
		super();
		this.outTradeNo = outTradeNo;
		this.totalAmount = totalAmount;
		this.merchantNo = merchantNo;
		this.deviceInfo = deviceInfo;
		this.subject = subject;
		this.body = body;
		this.timeStart = timeStart;
		this.timeExpire = timeExpire;
		this.operUser = operUser;
		this.payTypeLimit = payTypeLimit;
		this.goodTags = goodTags;
		this.reqIp = reqIp;
	}

	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] obj = new Object[][] {
				// (String outTradeNo, String totalAmount, String merchantNo, String deviceInfo,
				// String subject,
				// String body, String timeStart, String timeExpire, String operUser, String
				// payTypeLimit, String goodTags,
				// String reqIp)
//				{ System.currentTimeMillis() + "", "1", "huatingzhou", "1111111", "银商支付支付", "jjjjj", "20171010153512",
//						"20171010153512", "1111", "dfgdfg", "22333", "127.0.0.1" } ,
				//订单超时时间早于订单开始时间
//				{ System.currentTimeMillis() + "", "1", "huatingzhou", "1111111", "银商支付支付", "jjjjj", "20171010153512",
//							"20171010153412", "1111", "dfgdfg", "22333", "127.0.0.1" } ,
				{ System.currentTimeMillis() + "", "1", "huatingzhou", "1111111", "银商支付支付", "jjjjj", "20171010153512",
								"20171019203412", "1111", "", "22333", "127.0.0.1" } };
		return Arrays.asList(obj);
	}

	@Test
	public void tesAliPayNative() {
		Map<String, String> data = new HashMap<String, String>(16);
		data.put("service", service);
		data.put("mchId", MCH_ID);
		data.put("signType", signType);
		data.put("returnType", returnType);
		data.put("outTradeNo", this.outTradeNo);
		data.put("totalAmount", this.totalAmount);
		data.put("deviceInfo", this.deviceInfo);
		data.put("subject", this.subject);
		data.put("body", this.body);
		data.put("timeStart", this.timeStart);
		data.put("timeExpire", this.timeExpire);
		data.put("operUser", this.operUser);
		data.put("merchantNo", this.merchantNo);
		data.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		data.put("notifyUrl", "http://123.com");
		data.put("payTypeLimit", this.payTypeLimit);
		data.put("goodTags", this.goodTags);
		data.put("reqIp", this.reqIp);

		System.out.println(send(data));
	}
}
