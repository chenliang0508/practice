package cn.com.upcard.mgateway.channel.bank.cmbc.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClient {
	
	public static String sendPost(String url, String json) throws IllegalStateException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		RequestConfig config = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).setConnectionRequestTimeout(20000).setStaleConnectionCheckEnabled(true).build();
		 // 1.3 参数3：请求目标uri
	    URI uri = null;
	    try {
	        uri = new URI(url);
	    } catch (URISyntaxException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    int port = uri.getPort();
	    if (port == -1) {
	        port = 80;// 协议默认端口
	    }
	    
	    HttpHost target = new HttpHost(uri.getHost(), port);
	    // 2.2 创建请求对象
	    HttpPost request = new HttpPost(uri);
	    // 2.3 封装请求参数，设置配置信息
	    StringEntity se = new StringEntity(json,  Charset.forName("UTF-8"));
	    se.setContentType("application/json");
	    request.addHeader("Content-type","application/json; charset=utf-8");  
	    request.setHeader("Accept", "application/json"); 
	    request.setEntity(se);
	    request.setConfig(config);
	    // 3. 发送post请求
	    CloseableHttpResponse response = null;
	    String resultStr = null;
	    try {
	        response = client.execute(target, request);
	        resultStr =EntityUtils.toString(response.getEntity());
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } finally {
	        if (response != null) {
	        response.close();
	        client.close();
	        }
	    }
	    return resultStr;
	    
	}

}
