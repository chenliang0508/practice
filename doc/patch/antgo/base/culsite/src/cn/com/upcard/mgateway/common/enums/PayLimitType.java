package cn.com.upcard.mgateway.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * 禁用支付方式类型.
 * 
 * 不同的收单机构可能支持的禁用 支付类型不同，此处是我司对外统一的禁用类型，不同的收单机构需要转化为响应的请求参数
 * </pre>
 * @author huatingzhou
 *
 */
public enum PayLimitType {
	/**
	 * 花呗（仅支付宝）
	 */
//	PCREDIT("pcredit"),
	CREDIT_CARD("credit_card");//信用卡
	
	private PayLimitType(String payLimitType) {
		this.name = payLimitType;
	}
	private String name;
	public String getName() {
		return name;
	}
	
	public static PayLimitType topayLimitType(String payLimitTypeName) {
		if (StringUtils.isEmpty(payLimitTypeName)) {
			return null;
		}
		
		for (PayLimitType type : PayLimitType.values()) {
			if (type.name.equals(payLimitTypeName)) {
				return type;
			}
		}
		return null;
	}
}
