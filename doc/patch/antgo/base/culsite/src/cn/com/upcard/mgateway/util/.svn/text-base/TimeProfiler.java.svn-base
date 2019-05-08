package cn.com.upcard.mgateway.util;

public class TimeProfiler {
	private static final ThreadLocal<Long> TIME_THREAD = new ThreadLocal<Long>() {
		protected Long initialValue() {
			return System.currentTimeMillis();
		}
	};
	
	public static final void begin() {
		TIME_THREAD.set(System.currentTimeMillis());
	}
	
	public static final Long end() {
		return System.currentTimeMillis() - TIME_THREAD.get();
	}
	
//	public static void main(String[] args) {
//		TimeProfiler.begin();
//		try {
//			TimeUnit.SECONDS.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("cost" + TimeProfiler.end() + "mills");
//	}
}
