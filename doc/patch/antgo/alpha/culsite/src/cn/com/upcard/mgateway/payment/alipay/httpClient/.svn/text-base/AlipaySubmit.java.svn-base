package cn.com.upcard.mgateway.payment.alipay.httpClient;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

import cn.com.upcard.mgateway.payment.alipay.AlipayRequestBean;
import cn.com.upcard.mgateway.payment.alipay.AlipaySignBean;
import cn.com.upcard.mgateway.payment.alipay.sign.MD5;
import cn.com.upcard.mgateway.payment.alipay.sign.RSA;


public class AlipaySubmit {
	/**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值
     * 如：buildRequest("", "",sParaTemp)
     * @paramALIPAY_GATEWAY_NEW 支付宝网关地址
     * @param strParaFileName 文件类型的参数名
     * @param strFilePath 文件路径
     * @param sParaTemp 请求参数数组
     * @return 支付宝处理结果
     * @throws Exception
     */
    public static String buildRequest(AlipayRequestBean bean,String strParaFileName, String strFilePath, Map<String, String> sParaTemp) throws Exception {

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.STRING);//BYTES
        //设置编码集
        request.setCharset(bean.getCharset());
        request.setMethod(HttpRequest.METHOD_POST);
        request.setParameters(generatNameValuePair(sParaTemp));
        request.setUrl(bean.getGatewayUrl());
        HttpResponse response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
        if (response == null) {
            return null;
        }
        
        String strResult = response.getStringResult();
        return strResult;
    }
    
    
	/**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(AlipaySignBean bean,Map<String, String> sPara) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
    	System.out.println(prestr);
    	String mysign = "";
        if(bean.getSignType().equals("MD5") ) {
        	mysign = MD5.sign(prestr, bean.getKey(),bean.getCharset());
        }
        if(bean.getSignType().equals("RSA") ){
        	mysign = RSA.sign(prestr, bean.getPrivateKey(),bean.getCharset());
        }
        System.out.println(mysign);
        return mysign;
    }

	
	/** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                ) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    
    
    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }
}

