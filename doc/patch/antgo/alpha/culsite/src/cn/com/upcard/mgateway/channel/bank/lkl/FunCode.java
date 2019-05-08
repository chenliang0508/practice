package cn.com.upcard.mgateway.channel.bank.lkl;

import cn.com.upcard.mgateway.util.systemparam.SysPara;

/**
 * <pre>
 * 拉卡拉收单机构功能码，标识不同的交易类型
 * 
 * </pre>
 * @author huatingzhou
 * @version 1.0
 */
public enum FunCode {
	MICRO_PAY(8001),
	MICRO_CANCEL(8002),
	NATIVE_PAY(8011),
	NATIVE_CLOSE(8012),
	QUERY(8021),
	REFUND(8031),
	REFUND_QUERY(8032),
	PAY_NOTIFY(8101);
	
	private int funCode;
	
	public int getFunCode() {
		return funCode;
	}

	private FunCode(int funCode) {
		this.funCode = funCode;
	}
	
	public String getFunRequestUrl() {
		return SysPara.LKL_REQ_URL + "/" + this.funCode + ".dor";
	}
}
