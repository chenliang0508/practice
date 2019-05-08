package cn.com.upcard.mgateway.util.systemparam;

import cn.com.upcard.mgateway.common.enums.BankCode;

public class NotifyUrlInit {
	public static String createNotifyUrl(String bankCodeName) {
		BankCode bankCode = BankCode.toBankCode(bankCodeName);
		if (bankCode == null) {
			return null;
		}
		
		StringBuffer buf = new StringBuffer(SysPara.MGATEWAY_HOST);
		buf.append("/callback");
		buf.append("/").append(bankCode.getCode().toLowerCase());
		return buf.toString();
	}
}
