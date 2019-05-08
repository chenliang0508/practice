package cn.com.upcard.mgateway.channel.bank.lkl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import cn.com.upcard.mgateway.model.ChannelRequestInfo;
import cn.com.upcard.mgateway.util.DateUtil;
/**
 * <ROOT><responseCode>0100C0</responseCode><reqLogNo>1506316796675</reqLogNo><mercId>822121090010008</mercId><txnTm>0925132016</txnTm><txnAmt>1</txnAmt><merOrderNo>17092550000256</merOrderNo><payChlDesc>支付宝钱包</payChlDesc><message>付款码无效或等待用户输入密码</message><errCode>10003</errCode><MAC>00000000000000000000000000000000</MAC></ROOT>
 *	{mercid=822121090010008, errcode=10003, responsecode=0100C0, merorderno=17092550000256, reqlogno=1506316796675, txntm=0925132016, message=付款码无效或等待用户输入密码, txnamt=1, mac=00000000000000000000000000000000, paychldesc=支付宝钱包}
 *
 *<ROOT><responseCode>000000</responseCode><reqLogNo>1506317030249</reqLogNo><mercId>822121090010008</mercId><userId>2088912090935620</userId><txnTm>0925132410</txnTm><txnAmt>1</txnAmt><payOrderId>2017092521001004620299164870</payOrderId><merOrderNo>17092550000261</merOrderNo><payChlDesc>支付宝钱包</payChlDesc><mrkInfo/><message>OK</message><MAC>619f51685a03bd053623ff1c2219382d9cd3a2eb</MAC></ROOT>
{mercid=822121090010008, mrkinfo=, responsecode=000000, merorderno=17092550000261, reqlogno=1506317030249, txntm=0925132410, payorderid=2017092521001004620299164870, message=OK, userid=2088912090935620, txnamt=1, mac=619f51685a03bd053623ff1c2219382d9cd3a2eb, paychldesc=支付宝钱包}
 * @author huatingzhou
 *
 */
@RunWith(Parameterized.class)
public class MirocPayTest {
	private String reqLogNo;
	private String payChlTyp;
	private String mercId;
	private String termId;
	private String txnAmt;
	private String authCode;
	//订单描述信息和优惠商品信息占时不测试
	//sub_appid子商户appId占时不测试
	
	public MirocPayTest(String reqLogNo, String payChlTyp, String mercId, String termId, String txnAmt, String authCode) {
		this.reqLogNo = reqLogNo;
		this.payChlTyp = payChlTyp;
		this.mercId = mercId;
		this.termId = termId;
		this.txnAmt = txnAmt;
		this.authCode = authCode;
	}
	
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] d = {
			//请求号,                      支付渠道类型，		                            商户号，                                终端号，             金额，	条码
			//支付宝正常交易
//			{System.currentTimeMillis() + "", PayChannelType.ALIPAY.name(), "822121090010008", "91007751", "1", "289655122854100891"},	
			//微信支付
			{System.currentTimeMillis() + "", PayChannelType.WECHAT.name(), "822121090010008", "91007751", "2", "134506071072129528"
					+ ""}
		};
		return Arrays.asList(d);
	}
	
	@Test
	public void testPay() {
		ChannelRequestInfo requestInfo = new ChannelRequestInfo();
		requestInfo.setMchId(this.mercId);
		requestInfo.setDeviceInfo(this.termId);
		requestInfo.setOutTradeNo(this.reqLogNo);
		requestInfo.setAuthCode(this.authCode);
		requestInfo.setChannelType(this.payChlTyp);
		requestInfo.setTotalFee(this.txnAmt);
		requestInfo.setTimeStart(DateUtil.format2(new Date(), DateUtil.yyyyMMddHHmmss));
		LKLPayTradeOperDefaultImpl payOper = new LKLPayTradeOperDefaultImpl();
		payOper.pay(requestInfo);
	}
}
