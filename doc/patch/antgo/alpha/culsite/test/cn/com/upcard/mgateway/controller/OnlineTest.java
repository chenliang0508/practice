package cn.com.upcard.mgateway.controller;

import org.junit.Test;

public class OnlineTest {
	
	@Test
	public void createRequestUrl() {
		//生成支付宝线上支付的相关url
		String outTradeNo = System.currentTimeMillis() + "";
		AlipayNativeTest alipayPay = new AlipayNativeTest( outTradeNo + "", "1", "huatingzhou", "1111111", "银商支付支付", "jjjjj", "20171010153512",
				"20171011203412", "1111", "", "22333", "127.0.0.1" );
//		alipayPay.
	}
}
