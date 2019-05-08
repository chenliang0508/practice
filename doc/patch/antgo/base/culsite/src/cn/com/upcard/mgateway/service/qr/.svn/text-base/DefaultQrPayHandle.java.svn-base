package cn.com.upcard.mgateway.service.qr;

import java.math.BigDecimal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import cn.com.upcard.mgateway.common.Constants;
import cn.com.upcard.mgateway.common.enums.BrowserType;
import cn.com.upcard.mgateway.common.enums.OrderStatus;
import cn.com.upcard.mgateway.controller.request.BaseParam;
import cn.com.upcard.mgateway.controller.response.TradeQueryResponse;
import cn.com.upcard.mgateway.controller.validator.ResquestSignValidator;
import cn.com.upcard.mgateway.entity.TpCulCommerical;
import cn.com.upcard.mgateway.entity.TradeOrder;
import cn.com.upcard.mgateway.model.CommercialArguments;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.service.GatewayService;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.service.SessionService;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public abstract class DefaultQrPayHandle extends QrPayHandle {
	private static final Logger logger = LoggerFactory.getLogger(AlipayQrPayHandle.class);
	DefaultQrPayHandle(GatewayService gateway, BrowserType type) {
		super(gateway, type);
	}
	
	@Override
	public ModelAndView preToOrder(TpCulCommerical culCommInfo) {
		ModelAndView view = new ModelAndView();
		String[] strs = {culCommInfo.getCulCommNo(), browserType.getBrowserTypeSign()};
		String sign = ResquestSignValidator.genSign(strs, culCommInfo.getCommKey());
		
		//支付宝不需要获取用户参数，直接转发到输入金额页面
		String url = new StringBuffer().append("redirect:" + SysPara.MGATEWAY_HOST + "/gateway")
				.append("?service=unified.qrcode.toOrder")
				.append("&mchId=").append(culCommInfo.getCulCommNo())
				.append("&browserType=").append(browserType.getBrowserTypeSign())
				.append("&sign=").append(sign)
				.toString();
		logger.info("url:{}", url);
		view.setViewName(url);
		return view;
	}
	
	@Override
	public ModelAndView createInputMoneyPage(TpCulCommerical culCommInfo, SessionService sessionService, HttpServletRequest request) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String timestamp = System.currentTimeMillis() / 1000 + "";
		String[] strs = {culCommInfo.getCulCommNo(), timestamp, uuid};
		String sign = ResquestSignValidator.genSign(strs, culCommInfo.getCommKey());
		
		//保存数据到Redis,用于防重复提交
		BaseParam param = new BaseParam();
		param.setMchId(culCommInfo.getCulCommNo());
		sessionService.saveQrSessionInfo(uuid, param);
		
		ModelAndView view = new ModelAndView();
		view.setViewName("qr/" + browserType.name().toLowerCase() + "/" +  browserType.name().toLowerCase() + "-qr-pay");
		view.addObject("mchName", culCommInfo.getCommFullName());
		view.addObject("mchId", culCommInfo.getCulCommNo());
		view.addObject("timestamp", timestamp);
		view.addObject("qrToken", uuid);
		view.addObject("sign", sign);
		//放入签名防止页面篡改数据
		return view;
	}
	
	@Override
	public ModelAndView createAcceptPayResult(String orderNo, CommercialArguments arg, OrderOperService orderOperService) {
		PreTradeOrderInfo preTradeOrderInfo = new PreTradeOrderInfo();
		preTradeOrderInfo.setCommUnionNo(arg.getTpCulCommerical().getCulCommNo());
		preTradeOrderInfo.setBankMerNo(arg.getTpCommPayTypes().getTpCommericalInfo().getBankMerNo());
		preTradeOrderInfo.setBankCode(arg.getTpCommPayTypes().getTpAcquirer().getBankIdtCode());
		preTradeOrderInfo.setChannelId(arg.getTpCommPayTypes().getChannelId());
		preTradeOrderInfo.setOutTradeNo(orderNo);
		preTradeOrderInfo.setCommPrivateKey(arg.getTpCommPayTypes().getTpCommericalInfo().getCommKey());
		
		ModelAndView view = new ModelAndView();
		TradeQueryResponse response = gatewayService.unifiedPayQuery(preTradeOrderInfo);
		if (Constants.OK.equals(response.getCode()) && OrderStatus.SUCCESS.name().equals(response.getTradeState())) {
			String totalAmountInFen = response.getTotalAmount();
			String totalAmount = new BigDecimal(totalAmountInFen).divide(new BigDecimal(100)).toString();
			TradeOrder order = orderOperService.findOrderByTradeNo(response.getTransactionId(), false);
			view.addObject("totalAmount", totalAmount);
			if (order != null && order.getChannelTxnNo() != null) {
				view.addObject("orderNo", order.getChannelTxnNo().substring(order.getChannelTxnNo().length() - 4));
			}
			view.setViewName("qr/" + browserType.name().toLowerCase() + "/pay-success");
			return view;
		} else {
			view.setViewName("qr/" + browserType.name().toLowerCase() + "/pay-fail");
			return view;
		}
	}
}
