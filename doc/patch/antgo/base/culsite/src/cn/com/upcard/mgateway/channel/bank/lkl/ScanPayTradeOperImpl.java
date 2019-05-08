package cn.com.upcard.mgateway.channel.bank.lkl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;
import cn.com.upcard.mgateway.exception.HttpNoResponseException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.RandomStringCreator;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class ScanPayTradeOperImpl implements ThirdTradeOper {
	
	private static final Logger logger = LoggerFactory.getLogger(ScanPayTradeOperImpl.class);
	
	private static final String[] PAY_REQUEST_SIGN_FIELDS = {"compOrgCode", "reqLogNo", "reqTm", "payChlTyp", "mercId", "termId", "txnAmt", "authCode"};
	
	private static final String[] PAY_RESPONSE_SIGN_FIELDS = {"responseCode", "reqLogNo ", "mercId", "userId ", "txnTm", "txnAmt", "payOrderId"};
	
	private static final String[] CANCEL_REQUEST_SIGN_FIELDS = {"compOrgCode", "reqLogNo", "ornReqLogNo", "reqTm", "payChlTyp", "mercId", "termId"};
	
	private static final String[] CANCEL_RESPONSE_SIGN_FIELDS = {"responseCode", "reqLogNo", "ornReqLogNo", "mercId", "userId", "txnTm", "txnAmt", "payOrderId"};
	
	private static final String[] QUERY_REQUEST_SIGN_FIELDS = {"compOrgCode", "reqLogNo", "ornReqLogNo", "reqTm", "payChlTyp", "mercId", "txnTm"};
	
	private static final String[] QUERY_RESPONSE_SIGN_FIELDS = {"responseCode", "reqLogNo ", "ornReqLogNo", "mercId", "userId ", "txnTm", "txnAmt", "payOrderId", "merOrderNo"};
	
	private static final String[] REFUND_REQUEST_SIGN_FIELDS = {"compOrgCode", "reqLogNo", "ornReqLogNo", "reqTm", "payChlTyp", "mercId", "termId", "txnAmt"};
	
	private static final String[] REFUND_RESPONSE_SIGN_FIELDS = {"responseCode", "reqLogNo ", "ornReqLogNo", "mercId", "userId ", "txnTm", "txnAmt", "payOrderId"};
	
	private static final String[] REFUND_QUERY_REQUEST_SIGN_FIELDS = {"compOrgCode", "reqLogNo ", "ornReqLogNo", "reqTm", "payChlTyp", "mercId", "termId"};
	
	private static final String[] REFUND_QUERY_RESPONSE_SIGN_FIELDS = {"responseCode", "reqLogNo", "ornReqLogNo", "mercId", "userId", "txnTm", "txnAmt"};
	
	private static final String[] NATIVE_CLOSE_REQUEST_SIGN_FIELDS = {"compOrgCode", "reqLogNo ", "ornReqLogNo", "reqTm", "payChlTyp", "mercId", "termId"};
	
	private static final String[] NATIVE_CLOSE_RESPONSE_SIGN_FIELDS = {"responseCode", "reqLogNo", "ornReqLogNo", "mercId", "userId", "txnTm", "txnAmt"};
	
	private String createRequestSign(String[] signFields, Map<String, String> request, String sercretKey) {
		StringBuffer buffer = new StringBuffer();
		for (String fieldName : signFields) {
			buffer.append(request.get(fieldName));
		}
		buffer.append(sercretKey);
		
		String sign = Sha1.getSHA1(buffer.toString()).toLowerCase();
		logger.info("create sign :{}", sign);
		return sign;
	}
	
	private boolean checkResponseSign(String[] signFields, Map<String, String> response, String sercretKey) {
		String signByResponse = this.createRequestSign(signFields, response, sercretKey);
		String sign = response.get("MAC");
		if (StringUtils.isEmpty(sign) || !sign.equalsIgnoreCase(signByResponse)) {
			logger.warn("create sign by response:{}", signByResponse);
			return false;
		}
		return true;
	}
	
	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("FunCod", String.valueOf(FunCode.MICRO_PAY.getFunCode()));
		requestMap.put("compOrgCode", SysPara.LKL_KEY);
		requestMap.put("reqLogNo", channelRequestInfo.getOutTradeNo());
		requestMap.put("reqTm", channelRequestInfo.getTimeStart());
		requestMap.put("payChlTyp", PayChannelType.toPayChannelType(channelRequestInfo.getChannelType()).name());
		requestMap.put("mercId", channelRequestInfo.getMchId());
		requestMap.put("termId", channelRequestInfo.getDeviceInfo());
		requestMap.put("txnAmt", channelRequestInfo.getTotalFee());
		requestMap.put("authCode", channelRequestInfo.getAuthCode());
		requestMap.put("goodsTag", channelRequestInfo.getGoodsTag());
		requestMap.put("orderInfo", channelRequestInfo.getGoodsDetail());
		requestMap.put("sub_appid", channelRequestInfo.getSubAppid());
		requestMap.put("extData", channelRequestInfo.getExtendInfo());
        requestMap.put("MAC", this.createRequestSign(PAY_REQUEST_SIGN_FIELDS, requestMap, SysPara.LKL_KEY));
        
        Map<String, String> response = null;
        try {
        	response = HttpClient.sendXmlPost(requestMap, FunCode.MICRO_PAY.getFunRequestUrl(), LklApiContext.getDefaultEncoding());
		}  catch (HttpNoResponseException e) {
			logger.info("no response!");
			return null;
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉返回信息格式错误，不是xml");
			return new ChannelResponseInfo(ChannelResponseResult.PAYMENT_FAIL, ChannelTradeStatus.FAILED);
		}
        
        String responseCode = response.get("responsecode");
        LklError lklError = LklError.toLklError(responseCode);
        if (lklError == LklError.PAYING) {
        	return new ChannelResponseInfo(ChannelResponseResult.PAYING, ChannelTradeStatus.WAITING);
        } else if (lklError == LklError.SUCCESS) {
        	if (this.checkResponseSign(PAY_RESPONSE_SIGN_FIELDS, response, SysPara.LKL_KEY)) {
        		return new ChannelResponseInfo(ChannelResponseResult.STATUS_FAIL, ChannelTradeStatus.FAILED);
        	}
        	ChannelResponseInfo info = new ChannelResponseInfo(ChannelResponseResult.SUCCESS, ChannelTradeStatus.SUCCESS);
        	info.setTradeNo(response.get("reqLogNo"));
        	info.setMchId(response.get("mercId"));
        	info.setThirdBuyerAccount(response.get("userId"));
        	info.setThirdPaymentTxnid(response.get("payOrderId"));
        	info.setChannelTxnNo(response.get("merOrderNo"));
        	info.setAttach(response.get("extRpData"));
        	info.setTotalFee(Integer.parseInt(response.get("txnAmt")));
        	info.setDiscountInfo(response.get("mrkInfo"));
        	Date now = DateUtil.now();
        	info.setTradeEndDate(DateUtil.format2(now, DateUtil.yyyyMMdd));
        	info.setTradeEndDate(DateUtil.format2(now, DateUtil.HHmmss));
        	//校验返回签名
        	return info;
        } else {
        	return new ChannelResponseInfo(lklError.getChannelResponseResult(), ChannelTradeStatus.FAILED);
        }
	}
	
	
	@Override
	public ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("FunCod", String.valueOf(FunCode.MICRO_CANCEL.getFunCode()));
		requestMap.put("compOrgCode", SysPara.LKL_KEY);
		requestMap.put("reqLogNo", channelRequestInfo.getOutTradeNo());
		requestMap.put("ornReqLogNo", LklApiContext.generateReqLogNo());
		requestMap.put("reqTm", channelRequestInfo.getTimeStart());
		requestMap.put("payChlTyp", PayChannelType.toPayChannelType(channelRequestInfo.getChannelType()).name());
		requestMap.put("mercId", channelRequestInfo.getMchId());
		requestMap.put("termId", channelRequestInfo.getDeviceInfo());
		requestMap.put("sub_appid", channelRequestInfo.getSubAppid());
		requestMap.put("extData", channelRequestInfo.getExtendInfo());
        requestMap.put("MAC", this.createRequestSign(CANCEL_REQUEST_SIGN_FIELDS, requestMap, SysPara.LKL_KEY));
        
        Map<String, String> response = null;
        try {
        	response = HttpClient.sendXmlPost(requestMap, FunCode.MICRO_CANCEL.getFunRequestUrl(), LklApiContext.getDefaultEncoding());
		}  catch (HttpNoResponseException e) {
			logger.info("no response!");
			return null;
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉返回信息格式错误，不是xml");
			return new ChannelResponseInfo(ChannelResponseResult.PAYMENT_FAIL, ChannelTradeStatus.FAILED);
		}
        
        String responseCode = response.get("responseCode");
        LklError lklError = LklError.toLklError(responseCode);
        if (lklError == LklError.SUCCESS) {
        	if (this.checkResponseSign(CANCEL_RESPONSE_SIGN_FIELDS, response, SysPara.LKL_KEY)) {
        		return new ChannelResponseInfo(ChannelResponseResult.STATUS_FAIL, null);
        	}
        	ChannelResponseInfo info = new ChannelResponseInfo(ChannelResponseResult.SUCCESS, ChannelTradeStatus.SUCCESS);
        	info.setTradeNo(response.get("reqLogNo"));
        	info.setMchId(response.get("mercId"));
        	info.setTradeNo(response.get("ornReqLogNo"));
        	info.setThirdBuyerAccount(response.get("userId"));
        	info.setThirdPaymentTxnid(response.get("payOrderId"));
        	info.setChannelTxnNo(response.get("merOrderNo"));
        	info.setAttach(response.get("extRpData"));
        	info.setTotalFee(Integer.parseInt(response.get("txnAmt")));
        	info.setMsg(response.get("message"));
        	String txnTm = response.get("txnTm");
        	info.setTradeEndDate(txnTm.substring(0, 8));
        	info.setTradeEndDate(txnTm.substring(8));
        	//校验返回签名
        	return info;
        } else {
        	return new ChannelResponseInfo(lklError.getChannelResponseResult(), ChannelTradeStatus.FAILED);
        }
	}

	@Override
	public ChannelResponseInfo tradeQuery(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("FunCod", String.valueOf(FunCode.MICRO_CANCEL.getFunCode()));
		requestMap.put("compOrgCode", SysPara.LKL_KEY);
		requestMap.put("reqLogNo", channelRequestInfo.getOutTradeNo());
		requestMap.put("ornReqLogNo", LklApiContext.generateReqLogNo());
		requestMap.put("reqTm", DateUtil.format2(new Date(), DateUtil.yyyyMMddHHmmss));
		requestMap.put("payChlTyp", PayChannelType.toPayChannelType(channelRequestInfo.getChannelType()).name());
		requestMap.put("mercId", channelRequestInfo.getMchId());
		requestMap.put("termId", channelRequestInfo.getDeviceInfo());
		requestMap.put("sub_appid", channelRequestInfo.getSubAppid());
		requestMap.put("extData", channelRequestInfo.getExtendInfo());
        requestMap.put("MAC", this.createRequestSign(QUERY_REQUEST_SIGN_FIELDS, requestMap, SysPara.LKL_KEY));
        
        Map<String, String> response = null;
        try {
        	response = HttpClient.sendXmlPost(requestMap, FunCode.QUERY.getFunRequestUrl(), LklApiContext.getDefaultEncoding());
		}  catch (HttpNoResponseException e) {
			logger.info("no response!");
			return null;
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉返回信息格式错误，不是xml");
			return new ChannelResponseInfo(ChannelResponseResult.SYSTEM_ERROR, null);
		}
        
        String responseCode = response.get("responseCode");
        LklError lklError = LklError.toLklError(responseCode);
        if (lklError == LklError.PAYING) {
        	return new ChannelResponseInfo(ChannelResponseResult.PAYING, ChannelTradeStatus.WAITING);
        } else if (lklError == LklError.SUCCESS) {
        	if (this.checkResponseSign(QUERY_RESPONSE_SIGN_FIELDS, response, SysPara.LKL_KEY)) {
        		return new ChannelResponseInfo(ChannelResponseResult.STATUS_FAIL, null);
        	}
        	
        	ChannelResponseInfo info = new ChannelResponseInfo(ChannelResponseResult.SUCCESS, ChannelTradeStatus.SUCCESS);
        	info.setTradeNo(response.get("ornReqLogNo"));
        	info.setMchId(response.get("mercId"));
        	info.setThirdBuyerAccount(response.get("userId"));
        	String txnTm = response.get("txnTm");
        	//TODO确认时间格式
        	info.setTradeEndDate(txnTm.substring(0, 8));
        	info.setTradeEndTime(txnTm.substring(8));
        	String txnAmt = response.get("txnAmt");
        	if (StringUtils.isEmpty(txnAmt)) {
        		info.setTotalFee(Integer.parseInt(response.get("txnAmt")));
        	}
        	info.setThirdPaymentTxnid(response.get("payOrderId"));
        	info.setChannelTxnNo(response.get("merOrderNo"));
        	info.setAttach(response.get("extRpData"));
        	info.setDiscountInfo(response.get("mrkInfo"));
        	info.setMsg(response.get("message"));
        	info.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
        	//校验返回签名
        	return info;
        } else {
        	return new ChannelResponseInfo(lklError.getChannelResponseResult(), ChannelTradeStatus.FAILED);
        }
	
	
	}


	@Override
	public ChannelResponseInfo refund(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("FunCod", String.valueOf(FunCode.REFUND.getFunCode()));
		requestMap.put("compOrgCode", SysPara.LKL_KEY);
		requestMap.put("reqLogNo", channelRequestInfo.getOutRefundNo());
		requestMap.put("ornReqLogNo", channelRequestInfo.getOutTradeNo());
		requestMap.put("reqTm", DateUtil.format2(new Date(), DateUtil.yyyyMMddHHmmss));
		requestMap.put("payChlTyp", PayChannelType.toPayChannelType(channelRequestInfo.getChannelType()).name());
		requestMap.put("mercId", channelRequestInfo.getMchId());
		requestMap.put("termId", channelRequestInfo.getDeviceInfo());
		requestMap.put("txnAmt", channelRequestInfo.getRefundFee());
		requestMap.put("sub_appid", channelRequestInfo.getSubAppid());
		requestMap.put("extData", channelRequestInfo.getExtendInfo());
        requestMap.put("MAC", this.createRequestSign(REFUND_REQUEST_SIGN_FIELDS, requestMap, SysPara.LKL_KEY));
        
        Map<String, String> response = null;
        try {
        	response = HttpClient.sendXmlPost(requestMap, FunCode.REFUND.getFunRequestUrl(), LklApiContext.getDefaultEncoding());
		}  catch (HttpNoResponseException e) {
			logger.info("no response!");
			return null;
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉返回信息格式错误，不是xml");
			return new ChannelResponseInfo(ChannelResponseResult.SYSTEM_ERROR, null);
		}
        
        String responseCode = response.get("responseCode");
        LklError lklError = LklError.toLklError(responseCode);
        if (lklError == LklError.SUCCESS) {
        	if (this.checkResponseSign(REFUND_RESPONSE_SIGN_FIELDS, response, SysPara.LKL_KEY)) {
        		return new ChannelResponseInfo(ChannelResponseResult.STATUS_FAIL, null);
        	}
        	
        	ChannelResponseInfo info = new ChannelResponseInfo(ChannelResponseResult.SUCCESS, ChannelTradeStatus.SUCCESS);
        	info.setTradeNo(response.get("reqLogNo"));
        	info.setMchId(response.get("mercId"));
        	info.setThirdBuyerAccount(response.get("userId"));
        	String txnTm = response.get("txnTm");
        	//TODO确认时间格式
        	info.setTradeEndDate(txnTm.substring(0, 8));
        	info.setTradeEndTime(txnTm.substring(8));
        	info.setTotalFee(Integer.parseInt(response.get("txnAmt")));
        	info.setThirdPaymentTxnid(response.get("payOrderId"));
        	info.setChannelTxnNo(response.get("merOrderNo"));
        	info.setAttach(response.get("extRpData"));
        	info.setMsg(response.get("message"));
        	info.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
        	//校验返回签名
        	return info;
        } else {
        	return new ChannelResponseInfo(lklError.getChannelResponseResult(), ChannelTradeStatus.FAILED);
        }
	}

	@Override
	public ChannelResponseInfo refundQuery(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("FunCod", String.valueOf(FunCode.REFUND_QUERY.getFunCode()));
		requestMap.put("compOrgCode", SysPara.LKL_KEY);
		requestMap.put("reqLogNo", LklApiContext.generateReqLogNo());
		requestMap.put("ornReqLogNo", channelRequestInfo.getOutRefundNo());
		requestMap.put("reqTm", DateUtil.format2(new Date(), DateUtil.yyyyMMddHHmmss));
		requestMap.put("payChlTyp", PayChannelType.toPayChannelType(channelRequestInfo.getChannelType()).name());
		requestMap.put("mercId", channelRequestInfo.getMchId());
		requestMap.put("termId", channelRequestInfo.getDeviceInfo());
		requestMap.put("txnAmt", channelRequestInfo.getRefundFee());
		requestMap.put("sub_appid", channelRequestInfo.getSubAppid());
		requestMap.put("extData", channelRequestInfo.getExtendInfo());
        requestMap.put("MAC", this.createRequestSign(REFUND_QUERY_REQUEST_SIGN_FIELDS, requestMap, SysPara.LKL_KEY));
        
        Map<String, String> response = null;
        try {
        	response = HttpClient.sendXmlPost(requestMap, FunCode.REFUND_QUERY.getFunRequestUrl(), LklApiContext.getDefaultEncoding());
		}  catch (HttpNoResponseException e) {
			logger.info("no response!");
			return null;
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉返回信息格式错误，不是xml");
			return new ChannelResponseInfo(ChannelResponseResult.SYSTEM_ERROR, null);
		}
        
        String responseCode = response.get("responseCode");
        LklError lklError = LklError.toLklError(responseCode);
        if (lklError == LklError.SUCCESS) {
        	if (this.checkResponseSign(REFUND_QUERY_RESPONSE_SIGN_FIELDS, response, SysPara.LKL_KEY)) {
        		return new ChannelResponseInfo(ChannelResponseResult.STATUS_FAIL, null);
        	}
        	
        	ChannelResponseInfo info = new ChannelResponseInfo(ChannelResponseResult.SUCCESS, ChannelTradeStatus.SUCCESS);
        	info.setTradeNo(response.get("ornReqLogNo"));
        	info.setMchId(response.get("mercId"));
        	info.setThirdBuyerAccount(response.get("userId"));
        	String txnTm = response.get("txnTm");
        	//TODO确认时间格式
        	info.setTradeEndDate(txnTm.substring(0, 8));
        	info.setTradeEndTime(txnTm.substring(8));
        	info.setTotalFee(Integer.parseInt(response.get("txnAmt")));
        	info.setThirdPaymentTxnid(response.get("payOrderId"));
        	info.setChannelTxnNo(response.get("merOrderNo"));
        	info.setAttach(response.get("extRpData"));
        	info.setMsg(response.get("message"));
        	info.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
        	//校验返回签名
        	return info;
        } else {
        	return new ChannelResponseInfo(lklError.getChannelResponseResult(), ChannelTradeStatus.FAILED);
        }
	}

//	FunCod	功能码
//	compOrgCode	机构代码
//	reqLogNo	请求流水号
//	ornReqLogNo	原请求流水号
//	reqTm	请求时间
//	payChlTyp	支付渠道类型
//	mercId	商户号
//	termId	终端号
//	reqType	发起撤消类型
//	sub_appid	子商户appid
//	extData	扩展信息
//	MAC	SHA1计算值
	@Override
	public ChannelResponseInfo close(ChannelRequestInfo channelRequestInfo) {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("FunCod", String.valueOf(FunCode.NATIVE_CLOSE.getFunCode()));
		requestMap.put("compOrgCode", SysPara.LKL_KEY);
		requestMap.put("reqLogNo", LklApiContext.generateReqLogNo());
		requestMap.put("ornReqLogNo", channelRequestInfo.getOutTradeNo());
		requestMap.put("reqTm", DateUtil.format2(new Date(), DateUtil.yyyyMMddHHmmss));
		requestMap.put("payChlTyp", PayChannelType.toPayChannelType(channelRequestInfo.getChannelType()).name());
		requestMap.put("mercId", channelRequestInfo.getMchId());
		requestMap.put("termId", channelRequestInfo.getDeviceInfo());
		requestMap.put("reqType", RevokType.USER.getRevokCode());
		requestMap.put("sub_appid", channelRequestInfo.getSubAppid());
		requestMap.put("extData", channelRequestInfo.getExtendInfo());
        requestMap.put("MAC", this.createRequestSign(NATIVE_CLOSE_REQUEST_SIGN_FIELDS, requestMap, SysPara.LKL_KEY));
        
        Map<String, String> response = null;
        try {
        	response = HttpClient.sendXmlPost(requestMap, FunCode.NATIVE_CLOSE.getFunRequestUrl(), LklApiContext.getDefaultEncoding());
		}  catch (HttpNoResponseException e) {
			logger.info("no response!");
			return null;
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error("拉卡拉返回信息格式错误，不是xml");
			return new ChannelResponseInfo(ChannelResponseResult.SYSTEM_ERROR, null);
		}
        
        String responseCode = response.get("responseCode");
        LklError lklError = LklError.toLklError(responseCode);
        if (lklError == LklError.SUCCESS) {
        	if (this.checkResponseSign(NATIVE_CLOSE_RESPONSE_SIGN_FIELDS, response, SysPara.LKL_KEY)) {
        		return new ChannelResponseInfo(ChannelResponseResult.STATUS_FAIL, null);
        	}
        	
        	ChannelResponseInfo info = new ChannelResponseInfo(ChannelResponseResult.SUCCESS, ChannelTradeStatus.SUCCESS);
        	info.setTradeNo(response.get("ornReqLogNo"));
        	info.setMchId(response.get("mercId"));
        	info.setThirdBuyerAccount(response.get("userId"));
        	String txnTm = response.get("txnTm");
        	//TODO确认时间格式
        	info.setTradeEndDate(txnTm.substring(0, 8));
        	info.setTradeEndTime(txnTm.substring(8));
        	info.setTotalFee(Integer.parseInt(response.get("txnAmt")));
        	info.setThirdPaymentTxnid(response.get("payOrderId"));
        	info.setChannelTxnNo(response.get("merOrderNo"));
        	info.setAttach(response.get("extRpData"));
        	info.setMsg(response.get("message"));
        	info.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
        	//校验返回签名
        	return info;
        } else {
        	return new ChannelResponseInfo(lklError.getChannelResponseResult(), ChannelTradeStatus.FAILED);
        }
	}

}
