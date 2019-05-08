package cn.com.upcard.mgateway.dao.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;

import cn.com.upcard.mgateway.dao.RedisDao;
@Component
public class RedisDaoImpl implements RedisDao{
	private static final Logger logger = LoggerFactory.getLogger(RedisDaoImpl.class);
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public String getKey(String keyName) {
		Assert.hasText(keyName, "key for redis get can not be null");
		
		return redisTemplate.opsForValue().get(keyName);
	}
	
	@Override
	public void setKey(String keyName, String value) {
		Assert.hasText(keyName, "key for redis set can not be null");
		Assert.hasText(value, "value for redis set can not be null");
		
		redisTemplate.opsForValue().set(keyName, value);
	}
	
	@Override
	public void setKey(String keyName, String value, long timeOut) {
		Assert.hasText(keyName, "key for redis set can not be null");
		Assert.hasText(value, "value for redis set can not be null");
		
		redisTemplate.opsForValue().set(keyName, value, timeOut, TimeUnit.SECONDS);
	}
	
	@Override
	public void setObject(String keyName, Object value) {
		Assert.notNull(value, "value for redis set can not be null");
		
		String valueString = null;
		if (value instanceof String) {
			valueString = (String)value;
		} else {
			valueString = JSONObject.toJSONString(value);
		}
		
		this.setKey(keyName, valueString);
	}
	
	@Override
	public void setObject(String keyName, Object value, long timeOutInSecond) {
		Assert.notNull(value, "value for redis set can not be null");
		
		String valueString = null;
		if (value instanceof String) {
			valueString = (String)value;
		} else {
			valueString = JSONObject.toJSONString(value);
		}
		
		this.setKey(keyName, valueString, timeOutInSecond);
	}
	
	@Override
	public <T> T getObject(String keyName, Class<T> clazz) {
		String value = this.getKey(keyName);
		
		if (value == null) {
			return null;
		}
		return JSONObject.parseObject(value, clazz);
	}
}
