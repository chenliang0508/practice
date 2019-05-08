package cn.com.upcard.mgateway.payment.weixin;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpClientUtils {

	public static String getHttpGetResutl(String url)  {
		HttpClient httpClient = RestHttpClientFactory.newInstance();
		GetMethod get = new GetMethod(url);
		get.getParams().setContentCharset("utf-8");
		try {
			httpClient.executeMethod(get);
			String ret = get.getResponseBodyAsString();
			return ret;
		} catch (HttpException e) {
			e.printStackTrace();
			//TODO
			throw new RuntimeException("");
		} catch (IOException e) {
			e.printStackTrace();
			//TODO
			throw new RuntimeException("");
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}
	}

}
