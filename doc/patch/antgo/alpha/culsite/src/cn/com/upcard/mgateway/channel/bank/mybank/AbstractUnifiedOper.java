package cn.com.upcard.mgateway.channel.bank.mybank;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.bank.mybank.common.MyBankResponseCode;
import cn.com.upcard.mgateway.channel.bank.mybank.common.MyBankServiceType;
import cn.com.upcard.mgateway.channel.bank.mybank.sdk.base.HttpsMain;
import cn.com.upcard.mgateway.channel.bank.mybank.sdk.util.HttpClientMyBank;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.exception.AbnormalResponseException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

/**
 * 线上线下共有的功能
 * 
 * @author zhoudi
 *
 */
public abstract class AbstractUnifiedOper implements ThirdTradeOper {
	private static Logger logger = LoggerFactory.getLogger(AbstractUnifiedOper.class);

	private static String reqTime = String.valueOf(DateUtil.getNowLongValue());

	@Override
	public ChannelResponseInfo tradeQuery(ChannelRequestInfo channelRequestInfo) {
		// 订单查询接口
		String function = MyBankServiceType.SCANPAY_QUERY_SERVICE.getService();
		Map<String, String> form = new HashMap<String, String>();
		form.put("Function", function);
		form.put("ReqTime", reqTime);
		// reqMsgId每次报文必须都不一样
		form.put("ReqMsgId", UUID.randomUUID().toString());
		form.put("MerchantId", channelRequestInfo.getMchId());
		form.put("OutTradeNo", channelRequestInfo.getOutTradeNo());
		form.put("IsvOrgId", SysPara.MYBANK_ISV_ORG_ID);
		Map<String, Object> resMap = null;
		ChannelResponseInfo channelRespInfo = new ChannelResponseInfo();
		try {
			resMap = HttpClientMyBank.sendXmlPost(SysPara.MYBANK_REQ_URL, function, form);
			logger.info("resMap= {}", resMap);
		} catch (AbnormalResponseException e) {
			logger.error("网商返回全局错误码 = {}", e.getMessage());
			ResponseResult responseResult = MyBankResponseCode.transferCode(e.getMessage());
			channelRespInfo.setResponseResult(responseResult);
			channelRespInfo.setCode(responseResult.getCode());
			channelRespInfo.setMsg(responseResult.getMsg());
			return channelRespInfo;
		} catch (Exception e) {
			logger.error("调用网商银行查询接口返回异常", e);
			return null;
		}
		@SuppressWarnings("unchecked")
		Map<String, String> respInfo = (Map<String, String>) resMap.get("RespInfo");
		String outTradeNo = (null != resMap.get("OutTradeNo")) ? String.valueOf(resMap.get("OutTradeNo")) : null;
		channelRespInfo.setTradeNo(outTradeNo);
		String resultStatus = respInfo.get("ResultStatus");
		String resultMsg = respInfo.get("ResultMsg");
		if ("F".equals(resultStatus)) {
			channelRespInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelRespInfo.setMsg(resultMsg);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
			return channelRespInfo;
		} else if ("U".equals(resultStatus)) {
			channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
			channelRespInfo.setMsg(resultMsg);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.WAITING.name());
			return channelRespInfo;
		} else if ("S".equals(resultStatus)) {
			String orderNo = (null != resMap.get("OrderNo")) ? String.valueOf(resMap.get("OrderNo")) : null;
			String orderType = (null != resMap.get("OrderType")) ? String.valueOf(resMap.get("OrderType")) : null;
			String tradeStatus = (null != resMap.get("TradeStatus")) ? String.valueOf(resMap.get("TradeStatus")) : null;
			channelRespInfo.setChannelTxnNo(orderNo);
			channelRespInfo.setOrderType(orderType);
			channelRespInfo.setTradeStatus(tradeStatus);
			channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
			if ("succ".equals(tradeStatus)) {
				channelRespInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
			} else if ("fail".equals(tradeStatus)) {
				channelRespInfo.setMsg(resultMsg);
				channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
				return channelRespInfo;
			} else if ("paying".equals(tradeStatus)) {
				channelRespInfo.setMsg(resultMsg);
				channelRespInfo.setTradeStatus(ChannelTradeStatus.WAITING.name());
				return channelRespInfo;
			} else if ("closed".equals(tradeStatus)) {
				channelRespInfo.setMsg(resultMsg);
				channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
				return channelRespInfo;
			} else if ("cancel".equals(tradeStatus)) {
				channelRespInfo.setMsg(resultMsg);
				channelRespInfo.setTradeStatus(ChannelTradeStatus.CANCEL.name());
				return channelRespInfo;
			}
			String gmtPayment = (null != resMap.get("GmtPayment")) ? String.valueOf(resMap.get("GmtPayment")) : null;
			String gmt = null;
			try {
				gmt = DateUtil.formatCompactDateTime(DateUtil.DATE_TIME_FORMATTER.parse(gmtPayment));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			channelRespInfo.setTradeEndDate(gmt.trim().substring(0, 8));
			channelRespInfo.setTradeEndTime(gmt.trim().substring(8));
			String channelType = (null != resMap.get("ChannelType")) ? String.valueOf(resMap.get("ChannelType")) : null;
			String totalAmount = (null != resMap.get("TotalAmount")) ? String.valueOf(resMap.get("TotalAmount")) : null;
			String currency = (null != resMap.get("Currency")) ? String.valueOf(resMap.get("Currency")) : null;
			String merchantId = (null != resMap.get("MerchantId")) ? String.valueOf(resMap.get("MerchantId")) : null;
			String isvOrgId = (null != resMap.get("IsvOrgId")) ? String.valueOf(resMap.get("IsvOrgId")) : null;
			String settleType = (null != resMap.get("SettleType")) ? String.valueOf(resMap.get("SettleType")) : null;
			String attach = (null != resMap.get("Attach")) ? String.valueOf(resMap.get("Attach")) : null;
			String bankType = (null != resMap.get("BankType")) ? String.valueOf(resMap.get("BankType")) : null;
			String subIsSubscribe = (null != resMap.get("isSubscribe")) ? String.valueOf(resMap.get("isSubscribe")) : null;
			String payChannelOrderNo = (null != resMap.get("PayChannelOrderNo")) ? String.valueOf(resMap.get("PayChannelOrderNo")) : null;
			String subAppId = (null != resMap.get("SubAppId")) ? String.valueOf(resMap.get("SubAppId")) : null;
			String subOpenId = (null != resMap.get("SubOpenId")) ? String.valueOf(resMap.get("SubOpenId")) : null;
			String receiptAmount = (null != resMap.get("ReceiptAmount")) ? String.valueOf(resMap.get("ReceiptAmount")) : null;
			String buyerPayAmount = (null != resMap.get("BuyerPayAmount")) ? String.valueOf(resMap.get("BuyerPayAmount")) : null;

			channelRespInfo.setChannelType(channelType);
			channelRespInfo.setTotalFee(null != totalAmount ? Integer.parseInt(totalAmount) : 0);
			channelRespInfo.setFeeType(currency);
			channelRespInfo.setMchId(merchantId);
			channelRespInfo.setIsvOrgId(isvOrgId);
			channelRespInfo.setSettleType(settleType);
			channelRespInfo.setAttach(attach);
			channelRespInfo.setBankType(bankType);
			channelRespInfo.setSubIsSubscribe(subIsSubscribe);
			channelRespInfo.setThirdPaymentTxnid(payChannelOrderNo);
			channelRespInfo.setSubAppid(subAppId);
			channelRespInfo.setCouponFee(null != resMap.get("CouponFee") ? Integer.parseInt(String.valueOf(resMap
					.get("CouponFee"))) : 0);
			channelRespInfo.setThirdBuyerAccount(subOpenId);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
			channelRespInfo.setReceiptAmount(receiptAmount);
			channelRespInfo.setBuyerPayAmount(buyerPayAmount);

		}
		return channelRespInfo;
	}

	@Override
	public ChannelResponseInfo refund(ChannelRequestInfo channelRequestInfo) {
		// 订单查询接口
		String function = MyBankServiceType.REFUND_SERVICE.getService();
		Map<String, String> data = new HashMap<String, String>();
		data.put("Function", function);
		data.put("ReqMsgId", UUID.randomUUID().toString()); // reqMsgId每次报文必须都不一样
		data.put("ReqTime", reqTime);

		data.put("MerchantId", channelRequestInfo.getMchId()); // 商户号
		data.put("OutTradeNo", channelRequestInfo.getOutTradeNo()); // 原交易订单号
		data.put("OutRefundNo", channelRequestInfo.getOutRefundNo()); // 退款订单号
		data.put("IsvOrgId", SysPara.MYBANK_ISV_ORG_ID); // 合作方机构号（网商银行分配）
		data.put("RefundAmount", channelRequestInfo.getRefundFee()); // 退款金额
		data.put("RefundReason", null); // TODO 退款原因，支付宝交易须填写
		data.put("OperatorId", channelRequestInfo.getOpUserId()); // 操作员
		data.put("DeviceId", channelRequestInfo.getDeviceInfo()); // 终端设备号(门店号或收银设备ID)
		data.put("DeviceCreateIp", channelRequestInfo.getReqIp()); // 创建订单终端的IP
		logger.info("data = {}", data);

		Map<String, Object> resMap = null;
		ChannelResponseInfo channelRespInfo = new ChannelResponseInfo();
		try {
			resMap = HttpClientMyBank.sendXmlPost(SysPara.MYBANK_REQ_URL, function, data);
			logger.info("resultMap = {}", resMap);
		} catch (AbnormalResponseException e) {
			logger.error("网商返回全局错误码 = {}", e.getMessage());
			ResponseResult responseResult = MyBankResponseCode.transferCode(e.getMessage());
			channelRespInfo.setResponseResult(responseResult);
			channelRespInfo.setCode(responseResult.getCode());
			channelRespInfo.setMsg(responseResult.getMsg());
			return channelRespInfo;
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}
		@SuppressWarnings("unchecked")
		Map<String, String> respInfo = (Map<String, String>) resMap.get("RespInfo");

		String outTradeNo = (null != resMap.get("OutTradeNo")) ? String.valueOf(resMap.get("OutTradeNo")) : null;
		String isvOrgId = (null != resMap.get("IsvOrgId")) ? String.valueOf(resMap.get("IsvOrgId")) : null;
		String merchantId = (null != resMap.get("MerchantId")) ? String.valueOf(resMap.get("MerchantId")) : null;
		String outRefundNo = (null != resMap.get("OutRefundNo")) ? String.valueOf(resMap.get("OutRefundNo")) : null;

		channelRespInfo.setTradeNo(outTradeNo);
		channelRespInfo.setIsvOrgId(isvOrgId);
		channelRespInfo.setMchId(merchantId);
		channelRespInfo.setRefundNo(outRefundNo);
		channelRespInfo.setRefundFee(null != resMap.get("RefundAmount") ? Integer.parseInt(String.valueOf(resMap.get("RefundAmount"))) : 0);
		String resultStatus = respInfo.get("ResultStatus");
		String resultMsg = respInfo.get("ResultMsg");

		if ("F".equals(resultStatus)) {
			channelRespInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelRespInfo.setMsg(resultMsg);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
			return channelRespInfo;
		}
		if ("U".equals(resultStatus)) {
			channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
			channelRespInfo.setMsg(resultMsg);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.WAITING.name());
			return channelRespInfo;
		}
		if ("S".equals(resultStatus)) {
			channelRespInfo.setTradeEndDate(DateUtil.formatCompactDate(DateUtil.now()));
			channelRespInfo.setTradeEndTime(DateUtil.formatCompactTime(DateUtil.now()));
			channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
			channelRespInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
			if (null!=resMap.get("RefundOrderNo")) {
				channelRespInfo.setChannelTxnNo(String.valueOf(resMap.get("RefundOrderNo")));
			}
			if (null!=resMap.get("RefundAmount") ) {
				channelRespInfo.setRefundFee(Integer.valueOf((String) resMap.get("RefundAmount")));
			}
		}
		return channelRespInfo;
	}

	@Override
	public ChannelResponseInfo refundQuery(ChannelRequestInfo channelRequestInfo) {

		String function = MyBankServiceType.REFUND_QUERY_SERVICE.getService();
		Map<String, String> data = new HashMap<String, String>();
		data.put("Function", function);
		data.put("ReqMsgId", UUID.randomUUID().toString()); // reqMsgId每次报文必须都不一样
		data.put("ReqTime", reqTime);

		data.put("IsvOrgId", SysPara.MYBANK_ISV_ORG_ID); // 合作方机构号，指的是银商
		data.put("MerchantId", channelRequestInfo.getMchId()); // 该商户在网商商户号
		data.put("OutRefundNo", channelRequestInfo.getOutRefundNo()); // 原退款订单号

		Map<String, Object> resMap = null;
		ChannelResponseInfo channelRespInfo = new ChannelResponseInfo();
		try {
			resMap = HttpClientMyBank.sendXmlPost(SysPara.MYBANK_REQ_URL, function, data);
			logger.info("resultMap = {}", resMap);
		} catch (AbnormalResponseException e) {
			logger.error("网商返回全局错误码 = {}", e.getMessage());
			ResponseResult responseResult = MyBankResponseCode.transferCode(e.getMessage());
			channelRespInfo.setResponseResult(responseResult);
			channelRespInfo.setCode(responseResult.getCode());
			channelRespInfo.setMsg(responseResult.getMsg());
			return channelRespInfo;
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}
		@SuppressWarnings("unchecked")
		Map<String, String> respInfo = (Map<String, String>) resMap.get("RespInfo");
		String resultStatus = respInfo.get("ResultStatus");
		String resultMsg = respInfo.get("ResultMsg");
		String outRefundNo = (null != resMap.get("OutRefundNo")) ? String.valueOf(resMap.get("OutRefundNo")) : null;

		channelRespInfo.setRefundNo(outRefundNo);
		if ("F".equals(resultStatus)) {
			channelRespInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelRespInfo.setMsg(resultMsg);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
			return channelRespInfo;
		} else if ("U".equals(resultStatus)) {
			channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
			channelRespInfo.setMsg(resultMsg);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.WAITING.name());
			return channelRespInfo;
		} else if ("S".equals(resultStatus)) {
			channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
			String refundOrderNo = (null != resMap.get("OutRefundNo")) ? String.valueOf(resMap.get("OutRefundNo")) : null;
			String outTradeNo = (null != resMap.get("OutTradeNo")) ? String.valueOf(resMap.get("OutTradeNo")) : null;
			String tradeStatus = (null != resMap.get("TradeStatus")) ? String.valueOf(resMap.get("TradeStatus")) : null;
			channelRespInfo.setTradeNo(outTradeNo);
			channelRespInfo.setChannelTxnNo(refundOrderNo);
			if ("fail".equals(tradeStatus)) {
				channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
			}
			if ("refunding".equals(tradeStatus)) {
				channelRespInfo.setTradeStatus(ChannelTradeStatus.WAITING.name());
			}
			if ("succ".equals(tradeStatus)) {
				channelRespInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
				String isvOrgId = (null != resMap.get("IsvOrgId")) ? String.valueOf(resMap.get("IsvOrgId")) : null;
				String merchantId = (null != resMap.get("MerchantId")) ? String.valueOf(resMap.get("MerchantId")) : null;
				String orderNo = (null != resMap.get("OrderNo")) ? String.valueOf(resMap.get("OrderNo")) : null;
				String refundReason = (null != resMap.get("RefundReason")) ? String.valueOf(resMap.get("RefundReason")) : null;
				channelRespInfo.setIsvOrgId(isvOrgId);
				channelRespInfo.setMchId(merchantId);
				channelRespInfo.setTradeNo(orderNo);
				channelRespInfo.setRefundFee(null != resMap.get("RefundAmount") ? Integer.parseInt(String
						.valueOf(resMap.get("RefundAmount"))) : 0);
				channelRespInfo.setRefundReason(refundReason);
				String gmtRefundment = null;
				if (null != resMap.get("GmtRefundment")) {
					try {
						gmtRefundment = DateUtil.formatCompactDateTime(DateUtil.DATE_TIME_FORMATTER.parse(String
								.valueOf(resMap.get("GmtRefundment"))));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					channelRespInfo.setTradeEndDate(gmtRefundment.trim().substring(0, 8));
					channelRespInfo.setTradeEndTime(gmtRefundment.trim().substring(8));
				} else {
					channelRespInfo.setTradeEndDate(DateUtil.formatCompactDate(DateUtil.now()));
					channelRespInfo.setTradeEndTime(DateUtil.formatCompactTime(DateUtil.now()));
				}
			}

		}
		return channelRespInfo;
	}

}
