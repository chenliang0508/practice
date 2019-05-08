package cn.com.upcard.mgateway.channel.bank.cmbc.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {
	
	/**
	 * Base64加密
	 * @param parameter
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeBase64(String parameter) throws UnsupportedEncodingException {
		return Base64.encodeBase64String(parameter.getBytes("UTF-8"));
	}
	
	
	public static String decodeBase64(String parameter) throws UnsupportedEncodingException {
		return new String(Base64.decodeBase64(parameter.getBytes("UTF-8")));
	}
	
}
