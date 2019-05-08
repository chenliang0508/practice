package cn.com.upcard.mgateway.payment.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.com.upcard.mgateway.payment.alipay.httpClient.AlipaySubmit;
import cn.com.upcard.mgateway.payment.alipay.sign.RSA;
import cn.com.upcard.mgateway.util.DateUtil;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class AlipayClient {
	private static final Logger logger  = LoggerFactory.getLogger(AlipayClient.class);
	private static final String DEFAULT_ENCODING = "GBK";
	private static final String DEFAULT_SIGN_TYPE = "RSA";
	private static final String AUTH_URL = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id={appId}&scope={scope}&redirect_uri={redirectUrl}";
	private static final String ALIPAY_SYSTEM_OAUTH_TOKEN = "alipay.system.oauth.token";
	private static final String AUTH_SCOPE = "auth_base";
	private AlipayApiContext alipayApiContext;
	
	public AlipayClient() {
		AlipayApiContext a = new AlipayApiContext();
		a.setCharset(DEFAULT_ENCODING);
		a.setSignType(DEFAULT_SIGN_TYPE);
		a.setGateway(SysPara.ALIPAY_GATEWAY);
		this.alipayApiContext = a;
	}
	
	public AlipayClient(AlipayApiContext alipayApiContext) {
		this.alipayApiContext = alipayApiContext;
	}
	
	public String createOauth2Url(String redirectUrl) throws UnsupportedEncodingException {
		return AUTH_URL.replace("{appId}", alipayApiContext.getAppId())
				.replace("{scope}", AUTH_SCOPE)
				.replace("{redirectUrl}", URLEncoder.encode(redirectUrl, alipayApiContext.getCharset()));
	}
	
	public String verifyResponseXml() {
			String bizContent = new StringBuffer().append("<biz_content>")
					.append(this.alipayApiContext.getBizPublicKey())
					.append("</biz_content>")
					.append("<success>true</success>").toString();
			
			String sign = RSA.sign(bizContent, this.alipayApiContext.getPrivateKey(), this.alipayApiContext.getCharset());
	        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"" + this.alipayApiContext.getCharset() + "\"?>");
	        sb.append("<alipay>");
	        sb.append("<response>");
	        sb.append(bizContent);
	        sb.append("</response>");

	        sb.append("<sign>" + sign + "</sign>");
	        sb.append("<sign_type>RSA</sign_type>");
	        sb.append("</alipay>");
	        
	        logger.info("verify response :{}", sb.toString());
	        return sb.toString();
	}
	
	/**
	 * <pre>
	 * 换区用户的userId
	 * </pre>
	 * @param request
	 * @return
	 */
	public String getAlipayUserId(UserInfoRequest request) {
		//请求业务参数详细
		Map<String, String> alipayRequestPara = new HashMap<String, String>();
		//公共参数
		alipayRequestPara.put("app_id", this.alipayApiContext.getAppId());//支付宝分配给开发者的应用Id
		alipayRequestPara.put("method", ALIPAY_SYSTEM_OAUTH_TOKEN);//接口名称
		alipayRequestPara.put("charset", this.alipayApiContext.getCharset());//请求使用的编码格式，如utf-8,gbk,gb2312等
		alipayRequestPara.put("sign_type", this.alipayApiContext.getSignType());//商户生成签名字符串所使用的签名算法类型，目前支持RSA
		alipayRequestPara.put("version", this.alipayApiContext.getVersion());//调用的接口版本，固定为：1.0
		alipayRequestPara.put("timestamp", DateUtil.format2(new Date(), DateUtil.DATE_TIME_FORMATTER_STRING));//发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
		
		alipayRequestPara.put("grant_type", request.getGrantType().name().toLowerCase());
		alipayRequestPara.put("code", request.getCode());

		//除去数组中的空值和签名参数
        Map<String, String> sPara = AlipaySubmit.paraFilter(alipayRequestPara);
        //生成签名结果
        AlipaySignBean signBean = new AlipaySignBean();
        signBean.setCharset(this.alipayApiContext.getCharset());
        signBean.setSignType(this.alipayApiContext.getSignType());
        signBean.setPrivateKey(this.alipayApiContext.getPrivateKey());
        String mysign = AlipaySubmit.buildRequestMysign(signBean, sPara);
        alipayRequestPara.put("sign", mysign);
		//建立请求
        AlipayRequestBean requestBean = new AlipayRequestBean();
        requestBean.setCharset(this.alipayApiContext.getCharset());
        requestBean.setGatewayUrl(this.alipayApiContext.getGateway());
        logger.info("alipayRequestPara:" + alipayRequestPara);
		String sHtmlTextToken = null;
		try {
			sHtmlTextToken = AlipaySubmit.buildRequest(requestBean, "", "", alipayRequestPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("sHtmlTextToken:"+sHtmlTextToken);
		if (StringUtils.isEmpty(sHtmlTextToken)) {
			return null;
		}
		//TODO　验证签名
		JSONObject jsonObject = JSONObject.parseObject(sHtmlTextToken);
		JSONObject oauthResponseJsonObject = jsonObject.getJSONObject("alipay_system_oauth_token_response");
		String userId = oauthResponseJsonObject.getString("user_id");
        return userId;
	}
	
//	private static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMlONFPQu5ChGY5r" +
//            "vo4gfn3flw8/G/f5T1yoGffVMNGid+6cOhzQ11Uguw5qjA4Bf4SEMj8+rLqslXBn" +
//            "j7iWjt5c/Pfi6TnwoorDWFqpvDRcH9l6g6kf3b6UNMMSuYEZRyHtDJthGGi5OVXb" +
//            "LpKzg+DOz7Cpv58NxTNZkOkT8HjFAgMBAAECgYAlip4bm2u/Vyvq8ZEB9HFijBYh" +
//            "08Ulg6sXPopJO8r07XWsXBpCUXg2+fmogJpJ6mGblwO/47JWxeTAp6+X3wwZe1Ti" +
//            "GSXprMZ4eCVcZR1oXtLaZC8qWqz7YuaI03l9AV3z3LXd8Tyv8+Ji7AiPmh/RxWqK" +
//            "dn7of00loJm1aNXGAQJBAOVAgfoyi/bHW0Y+UtCrT8QPaGOEVo8gKMwSZ8iqfJ5h" +
//            "dCNyTLi0lgYBAVdcKy14BhxUw5nG7MsO+d0AA9hAZIUCQQDgyveQJ4S9fLxQyKpr" +
//            "ZdFx1Vlbf+B0r+KdEjvpiJ50uBMxf/GX3xXXJ+8Aq36iKvCqGGY7uDRJtxW3ZVPx" +
//            "AxdBAkATZY7ZxXcULS/q1JEbOLNqCkexy9urBnSNN61yTAJ5QBOcgCo/on/jCCi9" +
//            "5H1+vl39/aoDE9KrJ5w6d6rbnC8lAkEAju+ASyFVvkc6VwPBmVi8vO00TIIbbxcH" +
//            "uWiGwBAm8YC6c5cvP6qScKTrzDk/5E35ia7KEt8mIFlSbbL4lR+qAQJBAKeXo5wd" +
//            "GeL8Tc2QgZvrSGWwmHFCqvgqJph5St+C4ODLnRDy2EUwgk/AeNvRAlpHv6vqRoMN" +
//            "QUikpdl1iOqeqT4="; 
	
	
	private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALgAZ4Z76y97E+TA+im54KarxpBDhObR6tKic3cbdurE9anpVIcU/O1dqSX3eIKTfTbYAYZEpD5mGJJ+TgKWnB5v45iV8+dJ5PzrAlZEk6jHlHnqUsfsw8oBDXqsV71GuM9IbWAkV/Eq822qNEX5JN03KYw03+S+XzvCJWR3jLoVAgMBAAECgYABjL9Seqi4dajtPFLfFxm1Ta0WefsclpgLviKK1khiIdaP388+dAbNWQSbtUHml6duVtNsdheMDwAV+8pcazhVZaAU4j0baIfZagBcw6iXGVHtn91p/kqX3wTbOQVB39EXTKblJhGslUlUZ+fta7SUrc1e/44pwn/dNGZVLTE0AQJBAO2KX//27x+B8L0pUnEZ4Yw9tmNm2ltQgcMdU+MG8rObmKWBgZ7lqukefo0wV2ZQ+bnW1q4uc4LCqP6nyDRC86ECQQDGTO1Okndn8tdg90S+cRRCSGmm51sncGyYf866QJuzIVVdRBPfCSkO25HJYezjhcwfnmVUEUvS+XbQsNJHv/H1AkEAg+Ok0dYlhvT62cyrvaiFFvzc2+wrqS/WHKOYBCHDd/4YjyyM79DNSIpp8bK5lFG5lnm20Rdxg1TZyCsYegwCgQJAEpJ931AEcu3hkilWfOemWBbkHkeo1+bFpPrDZ9pJOtuyXQvVOJxpBgU2gD/1qdGMLqHtJ5R/H4FRyXXmG+Y0SQJAOdf6ZDIlGujnu65dhj/iVEkwtJro8j+zm5LLsXLUM1ecbBTuiQPydtKd3vyy8BKI3WToeiSI5YgwLbaHV4gu6A==";
	
	public static void main(String[] strs) throws UnsupportedEncodingException{
		AlipayApiContext alipayApi = new AlipayApiContext();
		alipayApi.setAppId("2014052100006078");//2015122901050446  2014052100006078
		alipayApi.setCharset(DEFAULT_ENCODING);
		alipayApi.setSignType("RSA");
		alipayApi.setVersion("1.0");
		alipayApi.setPrivateKey(privateKey);
		alipayApi.setGateway("https://openapi.alipay.com/gateway.do");
		AlipayClient client = new AlipayClient(alipayApi);
		UserInfoRequest r = new UserInfoRequest();
		r.setGrantType(UserInfoRequest.GrantType.AUTHORIZATION_CODE);
		r.setCode("6bc7a41b64524dc5a85b72d5a1f6PX62");
		logger.info(client.getAlipayUserId(r));
		
		
//		 System.out.println(AUTH_URL.replace("{appId}", "2014052100006078")
//				.replace("{scope}", AUTH_SCOPE)
//				.replace("{redirecUrl}", URLEncoder.encode("https://www.chinagiftcard.cn/mgateway/gateway/code" + 
//						"", "gbk")));
	}
	
	
}
