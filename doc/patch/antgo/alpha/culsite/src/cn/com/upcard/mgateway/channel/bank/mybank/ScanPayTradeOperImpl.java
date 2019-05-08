package cn.com.upcard.mgateway.channel.bank.mybank;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.bank.mybank.common.ChannelType;
import cn.com.upcard.mgateway.channel.bank.mybank.common.MyBankResponseCode;
import cn.com.upcard.mgateway.channel.bank.mybank.common.MyBankServiceType;
import cn.com.upcard.mgateway.channel.bank.mybank.sdk.base.HttpsMain;
import cn.com.upcard.mgateway.channel.bank.mybank.sdk.util.HttpClientMyBank;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;
import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.BankCode;
import cn.com.upcard.mgateway.common.enums.BankOrderNoLength;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.exception.AbnormalResponseException;
import cn.com.upcard.mgateway.exception.UnSupportApiException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.RandomStringCreator;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class ScanPayTradeOperImpl extends AbstractUnifiedOper {
	private static Logger logger = LoggerFactory.getLogger(ScanPayTradeOperImpl.class);
	private static String reqTime = String.valueOf(DateUtil.getNowLongValue());
	public static final String SettleType = "T1";
	/**
	 * 网商线下支付接口
	 * 
	 * @param channelRequestInfo
	 * @return channelRespInfo
	 */
	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo) {
		String function = MyBankServiceType.SCANPAY_PAY_SERVICE.getService();
		Map<String, String> form = new HashMap<String, String>();
		form.put("Function", function);
		form.put("ReqTime", reqTime);
		// reqMsgId每次报文必须都不一样
		form.put("ReqMsgId", UUID.randomUUID().toString());
		form.put("MerchantId", channelRequestInfo.getMchId());
		form.put("OutTradeNo", channelRequestInfo.getOutTradeNo());
		form.put("Body", channelRequestInfo.getBody());
		form.put("IsvOrgId", SysPara.MYBANK_ISV_ORG_ID);
		form.put("GoodsTag", channelRequestInfo.getGoodsTag());
		form.put("GoodsDetail", channelRequestInfo.getGoodsDetail());
		form.put("AuthCode", channelRequestInfo.getAuthCode());
		form.put("TotalAmount", channelRequestInfo.getTotalFee());
		form.put("ChannelType", channelRequestInfo.getChannelType());
		form.put("OperatorId", channelRequestInfo.getOpUserId());
		form.put("StoreId", channelRequestInfo.getOpShopId());
		form.put("DeviceId", channelRequestInfo.getOpDeviceId());
		form.put("DeviceCreateIp", channelRequestInfo.getReqIp());
		if (StringUtils.isNotEmpty(channelRequestInfo.getTimeExpire())) {
			String expireExpress = String.valueOf((int) Math.ceil(((DateUtil.parseCompactDateTime(
					channelRequestInfo.getTimeExpire()).getTime() - DateUtil.now().getTime()) / 60000)));
			form.put("ExpireExpress", expireExpress);
		}
		form.put("SettleType", SettleType);
		form.put("Attach", channelRequestInfo.getExtendInfo());
        
		ChannelResponseInfo channelRespInfo = new ChannelResponseInfo();

		Map<String, Object> resMap = null;
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
			logger.error("调用网商银行支付接口返回异常", e);
			return null;
		}

		@SuppressWarnings("unchecked")
		Map<String, String> respInfo = (Map<String, String>) resMap.get("RespInfo");
		String outTradeNo = (null != resMap.get("OutTradeNo")) ? String.valueOf(resMap.get("OutTradeNo")) : null;
		channelRespInfo.setTradeNo(outTradeNo);
		String resultStatus = respInfo.get("ResultStatus");
		String resultCode = respInfo.get("ResultCode");
		String resultMsg = respInfo.get("ResultMsg");
		if ("F".equals(resultStatus)) {
			channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
			channelRespInfo.setMsg(resultMsg);
			channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
			return channelRespInfo;
		}
		if ("U".equals(resultStatus)) {
			// 支付单处理中
			if ("MT_PAY_REQUEST_IN_PROCESS".equals(resultCode) || "MT_USER_PAYING".equals(resultCode)) {
				channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
				channelRespInfo.setTradeStatus(ChannelTradeStatus.WAITING.name());
				channelRespInfo.setMsg(resultMsg);
				return channelRespInfo;
			} else {
				channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
				channelRespInfo.setMsg(resultMsg);
				channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
				return channelRespInfo;
			}
		}
		if ("S".equals(resultStatus)) {
			if (null != resMap.get("GmtPayment")) {
				String gmt = null;
				try {
					gmt = DateUtil.formatCompactDateTime(DateUtil.DATE_TIME_FORMATTER.parse(String.valueOf(resMap
							.get("GmtPayment"))));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				channelRespInfo.setTradeEndDate(gmt.trim().substring(0, 8));
				channelRespInfo.setTradeEndTime(gmt.trim().substring(8));
			} else {
				channelRespInfo.setTradeEndDate(DateUtil.formatCompactDate(DateUtil.now()));
				channelRespInfo.setTradeEndTime(DateUtil.formatCompactTime(DateUtil.now()));
			}
			String orderNo = (null != resMap.get("OrderNo")) ? String.valueOf(resMap.get("OrderNo")) : null;
			String channelType = (null != resMap.get("ChannelType")) ? String.valueOf(resMap.get("ChannelType")) : null;
			String currency = (null != resMap.get("Currency")) ? String.valueOf(resMap.get("Currency")) : null;
			String merchantId = (null != resMap.get("MerchantId")) ? String.valueOf(resMap.get("MerchantId")) : null;
			String isvOrgId = (null != resMap.get("IsvOrgId")) ? String.valueOf(resMap.get("IsvOrgId")) : null;
			String attach = (null != resMap.get("Attach")) ? String.valueOf(resMap.get("Attach")) : null;
			String bankType = (null != resMap.get("BankType")) ? String.valueOf(resMap.get("BankType")) : null;
			String payChannelOrderNo = (null != resMap.get("PayChannelOrderNo")) ? String.valueOf(resMap.get("PayChannelOrderNo")) : null;
			String subOpenId = (null != resMap.get("SubOpenId")) ? String.valueOf(resMap.get("SubOpenId")) : null;
			String subAppId = (null != resMap.get("SubAppId")) ? String.valueOf(resMap.get("SubAppId")) : null;
			//是否关注
			String subIsSubscribe = (null != resMap.get("IsSubscribe")) ? String.valueOf(resMap.get("IsSubscribe")) : null;
			String receiptAmount = (null != resMap.get("ReceiptAmount")) ? String.valueOf(resMap.get("ReceiptAmount")) : null;
			String buyerPayAmount = (null != resMap.get("BuyerPayAmount")) ? String.valueOf(resMap.get("BuyerPayAmount")) : null;

			channelRespInfo.setChannelTxnNo(orderNo);
			channelRespInfo.setChannelType(channelType);
			channelRespInfo.setTotalFee(null != resMap.get("TotalAmount") ? Integer.parseInt(String
					.valueOf(resMap.get("TotalAmount"))) : 0);
			channelRespInfo.setFeeType(currency);
			channelRespInfo.setMchId(merchantId);
			channelRespInfo.setIsvOrgId(isvOrgId);
			channelRespInfo.setAttach(attach);
			channelRespInfo.setBankType(bankType);
			channelRespInfo.setThirdPaymentTxnid(payChannelOrderNo);
			channelRespInfo.setThirdBuyerAccount(subOpenId);
			channelRespInfo.setSubAppid(subAppId);
			channelRespInfo.setSubIsSubscribe(subIsSubscribe);
			channelRespInfo.setDiscountFee(null != resMap.get("CouponFee") ? Integer.parseInt(String.valueOf(resMap
					.get("CouponFee"))) : 0);
			channelRespInfo.setReceiptAmount(receiptAmount);
			channelRespInfo.setBuyerPayAmount(buyerPayAmount);
			channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
			channelRespInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
		}
		return channelRespInfo;
	}

	/**
	 * <pre>
	 * 撤销接口
	 * 
	 * 网商撤销接口不能撤销支付成功或支付失败的订单
	 * 1.撤销时查询订单转态，若订单支付成功调用退货接口退货，退货结果为成功或未知时认为退货成功
	 * 2.若订单状态未知，调用撤销接口撤销
	 * /pre>
	 * @param channelRequestInfo
	 * @return channelRespInfo
	 */
	@Override
	public ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo) {
		ChannelResponseInfo channelRespInfo = new ChannelResponseInfo();
		
		//查询原订单转态，支付中调用撤销，支付成功，调用退货
		ChannelRequestInfo tradeQueryInfo = new ChannelRequestInfo();
		tradeQueryInfo.setMchId(channelRequestInfo.getMchId());
		tradeQueryInfo.setOutTradeNo(channelRequestInfo.getOutTradeNo());
		ChannelResponseInfo tradeQueryResponse = this.tradeQuery(tradeQueryInfo);
		if (tradeQueryResponse == null) {
			logger.info("查询无响应");
			return null;
		} else if (!Constants.OK.equals(tradeQueryResponse.getCode())) {
			logger.info("原交易不成功，撤销失败");
			channelRespInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelRespInfo.setMsg(tradeQueryResponse.getMsg());
			channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
			return channelRespInfo;
		} else if (ChannelTradeStatus.WAITING.name().equals(tradeQueryResponse.getTradeStatus())) {
			logger.info("原交易状态未知，调用撤销接口");
			String function = MyBankServiceType.SCANPAY_CANCEL_SERVICE.getService();
			Map<String, String> form = new HashMap<String, String>();
			form.put("Function", function);
			form.put("ReqTime", reqTime);
			// reqMsgId每次报文必须都不一样
			form.put("ReqMsgId", UUID.randomUUID().toString());
			form.put("MerchantId", channelRequestInfo.getMchId());
			form.put("OutTradeNo", channelRequestInfo.getOutTradeNo());
			form.put("IsvOrgId", SysPara.MYBANK_ISV_ORG_ID);
			Map<String, Object> resMap = null;
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
				logger.error("调用网商银行撤销接口返回异常", e);
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
			} else if ("S".equals(resultStatus)) {
				String orderNo = (null != resMap.get("OrderNo")) ? String.valueOf(resMap.get("OrderNo")) : null;
				channelRespInfo.setChannelTxnNo(orderNo);
				channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
				channelRespInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
			}
			return channelRespInfo;
		} else if (ChannelTradeStatus.SUCCESS.name().equals(tradeQueryResponse.getTradeStatus())) {
			logger.info("原交易支付成，调用退货接口");
			//支付成功，调用退款接口
			ChannelRequestInfo refundInfo = new ChannelRequestInfo();
			refundInfo.setMchId(channelRequestInfo.getMchId());
			refundInfo.setOutTradeNo(channelRequestInfo.getOutTradeNo());
			refundInfo.setOutRefundNo(this.createTradeNo(BankCode.MYBANK));
			refundInfo.setRefundFee(tradeQueryResponse.getTotalFee() + "");
			refundInfo.setReqIp("127.0.0.1");
			logger.info(refundInfo.toString());
			ChannelResponseInfo refundResponse = this.refund(refundInfo);
			if (refundResponse != null && Constants.OK.equals(refundResponse.getCode()) 
					&& (ChannelTradeStatus.SUCCESS.name().equals(refundResponse.getTradeStatus()) || ChannelTradeStatus.WAITING.name().equals(refundResponse.getTradeStatus()))) {
				logger.info("调用退货接口撤销原交易成功");
				channelRespInfo.setTradeNo(channelRequestInfo.getOutTradeNo());
				channelRespInfo.setCode(ChannelResponseResult.SUCCESS.getCode());
				channelRespInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
				channelRespInfo.setMsg("调用退货接口撤销原交易成功:" + refundResponse.getTradeStatus());
				channelRespInfo.setChannelTxnNo(refundResponse.getChannelTxnNo());
				return channelRespInfo;
			}
			logger.info("调用退货接口撤销原交易失败");
			return refundResponse;
		} else {
			channelRespInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			channelRespInfo.setMsg(tradeQueryResponse.getMsg());
			channelRespInfo.setTradeStatus(ChannelTradeStatus.FAILED.name());
			return channelRespInfo;
		}
	}

	@Override
	public ChannelResponseInfo close(ChannelRequestInfo channelRequestInfo) {
		throw new UnSupportApiException("原支付方式不支持关闭操作");
	}
	
	public String createTradeNo(BankCode bankCode) {
		// yyMMdd + 3-6位银行代码  + 6-18位随机数
		String date = DateUtil.formatDate(DateUtil.now(), "yyMMdd");
		String code = bankCode.getCode();
		String str = date + code;
		int randomLength = BankOrderNoLength.getOrderLength(bankCode) - str.length();
		return str + RandomStringCreator.generateString(randomLength);
	}
}
