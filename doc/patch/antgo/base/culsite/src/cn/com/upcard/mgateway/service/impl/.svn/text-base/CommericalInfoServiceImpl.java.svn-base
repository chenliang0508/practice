package cn.com.upcard.mgateway.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.dao.TpCommPayTypesDAO;
import cn.com.upcard.mgateway.dao.TpCulCommPayTypeDAO;
import cn.com.upcard.mgateway.dao.TpCulCommericalDAO;
import cn.com.upcard.mgateway.dao.TpPaymentTypeDAO;
import cn.com.upcard.mgateway.entity.TpCommPayTypes;
import cn.com.upcard.mgateway.entity.TpCulCommerical;
import cn.com.upcard.mgateway.entity.TpPaymentType;
import cn.com.upcard.mgateway.service.CommericalInfoService;

@Service
public class CommericalInfoServiceImpl implements CommericalInfoService {
	private static Logger logger = LoggerFactory.getLogger(CommericalInfoServiceImpl.class);
	@Autowired
	private TpCulCommericalDAO tpCulCommericalDAO;
	@Autowired
	private TpCulCommPayTypeDAO tpCulCommPayTypeDAO;
	@Autowired
	private TpCommPayTypesDAO tpCommPayTypesDAO;
	@Autowired
	private TpPaymentTypeDAO tpPaymentTypeDAO;
	
	@Override
	public TpCulCommerical getCulCommInfoByNo(String culCommNo) {
		logger.info("查询银商商户门店信息，culCommNo:" + culCommNo);
		if (StringUtils.isEmpty(culCommNo)) {
			logger.warn("culCommNo is null.");
			return null;
		}
		return tpCulCommericalDAO.findByCulCommNoAndOrgStatus(culCommNo, Constants.YES);
	}

	@Override
	public TpCommPayTypes getCommPayTypesByTypeIdAndChannelIdAndCommNo(String channelId, String payTypeId,
			String culCommNo) {
		logger.info("查询商户支付类型,channelId:" + channelId + ",payTypeId:" + payTypeId + ",culCommNo:" + culCommNo);
		if (StringUtils.isEmpty(culCommNo) || StringUtils.isEmpty(channelId) || StringUtils.isEmpty(payTypeId)) {
			logger.info("商户:" + culCommNo + "没");
			return null;
		}
		TpPaymentType payType = new TpPaymentType();
		payType.setPayTypeId(payTypeId);
		return tpCommPayTypesDAO.findByCulCommNoAndChannelIdAndTpPaymentTypeAndStatus(culCommNo, channelId, payType, Constants.YES);
	}

	@Override
	public List<TpPaymentType> getAllPaymentTypes() {
		return tpPaymentTypeDAO.findByStatus(Constants.YES);
	}
}
