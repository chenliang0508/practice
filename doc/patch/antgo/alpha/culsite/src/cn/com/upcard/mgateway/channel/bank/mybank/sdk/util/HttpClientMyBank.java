package cn.com.upcard.mgateway.channel.bank.mybank.sdk.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.upcard.mgateway.channel.bank.mybank.sdk.base.HttpsMain;
import cn.com.upcard.mgateway.exception.AbnormalResponseException;

public class HttpClientMyBank {

	private static Logger logger = LoggerFactory.getLogger(HttpClientMyBank.class);

	public static Map<String, Object> sendXmlPost(String url, String function, Map<String, String> data)
			throws Exception {
		XmlUtil xmlUtil = new XmlUtil();
		String param = xmlUtil.format(data, function);
		param = XmlSignUtil.sign(param);
		logger.debug("\r\n网商银行请求参数，resquest = \r\n{}", param);
		String response = HttpsMain.httpsReq(url, param);
		logger.info("\r\n网商银行请求返回，response = \r\n{}", response);

		if (!response.startsWith("<document>")) {
			throw new AbnormalResponseException(response);
		}
		if (!XmlSignUtil.verify(response)) {
			logger.error("网商返回验签失败");
			return null;
		}

		Map<String, Object> resultMap = xmlUtil.parse(response, function);

		return resultMap;
	}

}
