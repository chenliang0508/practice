package cn.com.upcard.mgateway.channel.bank.cib.common;

public enum CibServiceType {

	// 线下刷卡支付,支付接口类型
	MICROPAY_PAY_SERVICE("unified.trade.micropay"),
	

	// 微信扫码支付相关
	// 支付
	WX_NATIVE_PAY_SERVICE("pay.weixin.native"),
	// 查询
	WX_NATIVE_QUERY_SERVICE("unified.trade.query"),
	// 退货
	WX_NATIVE_REFUND_SERVICE("unified.trade.refund"),
	// 退货查询
	WX_NATIVE_REFUND_QUERY_SERVICE("unified.trade.refundquery"),
	// 关单
	WX_NATIVE_CLOSE_SERVICE("unified.trade.close"),

	// 微信js支付相关
	// 支付
	WX_JS_PAY_SERVICE("pay.weixin.jspay"),
	// 查询
	WX_JS_QUERY_SERVICE("unified.trade.query"),
	// 退货
	WX_JS_REFUND_SERVICE("unified.trade.refund"),
	// 退货查询
	WX_JS_REFUND_QUERY_SERVICE("unified.trade.refundquery"),
	// 关单
	WX_JS_CLOSE_SERVICE("unified.trade.close"),
	
	// 微信APP支付相关
	// 支付
	WX_APP_PAY_SERVICE("pay.weixin.jspay"),
	// 查询
	WX_APP_QUERY_SERVICE("unified.trade.query"),
	// 退货
	WX_APP_REFUND_SERVICE("unified.trade.refund"),
	// 退货查询
	WX_APP_REFUND_QUERY_SERVICE("unified.trade.refundquery"),
	// 关单
	WX_APP_CLOSE_SERVICE("unified.trade.close"),
	
	/**
	 * 支付宝jsapi预下单
	 */
	ALIPAY_JSAPI_PAY_SERVICE("pay.alipay.jspay"),
	
	/**
	 * 支付宝扫码支付,接口文档https://open.swiftpass.cn/openapi/wiki?index=6&chapter=1
	 */
	ALIPAY_NATIVE_PAY("pay.alipay.native"),
	
	UNIFIED_TRADE_QUERY("unified.trade.query"),
	UNIFIED_TRADE_CANCEL("unified.micropay.reverse"),
	UNIFIED_TRADE_REFUND("unified.trade.refund"),
	UNIFIED_TRADE_REFUND_QUERY("unified.trade.refundquery"),
	UNIFIED_TRADE_CLOSE("unified.trade.close");
	
	private String service;

	private CibServiceType(String service) {
		this.service = service;
	}

	public String getService() {
		return service;
	}

}
