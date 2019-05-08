package cn.com.upcard.mgateway.async.notify.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.com.upcard.mgateway.async.notify.thread.factory.SimpleThreadFactory;

/**
 * 用于实时发送消息通知
 * 
 * @author zhoudi
 * 
 */
public class NotifyExcecutor {

	private static final int poolSize = 20;
	private static final int queueSize = 2000;
	private static final String poolName = "notify-first";
	private static final ThreadPoolExecutor notifyThreadPool = new ThreadPoolExecutor(
			poolSize, poolSize, 0L,
			TimeUnit.MILLISECONDS, 
			new LinkedBlockingQueue<Runnable>(queueSize), 
			new SimpleThreadFactory(poolName));

	public static ExecutorService getNotifythreadpool() {
		return notifyThreadPool;
	}

}