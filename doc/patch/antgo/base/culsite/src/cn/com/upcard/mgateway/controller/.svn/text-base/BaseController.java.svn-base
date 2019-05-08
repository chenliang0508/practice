package cn.com.upcard.mgateway.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.upcard.mgateway.common.RequestParameter;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.common.enums.ResponseType;
import cn.com.upcard.mgateway.controller.response.BaseResponse;
import cn.com.upcard.mgateway.exception.OrderNoRepeatException;
import cn.com.upcard.mgateway.exception.UnSupportApiException;
import cn.com.upcard.mgateway.exception.UndefinedTradeTypeException;
import cn.com.upcard.mgateway.util.DumpUtils;

public class BaseController {
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	protected static final String ILLEGAL_TYPE = "illegal type";

	/**
	 * @param type
	 * @param obj
	 * @return
	 */
	public String response(String type, Object obj, String commKey) {
		if (ResponseType.JSON.name().equalsIgnoreCase(type)) {
			Map<String, String> response = new HashMap<String, String>();
			String mgatewayResponse = JSONObject.toJSONString(obj);
			response.put("mgatewayResponse", mgatewayResponse);
			
			String[] signParam = {mgatewayResponse, commKey};
			logger.debug("加密前的字符串:" + StringUtils.join(signParam));
			String sign = DigestUtils.md5Hex(StringUtils.join(signParam));
			response.put("sign", sign);
			String ret = JSONObject.toJSONString(response);
			logger.info(ret);
			return ret;
		}
		return ILLEGAL_TYPE + ":" + type;
	}	
	public String response(String type, Object obj) {
		if (ResponseType.JSON.name().equalsIgnoreCase(type)) {
			Map<String, String> response = new HashMap<String, String>();
			String mgatewayResponse = JSONObject.toJSONString(obj);
			response.put("mgatewayResponse", mgatewayResponse);
			String ret = JSONObject.toJSONString(response);
			logger.info(ret);
			return ret;
		}
		return ILLEGAL_TYPE + ":" + type;
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public String exceptionHandler(Exception e, HttpServletRequest request) {
		String responseType = request.getParameter(RequestParameter.RETURN_TYPE);
		if(e instanceof OrderNoRepeatException) {
			return response(responseType, new BaseResponse(ResponseResult.OUT_TRADE_NO_IS_EXISTS));
		}
		if(e instanceof UndefinedTradeTypeException) {
			return response(responseType, new BaseResponse(ResponseResult.UNDEFINED_TRADETYPE_OR_BANKCODE));
		}
		if(e instanceof UnSupportApiException) {
			return response(responseType, new BaseResponse(ResponseResult.UNSUPPORT_API));
		}
		DumpUtils.dumpRequest(request);
		e.printStackTrace();
		return response(responseType, new BaseResponse(ResponseResult.SYSTEM_ERROR));
	}
//	public String buissnessProcess(BaseParam basePram, ServiceType serviceType) {
//		//1、验签，权限
//		CodeMsg<CommercialArguments> cm = validatorParamAndSign(basePram, serviceType);
//		//2、构造商户请求的参数
//		PreTradeOrderInfo preTradeOrderInfo = preTradeOrderInstance(basePram, cm.getInfo());
//		//3、业务处理
//		BaseResponse base = process(preTradeOrderInfo);
//		//4、加签，返回
//		return response(base, cm.getInfo());
//	}
//	
//	protected abstract CodeMsg<CommercialArguments> validatorParamAndSign(BaseParam basePram, ServiceType serviceType);
//	protected abstract BaseResponse process(PreTradeOrderInfo preTradeOrderInfo);
//	protected abstract PreTradeOrderInfo preTradeOrderInstance(BaseParam basePram, CommercialArguments commercialArguments);
//	private String response(BaseResponse base, CommercialArguments commercialArguments) {
//		return null;
//	}
}
