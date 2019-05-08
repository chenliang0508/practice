package cn.com.upcard.mgateway.channel.bank.cib;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibConstants;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibPayLimitType;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibServiceType;
import cn.com.upcard.mgateway.channel.bank.cib.sdk.util.SignUtils;
import cn.com.upcard.mgateway.channel.bank.cib.sdk.util.XmlUtils;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;
import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.OrderStatus;
import cn.com.upcard.mgateway.common.enums.PayLimitType;
import cn.com.upcard.mgateway.exception.UnSupportApiException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

@Component
public class AlipayNativeTradeOperImpl  extends AbstractUnifiedOper implements ThirdTradeOper {
	private static Logger logger = LoggerFactory.getLogger(AlipayNativeTradeOperImpl.class);
	private static final String SUCCESS = "0";
	private static final String VALID_SIGN_ERROR = "system error:valid sign error!";

	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("service", CibServiceType.ALIPAY_NATIVE_PAY.getService());
		map.put("version", CibConstants.VERSION);
		map.put("charset", CibConstants.CHARSET);
		map.put("sign_type", CibConstants.SIGN_TYPE);
		map.put("mch_id", channelRequestInfo.getMchId());
		map.put("out_trade_no", channelRequestInfo.getOutTradeNo());
		map.put("device_info", channelRequestInfo.getDeviceInfo());
		map.put("body", channelRequestInfo.getBody());
		map.put("attach", channelRequestInfo.getExtendInfo());
		map.put("total_fee", channelRequestInfo.getTotalFee());
		map.put("mch_create_ip", channelRequestInfo.getReqIp());
		map.put("notify_url", channelRequestInfo.getNotifyUrl());
		map.put("limit_credit_pay", CibPayLimitType.toCibPayLimitType(PayLimitType.topayLimitType(channelRequestInfo.getLimitCreditPay())));
		map.put("time_start", channelRequestInfo.getTimeStart());
		map.put("time_expire", channelRequestInfo.getTimeExpire());
		map.put("op_user_id", channelRequestInfo.getOpUserId());
		map.put("goods_tag", channelRequestInfo.getGoodsTag());
		map.put("product_id", channelRequestInfo.getProductId());
		map.put("nonce_str", String.valueOf(DateUtil.now().getTime()));
		String sign = SignUtils.generateSign(map, channelRequestInfo.getCommPrivateKey());
		map.put("sign", sign);
		logger.debug("发送前的数据:" + map);
		Map<String, String> resultMap = null;
		try {
			resultMap = HttpClient.sendXmlPost(map, SysPara.SWIFT_REQ_URL);
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}
		String res = XmlUtils.toXml(resultMap);
		logger.info("兴业支付宝C扫B预下单接口返回 res ---> \r\n" + res);
		ChannelResponseInfo channelReponseInfo = new ChannelResponseInfo(Constants.OK, "接口调用成功.");
		String status = resultMap.get("status");
		String message = resultMap.get("message");
		if (!SUCCESS.equals(status)) {
			logger.error("status={} , message={}", status, message);
			channelReponseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			channelReponseInfo.setMsg(message);
			return channelReponseInfo;
		}
		if (!SignUtils.checkParam(resultMap, channelRequestInfo.getCommPrivateKey())) {
			channelReponseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			channelReponseInfo.setMsg(VALID_SIGN_ERROR);
			return channelReponseInfo;
		}
		String result_code = resultMap.get("result_code");
		String err_code = resultMap.get("err_code");
		String err_msg = resultMap.get("err_msg");
		if (!SUCCESS.equals(result_code)) {
			logger.info("errCode={} , errMsg={}", err_code, err_msg);
			channelReponseInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelReponseInfo.setMsg(err_msg);
			channelReponseInfo.setTradeStatus(OrderStatus.FAILED.name());
			return channelReponseInfo;
		}

		String mch_id = resultMap.get("mch_id");
		// 当 status和result_code都为0的时候有返回
		String device_info = resultMap.get("device_info");
		String code_url = resultMap.get("code_url"); // 二维码链接
		String code_img_url = resultMap.get("code_img_url");// 二维码图片
		channelReponseInfo.setMchId(mch_id);
		channelReponseInfo.setDeviceInfo(device_info);
		channelReponseInfo.setTradeStatus(OrderStatus.SUCCESS.name());
		channelReponseInfo.setAuthCodeImgUrl(code_img_url);
		channelReponseInfo.setAuthCodeUrl(code_url);
		return channelReponseInfo;
	}

	@Override
	public ChannelResponseInfo close(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("service", CibServiceType.UNIFIED_TRADE_CLOSE.getService());
		map.put("version", CibConstants.VERSION);
		map.put("charset", CibConstants.CHARSET);
		map.put("sign_type", CibConstants.SIGN_TYPE);
		map.put("mch_id", channelRequestInfo.getMchId());
		map.put("out_trade_no", channelRequestInfo.getOutTradeNo());
		map.put("nonce_str", String.valueOf(System.currentTimeMillis()));
		String sign = SignUtils.generateSign(map, channelRequestInfo.getCommPrivateKey());
		map.put("sign", sign);

		Map<String, String> resultMap = null;
		try {
			resultMap = HttpClient.sendXmlPost(map, SysPara.SWIFT_REQ_URL);
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}
		String res = XmlUtils.toXml(resultMap);
		logger.info("兴业支付宝C扫B关闭订单返回 res ---> \r\n" + res);
		String status = resultMap.get("status");
		String message = resultMap.get("message");
		ChannelResponseInfo channelReponseInfo = new ChannelResponseInfo(Constants.OK, "接口调用成功.");
		if (!SUCCESS.equals(status)) {
			logger.error("status={} , message={}", status, message);
			channelReponseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			channelReponseInfo.setMsg(message);
			return channelReponseInfo;
		}
		if (!SignUtils.checkParam(resultMap, channelRequestInfo.getCommPrivateKey())) {
			channelReponseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			channelReponseInfo.setMsg(VALID_SIGN_ERROR);
			return channelReponseInfo;
		}
		// 0表示成功非0表示失败；
		// 0表示关单成功，此笔订单不能再发起支付；若已支付完成，则会发起退款；非0或其它表示关单接口异常，可再次发起关单操作；
		String result_code = resultMap.get("result_code");
		//String err_code = resultMap.get("err_code");
		String err_msg = resultMap.get("err_msg");
		if (!SUCCESS.equals(result_code)) {
			channelReponseInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelReponseInfo.setMsg(err_msg);
			return channelReponseInfo;
		}
		String mch_id = resultMap.get("mch_id");
		channelReponseInfo.setMchId(mch_id);
		channelReponseInfo.setTradeStatus(ChannelTradeStatus.CLOSED.name());
		channelReponseInfo.setTradeEndDate(DateUtil.formatCompactDate(DateUtil.now()));
		channelReponseInfo.setTradeEndTime(DateUtil.formatCompactTime(DateUtil.now()));
		return channelReponseInfo;
	}
	
	@Override
	public ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo) {
		throw new UnSupportApiException("原支付方式不支持撤销操作");
	}
}
