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
public class TradeCancelTest extends ControllerTest {
	private static Logger logger = LoggerFactory.getLogger(TradeCancelTest.class);
	private static final String service = "pay.trade.cancel";
	private static final String signType = "md5";
	private static final String returnType = "json";
	
	private String outTradeNo;
	
	public TradeCancelTest(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] data = {
				{"liujiemicropay10"}
		};
		return Arrays.asList(data);
	}
	
	@Test
	public void tesAliPayNative() {
		Map<String, String> data = new HashMap<String, String>(16);
		data.put("service", service);
		data.put("mchId", MCH_ID);
		data.put("signType", signType);
		data.put("returnType", returnType);
		data.put("outTradeNo", this.outTradeNo);
		data.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		
		send(data);
	}
}
