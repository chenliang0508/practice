package cn.com.upcard.mgateway.channel.bank.cib.common;
/**
 * <pre>
 * 兴业银行订单状态
 * 
 * </pre>
 * @author huatingzhou
 *
 */

import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;

public enum CibTradeStatus {
	// 支付成功或退货成功
	SUCCESS("SUCCESS", ChannelTradeStatus.SUCCESS),
	// 转入退款，退款的转态是不确定的
	REFUND("REFUND", ChannelTradeStatus.CANCEL),
	// 订单已创建，未处理
	NOTPAY("NOTPAY", ChannelTradeStatus.WAITING),
	// 订单关闭
	CLOSED("CLOSED", ChannelTradeStatus.CLOSED),
	// 支付错误
	PAYERROR("PAYERROR", ChannelTradeStatus.FAILED),
	// 支付订单已撤销
	REVOK("REVOK", ChannelTradeStatus.CANCEL),
	// 等待用户付款
	USERPAYING("USERPAYING", ChannelTradeStatus.WAITING),
	// 退款失败
	FAIL("FAIL", ChannelTradeStatus.FAILED),
	/**
	 * 退款处理中
	 */
	PROCESSING("PROCESSING", ChannelTradeStatus.WAITING),
	/**
	 * 未确定, 需要商户原退款单号重新发起
	 */
	NOTSURE("NOTSURE", ChannelTradeStatus.FAILED),
	/**
	 * 转入代发,退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者平台转账的方式进行退款。
	 */
	CHANGE("CHANGE", ChannelTradeStatus.WAITING),
	// 冲正成功
	REVERSE("REVERSE", ChannelTradeStatus.CANCEL);

	private String name;
	private ChannelTradeStatus tradeStatus;

	private CibTradeStatus(String cibTradeStatusName, ChannelTradeStatus status) {
		this.name = cibTradeStatusName;
		this.tradeStatus = status;
	}

	public String getName() {
		return name;
	}

	public ChannelTradeStatus getTradeStatus() {
		return tradeStatus;
	}

	public static String getStatus(String status) {
		if (status == null || status.equals("")) {
			return ChannelTradeStatus.WAITING.name();
		}
		for (CibTradeStatus s : CibTradeStatus.values()) {
			if (s.name.equals(status)) {
				return s.getTradeStatus().name();
			}
		}
		return ChannelTradeStatus.WAITING.name();
	}
}
