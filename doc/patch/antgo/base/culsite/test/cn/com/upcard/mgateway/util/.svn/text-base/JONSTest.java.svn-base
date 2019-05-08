package cn.com.upcard.mgateway.util;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import cn.com.upcard.mgateway.controller.response.AlipayJsapiResponse;

public class JONSTest {
	@Test
	public void testToJson() {
		String str = "{\"code\":\"00\",\"msg\":\"success\",\"payInfo\":\"{\\\"tradeNO\\\":\\\"2017091421001004620273353044\\\","
				+ "\\\"status\\\":\\\"0\\\"}\",\"payUrl\":\"https://pay.swiftpass.cn/pay/prepay?token_id=1c48e17977964838155e4ee1b6a2e5d2f&trade_type=pay.alipay.jspayv3\","
				+ "\"sign\":\"d845cb048c593a1c6ec9d366697e6127\",\"status\":\"0\"}";
		
		AlipayJsapiResponse s = JSONObject.parseObject(str, AlipayJsapiResponse.class);
		System.out.println(s.toString());
	}
}
