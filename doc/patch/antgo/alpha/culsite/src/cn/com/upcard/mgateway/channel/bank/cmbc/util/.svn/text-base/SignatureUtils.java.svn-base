package cn.com.upcard.mgateway.channel.bank.cmbc.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cfca.sm2.signature.SM2PrivateKey;
import cfca.sm2rsa.common.Mechanism;
import cfca.sm2rsa.common.PKIException;
import cfca.util.CertUtil;
import cfca.util.EnvelopeUtil;
import cfca.util.KeyUtil;
import cfca.util.SignatureUtil2;
import cfca.util.cipher.lib.JCrypto;
import cfca.util.cipher.lib.Session;
import cfca.x509.certificate.X509Cert;
import cfca.x509.certificate.X509CertHelper;
import cn.com.upcard.mgateway.channel.enums.ChannelResponseResult;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class SignatureUtils {
	private static Logger logger = LoggerFactory.getLogger(SignatureUtils.class);
	private static final String SUCCESS="SUCCESS";
	private static final String FAIL="FAIL";
	private static final String VALID_SIGN_ERROR = "返回参数验签不通过";
	

	private static Session session;

	static {
		try {
			JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
			session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
		} catch (PKIException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取签名
	 * @param context
	 * @return
	 */
	public static String getSign(String context) {

		String sign = "";
		try {
			JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
			Session session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
			SM2PrivateKey priKey = KeyUtil.getPrivateKeyFromSM2(SysPara.CMBC_SM2_PATH, SysPara.CMBC_SM2_KEY);
			sign = new String(new SignatureUtil2().p1SignMessage(Mechanism.SM3_SM2, context.getBytes("UTF-8"), priKey, session));
		} catch (PKIException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sign;
	}

	/**
	 * 签名
	 * 
	 * @param sign
	 * @param context
	 * @return
	 */
	public static String sign(String sign, String context) {
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		Gson gson = builder.create();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sign", sign);
		paramMap.put("body", context);
		String signInfo = gson.toJson(paramMap); // 待加密字符串
		return signInfo;
	}

	public static String encrypt(String signContext) {
		X509Cert cert = null;
		try {
			cert = X509CertHelper.parse(SysPara.CMBC_CER_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PKIException e) {
			
			e.printStackTrace();
		}
		X509Cert[] certs = { cert };
		byte[] encryptedData = null;
		try {
			encryptedData = EnvelopeUtil.envelopeMessage(signContext.getBytes("UTF-8"), Mechanism.SM4_CBC, certs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (PKIException e) {
			e.printStackTrace();
		}
		String encodeText = null;
		try {
			encodeText = new String(encryptedData, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeText;
	}
	
	public static String makeEncrypt(Map<String, String> map) {
		Map<String, String> params = JsonUtils.paraFilter(map);
		String context = JsonUtils.mapToJson(params);
		logger.info("context="+context);
		String sign = getSign(context);
		logger.info("sign="+sign);
		String signContext = sign(sign, context);
		logger.info("signContext="+signContext);
		String encryptContext = encrypt(signContext);
		logger.info("encryptContext="+encryptContext);
		return encryptContext;
	}
	
	
	public static Map<String, String> analysisResult(String data){
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isBlank(data)) {
			return null;
		}
		JSONObject  result=  JsonUtils.parseObject(data);
		
		String businessContext = result.getString("businessContext");
		if(StringUtils.isBlank(businessContext)) {
			map.put("gateReturnCode", result.getString("gateReturnCode"));
			map.put("gateReturnMessage", result.getString("gateReturnMessage"));
			map.put("gateReturnType", result.getString("gateReturnType"));
			return map;
		}
		
		String dncryptContext = dncrypt(businessContext);
		logger.info("dncryptContext="+dncryptContext);
		
		String signChkResult = signCheck(dncryptContext);
		if(SUCCESS.equals(signChkResult)) {
			map=JsonUtils.jsonToMap(dncryptContext);
			map.put("gateReturnType", result.getString("gateReturnType"));
		}else {
			map.put("gateReturnCode", ChannelResponseResult.SIGNATURE_ERROR.getCode());
			map.put("gateReturnMessage", VALID_SIGN_ERROR);
			map.put("gateReturnType", result.getString("gateReturnType"));
		}
		
		return map;
	}

	public static String signCheck(String dncryptContext) {
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		Map<String, Object> paraMap = gson.fromJson(dncryptContext, Map.class);
		String sign = paraMap.get("sign").toString();
		String body = paraMap.get("body").toString();
		boolean isSignOK = false;
		try {
			X509Cert cert = X509CertHelper.parse(SysPara.CMBC_CER_PATH);
			PublicKey pubKey = cert.getPublicKey();
			isSignOK = new SignatureUtil2().p1VerifyMessage(Mechanism.SM3_SM2, body.getBytes("UTF-8"),
					sign.getBytes(), pubKey, session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isSignOK) {
			return SUCCESS;
		} else {
			return FAIL;
		}
	
	}

	public static String dncrypt(String businessContext) {
		String decodeText = null;
		try {
			PrivateKey priKey = KeyUtil.getPrivateKeyFromSM2(SysPara.CMBC_SM2_PATH, SysPara.CMBC_SM2_KEY);
			X509Cert cert = CertUtil.getCertFromSM2(SysPara.CMBC_SM2_PATH);
			byte[] sourceData = EnvelopeUtil.openEvelopedMessage(businessContext.getBytes("UTF-8"), priKey, cert, session);
			decodeText = new String(sourceData, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decodeText;
	}
	 
}
