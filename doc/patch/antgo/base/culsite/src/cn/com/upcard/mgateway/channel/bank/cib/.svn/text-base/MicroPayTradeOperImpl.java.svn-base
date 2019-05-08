package cn.com.upcard.mgateway.channel.bank.cib;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.xml.ws.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibConstants;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibResponseCode;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibServiceType;
import cn.com.upcard.mgateway.channel.bank.cib.sdk.util.SignUtils;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.exception.UnSupportApiException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

/**
 * 兴业银行刷卡支付接口实现类
 * 
 * @author haozm
 * 
 */
public class MicroPayTradeOperImpl extends AbstractUnifiedOper {
	private static Logger logger = LoggerFactory.getLogger(MicroPayTradeOperImpl.class);

	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo) {

		SortedMap<String, String> map = new TreeMap<String, String>();
		// 接口类型
		map.put("service", CibServiceType.MICROPAY_PAY_SERVICE.getService());
		map.put("version", CibConstants.VERSION);
		map.put("charset", CibConstants.CHARSET);
		map.put("sign_type", CibConstants.SIGN_TYPE);
		map.put("mch_id", channelRequestInfo.getMchId());
		map.put("out_trade_no", channelRequestInfo.getOutTradeNo());
		map.put("device_info", channelRequestInfo.getDeviceInfo()); // 设备号
		map.put("body", channelRequestInfo.getBody());
		map.put("goods_detail", channelRequestInfo.getGoodsDetail());
		map.put("attach", channelRequestInfo.getExtendInfo());
		map.put("total_fee", channelRequestInfo.getTotalFee());
		map.put("mch_create_ip", channelRequestInfo.getReqIp());
		map.put("auth_code", channelRequestInfo.getAuthCode());
		map.put("time_start", channelRequestInfo.getTimeStart());
		map.put("time_expire", channelRequestInfo.getTimeExpire());
		map.put("op_user_id", channelRequestInfo.getOpUserId());
		map.put("op_shop_id", channelRequestInfo.getOpShopId());
		map.put("op_device_id", channelRequestInfo.getOpDeviceId());
		map.put("goods_tag", channelRequestInfo.getGoodsTag());
		map.put("nonce_str", UUID.randomUUID().toString().substring(15));
		String key = channelRequestInfo.getCommPrivateKey();
		String sign = SignUtils.generateSign(map, key);
		map.put("sign", sign);
		logger.info("发给兴业的参数", map);
		Map<String, String> resultMap = null;
		try {
			resultMap = HttpClient.sendXmlPost(map, SysPara.SWIFT_REQ_URL);
			logger.info(resultMap.toString());
		} catch (Exception e) {
			logger.error("调用兴业银行支付接口返回异常", e);
			return null;
		}
		ChannelResponseInfo channelRespInfo = new ChannelResponseInfo();
		String status = resultMap.get("status");
		String message = resultMap.get("message");
		// 通信不成功
		if (!CibConstants.SUCCESS.equals(status)) {
			logger.error("status={} , message={}", status, message);
			if (null != CibResponseCode.transferCode(message)) {
				channelRespInfo.setResponseResult(CibResponseCode.transferCode(message));
			} else {
				channelRespInfo.setResponseResult(ResponseResult.TRADE_FAILED);
			}
			return channelRespInfo;
		}
		// 返回的参数验签
		if (!SignUtils.checkParam(resultMap, key)) {
			logger.error("兴业返回参数验签失败");
			channelRespInfo.setResponseResult(ResponseResult.TRADE_FAILED);
			return channelRespInfo;
		}
		String resultCode = resultMap.get("result_code");
		String mchId = resultMap.get("mch_id");
		String needQuery = resultMap.get("need_query");
		String deviceInfo = resultMap.get("device_info");
		String errCode = resultMap.get("err_code");
		String errMsg = resultMap.get("err_msg");

		channelRespInfo.setMchId(mchId);
		channelRespInfo.setDeviceInfo(deviceInfo);
		channelRespInfo.setNeedQuery(needQuery);

		// 业务返回结果错误
		if (!CibConstants.SUCCESS.equals(resultCode)) {
			channelRespInfo.setMsg(errMsg);
			if (StringUtils.isNotEmpty(errCode)) {
				// 用户支付中，需要输入密码
				if ((errCode.equals("10003") || errCode.equals("USERPAYING")) && "Y".equals(needQuery)) {
					channelRespInfo.setResponseResult(ResponseResult.OK);
					channelRespInfo.setTradeStatus(ChannelTradeStatus.WAITING.name());
				} else {					
					channelRespInfo.setResponseResult(CibResponseCode.transferCode(errCode));
					channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
				}
			}
			return channelRespInfo;
		}
		String subOpenid = resultMap.get("sub_openid");
		String tradeType = resultMap.get("trade_type");
		String isSubscribe = resultMap.get("is_subscribe");
		String payResult = resultMap.get("pay_result");
		String payInfo = resultMap.get("pay_info");
		String transactionId = resultMap.get("transaction_id");
		String outTransactionId = resultMap.get("out_transaction_id");
		String subIsSubscribe = resultMap.get("sub_is_subscribe");
		String subAppid = resultMap.get("sub_appid");
		String outTradeNo = resultMap.get("out_trade_no");
		String totalFee = resultMap.get("total_fee");
		String couponFee = resultMap.get("coupon_fee");
		String feeType = resultMap.get("fee_type");
		String attach = resultMap.get("attach");
		String bankType = resultMap.get("bank_type");
		String timeEnd = resultMap.get("time_end").trim();
		String tradeEndDate = timeEnd.substring(0, 8);
		String tradeEndTime = timeEnd.substring(8, 14);
		channelRespInfo.setTradeType(tradeType);
		channelRespInfo.setPayResult(payResult);
		channelRespInfo.setPayInfo(payInfo);
		channelRespInfo.setThirdPaymentTxnid(outTransactionId);
		channelRespInfo.setThirdBuyerAccount(subOpenid);
		channelRespInfo.setIsSubscribe(isSubscribe);
		channelRespInfo.setTradeNo(outTradeNo);
		channelRespInfo.setSubIsSubscribe(subIsSubscribe);
		channelRespInfo.setSubAppid(subAppid);
		channelRespInfo.setTotalFee(Integer.parseInt(totalFee));
		channelRespInfo.setDiscountFee(null != couponFee ? Integer.parseInt(couponFee) : 0);
		channelRespInfo.setChannelTxnNo(transactionId);
		channelRespInfo.setBankType(bankType);
		channelRespInfo.setFeeType(feeType);
		channelRespInfo.setAttach(attach);
		channelRespInfo.setTradeEndDate(tradeEndDate);
		channelRespInfo.setTradeEndTime(tradeEndTime);
		channelRespInfo.setResponseResult(ResponseResult.OK);
		channelRespInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
		return channelRespInfo;

	}

	@Override
	public ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		// 接口类型
		map.put("service", CibServiceType.UNIFIED_TRADE_CANCEL.getService());
		map.put("version", CibConstants.VERSION);
		map.put("charset", CibConstants.CHARSET);
		map.put("sign_type", CibConstants.SIGN_TYPE);
		map.put("mch_id", channelRequestInfo.getMchId());
		map.put("out_trade_no", channelRequestInfo.getOutTradeNo());
		map.put("nonce_str", UUID.randomUUID().toString().substring(15));
		String key = channelRequestInfo.getCommPrivateKey();
		String sign = SignUtils.generateSign(map, key);
		map.put("sign", sign);
		logger.info(sign);
		Map<String, String> resultMap = null;
		try {
			resultMap = HttpClient.sendXmlPost(map, SysPara.SWIFT_REQ_URL);
			logger.info(resultMap.toString());
		} catch (Exception e) {
			logger.error("调用兴业银行撤销接口返回异常", e);
		}
		ChannelResponseInfo channelRespInfo = new ChannelResponseInfo();

		String status = resultMap.get("status");
		String message = resultMap.get("message");
		// 通信不成功
		if (!CibConstants.SUCCESS.equals(status)) {
			logger.error("status={} , message={}", status, message);
			channelRespInfo.setResponseResult(CibResponseCode.transferCode(message));
			return channelRespInfo;
		}
		// 返回的参数验签
		if (!SignUtils.checkParam(resultMap, key)) {
			logger.error("兴业返回参数验签失败");
			channelRespInfo.setResponseResult(ResponseResult.TRADE_FAILED);
			return channelRespInfo;
		}
		String resultCode = resultMap.get("result_code");
		String mchId = resultMap.get("mch_id");
		channelRespInfo.setMchId(mchId);
		if (!CibConstants.SUCCESS.equals(resultCode)) {
			channelRespInfo.setResponseResult(ResponseResult.TRADE_FAILED);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
		} else {
			channelRespInfo.setResponseResult(ResponseResult.OK);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
		}
		return channelRespInfo;

	}

	@Override
	public ChannelResponseInfo close(ChannelRequestInfo channelRequestInfo) {
		throw new UnSupportApiException("原支付方式不支持关闭操作");
	}

}
