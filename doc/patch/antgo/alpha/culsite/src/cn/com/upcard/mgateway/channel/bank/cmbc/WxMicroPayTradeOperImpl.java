package cn.com.upcard.mgateway.channel.bank.cmbc;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.bank.cmbc.common.CMBCPayType;
import cn.com.upcard.mgateway.channel.bank.cmbc.common.CMBCTradeStatus;
import cn.com.upcard.mgateway.channel.bank.cmbc.util.Base64Utils;
import cn.com.upcard.mgateway.channel.bank.cmbc.util.HttpClient;
import cn.com.upcard.mgateway.channel.bank.cmbc.util.JsonUtils;
import cn.com.upcard.mgateway.channel.bank.cmbc.util.SignatureUtils;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.channel.enums.ChannelTradeStatus;
import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class WxMicroPayTradeOperImpl extends AbstractUnifiedOper {
	private static Logger logger = LoggerFactory.getLogger(AlipayNativeTradeOperImpl.class);
	private static final String DATE_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo) {
		// 微信反扫
		Map<String, String> map = new HashMap<String, String>();
		map.put("platformId", SysPara.CMBC_PLATFORMID);
		map.put("merchantNo", channelRequestInfo.getMchId());
		map.put("selectTradeType", CMBCPayType.API_WXSCAN.name());
		map.put("amount", channelRequestInfo.getTotalFee());
		map.put("orderInfo", channelRequestInfo.getBody());
		map.put("merchantSeq", channelRequestInfo.getOutTradeNo());
		map.put("transDate", DateUtil.formatCompactDate(DateUtil.now()));
		map.put("transTime", DateUtil.format2(DateUtil.now(), DATE_YYYYMMDDHHMMSSSSS));
		
		//FIXME 异步通知地址（线下交易是不传的） 
		map.put("notifyUrl", SysPara.CMBC_NOTIFY_URL);
		try {
			map.put("remark", Base64Utils.encodeBase64(channelRequestInfo.getAuthCode()));
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception", e);
			return null;
		}

		return super.trade(map);

	}
	
	/**
	 * 撤销接口为退款接口改装的
	 */
	@Override
	public ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo) {
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
		// S 订单交易成功/E 订单失败/R 原订单成功，未支付（待支付)
		String tradeStatus = resultMap.get("tradeStatus");
		CMBCTradeStatus status = CMBCTradeStatus.toCMBCTradeStatus(tradeStatus);
		if (null == status) {
			throw new RuntimeException("无法识别的订单状态");
		} else if (ChannelTradeStatus.WAITING == status.getTradeStatus()) {
			// 撤销时，退款等待中应记录为退款成功
			channelReponseInfo.setMchId(map.get("merchantNo"));
			channelReponseInfo.setCode(Constants.OK);
			channelReponseInfo.setTradeStatus(ChannelTradeStatus.SUCCESS.name());
			channelReponseInfo.setMsg("撤销成功");

			return channelReponseInfo;
		} else if (ChannelTradeStatus.SUCCESS != status.getTradeStatus()) {
			channelReponseInfo.setCode(ChannelResponseResult.PAYMENT_FAIL.getCode());
			channelReponseInfo.setMsg("撤销失败");
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
		channelReponseInfo.setResponseResult(ResponseResult.OK);

		// channelReponseInfo.setAttach(attach);

		return channelReponseInfo;

	}

}
