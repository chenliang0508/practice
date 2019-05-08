package cn.com.upcard.mgateway.common.enums;

/**
 * 对外接口服务类型
 * 
 * @author chenliang
 * 
 */
public enum ServiceType {
	/**
	 * 支付宝C扫B，主扫模式，动态二维码
	 */
	ALI_NATIVE("pay.alipay.native"),
	TRADE_QUERY("unified.trade.query"),
	TRADE_CANCEL("pay.trade.cancel"),
	TRADE_CLOSE("unified.trade.close"),
	TRADE_REFUND("unified.trade.refund"),
	REFUND_QUERY("unified.trade.refundQuery"),
	QRCODE_CREATE("unified.qrcode.create"),
	QRCODE_TOORDER("unified.qrcode.toOrder"),
	QRCODE_PRE_ORDER("unified.qrcode.pre.order");
	

	private String serviceName;

	private ServiceType() {

	}

	private ServiceType(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
