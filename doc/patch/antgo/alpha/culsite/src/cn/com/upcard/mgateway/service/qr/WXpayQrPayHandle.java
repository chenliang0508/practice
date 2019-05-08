package cn.com.upcard.mgateway.service.qr;

import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.BrowserType;
import cn.com.upcard.mgateway.controller.request.BaseParam;
import cn.com.upcard.mgateway.controller.response.WxJsResponse;
import cn.com.upcard.mgateway.controller.validator.ResquestSignValidator;
import cn.com.upcard.mgateway.entity.TpCulCommerical;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.service.GatewayService;
import cn.com.upcard.mgateway.service.SessionService;
import cn.com.upcard.mgateway.util.JsonUtils;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class WXpayQrPayHandle extends DefaultQrPayHandle {
	private static final Logger logger = LoggerFactory.getLogger(WXpayQrPayHandle.class);
	private static final String  JS_PAY = "qr/weixin/weixin-qr-jspay";

	WXpayQrPayHandle(GatewayService gateway, BrowserType type) {
		super(gateway, type);
	}

	@Override
	public ModelAndView preToOrder(TpCulCommerical culCommInfo) {
		ModelAndView view = new ModelAndView();
		String[] strs = { culCommInfo.getCulCommNo(), browserType.getBrowserTypeSign() };
		String sign = ResquestSignValidator.genSign(strs, culCommInfo.getCommKey());
		// 微信要获取到用户的openId，编辑oauth2的协议的回调地址
		String data= new StringBuffer().append("&mchId=").append(culCommInfo.getCulCommNo())
				.append("&browserType=").append(browserType.getBrowserTypeSign()).append("&sign=").append(sign).toString();
		String uri= null;
		try {
			uri = new StringBuffer().append(SysPara.WEIXIN_OAUTH2_REDIRECT_URL + "?").append(URLEncoder.encode(data, "UTF-8")).toString();
			uri = URLEncoder.encode(uri, "UTF-8");
			String url = SysPara.WX_OAUTH2_URL.replace("{appId}", SysPara.WX_APPID).replace("{uri}", uri);
			logger.info("url:{}", url);
			view.setViewName("redirect:"+url);
			return view;

		} catch (Exception e) {
			e.printStackTrace();
			view.setViewName("qr/error");
			view.addObject("msg", "系统异常");
			return view;
		}
	}

	@Override
	public ModelAndView createInputMoneyPage(TpCulCommerical culCommInfo, SessionService sessionService, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String timestamp = System.currentTimeMillis() / 1000 + "";
		String[] strs = { culCommInfo.getCulCommNo(), timestamp, uuid };
		String sign = ResquestSignValidator.genSign(strs, culCommInfo.getCommKey());
		String openId = request.getParameter("openId");
		logger.info(openId);
		if (StringUtils.isBlank(openId)) {
			view.setViewName("qr/error");
			view.addObject("msg", "获取参数异常");
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
		view.addObject("thirdBuyerId", openId);
		// 放入签名防止页面篡改数据
		return view;
	}

	@Override
	public ModelAndView createPreOrder(PreTradeOrderInfo preTradeOrderInfo) {
		preTradeOrderInfo.setSubAppid(SysPara.WX_APPID);
		//原生js支付
		preTradeOrderInfo.setIsRaw("1");
		WxJsResponse wxJsResponse = gatewayService.wxJsPreOrder(preTradeOrderInfo);
		if (!Constants.OK.equals(wxJsResponse.getCode())) {
			logger.warn("wxjspay qr pre create order result-- code:{}----msg:{}", wxJsResponse.getCode(), wxJsResponse.getMsg());
			ModelAndView view = new ModelAndView();
			view.addObject("msg", "系统繁忙，请刷新重试");
			view.setViewName("qr/error");
			return view;
		} else {
			//QRCodePreResponse qrResponse = new QRCodePreResponse(ResponseResult.OK);
			Map<String, String> payInfo = JsonUtils.jsonToMap(wxJsResponse.getPayInfo());
			String appId = (String) payInfo.get("appId");
			 String timeStamp = (String)payInfo.get("timeStamp");
			String nonceStr = (String) payInfo.get("nonceStr");
			 String package1 = (String) payInfo.get("package");
			 String signType = (String) payInfo.get("signType");
			 String paySign = (String) payInfo.get("paySign"); 
			
			ModelAndView view = new ModelAndView();
			view.setViewName(JS_PAY);
			view.addObject("appId", appId);
			view.addObject("timeStamp", timeStamp);
			view.addObject("nonceStr", nonceStr);
			view.addObject("package1", package1);
			view.addObject("signType", signType);
			view.addObject("paySign", paySign);
			view.addObject("url", preTradeOrderInfo.getReturnUrl());
			return view;
		}
	}
}
