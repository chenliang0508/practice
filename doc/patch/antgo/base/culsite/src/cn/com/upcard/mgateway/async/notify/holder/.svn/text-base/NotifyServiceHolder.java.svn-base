package cn.com.upcard.mgateway.async.notify.holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.upcard.mgateway.async.notify.service.NotifyService;

@Component("NotifyServiceHolder")
public class NotifyServiceHolder {

	private static NotifyService notifyService;

	public static NotifyService getNotifyService() {
		return notifyService;
	}
	@Autowired
	public void setNotifyService(NotifyService notifyService) {
		NotifyServiceHolder.notifyService = notifyService;
	}

}
