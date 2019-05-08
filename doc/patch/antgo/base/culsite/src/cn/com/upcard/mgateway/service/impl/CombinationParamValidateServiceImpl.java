package cn.com.upcard.mgateway.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.PayLimitType;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.controller.request.AlipayJsapiParam;
import cn.com.upcard.mgateway.controller.request.AlipayNativePayParam;
import cn.com.upcard.mgateway.controller.request.MicroPayRequest;
import cn.com.upcard.mgateway.controller.request.WxJsPayParam;
import cn.com.upcard.mgateway.controller.request.WxNativePayParam;
import cn.com.upcard.mgateway.controller.response.BaseResponse;
import cn.com.upcard.mgateway.model.CodeMsg;
import cn.com.upcard.mgateway.service.CombinationParamValidateService;
import cn.com.upcard.mgateway.util.DateUtil;
@Service
public class CombinationParamValidateServiceImpl implements CombinationParamValidateService {
	private static Logger logger = LoggerFactory.getLogger(CombinationParamValidateServiceImpl.class);
	
	private void validatePayTypeLimit(String payTypeLimit, CodeMsg<?> codeMsg) {
		if (codeMsg == null || !Constants.OK.equals(codeMsg.getCode())) {
			return ;
		}
		
		if (StringUtils.isEmpty(payTypeLimit)) {
			codeMsg.setCode(Constants.OK);
			codeMsg.setMsg("验证通过");
			return;
		}
		
		logger.info("payTypeLimit:{}", payTypeLimit);
		PayLimitType type = PayLimitType.topayLimitType(payTypeLimit);
		if (type == null) {
			codeMsg.setCode(ResponseResult.UNSUPPORTED_SERVICE.getCode());
			codeMsg.setMsg("不支持的支付限制类型");
			return;
		}
		codeMsg.setCode(Constants.OK);
		codeMsg.setMsg("验证通过");
	}

	private void validateOrderTime(String startTime, String expiredTime, CodeMsg<?> codeMsg) {
		if (codeMsg == null || !Constants.OK.equals(codeMsg.getCode())) {
			return;
		}

		if (StringUtils.isEmpty(startTime) ^ StringUtils.isEmpty(expiredTime)) {
			codeMsg.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
			codeMsg.setMsg("订单开始时间和订单过期时间必须同时为空或者同时不为空");
			return;
		}

		Date startDate = null;
		Date expiredDate = null;
		if (!StringUtils.isEmpty(startTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.yyyyMMddHHmmss);
			sdf.setLenient(false);
			try {
				startDate = sdf.parse(startTime);
			} catch (ParseException e) {
				e.printStackTrace();
				codeMsg.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
				codeMsg.setMsg("订单开始时间格式不正确");
				return;
			}
		}

		if (!StringUtils.isEmpty(expiredTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.yyyyMMddHHmmss);
			sdf.setLenient(false);
			try {
				expiredDate = sdf.parse(expiredTime);
			} catch (ParseException e) {
				e.printStackTrace();
				codeMsg.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
				codeMsg.setMsg("订单过期时间格式不正确");
				return;
			}

			// 订单过期时间不能早于当前时间
			if (expiredDate.before(new Date())) {
				codeMsg.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
				codeMsg.setMsg("订单过期时间早于当前时间");
				return;
			}
		}

		if (startDate != null && expiredDate != null && !startDate.before(expiredDate)) {
			codeMsg.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
			codeMsg.setMsg("订单过期时间必须大于订单生成时间");
			return;
		}
		codeMsg.setCode(Constants.OK);
		codeMsg.setMsg("验证通过");
	}

	public CodeMsg<?> validateAlipayJsapiParam(AlipayJsapiParam param) {
		CodeMsg<Object> codeMsg = new CodeMsg<Object>(Constants.OK, "验证通过");
		this.validatePayTypeLimit(param.getPayTypeLimit(), codeMsg);
		this.validateOrderTime(param.getTimeStart(), param.getTimeExpire(), codeMsg);
		return codeMsg;
	}
	
	public CodeMsg<?> validateAlipayNativePayParam(AlipayNativePayParam param) {
		CodeMsg<Object> codeMsg = new CodeMsg<Object>(Constants.OK, "验证通过");
		this.validatePayTypeLimit(param.getPayTypeLimit(), codeMsg);
		this.validateOrderTime(param.getTimeStart(), param.getTimeExpire(), codeMsg);
		return codeMsg;
	}
	
	public CodeMsg<?> validateWxNativePayParam(WxNativePayParam param) {
		CodeMsg<Object> codeMsg = new CodeMsg<Object>(Constants.OK, "验证通过");
		this.validatePayTypeLimit(param.getPayTypeLimit(), codeMsg);
		this.validateOrderTime(param.getTimeStart(), param.getTimeExpire(), codeMsg);
		return codeMsg;
	}
	
	public CodeMsg<?> validateWxJsPayParam(WxJsPayParam param) {
		CodeMsg<Object> codeMsg = new CodeMsg<Object>(Constants.OK, "验证通过");
		this.validatePayTypeLimit(param.getPayTypeLimit(), codeMsg);
		this.validateOrderTime(param.getTimeStart(), param.getTimeExpire(), codeMsg);
		return codeMsg;
	}
	
	public CodeMsg<?> validateMicroPayRequest(MicroPayRequest param) {
		CodeMsg<Object> codeMsg = new CodeMsg<Object>(Constants.OK, "验证通过");
		this.validateOrderTime(param.getTimeStart(), param.getTimeExpire(), codeMsg);
		return codeMsg;
	}
}
