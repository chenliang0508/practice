package cn.com.upcard.mgateway.service;


import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.controller.response.AlipayJsapiResponse;
import cn.com.upcard.mgateway.controller.response.AlipayNativeResponse;
import cn.com.upcard.mgateway.controller.response.MicroPayResponse;
import cn.com.upcard.mgateway.controller.response.QRCodePreResponse;
import cn.com.upcard.mgateway.controller.response.RefundQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeCancelResponse;
import cn.com.upcard.mgateway.controller.response.TradeQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeRefundResponse;
import cn.com.upcard.mgateway.controller.response.TradecloseResponse;
import cn.com.upcard.mgateway.controller.response.WxJsResponse;
import cn.com.upcard.mgateway.controller.response.WxNativeResponse;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;

/**
 * 处理业务
 * @author chenliang
 *
 */
public interface GatewayService {
	/**
	 * 支付宝在线扫码支付预下单
	 * @param preTradeOrderInfo
	 * @return
	 */
	AlipayNativeResponse alipayNativePreOrder(PreTradeOrderInfo preTradeOrderInfo);
	/**
	 * 支付宝服务窗预下单
	 * @param preTradeOrderInfo
	 * @return
	 */
	AlipayJsapiResponse alipayJsapiPreOrder(PreTradeOrderInfo preTradeOrderInfo);
	/**
	 * 创建交易订单、退款订单
	 * @param preTradeOrderInfo
	 * @return
	 */
	TradeOrder createTradeOrder(PreTradeOrderInfo preTradeOrderInfo);
	/**
	 * 统一支付交易查询
	 * @param preTradeOrderInfo
	 * @return
	 */
	TradeQueryResponse unifiedPayQuery(PreTradeOrderInfo preTradeOrderInfo);
	/**
	 *线下支付
	 * 
	 * @param preTradeOrderInfo
	 */
	MicroPayResponse  microPayOrder(PreTradeOrderInfo preTradeOrderInfo);
	/**
	 * 微信扫码支付接口
	 * 
	 * @param preTradeOrderInfo
	 */
	WxNativeResponse wxNativePreOrder(PreTradeOrderInfo preTradeOrderInfo);

	/**
	 * 微信公众号和小程序支付
	 * 
	 * @param preTradeOrderInfo
	 */
	WxJsResponse wxJsPreOrder(PreTradeOrderInfo preTradeOrderInfo);

	/**
	 * 统一退款接口
	 * @return 
	 */
	TradeRefundResponse unifiedRefund(PreTradeOrderInfo preTradeOrderInfo);
	
	/**
	 * 退款查询接口
	 * @param preTradeOrderInfo 
	 * @return
	 */
	RefundQueryResponse tradeRefundQuery(PreTradeOrderInfo preTradeOrderInfo);
	
	/**
	 * 通用订单撤销
	 * @param preTradeOrderInfo
	 * @version 1.0
	 */
	public TradeCancelResponse tradeCancel(PreTradeOrderInfo preTradeOrderInfo);

	/**
	 * 线上C扫B关闭订单接口
	 * @param preTradeOrderInfo 
	 * @return
	 */
	TradecloseResponse unifiedClose(PreTradeOrderInfo preTradeOrderInfo);
}
