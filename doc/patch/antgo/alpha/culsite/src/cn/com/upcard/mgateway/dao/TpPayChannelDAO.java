package cn.com.upcard.mgateway.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.upcard.mgateway.entity.TpCommPayTypes;
import cn.com.upcard.mgateway.entity.TpPayChannel;
import cn.com.upcard.mgateway.entity.TpPaymentType;

public interface TpPayChannelDAO extends PagingAndSortingRepository<TpPayChannel, String>{
	/**
	 * 查询收单机构id
	 * @param channelId
	 * @return
	 */
	List<TpPayChannel> findByChannelIdIn(List<String> channelId);
}
