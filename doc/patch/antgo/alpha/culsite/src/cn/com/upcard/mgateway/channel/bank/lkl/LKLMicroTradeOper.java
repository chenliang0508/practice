package cn.com.upcard.mgateway.channel.bank.lkl;

import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;

public interface LKLMicroTradeOper {
	ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo);

	ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo);

	ChannelResponseInfo tradeQuery(ChannelRequestInfo channelRequestInfo);

	ChannelResponseInfo refund(ChannelRequestInfo channelRequestInfo);

	ChannelResponseInfo refundQuery(ChannelRequestInfo channelRequestInfo);
}
