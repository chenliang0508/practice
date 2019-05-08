package cn.com.upcard.mgateway.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.upcard.mgateway.entity.OrderDiscountInfo;

public interface OrderDiscountInfoDAO extends PagingAndSortingRepository<OrderDiscountInfo, String> {

}
