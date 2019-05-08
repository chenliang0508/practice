package cn.com.upcard.mgateway.async.task.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.com.upcard.mgateway.async.notify.thread.factory.SimpleThreadFactory;

/**
 * 用于退货结果查询
 * 
 * @author zhoudi
 * 
 */
public class RefundQueryExcecutor {

	private static final int poolSize = 2;
	private static final int queueSize = 2000;
	private static final String poolName = "refund-query-task";
	private static final ThreadPoolExecutor refundQueryThreadPool = new ThreadPoolExecutor(
			poolSize, poolSize, 0L,
			TimeUnit.MILLISECONDS, 
			new LinkedBlockingQueue<Runnable>(queueSize), 
			new SimpleThreadFactory(poolName));

	public static ExecutorService getRefundQueryThreadPool() {
		return refundQueryThreadPool;
	}

}