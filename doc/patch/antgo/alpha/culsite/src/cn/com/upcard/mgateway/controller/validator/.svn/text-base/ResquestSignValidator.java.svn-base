package cn.com.upcard.mgateway.controller.validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import cn.com.upcard.mgateway.controller.response.BaseResponse;

public class ResquestSignValidator {
	private static final Logger logger = LoggerFactory.getLogger(ResquestSignValidator.class);
	
	public static Object createSign(Object param, String privateKey) {
		if(StringUtils.isEmpty(privateKey)) {
			logger.info("commkey is null");
			return param;
		}
		if(!(param instanceof BaseResponse)) {
			logger.info("不支持的加密对象");
			return null;
		}
//		@SuppressWarnings("unused")
		List<Field> fieldList = getFields(param);
		if (CollectionUtils.isEmpty(fieldList)) {
			logger.info("没有待加密的字段");
			return null;
		}
		BaseResponse baseResponse = (BaseResponse) param;
		List<String> fieldsToCheck = new ArrayList<String>();
		for (Field field : fieldList) {
			field.setAccessible(true);
			Object obj;
			try {
				obj = field.get(param);
				if (obj != null && obj instanceof String) {
					String fieldName = field.getName();
					if ("sign".equalsIgnoreCase(fieldName)) {
						continue;
					}
					fieldsToCheck.add(obj.toString());
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
		Collections.sort(fieldsToCheck);
		fieldsToCheck.add(privateKey);
		String[] str = fieldsToCheck.toArray(new String[] {});
		logger.debug("加密前的字符串:" + StringUtils.join(str));
		String sign = DigestUtils.md5Hex(StringUtils.join(str));
		logger.debug("返回参数的签名sign:" + sign);
		return baseResponse;
	}
	
	public static String createRequestSign(Object param, String privateKey) {
		logger.debug("检查使用注解的接口的sign签名");
		List<Field> fieldList = getFields(param);
		if (CollectionUtils.isEmpty(fieldList)) {
			logger.info("验证签名失败");
			return "";
		}
		List<String> fieldsToCheck = new ArrayList<String>();
		for (Field field : fieldList) {
			field.setAccessible(true);
			Object obj;
			try {
				obj = field.get(param);
				if (obj != null && obj instanceof String) {
					String fieldName = field.getName();
					if ("sign".equalsIgnoreCase(fieldName)) {
						continue;
					}
					fieldsToCheck.add(obj.toString());
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new RuntimeException(field.getName() + "is not a field of " + param.getClass().getName());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(field.getName() + "is static , but it has not already been initialized");
			}
		}
		Collections.sort(fieldsToCheck);
		fieldsToCheck.add(privateKey);
		String[] str = fieldsToCheck.toArray(new String[] {});
		logger.debug("加密前的字符串:" + StringUtils.join(str));
		return DigestUtils.md5Hex(StringUtils.join(str));
	}
	
	public static boolean checkUseAnnotionSign(Object param, String privateKey) {
		logger.debug("检查使用注解的接口的sign签名");
		List<Field> fieldList = getFields(param);
		if (CollectionUtils.isEmpty(fieldList)) {
			logger.info("验证签名失败");
			return false;
		}
		String clientSign = null;
		List<String> fieldsToCheck = new ArrayList<String>();
		for (Field field : fieldList) {
			field.setAccessible(true);
			Object obj;
			try {
				obj = field.get(param);
				if (obj != null && obj instanceof String) {
					String fieldName = field.getName();
					if ("sign".equalsIgnoreCase(fieldName)) {
						clientSign = (String) obj;
						continue;
					}
					fieldsToCheck.add(obj.toString());
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
		}
		Collections.sort(fieldsToCheck);
		fieldsToCheck.add(privateKey);
		String[] str = fieldsToCheck.toArray(new String[] {});
		logger.debug("加密前的字符串:" + StringUtils.join(str));
		String sign = DigestUtils.md5Hex(StringUtils.join(str));
		if (sign.equalsIgnoreCase(clientSign)) {
			return true;
		}
		// 验签失败
		logger.info("签名错误,server sign:" + sign);
		return false;
	}

	private static List<Field> getFields(Object param) {
		if (param == null) {
			return null;
		}
		List<Field> fieldList = new LinkedList<Field>();
		if (param.getClass().getSuperclass() != Object.class) {
			Field[] superFileds = param.getClass().getSuperclass().getDeclaredFields();
			fieldList.addAll(Arrays.asList(superFileds));
		}
		Field[] childField = param.getClass().getDeclaredFields();
		fieldList.addAll(Arrays.asList(childField));
		return fieldList;
	}
	
	public static boolean checkCustomSign(String[] params, String privateKey, String clientSign) {
		String sign = genSign(params, privateKey);
		if (sign.equalsIgnoreCase(clientSign)) {
			return true;
		}
		// 验签失败
		logger.info("签名错误,server sign:" + sign);
		return false;
	}
	
	public static String genSign(String[] params, String privateKey) {
		List<String> paraList = new ArrayList<String>(Arrays.asList(params));
		Collections.sort(paraList);
		paraList.add(privateKey);
		
		logger.debug("加密前的字符串:" + paraList);
		return DigestUtils.md5Hex(StringUtils.join(paraList.toArray(new String[0])));
	}
}
