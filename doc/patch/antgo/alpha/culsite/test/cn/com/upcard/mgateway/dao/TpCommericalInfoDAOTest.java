package cn.com.upcard.mgateway.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.upcard.mgateway.BaseTest;
import cn.com.upcard.mgateway.entity.TpCommericalInfo;

public class TpCommericalInfoDAOTest extends BaseTest{
	@Autowired
	private TpCommericalInfoDAO tpCommericalInfoDAO;
	@Test
	public void testFindByCulCommNoAndBankMerNoAndCommStatus() {
		TpCommericalInfo t = tpCommericalInfoDAO.findByCulCommNoAndBankMerNoAndCommStatus("17091912000558", "101530136432", "Y");
		System.out.println(t.getCommKey());
	}

}
