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
@RunWith(Parameterized.class)
public class MirocRefundTest {

	private String refundNo;
	private String ornReqLogNo;
	private String payChlTyp;
	private String mercId;
	private String termId;
	private String txnAmt;
	//订单描述信息和优惠商品信息占时不测试
	//sub_appid子商户appId占时不测试
	
	public MirocRefundTest(String refundNo, String ornReqLogNo, String payChlTyp, String mercId, String termId, String txnAmt) {
		this.refundNo = refundNo;
		this.payChlTyp = payChlTyp;
		this.mercId = mercId;
		this.termId = termId;
		this.ornReqLogNo = ornReqLogNo;
		this.txnAmt = txnAmt;
	}
	
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] d = {
			//请求号,                      原交易请求号 ,           支付渠道类型，		                            商户号，                                终端号，             金额，	条码
			//支付宝正常交易
			{System.currentTimeMillis() + "", "1506317030249", PayChannelType.ALIPAY.name(), "822121090010008", "91007751", "1"}	
		};
		return Arrays.asList(d);
	}
	
	@Test
	public void testPay() {
		ChannelRequestInfo requestInfo = new ChannelRequestInfo();
		requestInfo.setMchId(this.mercId);
		requestInfo.setDeviceInfo(this.termId);
		requestInfo.setOutTradeNo(this.ornReqLogNo);
		requestInfo.setOutRefundNo(this.refundNo);
		requestInfo.setChannelType(this.payChlTyp);
		requestInfo.setRefundFee(this.txnAmt);
		requestInfo.setTimeStart(DateUtil.format2(new Date(), DateUtil.yyyyMMddHHmmss));
		LKLPayTradeOperDefaultImpl payOper = new LKLPayTradeOperDefaultImpl();
		payOper.refund(requestInfo);
	}
}
