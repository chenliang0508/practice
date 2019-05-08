package cn.com.upcard.mgateway.common;

/**
 * 统一管理redis的key的命名空间
 * @author huatingzhou
 * @version 1.0
 */
public class RedisNamespaceConstants {
	/**
	 * <pre>
	 * 静态二维码回话redis的key
	 * </pre>
	 */
	public static final String QR_SESSION_KEYNAME = "mgateway:qr:{uuid}";
	
	/**
	 * <pre>
	 * 微信接口令牌的redis的key
	 * </pre>
	 */
	public static final String MGATEWAY_ACCESS_TOKEN_KEYNAME = "vcweixin:ACCESSTOKEN";
}
