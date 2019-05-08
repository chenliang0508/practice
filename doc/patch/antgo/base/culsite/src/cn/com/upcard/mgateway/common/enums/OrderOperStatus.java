package cn.com.upcard.mgateway.common.enums;
/**
 * 订单操作请求状态
 * @author chenliang
 *
 */
public enum OrderOperStatus {
	/**
	 * 初始化
	 */
	INIT,
	/**
	 * 已提交
	 */
	COMMIT,
	/**
	 * 无应答
	 */
	NON_RESP,
	/**
	 * 已应答
	 */
	RESP
}
