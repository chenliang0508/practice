package cn.com.upcard.mgateway.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 银行收银通道银行代码
 * 
 * @author chenliang
 * 
 */
public enum BankCode {
	CIB("CIB", "兴业银行"),
	MYBANK("MYBANK", "网商银行"),
	LKL("LKL", "拉卡拉"),
	CMBC("CMBC", "民生银行");
	private String code;
	private String name;

	private BankCode() {
	}

	private BankCode(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static BankCode toBankCode(String bankCodeName) {
		if (StringUtils.isEmpty(bankCodeName)) {
			return null;
		}
		
		for (BankCode bankCode : BankCode.values()) {
			if (bankCode.code.equals(bankCodeName)) {
				return bankCode;
			}
		}
		return null;
	}
}
