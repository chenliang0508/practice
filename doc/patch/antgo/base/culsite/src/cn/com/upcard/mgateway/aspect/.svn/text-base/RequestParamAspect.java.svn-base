package cn.com.upcard.mgateway.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.com.upcard.mgateway.annotion.HttpParamName;
import cn.com.upcard.mgateway.annotion.Validator;
import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.Required;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.controller.request.BaseParam;
import cn.com.upcard.mgateway.controller.response.BaseResponse;
import cn.com.upcard.mgateway.exception.GatewayBusinessException;
@Component
public class RequestParamAspect {

	private static Logger logger = LoggerFactory.getLogger(RequestParamAspect.class);
	private static final String VALIDA_ERROR_MSG_FOMATTER = 
		"{\"mgatewayResponse\": {\"status\":\"0\",\"code\":\"{code}\",\"msg\":\"{msg}\"}}";

	public Object paramValidator(ProceedingJoinPoint point) {
		Signature signature = point.getSignature();
		if (!(signature instanceof MethodSignature)) {
			throw new IllegalArgumentException("该注解只能用于方法");
		}
		MethodSignature msig = (MethodSignature) signature;
		Object target = point.getTarget();
		try {
			// 接口方法
			Method currentMethod = target.getClass().getMethod(msig.getMethod().getName(),
					msig.getMethod().getParameterTypes());
			logger.info("currentMethod:" + currentMethod.getName());
			// 获取注解
			Validator annotation = currentMethod.getAnnotation(Validator.class);
			Object[] args = point.getArgs();
			// request
			if (annotation != null) {
				BaseResponse cm = checkParam(args);
				if (cm != null && !(Constants.OK.equals(cm.getCode()))) {
					logger.info("code:{}, msg:{}", cm.getCode(), cm.getMsg());
					return VALIDA_ERROR_MSG_FOMATTER.replace("{code}", cm.getCode())
							.replace("{msg}", cm.getMsg());
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		Object obj = null;
		try {
			obj = point.proceed();
		} catch (Throwable e) {
			logger.error("Throwable", e);
			if (e instanceof GatewayBusinessException) {
				throw (GatewayBusinessException)e;
			}
			throw new RuntimeException("系统异常");
		}
		return obj;
	}

	private BaseResponse checkParam(Object[] args) {
		//获取方法入参中的HttpServletRequest和BaseParam
		HttpServletRequest req = null;
		BaseParam param = null;
		for (Object obj : args) {
			logger.debug("args class name:" + obj.getClass().getName());
			if (obj instanceof HttpServletRequest) {
				req = (HttpServletRequest) obj;
				continue;
			} else if (obj instanceof BaseParam) {
				param = (BaseParam)obj;
				continue;
			}
		}
		if (req == null) {
			logger.error("no parameter of type [avax.servlet.http.HttpServletRequest], plase delete Validator Annotation");
			throw new RuntimeException("no parameter of type [avax.servlet.http.HttpServletRequest], plase delete Validator Annotation");
		}
		if (param == null) {
			logger.error("no parameter of type [cn.com.upcard.mgateway.controller.request.BaseParam], plase delete Validator Annotation");
			throw new RuntimeException("no parameter of [cn.com.upcard.mgateway.controller.request.BaseParam], plase delete Validator Annotation");
		}
		
		// 根据BaseParam中属性的注解验证请求参数是否正确
		List<Field> fieldList = new ArrayList<Field>();
		if (param.getClass().getSuperclass() != Object.class) {
			Field[] parentFields = param.getClass().getSuperclass().getDeclaredFields();
			fieldList.addAll(Arrays.asList(parentFields));
		}
		
		Field[] childFields = param.getClass().getDeclaredFields();
		fieldList.addAll(Arrays.asList(childFields));
		Field.setAccessible(fieldList.toArray(new Field[] {}), true);
		for (int i = 0; i < fieldList.size(); i++) {
			HttpParamName ann = fieldList.get(i).getAnnotation(HttpParamName.class);// 获取field上的注解
			if(ann == null) {
				continue;
			}
			BaseResponse cm = validateFiled(ann, param, fieldList.get(i), req);
			if (cm != null && !("00".equals(cm.getCode()))) {
				logger.debug("cm.getcode-->" + cm.getCode() + ",msg:" + cm.getMsg());
				return cm;
			}
			
		}
		return null;
	}

	private BaseResponse validateFiled(HttpParamName httpParamName, Object obj, Field field, HttpServletRequest req) {
		// 设置权限
		field.setAccessible(true);
		String str = req.getParameter(httpParamName.name());
		logger.debug("httpParamName.name.value-->" + str);
		if (Required.REQUIRE.equals(httpParamName.param())) {
			if (StringUtils.isEmpty(str)) {
				return new BaseResponse(ResponseResult.PARAM_IS_NULL.getCode(), ResponseResult.PARAM_IS_NULL.getMsg().replace("{field}", httpParamName.name()));
			}
		}

		if (StringUtils.isNotEmpty(str)) {
			if (!Integer.valueOf(httpParamName.maxLen()).equals(Integer.MAX_VALUE)) {
				if (httpParamName.maxLen() > 0 && (str.getBytes().length > httpParamName.maxLen())) {
					return new BaseResponse(ResponseResult.PARAM_OVER_LENGTH.getCode(), ResponseResult.PARAM_OVER_LENGTH.getMsg().replace("{field}", httpParamName.name()));
				}
			}
			if (!Integer.valueOf(httpParamName.minLen()).equals(Integer.MIN_VALUE)) {
				if (httpParamName.minLen() > 0 && (str.getBytes().length < httpParamName.minLen())) {
					return new BaseResponse(ResponseResult.PARAM_LENGTH_SHORT.getCode(), ResponseResult.PARAM_LENGTH_SHORT.getMsg().replace("{field}", httpParamName.name()));
				}
			}

			if (httpParamName.unSignedInt()) {
				try {
					if(Integer.valueOf(str) < 0) {
						return new BaseResponse(ResponseResult.PARAM_POSITIVE_INTEGER.getCode(), ResponseResult.PARAM_POSITIVE_INTEGER.getMsg().replace("{field}", httpParamName.name()));
					}
				} catch (Exception e) {
					return new BaseResponse(ResponseResult.PARAM_POSITIVE_INTEGER.getCode(), ResponseResult.PARAM_POSITIVE_INTEGER.getMsg().replace("{field}", httpParamName.name()));
				}
			}

			if (httpParamName.minValue() > -9999) {
				try {
					Long.valueOf(str);
				} catch (Exception e) {
					return new BaseResponse(ResponseResult.PARAM_POSITIVE_INTEGER.getCode(), ResponseResult.PARAM_POSITIVE_INTEGER.getMsg().replace("{field}", httpParamName.name()));
				}
				if (Long.valueOf(str).compareTo(httpParamName.minValue()) < 0) {
					return new BaseResponse(ResponseResult.PARAM_GREATER.getCode(), ResponseResult.PARAM_GREATER.getMsg().replace("{field}", httpParamName.name()).replace("{minvalue}", String.valueOf(httpParamName.minValue())));
				}
			}

			if (httpParamName.maxValue() < 999999999) {
				try {
					Long.valueOf(str);
				} catch (Exception e) {
					return new BaseResponse(ResponseResult.PARAM_POSITIVE_INTEGER.getCode(), ResponseResult.PARAM_POSITIVE_INTEGER.getMsg().replace("{field}", httpParamName.name()));
				}
				if (Long.valueOf(str).compareTo(httpParamName.maxValue()) > 0) {
					return new BaseResponse(ResponseResult.PARAM_LESS.getCode(), ResponseResult.PARAM_LESS.getMsg().replace("{field}", httpParamName.name()).replace("{maxvalue}", String.valueOf(httpParamName.maxValue())));
				}
			}

			if (StringUtils.isNotEmpty(httpParamName.restrict())) {
				if (!str.matches(httpParamName.restrict())) {
					return new BaseResponse(ResponseResult.PARAM_INCONFORMITY.getCode(), ResponseResult.PARAM_INCONFORMITY.getMsg().replace("{field}", httpParamName.name()).replace("{match}", httpParamName.restrict()));
				}
			}
			
			if(StringUtils.isNotEmpty(httpParamName.date())) {
				try{
					SimpleDateFormat sdf = new SimpleDateFormat(httpParamName.date());
					sdf.setLenient(false);
					sdf.parse(str);
				}catch (ParseException e) {
					return new BaseResponse(ResponseResult.PARAM_IS_DATE.getCode(), ResponseResult.PARAM_IS_DATE.getMsg().replace("{field}", httpParamName.name()).replace("{match}", httpParamName.date()));
				}
				
			}
		}
		try {
			field.set(obj, str);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return new BaseResponse("00", "通过");
	}
}
