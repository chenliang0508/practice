package cn.com.upcard.mgateway.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.upcard.mgateway.BaseTest;
import cn.com.upcard.mgateway.dao.TradeOrderDAO;


public class TradeOrderDAOTest extends BaseTest {

	@Autowired
	private TradeOrderDAO tradeOrderDAO;
	
	@Test
	public void testFindByTradeNoForUpdate() {
		System.out.println("begin.........");
		String tradeNo = "2017091400014CCC6A537A7F950105";
		tradeOrderDAO.findByTradeNoForUpdate(tradeNo);
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println("end..........");
	}
}
