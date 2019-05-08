package cn.com.upcard.mgateway.channel.bank.cib;

import org.junit.Test;

import cn.com.upcard.mgateway.model.ChannelRequestInfo;

public class WxNativeTradeOperTests {

	/*	
    <xml>
	    <appid>wx1f87d44db95cba7a</appid>
	    <charset>UTF-8</charset>
	    <code_img_url>https://pay.swiftpass.cn/pay/qrcode?uuid=weixin%3A%2F%2Fwxpay%2Fbizpayurl%3Fpr%3DTh3HKJI</code_img_url>
	    <code_url>weixin://wxpay/bizpayurl?pr=Th3HKJI</code_url>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503037567383</nonce_str>
	    <result_code>0</result_code>
	    <sign>B2F9705D9166DB020C764E5EA11CC635</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <uuid>21b9c35b828d6b53890070918a885aeb3</uuid>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void payTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setOutTradeNo("zdwxnativepay012");
		bean.setBody("1111");
		bean.setTotalFee("1");
		bean.setReqIp("127.0.0.1");
		bean.setNotifyUrl("https://www.chinagiftcard.cn/mgateway/notifytest/relaythemessage");
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");

		WxNativeTradeOperImpl impl = new WxNativeTradeOperImpl();
		System.out.println(impl.pay(bean));
	}

	/*
	<xml>
	    <appid>wx1f87d44db95cba7a</appid>
	    <bank_type>CFT</bank_type>
	    <charset>UTF-8</charset>
	    <fee_type>CNY</fee_type>
	    <is_subscribe>N</is_subscribe>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503038261191</nonce_str>
	    <openid>oywgtuL6LdCrrgvkwUTk0qJr5SvI</openid>
	    <out_trade_no>zdpay001</out_trade_no>
	    <out_transaction_id>4009212001201708186883873550</out_transaction_id>
	    <result_code>0</result_code>
	    <sign>6A55BB003B97E911BF77910325CC23BC</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <sub_appid>wxce38685bc050ef82</sub_appid>
	    <sub_is_subscribe>N</sub_is_subscribe>
	    <sub_openid>oHmbkt6QpTx-k9qfc763qAATUB8s</sub_openid>
	    <time_end>20170818143116</time_end>
	    <total_fee>100</total_fee>
	    <trade_state>SUCCESS</trade_state>
	    <trade_type>pay.weixin.native</trade_type>
	    <transaction_id>7551000001201708185231404862</transaction_id>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void tradeQueryTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setOutTradeNo("zdwxnativepay001");
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");

		WxNativeTradeOperImpl impl = new WxNativeTradeOperImpl();
		System.out.println(impl.tradeQuery(bean));
	}

	/*
	<xml>
	    <charset>UTF-8</charset>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503039241674</nonce_str>
	    <out_refund_no>zdrefund001</out_refund_no>
	    <out_trade_no>zdpay001</out_trade_no>
	    <out_transaction_id>4009212001201708186883873550</out_transaction_id>
	    <refund_channel>ORIGINAL</refund_channel>
	    <refund_fee>100</refund_fee>
	    <refund_id>7551000001201708184287530120</refund_id>
	    <result_code>0</result_code>
	    <sign>02499EC18F1337099308EA33C4F9EC98</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <trade_type>pay.weixin.native</trade_type>
	    <transaction_id>7551000001201708185231404862</transaction_id>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void refundTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setOutTradeNo("zdwxnativepay001");
		bean.setOutRefundNo("zdnativepayrefund002");
		bean.setTotalFee("100");
		bean.setRefundFee("20");
		bean.setOpUserId("7551000001");
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");

		WxNativeTradeOperImpl impl = new WxNativeTradeOperImpl();
		impl.refund(bean);
	}

	/*
	<xml>
	    <appid>wx1f87d44db95cba7a</appid>
	    <charset>UTF-8</charset>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503039624919</nonce_str>
	    <out_refund_id_0>50000603982017081801581555773</out_refund_id_0>
	    <out_refund_no_0>zdrefund001</out_refund_no_0>
	    <out_trade_no>zdpay001</out_trade_no>
	    <out_transaction_id>4009212001201708186883873550</out_transaction_id>
	    <refund_channel_0>ORIGINAL</refund_channel_0>
	    <refund_count>1</refund_count>
	    <refund_fee_0>100</refund_fee_0>
	    <refund_id_0>7551000001201708184287530120</refund_id_0>
	    <refund_status_0>SUCCESS</refund_status_0>
	    <refund_time_0>20170818145403</refund_time_0>
	    <result_code>0</result_code>
	    <sign>239277817FC7B6C909DF9B4AEFFBB595</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <trade_type>pay.weixin.native</trade_type>
	    <transaction_id>7551000001201708185231404862</transaction_id>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void refundQueryTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setTransactionId("4009212001201709050311649670");
		bean.setOutTradeNo("zdwxnativepay001");
		bean.setOutRefundNo("zdnativepayrefund002");
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");

		WxNativeTradeOperImpl impl = new WxNativeTradeOperImpl();
		impl.refundQuery(bean);
	}

	/*	
	<xml>
	    <appid>wx1f87d44db95cba7a</appid>
	    <charset>UTF-8</charset>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503283782330</nonce_str>
	    <result_code>0</result_code>
	    <sign>A84E9E02DC3F29FB881AC79DD9818115</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void closeTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setOutTradeNo("zdnativepay007");
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");

		WxNativeTradeOperImpl impl = new WxNativeTradeOperImpl();
		impl.close(bean);
	}

}
