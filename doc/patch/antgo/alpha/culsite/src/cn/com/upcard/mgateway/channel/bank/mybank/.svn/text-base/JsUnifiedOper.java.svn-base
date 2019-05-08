package cn.com.upcard.mgateway.channel.bank.mybank;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.bank.mybank.common.MyBankResponseCode;
import cn.com.upcard.mgateway.channel.bank.mybank.common.MyBankServiceType;
import cn.com.upcard.mgateway.channel.bank.mybank.sdk.util.HttpClientMyBank;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.common.enums.OrderStatus;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.exception.AbnormalResponseException;
import cn.com.upcard.mgateway.exception.UnSupportApiException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

/**
 * 用于线上js支付
 * 
 * @author zhoudi
 *
 */
public class JsUnifiedOper extends AbstractUnifiedOper {

	private static Logger logger = LoggerFactory.getLogger(JsUnifiedOper.class);

	private final String URL = SysPara.MYBANK_REQ_URL;
	private static final String DEFAULT_SETTLE_TYPE = "T1"; // 网商默认清算方式
	private static final String VERSION = "1.0.0";
	private static final String INPUT_CHARSET = "UTF-8";
	private static final String VALID_SIGN_ERROR = "system error:valid sign error!";

	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo) {

		String function = MyBankServiceType.JS_PAY_SERVICE.getService();

		Map<String, String> data = new HashMap<String, String>();
		data.put("Function", function);
		data.put("ReqMsgId", UUID.randomUUID().toString());
		data.put("Version", VERSION);
		data.put("Appid", SysPara.MYBANK_APP_ID);
		data.put("ReqTime", new Timestamp(System.currentTimeMillis()).toString());
		data.put("InputCharset", INPUT_CHARSET);

		data.put("OutTradeNo", channelRequestInfo.getOutTradeNo()); // 外部订单号
		data.put("Body", channelRequestInfo.getBody()); // 客户端明细中展示，店名-销售商品类目
		data.put("GoodsTag", channelRequestInfo.getGoodsTag()); // 微信使用，商品标记，用于微信代金券或立减优惠
		// data.put("GoodsDetail", channelRequestInfo.getGoodsTag()); //
		// 支付宝单品优惠功能字段，商品详情列表，JSON格式，会透传至第三方支付。
		data.put("TotalAmount", channelRequestInfo.getTotalFee()); // 交易总额度，单位分
		data.put("Currency", "CNY"); // 币种默认 CNY
		data.put("MerchantId", channelRequestInfo.getMchId()); // 商户号
		data.put("IsvOrgId", SysPara.MYBANK_ISV_ORG_ID); // 合作方机构号，网商银行分配，指的是银商
		data.put("ChannelType", channelRequestInfo.getChannelType()); // 支付渠道：WX：微信支付，ALI：支付宝
		data.put("OpenId", channelRequestInfo.getThirdBuyerId()); // 消费者用户标识，分支付宝和微信
		data.put("OperatorId", channelRequestInfo.getOpUserId()); // 操作员id
		// data.put("StoreId", null); // 非必传，门店id
		data.put("DeviceId", channelRequestInfo.getDeviceInfo()); // 终端设备号
		data.put("DeviceCreateIp", channelRequestInfo.getReqIp()); // 生成订单机器的ip
		data.put("ExpireExpress", channelRequestInfo.getExpireExpress()); // 订单有效期，按分钟计算，1-1440。不设置为 1 小时
		data.put("SettleType", DEFAULT_SETTLE_TYPE); // 清算方式，默认T1
		data.put("Attach", channelRequestInfo.getExtendInfo()); // 附加信息原样返回
		data.put("NotifyUrl", channelRequestInfo.getNotifyUrl()); // 该字段目前暂不支持，回调地址在合作机构进驻时领取PartnerId时手工配置
		data.put("SubAppId", channelRequestInfo.getSubAppid()); // 合作方公众号。微信支付必须上送，请合作方进驻时进行人工报备
		data.put("PayLimit", channelRequestInfo.getPayLimit()); // 禁用的支付方式，用逗号隔开；credit：信用卡，pcredit：花呗（仅支付宝）
		logger.info("网商h5支付请求，data={}", data);

		Map<String, Object> resultMap = null;
		ChannelResponseInfo responseInfo = new ChannelResponseInfo();
		try {
			resultMap = HttpClientMyBank.sendXmlPost(URL, function, data);
			logger.info("网商h5支付返回，resultMap={}", resultMap);
		} catch (AbnormalResponseException e) {
			logger.error("网商返回全局错误码 = {}", e.getMessage());
			ResponseResult responseResult = MyBankResponseCode.transferCode(e.getMessage());
			responseInfo.setResponseResult(responseResult);
			responseInfo.setCode(responseResult.getCode());
			responseInfo.setMsg(responseResult.getMsg());
			return responseInfo;
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}

		if (null == resultMap) {
			logger.info("网商返回结果验签失败");
			responseInfo.setCode(ChannelResponseResult.SIGNATURE_ERROR.getCode());
			responseInfo.setMsg(VALID_SIGN_ERROR);
			return responseInfo;
		}

		@SuppressWarnings("unchecked")
		Map<String, String> respInfo = (Map<String, String>) resultMap.get("RespInfo");
		String resultStatus = respInfo.get("ResultStatus"); // 处理状态：S:成功,F:失败,U:未知
		String resultCode = respInfo.get("ResultCode"); // 当状态为S时 0000，否则为全局返回码或者业务返回码
		String resultMsg = respInfo.get("ResultMsg"); // 失败原因
		// String outTradeNo = (String) resultMap.get("OutTradeNo"); // 银商的订单号

		// 失败
		if ("F".equals(resultStatus)) {
			logger.error("resultCode={} , resultMsg={}", resultCode, resultMsg);
			responseInfo.setResponseResult(ResponseResult.TRADE_FAILED);
			responseInfo.setTradeStatus(OrderStatus.FAILED.name());
			responseInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			return responseInfo;
		}
		// 表示未知，需要调用查询接口
		if ("U".equals(resultStatus)) {
			responseInfo.setResponseResult(ResponseResult.OK);
			responseInfo.setTradeStatus(OrderStatus.WAITING.name());
			responseInfo.setCode(ResponseResult.OK.getCode());
			return responseInfo;
		}

		// 下面参数在 resultStatus = S 时返回
		String orderNo = null;
		if (null != resultMap.get("OrderNo")) {
			orderNo = (String) resultMap.get("OrderNo"); // 网商订单号
		}

		String prePayId = null;
		if (null != resultMap.get("PrePayId")) {
			prePayId = (String) resultMap.get("PrePayId"); // 支付宝支付使用
		}

		String payInfo = null;
		if (null != resultMap.get("PayInfo")) {
			payInfo = (String) resultMap.get("PayInfo"); // 微信支付使用
		}

		responseInfo.setResponseResult(ResponseResult.OK);
		responseInfo.setCode(ResponseResult.OK.getCode());
		responseInfo.setTradeStatus(OrderStatus.SUCCESS.name());
		responseInfo.setChannelTxnNo(orderNo);
		if (StringUtils.isNotEmpty(payInfo)) {
			responseInfo.setPayInfo(payInfo);
		} else {
			responseInfo.setPayInfo(prePayId);
		}
		return responseInfo;
	}

	@Override
	public ChannelResponseInfo close(ChannelRequestInfo channelRequestInfo) {

		String function = MyBankServiceType.CLOSE_SERVICE.getService();

		Map<String, String> data = new HashMap<String, String>();
		data.put("Function", function);
		data.put("ReqMsgId", UUID.randomUUID().toString());
		data.put("Version", VERSION);
		data.put("Appid", SysPara.MYBANK_APP_ID);
		data.put("ReqTime", new Timestamp(System.currentTimeMillis()).toString());
		data.put("InputCharset", INPUT_CHARSET);

		data.put("IsvOrgId", SysPara.MYBANK_ISV_ORG_ID);
		data.put("MerchantId", channelRequestInfo.getMchId());
		data.put("OutTradeNo", channelRequestInfo.getOutTradeNo());
		logger.info("网商关单请求，data={}", data);

		Map<String, Object> resultMap = null;
		ChannelResponseInfo responseInfo = new ChannelResponseInfo();
		try {
			resultMap = HttpClientMyBank.sendXmlPost(URL, function, data);
			logger.info("网商关单结果，resultMap={}", resultMap);
		} catch (AbnormalResponseException e) {
			logger.error("网商返回全局错误码 = {}", e);
			ResponseResult responseResult = MyBankResponseCode.transferCode(e.getMessage());
			responseInfo.setResponseResult(responseResult);
			responseInfo.setCode(responseResult.getCode());
			responseInfo.setMsg(responseResult.getMsg());
			return responseInfo;
		} catch (Exception e) {
			logger.error("Exception", e);
			return null;
		}

		if (null == resultMap) {
			logger.info("网商返回参数验签错误");
			responseInfo.setResponseResult(ResponseResult.TRADE_EXCEPTION);
			responseInfo.setCode(ResponseResult.TRADE_EXCEPTION.getCode());
			responseInfo.setMsg(VALID_SIGN_ERROR);
			return responseInfo;
		}

		@SuppressWarnings("unchecked")
		Map<String, String> respInfo = (Map<String, String>) resultMap.get("RespInfo");
		String resultStatus = respInfo.get("ResultStatus");
		String resultCode = respInfo.get("ResultCode");
		String resultMsg = respInfo.get("ResultMsg");
		// String outTradeNo = (String) resultMap.get("OutTradeNo"); // 银商订单号

		// 失败
		if ("F".equals(resultStatus)) {
			logger.error("resultCode={} , resultMsg={}", resultCode, resultMsg);
			responseInfo.setResponseResult(ResponseResult.TRADE_FAILED);
			responseInfo.setTradeStatus(OrderStatus.FAILED.name());
			responseInfo.setCode(ChannelResponseResult.RESULT_FAIL.getCode());
			return responseInfo;
		}
		// 表示未知，需要调用查询接口
		if ("U".equals(resultStatus)) {
			responseInfo.setResponseResult(ResponseResult.OK);
			responseInfo.setTradeStatus(OrderStatus.WAITING.name());
			responseInfo.setCode(ResponseResult.OK.getCode());
			return responseInfo;
		}

		// 当 resultStatus 为 S 的时候返回
		String orderNo = null;
		if (null != resultMap.get("OrderNo")) {
			orderNo = (String) resultMap.get("OrderNo");// 网商银行支付订单号
		}

		responseInfo.setResponseResult(ResponseResult.OK);
		responseInfo.setCode(ResponseResult.OK.getCode());
		responseInfo.setChannelTxnNo(orderNo);
		return responseInfo;
	}

	@Override
	public ChannelResponseInfo cancel(ChannelRequestInfo channelRequestInfo) {
		throw new UnSupportApiException("原支付方式不支持撤销操作");
	}

}
