package cn.com.upcard.mgateway.async.notify.service;

import cn.com.upcard.mgateway.async.notify.model.NotifyPara;

public interface NotifyService {

	/**
	 * 执行消息通知线程
	 * 
	 * @param notifyPara
	 */
	void executeNotifyThread(NotifyPara notifyPara);

	/**
	 * 执行消息通知线程，用于task
	 * 
	 * @param notifyPara
	 */
	void executeNotifyThreadForTask(NotifyPara notifyPara);

	/**
	 * 向商户推送消息
	 * 
	 * @param notifyPara
	 */
	void sendNotice(NotifyPara notifyPara);

}
