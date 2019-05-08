package cn.com.upcard.mgateway.channel.bank.cmbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.exception.UndefinedTradeTypeException;

public class CMBCTradeFactory {
	private static Logger logger = LoggerFactory.getLogger(CMBCTradeFactory.class);

	public static ThirdTradeOper produce(String tradeType) {
		logger.info("tradeType:" + tradeType );
		if (TradeType.ALI_NATIVE.name().equals(tradeType)) {
			
			return  new AlipayNativeTradeOperImpl();
		}
		if (TradeType.ALI_JSAPI.name().equals(tradeType)) {
			return new AlipayJSPayTradeOperImpl();
		}
		if (TradeType.ALI_DIRECT.name().equals(tradeType)) {
			return new AliDirectTradeOperImpl();
		}
		if (TradeType.WX_MICROPAY.name().equals(tradeType) ) {
			return new WxMicroPayTradeOperImpl();
		}
		
		if (TradeType.WX_NATIVE.name().equals(tradeType)) {
			return new WxNativeTradeOperImpl();
		}
		if (TradeType.WX_JSAPI.name().equals(tradeType)) {
			return new WxJsTradeOperImpl();
		}
		throw new UndefinedTradeTypeException("invalid tradeType.");
	}
	
}
