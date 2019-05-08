package cn.com.upcard.mgateway.controller.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.Restrict;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.common.enums.ResponseType;
import cn.com.upcard.mgateway.common.enums.SignType;
import cn.com.upcard.mgateway.controller.request.MicroPayRequest;
import cn.com.upcard.mgateway.controller.response.MicroPayResponse;
import cn.com.upcard.mgateway.util.DateUtil;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class ValidateUtil {
	protected static final String ILLEGAL_TYPE = "illegal type";

	/**
	 * 校验商户支付请求参数,线下使用
	 * 
	 * @param bean
	 * @return
	 */
	public static String checkPayParams(MicroPayRequest bean, MicroPayResponse resp) {
		String returnType = bean.getReturnType();
		if (StringUtils.isEmpty(bean.getSignType())) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("签名类型不能为空");
			return response(returnType, resp);
		}
		if (!(SignType.MD5.name().equalsIgnoreCase(bean.getSignType()))) {
			resp.setCode(ResponseResult.PARAM_INCONFORMITY.getCode());
			resp.setMsg("签名类型格式不正确");
			return response(returnType, resp);
		}
		if (StringUtils.isEmpty(bean.getMchId())) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("商户号不能为空");
			return response(returnType, resp);
		}
		if (bean.getMchId().getBytes().length > 30) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("商户号字符长度不能大于30");
			return response(returnType, resp);
		}
		if (StringUtils.isEmpty(bean.getOutTradeNo())) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("商户订单号不能为空！");
			return response(returnType, resp);
		}
		if (bean.getOutTradeNo().getBytes().length > 32) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("商户订单号字符长度不能大于32");
			return response(returnType, resp);
		}
		if (!bean.getOutTradeNo().matches(Restrict.OUT_TRADE_NO)) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("商户订单号只能包含数字字母");
			return response(returnType, resp);
		}
		if (StringUtils.isNotEmpty(bean.getDeviceInfo()) && bean.getDeviceInfo().getBytes().length > 30) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("设备号字符长度不能大于30");
			return response(returnType, resp);
		}
		if (StringUtils.isEmpty(bean.getBody())) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("商品描述不能为空");
			return response(returnType, resp);
		}
		if (bean.getBody().getBytes().length > 127) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("商品描述字符长度不能大于127");
			return response(returnType, resp);
		}
		if (StringUtils.isNotEmpty(bean.getGoodsDetail()) && bean.getGoodsDetail().getBytes().length > 1024) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("商品信息字符长度不能大于1024");
			return response(returnType, resp);
		}
		if (StringUtils.isNotEmpty(bean.getGoodsDetail())) {
			try {
				JSONObject.parse(bean.getGoodsDetail());
			} catch (JSONException e) {
				resp.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
				resp.setMsg("单品信息必须是JSON格式,格式不正确");
				return response(returnType, resp);
			}
		}
		if (StringUtils.isNotEmpty(bean.getExtendParam()) && bean.getExtendParam().getBytes().length > 120) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("附加信息字符长度不能大于120");
			return response(returnType, resp);
		}
		if (StringUtils.isEmpty(bean.getTotalFee())) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("总金额不能为空");
			return response(returnType, resp);
		}
		if (!bean.getTotalFee().matches(Restrict.UNSIGNED_INT_AMOUNT)) {
			resp.setCode(ResponseResult.PARAM_INCONFORMITY.getCode());
			resp.setMsg("总金额只能为数字类型,长度不能超过10位数字");
			return response(returnType, resp);
		}
		if (!bean.getTotalFee().matches(Restrict.FEE_LIMIT)) {
			resp.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
			resp.setMsg("总金额不能小于1分");
			return response(returnType, resp);
		}
		if (StringUtils.isEmpty(bean.getMchCreateIp())) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("终端IP不能为空");
			return response(returnType, resp);
		}
		if (!bean.getMchCreateIp().matches(Restrict.IP_V4)) {
			resp.setCode(ResponseResult.PARAM_INCONFORMITY.getCode());
			resp.setMsg("IP不符合格式");
			return response(returnType, resp);
		}
		if (bean.getMchCreateIp().getBytes().length > 16) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("终端IP字符长度不能大于16");
			return response(returnType, resp);
		}
		if (StringUtils.isEmpty(bean.getAuthCode())) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("条码号不能为空");
			return response(returnType, resp);
		}
		if (!bean.getAuthCode().matches(Restrict.UNSIGNED_INT_AUTHCODE)) {
			resp.setCode(ResponseResult.PARAM_INCONFORMITY.getCode());
			resp.setMsg("条码号只能是数字类型");
			return response(returnType, resp);
		}
		if (bean.getAuthCode().getBytes().length > 30) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("条码号字符长度不能大于30");
			return response(returnType, resp);
		}
		if ((StringUtils.isNotEmpty(bean.getTimeStart()) && StringUtils.isEmpty(bean.getTimeExpire()))
				|| (StringUtils.isEmpty(bean.getTimeStart()) && StringUtils.isNotEmpty(bean.getTimeExpire()))) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("time_start与time_expire必须同时为空或不为空");
			return response(returnType, resp);
		}
		if (StringUtils.isNotEmpty(bean.getTimeStart()) && StringUtils.isNotEmpty(bean.getTimeExpire())) {
			if (!(bean.getTimeStart().getBytes().length == 14)) {
				resp.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
				resp.setMsg("time_start格式不正确");
				return response(returnType, resp);
			}
			if (!(bean.getTimeExpire().getBytes().length == 14)) {
				resp.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
				resp.setMsg("time_expire格式不正确");
				return response(returnType, resp);
			}
			if (!(DateUtil.parseCompactDateTime(bean.getTimeStart()).getTime() < DateUtil.parseCompactDateTime(
					bean.getTimeExpire()).getTime())) {
				resp.setCode(ResponseResult.PARAM_FORMAT_ERROR.getCode());
				resp.setMsg("time_start必须小于time_expire");
				return response(returnType, resp);
			}
			if (DateUtil.parseCompactDateTime(bean.getTimeExpire()).getTime() < DateUtil.now().getTime()) {
				resp.setCode(ResponseResult.PARAM_LESS.getCode());
				resp.setMsg("time_expire需要大于当前时间");
				return response(returnType, resp);
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.yyyyMMddHHmmss);
				sdf.setLenient(false);
				sdf.parse(bean.getTimeStart());
			} catch (ParseException e) {
				resp.setCode(ResponseResult.PARAM_IS_DATE.getCode());
				resp.setMsg("time_start格式不符合要求");
				return response(returnType, resp);
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.yyyyMMddHHmmss);
				sdf.setLenient(false);
				sdf.parse(bean.getTimeExpire());
			} catch (ParseException e) {
				resp.setCode(ResponseResult.PARAM_IS_DATE.getCode());
				resp.setMsg("time_expire格式不符合要求");
				return response(returnType, resp);
			}

		}

		if (StringUtils.isNotEmpty(bean.getOpUser()) && bean.getOpUser().getBytes().length > 20) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("操作员字段字符长度不能大于20");
			return response(returnType, resp);
		}
		if (StringUtils.isNotEmpty(bean.getMerchantNo()) && bean.getMerchantNo().getBytes().length > 20) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("门店编号字符长度不能大于20");
			return response(returnType, resp);
		}

		if (StringUtils.isNotEmpty(bean.getGoodsTag()) && bean.getGoodsTag().getBytes().length > 32) {
			resp.setCode(ResponseResult.PARAM_OVER_LENGTH.getCode());
			resp.setMsg("商品标记字符长度不能大于32");
			return response(returnType, resp);
		}

		if (StringUtils.isEmpty(bean.getTimestamp())) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("时间戳不能为空");
			return response(returnType, resp);
		}
		if (!bean.getTimestamp().matches(Restrict.TIMESTAMP)) {
			resp.setCode(ResponseResult.PARAM_IS_DATE.getCode());
			resp.setMsg("时间戳格式不正确");
			return response(returnType, resp);
		}
		if (StringUtils.isEmpty(bean.getSign())) {
			resp.setCode(ResponseResult.PARAM_IS_NULL.getCode());
			resp.setMsg("签名字符串不能为空");
			return response(returnType, resp);
		}
		return Constants.OK;
	}

	/**
	 * @param type
	 * @param obj
	 * @return
	 */
	public static String response(String type, Object obj) {
		if (ResponseType.JSON.name().equalsIgnoreCase(type)) {
			String ret = JSONObject.toJSONString(obj);
			return ret;
		}
		return ILLEGAL_TYPE + ":" + type;
	}

}
