package cn.com.upcard.mgateway.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.com.upcard.mgateway.entity.TradeOrder;

@Repository
public interface TradeOrderDAO extends PagingAndSortingRepository<TradeOrder, String> {
	TradeOrder findByTradeNo(String tradeNo);
	
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT T FROM TradeOrder T WHERE T.tradeNo = :tradeNo")
	TradeOrder findByTradeNoForUpdate(@Param("tradeNo") String tradeNo);
	
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	TradeOrder findById(String orderId);
	
	TradeOrder findByOrderNoAndCulCommNoAndOrderType(String orderNo, String culCommNo, String orderType);
	TradeOrder findByTradeNoAndOrderNoAndCulCommNo(String tradeNo, String orderNo, String culCommNo);
	
	TradeOrder findByOrderNoAndCulCommNo(String orderNo, String culCommNo);
	
	List<TradeOrder> findByOrigOrderNoAndCulCommNoAndStatusInAndOrderType(String origOrderNo, String culCommNo, List<String> status, String orderType);

	List<TradeOrder> findByOrderTypeAndStatusAndCreateTimeBetween(String orderType, String status,
			Date startTime, Date endTime);

	List<TradeOrder> findByOrderTypeAndStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(String orderType,
			String status, Date startTime, Date endtime);
}
