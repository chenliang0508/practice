package cn.com.upcard.mgateway.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.upcard.mgateway.BaseTest;

public class TpAcquireTxnPriorityDAOTest extends BaseTest{
	@Autowired
	private TpAcquireTxnPriorityDAO tpAcquireTxnPriorityDAO;
	@Test
	public void testFindByPriorityNumAndPayTypeCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByPayTypeCodeAndChannelIdInOrderByPriorityNumDesc() {
		List<String> channelIds = new ArrayList<String>();
		channelIds.add("00001");
		channelIds.add("00002");
		channelIds.add("00003");
		channelIds.add("00004");
		tpAcquireTxnPriorityDAO.findTopByPayTypeCodeAndAcquirerIdInOrderByPriorityNum("WX_JSAPI", channelIds);
	}

}
