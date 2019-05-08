package cn.com.upcard.mgateway.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.BaseTest;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.HttpClient;


public class MicropayControllerTest extends BaseTest{
	private static Logger logger = LoggerFactory.getLogger(GatewayControllerTest.class);
//	private static String privateKey="33B4E81507802BBAC1064AB43919B832";//兴业银行商户号
//	private static String mchId="17091311000000";//兴业银行商户号
	private static String privateKey="98AA899E1C634AA29D81D3156B017ECD";//网商银行商户号
	private static String mchId="17092712000792";//网商银行商户号
	@Test
	public void microPay() {
		String resultString = null;
		String requestUrl = "http://127.0.0.1:8098/mgateway/gateway";
	 //   String requestUrl="https://www.chinagiftcard.cn/mgateway/gateway";
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		Map<String, String> data = new HashMap<String, String>(16);
		data.put("service", "unified.trade.micropay");//必传
		data.put("mchId", mchId);//必传
		data.put("signType", "MD5");
		data.put("returnType", "JSON");
		String out_trade_no ="alipay"+String.valueOf(DateUtil.getNowLongValue()) ;//必传
		data.put("outTradeNo", out_trade_no);
		logger.info("outTradeNo={}",data.get("outTradeNo"));
		data.put("deviceInfo", "1205872584455");
		data.put("body", "线下扫码付");//必传
		String jsonStr = "[{\"goods_id\":\"apple-01\",\"alipay_goods_id\":\"20010001\",\"goods_name\":\"ipad\",\"quantity\":1,\"price\":2000,\"goods_category\":\"34543238\",\"body\":\"特价手机\",\"show_url\":\"http://www.alipay.com/xxx.jpg\"}]";
	    data.put("goodsDetail", jsonStr);//必传	
		data.put("extendInfo", "test");
		data.put("totalAmount", "1");//必传
		data.put("reqIp", "127.0.0.1");//必传
		data.put("authCode", "280613777466877476");//必传
		data.put("timeStart", "20170920204900");//time_start与time_expire必须同时为空或不为空
		data.put("timeExpire", "20170929204910");
		data.put("operUser", "1251sadd8");
		data.put("merchantNo", "124a");
		data.put("goodTags", "test");
		data.put("payTypeLimit", "0");

		String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
		data.put("timestamp", timestamp);//必传
		Map<String, String> result = new HashMap<String, String>(data.size());      
		for (String key : data.keySet()) {
			String value = data.get(key);
			if (value == null || value.equals("") ) {
				continue;
			}
			result.put(key, value);
		}   
		List<String> values = new ArrayList<String>(result.values());
		Collections.sort(values);
		values.add(privateKey);	
		String[] str = values.toArray(new String[] {});
		logger.debug("生成签名的字符串:" + StringUtils.join(str));
		String sign = DigestUtils.md5Hex(StringUtils.join(str));		
		data.put("sign", sign);	
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
	/**
	 *线下退款
	 * 
	 *
	 */
	@Test
	public void testRefund() {
		String resultString = null;
	   // String  requestUrl="https://www.chinagiftcard.cn/mgateway/gateway";

		String requestUrl = "http://127.0.0.1:8098/mgateway/gateway";
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		Map<String, String> data = new HashMap<String, String>(16);	
		data.put("service","unified.trade.refund");
		data.put("signType", "MD5");
		data.put("returnType", "JSON");
		data.put("mchId", mchId);
		data.put("outTradeNo","alipay20170929150317");
		data.put("refundNo", UUID.randomUUID().toString().substring(15));
		data.put("refundAmount", "1");
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
		data.put("timestamp", timestamp);
		data.put("operUser", "7551000001");
		logger.info(data.get("refundNo"));
		Map<String, String> result = new HashMap<String, String>(data.size());      
		for (String key : data.keySet()) {
			String value = data.get(key);
			if (value == null || value.equals("") ) {
				continue;
			}
			result.put(key, value);
		}
		List<String> values = new ArrayList<String>(result.values());
		Collections.sort(values);
		values.add(privateKey);	
		String[] str = values.toArray(new String[] {});
		String sign = DigestUtils.md5Hex(StringUtils.join(str));		
		data.put("sign", sign);	
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

	//线下测试接口
	@Test
	public void testCancel() {
		String resultString = null;
	    //String  requestUrl="https://www.chinagiftcard.cn/mgateway/gateway";

		String requestUrl = "http://127.0.0.1:8098/mgateway/gateway";
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		Map<String, String> data = new HashMap<String, String>(16);	
		data.put("service","pay.trade.cancel");
		data.put("signType", "MD5");
		data.put("returnType", "JSON");
		data.put("mchId", mchId);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
		data.put("timestamp", timestamp);
		data.put("outTradeNo","alipay20170929143121");
		data.put("transactionId", "170929MYBANKhmokdlmbpdaneopkkb");
		Map<String, String> result = new HashMap<String, String>(data.size());      
		for (String key : data.keySet()) {
			String value = data.get(key);
			if (value == null || value.equals("") ) {
				continue;
			}
			result.put(key, value);
		}
		List<String> values = new ArrayList<String>(result.values());
		Collections.sort(values);
		values.add(privateKey);	
		String[] str = values.toArray(new String[] {});
		String sign = DigestUtils.md5Hex(StringUtils.join(str));		
		data.put("sign", sign);	
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

	//查询接口测试类
	@Test
	public void testQuery() {
		String resultString = null;
		String requestUrl = "http://127.0.0.1:8098/mgateway/gateway";
	 //   String  requestUrl="https://www.chinagiftcard.cn/mgateway/gateway";
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		Map<String, String> data = new HashMap<String, String>(16);	
		data.put("service","unified.trade.query");
		data.put("signType", "MD5");
		data.put("returnType", "JSON");
		data.put("mchId", mchId);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
		data.put("timestamp", timestamp);
		data.put("outTradeNo","alipay20170929141842");
		//data.put("transaction_id", "201709080043448A5B0F3C6B919128");
		Map<String, String> result = new HashMap<String, String>(data.size());      
		for (String key : data.keySet()) {
			String value = data.get(key);
			if (value == null || value.equals("") ) {
				continue;
			}
			result.put(key, value);
		}
		List<String> values = new ArrayList<String>(result.values());
		Collections.sort(values);
		values.add(privateKey);	
		String[] str = values.toArray(new String[] {});
		String sign = DigestUtils.md5Hex(StringUtils.join(str));		
		data.put("sign", sign);	
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
	//退款查询接口测试类
	@Test
	public void testRefundQuery() {
		String resultString = null;
	   // String  requestUrl="https://www.chinagiftcard.cn/mgateway/gateway";
	    String requestUrl = "http://127.0.0.1:8098/mgateway/gateway";
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		Map<String, String> data = new HashMap<String, String>(16);	
		data.put("service","unified.trade.refundQuery");
		data.put("signType", "MD5");
		data.put("returnType", "JSON");
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
		data.put("timestamp", timestamp);
		data.put("mchId", mchId);
		data.put("refundNo","15d-87c2-f80836f28680");
		//data.put("refund_id", "201709080043448A5B0F3C6B347843");
		Map<String, String> result = new HashMap<String, String>(data.size());      
		for (String key : data.keySet()) {
			String value = data.get(key);
			if (value == null || value.equals("") ) {
				continue;
			}
			result.put(key, value);
		}
		List<String> values = new ArrayList<String>(result.values());
		Collections.sort(values);
		values.add(privateKey);	
		String[] str = values.toArray(new String[] {});
		String sign = DigestUtils.md5Hex(StringUtils.join(str));		
		data.put("sign", sign);	
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
}
