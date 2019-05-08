package cn.com.upcard.mgateway.util.systemparam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.entity.TpPaymentType;
import cn.com.upcard.mgateway.service.CommericalInfoService;

public class CommercialInfo {
	private static Logger logger = LoggerFactory.getLogger(CommercialInfo.class);
	public static Map<String, TpPaymentType> paymentTypeMap = null;
	
	public static void init() {
		logger.info("加载收银通道交易类型");
		Map<String, TpPaymentType> payTypes = new HashMap<String, TpPaymentType>();
		CommericalInfoService commericalInfoService = (CommericalInfoService) SpringContextUtil.getBean("commericalInfoServiceImpl");
		List<TpPaymentType> paymentTypes = commericalInfoService.getAllPaymentTypes();
		if(paymentTypes == null || paymentTypes.isEmpty()) {
			logger.warn("没有有效的收银通道交易类型");
			return;
		}
		for(TpPaymentType type : paymentTypes) {
			payTypes.put(type.getPayTypeId(), type);
		}
		paymentTypeMap = payTypes;
		logger.info("加载收银通道交易类型结束");
	}
	
	public static TpPaymentType getByChannelId(String channelId, String payTypeCode) {
		if(paymentTypeMap == null) {
			init();
		}
		for(String key : paymentTypeMap.keySet()) {
			TpPaymentType type = paymentTypeMap.get(key);
			if(type.getChannelId().equals(channelId) && type.getPayTypeCode().equals(payTypeCode)) {
				return type;
			}
		}
		return null;
	}
	
	public static TpPaymentType getByAcquirerId(String acquirerId, String payTypeCode) {
		if(paymentTypeMap == null) {
			init();
		}
		for(String key : paymentTypeMap.keySet()) {
			TpPaymentType type = paymentTypeMap.get(key);
			if(type.getTpAcquirer().getAcquirerId().equals(acquirerId) && type.getPayTypeCode().equals(payTypeCode)) {
				return type;
			}
		}
		return null;
	}
	
}
