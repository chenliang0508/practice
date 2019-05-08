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
import cn.com.upcard.mgateway.controller.request.BaseParam;
import cn.com.upcard.mgateway.controller.request.QrCodeOrderPayParam;
import cn.com.upcard.mgateway.controller.response.BaseResponse;
import cn.com.upcard.mgateway.controller.response.QRCodeResponse;
import cn.com.upcard.mgateway.controller.validator.QrCodeValidator;
import cn.com.upcard.mgateway.controller.validator.ResquestSignValidator;
import cn.com.upcard.mgateway.model.CodeMsg;
import cn.com.upcard.mgateway.model.CommercialArguments;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.service.CommericalRightsService;
import cn.com.upcard.mgateway.service.GatewayService;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.service.SessionService;
import cn.com.upcard.mgateway.service.qr.QrPayHandle;
import cn.com.upcard.mgateway.service.qr.QrPayHandleFactory;
import cn.com.upcard.mgateway.util.systemparam.SysPara;
@Controller
@RequestMapping(value="/q")
public class QrController extends BaseController {
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
	/**
	 * 产生静态二维码的链接url,
	 * URL样例如下：mgateway/q?d=dfgdfgsdfgdfgdfg&m =1524562584
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value="/createQrUrl")
	@Validator
	public String createQrCodeUrl(HttpServletRequest request, BaseParam param) {
		
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(param, QRType.getAllTradeType());
		if (codeMsg == null) {
			return response(param.getReturnType(), ResponseResult.NON_RESP);
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			return response(param.getReturnType(), new BaseResponse(codeMsg));
		}
		CommercialArguments comArg = codeMsg.getInfo();
		//生产签名
		String[] signParams = {param.getMchId()};
		String sign = ResquestSignValidator.genSign(signParams, comArg.getTpCulCommerical().getCommKey());
		
		//生成url
		String qrCodeUrl = new StringBuffer()
				.append(SysPara.MGATEWAY_HOST)
				.append("/q/p")
				.append("?m=")
				.append(param.getMchId())
				.append("&s=")
				.append(sign)
				.toString();
		QRCodeResponse qRCodeResponse = new QRCodeResponse(ResponseResult.OK);
		qRCodeResponse.setQrCodeUrl(qrCodeUrl);
		return response(param.getReturnType(), qRCodeResponse);
	}
	/**
	 * <pre>
	 * 跳转输入金额页面前准备获取购买人信息，比如openId
	 * 
	 * 若收单机构预下单接口需要传入购买人信息，比如兴业银行微信原生js支付需要传入用户的openId，可以
	 * 直接重定向微信auth2.0接口，并传入回调url;若收单机构预下单接口不需要用户信息，可以直接转发到
	 * 输入金额页面。
	 * </pre>
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/p")
	public ModelAndView preBeforeToQrCodeOrder(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String userAgent = request.getHeader("user-agent");
		logger.info("user-agent:{}", userAgent);
		BrowserType browser = BrowserType.toBrowserType(userAgent);
		if (browser == null) {
			view.setViewName("qr/error");
			view.addObject("msg", "不支持的交易支付方式,请使用支付宝或微信扫描二维码");
			return view;
		}
		
		String mchId = request.getParameter("m");
		String clientSign = request.getParameter("s");
		if (StringUtils.isEmpty(mchId)) {
			view.setViewName("qr/error");
			view.addObject("msg", "商户号不能为空");
			return view;
		}
		if (StringUtils.isEmpty(clientSign)) {
			view.setViewName("qr/error");
			view.addObject("msg", "签名不能为空");
			return view;
		}
		//设置视图的名称
		String[] signParam = {mchId};
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(signParam, mchId, clientSign, browser.getTradeType());
		if (codeMsg == null) {
			view.setViewName("qr/error");
			view.addObject("msg", "系统异常");
			return view;
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			view.setViewName("qr/error");
			view.addObject("msg", codeMsg.getMsg());
			return view;
		}
		
		//
		QrPayHandle handle = QrPayHandleFactory.createHandle(gatewayService, browser);
		return handle.preToOrder(codeMsg.getInfo().getTpCulCommerical());
	}
	/**
	 * 跳转到输入金额页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/toOrder")
	public ModelAndView toQrCodeOrder(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("qr/error");
		String browserType = request.getParameter("browserType");
		String mchId = request.getParameter("mchId");
		logger.info("user-agent:{}", browserType);
		logger.info("mchId:{}", mchId);
		BrowserType browser = BrowserType.toBrowserType(browserType);
		if (browser == null) {
			view.addObject("msg", "不支持的交易支付方式,请使用支付宝或微信扫描二维码");
			return view;
		}
		
		String[] params = {mchId, browserType}; 
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(params, mchId, 
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
		
		//放入签名防止页面篡改数据
		QrPayHandle handle = QrPayHandleFactory.createHandle(gatewayService, browser);
		return handle.createInputMoneyPage(codeMsg.getInfo().getTpCulCommerical(), sessionService, request);
	}
	
	/**
	 * 跳转到输入金额页面,请求参数中不带有
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/payResult")
	public ModelAndView acceptPayResult(HttpServletRequest request) {
		//订单的实际支付结果已系统的结果为准
		String tradeNo = request.getParameter("mgatewayOrderNo");
		String mchId = request.getParameter("mchId");
		String browserType = request.getParameter("browserType");
		
		ModelAndView view = new ModelAndView();
		BrowserType browser = BrowserType.toBrowserType(browserType);
		if (browser == null) {
			view.setViewName("qr/error");
			view.addObject("msg", "不支持的服务类型");
			return view;
		}
		if (StringUtils.isEmpty(tradeNo) || StringUtils.isEmpty(mchId) || StringUtils.isEmpty(browserType)) {
			view.setViewName("qr/" + browser.name().toLowerCase() + "/pay-fail");
			return view;
		}
		
		String[] params = {mchId, browserType, tradeNo};
		CodeMsg<CommercialArguments> codeMsg = commericalRightsService.checkCommericalRights(params, mchId, 
				request.getParameter("sign"), browser.getTradeType());
		if (codeMsg == null) {
			view.setViewName("qr/error");
			view.addObject("msg", "系统异常");
			return view;
		} else if (!Constants.OK.equals(codeMsg.getCode())) {
			view.setViewName("qr/error");
			view.addObject("msg", "系统异常");
			return view;
		}
		
		QrPayHandle handle = QrPayHandleFactory.createHandle(gatewayService, browser);
		return handle.createAcceptPayResult(tradeNo, codeMsg.getInfo(), orderOperService);
	}
}
