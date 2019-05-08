package cn.com.upcard.mgateway.payment.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author WangHB
 * 
 */
public class HttpClientTool {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	/**
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getHttpGetResutl(String url) throws Exception {
		logger.debug("try to connect url:: {}", url);
		HttpClient httpClient = RestHttpClientFactory.newInstance();
		GetMethod get = new GetMethod(url);
		get.getParams().setContentCharset("utf-8");
		try{
			httpClient.executeMethod(get);
			String ret = get.getResponseBodyAsString();
			logger.debug("get response string:{}", ret);
			return ret;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}

	}

	/**
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getHttpsResult(String url) throws Exception {
		return getHttpsResult(url, null);
	}

	/**
	 * @param url
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String getHttpsResult(String url, String json)
			throws Exception {
		logger.debug("[url]: {}", url);
		System.out.println("-------url------"+url);
        logger.debug("[request]: {}", json);
		X509TrustManager trustManager = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
				// Don't do anything.
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[],
			 *      java.lang.String)
			 */
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
				// Don't do anything.
			}

			public X509Certificate[] getAcceptedIssuers() {
				// Don't do anything.
				return null;
			}

		};
		// Now put the trust manager into an SSLContext.
		SSLContext sslcontext = SSLContext.getInstance("SSL");
		sslcontext.init(null, new TrustManager[] { trustManager }, null);

		// Use the above SSLContext to create your socket factory
		// (I found trying to extend the factory a bit difficult due to a
		// call to createSocket with no arguments, a method which doesn't
		// exist anywhere I can find, but hey-ho).
		SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
		sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getConnectionManager().getSchemeRegistry().register(
				new Scheme("https", sf, 443));

		HttpPost httpPost = new HttpPost(url);
		// Execute HTTP request
		// httpPost.setHeader("Authorization", "basic "
		// + "dGNsb3VkYWRtaW46dGNsb3VkMTIz");
		httpPost.setHeader("Content-type", "application/xml");

		if (null != json) {
			httpPost.setEntity(new StringEntity(json, "UTF-8"));
		} else {
			httpPost.setEntity(new StringEntity(""));
		}
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity resEntity = response.getEntity();
		InputStreamReader reader = new InputStreamReader(resEntity.getContent());

		char[] buff = new char[1024];
		int length = 0;
		String result = "";
		while ((length = reader.read(buff)) != -1) {
			result += new String(buff, 0, length);
		}
		httpclient.getConnectionManager().shutdown();
		logger.debug("get response:{}", result);
		return result;
	}

    public String urlEncode(String url, String charset) {
        try {
            if (StringUtils.isEmpty(url)) {
                return url;
            }
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String concatHttpParams(Map<String, String> params, String charset) {
        if (params == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = false;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (isFirst == false) {
                isFirst = true;
            } else {
                sb.append('&');
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(urlEncode(entry.getValue(), charset));
        }
        return sb.toString();
    }

    public String doPostRequest(String url, Map<String, String> params, String charset) {
        PrintWriter writer = null;
        InputStreamReader reader = null;
        StringBuffer ret = new StringBuffer();
        try {
            if (url.startsWith("https")) {
                // Create all-trusting host name verifier
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            }

            String request = concatHttpParams(params, charset);
            URLConnection conn = new URL(url).openConnection();
            logger.debug("[url]:" + url);
            logger.debug("[req]:" + request);

            conn.setDoOutput(true); //implicitly set the request method to POST because that's the default method whenever you want to send a request body.
            conn.setDoInput(true);

            OutputStream remoteInput = conn.getOutputStream();
            writer = new PrintWriter(remoteInput);
            writer.print(request);
            writer.flush();

            InputStream inputStream = conn.getInputStream();
            reader = new InputStreamReader(inputStream, charset);

            char[] buff = new char[1024];
            int length = 0;
            while ((length = reader.read(buff)) != -1) {
                ret.append(buff, 0, length);
            }

            logger.debug("[api ret orig]:" + ret);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return ret.toString();
    }
}
