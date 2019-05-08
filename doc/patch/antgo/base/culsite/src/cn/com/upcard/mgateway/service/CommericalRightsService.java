package cn.com.upcard.mgateway.service;

import java.util.EnumSet;

import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.controller.request.BaseParam;
import cn.com.upcard.mgateway.entity.TpCulCommerical;
import cn.com.upcard.mgateway.model.CodeMsg;
import cn.com.upcard.mgateway.model.CommercialArguments;

/**
 * <pre>
 * 商户权限处理业务逻辑类
 * 
 * </pre>
 * @author huatingzhou
 *
 */
public interface CommericalRightsService {
	
	public CodeMsg<CommercialArguments> checkCommericalRights(BaseParam param, TradeType tradeType);
	
	public CodeMsg<CommercialArguments> checkCommericalRights(BaseParam param, String outTradeNo);
	
	public CodeMsg<CommercialArguments> checkCommericalRights(BaseParam param, EnumSet<TradeType> tradeTypes);
	
	public CodeMsg<CommercialArguments> hasPayRight(TpCulCommerical culCommInfo, EnumSet<TradeType> tradeTypes);
	
	public CodeMsg<CommercialArguments> checkCommericalRights(String[] params, String mchId, String clientSign, TradeType tradeType);
}
