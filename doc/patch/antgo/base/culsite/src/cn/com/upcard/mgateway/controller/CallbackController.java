package cn.com.upcard.mgateway.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.com.upcard.mgateway.async.notify.model.CallbackPara;
import cn.com.upcard.mgateway.async.notify.model.CallbackRequestInfo;
import cn.com.upcard.mgateway.async.notify.model.OrderProcessingResult;
import cn.com.upcard.mgateway.channel.bank.cib.common.CibServiceType;
import cn.com.upcard.mgateway.channel.bank.cib.sdk.util.SignUtils;
import cn.com.upcard.mgateway.channel.bank.lkl.LklError;
import cn.com.upcard.mgateway.channel.bank.lkl.LklSignTool;
import cn.com.upcard.mgateway.channel.bank.mybank.JsUnifiedOper;
import cn.com.upcard.mgateway.channel.bank.mybank.common.ChannelType;
import cn.com.upcard.mgateway.channel.bank.mybank.sdk.util.PreNoticeXmlUtil;
import cn.com.upcard.mgateway.channel.bank.mybank.sdk.util.XmlSignUtil;
import cn.com.upcard.mgateway.channel.bank.mybank.sdk.util.XmlUtil;
import cn.com.upcard.mgateway.common.enums.OrderOperType;
import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.exception.AbnormalResponseException;
import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;
import cn.com.upcard.mgateway.payment.alipay.AlipayApiContext;
import cn.com.upcard.mgateway.payment.alipay.AlipayClient;
import cn.com.upcard.mgateway.service.ApiLogService;
import cn.com.upcard.mgateway.service.CallbackService;
import cn.com.upcard.mgateway.service.CommericalInfoService;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.SignUtil;
import cn.com.upcard.mgateway.util.XmlUtils;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

@Controller
@RequestMapping("callback")
public class CallbackController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(CallbackController.class);

	private static final String CIB_RETURN_SUCCESS = "0";
	private static final String FAIL = "fail";

	@Autowired
	private CallbackService callbackService;
	@Autowired
	private ApiLogService apiLogService;
	@Autowired
	private OrderOperService orderOperService;
	@Autowired
	private CommericalInfoService commericalInfoService;

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "cib")
	public String cibNotify(HttpServletRequest request) {

		Map<String, String> resultMap = null;
		try {
			resultMap = XmlUtils.xmlToMap(request.getInputStream());
			logger.info("兴业异步通知 res ---> \r\n" + XmlUtils.toXml(resultMap));
		} catch (Exception e) {
			logger.error("Exception", e);
			return FAIL;
		}

		String status = resultMap.get("status");
		String message = resultMap.get("message");

		if (!CIB_RETURN_SUCCESS.equals(status)) {
			logger.info("status={} , message={}", status, message);
			return FAIL;
		}

		// status为0的时候返回
		String mchId = resultMap.get("mch_id"); // 商户在威富通的商户号
		String resultCode = resultMap.get("result_code"); // 业务结果，0成功非0失败
		String deviceInfo = resultMap.get("device_info"); // 设备号
		String errCode = resultMap.get("err_code"); // 参考错误码
		String errMsg = resultMap.get("err_msg"); // 错误信息描述

		if (!CIB_RETURN_SUCCESS.equals(resultCode)) {
			logger.info("resultCode={}", resultCode);
			logger.info("errCode={} , errMsg={}", errCode, errMsg);
			logger.info("mchId={} , deviceInfo={}", mchId, deviceInfo);
			return FAIL;
		}

		String outTradeNo = resultMap.get("out_trade_no");// 本系统订单号
		logger.info("mchId:{}", mchId);
		logger.info("out_trade_no:{}", outTradeNo);
		// 根据订单号查询门店信息，验证签名
		if (StringUtils.isEmpty(mchId) || StringUtils.isEmpty(outTradeNo)) {
			return FAIL;
		}
		String commKey = orderOperService.findCommKeyByOutTradeNoAndBankMerNo(outTradeNo, mchId);
		// 校验签名
		if (!SignUtils.checkParam(resultMap, commKey)) {
			logger.info("Signature verification error");
			return FAIL;
		}

		// 当 status和result_code都为0的时候有返回
		String tradeType = resultMap.get("trade_type"); // 兴业交易类型
		if (CibServiceType.WX_NATIVE_PAY_SERVICE.getService().equals(tradeType)) {
			tradeType = TradeType.WX_NATIVE.name();
		} else if (CibServiceType.WX_JS_PAY_SERVICE.getService().equals(tradeType)) {
			tradeType = TradeType.WX_JSAPI.name();
		} else if (CibServiceType.ALIPAY_NATIVE_PAY.getService().equals(tradeType)) {
			tradeType = TradeType.ALI_NATIVE.name();
		} else if (CibServiceType.ALIPAY_JSAPI_PAY_SERVICE.getService().equals(tradeType)) {
			tradeType = TradeType.ALI_JSAPI.name();
		} else {
			logger.error("nonexistent tradeType={}", tradeType);
			return FAIL;
		}

		String outTransactionId = resultMap.get("out_transaction_id");// 第三方支付机构流水号
		String transactionId = resultMap.get("transaction_id"); // 兴业平台订单号
		String payResult = resultMap.get("pay_result"); // 支付结果，0成功其他失败
		String subIsSubscribe = resultMap.get("sub_is_subscribe"); // 是否关注商户公众号
		String subAppid = resultMap.get("sub_appid"); // 商户appid（微信）
		String subOpenid = resultMap.get("sub_openid"); // 用户openid（微信）
		String openid = resultMap.get("openid"); // 支付宝支付表示openid，微信支付表示服务商下的openid
		String totalFee = resultMap.get("total_fee"); // 总金额（分）
		String couponFee = resultMap.get("coupon_fee") == null ? "0" : resultMap.get("coupon_fee"); // 微信现金券金额（分）
		int payFee = Integer.valueOf(totalFee) - Integer.valueOf(couponFee); // 实际支付金额（分）
		int discountFee = Integer.valueOf(couponFee); // // 折扣金额
		String feeType = resultMap.get("fee_type"); // 货币种类
		String attach = resultMap.get("attach"); // 附加信息
		String timeEnd = resultMap.get("time_end"); // 支付完成时间，yyyyMMddHHmmss

		CallbackRequestInfo callbackRequestInfo = new CallbackRequestInfo();
		callbackRequestInfo.setTradeType(tradeType);
		callbackRequestInfo.setTransactionId(transactionId);
		callbackRequestInfo.setOutTransactionId(outTransactionId);
		callbackRequestInfo.setPayResult(payResult);
		callbackRequestInfo.setErrMsg(errMsg);
		callbackRequestInfo.setSubIsSubscribe(subIsSubscribe);
		callbackRequestInfo.setSubAppid(subAppid);
		callbackRequestInfo.setSubOpenid(subOpenid);
		callbackRequestInfo.setAliOpenid(openid);
		callbackRequestInfo.setOutTradeNo(outTradeNo);
		callbackRequestInfo.setTotalFee(Integer.valueOf(totalFee));
		callbackRequestInfo.setDiscountFee(discountFee);
		callbackRequestInfo.setTimeEnd(timeEnd);
		// 订单校验和更新
		OrderProcessingResult processingResult = callbackService.updateOrderProcessing(callbackRequestInfo);

		TradeOrder tradeOrder = processingResult.getTradeOrder();

		if (null != tradeOrder) {
			apiLogService.saveApiLog(tradeOrder.getId(), OrderOperType.PAY, JSON.toJSONString(resultMap));
		}

		if (processingResult.isNeedPush()) {

			// 组装消息推送内容
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("code", CIB_RETURN_SUCCESS.equals(payResult) ? "00" : "71"); // 支付结果
			contentMap.put("msg", CIB_RETURN_SUCCESS.equals(payResult) ? "success" : errMsg); // 结果描述
			contentMap.put("mchId", tradeOrder.getCulCommNo()); // 商户号
			contentMap.put("deviceInfo", deviceInfo); // 设备号
			contentMap.put("tradeType", tradeType); // 交易类型
			contentMap.put("outTradeNo", tradeOrder.getOrderNo()); // 商户订单号
			contentMap.put("transactionId", tradeOrder.getTradeNo()); // 银商订单号
			contentMap.put("subAppid", subAppid); // 商户appid
			contentMap.put("buyerAccount", processingResult.getChannelResponseInfo().getThirdBuyerAccount()); // 买家账号
			contentMap.put("subIsSubscribe", subIsSubscribe); // 是否关注公众号
			contentMap.put("feeType", feeType); // 货币类型
			contentMap.put("extendInfo", attach); // 附加信息
			contentMap.put("totalAmount", String.valueOf(totalFee)); // 订单金额
			contentMap.put("payAmount", String.valueOf(payFee)); // 支付金额
			contentMap.put("discountAmount", String.valueOf(discountFee)); // 折扣金额
			contentMap.put("timeEnd", timeEnd); // 交易完成时间

			String culCommKey = commericalInfoService.getCulCommInfoByNo(tradeOrder.getCulCommNo()).getCommKey();
			String sign = SignUtil.generateResponseSign(contentMap, culCommKey);
			contentMap.put("sign", sign); // 添加签名

			CallbackPara callbackPara = new CallbackPara();
			callbackPara.setOrderId(tradeOrder.getId());
			callbackPara.setUrl(tradeOrder.getNotifyUrl());
			callbackPara.setContentMap(contentMap);

			// 推送消息
			callbackService.callbackHandle(callbackPara);
		}

		logger.info("cibNotify end result={}", processingResult.getResult());

		return processingResult.getResult();
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "lkl")
	public String lklNotify(HttpServletRequest request) {

		Map<String, String> resultMap = null;
		try {
			resultMap = XmlUtils.xmlToMap(request.getInputStream());
			logger.info("兴业异步通知 res ---> \r\n" + XmlUtils.toXml(resultMap));
		} catch (Exception e) {
			logger.error("Exception", e);
			return FAIL;
		}

//		FunCod	功能码
//		responseCode	返回码
//		compOrgCode	机构代码
//		reqLogNo	请求流水号
//		ornReqLogNo	原请求流水号
//		reqTm	请求时间
//		payChlTyp	支付渠道类型
//		mercId	商户号
//		userId	用户识别码
//		txnAmt	交易金额
//		txnTm	交易时间
//		tradeType	交易接口类型
//		sub_appid	子商户appid
//		payOrderId	第三方钱包订单号
//		merOrderNo	商户订单号
//		message	返回码描述
//		errCode	第三方钱包返回错误码
//		extData	扩展信息
//		MAC	SHA1计算值
		String responseCode = request.getParameter("responseCode");
		logger.info("responseCode:{}", responseCode);
		String payResult = null;
		LklError lklError = LklError.toLklError(responseCode);
		if (lklError != LklError.SUCCESS) {
			//支付失败
			payResult = "1";
		} else {
			//支付成功
			payResult = "0";
		}

		String reqLogNo = resultMap.get("reqLogNo");
		String ornReqLogNo = resultMap.get("ornReqLogNo");
		String reqTm = resultMap.get("reqTm");
		String payChlTyp = resultMap.get("payChlTyp");
		String mercId = resultMap.get("mercId");
		String userId = resultMap.get("userId");
		String txnAmt = resultMap.get("txnAmt");
		String txnTm = resultMap.get("txnTm");
		String tradeType = resultMap.get("tradeType");
		String sub_appId = resultMap.get("sub_appid");
		String payOrderId = resultMap.get("payOrderId");
		String merOrderNo = resultMap.get("merOrderNo");
		String message = resultMap.get("message");
		String extData = resultMap.get("extData");
		String mgatewayTradeType = request.getParameter("mgatewayTradeType");
		String[] signField = {"responseCode", "compOrgCode", "reqLogNo", "ornReqLogNo", "reqTm", "payChlTyp", "mercId", "txnAmt", "txnTm", "tradeType", "sub_appid", "payOrderId", "merOrderNo"};
		//验证签名
		if (!LklSignTool.checkResponseSign(signField, resultMap, SysPara.LKL_KEY)) {
			return FAIL;
		}
		CallbackRequestInfo callbackRequestInfo = new CallbackRequestInfo();
		callbackRequestInfo.setTradeType(tradeType);
		callbackRequestInfo.setTransactionId(merOrderNo);
		callbackRequestInfo.setOutTransactionId(payOrderId);
		callbackRequestInfo.setPayResult(payResult);
		callbackRequestInfo.setSubAppid(sub_appId);
		callbackRequestInfo.setSubOpenid(userId);
		callbackRequestInfo.setErrMsg(message);
		callbackRequestInfo.setOutTradeNo(ornReqLogNo);
		callbackRequestInfo.setTotalFee(Integer.valueOf(txnAmt));
		callbackRequestInfo.setTimeEnd(txnTm);
		callbackRequestInfo.setTradeType(mgatewayTradeType);
		// 订单校验和更新
		OrderProcessingResult processingResult = callbackService.updateOrderProcessing(callbackRequestInfo);

		TradeOrder tradeOrder = processingResult.getTradeOrder();

		if (null != tradeOrder) {
			apiLogService.saveApiLog(tradeOrder.getId(), OrderOperType.PAY, JSON.toJSONString(resultMap));
		}

		if (processingResult.isNeedPush()) {

			// 组装消息推送内容
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("code", CIB_RETURN_SUCCESS.equals(payResult) ? "00" : "71"); // 支付结果
			contentMap.put("msg", CIB_RETURN_SUCCESS.equals(payResult) ? "success" : message); // 结果描述
			contentMap.put("mchId", tradeOrder.getCulCommNo()); // 商户号
			contentMap.put("tradeType", mgatewayTradeType); // 交易类型
			contentMap.put("outTradeNo", tradeOrder.getOrderNo()); // 商户订单号
			contentMap.put("transactionId", tradeOrder.getTradeNo()); // 银商订单号
			contentMap.put("subAppid", sub_appId); // 商户appid
			contentMap.put("buyerAccount", processingResult.getChannelResponseInfo().getThirdBuyerAccount()); // 买家账号
			contentMap.put("attach", extData); // 附加信息
			contentMap.put("totalFee", String.valueOf(txnAmt)); // 订单金额
			contentMap.put("payFee", String.valueOf(txnAmt)); // 支付金额
			contentMap.put("timeEnd", txnTm); // 交易完成时间

			CallbackPara callbackPara = new CallbackPara();
			callbackPara.setOrderId(tradeOrder.getId());
			callbackPara.setUrl(tradeOrder.getNotifyUrl());
			callbackPara.setContentMap(contentMap);

			// 推送消息
			callbackService.callbackHandle(callbackPara);
		}

		logger.info("cibNotify end result={}", processingResult.getResult());

		return processingResult.getResult();
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value="mybank")
	public String mybankNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BufferedReader reader = null;
		String doc = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String tempLine = "";
			StringBuffer resultBuffer = new StringBuffer();
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine).append(System.getProperty("line.separator"));
			}
			doc = resultBuffer.toString();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		logger.info("request info :{}", doc);
		//判断请求的报文格式是否正确
		if (!doc.startsWith("<document>")) {
			throw new AbnormalResponseException(doc);
		}
		//验证签名
		if (!XmlSignUtil.verify(doc)) {
			logger.error("网商返回验签失败");
			PreNoticeXmlUtil xmlUtils = new PreNoticeXmlUtil();
			xmlUtils.printErrorMsg(response.getOutputStream());
			return null;
		}
		
		//解析报文
		XmlUtil xmlUtil = new XmlUtil();
		Map<String, Object> resultMap = null;
		try {
			resultMap = xmlUtil.parseReceive(doc, null);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		// 当 status和result_code都为0的时候有返回
		String payResult = "0";//支付成功
		String outTradeNo = (String) resultMap.get("OutTradeNo");
		String channelType = (String) resultMap.get("ChannelType");
		String totalAmount = (String) resultMap.get("TotalAmount");//交易总额度。货币最小单位，人民币：分
		String currency = (String) resultMap.get("Currency");
		String merchantId = (String) resultMap.get("MerchantId");//商户号。网商为商户分配的商户号，通过商户入驻结果查询接口获取。
		String IsvOrgId = (String) resultMap.get("IsvOrgId");//合作方机构号（网商银行分配）
		String gmtPayment = (String) resultMap.get("GmtPayment");
		String attach = null;
		if (resultMap.get("Attach") != null) {
			attach = (String) resultMap.get("Attach");
		}
		String isSubscribe = null;
		if (resultMap.get("IsSubscribe") != null) {
			isSubscribe	= (String) resultMap.get("IsSubscribe");
		}
		String payChannelOrderNo = null;
		if (resultMap.get("PayChannelOrderNo") != null) {
			payChannelOrderNo = (String) resultMap.get("PayChannelOrderNo");//微信或支付宝的订单号
		}
		String merchantOrderNo = null;
		if (resultMap.get("MerchantOrderNo") != null) {
			merchantOrderNo = (String) resultMap.get("MerchantOrderNo");//不是网商订单号
		}
		String couponFee = null;
		if (resultMap.get("CouponFee") != null) {
			couponFee = (String)resultMap.get("CouponFee");
		}
		String subAppId = null;
		if (resultMap.get("SubAppId") != null) {
			subAppId = (String) resultMap.get("SubAppId");
		}
		String openId = null;
		if (resultMap.get("OpenId") != null) {
			openId = (String) resultMap.get("OpenId");
		}
		
		String subOpenId = null;
		if (resultMap.get("SubOpenId") != null) {
			subOpenId = (String) resultMap.get("SubOpenId");
		}
		
		String buyerLogonId = null;
		if (resultMap.get("BuyerLogonId") != null) {
			buyerLogonId = (String) resultMap.get("BuyerLogonId");
		}
		String buyerUserId = null;
		if (resultMap.get("BuyerUserId") != null) {
			buyerUserId = (String) resultMap.get("BuyerUserId");
		}
		
		String credit = null;
		if (resultMap.get("Credit") != null) {
			credit = (String) resultMap.get("Credit");
		}
		
		String orderNo = null;
		if (resultMap.get("OrderNo") != null) {
			orderNo = (String)resultMap.get("OrderNo");
		}
		int disCountAmount = 0;
		//计算折扣金额
		if(!StringUtils.isEmpty(couponFee)) {
			disCountAmount = disCountAmount + Integer.valueOf(couponFee);
		}
		int payAmount = Integer.valueOf(totalAmount) - disCountAmount;
		String tradeType = null;
		if (ChannelType.ALI.name().equals(channelType)) {
			tradeType = TradeType.ALI_JSAPI.name();
		} else if (ChannelType.WX.name().equals(channelType)) {
			tradeType = TradeType.WX_JSAPI.name();
		} else {
			//不支持的交易类型
			PreNoticeXmlUtil xmlUtils = new PreNoticeXmlUtil();
			xmlUtils.printErrorMsg(response.getOutputStream());
			return null;
		}
		CallbackRequestInfo callbackRequestInfo = new CallbackRequestInfo();
		callbackRequestInfo.setTransactionId(null);
		callbackRequestInfo.setOutTransactionId(payChannelOrderNo);
		callbackRequestInfo.setPayResult(payResult);
		callbackRequestInfo.setErrMsg(null);
		callbackRequestInfo.setSubIsSubscribe(isSubscribe);
		callbackRequestInfo.setSubAppid(subAppId);
		callbackRequestInfo.setSubOpenid(subOpenId);
		callbackRequestInfo.setAliOpenid(StringUtils.isEmpty(buyerUserId) ? buyerLogonId : buyerUserId);
		callbackRequestInfo.setOutTradeNo(outTradeNo);
		callbackRequestInfo.setTotalFee(Integer.valueOf(totalAmount));
		callbackRequestInfo.setTradeType(tradeType);
		callbackRequestInfo.setDiscountFee(disCountAmount);
		try {
			callbackRequestInfo.setTimeEnd(DateUtil.format2(DateUtil.parse2(gmtPayment, DateUtil.DATE_TIME_FORMATTER_STRING), DateUtil.yyyyMMddHHmmss));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (!StringUtils.isEmpty(orderNo)) {
			callbackRequestInfo.setTransactionId(orderNo);
		} else {
			try {
				//查询商户订单号
				JsUnifiedOper jsUnifiedOper = new JsUnifiedOper();
				ChannelRequestInfo info = new ChannelRequestInfo();
				info.setMchId(merchantId);
				info.setOutTradeNo(outTradeNo);
				ChannelResponseInfo queryInfo = jsUnifiedOper.tradeQuery(info);
				if (queryInfo != null && queryInfo.getChannelTxnNo() != null) {
					logger.info("查询网商银行订单号成功:{}", queryInfo.getChannelTxnNo());
					callbackRequestInfo.setTransactionId(queryInfo.getChannelTxnNo());
				}
			} catch (Exception e) {
				logger.info("查询网商银行订单号失败");
				e.printStackTrace();
			}
		}
		// 订单校验和更新
		OrderProcessingResult processingResult = callbackService.updateOrderProcessing(callbackRequestInfo);

		TradeOrder tradeOrder = processingResult.getTradeOrder();

		if (null != tradeOrder) {
			apiLogService.saveApiLog(tradeOrder.getId(), OrderOperType.PAY, JSON.toJSONString(resultMap));
		}

		if (processingResult.isNeedPush()) {

			// 组装消息推送内容
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("code", CIB_RETURN_SUCCESS.equals(payResult) ? "00" : "71"); // 支付结果
			contentMap.put("msg", CIB_RETURN_SUCCESS.equals(payResult) ? "success" : "支付失败"); // 结果描述
			contentMap.put("mchId", tradeOrder.getCulCommNo()); // 商户号
			contentMap.put("deviceInfo", tradeOrder.getDeviceInfo()); // 设备号
			contentMap.put("tradeType", tradeType); // 交易类型
			contentMap.put("outTradeNo", tradeOrder.getOrderNo()); // 商户订单号
			contentMap.put("transactionId", tradeOrder.getTradeNo()); // 银商订单号
			contentMap.put("subAppid", subAppId); // 商户appid
			contentMap.put("buyerAccount", processingResult.getChannelResponseInfo().getThirdBuyerAccount()); // 买家账号
			contentMap.put("subIsSubscribe", isSubscribe); // 是否关注公众号
			contentMap.put("feeType", "CNY"); // 货币类型
			contentMap.put("extendInfo", attach); // 附加信息
			contentMap.put("totalAmount", totalAmount); // 订单金额
			contentMap.put("payAmount", String.valueOf(payAmount)); // 支付金额
			contentMap.put("discountAmount", String.valueOf(disCountAmount)); // 折扣金额
			contentMap.put("timeEnd", callbackRequestInfo.getTimeEnd()); // 交易完成时间

			CallbackPara callbackPara = new CallbackPara();
			callbackPara.setOrderId(tradeOrder.getId());
			callbackPara.setUrl(tradeOrder.getNotifyUrl());
			callbackPara.setContentMap(contentMap);

			// 推送消息
			callbackService.callbackHandle(callbackPara);
		}

		logger.info("cibNotify end result={}", processingResult.getResult());

		String processResult = processingResult.getResult();
		if ("SUCCESS".equalsIgnoreCase(processResult)) {
			PreNoticeXmlUtil xmlUtils = new PreNoticeXmlUtil();
			xmlUtils.printSuccessMsg(response.getOutputStream());
			return null;
		} else {
			PreNoticeXmlUtil xmlUtils = new PreNoticeXmlUtil();
			xmlUtils.printErrorMsg(response.getOutputStream());
			return null;
		}
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "verify")
	public ModelAndView callback(HttpServletRequest request, HttpServletResponse response) {
		AlipayApiContext apiContexnt = new AlipayApiContext();
		apiContexnt.setAppId(SysPara.ALIPAY_APPID);
		apiContexnt.setBizPublicKey(SysPara.ALIPAY_BIZ_PULBIC_KEY);
		apiContexnt.setPrivateKey(SysPara.ALIPAY_PRIVATE_KEY);
		apiContexnt.setGateway(SysPara.ALIPAY_GATEWAY);
		apiContexnt.setCharset("GBK");
		AlipayClient client = new AlipayClient(apiContexnt);
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(client.verifyResponseXml().getBytes(apiContexnt.getCharset()));
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}