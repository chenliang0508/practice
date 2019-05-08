package cn.com.upcard.mgateway.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.BaseTest;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.HttpClient;


public class TradeQueryTimerTaskTest  extends BaseTest {
	private static Logger logger = LoggerFactory.getLogger(TradeQueryTimerTaskTest.class);
	private static final String ENCODING = "UTF-8";
	private static final String URL = "http://127.0.0.1:8098/mgateway/timer/tradeQuery";
    @Test 
    public void payResultQuery() throws Exception{
    	Date startTime = DateUtil.getBeforeMinuteTime(60, DateUtil.DATE_TIME_FORMATTER_STRING);
		Date endtime = DateUtil.getBeforeMinuteTime(30, DateUtil.DATE_TIME_FORMATTER_STRING); 

		Map<String, String> data = new HashMap<String, String>(3);
		data.put("startTime", DateFormatUtils.format(startTime, "yyyyMMddHHmmss"));
		data.put("endTime", DateFormatUtils.format(endtime, "yyyyMMddHHmmss"));
		logger.info("data = {}", data);

		HttpClient client = new HttpClient(URL, 3000, 3000);
		client.send(data, ENCODING);
		String result = client.getResult();

		logger.info("payResultQuery result = {}", result);
    }

}
