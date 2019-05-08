package cn.com.upcard.mgateway.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.upcard.mgateway.entity.TpCommPayTypes;
import cn.com.upcard.mgateway.entity.TpPaymentType;

public interface TpCommPayTypesDAO extends PagingAndSortingRepository<TpCommPayTypes, String>{

	/**
	 * 根据银商商户号、通道ID、交易类型Id查找商户支付类型
	 * @param culCommNo
	 * @param channelId
	 * @param payTypeId
	 * @param status
	 * @return
	 */
	TpCommPayTypes findByCulCommNoAndChannelIdAndTpPaymentTypeAndStatus(String culCommNo, String channelId,
			TpPaymentType payTypeId, String status);
	
}
