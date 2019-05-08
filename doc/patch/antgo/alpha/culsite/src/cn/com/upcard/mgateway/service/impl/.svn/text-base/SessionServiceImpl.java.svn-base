package cn.com.upcard.mgateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.upcard.mgateway.common.RedisNamespaceConstants;
import cn.com.upcard.mgateway.controller.request.QrCodeOrderPayParam;
import cn.com.upcard.mgateway.dao.RedisDao;
import cn.com.upcard.mgateway.service.SessionService;

@Service
public class SessionServiceImpl  implements SessionService{
	//session默认过期时间30分钟
	private static final long DEFAULT_TIMEOUT = 60 * 30;
	@Autowired
	private RedisDao redisDao;
	
	private void saveSessionInfo(String key, Object sessionInfo) {
		redisDao.setObject(key, sessionInfo, DEFAULT_TIMEOUT);
	}
	
	private <T> T getSessionInfo(String key, Class<T> clazz) {
		return redisDao.getObject(key, clazz);
	}
	
	public void saveQrSessionInfo(String uuid, Object sessionInfo) {
		String key = RedisNamespaceConstants.QR_SESSION_KEYNAME.replace("{uuid}", uuid);
		this.saveSessionInfo(key, sessionInfo);
	}
	
	public QrCodeOrderPayParam getQySessionInfo(String uuid) {
		String key = RedisNamespaceConstants.QR_SESSION_KEYNAME.replace("{uuid}", uuid);
		return this.getSessionInfo(key, QrCodeOrderPayParam.class);
	}
}
