package cn.com.upcard.mgateway.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CreateQrCodeUrlTest extends ControllerTest{
	private String service = "unified.qrcode.create";
	private String signType = "MD5";
	private String returnType = "JSON";
	private String mchId;
	private String timestamp;
	
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] data = {
				//商户号, 请求时间搓
				//商户不存在
//				{"17091311000001", System.currentTimeMillis() / 1000},
//				//商户号为空
//				{"17091311000001", System.currentTimeMillis() / 1000},
//				//商户格式错误 兴业
				{"17091912000558", System.currentTimeMillis() / 1000 + ""},
				//网商
//				{"17092712000792", System.currentTimeMillis() / 1000 + ""}
		};
		return Arrays.asList(data);
	}

	@Test
	public void testCreateQrCodeUrl() {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("service", service);
		requestMap.put("signType", signType);
		requestMap.put("returnType", returnType);
		requestMap.put("mchId", mchId);
		requestMap.put("timestamp", timestamp);
		
		String requestUrl = "https://www.chinagiftcard.cn/mgateway/gateway";
		String privateKey = "051E5FF91C293D673C974FEF67571056";
		
		send(requestUrl, requestMap, privateKey);
	}
	
	public CreateQrCodeUrlTest (String mchId, String timestamp) {
		this.mchId = mchId;
		this.timestamp = timestamp;
	}
}
