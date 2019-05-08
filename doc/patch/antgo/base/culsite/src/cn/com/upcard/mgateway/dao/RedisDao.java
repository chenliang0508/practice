package cn.com.upcard.mgateway.dao;

public interface RedisDao {
	public String getKey(String keyName);
	
	public void setKey(String keyName, String value);
	
	public void setKey(String keyName, String value, long timeOut);
	
	public void setObject(String keyName, Object value);
	
	public <T> T getObject(String keyName, Class<T> clazz);
	
	public void setObject(String keyName, Object value, long timeOutInSecond);
}
