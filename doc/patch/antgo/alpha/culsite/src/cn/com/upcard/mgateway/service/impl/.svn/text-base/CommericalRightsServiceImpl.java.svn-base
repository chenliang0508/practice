package cn.com.upcard.mgateway.service.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.controller.request.BaseParam;
import cn.com.upcard.mgateway.controller.validator.ResquestSignValidator;
import cn.com.upcard.mgateway.dao.TpAcquireTxnPriorityDAO;
import cn.com.upcard.mgateway.dao.TpCulCommPayTypeDAO;
import cn.com.upcard.mgateway.dao.TpPayChannelDAO;
import cn.com.upcard.mgateway.entity.TpAcquireTxnPriority;
import cn.com.upcard.mgateway.entity.TpCommPayTypes;
import cn.com.upcard.mgateway.entity.TpCulCommPayType;
import cn.com.upcard.mgateway.entity.TpCulCommerical;
import cn.com.upcard.mgateway.entity.TpPayChannel;
import cn.com.upcard.mgateway.entity.TpPaymentType;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.model.CodeMsg;
import cn.com.upcard.mgateway.model.CommercialArguments;
import cn.com.upcard.mgateway.service.CommericalInfoService;
import cn.com.upcard.mgateway.service.CommericalRightsService;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.util.systemparam.CommercialInfo;

@Service
public class CommericalRightsServiceImpl implements CommericalRightsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CommericalRightsServiceImpl.class);
	@Autowired
	private OrderOperService orderOperService;
	@Autowired
	private CommericalInfoService commericalInfoService;
	@Autowired
	private TpAcquireTxnPriorityDAO tpAcquireTxnPriorityDAO;
	@Autowired
	private TpCulCommPayTypeDAO tpCulCommPayTypeDAO;
	@Autowired
	private TpPayChannelDAO tpPayChannelDAO;
	@Override
	public CodeMsg<CommercialArguments> checkCommericalRights(BaseParam param, TradeType tradeType) {
		CodeMsg<CommercialArguments> codeMsg = checkSign(param);
		if(!Constants.OK.equals(codeMsg.getCode())) {
			return codeMsg;
		}
		CodeMsg<CommercialArguments> cmPer = checkPermission(codeMsg.getInfo().getTpCulCommerical(), tradeType);
		if(!Constants.OK.equals(cmPer.getCode())) {
			return cmPer;
		}
		codeMsg.getInfo().setTpCommPayTypes(cmPer.getInfo().getTpCommPayTypes());
		return codeMsg;
	}
	
	/**
	 * 校验签名
	 * @param param
	 * @return
	 */
	private CodeMsg<CommercialArguments> checkSign(BaseParam param) {
		// 获取商户参数,查询银商商户门店表
		TpCulCommerical culCommInfo = commericalInfoService.getCulCommInfoByNo(param.getMchId());
		if (culCommInfo == null) {
			logger.info("商户/门店未注册.");
			return new CodeMsg<CommercialArguments>(ResponseResult.COMMERICAL_NOT_REGISTERED);
		}
		// 校验签名
		logger.info(culCommInfo.toString());
		if (!ResquestSignValidator.checkUseAnnotionSign(param, culCommInfo.getCommKey())) {
			logger.info("验证签名失败");
			return new CodeMsg<CommercialArguments>(ResponseResult.INVALID_SIGN);
		}
		CommercialArguments arg = new CommercialArguments();
		arg.setTpCulCommerical(culCommInfo);
		return new CodeMsg<CommercialArguments>(ResponseResult.OK, arg);
	}
	
	/**
	 * 检查权限
	 * @param param
	 * @return
	 */
	private CodeMsg<CommercialArguments> checkPermission(TpCulCommerical culCommInfo, TradeType tradeType) {
		//查询商户进件的渠道
		List<String> channelIds = tpCulCommPayTypeDAO.findChannelIdByCommIdAndTradeType(culCommInfo.getCommId(), tradeType.name());
		if (channelIds == null || channelIds.isEmpty()) {
			logger.warn("没有{}支付方式的权限", tradeType.name());
			return new CodeMsg<CommercialArguments>(ResponseResult.INVALID_COMMERICAL_CHANNEL);
		}
		logger.info("商户{}拥有的支付方式渠道{}", culCommInfo.getCulCommNo(), channelIds);
		List<TpPayChannel> tpPayChannels = tpPayChannelDAO.findByChannelIdIn(channelIds);
		if (tpPayChannels == null || tpPayChannels.isEmpty()) {
			logger.warn("没有{}支付方式的权限", tradeType.name());
			return new CodeMsg<CommercialArguments>(ResponseResult.INVALID_COMMERICAL_CHANNEL);
		}
		List<String> acquireIds = new ArrayList<String>();
		for (TpPayChannel p : tpPayChannels) {
			acquireIds.add(p.getAcquirerId());
		}
		//获取优先级最高的渠道
		TpAcquireTxnPriority priorty = tpAcquireTxnPriorityDAO.findTopByPayTypeCodeAndAcquirerIdInOrderByPriorityNum(tradeType.name(), acquireIds);
		if (priorty == null) {
			logger.warn("没有默认的收单机构通道");
			return new CodeMsg<CommercialArguments>(ResponseResult.INVALID_COMMERICAL_CHANNEL);
		}
		logger.info("商户{}的{}方式使用的收单机构是{}", culCommInfo.getCulCommNo(), tradeType.name(), priorty.getAcquirerId());
		// 收银通道交易类型表 TpPaymentType
		TpPaymentType paymentType = CommercialInfo.getByAcquirerId(priorty.getAcquirerId(), priorty.getPayTypeCode());
		if (paymentType == null) {
			logger.info("没有调用权限");
			return new CodeMsg<CommercialArguments>(ResponseResult.INVALID_COMMERICAL_CHANNEL);
		}

		TpCommPayTypes type = commericalInfoService.getCommPayTypesByTypeIdAndChannelIdAndCommNo(
				paymentType.getChannelId(), paymentType.getPayTypeId(), culCommInfo.getCulCommNo());
		if (type == null) {
			logger.info("没有完成进件");
			return new CodeMsg<CommercialArguments>(ResponseResult.UNREGISTERES_ACQUIRER);
		}
		CommercialArguments arg = new CommercialArguments();
		arg.setTpCommPayTypes(type);
		arg.setTpCulCommerical(culCommInfo);
		if(StringUtils.isEmpty(type.getTpCommericalInfo().getCommKey())){
			logger.info("请配置商户密码");
			return new CodeMsg<CommercialArguments>(ResponseResult.COMMERICAL_NOT_REGISTERED);
		}
		return new CodeMsg<CommercialArguments>(ResponseResult.OK, arg);
	}
	
	@Override
	public CodeMsg<CommercialArguments> checkCommericalRights(BaseParam param, EnumSet<TradeType> tradeTypes) { 
		//获取商户参数,查询银商商户门店表
		TpCulCommerical culCommInfo = commericalInfoService.getCulCommInfoByNo(param.getMchId());
		if(culCommInfo == null) {
			logger.info("商户/门店未注册.");
			return new CodeMsg<CommercialArguments>(ResponseResult.COMMERICAL_NOT_REGISTERED);
		}
		// 校验签名
		logger.info(culCommInfo.toString());
		if(!ResquestSignValidator.checkUseAnnotionSign(param, culCommInfo.getCommKey())) {
			logger.info("验证签名失败");
			return new CodeMsg<CommercialArguments>(ResponseResult.INVALID_SIGN);
		}
		return hasPayRight(culCommInfo, tradeTypes);
	}
	
	@Override
	public CodeMsg<CommercialArguments> hasPayRight(TpCulCommerical culCommInfo, EnumSet<TradeType> tradeTypes) {
		CodeMsg<CommercialArguments> codeMsg = null;
		for (TradeType tradeT : tradeTypes) {
			codeMsg = this.checkPermission(culCommInfo, tradeT);
			if (Constants.OK.equals(codeMsg.getCode())) {
				return codeMsg;
			}
		}
		
		return codeMsg;
	}
	
	
	@Override
	public CodeMsg<CommercialArguments> checkCommericalRights(BaseParam param, String outTradeNo) {
		TradeOrder order = orderOperService.findByOrderNoAndCulCommNoAndType(outTradeNo, param.getMchId(), null);
		if (order == null) {
			return new CodeMsg<CommercialArguments>(ResponseResult.ORDER_NOT_EXISTS);
		}
		TradeType type = TradeType.toTradeType(order.getTradeType());
		if (type == null) {
			return new CodeMsg<CommercialArguments>(ResponseResult.SYSTEM_ERROR);
		}
		return this.checkCommericalRights(param, type);
	}
	
	@Override
	public CodeMsg<CommercialArguments> checkCommericalRights(String[] params, String mchId, String clientSign, TradeType tradeType) {
		//获取商户参数,查询银商商户门店表
		TpCulCommerical culCommInfo = commericalInfoService.getCulCommInfoByNo(mchId);
		if(culCommInfo == null) {
			logger.info("商户/门店未注册.");
			return new CodeMsg<CommercialArguments>(ResponseResult.COMMERICAL_NOT_REGISTERED);
		}
		// 校验签名
		logger.info(culCommInfo.toString());
		if(!ResquestSignValidator.checkCustomSign(params, culCommInfo.getCommKey(), clientSign)) {
			logger.info("验证签名失败");
			return new CodeMsg<CommercialArguments>(ResponseResult.INVALID_SIGN);
		}
		return checkPermission(culCommInfo, tradeType);
	}
}
