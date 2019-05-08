package cn.com.upcard.mgateway.async.notify.model;

import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;

public class OrderProcessingResult {

	private String result; // success：不需要渠道方再次推送；fail：需要再次推送
	private boolean needPush; // true：需要推送给商户；false：不需要推送
	private TradeOrder tradeOrder;
	private ChannelResponseInfo channelResponseInfo;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public TradeOrder getTradeOrder() {
		return tradeOrder;
	}

	public void setTradeOrder(TradeOrder tradeOrder) {
		this.tradeOrder = tradeOrder;
	}

	public ChannelResponseInfo getChannelResponseInfo() {
		return channelResponseInfo;
	}

	public void setChannelResponseInfo(ChannelResponseInfo channelResponseInfo) {
		this.channelResponseInfo = channelResponseInfo;
	}

	public boolean isNeedPush() {
		return needPush;
	}

	public void setNeedPush(boolean needPush) {
		this.needPush = needPush;
	}

}
