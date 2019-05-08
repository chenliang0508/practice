package cn.com.upcard.mgateway.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.upcard.mgateway.entity.TpCommericalInfo;

public interface TpCommericalInfoDAO extends PagingAndSortingRepository<TpCommericalInfo, String> {

	//TpCommericalInfo findByCommUnionNoAndOrgStatusAndCommTypeIn(String commUnionNo, String status, List<String> commTypes);
	TpCommericalInfo findByCulCommNoAndBankMerNoAndCommStatus(String commUnionNo, String bankMerNo, String status);
}
