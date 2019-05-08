package cn.com.upcard.mgateway.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumpUtils {
	private static Logger logger = LoggerFactory.getLogger(DumpUtils.class);

	public static void dumpRequest(HttpServletRequest request) {
		Map<String, String[]> requestMap = request.getParameterMap();
		logger.info("request URL: " + request.getRequestURL());

		for (String key : requestMap.keySet()) {
			Object object = requestMap.get(key);
			if (object.getClass().isArray()) {
				String[] ret = (String[]) object;
				logger.info(key + ": " + ret[0]);
			} else {
				logger.info(key + ": " + object);
			}

		}
	}
}
