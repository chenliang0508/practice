package cn.com.upcard.mgateway.async.notify.thread;

import cn.com.upcard.mgateway.async.notify.holder.NotifyServiceHolder;
import cn.com.upcard.mgateway.async.notify.model.NotifyPara;

public class NotifyThread extends Thread {

	private NotifyPara notifyPara;

	public NotifyThread(NotifyPara notifyPara) {
		this.notifyPara = notifyPara;
	}

	@Override
	public void run() {
		NotifyServiceHolder.getNotifyService().sendNotice(notifyPara);
	}

}
