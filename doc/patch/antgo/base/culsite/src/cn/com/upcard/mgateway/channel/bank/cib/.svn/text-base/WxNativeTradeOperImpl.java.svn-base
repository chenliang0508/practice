package cn.com.upcard.mgateway.channel.bank.cib;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibPayLimitType;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibResponseCode;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibServiceType;
import cn.com.upcard.mgateway.channel.bank.cib.sdk.util.SignUtils;
import cn.com.upcard.mgateway.channel.bank.cib.sdk.util.XmlUtils;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.common.enums.OrderStatus;
import cn.com.upcard.mgateway.common.enums.PayLimitType;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.exception.HttpNoResponseException;
import cn.com.upcard.mgateway.exception.UnSupportApiException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

/**
 * 兴业微信扫码支付
 * 
 * @author zhoudi
 * 
 */
public class WxNativeTradeOperImpl extends AbstractUnifiedOper implements ThirdTradeOper {

	private static Logger logger = LoggerFactory.getLogger(WxNativeTradeOperImpl.class);

	private static final String THIRD_RETURN_SUCCESS = "0";
	private static final String SUCCESS_MSG = "success";
	private static final String VALID_SIGN_ERROR = "system error:valid sign error!";

	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo requestInfo) {

		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("service", CibServiceType.WX_NATIVE_PAY_SERVICE.getService()); // 接口类型
		map.put("mch_id", requestInfo.getMchId()); // 兴业分配的商户号
		map.put("out_trade_no", requestInfo.getOutTradeNo()); // 银商订单号
		map.put("device_info", requestInfo.getDeviceInfo()); // 设备号
		map.put("body", requestInfo.getBody()); // 商品描述
		map.put("attach", requestInfo.getExtendInfo()); // 附加信息
		map.put("total_fee", requestInfo.getTotalFee()); // 总金额（分）
		map.put("mch_create_ip", requestInfo.getReqIp()); // 终端ip
		map.put("notify_url", requestInfo.getNotifyUrl()); // 通知地址
		map.put("time_start", requestInfo.getTimeStart()); // 订单生成时间，yyyyMMddHHmmss
		map.put("time_expire", requestInfo.getTimeExpire()); // 订单超时时间，yyyyMMddHHmmss
		map.put("op_user_id", requestInfo.getOpUserId()); // 操作员
		map.put("goods_tag", requestInfo.getGoodsTag()); // 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
		map.put("product_id", requestInfo.getProductId()); // 商品id
		map.put("limit_credit_pay", CibPayLimitType.toCibPayLimitType(PayLimitType.topayLimitType(requestInfo.getLimitCreditPay()))); // 是否限制使用信用卡，1：禁用，0：可用，默认可用

		map.put("nonce_str", String.valueOf(new Date().getTime()));
		map.put("sign", SignUtils.generateSign(map, requestInfo.getCommPrivateKey()));
		logger.info("map = {}", map);

		Map<String, String> resultMap = null;
		try {
			resultMap = HttpClient.sendXmlPost(map, SysPara.SWIFT_REQ_URL);
			logger.info("微信扫码支付调用兴业返回 res ---> \r\n" + XmlUtils.toXml(resultMap));
		} catch (HttpNoResponseException e) {
			logger.error("HttpNoResponseException", e);
			return null;
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}

		ChannelResponseInfo responseInfo = new ChannelResponseInfo();

		// 基础返回参数
		String status = resultMap.get("status");
		String message = resultMap.get("message"); // 如果非空，返回签名失败或者参数错误

		if (!THIRD_RETURN_SUCCESS.equals(status)) {
			logger.error("status={} , message={}", status, message);
			responseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			responseInfo.setMsg(message);
			return responseInfo;
		}
		if (!SignUtils.checkParam(resultMap, requestInfo.getCommPrivateKey())) {
			logger.info("Signature verification error");
			responseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			responseInfo.setMsg(VALID_SIGN_ERROR);
			return responseInfo;
		}

		// status为0的时候
		String resultCode = resultMap.get("result_code"); // 业务结果
		String mchId = resultMap.get("mch_id"); // 商户号
		String deviceInfo = resultMap.get("device_info"); // 终端号
		String errCode = resultMap.get("err_code"); // 参考错误码
		String errMsg = resultMap.get("err_msg"); // 结果信息描述

		responseInfo.setMchId(mchId);
		responseInfo.setDeviceInfo(deviceInfo);

		if (!THIRD_RETURN_SUCCESS.equals(resultCode)) {
			logger.error("errCode = {}，errMsg = {}", errCode, errMsg);
			ResponseResult responseResult = CibResponseCode.transferCode(errCode);
			responseInfo.setResponseResult(responseResult);
			responseInfo.setCode(responseResult.getCode());
			responseInfo.setMsg(responseResult.getMsg());
			responseInfo.setTradeStatus(OrderStatus.FAILED.name());
			return responseInfo;
		}

		// 当 status和result_code都为0的时候有返回
		String codeUrl = resultMap.get("code_url"); // 二维码链接
		String codeImgUrl = resultMap.get("code_img_url");// 二维码图片

		responseInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
		responseInfo.setMsg(SUCCESS_MSG);
		responseInfo.setTradeStatus(OrderStatus.SUCCESS.name());
		responseInfo.setAuthCodeUrl(codeUrl);
		responseInfo.setAuthCodeImgUrl(codeImgUrl);

		return responseInfo;
	}

	@Override
	public ChannelResponseInfo close(ChannelRequestInfo requestInfo) {

		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("service", CibServiceType.WX_NATIVE_CLOSE_SERVICE.getService());
		map.put("mch_id", requestInfo.getMchId());
		map.put("out_trade_no", requestInfo.getOutTradeNo());
		map.put("nonce_str", String.valueOf(new Date().getTime()));
		map.put("sign", SignUtils.generateSign(map, requestInfo.getCommPrivateKey()));

		Map<String, String> resultMap = null;
		try {
			resultMap = HttpClient.sendXmlPost(map, SysPara.SWIFT_REQ_URL);
			logger.info("微信扫码支付调用兴业返回 res ---> \r\n" + XmlUtils.toXml(resultMap));
		} catch (HttpNoResponseException e) {
			logger.error("HttpNoResponseException", e);
			return null;
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}

		ChannelResponseInfo responseInfo = new ChannelResponseInfo();

		String status = resultMap.get("status");
		String message = resultMap.get("message");

		if (!THIRD_RETURN_SUCCESS.equals(status)) {
			logger.error("status={} , message={}", status, message);
			responseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			responseInfo.setMsg(message);
			return responseInfo;
		}
		if (!SignUtils.checkParam(resultMap, requestInfo.getCommPrivateKey())) {
			logger.info("Signature verification error");
			responseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			responseInfo.setMsg(VALID_SIGN_ERROR);
			return responseInfo;
		}

		// status为0的时候返回
		String resultCode = resultMap.get("result_code");
		String mchId = resultMap.get("mch_id");
		String errCode = resultMap.get("err_code");
		String errMsg = resultMap.get("err_msg");

		responseInfo.setMchId(mchId);

		if (!THIRD_RETURN_SUCCESS.equals(resultCode)) {
			logger.info("errCode={} , errMsg={}", errCode, errMsg);
			ResponseResult responseResult = CibResponseCode.transferCode(errCode);
			responseInfo.setResponseResult(responseResult);
			responseInfo.setCode(responseResult.getCode());
			responseInfo.setMsg(responseResult.getMsg());
			responseInfo.setTradeStatus(OrderStatus.FAILED.name());
			return responseInfo;
		}

		responseInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
		responseInfo.setMsg(SUCCESS_MSG);
		responseInfo.setTradeStatus(OrderStatus.CLOSED.name());

		return responseInfo;
	}

	@Override
	public ChannelResponseInfo cancel(ChannelRequestInfo requestInfo) {
		throw new UnSupportApiException("原支付方式不支持撤销操作");
	}

}