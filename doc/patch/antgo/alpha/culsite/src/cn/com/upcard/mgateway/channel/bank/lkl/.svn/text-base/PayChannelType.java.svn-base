package cn.com.upcard.mgateway.channel.bank.lkl;

public enum PayChannelType {
	WECHAT,
	ALIPAY;
	
	public static PayChannelType toPayChannelType(String channelTypeName) {
		if (channelTypeName == null)
			return null;
		
		for (PayChannelType payChannelType : PayChannelType.values()) {
			if (payChannelType.name().equals(channelTypeName)) {
				return payChannelType;
			}
		}
		return null;
	}
}
