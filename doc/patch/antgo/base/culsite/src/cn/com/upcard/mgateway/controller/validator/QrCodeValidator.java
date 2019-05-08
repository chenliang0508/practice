package cn.com.upcard.mgateway.controller.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.model.CodeMsg;

@Component
public class QrCodeValidator {
	
	public CodeMsg<?> validQrCodeOrderPay(HttpServletRequest request) {
		String mchId = request.getParameter("mchId");
		String qrToken = request.getParameter("qrToken");
		String timestamp = request.getParameter("timestamp");
		String sign = request.getParameter("sign");
		
		if (StringUtils.isEmpty(mchId)) {
			new CodeMsg<String>(ResponseResult.PARAM_IS_NULL.getCode(), ResponseResult.PARAM_IS_NULL.getMsg().replace("{field}", "mchId"));
		}
		if (StringUtils.isEmpty(qrToken)) {
			new CodeMsg<String>(ResponseResult.PARAM_IS_NULL.getCode(), ResponseResult.PARAM_IS_NULL.getMsg().replace("{field}", "qrToken"));
		}
		if (StringUtils.isEmpty(timestamp)) {
			new CodeMsg<String>(ResponseResult.PARAM_IS_NULL.getCode(), ResponseResult.PARAM_IS_NULL.getMsg().replace("{field}", "timestamp"));
		}
		if (StringUtils.isEmpty(sign)) {
			new CodeMsg<String>(ResponseResult.PARAM_IS_NULL.getCode(), ResponseResult.PARAM_IS_NULL.getMsg().replace("{field}", "sign"));
		}
		return new CodeMsg<String>(ResponseResult.OK);
	}
	
	public CodeMsg<?> validToQrCodeOrder(HttpServletRequest request) {
		String browserType = request.getParameter("browserType");
		String mchId = request.getParameter("mchId");
		String sign = request.getParameter("sign");
		
		if (StringUtils.isEmpty(mchId)) {
			new CodeMsg<String>(ResponseResult.PARAM_IS_NULL.getCode(), ResponseResult.PARAM_IS_NULL.getMsg().replace("{field}", "mchId"));
		}
		if (StringUtils.isEmpty(browserType)) {
			new CodeMsg<String>(ResponseResult.PARAM_IS_NULL.getCode(), ResponseResult.PARAM_IS_NULL.getMsg().replace("{field}", "browserType"));
		}
		if (StringUtils.isEmpty(sign)) {
			new CodeMsg<String>(ResponseResult.PARAM_IS_NULL.getCode(), ResponseResult.PARAM_IS_NULL.getMsg().replace("{field}", "sign"));
		}
		return new CodeMsg<String>(ResponseResult.OK);
	}
}
