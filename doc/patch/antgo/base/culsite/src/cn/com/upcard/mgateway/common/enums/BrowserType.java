package cn.com.upcard.mgateway.common.enums;

import org.apache.commons.lang3.StringUtils;
/**
 * <pre>
 * 浏览器类型
 * 
 * </pre>
 * @author huatingzhou
 * @version 1.0
 */
public enum BrowserType {
	ALIPAY("alipay", TradeType.ALI_JSAPI),
//	QQ("qq", null),
	WEIXIN("micromessenger", TradeType.WX_JSAPI);
	
	private String browserTypeSign;
	private TradeType tradeType;
	
	BrowserType(String browserTypeSign, TradeType type) {
		this.browserTypeSign = browserTypeSign;
		this.tradeType = type;
	}
	
	
	public String getBrowserTypeSign() {
		return browserTypeSign;
	}
	
	public TradeType getTradeType() {
		return tradeType;
	}
	
	/**
	 * <pre>
	 * 根据请求头里的user-agent判断浏览器类型.
	 * 
	 * </pre>
	 * @param userAgent
	 * @return
	 */
	public static BrowserType toBrowserType(String userAgent) {
		if (StringUtils.isEmpty(userAgent)) {
			return null;
		}
		
		for (BrowserType browser : BrowserType.values()) {
			if (userAgent.toLowerCase().indexOf(browser.browserTypeSign) != -1) {
				return browser;
			}
		}
		return null;
	}
}
