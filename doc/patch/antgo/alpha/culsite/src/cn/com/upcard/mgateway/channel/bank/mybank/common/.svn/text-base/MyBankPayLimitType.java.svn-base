package cn.com.upcard.mgateway.channel.bank.mybank.common;

import org.apache.commons.lang3.StringUtils;

import cn.com.upcard.mgateway.common.enums.PayLimitType;

public enum MyBankPayLimitType {

	/**
	 * 默认
	 */
	DEFAULT("", null),
	/**
	 * 禁用信用卡
	 */
	CREDIT_CARD("credit", PayLimitType.CREDIT_CARD);
	// pcredit：花呗（仅支付宝）
	/**
	 * 花呗（仅支付宝）
	 */
//	PCREDIT("pcredit", PayLimitType.PCREDIT);
	private PayLimitType payLimitType;
	private String limitType;

	public PayLimitType getPayLimitType() {
		return payLimitType;
	}

	public void setPayLimitType(PayLimitType payLimitType) {
		this.payLimitType = payLimitType;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	private MyBankPayLimitType() {
	}

	private MyBankPayLimitType(String limitType, PayLimitType payLimitType) {
		this.limitType = limitType;
		this.payLimitType = payLimitType;
	}

	public static String toMyBankPayLimitType(PayLimitType payLimitType) {
		if (payLimitType == null) {
			return DEFAULT.getLimitType();
		}
		String limitType = null;
		for (MyBankPayLimitType type : MyBankPayLimitType.values()) {
			if (type.getPayLimitType() == payLimitType) {
				limitType = limitType + type.getLimitType() + ",";
			}
		}
		if (StringUtils.isEmpty(limitType))
			return DEFAULT.getLimitType();

		return DEFAULT.getLimitType();
	}
}
