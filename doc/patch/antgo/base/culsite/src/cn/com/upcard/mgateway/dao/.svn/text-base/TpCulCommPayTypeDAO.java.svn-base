package cn.com.upcard.mgateway.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.upcard.mgateway.entity.TpCulCommPayType;
public interface TpCulCommPayTypeDAO extends JpaRepository<TpCulCommPayType, String>, JpaSpecificationExecutor<TpCulCommPayType>{
	@Query("select distinct t.channelId " + 
			"from TpCulCommPayType t " + 
			"left join TpPaymentType p " + 
			"on t.payTypeId = p.payTypeId " + 
			" where p.payTypeCode =  :tradeType" + 
			"  and t.commId = :commId " + 
			"  and t.status = 'Y' ")
	List<String> findChannelIdByCommIdAndTradeType(@Param("commId") String commId, @Param("tradeType")String tradeType);
}
