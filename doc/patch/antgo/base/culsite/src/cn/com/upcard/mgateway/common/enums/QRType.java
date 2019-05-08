package cn.com.upcard.mgateway.common.enums;

import java.util.EnumSet;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * 静态二维码类型
 * 
 * </pre>
 * @author huatingzhou
 *
 */
public enum QRType {
	//支付宝静态二微码
	ALI_QR(TradeType.ALI_NATIVE),
	//微信静态二维码
	WEIXIN_QR(TradeType.WX_NATIVE);
	
	private TradeType tradeType;
	
	QRType(TradeType tradeType) {
		this.tradeType = tradeType;
	}
	
	public TradeType getTradeType() {
		return tradeType;
	}


	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}


	public static QRType toQRType(String qrTypeName) {
		if (StringUtils.isEmpty(qrTypeName)) {
			return null;
		}
		
		for (QRType type : QRType.values()) {
			if (type.name().equals(qrTypeName)) {
				return type;
			}
		}
		return null;
	}
	
	public static TradeType getTradeType(String qrTypeName) {
		QRType type = toQRType(qrTypeName);
		if (type == null) {
			return null;
		}
		return type.getTradeType();
	}
	
	public static EnumSet<TradeType> getAllTradeType() {
		EnumSet<TradeType> tradeTypes = null;
		for (QRType type : QRType.values()) {
			if (tradeTypes == null) {
				tradeTypes = EnumSet.of(type.getTradeType());
			} else {
				tradeTypes.add(type.getTradeType());
			}
		}
		return tradeTypes;
	}
}
