package cn.com.upcard.mgateway.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.com.upcard.mgateway.entity.OrderOper;

@Repository
public interface OrderOperDAO extends PagingAndSortingRepository<OrderOper, String> {

	OrderOper findByOrderIdAndOperType(String tradeOrderId, String orderType);

}
