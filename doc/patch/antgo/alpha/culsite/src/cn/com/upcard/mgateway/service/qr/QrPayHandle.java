package cn.com.upcard.mgateway.service.qr;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import cn.com.upcard.mgateway.common.enums.BrowserType;
import cn.com.upcard.mgateway.entity.TpCulCommerical;
import cn.com.upcard.mgateway.model.CommercialArguments;
import cn.com.upcard.mgateway.model.PreTradeOrderInfo;
import cn.com.upcard.mgateway.service.GatewayService;
import cn.com.upcard.mgateway.service.OrderOperService;
import cn.com.upcard.mgateway.service.SessionService;

/**
 * <pre>
 * 静态二维码
 * </pre>
 * @author huatingzhou
 * @version 1.0
 */
public abstract class QrPayHandle {
	protected BrowserType browserType;
	protected GatewayService gatewayService;
	
	QrPayHandle(GatewayService catewayService,BrowserType browserType){
		this.browserType = browserType;
		this.gatewayService = catewayService;
	}
	
	/**
	 * 跳转到输入金额页面前准备用户信息
	 * @return
	 */
	public abstract ModelAndView preToOrder(TpCulCommerical culCommInfo);
	/**
	 * <pre>
	 * 生成金额输入页面
	 * </pre>
	 * @return
	 */
	public abstract ModelAndView createInputMoneyPage(TpCulCommerical culCommInfo, SessionService sessionService, HttpServletRequest request);
	
	/**
	 * <pre>
	 * 预下单后跳转页面
	 * 
	 * </pre>
	 * @return
	 */
	public abstract ModelAndView createPreOrder(PreTradeOrderInfo preTradeOrderInfo);
	/**
	 * <pre>
	 * 支付成功后跳转页面
	 * 
	 * </pre>
	 * @return
	 */
	public abstract ModelAndView createAcceptPayResult(String orderNo, CommercialArguments agm, OrderOperService orderOperService);
}
