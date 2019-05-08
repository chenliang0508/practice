package cn.com.upcard.mgateway.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.upcard.mgateway.util.HttpRequestUtils;

public class HttpRequestLogInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(HttpRequestLogInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			RequestMapping rm = method.getMethodAnnotation(RequestMapping.class);
			//通过判断method是否有@RequestMapping ，只输出controller中的每个标记@RequestMapping的日志
			if (null != rm) {
				logger.info("-------start to record httpRequest log--------");
				HttpRequestUtils.logHttpRequestParams(request);
				logger.info("-------end of httpRequest log---------");
			}
		}
		return true;
	}

}
