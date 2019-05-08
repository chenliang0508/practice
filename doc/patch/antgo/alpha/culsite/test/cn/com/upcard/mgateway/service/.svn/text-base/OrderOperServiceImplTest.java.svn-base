package cn.com.upcard.mgateway.service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.com.upcard.mgateway.BaseTest;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.util.DateUtil;

public class OrderOperServiceImplTest extends BaseTest{
	private static Logger logger = LoggerFactory.getLogger(OrderOperServiceImplTest.class);
	private final static Lock lock = new ReentrantLock();  
	@Autowired
	private OrderOperService orderOperService;
	@Test
	public void testcreateOrder() {
		PreTradeOrderInfo preTradeOrderInfo = new PreTradeOrderInfo();
		preTradeOrderInfo.setCommUnionNo("123848468585");
		preTradeOrderInfo.setOutTradeNo("" + DateUtil.formatCompactDateTime(DateUtil.now()));
		TradeOrder order = orderOperService.createOrder(preTradeOrderInfo);
		logger.info(order.getId());
	}
//	/findOrderByTradeNo
	
	@Test
	public void testfindOrderByTradeNo() {
		TradeOrder order = orderOperService.findOrderByTradeNo("2017091500018097199710135902", true);
		logger.info(order.getId());
	}
	
	private Set<String> tradeNoSet = new HashSet<>();
	@Test
	public void testTradeNo() {
		int count = 100 * 10000;
		int i = 0;
		for(; i < count;) {
			if(!tradeNoSet.add(this.createTradeNo("17091512458698"))) {
				System.out.println("repeat");
			}
		}
	}
	
	
	private synchronized String createTradeNo(String commonUnionNo) {
		lock.lock();  
		try {   
			// yyyyMMdd + 商户号后四位 + 12位mac + 6位随机数
			String date = DateUtil.formatCompactDate(DateUtil.now());
			String commonNo = commonUnionNo;
			if (commonUnionNo.length() > 4) {
				commonNo = commonUnionNo.substring(commonUnionNo.length() - 4, commonUnionNo.length());
			}
			String random = RandomStringUtils.randomNumeric(6);
			
			String tradeNo = date + commonNo + "jhfwuieyf" + random;
			//logger.info("产生的系统流水号tradeNo：" + tradeNo);
			return tradeNo;
		}  
		finally {  
		  lock.unlock();   
		} 
	}
}
