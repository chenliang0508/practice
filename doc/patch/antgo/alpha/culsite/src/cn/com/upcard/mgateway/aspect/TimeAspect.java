package cn.com.upcard.mgateway.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.com.upcard.mgateway.util.TimeProfiler;

@Component
public class TimeAspect {
	private static Logger logger = LoggerFactory.getLogger(TimeAspect.class);

	public void begin() {
		logger.info("begin time in mills:" + System.currentTimeMillis());
		TimeProfiler.begin();
	}
	
	public void end() {
		logger.info("end time in mills:" + TimeProfiler.end());
	}
}
