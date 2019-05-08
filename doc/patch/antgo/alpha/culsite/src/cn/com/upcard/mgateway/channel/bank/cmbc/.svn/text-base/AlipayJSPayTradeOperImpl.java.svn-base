package cn.com.upcard.mgateway.channel.bank.cmbc;

import java.util.HashMap;
import java.util.Map;

import cn.com.upcard.mgateway.channel.bank.cmbc.common.CMBCPayType;
import cn.com.upcard.mgateway.exception.UnSupportApiException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class AlipayJSPayTradeOperImpl extends AbstractUnifiedOper{
	private static final String DATE_YYYYMMDDHHMMSSSSS="yyyyMMddHHmmssSSS";
	
	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo) {
		//支付宝服务窗
		Map<String, String> map = new HashMap<String, String>();
		map.put("platformId", SysPara.CMBC_PLATFORMID);
		map.put("merchantNo", channelRequestInfo.getMchId());
		map.put("selectTradeType", CMBCPayType.H5_ZFBJSAPI.name());
		map.put("amount", channelRequestInfo.getTotalFee());
		map.put("orderInfo", channelRequestInfo.getBody());
		map.put("merchantSeq", channelRequestInfo.getOutTradeNo());
		map.put("transDate", DateUtil.formatCompactDate(DateUtil.now()));
		map.put("transTime", DateUtil.format2(DateUtil.now(), DATE_YYYYMMDDHHMMSSSSS));
		map.put("notifyUrl", channelRequestInfo.getNotifyUrl());
		map.put("remark", channelRequestInfo.getExtendInfo());
		map.put("userId", channelRequestInfo.getThirdBuyerId());
		return super.trade(map);
		
	}
	
	@Override
	public ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo) {
		throw new UnSupportApiException("原支付方式不支持撤销操作");
	}

}
