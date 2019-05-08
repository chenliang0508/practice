package cn.com.upcard.mgateway.channel.bank.mybank.common;

/**
 * 支付渠道类型
 * 
 * @author Haozm
 *
 */
public enum ChannelType {
	/**
	 * 微信支付
	 */
	WX,
	/**
	 * 支付宝
	 */
	ALI;

	public static String getChannelType(String tradeType) {
		String channelType = null;
		for (ChannelType chlType : ChannelType.values()) {
			if (null != tradeType && tradeType.substring(0, tradeType.indexOf("_")).equals(chlType.name())) {
				channelType = chlType.name();
			}
		}
		return channelType;
	}

}
