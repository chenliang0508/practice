package cn.com.upcard.mgateway.channel.bank.cmbc;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.bank.cmbc.common.CMBCPayType;
import cn.com.upcard.mgateway.channel.bank.cmbc.common.CMBCQueryType;
import cn.com.upcard.mgateway.channel.bank.cmbc.common.CMBCTradeStatus;
import cn.com.upcard.mgateway.channel.bank.cmbc.util.JsonUtils;
import cn.com.upcard.mgateway.channel.bank.cmbc.util.SignatureUtils;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;
import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.channel.bank.cmbc.util.Base64Utils;
import cn.com.upcard.mgateway.channel.bank.cmbc.util.CMBCToolUtils;
import cn.com.upcard.mgateway.channel.bank.cmbc.util.HttpClient;
import cn.com.upcard.mgateway.exception.UnSupportApiException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public abstract class AbstractUnifiedOper implements ThirdTradeOper {
	private static Logger logger = LoggerFactory.getLogger(AbstractUnifiedOper.class);
	protected static final String RETURN_TYPE_SUCCESS = "S";
	protected static final String VALID_SIGN_ERROR = "返回参数验签不通过";

	@Override
	public abstract ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo);

	@Override
	public abstract ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo);
	
	
	@Override
	public ChannelResponseInfo tradeQuery(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("platformId", SysPara.CMBC_PLATFORMID);
		map.put("merchantNo", channelRequestInfo.getMchId());
		map.put("merchantSeq", channelRequestInfo.getOutTradeNo());
		map.put("tradeType", CMBCQueryType.PAY.getCode());
		map.put("reserve", channelRequestInfo.getExtendInfo());

		return query(map);
	}

	@Override
	public ChannelResponseInfo refund(ChannelRequestInfo channelRequestInfo) {
		ChannelResponseInfo channelReponseInfo = new ChannelResponseInfo(Constants.OK, "接口调用成功.");
		//退款先查询上一笔退款情况，如果上一笔退款在等待中直接返回错误
		ChannelResponseInfo info= refundQuery(channelRequestInfo);
		if(null!=info) {
			//上一笔订单还在退款等待中，因直接返回退款失败
			if(Constants.OK.equals(info.getCode()) && ChannelTradeStatus.WAITING.name().equals(info.getTradeStatus())) {
				channelReponseInfo.setCode(ChannelResponseResult.WAITING.getCode());
				channelReponseInfo.setMsg("退款单处理中，请稍后使用原参数再次发起调用,或查询交易订单状态");
				return channelReponseInfo;
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("platformId", SysPara.CMBC_PLATFORMID);
		map.put("merchantNo", channelRequestInfo.getMchId());
		map.put("merchantSeq", channelRequestInfo.getOutTradeNo());
		map.put("orderAmount", channelRequestInfo.getRefundFee());
		

		String encryptContext = SignatureUtils.makeEncrypt(map);

		Map<String, String> params = new TreeMap<String, String>();
		params.put("businessContext", encryptContext);
		params.put("merchantNo", "");
		params.put("merchantSeq", "");
		params.put("reserve1", "");
		params.put("reserve2", "");
		params.put("reserve3", "");
		params.put("reserve4", "");
		params.put("reserve5", "");
		params.put("reserveJson", "");
		params.put("securityType", "");
		params.put("sessionId", "");
		params.put("source", "");
		params.put("transCode", "");
		params.put("transTime", "");
		params.put("version", "");
		logger.info(JsonUtils.mapToJson(params));
		String result = null;
		try {
			result = HttpClient.sendPost(SysPara.CMBC_REFUND_URL, JsonUtils.mapToJson(params));
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}
		logger.info(result);
		// 获取参数解析成map
		Map<String, String> resultMap = SignatureUtils.analysisResult(result);
		if (null == resultMap || resultMap.size() <= 0) {
			return null;
		}
		
		// 当获取参数没有问题是gateReturnCode是null,gateReturnType为S
		String gateReturnCode = resultMap.get("gateReturnCode");
		String gateReturnMessage = resultMap.get("gateReturnMessage");
		String gateReturnType = resultMap.get("gateReturnType");
		// 当gateReturnType不为S是也就没有返回的参数信息,参看原因
		if (!RETURN_TYPE_SUCCESS.equals(gateReturnType)) {
			logger.error("gateReturnCode={} , gateReturnMessage={}", gateReturnCode, gateReturnMessage);
			channelReponseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			channelReponseInfo.setMsg(gateReturnMessage);
			return channelReponseInfo;
		}
		// 验签不通过时,为自己添加错误信息
		if (ChannelResponseResult.SIGNATURE_ERROR.getCode().equals(gateReturnCode)) {
			logger.error("gateReturnCode={} , gateReturnMessage={}", gateReturnCode, gateReturnMessage);
			logger.info("Signature verification error");
			channelReponseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			channelReponseInfo.setMsg(VALID_SIGN_ERROR);
			return channelReponseInfo;
		}
		// S 订单交易成功/E 订单失败/R 原订单成功，未支付（待支付)
		String tradeStatus = resultMap.get("tradeStatus");
		CMBCTradeStatus status = CMBCTradeStatus.toCMBCTradeStatus(tradeStatus);
		if (null == status) {
			throw new RuntimeException("无法识别的订单状态");
		} else if (ChannelTradeStatus.WAITING == status.getTradeStatus()) {
			channelReponseInfo.setCode(Constants.OK);
			channelReponseInfo.setTradeStatus(status.getTradeStatus().name());
			channelReponseInfo.setMsg("用户退款中");
			return channelReponseInfo;
		} else if (ChannelTradeStatus.SUCCESS != status.getTradeStatus()) {
			channelReponseInfo.setCode(ChannelResponseResult.PAYMENT_FAIL.getCode());
			channelReponseInfo.setMsg("退款失败");
			channelReponseInfo.setTradeStatus(status.getTradeStatus().name());
			return channelReponseInfo;
		}
		// 退款成功获取参数
		String merchantName = resultMap.get("merchantName");// 回显商户名
		String merchantSeq = resultMap.get("merchantSeq");// 商户订单号
		String amount = resultMap.get("amount");// 交易金额
		String orderInfo = resultMap.get("orderInfo");// 订单详情,传入商品信息等
		String voucherNo = resultMap.get("voucherNo");// 凭证号,收单凭证号
		String bankTradeNo = resultMap.get("bankTradeNo");// 银行流水号,收单系统流水号
		String remark = resultMap.get("remark");// 保留域,所查询订单的状态
		channelReponseInfo.setTradeStatus(status.getTradeStatus().name());
		channelReponseInfo.setMchId(map.get("merchantNo"));
		// channelReponseInfo.setThirdPaymentTxnid(centerSeqId);
		channelReponseInfo.setChannelTxnNo(bankTradeNo);
		channelReponseInfo.setAttach(remark);
		channelReponseInfo.setTradeEndDate(DateUtil.formatCompactDate(DateUtil.now()));
		channelReponseInfo.setTradeEndTime(DateUtil.formatCompactTime(DateUtil.now()));
		if (StringUtils.isNotBlank(amount)) {
			channelReponseInfo.setRefundFee(Integer.valueOf(amount));
		}
		return channelReponseInfo;

	}

	@Override
	public ChannelResponseInfo refundQuery(ChannelRequestInfo channelRequestInfo) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("platformId", SysPara.CMBC_PLATFORMID);
		map.put("merchantNo", channelRequestInfo.getMchId());
		map.put("merchantSeq", channelRequestInfo.getOutTradeNo());
		map.put("tradeType", CMBCQueryType.REFUND.getCode());
		map.put("reserve", channelRequestInfo.getExtendInfo());
		map.put("orgvoucherNo", "11111111");

		return query(map);

	}

	@Override
	public ChannelResponseInfo close(ChannelRequestInfo channelRequestInfo) {
		throw new UnSupportApiException("原支付方式不支持关单操作");
	}

	public ChannelResponseInfo query(Map<String, String> map) {
		String encryptContext = SignatureUtils.makeEncrypt(map);
		Map<String, String> params = new TreeMap<String, String>();
		params.put("businessContext", encryptContext);
		params.put("merchantNo", "");
		params.put("merchantSeq", "");
		params.put("reserve1", "");
		params.put("reserve2", "");
		params.put("reserve3", "");
		params.put("reserve4", "");
		params.put("reserve5", "");
		params.put("reserveJson", "");
		params.put("securityType", "");
		params.put("sessionId", "");
		params.put("source", "");
		params.put("transCode", "");
		params.put("transTime", "");
		params.put("version", "");
		logger.info(JsonUtils.mapToJson(params));

		String result = null;
		try {
			result = HttpClient.sendPost(SysPara.CMBC_QUERY_URL, JsonUtils.mapToJson(params));
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}
		logger.info(result);
		// 获取参数解析成map
		Map<String, String> resultMap = SignatureUtils.analysisResult(result);
		if (null == resultMap || resultMap.size() <= 0) {
			return null;
		}

		ChannelResponseInfo channelReponseInfo = new ChannelResponseInfo(Constants.OK, "接口调用成功.");
		// 当获取参数没有问题是gateReturnCode是null,gateReturnType为S
		String gateReturnCode = resultMap.get("gateReturnCode");
		String gateReturnMessage = resultMap.get("gateReturnMessage");
		String gateReturnType = resultMap.get("gateReturnType");
		// 当gateReturnType不为S是也就没有返回的参数信息,参看原因
		if (!RETURN_TYPE_SUCCESS.equals(gateReturnType)) {
			logger.error("gateReturnCode={} , gateReturnMessage={}", gateReturnCode, gateReturnMessage);
			channelReponseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			channelReponseInfo.setMsg(gateReturnMessage);
			return channelReponseInfo;
		}
		// 验签不通过时,为自己添加错误信息
		if (ChannelResponseResult.SIGNATURE_ERROR.getCode().equals(gateReturnCode)) {
			logger.error("gateReturnCode={} , gateReturnMessage={}", gateReturnCode, gateReturnMessage);
			logger.info("Signature verification error");
			channelReponseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			channelReponseInfo.setMsg(VALID_SIGN_ERROR);
			return channelReponseInfo;
		}
		// S 订单交易成功/E 订单失败/R 原订单成功，未支付（待支付)/C 已撤销（理论上不存在) 已关闭/T 订单转入退款
		String tradeStatus = resultMap.get("tradeStatus");
		CMBCTradeStatus status = CMBCTradeStatus.toCMBCTradeStatus(tradeStatus);
		if (null == status) {
			throw new RuntimeException("无法识别的订单状态");
		} else if (ChannelTradeStatus.WAITING == status.getTradeStatus()) {
			channelReponseInfo.setCode(Constants.OK);
			channelReponseInfo.setTradeStatus(status.getTradeStatus().name());
			channelReponseInfo.setMsg("用户支付中");
			return channelReponseInfo;
		} else if (ChannelTradeStatus.SUCCESS != status.getTradeStatus()) {
			channelReponseInfo.setCode(ChannelResponseResult.PAYMENT_FAIL.getCode());
			channelReponseInfo.setMsg("支付失败");
			channelReponseInfo.setTradeStatus(status.getTradeStatus().name());
			return channelReponseInfo;
		}
		// 查询支付成功获取参数
		String merchantName = resultMap.get("merchantName");// 回显商户名
		String merchantSeq = resultMap.get("merchantSeq");// 商户订单号
		String amount = resultMap.get("amount");// 交易金额
		String orderInfo = resultMap.get("orderInfo");// 订单详情,传入商品信息等
		String voucherNo = resultMap.get("voucherNo");// 凭证号,收单凭证号
		String bankTradeNo = resultMap.get("bankTradeNo");// 银行流水号,收单系统流水号
		String remark = resultMap.get("remark");// 保留域,所查询订单的状态
		String refNo = resultMap.get("refNo");// 参考号
		String batchNo = resultMap.get("batchNo");// 批次号
		String cardType = resultMap.get("cardType");// 卡类型,0借记卡 1贷记卡
		String cardNo = resultMap.get("cardNo");// 卡号,前六后四中间*
		String cbCode = resultMap.get("cbCode");// 发卡行行号
		String cardName = resultMap.get("cardName");// 发卡行行名
		String fee = resultMap.get("fee");// 交易手续费
		String transType = resultMap.get("transType");// 交易类型
		String cupTermId = resultMap.get("cupTermId");// 银联终端号
		String cupTsamNo = resultMap.get("cupTsamNo");// 设备序列号
		String centerInfo = resultMap.get("centerInfo");// 其他信息
		String centerSeqId = resultMap.get("centerSeqId");// 微信订单号
		String bankOrderNo = resultMap.get("bankOrderNo");// 收单到微信下单编号
		channelReponseInfo.setTradeStatus(status.getTradeStatus().name());
		channelReponseInfo.setMchId(map.get("merchantNo"));
		channelReponseInfo.setThirdPaymentTxnid(centerSeqId);
		channelReponseInfo.setChannelTxnNo(bankTradeNo);
		channelReponseInfo.setTradeNo(merchantSeq);
		channelReponseInfo.setTotalFee(Integer.valueOf(amount));
		channelReponseInfo.setAttach(remark);
		channelReponseInfo.setTradeEndDate(DateUtil.formatCompactDate(DateUtil.now()));
		channelReponseInfo.setTradeEndTime(DateUtil.formatCompactTime(DateUtil.now()));

		if (CMBCQueryType.REFUND.getCode().equals(map.get("tradeType"))) {
			channelReponseInfo.setRefundNo(map.get("merchantNo"));
			channelReponseInfo.setRefundId(bankTradeNo);
			channelReponseInfo.setRefundFee(Integer.valueOf(amount));
		}
		return channelReponseInfo;
	}

	public ChannelResponseInfo trade(Map<String, String> map) {
		String encryptContext = SignatureUtils.makeEncrypt(map);
		Map<String, String> params = new TreeMap<String, String>();
		params.put("businessContext", encryptContext);
		params.put("merchantNo", "");
		params.put("merchantSeq", "");
		params.put("reserve1", "");
		params.put("reserve2", "");
		params.put("reserve3", "");
		params.put("reserve4", "");
		params.put("reserve5", "");
		params.put("reserveJson", "");
		params.put("securityType", "");
		params.put("sessionId", "");
		params.put("source", "");
		params.put("transCode", "");
		params.put("transTime", "");
		params.put("version", "");
		logger.info(JsonUtils.mapToJson(params));

		String result = null;
		try {
			result = HttpClient.sendPost(SysPara.CMBC_PAY_URL, JsonUtils.mapToJson(params));
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}
		logger.info(result);
		// 获取参数解析成map
		Map<String, String> resultMap = SignatureUtils.analysisResult(result);
		if (null == resultMap || resultMap.size() <= 0) {
			return null;
		}
		ChannelResponseInfo channelReponseInfo = new ChannelResponseInfo(Constants.OK, "接口调用成功.");
		// 当获取参数没有问题是gateReturnCode是null,gateReturnType为S
		String gateReturnCode = resultMap.get("gateReturnCode");
		String gateReturnMessage = resultMap.get("gateReturnMessage");
		String gateReturnType = resultMap.get("gateReturnType");
		// 当gateReturnType不为S是也就没有返回的参数信息,参看原因
		if (!RETURN_TYPE_SUCCESS.equals(gateReturnType)) {
			logger.error("gateReturnCode={} , gateReturnMessage={}", gateReturnCode, gateReturnMessage);
			channelReponseInfo.setCode(ChannelResponseResult.STATUS_FAIL.getCode());
			channelReponseInfo.setMsg(gateReturnMessage);
			return channelReponseInfo;
		}
		// 验签不通过时,为自己添加错误信息
		if (ChannelResponseResult.SIGNATURE_ERROR.getCode().equals(gateReturnCode)) {
			logger.error("gateReturnCode={} , gateReturnMessage={}", gateReturnCode, gateReturnMessage);
			logger.info("Signature verification error");
			channelReponseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			channelReponseInfo.setMsg(VALID_SIGN_ERROR);
			return channelReponseInfo;
		}
		// E 订单失败/R 原订单成功，未支付（待支付)
		String tradeStatus = resultMap.get("tradeStatus");
		CMBCTradeStatus status = CMBCTradeStatus.toCMBCTradeStatus(tradeStatus);
		if (null == status) {
			throw new RuntimeException("无法识别的订单状态");
		} else if (ChannelTradeStatus.FAILED == status.getTradeStatus()) {
			channelReponseInfo.setCode(ChannelResponseResult.PAYMENT_FAIL.getCode());
			channelReponseInfo.setMsg("支付失败");
			channelReponseInfo.setTradeStatus(status.getTradeStatus().name());
			return channelReponseInfo;
		}

		String merchantName = resultMap.get("merchantName");// 回显商户名
		String merchantSeq = resultMap.get("merchantSeq");// 商户订单号
		String amount = resultMap.get("amount");// 交易金额
		String orderInfo = resultMap.get("orderInfo");// 订单详情,传入商品信息等

		String bankTradeNo = resultMap.get("bankTradeNo");// 银行流水号,收单系统流水号
		String remark = resultMap.get("remark");// 保留域,所查询订单的状态
		String payInfo = resultMap.get("payInfo");// 微信/支付宝正扫下单返回的是base64二维码字符串;公众号支付API下单返回的是prepay_id

		channelReponseInfo.setTradeStatus(status.getTradeStatus().name());
		channelReponseInfo.setMchId(map.get("merchantNo"));
		channelReponseInfo.setChannelTxnNo(bankTradeNo);
		channelReponseInfo.setTradeNo(merchantSeq);
		channelReponseInfo.setTotalFee(Integer.valueOf(amount));
		channelReponseInfo.setAttach(remark);
		try {
			if (CMBCPayType.API_WXQRCODE.name().equals(map.get("selectTradeType")) || CMBCPayType.API_ZFBQRCODE.name().equals(map.get("selectTradeType"))) {
				//如果正扫的情况下，民生银行有参数返回说明预下单成功了 ，这边要返回成功
				channelReponseInfo.setTradeStatus("SUCCESS");
				channelReponseInfo.setAuthCodeUrl(Base64Utils.decodeBase64(payInfo));
			} else if (CMBCPayType.H5_WXJSAPI.name().equals(map.get("selectTradeType")) || CMBCPayType.H5_ZFBJSAPI.name().equals(map.get("selectTradeType"))) {
				logger.info("解析payInfo ，payInfo=" + payInfo);
				Map<String, String> payInfoMap= CMBCToolUtils.analyzeCMBCMessageToMap(payInfo);
				payInfoMap.put("package", "prepay_id="+payInfoMap.get("prepayId"));
				payInfoMap.put("signType", "MD5");
				logger.info(JsonUtils.mapToJson(payInfoMap));
				channelReponseInfo.setPayInfo(JsonUtils.mapToJson(payInfoMap));
				channelReponseInfo.setTradeStatus("SUCCESS");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception", e);
			
			
			return null;
		}
		return channelReponseInfo;             

	}

}
