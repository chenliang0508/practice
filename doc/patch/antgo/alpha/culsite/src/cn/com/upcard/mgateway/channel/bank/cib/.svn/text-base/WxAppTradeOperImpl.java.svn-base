package cn.com.upcard.mgateway.channel.bank.cib;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.upcard.mgateway.channel.ThirdTradeOper;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibPayLimitType;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibServiceType;
import cn.com.upcard.mgateway.channel.bank.cib.sdk.util.SignUtils;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.common.enums.PayLimitType;
import cn.com.upcard.mgateway.exception.UnSupportApiException;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.XmlUtils;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

@Service
public class WxAppTradeOperImpl extends AbstractUnifiedOper implements ThirdTradeOper {

	private static Logger logger = LoggerFactory.getLogger(WxAppTradeOperImpl.class);

	private static final String THIRD_RETURN_SUCCESS = "0";
	private static final String SUCCESS_MSG = "success";
	private static final String VALID_SIGN_ERROR = "system error:valid sign error!";

	/**
	 * 非原生态预下单API
	 */
	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo requestInfo) {

		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("service", CibServiceType.WX_APP_PAY_SERVICE.getService()); // 接口类型
		map.put("mch_id", requestInfo.getMchId()); // 商户号
		map.put("out_trade_no", requestInfo.getOutTradeNo()); // 商户订单号
		map.put("device_info", requestInfo.getDeviceInfo()); // 设备号
		map.put("body", requestInfo.getBody()); // 商品描述
		map.put("attach", requestInfo.getExtendInfo()); // 附加信息
		map.put("total_fee", requestInfo.getTotalFee()); // 总金额
		map.put("mch_create_ip", requestInfo.getReqIp()); // 终端ip
		map.put("notify_url", requestInfo.getNotifyUrl()); // 通知地址
		map.put("time_start", requestInfo.getTimeStart()); // 订单生成时间
		map.put("time_expire", requestInfo.getTimeExpire()); // 订单超时时间
		map.put("goods_tag", requestInfo.getGoodsTag()); // 商品标记
		map.put("sub_appid", requestInfo.getSubAppid());
		map.put("limit_credit_pay", CibPayLimitType.toCibPayLimitType(PayLimitType.topayLimitType(requestInfo.getLimitCreditPay()))); // 是否限制使用信用卡，1：禁用，0：可用，不传为可用
		map.put("nonce_str", String.valueOf(new Date().getTime())); // 随机字符串
		map.put("sign", SignUtils.generateSign(map, requestInfo.getCommPrivateKey()));
		logger.info("map = {}", map);

		Map<String, String> resultMap = null;
		try {
			resultMap = HttpClient.sendXmlPost(map, "https://124.74.143.162:15023/thirdpartplatform/scancodpay/8001.dor");
			logger.info("调用兴业微信app支付接口返回 res ---> \r\n" + XmlUtils.toXml(resultMap));
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
		if (!SignUtils.checkParam(resultMap, requestInfo.getPrivateKey())) {
			logger.info("Signature verification error");
			responseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			responseInfo.setMsg(VALID_SIGN_ERROR);
			return responseInfo;
		}

		// status为0的时候
		String services = resultMap.get("services"); // 支持的支付类型
		String token_id = resultMap.get("token_id");

		return null;
	}

	@Override
	public ChannelResponseInfo close(ChannelRequestInfo requestInfo) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("service", CibServiceType.WX_APP_CLOSE_SERVICE.getService());
		map.put("mch_id", requestInfo.getMchId());
		map.put("out_trade_no", requestInfo.getOutTradeNo());
		map.put("nonce_str", String.valueOf(new Date().getTime()));
		map.put("sign", SignUtils.generateSign(map, requestInfo.getCommPrivateKey()));

		Map<String, String> resultMap = null;
		try {
			resultMap = HttpClient.sendXmlPost(map, SysPara.SWIFT_REQ_URL);
		} catch (Exception e) {
			logger.error("Exception", e);
			// TODO 失败的返回
		}

		String res = XmlUtils.toXml(resultMap);
		logger.info("调用威富通支付接口返回 res ---> \r\n" + res);

		String status = resultMap.get("status");
		String message = resultMap.get("message");

		if (!"0".equals(status)) {
			// TODO 错误情况的应答，记录错误，系统错误
			logger.error("status = {}", status);
		}
		if (!resultMap.containsKey("sign")) {
			// TODO 返回结果没有签名，记录错误，系统错误
			logger.info("Signature does not exist");
		}
		if (!SignUtils.checkParam(resultMap, requestInfo.getCommPrivateKey())) {
			// TODO 签名错误，记录错误，系统错误
			logger.info("Signature verification error");
		}

		// status为0的时候返回
		String resultCode = resultMap.get("result_code");
		String mchId = resultMap.get("mch_id");
		String nonceStr = resultMap.get("nonce_str");
		String errCode = resultMap.get("err_code");
		String errMsg = resultMap.get("err_msg");

		return null;
	}

	@Override
	public ChannelResponseInfo cancel(ChannelRequestInfo requestInfo) {
		// 不支持撤销接口
		throw new UnSupportApiException("原支付方式不支持撤销操作");
	}

}
