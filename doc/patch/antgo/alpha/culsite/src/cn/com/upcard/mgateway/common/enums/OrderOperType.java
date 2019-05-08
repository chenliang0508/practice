package cn.com.upcard.mgateway.common.enums;
/**
 * 订单操作类型
 * @author chenliang
 *
 */
public enum OrderOperType {
	/**
	 * 撤销
	 */
	CANCEL,
	/**
	 * 退款
	 */
	REFUND,
	/**
	 * 支付
	 */
	PAY,
	/**
	 * 关闭
	 */
	CLOSE
}
