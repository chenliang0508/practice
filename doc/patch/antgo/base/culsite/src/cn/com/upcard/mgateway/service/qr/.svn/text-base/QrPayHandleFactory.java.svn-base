package cn.com.upcard.mgateway.service.qr;

import cn.com.upcard.mgateway.common.enums.BrowserType;
import cn.com.upcard.mgateway.service.GatewayService;

/**
 * <pre>
 * 静态二维码处理器工厂
 * 
 * </pre>
 * @author huatingzhou
 * @version 1.0
 */
public class QrPayHandleFactory {
	public static QrPayHandle createHandle(GatewayService gatewayService, BrowserType browserType) {
		if (gatewayService == null) {
			throw new RuntimeException("gateWay is null");
		}

		if (browserType == null) {
			throw new RuntimeException("browserType is null");
		} else if (browserType == BrowserType.ALIPAY) {
			return new AlipayQrPayHandle(gatewayService, browserType);

		} else if (browserType == BrowserType.WEIXIN) {
			return new WXpayQrPayHandle(gatewayService, browserType);
		}else {
			return null;
		}
	}
}
