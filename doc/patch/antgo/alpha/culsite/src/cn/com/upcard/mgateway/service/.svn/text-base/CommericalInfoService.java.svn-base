package cn.com.upcard.mgateway.service;


import java.util.List;

import cn.com.upcard.mgateway.entity.TpCommPayTypes;
import cn.com.upcard.mgateway.entity.TpCulCommerical;
import cn.com.upcard.mgateway.entity.TpPaymentType;


public interface CommericalInfoService {
	/**
	 * 根据商户号获取银商商户门店的信息，商户状态为Y
	 * 
	 * @param culCommNo
	 * @return
	 */
	TpCulCommerical getCulCommInfoByNo(String culCommNo);

//	/**
//	 * 获取商户当前配置的有效的支付通道
//	 * 
//	 * @param commId
//	 * @return
//	 */
//	TpCulCommPayType getCulCommPayTypeByCommId(String commId);

	/**
	 * 获取有效的商户支付类型
	 * 
	 * @param channelId 交易通道ID
	 * @param payTypeId 交易类型ID
	 * @param culCommNo 银商商户号
	 * @return
	 */
	TpCommPayTypes getCommPayTypesByTypeIdAndChannelIdAndCommNo(String channelId, String payTypeId, String culCommNo);
	/**
	 * 获取所有的交易类型
	 * @return
	 */
	List<TpPaymentType> getAllPaymentTypes();
}
