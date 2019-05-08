package cn.com.upcard.mgateway.channel.bank.mybank.common;

public enum MyBankServiceType {
	// 线下刷卡支付,支付接口类型
	SCANPAY_PAY_SERVICE("ant.mybank.bkmerchanttrade.pay"),
	// 线下刷卡支付,查询接口类型
	SCANPAY_QUERY_SERVICE("ant.mybank.bkmerchanttrade.payQuery"),
	// 线下刷卡支付,撤销接口类型
	SCANPAY_CANCEL_SERVICE("ant.mybank.bkmerchanttrade.payCancel"),
	// 线上支付,关单接口类型
	CLOSE_SERVICE("ant.mybank.bkmerchanttrade.payClose"),
	// 线下刷卡支付,退款接口类型
	REFUND_SERVICE("ant.mybank.bkmerchanttrade.refund"),
	// 线下刷卡支付,退款接口类型
	REFUND_QUERY_SERVICE("ant.mybank.bkmerchanttrade.refundQuery"),
	// Js支付
	JS_PAY_SERVICE("ant.mybank.bkmerchanttrade.prePay"),
	//h5支付回调
	H5_NOTICE_SERVICE("ant.mybank.bkmerchanttrade.prePayNotice");

	private String service;

	private MyBankServiceType(String service) {
		this.service = service;
	}

	public String getService() {
		return service;
	}

}
