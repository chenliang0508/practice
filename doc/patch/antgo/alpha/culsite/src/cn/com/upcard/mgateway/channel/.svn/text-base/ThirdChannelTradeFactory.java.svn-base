package cn.com.upcard.mgateway.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.bank.cib.MicroPayTradeOperImpl;
import cn.com.upcard.mgateway.channel.bank.cib.WxJsTradeOperImpl;
import cn.com.upcard.mgateway.channel.bank.cib.WxNativeTradeOperImpl;
import cn.com.upcard.mgateway.channel.bank.cmbc.CMBCTradeFactory;
import cn.com.upcard.mgateway.channel.bank.mybank.AlipayJSPayTradeOperImpl;
import cn.com.upcard.mgateway.channel.bank.mybank.ScanPayTradeOperImpl;
import cn.com.upcard.mgateway.common.enums.BankCode;
import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.exception.UndefinedTradeTypeException;


public class ThirdChannelTradeFactory {
	private static Logger logger = LoggerFactory.getLogger(ThirdChannelTradeFactory.class);

	public static ThirdTradeOper produce(String tradeType, String bankCode) {
		logger.info("tradeType:" + tradeType + ",bankCode:" + bankCode);
		if (TradeType.ALI_NATIVE.name().equals(tradeType) && BankCode.CIB.getCode().equals(bankCode)) {
			// 兴业的支付宝C扫B
			logger.info("兴业的支付宝C扫B");
			return new cn.com.upcard.mgateway.channel.bank.cib.AlipayNativeTradeOperImpl();
		}

		if (TradeType.ALI_JSAPI.name().equals(tradeType) && BankCode.CIB.getCode().equals(bankCode)) {
			// 兴业的支付宝C扫B
			logger.info("兴业的支付宝服务窗jsapi");
			return new cn.com.upcard.mgateway.channel.bank.cib.AlipayJSPayTradeOperImpl();
		}

		if (TradeType.ALI_DIRECT.name().equals(tradeType) && BankCode.CIB.getCode().equals(bankCode)
				|| TradeType.WX_MICROPAY.name().equals(tradeType) && BankCode.CIB.getCode().equals(bankCode)) {
			return new MicroPayTradeOperImpl();
		}
		
		if (TradeType.ALI_DIRECT.name().equals(tradeType) && BankCode.MYBANK.getCode().equals(bankCode)
				|| TradeType.WX_MICROPAY.name().equals(tradeType) && BankCode.MYBANK.getCode().equals(bankCode)) {
			return new ScanPayTradeOperImpl();
		}		
        
		if (TradeType.WX_NATIVE.name().equals(tradeType) && BankCode.CIB.getCode().equals(bankCode)) {
			return new WxNativeTradeOperImpl();
		}
		
		if (TradeType.WX_JSAPI.name().equals(tradeType) && BankCode.CIB.getCode().equals(bankCode)) {
			return new WxJsTradeOperImpl();
		}
		if (BankCode.CMBC.getCode().equals(bankCode)) {
			return CMBCTradeFactory.produce(tradeType);
		}
		
		if (TradeType.ALI_JSAPI.name().equals(tradeType) && BankCode.MYBANK.getCode().equals(bankCode)) {
			return new AlipayJSPayTradeOperImpl();
		}
		if (TradeType.WX_JSAPI.name().equals(tradeType) && BankCode.MYBANK.getCode().equals(bankCode)) {
			return new cn.com.upcard.mgateway.channel.bank.mybank.WxJsTradeOperImpl();
		}

		throw new UndefinedTradeTypeException("invalid tradeType or bankCode.");
	}
}
