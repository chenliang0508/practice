package cn.com.upcard.mgateway.channel.bank.mybank.sdk.util;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.jcraft.jsch.Logger;

import cn.com.upcard.mgateway.channel.bank.mybank.common.MyBankServiceType;
import cn.com.upcard.mgateway.util.systemparam.SysPara;

public class PreNoticeXmlUtil extends XmlUtil {
    //封装报文
    public String format(Map<String,String> form,String function) throws DocumentException{
        //报文头
        Element headElement=formatHead(form);
        //报文体
        Element bodyElement = formatResponse(form);
        
        Document docRes=DocumentHelper.createDocument();
        Element rootElement=docRes.addElement("document");
        Element requestElement=rootElement.addElement("response").addAttribute("id", "response");
        requestElement.add(headElement);
        requestElement.add(bodyElement);
        
        String out=docRes.asXML();
        return out;
    }
    
    public Element formatResponse(Map<String,String> form) {
        Element bodyElementt=new DOMElement("body");
        Element responInfo=new DOMElement("RespInfo");
        responInfo.addElement("ResultStatus").setText(form.get("ResultStatus"));
        responInfo.addElement("ResultCode").setText(form.get("ResultCode"));
        responInfo.addElement("ResultMsg");
        bodyElementt.add(responInfo);
        return bodyElementt;
    }
    
	public String createNotifyErrorMsg() throws Exception {
		System.out.println("fff");
		Map<String, String> data = new HashMap<String, String>();
		data.put("Function", MyBankServiceType.H5_NOTICE_SERVICE.getService());
		data.put("ReqMsgId", UUID.randomUUID().toString());
		data.put("Version", "1.0.0");
		data.put("Appid", SysPara.MYBANK_APP_ID);
		data.put("ReqTime", new Timestamp(System.currentTimeMillis()).toString());
		data.put("InputCharset", "UTF-8");
		data.put("ResultStatus", "F");
		data.put("ResultCode", "0001");
		
		PreNoticeXmlUtil xmlUtils = new PreNoticeXmlUtil();
		String para = xmlUtils.format(data, MyBankServiceType.H5_NOTICE_SERVICE.getService());
		para = XmlSignUtil.signResponse(para);
		System.out.println(para);
		return para;
	}
	
	public String createNotifySuccessMsg() throws Exception {
		System.out.println("sss");
		Map<String, String> data = new HashMap<String, String>();
		data.put("Function", MyBankServiceType.H5_NOTICE_SERVICE.getService());
		data.put("ReqMsgId", UUID.randomUUID().toString());
		data.put("Version", "1.0.0");
		data.put("Appid", SysPara.MYBANK_APP_ID);
		data.put("RespTime", new Timestamp(System.currentTimeMillis()).toString());
		data.put("InputCharset", "UTF-8");
		data.put("ResultStatus", "S");
		data.put("ResultCode", "0000");
		
		String para = this.format(data, MyBankServiceType.H5_NOTICE_SERVICE.getService());
		para = XmlSignUtil.signResponse(para);
		System.out.println(para);
		return para;
	}
	
	public void printErrorMsg(OutputStream os) throws Exception {
		String para = this.createNotifyErrorMsg();
		try {
			os.write(para.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void printSuccessMsg(OutputStream os) throws Exception {
		String para = this.createNotifySuccessMsg();
		try {
			os.write(para.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
