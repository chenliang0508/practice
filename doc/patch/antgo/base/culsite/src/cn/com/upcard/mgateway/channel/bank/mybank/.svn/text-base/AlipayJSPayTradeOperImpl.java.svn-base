package cn.com.upcard.mgateway.channel.bank.mybank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.model.ChannelResponseInfo;

/**
 * 用于阿里js支付
 * 
 * @author zhoudi
 *
 */
public class AlipayJSPayTradeOperImpl extends JsUnifiedOper {

	private static Logger logger = LoggerFactory.getLogger(AlipayJSPayTradeOperImpl.class);

	@Override
	public ChannelResponseInfo pay(ChannelRequestInfo channelRequestInfo) {
		channelRequestInfo.setChannelType("ALI");
		if (StringUtils.isNotEmpty(channelRequestInfo.getTimeExpire())) {
			try {
				channelRequestInfo.setExpireExpress(getMinutesFromNow(channelRequestInfo.getTimeExpire()));
			} catch (ParseException e) {
				// 如果转换失败，默认为 1 小时超时
				logger.error("获取订单过期时间错误", e);
			}
		}

		ChannelResponseInfo channelResponseInfo = super.pay(channelRequestInfo);
		Map<String, String> payInfoMap = new HashMap<>();
		payInfoMap.put("tradeNO", channelResponseInfo.getPayInfo());
		channelResponseInfo.setPayInfo(JSONObject.toJSONString(payInfoMap));
		return channelResponseInfo;
	}

	/**
	 * 获取时间和当前时间相差的分钟数
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	private String getMinutesFromNow(String time) throws ParseException {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		long end = simpleFormat.parse(time).getTime();
		long seconds = (end - System.currentTimeMillis()) / 1000;

		// 如果相差秒数小于 60，设置超时时间为 1 分，网商设置超时时间最短为1分钟
		if (seconds < 60) {
			return "1";
		}

		if (seconds % 60 == 0) {
			return String.valueOf(seconds / 60);
		}

		return String.valueOf(seconds / 60 + 1);
	}

}
