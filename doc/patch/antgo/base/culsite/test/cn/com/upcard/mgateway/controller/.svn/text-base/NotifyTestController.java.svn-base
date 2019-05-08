package cn.com.upcard.mgateway.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.upcard.mgateway.util.HttpClient;
import cn.com.upcard.mgateway.util.XmlUtils;

@Controller
@RequestMapping("notifytest")
public class NotifyTestController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(NotifyTestController.class);

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static Map<String, Integer> tradeNoMap = new HashMap<>();

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "relaythemessage")
	public String relayTheMessage(HttpServletRequest request) throws DocumentException, IOException {
		final Map<String, String> resultMap = XmlUtils.xmlToMap(request.getInputStream());
		logger.info("relayTheMessage begin!");
		logger.info("out_trade_no={}", resultMap.get("out_trade_no"));

		String url = "http://172.128.50.107:8080/mgateway/callback/cib";

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					try {
						HttpClient.sendXmlPost(resultMap, url);
					} catch (Exception e) {

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "default_thread");
		thread.start();

		logger.info("success!");
		return SUCCESS;
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "analogreception")
	public String analogReception(HttpServletRequest request) {

		logger.info("notify para = {}", request.getParameterMap());

		String outTradeNo = request.getParameter("outTradeNo");

		if (null == tradeNoMap.get(outTradeNo)) {
			tradeNoMap.put(outTradeNo, 1);
			return response("json", FAIL);
		} else if (1 == tradeNoMap.get(outTradeNo)) {
			tradeNoMap.put(outTradeNo, 2);
			return response("json", FAIL);
		} else if (2 == tradeNoMap.get(outTradeNo)) {
			tradeNoMap.put(outTradeNo, 3);
			return response("json", FAIL);
		} else {
			tradeNoMap.put(outTradeNo, 4);
			return response("json", SUCCESS);
		}

	}

}
