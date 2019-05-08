package cn.com.upcard.mgateway.channel.bank.lkl;

import org.apache.commons.lang3.StringUtils;

public enum TradeType {
	JSAPI,
	NATIVE,
	APP;
	
	public static TradeType toTradeType(String tradeTypeName) {
		if (StringUtils.isEmpty(tradeTypeName)) {
			return null;
		}
		
		for (TradeType type : TradeType.values()) {
			if (type.name().equals(tradeTypeName)) {
				return type;
			}
		}
		return null;
	}
}
