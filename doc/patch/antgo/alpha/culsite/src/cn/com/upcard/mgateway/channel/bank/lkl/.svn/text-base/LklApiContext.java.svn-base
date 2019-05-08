package cn.com.upcard.mgateway.channel.bank.lkl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.common.enums.BankCode;
import cn.com.upcard.mgateway.common.enums.BankOrderNoLength;
import cn.com.upcard.mgateway.util.RandomStringCreator;

public class LklApiContext {
	private static final Logger logger = LoggerFactory.getLogger(LklApiContext.class);
	private static final String DEFAULT_ENCODING = "GBK";

	public static String getDefaultEncoding() {
		return DEFAULT_ENCODING;
	}
	
	public static String generateReqLogNo() {
		int randomLength = BankOrderNoLength.getOrderLength(BankCode.LKL) - BankCode.LKL.getCode().length();
		String reqLogNo = BankCode.LKL.getCode() + RandomStringCreator.generateString(randomLength);
		logger.info("reqLogNo:{}", reqLogNo);
		return reqLogNo;
	}
}
