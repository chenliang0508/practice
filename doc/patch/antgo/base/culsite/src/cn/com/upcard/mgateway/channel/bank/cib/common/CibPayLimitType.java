package cn.com.upcard.mgateway.channel.bank.cib.common;

import cn.com.upcard.mgateway.common.enums.PayLimitType;

public enum CibPayLimitType {
	/**
	 * 默认支持所有
	 */
	DEFAULT("0", null), 
	/**
	 * 	禁用信用卡
	 */
	CREDIT_CARD("1", PayLimitType.CREDIT_CARD);
	
	private String limitType;
	private PayLimitType payLimitType;
	
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

	private CibPayLimitType() {
	}

	private CibPayLimitType(String limitType, PayLimitType payLimitType) {
		this.limitType = limitType;
		this.payLimitType = payLimitType;
	}
	
	public static String toCibPayLimitType(PayLimitType payLimitType) {
		if(payLimitType == null) {
			return DEFAULT.getLimitType();
		}
		for(CibPayLimitType type : CibPayLimitType.values()) {
			if(type.getPayLimitType() == payLimitType) {
				return type.getLimitType();
			}
		}
		return DEFAULT.getLimitType();
	}
}
