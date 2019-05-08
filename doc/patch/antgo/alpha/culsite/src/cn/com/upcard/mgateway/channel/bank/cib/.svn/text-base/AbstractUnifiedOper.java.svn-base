package cn.com.upcard.mgateway.channel.bank.cib;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibConstants;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibServiceType;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibTradeStatus;
import cn.com.upcard.mgateway.channel.bank.cib.sdk.util.SignUtils;
import cn.com.upcard.mgateway.channel.bank.cib.sdk.util.XmlUtils;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;
import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.OrderStatus;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public abstract class AbstractUnifiedOper implements ThirdTradeOper {
	private static Logger logger = LoggerFactory.getLogger(AbstractUnifiedOper.class);

	@Override
	public ChannelResponseInfo tradeQuery(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("service", CibServiceType.UNIFIED_TRADE_QUERY.getService());
		map.put("version", CibConstants.VERSION);
		map.put("charset", CibConstants.CHARSET);
		map.put("sign_type", CibConstants.SIGN_TYPE);
		map.put("mch_id", channelRequestInfo.getMchId());
		map.put("out_trade_no", channelRequestInfo.getOutTradeNo());
		map.put("transaction_id", channelRequestInfo.getTransactionId());
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
		logger.info("兴业支付订单查询接口返回 res ---> \r\n" + res);

		String status = resultMap.get("status");
		String message = resultMap.get("message");
		ChannelResponseInfo channelReponseInfo = new ChannelResponseInfo(Constants.OK, "接口调用成功.");

		if (!CibConstants.SUCCESS.equals(status)) {
			logger.error("status={} , message={}", status, message);
			channelReponseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			channelReponseInfo.setMsg(message);
			return channelReponseInfo;
		}
		if (!SignUtils.checkParam(resultMap, channelRequestInfo.getCommPrivateKey())) {
			channelReponseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			channelReponseInfo.setMsg(CibConstants.VALID_SIGN_ERROR);
			return channelReponseInfo;
		}
		String result_code = resultMap.get("result_code");
		String err_code = resultMap.get("err_code");
		String err_msg = resultMap.get("err_msg");
		if (!CibConstants.SUCCESS.equals(result_code)) {
			logger.info("errCode={} , errMsg={}", err_code, err_msg);
			channelReponseInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelReponseInfo.setMsg(err_msg);
			return channelReponseInfo;
		}
		// SUCCESS—支付成功REFUND—转入退款NOTPAY—未支付CLOSED—已关闭REVERSE—已冲正REVOK—已撤销
		String trade_state = resultMap.get("trade_state");
		// 当 status和result_code都为0的时候有返回
		String mch_id = resultMap.get("mch_id");
		String device_info = resultMap.get("device_info");
		String tradeStateDesc = resultMap.get("trade_state_desc");
		String tradeStatus = CibTradeStatus.getStatus(trade_state);
		
		String openid = resultMap.get("openid"); // 用户openid
		String isSubscribe = resultMap.get("is_subscribe"); // 是否关注公众账号
		String appid = resultMap.get("appid"); // 服务商公众号appid
		//支付成功,获取
		String trade_type = resultMap.get("trade_type");// 交易类型 pay.alipay.native
		String transaction_id = resultMap.get("transaction_id");// 平台订单号
		String out_transaction_id = resultMap.get("out_transaction_id");// 第三方商户号
		String out_trade_no = resultMap.get("out_trade_no");// 商户订单号
		String total_fee = resultMap.get("total_fee");// 总金额
		String coupon_fee = resultMap.get("coupon_fee");// 现金券金额
		
		String fee_type = resultMap.get("fee_type");// 货币种类
		String attach = resultMap.get("attach");// 附加信息
		String bank_type = resultMap.get("bank_type");// 付款银行
		String bank_billno = resultMap.get("bank_billno");// 银行订单号
		String time_end = resultMap.get("time_end");// 支付完成时间yyyyMMddhhmmss
		
		channelReponseInfo.setMsg(tradeStateDesc);
		channelReponseInfo.setTradeStatus(tradeStatus);
		channelReponseInfo.setSubAppid(resultMap.get("sub_appid"));
		channelReponseInfo.setThirdBuyerAccount(resultMap.get("sub_openid"));
		channelReponseInfo.setSubOpenId(resultMap.get("sub_openid"));
		channelReponseInfo.setSubIsSubscribe(resultMap.get("sub_is_subscribe"));
		
		channelReponseInfo.setThirdBuyerAccount(openid);
		channelReponseInfo.setIsSubscribe(isSubscribe);
		channelReponseInfo.setAppid(appid);
		channelReponseInfo.setMchId(mch_id);
		channelReponseInfo.setDeviceInfo(device_info);
		channelReponseInfo.setTradeType(trade_type);
		channelReponseInfo.setThirdPaymentTxnid(out_transaction_id);
		channelReponseInfo.setChannelTxnNo(transaction_id);
		channelReponseInfo.setTradeNo(out_trade_no);
		if(StringUtils.isNotEmpty(coupon_fee)) {
			channelReponseInfo.setCouponFee(Integer.valueOf(coupon_fee));
			channelReponseInfo.setDiscountFee(Integer.valueOf(coupon_fee));
		}
		if(StringUtils.isNotEmpty(total_fee)) {
			channelReponseInfo.setTotalFee(Integer.valueOf(total_fee));
		}
		channelReponseInfo.setFeeType(fee_type);
		channelReponseInfo.setAttach(attach);
		channelReponseInfo.setBankType(bank_type);
		channelReponseInfo.setBankBillNo(bank_billno);
		if(StringUtils.isNotEmpty(time_end)) {
			channelReponseInfo.setTradeEndDate(time_end.substring(0, 8));
			channelReponseInfo.setTradeEndTime(time_end.substring(8));
		} else {
			channelReponseInfo.setTradeEndDate(DateUtil.formatCompactDate(DateUtil.now()));
			channelReponseInfo.setTradeEndTime(DateUtil.formatCompactTime(DateUtil.now()));
		}
		return channelReponseInfo;
	}

	@Override
	public ChannelResponseInfo refund(ChannelRequestInfo channelRequestInfo) {
		ChannelResponseInfo channelReponseInfo = new ChannelResponseInfo(ChannelResponseResult.SUCCESS.getCode(),
				"success");
		//首先查询原交易订单信息
		
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("service", CibServiceType.UNIFIED_TRADE_REFUND.getService());
		map.put("version", CibConstants.VERSION);
		map.put("charset", CibConstants.CHARSET);
		map.put("sign_type", CibConstants.SIGN_TYPE);
		map.put("mch_id", channelRequestInfo.getMchId());
		map.put("out_trade_no", channelRequestInfo.getOutTradeNo());
		map.put("transaction_id", channelRequestInfo.getTransactionId());
		map.put("out_refund_no", channelRequestInfo.getOutRefundNo());
		map.put("total_fee", channelRequestInfo.getTotalFee());
		map.put("refund_fee", channelRequestInfo.getRefundFee());
		map.put("op_user_id", channelRequestInfo.getOpUserId());
		map.put("refund_channel", channelRequestInfo.getRefundChannel());
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
		logger.info("兴业退款接口返回 res ---> \r\n" + res);
		String status = resultMap.get("status");
		String message = resultMap.get("message");
		
		if (!CibConstants.SUCCESS.equals(status)) {
			logger.error("status={} , message={}", status, message);
			channelReponseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			channelReponseInfo.setMsg(message);
			return channelReponseInfo;
		}
		if (!SignUtils.checkParam(resultMap, channelRequestInfo.getCommPrivateKey())) {
			channelReponseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			channelReponseInfo.setMsg(CibConstants.VALID_SIGN_ERROR);
			return channelReponseInfo;
		}
		String result_code = resultMap.get("result_code");
		String err_code = resultMap.get("err_code");
		String err_msg = resultMap.get("err_msg");
		if (!CibConstants.SUCCESS.equals(result_code)) {
			logger.info("errCode={} , errMsg={}", err_code, err_msg);
			channelReponseInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelReponseInfo.setMsg(err_msg);
			channelReponseInfo.setTradeStatus(OrderStatus.FAILED.name());
			return channelReponseInfo;
		}
		String mch_id = resultMap.get("mch_id");
		String device_info = resultMap.get("device_info");

		// 当 status和result_code都为0的时候有返回
		//String transaction_id = resultMap.get("transaction_id");// 平台订单号
		//String out_trade_no = resultMap.get("out_trade_no");// 商户订单号
		String out_refund_no = resultMap.get("out_refund_no");// 商户退款单号
		String refund_id = resultMap.get("refund_id");// 平台退款单号
		String refund_channel = resultMap.get("refund_channel");// 退款渠道
		String refund_fee = resultMap.get("refund_fee");// 退款金额
		String coupon_refund_fee = resultMap.get("coupon_refund_fee");// 现金券退款金额

		channelReponseInfo.setMchId(mch_id);
		channelReponseInfo.setDeviceInfo(device_info);
		channelReponseInfo.setChannelTxnNo(refund_id);
		channelReponseInfo.setTradeNo(out_refund_no);
		if(StringUtils.isNotEmpty(refund_fee)) {
			channelReponseInfo.setRefundFee(Integer.valueOf(refund_fee));
		}
		if(StringUtils.isNotEmpty(coupon_refund_fee)) {
			channelReponseInfo.setCouponRefundFee(Integer.valueOf(coupon_refund_fee));
		}
		channelReponseInfo.setRefundChannel(refund_channel);
		channelReponseInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
		channelReponseInfo.setTradeEndDate(DateUtil.formatCompactDate(DateUtil.now()));
		channelReponseInfo.setTradeEndTime(DateUtil.formatCompactTime(DateUtil.now()));
		return channelReponseInfo;
	}

	@Override
	public ChannelResponseInfo refundQuery(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("service", CibServiceType.UNIFIED_TRADE_REFUND_QUERY.getService());
		map.put("version", CibConstants.VERSION);
		map.put("charset", CibConstants.CHARSET);
		map.put("sign_type", CibConstants.SIGN_TYPE);
		map.put("mch_id", channelRequestInfo.getMchId());
		map.put("out_refund_no", channelRequestInfo.getOutRefundNo());
		map.put("refund_id", channelRequestInfo.getRefundId());
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
		logger.info("兴业退款查询返回 res ---> \r\n" + res);
		String status = resultMap.get("status");
		String message = resultMap.get("message");
		ChannelResponseInfo channelReponseInfo = new ChannelResponseInfo(Constants.OK, "接口调用成功.");
		if (!CibConstants.SUCCESS.equals(status)) {
			logger.error("status={} , message={}", status, message);
			channelReponseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			channelReponseInfo.setMsg(message);
			return channelReponseInfo;
		}
		if (!SignUtils.checkParam(resultMap, channelRequestInfo.getCommPrivateKey())) {
			channelReponseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			channelReponseInfo.setMsg(CibConstants.VALID_SIGN_ERROR);
			return channelReponseInfo;
		}
		String err_code = resultMap.get("err_code");
		String err_msg = resultMap.get("err_msg");
		String result_code = resultMap.get("result_code");
		if (!CibConstants.SUCCESS.equals(result_code)) {
			logger.info("errCode={} , errMsg={}", err_code, err_msg);
			channelReponseInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelReponseInfo.setMsg(err_msg);
			return channelReponseInfo;
		}
		
		String mch_id = resultMap.get("mch_id");
		String device_info = resultMap.get("device_info");

		// 当 status和result_code都为0的时候有返回
		//String transaction_id = resultMap.get("transaction_id");// 平台订单号
		//String out_trade_no = resultMap.get("out_trade_no");// 商户订单号
		String refund_count = resultMap.get("refund_count");// 退款笔数
		String refund_id = null;
		String refund_channel = null;
		String refund_fee = null;
		String coupon_refund_fee = null;
		String refund_time = null;
		String refund_status = null;
		String out_refund_no = null;
		for (int i = 0; i < Integer.valueOf(refund_count); i++) {
			// $n 表示记录的序号，取值为 0~($ refund_count -1)，
			// 例如 refund_count 指示返回的退款记录有 2 条。第一条序号为“0”，第二条序号为“1”。
			out_refund_no = resultMap.get("out_refund_no_" + i);// 商户退款单号
			if (channelRequestInfo.getOutRefundNo().equals(out_refund_no)) {
				refund_id = resultMap.get("refund_id_" + i);// 平台退款单号
				refund_channel = resultMap.get("refund_channel_" + i);// 退款渠道
				refund_fee = resultMap.get("refund_fee_" + i);// 退款金额
				coupon_refund_fee = resultMap.get("coupon_refund_fee_" + i);// 现金券退款金额
				refund_time = resultMap.get("refund_time_" + i);// 退款时间
				/**
				 * refund_status_$n 是 String(16) 退款状态： SUCCESS—退款成功 FAIL—退款失败 PROCESSING—退款处理中
				 * NOTSURE—未确定， 需要商户原退款单号重新发起
				 * CHANGE—转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者平台转账的方式进行退款
				 */
				refund_status = resultMap.get("refund_status_" + i);// 退款状态
				break;
			}
		}
		if (org.apache.commons.lang3.StringUtils.isEmpty(out_refund_no)) {
			channelReponseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			channelReponseInfo.setMsg(message);
			return channelReponseInfo;
		}
		channelReponseInfo.setTradeStatus(CibTradeStatus.getStatus(refund_status));
		channelReponseInfo.setMchId(mch_id);
		channelReponseInfo.setDeviceInfo(device_info);
		channelReponseInfo.setTradeNo(out_refund_no);
		channelReponseInfo.setRefundId(refund_id);
		channelReponseInfo.setRefundChannel(refund_channel);
		if(StringUtils.isNotEmpty(refund_fee)) {
			channelReponseInfo.setRefundFee(Integer.valueOf(refund_fee));
		}
		if(StringUtils.isNotEmpty(coupon_refund_fee)) {
			channelReponseInfo.setCouponRefundFee(Integer.valueOf(coupon_refund_fee));
		}
		if(StringUtils.isNotEmpty(refund_time)) {
			channelReponseInfo.setTradeEndDate(refund_time.substring(0, 8));
			channelReponseInfo.setTradeEndTime(refund_time.substring(8));
		} else {
			channelReponseInfo.setTradeEndDate(DateUtil.formatCompactDate(DateUtil.now()));
			channelReponseInfo.setTradeEndTime(DateUtil.formatCompactTime(DateUtil.now()));
		}
		channelReponseInfo.setChannelTxnNo(refund_id);
		return channelReponseInfo;
	}
}
