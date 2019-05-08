package cn.com.upcard.mgateway.channel.bank.lkl;
/**
 * <pre>
 * 兴业银行订单状态
 * 
 * </pre>
 * @author huatingzhou
 *
 */

import org.apache.commons.lang3.StringUtils;

import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;

public enum LklTradeStatus {
	//支付成功或退货成功
	SUCCESS("SUCCESS", ChannelTradeStatus.SUCCESS),
	//转入退款，退款的转态是不确定的
	REFUND("REFUND", ChannelTradeStatus.CANCEL),
	//订单已创建，未处理
	NOTPAY("NOTPAY", ChannelTradeStatus.WAITING),
	//订单关闭
	CLOSED("CLOSED", ChannelTradeStatus.CLOSED),
	//支付订单已撤销
	REVOKED("REVOKED", ChannelTradeStatus.CANCEL),
	//等待用户付款
	USERPAYING("USERPAYING", ChannelTradeStatus.WAITING),
	//支付错误
	PAYERROR("PAYERROR", ChannelTradeStatus.FAILED);
	private String name;
	private ChannelTradeStatus tradeStatus;
	
	private LklTradeStatus(String cibTradeStatusName, ChannelTradeStatus status) {
		this.name = cibTradeStatusName;
		this.tradeStatus = status;
	}
	
	public String getName() {
		return name;
	}

	public ChannelTradeStatus getTradeStatus() {
		return tradeStatus;
	}

	public static LklTradeStatus toLklTradeStatus(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		
		for (LklTradeStatus s : LklTradeStatus.values()) {
			if (s.name.equals(name)) {
				return s;
			}
		}
		return null;
	}
}
