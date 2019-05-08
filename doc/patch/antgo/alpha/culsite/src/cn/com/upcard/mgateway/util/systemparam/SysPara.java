package cn.com.upcard.mgateway.util.systemparam;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统参数
 * 
 * @author zhoudi
 *
 */
public class SysPara {

	private static Logger logger = LoggerFactory.getLogger(SysPara.class);

	public static String MGATEWAY_HOST;
	public static String WEIXIN_OAUTH2_REDIRECT_URL;
	public static String SWIFT_REQ_URL; // 威富通请求url

	public static String LKL_MCH_ID;// 拉卡拉商户号
	public static String LKL_KEY;// 拉卡拉交易密钥
	public static String LKL_REQ_URL;// 拉卡拉请求地址

	public static String WX_OAUTH2_URL;
	public static String WX_APPID;

	public static String MYBANK_REQ_URL;
	public static String MYBANK_ISV_ORG_ID;// 网商银行分配的合作机构号
	public static String MYBANK_APP_ID;
	public static String MYBANK_RSA_PUBLICKEY;// 网商银行返回参数验签的公钥
	public static String MYBANK_RSA_PRIVATEKEY;// 银商请求网商接口加签名的私钥
	
	public static String ALIPAY_APPID;
	public static String ALIPAY_BIZ_PULBIC_KEY;
	public static String ALIPAY_PRIVATE_KEY;
	public static String ALIPAY_GATEWAY;//支付宝网关地址
	
	public static String CMBC_PLATFORMID;// 民生银行的接入平台号
	public static String CMBC_SM2_PATH;// 民生银行的私钥路劲
	public static String CMBC_SM2_KEY;// 民生银行的私钥密码
	public static String CMBC_CER_PATH;// 民生银行的公钥路劲
	public static String CMBC_QUERY_URL;// 民生银行的查询url
	public static String CMBC_PAY_URL;// 民生银行的交易url
	public static String CMBC_REFUND_URL;// 民生银行的退款url
	public static String WEB_INF_PATH;
	public static String CMBC_NOTIFY_URL;

	static public void init(){
		Properties prop = new Properties();
		InputStream in = SysPara.class.getResourceAsStream("/config.properties");
		try {
			String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			WEB_INF_PATH = classPath.substring(0, classPath.indexOf("WEB-INF") + "WEB-INF/".length());
			logger.info("WEB_INF_PATH=" + WEB_INF_PATH);
			prop.load(in);
			logger.info("--------------微信支付相关参数开始初始化--------------------");
			MGATEWAY_HOST = prop.getProperty("MGATEWAY_HOST").trim();
			WEIXIN_OAUTH2_REDIRECT_URL = prop.getProperty("WEIXIN_OAUTH2_REDIRECT_URL").trim();
			WX_OAUTH2_URL = prop.getProperty("WX_OAUTH2_URL");
			WX_APPID = prop.getProperty("WX_APPID");
			logger.info("WX_OAUTH2_URL=" + WX_OAUTH2_URL);
			logger.info("WX_APPID=" + WX_APPID);
			logger.info("WEIXIN_OAUTH2_REDIRECT_URL:{}", WEIXIN_OAUTH2_REDIRECT_URL);
			logger.info("--------------微信支付相关参数初始化结束--------------------");
			
			
			logger.info("--------------支付宝支付相关参数开始初始化--------------------");
			ALIPAY_APPID = prop.getProperty("ALIPAY_APPID");
			ALIPAY_BIZ_PULBIC_KEY = prop.getProperty("ALIPAY_BIZ_PULBIC_KEY");
			ALIPAY_PRIVATE_KEY = prop.getProperty("ALIPAY_PRIVATE_KEY");
			ALIPAY_GATEWAY = prop.getProperty("ALIPAY_GATEWAY");
			logger.info("ALIPAY_APPID=" + ALIPAY_APPID);
			logger.info("ALIPAY_BIZ_PULBIC_KEY=" + ALIPAY_BIZ_PULBIC_KEY);
			logger.info("ALIPAY_PRIVATE_KEY=" + ALIPAY_PRIVATE_KEY);
			logger.info("ALIPAY_GATEWAY=" + ALIPAY_GATEWAY);
			logger.info("--------------支付宝支付相关参数初始化结束--------------------");
			
			
			logger.info("--------------兴业银行参数开始初始化--------------------");
			SWIFT_REQ_URL = prop.getProperty("swift_req_url").trim();
			logger.info("SWIFT_REQ_URL=" + SWIFT_REQ_URL);
			logger.info("--------------兴业银行参数初始化结束--------------------");

			
			logger.info("--------------拉卡拉参数开始初始化--------------------");
			LKL_MCH_ID = prop.getProperty("lkl_mch_id");
			LKL_KEY = prop.getProperty("lkl_key");
			LKL_REQ_URL = prop.getProperty("lkl_req_url");
			logger.info("LKL_MCH_ID=" + LKL_MCH_ID);
			logger.info("LKL_KEY=" + LKL_KEY);
			logger.info("LKL_REQ_URL=" + LKL_REQ_URL);
			logger.info("--------------拉卡拉参数开始初始化--------------------");
			

			
			logger.info("--------------网商银行参数开始初始化--------------------");
			MYBANK_REQ_URL = prop.getProperty("mybank_req_url");
			MYBANK_ISV_ORG_ID = prop.getProperty("mybank_IsvOrgId");
			MYBANK_APP_ID = prop.getProperty("mybank_app_id");
			MYBANK_RSA_PUBLICKEY = prop.getProperty("mybank_RSA_publicKey");
			MYBANK_RSA_PRIVATEKEY = prop.getProperty("mybank_RSA_privateKey");
			logger.info("MYBANK_REQ_URL=" + MYBANK_REQ_URL);
			logger.info("MYBANK_ISV_ORG_ID=" + MYBANK_ISV_ORG_ID);
			logger.info("MYBANK_APP_ID=" + MYBANK_APP_ID);
			logger.info("MYBANK_RSA_PUBLICKEY=" + MYBANK_RSA_PUBLICKEY);
			logger.info("MYBANK_RSA_PRIVATEKEY=" + MYBANK_RSA_PRIVATEKEY);
			logger.info("--------------网商银行参数开始初始化--------------------");
			
			logger.info("--------------民生银行参数开始初始化--------------------");
			CMBC_PLATFORMID = prop.getProperty("CMBC_PLATFORMID");
			CMBC_SM2_PATH = WEB_INF_PATH + prop.getProperty("CMBC_SM2_PATH");
			CMBC_SM2_KEY = prop.getProperty("CMBC_SM2_KEY");
			CMBC_CER_PATH = WEB_INF_PATH + prop.getProperty("CMBC_CER_PATH");
			CMBC_QUERY_URL = prop.getProperty("CMBC_QUERY_URL");
			CMBC_PAY_URL = prop.getProperty("CMBC_PAY_URL");
			CMBC_REFUND_URL = prop.getProperty("CMBC_REFUND_URL");
			CMBC_NOTIFY_URL = prop.getProperty("CMBC_NOTIFY_URL");
			
			logger.info("CMBC_PLATFORMID=" + CMBC_PLATFORMID);
			logger.info("CMBC_SM2_PATH=" + CMBC_SM2_PATH);
			logger.info("CMBC_SM2_KEY=" + CMBC_SM2_KEY);
			logger.info("CMBC_CER_PATH=" + CMBC_CER_PATH);
			logger.info("CMBC_QUERY_URL=" + CMBC_QUERY_URL);
			logger.info("CMBC_PAY_URL=" + CMBC_PAY_URL);
			logger.info("CMBC_REFUND_URL=" + CMBC_REFUND_URL);
			logger.info("CMBC_NOTIFY_URL=" + CMBC_NOTIFY_URL);
			logger.info("--------------民生银行参数结束初始化--------------------");
		} catch (IOException e) {
			logger.info("IOException", e);
		}
	}

}
