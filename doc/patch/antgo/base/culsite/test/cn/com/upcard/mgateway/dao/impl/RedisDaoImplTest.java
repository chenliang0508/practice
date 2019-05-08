package cn.com.upcard.mgateway.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.upcard.mgateway.BaseTest;
import cn.com.upcard.mgateway.dao.RedisDao;

public class RedisDaoImplTest extends BaseTest{
	@Autowired
	private RedisDao redisDao;
	@Test
	public void testGetKey() {
		System.out.println(redisDao.getKey("culsite:wechat:infoByAll:{para}"));
	}
	
	@Test
	public void testSetKey() {
		redisDao.setKey("test", "huatingzhou");
	}
}
