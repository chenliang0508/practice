package cn.com.upcard.mgateway.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.upcard.mgateway.entity.TpCulCommerical;

public interface TpCulCommericalDAO  extends PagingAndSortingRepository<TpCulCommerical, String> {

	/**
	 * 获取银商商户信息
	 * @param culCommNo 银商商户号
	 * @param orgStatus 有效状态
	 * @return
	 */
	TpCulCommerical findByCulCommNoAndOrgStatus(String culCommNo, String orgStatus);

}
