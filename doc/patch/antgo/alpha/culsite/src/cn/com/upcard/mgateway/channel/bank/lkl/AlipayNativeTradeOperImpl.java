package cn.com.upcard.mgateway.channel.bank.lkl;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.exception.GatewayBusinessException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;

public class AlipayNativeTradeOperImpl extends LKLPayTradeOperDefaultImpl
		implements LKLNativeTradeOper, ThirdTradeOper {
	
	public ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo) {
		channelRequestInfo.setChannelType(TradeType.NATIVE.name());
		channelRequestInfo.setPayType(PayChannelType.ALIPAY.name());
		return super.prePay(channelRequestInfo);
	}

	public ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo) {
		throw new GatewayBusinessException("unsupported service");
	}

	public ChannelResponseInfo tradeQuery(ChannelRequestInfo channelRequestInfo) {
		channelRequestInfo.setChannelType(TradeType.NATIVE.name());
		channelRequestInfo.setPayType(PayChannelType.ALIPAY.name());
		return super.tradeQuery(channelRequestInfo);
	}

	public ChannelResponseInfo refund(ChannelRequestInfo channelRequestInfo) {
		channelRequestInfo.setChannelType(TradeType.NATIVE.name());
		channelRequestInfo.setPayType(PayChannelType.ALIPAY.name());
		return super.refund(channelRequestInfo);
	}

	public ChannelResponseInfo refundQuery(ChannelRequestInfo channelRequestInfo) {
		channelRequestInfo.setChannelType(TradeType.NATIVE.name());
		channelRequestInfo.setPayType(PayChannelType.ALIPAY.name());
		return super.refundQuery(channelRequestInfo);
	}

	public ChannelResponseInfo close(ChannelRequestInfo channelRequestInfo) {
		channelRequestInfo.setChannelType(TradeType.NATIVE.name());
		channelRequestInfo.setPayType(PayChannelType.ALIPAY.name());
		return super.close(channelRequestInfo);
	}
}
