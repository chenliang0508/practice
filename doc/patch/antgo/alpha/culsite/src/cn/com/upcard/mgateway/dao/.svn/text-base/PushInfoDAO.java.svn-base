package cn.com.upcard.mgateway.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.com.upcard.mgateway.entity.PushInfo;

public interface PushInfoDAO extends PagingAndSortingRepository<PushInfo, String> {

	List<PushInfo> findByPushTimeBeforeAndStatusEquals(String endTime, String status);

	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT T FROM PushInfo T WHERE T.orderId = :orderId")
	PushInfo findByOrderIdForUpdate(@Param("orderId") String orderId);

	PushInfo findByOrderId(String orderId);

}