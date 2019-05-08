package cn.com.upcard.mgateway.channel.bank.lkl;

import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;

public interface LKLNativeTradeOper{
	ChannelResponseInfo prePay(ChannelRequestInfo channelRequestInfo);

	ChannelResponseInfo tradeQuery(ChannelRequestInfo channelRequestInfo);

	ChannelResponseInfo refund(ChannelRequestInfo channelRequestInfo);

	ChannelResponseInfo refundQuery(ChannelRequestInfo channelRequestInfo);

	ChannelResponseInfo close(ChannelRequestInfo channelRequestInfo);
}
