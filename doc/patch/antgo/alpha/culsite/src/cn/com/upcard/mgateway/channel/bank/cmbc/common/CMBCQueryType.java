package cn.com.upcard.mgateway.channel.bank.cmbc.common;

public enum CMBCQueryType {

	PAY("1"), REFUND("2");

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	CMBCQueryType(String code) {
		this.code = code;
	}
}
