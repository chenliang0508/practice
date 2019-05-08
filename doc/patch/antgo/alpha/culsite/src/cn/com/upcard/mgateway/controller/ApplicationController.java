package cn.com.upcard.mgateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/application")
public class ApplicationController extends BaseController{
	
	/**
	 * <pre>
	 * 心跳接口
	 * </pre>
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/heartbeat")
	public String heartbeat() {
		return "success";
	}
}
