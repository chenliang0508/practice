package cn.com.upcard.mgateway.controller;
import java.text.ParseException;

public class GatewayControllerTest extends BaseTest{
	private static Logger logger = LoggerFactory.getLogger(GatewayControllerTest.class);
	@Resource
	private GatewayController gatewayController;
	public static void main(String[] args) {
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
		data.put("return_url", "http://123.com");
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