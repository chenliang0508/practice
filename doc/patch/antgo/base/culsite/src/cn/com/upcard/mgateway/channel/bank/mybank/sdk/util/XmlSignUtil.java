/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package cn.com.upcard.mgateway.channel.bank.mybank.sdk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.security.signature.XMLSignature;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.com.upcard.mgateway.channel.bank.mybank.sdk.base.HttpsMain;

/**
 * 进行签名验签的测试
 * 
 * @author jin.xie
 * @version $Id: XmlSignTest.java, v 0.1 2016年2月1日 上午10:51:39 jin.xie Exp $
 */
public class XmlSignUtil {
    /** 公钥 RSA */
    public static final String RSA_publicKey  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvZprNztidrvAObGaomWTe8Ra+VYSIGGVsHZlPV9YKYH2A6pbcFnfk1gf+mI2TPDK0ID/0ET1KxIgsUiHlbqTpCzuoZdWnOhPmDNoCD39LAOrZ6w/DQaVPUCohwGCG6qX7MJ5shSVjr9Vxh79bLNAoK10BdXMUdSoE3we9TSEnf4zCPoMT1Wm6LCaca0m77k12K16IWfsdjE8V0p7IoiCv2AQHPPRlBq0ANIQoKNiwYUVcSgO73NOAXukuNBL42jAYsop8S3HgoNsH2IWgEyseLSqi2VwVRjqPLPpu0/zGBxljT4TVmKd7J8IuaWMtXKc5XBQBqKWotVVsojolK7NuQIDAQAB";
    /** 私钥 RSA */
    public static final String RSA_privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQChl5wlGApywkJGMstTCdvYKieNj28rUpuEPAB2m50Cen0leNBjf4zniX37dlbAaXK8MxqgW8h52MkWNV3f2GS/bqf0TmtxxkAxBWCy+eef4axyA3iTbdSbtnzm/Vqqfp9BWuaou5+5xAMDtOA/aiR74IyEJN/voZ4L9V7h9eangQwt1/cXPWnw8r3CTXis/hhBbn5/rCVuE08+/26Drv18VXE73MftpUc0sXoVOn9r0lpKlhTmTjfbun+geBSmPi5I3ZWW1gHMhmGzbr7BkxOCNJuoztfo3IgJwod7aVn0QelkGqxMTmLx6SiARLAeWO+83mSTFcsfqHC6q6I1WoQjAgMBAAECggEAMnCpu+ZyNHowx8rJLuVv61zVNdnlAKfrhW5ZUqymaQSlFdJNchF5gTioy5qX373Ko7ZsBExCGs6xji4gXGQmpp667IaiAUS5+tNL6PIa/AwskPZGZ/arm+Ntv4isCXY27eabA2Z1qD3oFvsZ2JGgS86+7ey4vgs7003HTU89rWEa0do3YTzLbmmTk5adukYKgQHwzO2zhJ8J2nip+oqdckvzEh3PkqrSaJehTHvBbrZ7Ym2UR8DIMg4mOVMeSRnLk7scxPBy8D/3JaAf/+haH5StiseBorAEWzL0DSrCaym8pkzIAUHL9SDu/rxymivx1DMb78kl+z0zSuI/T/ungQKBgQDQVYtbaCjcKZciE0Ma5/Ogd/1X3mxI24kTr0CiTNghNd6HuAHjWjCBHEzAy0tveLJFepEBbaMKL1lPLR/rET0hDhZrbiEuy2+ZaIpO8fIN+nSoIFLiEtW+Id0OrQoHITHf31vzmHxnwSFzo5oKEaZscIZf1LJAyg+/vga6uwau4wKBgQDGkFFl6vhp8stFbBzofUaTYTgqD3RXB+oybrBwPvDjzBwRa+rKI+0LoXWbSovdTKoY+xst9Z7nG9Afc1cQ6gLVLC2PmOeVMEfbiCH5GjIiWdnKkaTWqT45tCWacUH1TtMfATRTI0xED5G08PJDJ+ItzObG81wY8I5Cr90aNPmZwQKBgBAQGG+Sx1u6RTHWZF0lty4PMlRdDOJNMvN2lrgszpk46xBxyot5/7VktjJDOmy81Jnwyk9e4aw+XFdzdjuMl7b9vBLKafqOquscGVXe57fWMLppy5oss5g2SjIAe35zO8I0GYXdovHo/ShKW+0c0UUZI/MtlhTSv4YcI66wDuLbAoGAfqOwpnXT9Y6aQi6PyV+M0ZZhwcGVi6RKo9ugwBYEgOS+ygWv+zgqiQ5y+ZXz2jJ5EfGlBgzvSaqddFGVp/33zImejUUR/j6KteL+9+bTLsFrfByjqxzJyPeyO+wcCx443D9iZdvPWmErpuE9QcOAJ4HVdfHkPSu/L50GQR6kSoECgYBvK8GLUUvn7lzO+s8qujIDu9eZJBRvuZEnsTJEj7bQe4oS/W3+U/HtJsjgWlIe7OUxkuBDUOMLG/IKxt5bCnNQizfDw5lg6EfEUbnMgjbp7Hfy4hJUI8ZBCTmS7xx7H1tslYknINk9v5fmuhAuSNJQPFy3yJykYxJn+k2TfMA38g==";

    /** 网商银行测试环境公钥 RSA */
    public static String bank_RSA_publicKey  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOb4B1dnwONcW0RoJMa0IOq3O6jiqnTGLUpxEw2xJg+c7wsb6DBy5CAoR0w2ZjZ/BjKxGIQ+DoDg3NsHJeyuEjNF0/Ro/R5xVpFC5z4cBVSC2/gddz4a1EoGDJewML/Iv0yIw7ylB86++h23nRd079c5S9RZXurBfnLW2Srhqk2QIDAQAB";
    /**
     * 签名- XML
     * 
     * @throws Exception
     */
    public static String sign(String xmlContent) throws Exception {
        Document doc = parseDocumentByString(xmlContent);
        PrivateKey privateKey = SignatureUtils.getPrivateKey(HttpsMain.mybank_RSA_privateKey);
        String result = SignatureUtils.signXmlElement(privateKey, doc, "request",
            XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256, 2);
        return result;
    }

    /**
     * 验签 - XML
     * 
     * @throws Exception
     */
    public static boolean verify(String xmlContent) throws Exception {
        Document doc = parseDocumentByString(xmlContent);
        PublicKey publicKey = SignatureUtils.getPublicKey(HttpsMain.mybank_RSA_publicKey);

        return SignatureUtils.verifyXmlElement(publicKey, doc);
    }

    /**
     * 解析XML
     * 
     * @param xmlContent
     * @return
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    private static Document parseDocumentByString(String xmlContent) throws SAXException,
                                                                    IOException, Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);// 否则无法识别namespace
        return factory.newDocumentBuilder().parse(new InputSource(new StringReader(xmlContent)));
    }
    
    public static void main(String[] args) throws Exception{
        bank_RSA_publicKey  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg0HDiStmHDTHOAptH5Jlp4sg03Czir/My4vvFK/7Un/zyuyTa3aHqav4+GqPhGJcIGIiLVuP/tCCEU4Edl3LC0bNxUBdnPv7k3HDoonWmqD/EhBXKe/GD/Dwrn90VfP1s314ykj8Z2T06l3LYb5F44L8JOgVrqJOe2FMXvsEt6sik2jnMdq2rJLeda1QLQMKnla4t2wVK1DoC0eu+ry3Oc2BWhYwU1bwWaX71mROH0Q3jXj3FFLdGp2trrnkKnQzNfGHjRChxTWl3CgiWbFd/Ejgp0/cpovq3cAJtxD7HdtwOibiI9u6owyWFM6C2pMjsuI23WofRyD9UQoU0UToiwIDAQAB";
        File file = new File("d:/sign.txt");
        InputStream in  = new FileInputStream(file);
        byte[] b = new byte[in.available()];
        in.read(b);
        String xml =new String(b);
        in.close();
       boolean result = verify(xml);
       System.out.println(result);
    }

    /**
     * 签名- XML
     * 
     * @throws Exception
     */
    public static String signResponse(String xmlContent) throws Exception {
        Document doc = parseDocumentByString(xmlContent);
        PrivateKey privateKey = SignatureUtils.getPrivateKey(HttpsMain.mybank_RSA_privateKey);
        String result = SignatureUtils.signXmlElement(privateKey, doc, "response",
            XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256, 2);
        return result;
    }
}