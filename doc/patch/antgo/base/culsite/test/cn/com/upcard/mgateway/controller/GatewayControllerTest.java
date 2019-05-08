package cn.com.upcard.mgateway.controller;
import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.Date;import java.util.HashMap;import java.util.Map;import javax.annotation.Resource;import org.junit.Test;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import cn.com.upcard.mgateway.BaseTest;import cn.com.upcard.mgateway.util.HttpClient;

public class GatewayControllerTest extends BaseTest{
	private static Logger logger = LoggerFactory.getLogger(GatewayControllerTest.class);
	@Resource
	private GatewayController gatewayController;	
	public static void main(String[] args) {		Map<String, String> data = new HashMap<String, String>(16);		data.put("service", "pay.alipay.native");		data.put("sign_type", "md5");		data.put("return_type", "json");		data.put("mch_id", "17082511000002");		data.put("out_trade_no", "out_trade_no152985165874548");		data.put("total_amount", "100050");		data.put("device_info", "1205872584455");		data.put("subject", "拿铁咖啡");		data.put("body", "优惠立减");		//data.put("extend_param", "extendParam");		data.put("time_start", "20170816113222");		data.put("time_expire", "20170816113222");		data.put("oper_user", "1251sadd8");		data.put("merchant_no", "156098");		data.put("timestamp", "1524548560");		//data.put("limit_credit_card", "Y");		data.put("notify_url", "http://123.com");		data.put("return_url", "http://123.com");		String requestUrl = "http://127.0.0.1:8080/mgateway/gateway";		String sign = sign(data, "0A0C564281038ECE5F2C8D0E0E0FB8BB");		data.put("sign", sign);		logger.info(requestUrl + "?" + formatBizQueryParaMap(data, false));		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");		sdf.setLenient(false);		try {			Date date = sdf.parse("20170816233222");			System.out.println(sdf.parse("20170816233222"));			System.out.println(sdf.format(date));		} catch (ParseException e) {			// TODO Auto-generated catch block			e.printStackTrace();		}	}		@Test
	public void testAlipayNative() {
		String resultString = null;
		String requestUrl = "http://127.0.0.1:8080/mgateway/gateway";
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		Map<String, String> data = new HashMap<String, String>(16);
		data.put("service", "pay.alipay.native");
		data.put("mch_id", "17083012000044");
		data.put("sign_type", "md5");
		data.put("return_type", "json");
		data.put("out_trade_no", String.valueOf(System.currentTimeMillis()));
		data.put("total_amount", "100");
		data.put("device_info", "1205872584455");
		data.put("subject", "拿铁咖啡");
		data.put("body", "优惠立减");
		data.put("time_start", "20170816113222");
		data.put("time_expire", "20170816113222");
		data.put("oper_user", "1251sadd8");
		data.put("merchant_no", "156098");
		data.put("timestamp", "1524548560");
		data.put("notify_url", "http://123.com");
		data.put("return_url", "http://123.com");		data.put("req_ip", "127.0.0.1");
		data.put("sign", sign(data, "2138C1821C51C245DC2A39B9CC2C00E4"));
		try {
			resultString = "";
			int status = hc.send(data, "utf-8");
			if (200 == status) {
				resultString = hc.getResult();
			} else {
				logger.info(String.valueOf(status));
			}
			logger.info(resultString);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss");
//		Date s = sdf.parse("20170816113222");
//		System.out.println(s);
//	}
}
