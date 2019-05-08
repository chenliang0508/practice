package cn.com.upcard.mgateway.service;

import cn.com.upcard.mgateway.async.notify.model.CallbackPara;
import cn.com.upcard.mgateway.async.notify.model.CallbackRequestInfo;
import cn.com.upcard.mgateway.async.notify.model.OrderProcessingResult;

public interface CallbackService {

	/**
	 * 回调订单处理
	 * 
	 * @param callbackRequestInfo
	 * @return
	 */
	OrderProcessingResult updateOrderProcessing(CallbackRequestInfo callbackRequestInfo);

	/**
	 * 威富通支付回调结果处理
	 * 
	 * @return
	 */
	void callbackHandle(CallbackPara callbackPara);

	/**
	 * 批量通知方法，用于内部调用
	 * 
	 * @param endTime
	 * @return
	 */
	void batchNotify(String endTime);

}
