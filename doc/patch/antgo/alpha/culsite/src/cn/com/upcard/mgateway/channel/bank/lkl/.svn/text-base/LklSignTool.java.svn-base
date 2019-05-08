package cn.com.upcard.mgateway.channel.bank.lkl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LklSignTool {
	private static Logger logger = LoggerFactory.getLogger(LklSignTool.class);
	
	public static  String createRequestSign(String[] signFields, Map<String, String> request, String sercretKey) {
		StringBuffer buffer = new StringBuffer();
		StringBuffer s = new StringBuffer();
		for (String fieldName : signFields) {
			s.append(fieldName + "+");
			if(StringUtils.isEmpty(request.get(fieldName))) {
				buffer.append("");
				continue;
			}
			buffer.append(request.get(fieldName));
		}
		buffer.append(sercretKey);
		logger.info("signField:" + s.toString());
		logger.info("value:" + buffer.toString());
		String sign = Sha1.getSHA1(buffer.toString()).toLowerCase();
		logger.info("create sign :{}", sign);
		return sign;
	}
	
	public static boolean checkResponseSign(String[] signFields, Map<String, String> response, String sercretKey) {
		String signByResponse = LklSignTool.createRequestSign(signFields, response, sercretKey);
		String sign = response.get("MAC");
		if (StringUtils.isEmpty(sign) || !sign.equalsIgnoreCase(signByResponse)) {
			logger.warn("create sign by response:{}", signByResponse);
			return false;
		}
		return true;
	}
}
