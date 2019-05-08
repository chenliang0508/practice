package cn.com.upcard.mgateway.service;

import java.util.Date;
import java.util.List;

import cn.com.upcard.mgateway.common.enums.BankCode;
import cn.com.upcard.mgateway.controller.response.AlipayJsapiResponse;
import cn.com.upcard.mgateway.controller.response.AlipayNativeResponse;
import cn.com.upcard.mgateway.controller.response.MicroPayResponse;
import cn.com.upcard.mgateway.controller.response.RefundQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeCancelResponse;
import cn.com.upcard.mgateway.controller.response.TradeQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeRefundResponse;
import cn.com.upcard.mgateway.controller.response.TradecloseResponse;
import cn.com.upcard.mgateway.controller.response.WxJsResponse;
import cn.com.upcard.mgateway.controller.response.WxNativeResponse;
import cn.com.upcard.mgateway.entity.OrderOper;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;

/**
 * 定义对订单表的基本操作，有：创建、查询、更新
 * @author chenliang
 *
 */
public interface OrderOperService {
	/**
	 * 根据商户订单号和商户号查询订单表
	 * @param orderNo
	 * @param culCommNo
	 * @param orderType
	 * @return
	 */
	TradeOrder findByOrderNoAndCulCommNoAndType(String orderNo, String culCommNo, String orderType);
	/**
	 * 创建订单
	 * @return  TradeOrder
	 * @see cn.com.upcard.mgateway.entity.TradeOrder
	 */
	TradeOrder createOrder(PreTradeOrderInfo preTradeOrderInfo);
	/**
	 * 记录订单操作表
	 * @param tradeOrder
	 * @param operType
	 * @param sellAccount
	 * @param buyerAccount
	 * @return
	 */
	OrderOper saveOrderOper(TradeOrder tradeOrder, String operType, String sellAccount, String buyerAccount, String reqIp);
	/**
	 * 更新订单
	 * @param tradeOrder
	 */
	void updateTradeOrder(TradeOrder tradeOrder);
	/**
	 * 更新订单操作
	 * @param orderOper
	 */
	void updateOrderOper(OrderOper orderOper);
	/**
	 * 根据订单号、交易号、商户号获取订单信息
	 * @param tradeNo
	 * @param orderNo
	 * @param culCommNo
	 * @param orderType
	 * @return
	 */
	TradeOrder findByTradeNoAndOrderNoAndCulCommNoAndType(String tradeNo, String orderNo, String culCommNo, String orderType);
	/**
	 * 根据交易号(系统订单号)查询订单
	 * @param tradeNo
	 * @return
	 */
	TradeOrder findOrderByTradeNo(String tradeNo, boolean lock);
	/**
	 * 根据orderId查询订单
	 * @param orderId
	 * @param lock
	 * @return
	 */
	TradeOrder findOrderById(String orderId, boolean lock);
	/**
	 * 获得订单操作信息
	 * @param orderId
	 * @return
	 */
	OrderOper findByOrderIdAndOperType(String orderId, String orderType);
	/**
	 * 查询退款记录
	 * @param origOrderNo
	 * @param commUnionNo
	 * @param status
	 * @return
	 */
	List<TradeOrder> findByOrigOrderNoAndCulCommNoAndStatus(String origOrderNo, String culCommNo, List<String> status);
	
	/**
	 * 支付宝C扫B返回参数的处理以及对订单的更新
	 * @param channelReponseInfo
	 * @return
	 */
	AlipayNativeResponse updateAlipayNativePreOrder(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo);

	/**
	 * 支付宝服务窗jsapi预下单处理
	 * @param order
	 * @param orderOper
	 * @param channelReponseInfo
	 * @return
	 */
	AlipayJsapiResponse updateAlipayJsapiPreOrder(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo);
	
	/**
	 * 微信扫码付
	 * 
	 * @param channelReponseInfo
	 * @return
	 */
	WxNativeResponse updateWxNativePreOrder(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo);

	/**
	 * 微信js支付
	 * 
	 * @param channelReponseInfo
	 * @return
	 */
	WxJsResponse updateWxJsPreOrder(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo);
    
	/**
	 * 线下B扫C支付
	 * 
	 * @param channelReponseInfo
	 * @return
	 */
	MicroPayResponse updateMicropayOrder(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo);
	
	/**
	 * 查询接口返回数据的封装以及订单的更新
	 * @param tradeOrder
	 * @param channelReponseInfo
	 * @return
	 */
	TradeQueryResponse updateUnifiedPayQuery(TradeOrder tradeOrder, ChannelResponseInfo channelReponseInfo);
	/**
	 * 撤销接口的处理
	 * @param order
	 * @param orderOper
	 * @param channelReponseInfo
	 * @return
	 */
	TradeCancelResponse updateUnifiedCancel(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo);
	/**
	 * 订单关闭
	 * @param order
	 * @param orderOper
	 * @param channelReponseInfo
	 * @return
	 */
	TradecloseResponse updateUnifiedClose(TradeOrder order, OrderOper orderOper, ChannelResponseInfo channelReponseInfo);
	/**
	 * 退款订单的更新
	 * @param refundOrder
	 * @param refundOrderOper
	 * @param channelReponseInfo
	 * @return
	 */
	TradeRefundResponse updateUnifiedRefund(TradeOrder refundOrder, OrderOper refundOrderOper,
			ChannelResponseInfo channelReponseInfo);
	/**
	 * 退款查询
	 * @param tradeOrder
	 * @param channelReponseInfo
	 * @return
	 */
	RefundQueryResponse updateUnifiedRefundQuery(TradeOrder tradeOrder, ChannelResponseInfo channelReponseInfo);
	
	/**
	 * 静态二维码生成商户订单号
	 * @param bankCode
	 * @return
	 */
	String createOutTradeNo(String bankCode);
	/**
	 * 根据支付通道的类型生成atradeNo,默认长度30位，格式：yyMMdd + 银行代码  + 6-18位的随机数
	 * @param bankCode
	 * @return
	 */
	String createTradeNo(BankCode bankCode);
	
	/**
	 * 查询订单；条件：订单类型、状态、创建时间的范围
	 * @param orderType
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<TradeOrder> findByOrderTypeAndStatusAndCreateTimeBetween(String orderType, String status, Date startTime, Date endTime);
	
	String findCommKeyByOutTradeNoAndBankMerNo(String outTradeNo, String bankMerNo);
}
