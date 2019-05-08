package cn.com.upcard.mgateway.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.BaseTest;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.Utils;

public class UnifiedRefundTest extends BaseTest {
	private static String URL = "https://www.chinagiftcard.cn/mgateway/gateway?";
	private static Logger logger = LoggerFactory.getLogger(UnifiedRefundTest.class);
	
	@Test
	public void testTradeRefund() throws ParseException {

		Map<String, String> data = new HashMap<String, String>(16);

		data.put("out_trade_no", "1505457355695");
		data.put("refund_no", "R" + System.currentTimeMillis());
		data.put("refund_amount", "1");
		data.put("service", "unified.trade.refund");
		data.put("mch_id", "17091311000001");
		data.put("oper_user", "cszh");
		data.put("sign_type", "md5");
		data.put("return_type", "json");
		data.put("timestamp", String.valueOf(System.currentTimeMillis()/1000) + "");
		data.put("sign",  sign(data, "33B4E81507802BBAC1064AB439190000"));

		logger.info(URL + "?" + Utils.formatBizQueryParaMap(data, false));

		try {
			HttpClient hc = new HttpClient(URL, 30000, 30000);
			int status = hc.send(data, "utf-8");
			if (200 == status) {
				String resultString = hc.getResult();
				logger.info("\r\n" + resultString);
			} else {
				logger.info(String.valueOf(status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
