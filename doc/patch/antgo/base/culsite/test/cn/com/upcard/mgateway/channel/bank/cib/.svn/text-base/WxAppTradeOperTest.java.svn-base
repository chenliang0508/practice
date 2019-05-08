package cn.com.upcard.mgateway.channel.bank.cib;

import org.junit.Test;

import cn.com.upcard.mgateway.model.ChannelRequestInfo;

public class WxAppTradeOperTest {

	/*
	<xml>
	    <charset>UTF-8</charset>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503285570048</nonce_str>
	    <services>pay.alipay.app|pay.alipay.micropay|pay.alipay.native|pay.alipay.wappay|pay.jdpay.jspay|pay.jdpay.micropay|pay.jdpay.native|pay.qq.micropay|pay.qq.jspay|pay.weixin.app|pay.weixin.jspay|pay.weixin.micropay|pay.weixin.native|pay.weixin.scancode|trade.urovo.pos|wft.rns.smzy|wft.rns.tft</services>
	    <sign>7A0E50B46D5316D005AFB7642FD0BCF0</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <token_id>1890c02eb555ff1bf70c3f026e610f40b</token_id>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void payTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("755437000006");
		bean.setOutTradeNo("zdwxapppay009");
		bean.setBody("1111");
		bean.setTotalFee("100");
		bean.setReqIp("127.0.0.1");
		bean.setSubAppid("wx2a5538052969956e");
		bean.setNotifyUrl("http://zhangwei.dev.swiftpass.cn/native-pay/testPayResult");
		bean.setPrivateKey("7daa4babae15ae17eee90c9e");

		WxAppTradeOperImpl impl = new WxAppTradeOperImpl();
		impl.pay(bean);
	}

}
