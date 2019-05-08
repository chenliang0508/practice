package cn.com.upcard.mgateway.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.upcard.mgateway.entity.TpPaymentType;

public interface TpPaymentTypeDAO extends PagingAndSortingRepository<TpPaymentType, String> {
	/**
	 * 获取所有的交易类型
	 * @param status
	 * @return
	 */
	List<TpPaymentType> findByStatus(String status);
	
	TpPaymentType findByChannelIdAndPayTypeCodeAndStatus(String channelId,String payTypeCode,String status);
}
