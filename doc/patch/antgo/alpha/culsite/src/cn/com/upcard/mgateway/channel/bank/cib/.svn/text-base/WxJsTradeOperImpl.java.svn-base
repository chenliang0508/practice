package cn.com.upcard.mgateway.channel.bank.cib;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibConstants;
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
 * 兴业公众号和小程序支付
 * 
 * @author zhoudi
 * 
 */
public class WxJsTradeOperImpl extends AbstractUnifiedOper implements ThirdTradeOper {

	private static Logger logger = LoggerFactory.getLogger(WxJsTradeOperImpl.class);

	private static final String THIRD_RETURN_SUCCESS = "0";
	private static final String SUCCESS_MSG = "success";
	private static final String VALID_SIGN_ERROR = "system error:valid sign error!";

	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo requestInfo) {

		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("service", CibServiceType.WX_JS_PAY_SERVICE.getService()); // 接口类型
		map.put("mch_id", requestInfo.getMchId()); // 兴业分配的商户号
		map.put("is_raw", requestInfo.getIsRaw()); // 是否原生支付，1：是，0：否，默认0
		map.put("is_minipg", requestInfo.getIsMinipg()); // 是否小程序支付，1：小程序，非1公众号
		map.put("out_trade_no", requestInfo.getOutTradeNo()); // 银商订单号
		map.put("device_info", requestInfo.getDeviceInfo()); // 设备号
		map.put("body", requestInfo.getBody()); // 商品描述
		map.put("sub_openid", requestInfo.getSubOpenid()); // 用户openid
		map.put("sub_appid", requestInfo.getSubAppid()); // 公众号或者小程序id
		map.put("attach", requestInfo.getExtendInfo()); // 附加信息
		map.put("total_fee", requestInfo.getTotalFee()); // 订单金额
		map.put("mch_create_ip", requestInfo.getReqIp()); // 终端ip
		map.put("notify_url", requestInfo.getNotifyUrl()); // 通知url
		if (!StringUtils.isEmpty(requestInfo.getCallbackUrl())) {
			try {
				// 前台地址
				map.put("callback_url", URLEncoder.encode(requestInfo.getCallbackUrl(), CibConstants.CHARSET));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		map.put("time_start", requestInfo.getTimeStart()); // 订单生成时间
		map.put("time_expire", requestInfo.getTimeExpire()); // 订单超时时间
		map.put("goods_tag", requestInfo.getGoodsTag()); // 商品标记
		map.put("limit_credit_pay", CibPayLimitType.toCibPayLimitType(PayLimitType.topayLimitType(requestInfo.getLimitCreditPay()))); // 是否限制信用卡，1禁用，0不禁用，默认不禁用
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
		String appid = resultMap.get("appid"); // 服务商公众号APPID

		responseInfo.setAppid(appid);

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
		String mchId = resultMap.get("mch_id"); // 商户号
		String deviceInfo = resultMap.get("device_info"); // 设备号
		String errCode = resultMap.get("err_code");
		String errMsg = resultMap.get("err_msg");

		responseInfo.setMchId(mchId);
		responseInfo.setDeviceInfo(deviceInfo);

		if (!THIRD_RETURN_SUCCESS.equals(resultCode)) {
			logger.info("errCode={} , errMsg={}", errCode, errMsg);
			ResponseResult responseResult = CibResponseCode.transferCode(errCode);
			responseInfo.setResponseResult(responseResult);
			responseInfo.setCode(responseResult.getCode());
			responseInfo.setMsg(responseResult.getMsg());
			responseInfo.setTradeStatus(OrderStatus.FAILED.name());
			return responseInfo;
		}

		// status和result_code为0的时候返回
		String payInfo = resultMap.get("pay_info"); // 原生态js支付信息或小程序支付信息
		String tokenId = resultMap.get("token_id"); // 公司决定不做非原生的支付，因此不返回

		responseInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
		responseInfo.setMsg(SUCCESS_MSG);
		responseInfo.setTradeStatus(OrderStatus.SUCCESS.name());
		responseInfo.setPayInfo(payInfo);
		responseInfo.setTokenId(tokenId);

		return responseInfo;
	}

	@Override
	public ChannelResponseInfo close(ChannelRequestInfo requestInfo) {

		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("service", CibServiceType.WX_JS_CLOSE_SERVICE.getService()); // 接口类型
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

		if (!"0".equals(status)) {
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
	public ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo) {
		throw new UnSupportApiException("原支付方式不支持撤销操作");
	}

}
