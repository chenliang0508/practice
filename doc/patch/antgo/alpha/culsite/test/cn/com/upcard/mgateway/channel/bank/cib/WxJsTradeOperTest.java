package cn.com.upcard.mgateway.channel.bank.cib;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

import cn.com.upcard.mgateway.model.ChannelRequestInfo;

public class WxJsTradeOperTest {

	/*
	<xml>
	    <appid>wx1f87d44db95cba7a</appid>
	    <charset>UTF-8</charset>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503282185559</nonce_str>
	    <result_code>0</result_code>
	    <sign>07299664321E5078CD75DB54009C94AD</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <token_id>287931b246b5eca5a9d472f9a193545f7</token_id>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void payTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("101530136432");
		bean.setOutTradeNo("170921CIBpnddkfacmjeaedsc2");
		bean.setBody("sssddd");
		bean.setSubOpenid("o1ChZsxBCPs3xTjdnJxzC6OjKnGo");
		bean.setSubAppid("wxe933db7eee5e92b8");
		bean.setTotalFee("1");
		bean.setReqIp("127.0.0.1");
		try {
			bean.setNotifyUrl("https://www.chinagiftcard.cn/mgateway/gateway?" + URLEncoder.encode("service=unified.qrcode.returnBack&mgateway_order_no=170921CIBpaihblbkballediejn&mch_id=17091311000000&borwserType=micromessenger&sign=c0f135c6ce9aea525a0fa316b1d8dfc2", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");
		bean.setIsRaw("1");
		WxJsTradeOperImpl impl = new WxJsTradeOperImpl();
		impl.pay(bean);
	}

	@Test
	public void payTest2() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setOutTradeNo("170921CIBpnddkfacmsaedsc2");
		bean.setBody("sssddd");
		bean.setSubOpenid("");
		bean.setSubAppid("");
		bean.setTotalFee("1");
		bean.setReqIp("127.0.0.1");
		try {
			bean.setNotifyUrl("https://www.chinagiftcard.cn/mgateway/gateway?" + URLEncoder.encode("service=unified.qrcode.returnBack&mgateway_order_no=170921CIBpaihblbkballediejn&mch_id=17091311000000&borwserType=micromessenger&sign=c0f135c6ce9aea525a0fa316b1d8dfc2", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");
		bean.setIsRaw("1");
		WxJsTradeOperImpl impl = new WxJsTradeOperImpl();
		impl.pay(bean);
	}
	
	/*	
	<xml>
	    <appid>wx1f87d44db95cba7a</appid>
	    <bank_type>CFT</bank_type>
	    <charset>UTF-8</charset>
	    <fee_type>CNY</fee_type>
	    <is_subscribe>N</is_subscribe>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503282368830</nonce_str>
	    <openid>oywgtuL6LdCrrgvkwUTk0qJr5SvI</openid>
	    <out_trade_no>zdwxjspay002</out_trade_no>
	    <out_transaction_id>4009212001201708217399410240</out_transaction_id>
	    <result_code>0</result_code>
	    <sign>630960BCA033FBCF983E57C6D4154479</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <sub_appid>wxce38685bc050ef82</sub_appid>
	    <sub_is_subscribe>N</sub_is_subscribe>
	    <sub_openid>oHmbkt6QpTx-k9qfc763qAATUB8s</sub_openid>
	    <time_end>20170821102559</time_end>
	    <total_fee>100</total_fee>
	    <trade_state>SUCCESS</trade_state>
	    <trade_type>pay.weixin.jspay</trade_type>
	    <transaction_id>7551000001201708217267979366</transaction_id>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void tradeQueryTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setOutTradeNo("zdwxjspay002");
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");

		WxJsTradeOperImpl impl = new WxJsTradeOperImpl();
		impl.tradeQuery(bean);
	}

	/*
	<xml>
	    <charset>UTF-8</charset>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503282543282</nonce_str>
	    <out_refund_no>zdwxjsrefund002</out_refund_no>
	    <out_trade_no>zdwxjspay002</out_trade_no>
	    <out_transaction_id>4009212001201708217399410240</out_transaction_id>
	    <refund_channel>ORIGINAL</refund_channel>
	    <refund_fee>100</refund_fee>
	    <refund_id>7551000001201708215140314145</refund_id>
	    <result_code>0</result_code>
	    <sign>2D85560F434D5D5FB61BD76185F50E82</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <trade_type>pay.weixin.jspay</trade_type>
	    <transaction_id>7551000001201708217267979366</transaction_id>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void refundTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setOutTradeNo("zdwxjspay002");
		bean.setOutRefundNo("zdwxjsrefund002");
		bean.setTotalFee("100");
		bean.setRefundFee("100");
		bean.setOpUserId("7551000001");
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");

		WxJsTradeOperImpl impl = new WxJsTradeOperImpl();
		impl.refund(bean);
	}
	
	/*
	<xml>
	    <appid>wx1f87d44db95cba7a</appid>
	    <charset>UTF-8</charset>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503283628680</nonce_str>
	    <out_refund_id_0>50000003842017082101597889352</out_refund_id_0>
	    <out_refund_no_0>zdwxjsrefund002</out_refund_no_0>
	    <out_trade_no>zdwxjspay002</out_trade_no>
	    <out_transaction_id>4009212001201708217399410240</out_transaction_id>
	    <refund_channel_0>ORIGINAL</refund_channel_0>
	    <refund_count>1</refund_count>
	    <refund_fee_0>100</refund_fee_0>
	    <refund_id_0>7551000001201708215140314145</refund_id_0>
	    <refund_status_0>SUCCESS</refund_status_0>
	    <refund_time_0>20170821102907</refund_time_0>
	    <result_code>0</result_code>
	    <sign>48BADDD93865587E1E7C586CB54471E0</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <trade_type>pay.weixin.jspay</trade_type>
	    <transaction_id>7551000001201708217267979366</transaction_id>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void refundQueryTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setRefundId("7551000001201708215140314145");
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");

		WxJsTradeOperImpl impl = new WxJsTradeOperImpl();
		impl.refundQuery(bean);
	}

	/*
	<xml>
	    <appid>wx1f87d44db95cba7a</appid>
	    <charset>UTF-8</charset>
	    <mch_id>7551000001</mch_id>
	    <nonce_str>1503283877852</nonce_str>
	    <result_code>0</result_code>
	    <sign>2D1EB2D63791C7B21CD7631568E6F224</sign>
	    <sign_type>MD5</sign_type>
	    <status>0</status>
	    <version>2.0</version>
	</xml>
	*/
	@Test
	public void closeTest() {
		ChannelRequestInfo bean = new ChannelRequestInfo();
		bean.setMchId("7551000001");
		bean.setOutTradeNo("zdwxjspay003");
		bean.setPrivateKey("9d101c97133837e13dde2d32a5054abb");

		WxJsTradeOperImpl impl = new WxJsTradeOperImpl();
		impl.close(bean);
	}

}
