package cn.com.upcard.mgateway.common.enums;

import org.apache.commons.lang3.StringUtils;

public enum TradeType {
	/**
	 * 微信app支付
	 */
	WX_APP,
	/**
	 * 微信公众号支付
	 */
	WX_JSAPI,
	/**
	 * 微信原生扫码支付
	 */
	WX_NATIVE,
	/**
	 * 微信线下扫码支付
	 */
	WX_MICROPAY,
	/**
	 * 支付宝当面付
	 */
	ALI_DIRECT,
	/**
	 * 支付宝在线支付
	 */
	ALI_NATIVE,
	/**
	 * 支付宝服务窗
	 */
	ALI_JSAPI;
	public static TradeType toTradeType(String tradeTypeName) {
		if (StringUtils.isEmpty(tradeTypeName)) {
			return null;
		}
		for (TradeType tradeType : TradeType.values()) {
			if (tradeType.name().equals(tradeTypeName)) {
				return tradeType;
			}
		}
		return null;
	}
}
