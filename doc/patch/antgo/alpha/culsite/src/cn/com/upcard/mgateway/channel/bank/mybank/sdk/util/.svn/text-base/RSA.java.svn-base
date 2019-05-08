/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package cn.com.upcard.mgateway.channel.bank.mybank.sdk.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;

/**
 * 
 * @author simon.xxm
 * @version $Id: RSA.java, v 0.1 2016年1月25日 上午10:34:58 simon.xxm Exp $
 */
public class RSA {
    /** 指定key的大小 */
    private static int          KEYSIZE        = 2048;
    private static final String encoding       = "UTF-8";
    private static final String RSA_ALGORITHM  = "RSA";
    public static final String RSA_publicKey  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvZprNztidrvAObGaomWTe8Ra+VYSIGGVsHZlPV9YKYH2A6pbcFnfk1gf+mI2TPDK0ID/0ET1KxIgsUiHlbqTpCzuoZdWnOhPmDNoCD39LAOrZ6w/DQaVPUCohwGCG6qX7MJ5shSVjr9Vxh79bLNAoK10BdXMUdSoE3we9TSEnf4zCPoMT1Wm6LCaca0m77k12K16IWfsdjE8V0p7IoiCv2AQHPPRlBq0ANIQoKNiwYUVcSgO73NOAXukuNBL42jAYsop8S3HgoNsH2IWgEyseLSqi2VwVRjqPLPpu0/zGBxljT4TVmKd7J8IuaWMtXKc5XBQBqKWotVVsojolK7NuQIDAQAB";
    public static final String RSA_privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC9mms3O2J2u8A5sZqiZZN7xFr5VhIgYZWwdmU9X1gpgfYDqltwWd+TWB/6YjZM8MrQgP/QRPUrEiCxSIeVupOkLO6hl1ac6E+YM2gIPf0sA6tnrD8NBpU9QKiHAYIbqpfswnmyFJWOv1XGHv1ss0CgrXQF1cxR1KgTfB71NISd/jMI+gxPVabosJpxrSbvuTXYrXohZ+x2MTxXSnsiiIK/YBAc89GUGrQA0hCgo2LBhRVxKA7vc04Be6S40EvjaMBiyinxLceCg2wfYhaATKx4tKqLZXBVGOo8s+m7T/MYHGWNPhNWYp3snwi5pYy1cpzlcFAGopai1VWyiOiUrs25AgMBAAECggEAG0LrrYpVUvsV3XXC5RyzwvTtm7Ibcxp02mV7kwJ1e6pbBBXnhdT2R4pBNtAOPKvWjXouzfRMSAAYQUVLcWTdO5rWSNeotXDVmO2zRJQdJcn1SDfE7QuIQ8FbOeYmnfG+XGVt+APrqRWrsIveJlXzseaeqwQdl5p6/Co5jUoa01hsp1ZilQJBzNHwnAtqiNG4rRNgcsxDp7Ei4CmoYEsNPV0gq5uHeYOJV9kGjggJmWg3Qrk9bI1qKJtXl/hAPzbgZh5mVIBzQ66k8jyQzL3a6RPU2oGxlOu9YlA4eLqIiAXVXyHj1YnljHsfSri+jYDo0Li21BjpVNv6CvO7Wah0sQKBgQD1Usic42Nv1nc5+IqOoC4bgQMqBR3YSoYOG+JuiIeim8os0AcRc6+1PBhwxzGpYaw7ec6P3TIa9kNzecYnHaqEDGSOai4KLdSNsM4bl3YO0IPME2TA8EwIkW7lvJ6qJD+rhkCrPNTlByeUgKfyMCZL8xeEpDVCV9picyiH5AwXnQKBgQDF2tdSlg9s6kt8ZuC5nJxeNmkmnvIt7TdSme9iJ1FG7gc65VtLCIKbkbi3ibfvZgvanrAvH29yShJXrgzvnrok1nFwvbhuWd/jpr8l/StHiaJndzMv7xoHdSmb/7KU+sC7vWLFDjtm8t6BuqkT7QLmmH1D/IdRJc6Bt1rF4OrpzQKBgQDTT9pzoT4uwFp0eczHq9vrXwZdtIiPnSm5j3VMZpgGjhDo5suf0blg8AHRaxMw5mwX0wUFUK/vH37cQeFYIiqVkaMwNO+xXua+obP3elB71Eoih/X6Z0HnA3a1tvIodg7N7VdY6I4tNSt3tBZ1+9mRBDSW0Wb63XfD45Pe8aUk1QKBgGJdSEuQux6E5P52Dyd4Su0z09cVkoVut+BjE3YS4f+HeyS2vkpxcq1xJwpod3+XljEcT689y6RgWvooV9oRaa3CxycryzNhj0OYtNNoKEoqjQkvY81i6+flQciCuQAEIim0IBSj3Lhz6ldIu6JiZNzL1wsj0wpu51nqFEDobyHJAoGBANdbyUnOBSk2r09ZAaL2T4SdH9EHGL2h9XEcRKdc16H0y15dQMRchLWrNpjvZS769pcqgJMsaCa7oNcawaoHsXoIivNBZaG0Y2gS/gBh2yM00ngKvuFayQPSvQjH0ospqLBd7lTPA2fttui1LHXBdhdGkHuUxEf8MEml6lD9CkKf";

    public static void main(String[] args) throws Exception {
//        Map<String, String> map = generateKeyPair();
//        System.out.println(map.get("publicKey"));
//        System.out.println(map.get("privateKey"));
        String sign = RSA.sign("rsa", RSA_privateKey);
        System.out.println(RSA.checkSign("rsa", sign, RSA_publicKey));
    }

    /**
     * 生成密钥对
     */
    public static Map<String, String> generateKeyPair() throws Exception {
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        /** 得到公钥 */
        Key publicKey = kp.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String pub = new String(Base64.encodeBase64(publicKeyBytes), encoding);
        /** 得到私钥 */
        Key privateKey = kp.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String pri = new String(Base64.encodeBase64(privateKeyBytes), encoding);

        Map<String, String> map = new HashMap<String, String>();
        map.put("publicKey", pub);
        map.put("privateKey", pri);
        RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
        BigInteger bint = rsp.getModulus();
        byte[] b = bint.toByteArray();
        byte[] deBase64Value = Base64.encodeBase64(b);
        String retValue = new String(deBase64Value);
        map.put("modulus", retValue);
        return map;
    }

    /**
     * 加密方法 source： 源数据
     */
    public static String encrypt(String source, String publicKey) throws Exception {
        Key key = getPublicKey(publicKey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        return new String(Base64.encodeBase64(b1), encoding);
    }

    /**
     * 解密算法 cryptograph:密文
     */
    public static String decrypt(String cryptograph, String privateKey) throws Exception {
        Key key = getPrivateKey(privateKey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] b1 = Base64.decodeBase64(cryptograph.getBytes());
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }

    /**
     * 得到公钥
     * 
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     * 
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String sign(String content, String privateKey) {
        String charset = encoding;
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey
                .getBytes()));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            Signature signature = Signature.getInstance("SHA256WithRSA");

            signature.initSign(priKey);
            signature.update(content.getBytes(charset));

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {

        }

        return null;
    }

    public static boolean checkSign(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSA");

            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));

            boolean bverify = signature.verify(Base64.decodeBase64(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
