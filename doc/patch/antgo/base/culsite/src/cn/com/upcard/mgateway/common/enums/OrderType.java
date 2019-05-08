package cn.com.upcard.mgateway.common.enums;

public enum OrderType {
	/**
	 * 支付
	 */
	PAY,
	/**
	 * 退款
	 */
	REFUND;
	
	public static OrderType getType(String orderType) {
		try {
			return OrderType.valueOf(orderType);
		} catch (IllegalArgumentException e) {
			
			return null;
		}
	}
}
