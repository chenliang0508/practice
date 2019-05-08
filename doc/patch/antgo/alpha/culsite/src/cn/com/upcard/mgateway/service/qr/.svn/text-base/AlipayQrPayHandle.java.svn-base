package cn.com.upcard.mgateway.service.qr;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.BrowserType;
import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.controller.request.BaseParam;
import cn.com.upcard.mgateway.controller.response.AlipayJsapiResponse;
import cn.com.upcard.mgateway.controller.response.QRCodePreResponse;
import cn.com.upcard.mgateway.controller.validator.ResquestSignValidator;
import cn.com.upcard.mgateway.entity.TpCulCommerical;
import cn.com.upcard.mgateway.exception.GatewayBusinessException;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.payment.alipay.AlipayApiContext;
import cn.com.upcard.mgateway.payment.alipay.AlipayClient;
import cn.com.upcard.mgateway.payment.alipay.UserInfoRequest;
import cn.com.upcard.mgateway.service.GatewayService;
import cn.com.upcard.mgateway.service.SessionService;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class AlipayQrPayHandle extends DefaultQrPayHandle {
	
	private static final Logger logger = LoggerFactory.getLogger(AlipayQrPayHandle.class);
	AlipayQrPayHandle(GatewayService gateway, BrowserType type) {
		super(gateway, type);
	}

	@Override
	public ModelAndView preToOrder(TpCulCommerical culCommInfo) {
		ModelAndView view = new ModelAndView();
		String[] strs = {culCommInfo.getCulCommNo(), browserType.getBrowserTypeSign()};
		String sign = ResquestSignValidator.genSign(strs, culCommInfo.getCommKey());
		String url = new StringBuffer().append(SysPara.MGATEWAY_HOST + "/q/toOrder")
				.append("?")
				.append("&mchId=").append(culCommInfo.getCulCommNo())
				.append("&browserType=").append(browserType.getBrowserTypeSign())
				.append("&sign=").append(sign)
				.toString();
		logger.info("url:{}", url);
		
		//生成支付宝oauth2连接
		AlipayApiContext alipayApi = new AlipayApiContext();
		alipayApi.setAppId(SysPara.ALIPAY_APPID);//2015122901050446  2014052100006078
		alipayApi.setCharset("GBK");
		alipayApi.setSignType("RSA");
		alipayApi.setVersion("1.0");
		alipayApi.setPrivateKey(SysPara.ALIPAY_PRIVATE_KEY);
		alipayApi.setGateway(SysPara.ALIPAY_GATEWAY);
		AlipayClient alipayClient = new AlipayClient(alipayApi);
		String alipayOauthUrl = null;
		try {
			alipayOauthUrl = alipayClient.createOauth2Url(url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new GatewayBusinessException("不支持的字符集");
		}
		logger.info("alipayOauth2:{}", alipayOauthUrl);
		//支付宝
		view.setViewName("redirect:" + alipayOauthUrl);
		return view;
	}
	
	@Override
	public ModelAndView createInputMoneyPage(TpCulCommerical culCommInfo, SessionService sessionService, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String timestamp = System.currentTimeMillis() / 1000 + "";
		String[] strs = { culCommInfo.getCulCommNo(), timestamp, uuid };
		String sign = ResquestSignValidator.genSign(strs, culCommInfo.getCommKey());
		String authCode = request.getParameter("auth_code");
		
		logger.info("authCode:{}", authCode);
		if (StringUtils.isBlank(authCode)) {
			view.setViewName("qr/error");
			view.addObject("msg", "获取参数异常");
			return view;
		}
		//生成支付宝oauth2连接
		//初始化alipay调用接口环境
		AlipayApiContext alipayApi = new AlipayApiContext();
		alipayApi.setAppId(SysPara.ALIPAY_APPID);//2015122901050446  2014052100006078
		alipayApi.setCharset("GBK");
		alipayApi.setSignType("RSA");
		alipayApi.setVersion("1.0");
		alipayApi.setPrivateKey(SysPara.ALIPAY_PRIVATE_KEY);
		alipayApi.setGateway(SysPara.ALIPAY_GATEWAY);
		//初始化换取用户信息的api参数
		UserInfoRequest userInfoRequest = new UserInfoRequest();
		userInfoRequest.setCode(authCode);
		userInfoRequest.setGrantType(UserInfoRequest.GrantType.AUTHORIZATION_CODE);
		
		AlipayClient alipayClient = new AlipayClient(alipayApi);
		String userId = alipayClient.getAlipayUserId(userInfoRequest);
		if (StringUtils.isEmpty(userId)) {
			view.setViewName("qr/error");
			view.addObject("msg", "获取用户信息失败");
			return view;
		}
		
		// 保存数据到Redis,用于防重复提交
		BaseParam param = new BaseParam();
		param.setMchId(culCommInfo.getCulCommNo());
		sessionService.saveQrSessionInfo(uuid, param);

		view.setViewName("qr/" + browserType.name().toLowerCase() + "/" + browserType.name().toLowerCase() + "-qr-pay");
		view.addObject("mchName", culCommInfo.getCommFullName());
		view.addObject("mchId", culCommInfo.getCulCommNo());
		view.addObject("timestamp", timestamp);
		view.addObject("qrToken", uuid);
		view.addObject("sign", sign);
		view.addObject("thirdBuyerId", userId);
		// 放入签名防止页面篡改数据
		return view;
	}
	
	@Override
	public ModelAndView createPreOrder(PreTradeOrderInfo preTradeOrderInfo) {
		AlipayJsapiResponse aliJsResponse = gatewayService.alipayJsapiPreOrder(preTradeOrderInfo);
		//预下单出错，刷新页面重试
		if (!Constants.OK.equals(aliJsResponse.getCode())) {
			logger.warn("alipay qr pre create order result-- code:{}----msg:{}", aliJsResponse.getCode(), aliJsResponse.getMsg());
			ModelAndView view = new ModelAndView();
			view.addObject("msg", "系统繁忙，请刷新重试");
			view.setViewName("qr/error");
			return view;
		} else {
			QRCodePreResponse qrResponse = new QRCodePreResponse(ResponseResult.OK);
			qrResponse.setMchId(preTradeOrderInfo.getCommUnionNo());
			qrResponse.setPayUrl(aliJsResponse.getPayUrl());
			qrResponse.setPayInfo(aliJsResponse.getPayInfo());
			logger.info("alipay pay url:{}", aliJsResponse.getPayInfo());
			
			String tradeNO = JSONObject.parseObject(aliJsResponse.getPayInfo()).getString("tradeNO");
			ModelAndView view = new ModelAndView();
			view.addObject("tradeNO", tradeNO);
			view.setViewName("qr/alipay/alipay-qr-jspay");
			view.addObject("url", preTradeOrderInfo.getReturnUrl());
			return view;
		}
	}
}
