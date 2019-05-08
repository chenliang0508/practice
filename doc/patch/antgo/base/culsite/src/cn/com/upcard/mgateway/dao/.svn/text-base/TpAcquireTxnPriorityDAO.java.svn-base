package cn.com.upcard.mgateway.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.upcard.mgateway.entity.TpAcquireTxnPriority;

public interface TpAcquireTxnPriorityDAO extends PagingAndSortingRepository<TpAcquireTxnPriority, String> {

	/**
	 * 获取交易方式的默认通道
	 * @param priorty
	 * @param payTypeCode
	 * @return
	 */
	TpAcquireTxnPriority findByPriorityNumAndPayTypeCode(int priorty, String payTypeCode);
	
	TpAcquireTxnPriority findTopByPayTypeCodeAndAcquirerIdInOrderByPriorityNum(String payTypeCode, List<String> acquirerIds);
}
