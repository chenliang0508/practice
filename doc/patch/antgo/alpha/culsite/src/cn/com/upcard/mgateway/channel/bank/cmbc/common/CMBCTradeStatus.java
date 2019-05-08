package cn.com.upcard.mgateway.channel.bank.cmbc.common;

import org.apache.commons.lang3.StringUtils;

import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;

public enum CMBCTradeStatus {
	
	//支付成功或退货成功
	SUCCESS("S", ChannelTradeStatus.SUCCESS),
	//转入退款，退款的转态是不确定的
	REFUND("T", ChannelTradeStatus.SUCCESS),
	//订单已创建，未处理
	NOTPAY("R", ChannelTradeStatus.WAITING),
	//支付订单已撤销
	REVOK("C", ChannelTradeStatus.CANCEL),
	//订单失败
	FAILED("E",ChannelTradeStatus.FAILED);
	
	private String name;
	private ChannelTradeStatus tradeStatus;
	
	private CMBCTradeStatus(String cibTradeStatusName, ChannelTradeStatus status) {
		this.name = cibTradeStatusName;
		this.tradeStatus = status;
	}
	
	public String getName() {
		return name;
	}

	public ChannelTradeStatus getTradeStatus() {
		return tradeStatus;
	}

	public static CMBCTradeStatus toCMBCTradeStatus(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		
		for (CMBCTradeStatus s : CMBCTradeStatus.values()) {
			if (s.name.equals(name)) {
				return s;
			}
		}
		return null;
	}
}
