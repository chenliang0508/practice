package cn.com.upcard.mgateway.service;

import cn.com.upcard.mgateway.controller.request.AlipayJsapiParam;
import cn.com.upcard.mgateway.controller.request.AlipayNativePayParam;
import cn.com.upcard.mgateway.controller.request.MicroPayRequest;
import cn.com.upcard.mgateway.controller.request.WxJsPayParam;
import cn.com.upcard.mgateway.controller.request.WxNativePayParam;
import cn.com.upcard.mgateway.model.CodeMsg;

public interface CombinationParamValidateService {
	CodeMsg<?> validateAlipayJsapiParam(AlipayJsapiParam param);
	
	CodeMsg<?> validateAlipayNativePayParam(AlipayNativePayParam param);
	
	CodeMsg<?> validateWxNativePayParam(WxNativePayParam param);
	
	CodeMsg<?> validateWxJsPayParam(WxJsPayParam param);
	
	CodeMsg<?> validateMicroPayRequest(MicroPayRequest param);
}
