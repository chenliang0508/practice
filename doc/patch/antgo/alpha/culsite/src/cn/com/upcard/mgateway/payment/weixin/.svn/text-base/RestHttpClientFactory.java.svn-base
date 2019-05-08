package cn.com.upcard.mgateway.payment.weixin;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class RestHttpClientFactory {
	private static int timeout = 600000;

	private static int maxTotalConnection = 10000;
	private static int defaultMaxConnectionsPerHost = 3000;

	private static HttpClient httpClient;

	public static HttpClient newInstance() {
		if (httpClient == null) {
			synchronized (RestHttpClientFactory.class) {
				MultiThreadedHttpConnectionManager connectionManager = createHttpConnectionManagerParams();
				httpClient = new HttpClient();
				httpClient.setHttpConnectionManager(connectionManager);
			}
		}
		return httpClient;
	}

	public static void refreshHttpClient() {
		synchronized (RestHttpClientFactory.class) {
			MultiThreadedHttpConnectionManager connectionManager = createHttpConnectionManagerParams();
			httpClient = new HttpClient();
			httpClient.setHttpConnectionManager(connectionManager);
		}
	}

	private static MultiThreadedHttpConnectionManager createHttpConnectionManagerParams() {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
		params.setMaxTotalConnections(maxTotalConnection);
		params.setConnectionTimeout(timeout);
		params.setSoTimeout(timeout);
		connectionManager.setParams(params);
		return connectionManager;
	}
}
