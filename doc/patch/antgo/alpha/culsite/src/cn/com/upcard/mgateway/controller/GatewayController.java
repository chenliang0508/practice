package cn.com.upcard.mgateway.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.upcard.mgateway.annotion.Validator;
import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.BrowserType;
import cn.com.upcard.mgateway.common.enums.QRType;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.common.enums.TradeType;
import cn.com.upcard.mgateway.controller.request.AlipayJsapiParam;
import cn.com.upcard.mgateway.controller.request.AlipayNativePayParam;
import cn.com.upcard.mgateway.controller.request.BaseParam;
import cn.com.upcard.mgateway.controller.request.MicroPayRequest;
import cn.com.upcard.mgateway.controller.request.QrCodeOrderPayParam;
import cn.com.upcard.mgateway.controller.request.QrCreateParam;
import cn.com.upcard.mgateway.controller.request.TradeCancelParam;
import cn.com.upcard.mgateway.controller.request.TradeCloseParam;
import cn.com.upcard.mgateway.controller.request.UnifiedRefundParam;
import cn.com.upcard.mgateway.controller.request.UnifiedRefundQueryParam;
import cn.com.upcard.mgateway.controller.request.UnifiedTradeQueryParam;
import cn.com.upcard.mgateway.controller.request.WxJsPayParam;
import cn.com.upcard.mgateway.controller.request.WxNativePayParam;
import cn.com.upcard.mgateway.controller.response.AlipayJsapiResponse;
import cn.com.upcard.mgateway.controller.response.AlipayNativeResponse;
import cn.com.upcard.mgateway.controller.response.BaseResponse;
import cn.com.upcard.mgateway.controller.response.MicroPayResponse;
import cn.com.upcard.mgateway.controller.response.QRCodeResponse;
import cn.com.upcard.mgateway.controller.response.RefundQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeCancelResponse;
import cn.com.upcard.mgateway.controller.response.TradeQueryResponse;
import cn.com.upcard.mgateway.controller.response.TradeRefundResponse;
import cn.com.upcard.mgateway.controller.response.TradecloseResponse;
import cn.com.upcard.mgateway.controller.response.WxJsResponse;
import cn.com.upcard.mgateway.controller.response.WxNativeResponse;
import cn.com.upcard.mgateway.controller.validator.QrCodeValidator;
import cn.com.upcard.mgateway.controller.validator.ResquestSignValidator;
import cn.com.upcard.mgateway.controller.validator.ValidateUtil;
import cn.com.upcard.mgateway.model.CodeMsg;
import cn.com.upcard.mgateway.model.CommercialArguments;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.service.CombinationParamValidateService;
import cn.com.upcard.mgateway.service.CommericalRightsService;
import cn.com.upcard.mgateway.service.GatewayService;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.service.SessionService;
import cn.com.upcard.mgateway.service.qr.QrPayHandle;
import cn.com.upcard.mgateway.service.qr.QrPayHandleFactory;
import cn.com.upcard.mgateway.util.systemparam.BarcodeType;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

@Controller
@RequestMapping("/gateway")
public class GatewayController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(GatewayController.class);
	@Autowired
	private GatewayService gatewayService;
	@Autowired
	private CommericalRightsService commericalRightsService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private QrCodeValidator qrCodeValidator;
	@Autowired
	private OrderOperService orderOperService;
	@Autowired
	private CombinationParamValidateService combinationParamValidateService;

	/**
	 * 支付宝扫码支付,场景：C扫B,动态二维码
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "service=pay.alipay.native")
	@Validator
	public String alipayNative(HttpServletRequest request, AlipayNativePayParam param) {
		logger.info(param.toString());
		CodeMsg<?> combinationParamValidateCodeMsg = combinationParamValidateService.validateAlipayNativePayParam(param);
		if (combinationParamValidateCodeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(combinationParamValidateCodeMsg.getCode())){
			return response(param.getReturnType(), new BaseResponse(combinationParamValidateCodeMsg));
		}
		
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, TradeType.ALI_NATIVE);
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		
		
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.alipayNativeInstance(param);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		AlipayNativeResponse resp = gatewayService.alipayNativePreOrder(preTradeOrderInfo);
		return response(param.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());
	}
	
	/**
	 * 支付宝服务窗预下单
	 * @param request
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "service=pay.alipay.jspay")
	@Validator
	public String alipayJsapi(HttpServletRequest request, AlipayJsapiParam param) {
		logger.info(param.toString());
		CodeMsg<?> combinationParamValidateCodeMsg = combinationParamValidateService.validateAlipayJsapiParam(param);
		if (combinationParamValidateCodeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(combinationParamValidateCodeMsg.getCode())){
			return response(param.getReturnType(), new BaseResponse(combinationParamValidateCodeMsg));
		}

		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, TradeType.ALI_JSAPI);
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		
		
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.alipayJsapiInstance(param);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		AlipayJsapiResponse resp = gatewayService.alipayJsapiPreOrder(preTradeOrderInfo);
		return response(param.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());
	}
	
	/**
	 * 微信扫码支付（主扫接口）
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "service=pay.wx.native")
	@Validator
	public String wxNative(HttpServletRequest request, WxNativePayParam param) {
		logger.info(param.toString());
		CodeMsg<?> combinationParamValidateCodeMsg = combinationParamValidateService.validateWxNativePayParam(param);
		if (combinationParamValidateCodeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(combinationParamValidateCodeMsg.getCode())){
			return response(param.getReturnType(), new BaseResponse(combinationParamValidateCodeMsg));
		}

		// 权限校验和验签
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, TradeType.WX_NATIVE);
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.wxNativeInstance(param);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		WxNativeResponse resp = gatewayService.wxNativePreOrder(preTradeOrderInfo);

		return response(param.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());
	}

	/**
	 * 微信js支付，用于公众号和小程序
	 * 
	 * @param request
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "service=pay.wx.js")
	@Validator
	public String wxJs(HttpServletRequest request, WxJsPayParam param) {
		logger.info(param.toString());
		CodeMsg<?> combinationParamValidateCodeMsg = combinationParamValidateService.validateWxJsPayParam(param);
		if (combinationParamValidateCodeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(combinationParamValidateCodeMsg.getCode())){
			return response(param.getReturnType(), new BaseResponse(combinationParamValidateCodeMsg));
		}

		// 权限校验和验签
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, TradeType.WX_JSAPI);
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.wxJsInstance(param);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		WxJsResponse resp = gatewayService.wxJsPreOrder(preTradeOrderInfo);

		return response(param.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());
	}

	/**
	 * 线下刷卡支付,场景：b扫c
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = "service=unified.trade.micropay")
	@ResponseBody
	public String microPay(HttpServletRequest request) {
		MicroPayRequest payRequestBean = MicroPayRequest.instance(request);
		MicroPayResponse resp = new MicroPayResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		String vdResult = ValidateUtil.checkPayParams(payRequestBean, resp);
		if (!Constants.OK.equals(vdResult)) {
			return vdResult;
		}
		TradeType tradeType = null;
		if ((tradeType = BarcodeType.route(payRequestBean.getAuthCode())) == null) {
			resp.setCode(ResponseResult.AUTH_CODE_INVALID.getCode());
			resp.setMsg(ResponseResult.AUTH_CODE_INVALID.getMsg());
			return response(payRequestBean.getReturnType(), resp);
		}
		
		CodeMsg<?> combinationParamValidateCodeMsg = combinationParamValidateService.validateMicroPayRequest(payRequestBean);
		if (combinationParamValidateCodeMsg == null) {
			return response(payRequestBean.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(combinationParamValidateCodeMsg.getCode())){
			return response(payRequestBean.getReturnType(), new BaseResponse(combinationParamValidateCodeMsg));
		}
		
		// 权限校验和验签
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(payRequestBean, tradeType);
		if (codeMsg == null) {
			return response(payRequestBean.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(payRequestBean.getReturnType(), new BaseResponse(codeMsg));
		}
		
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.micropayInstance(payRequestBean);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setTradeType(tradeType.name());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		
		resp = gatewayService.microPayOrder(preTradeOrderInfo);
		return response(payRequestBean.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());

	}

	/**
	 * 统一的交易查询接口
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "service=unified.trade.query")
	@Validator
	public String tradeQuery(HttpServletRequest request, UnifiedTradeQueryParam param) {
		TradeQueryResponse resp = new TradeQueryResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, param.getOutTradeNo());
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.tradeQueryInstance(param);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		resp = gatewayService.unifiedPayQuery(preTradeOrderInfo);
		return response(param.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());
	}

	/**
	 * 交易撤销接口
	 * 
	 * @param request
	 * @return
	 */
	@Validator
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "service=pay.trade.cancel")
	public String tradeCancel(HttpServletRequest request, TradeCancelParam param) {
		logger.info(param.toString());
		// 预下单 Acceptor Bank ACQUIRER
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, param.getOutTradeNo());
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.cancelInstance(param);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		TradeCancelResponse resp = gatewayService.tradeCancel(preTradeOrderInfo);
		return response(param.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());
	}

	/**
	 * 线上C扫B模式，超时未支付后，商户关闭订单接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "service=unified.trade.close")
	@Validator
	public String tradeClose(HttpServletRequest request, TradeCloseParam param) {
		logger.info(param.toString());
		// 预下单 Acceptor Bank ACQUIRER
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, param.getOutTradeNo());
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.closeInstance(param);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		TradecloseResponse resp = gatewayService.unifiedClose(preTradeOrderInfo);
		return response(param.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());
	}

	/**
	 * 统一退款接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "service=unified.trade.refund")
	@Validator
	public String tradeRefund(HttpServletRequest request, UnifiedRefundParam param) {
		TradeRefundResponse resp = new TradeRefundResponse(ResponseResult.OK.getCode(), ResponseResult.OK.getMsg());
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, param.getOutTradeNo());
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.tradeRefundInstance(param);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		resp = gatewayService.unifiedRefund(preTradeOrderInfo);
		return response(param.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());
	}

	/**
	 * 通用退款查询接口
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = "service=unified.trade.refundQuery")
	@Validator
	public String tradeRefundQuery(HttpServletRequest request, UnifiedRefundQueryParam param) {
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, param.getRefundNo());
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.refundQueryInstance(param);
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		RefundQueryResponse resp = gatewayService.tradeRefundQuery(preTradeOrderInfo);
		return response(param.getReturnType(), resp, comArg.getTpCulCommerical().getCommKey());
	}
	
	/**
	 * 输入金额页面的表单提交 创建订单，在支付机构预下单，唤起第三方支付网关
	 * 请求是从页面提交过来的，需要做防重复提交处理
	 * @param request
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, params = "service=unified.qrcode.pre")
	public ModelAndView qrCodeOrderPay(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("qr/error");
		String userAgent = request.getHeader("user-agent");
		BrowserType browser = BrowserType.toBrowserType(userAgent);
		if (browser == null) {
			view.addObject("msg", ResponseResult.UNSUPPORTED_SERVICE.getMsg());
			view.addObject("code", ResponseResult.UNSUPPORTED_SERVICE.getCode());
			return view;
		}
		 
		CodeMsg<?> validatorResult = qrCodeValidator.validQrCodeOrderPay(request);
		if (!Constants.OK.equals(validatorResult.getCode())) {
			view.addObject("code", validatorResult.getCode());
			view.addObject("msg", validatorResult.getMsg());
			return view;
		}
		QrCodeOrderPayParam param = QrCodeOrderPayParam.instance(request);
		String[] params = {param.getMchId(), param.getTimestamp(), param.getQrToken()}; 
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(params, param.getMchId(), 
				request.getParameter("sign"), browser.getTradeType());
		if (codeMsg == null) {
			view.addObject("msg", ResponseResult.NON_RESP.getMsg());
			view.addObject("code", ResponseResult.NON_RESP.getCode());
			return view;
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			view.addObject("msg", codeMsg.getMsg());
			view.addObject("code", codeMsg.getCode());
			return view;
		}
		//校验token 
		QrCodeOrderPayParam sessionParam = sessionService.getQySessionInfo(param.getQrToken());
		if (sessionParam == null) {
			view.addObject("msg", ResponseResult.INVALID_TOKEN.getMsg());
			view.addObject("code", ResponseResult.INVALID_TOKEN.getCode());
			return view;
		}

		CommercialArguments comArg = codeMsg.getInfo();
		PreTradeOrderInfo preTradeOrderInfo = PreTradeOrderInfo.qrCodeOrderPayInstance(param, browser.getTradeType());
		preTradeOrderInfo.setBankMerNo(comArg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(comArg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(comArg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setCommPrivateKey(comArg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		//商户订单号由系统生成
		preTradeOrderInfo.setOutTradeNo(orderOperService.createOutTradeNo(preTradeOrderInfo.getBankCode()));
		
		//设置支付成功回调页面
		StringBuffer buf = new StringBuffer(SysPara.MGATEWAY_HOST);
		buf.append("/q/payResult")
		.append("?")
		.append("&mgatewayOrderNo=").append(preTradeOrderInfo.getOutTradeNo())
		.append("&mchId=").append(preTradeOrderInfo.getCommUnionNo())
		.append("&browserType=").append(browser.getBrowserTypeSign());
		String[] signParam = {preTradeOrderInfo.getCommUnionNo(), browser.getBrowserTypeSign(), preTradeOrderInfo.getOutTradeNo()};
		String sign = ResquestSignValidator.genSign(signParam, comArg.getTpCulCommerical().getCommKey());
		buf.append("&sign=").append(sign);
		preTradeOrderInfo.setReturnUrl(buf.toString());
		
		
		QrPayHandle handle = QrPayHandleFactory.createHandle(gatewayService, browser);
		return handle.createPreOrder(preTradeOrderInfo);
	}
}