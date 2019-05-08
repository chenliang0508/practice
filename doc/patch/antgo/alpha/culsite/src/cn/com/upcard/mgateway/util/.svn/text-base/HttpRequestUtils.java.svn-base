package cn.com.upcard.mgateway.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

	/**
	 * 将request 中的所有参数都log 显示出来
	 * 
	 * @param request
	 */
	public static void logHttpRequestParams(HttpServletRequest request) {
		if (null == request) {
			return;
		}

		String qString = request.getQueryString();
		if (StringUtils.isEmpty(qString)) {
			qString = "";
		}

		logger.info("url:{}", request.getRequestURL() + "?" + qString);
		Map<String, String[]> map = request.getParameterMap();
		if (null == map) {
			return;
		}
		for (String key : map.keySet()) {
			logger.info("parameter:{}{}", key, map.get(key));
		}
	}
}
