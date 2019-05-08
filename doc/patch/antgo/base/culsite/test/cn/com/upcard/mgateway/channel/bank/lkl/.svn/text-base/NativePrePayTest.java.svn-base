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
public class NativePrePayTest {

	private String reqLogNo;
	private String payChlTyp;
	private String mercId;
	private String termId;
	private String txnAmt;
	private String tradeType;
	private String notifyUrl;
	//订单描述信息和优惠商品信息占时不测试
	//sub_appid子商户appId占时不测试
	
	public NativePrePayTest(String reqLogNo, String payChlTyp, String tradeType, String mercId, String termId, String txnAmt, String notifyUrl) {
		this.reqLogNo = reqLogNo;
		this.payChlTyp = payChlTyp;
		this.mercId = mercId;
		this.termId = termId;
		this.txnAmt = txnAmt;
		this.tradeType = tradeType;
		this.notifyUrl = notifyUrl;
	}
	
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] d = {
			//请求号,                      支付渠道类型，		                            商户号，                                终端号，             金额，	条码
			//支付宝正常交易
			{System.currentTimeMillis() + "", PayChannelType.WECHAT.name(), "JSAPI", "822121090010008", "91007751", "1", "https://www.chinagiftcard.cn/mgateway/gateway/lkl"},	
		};
		return Arrays.asList(d);
	}
	
	@Test
	public void testPay() {
		ChannelRequestInfo requestInfo = new ChannelRequestInfo();
		requestInfo.setMchId(this.mercId);
		requestInfo.setDeviceInfo(this.termId);
		requestInfo.setOutTradeNo(this.reqLogNo);
		requestInfo.setChannelType(this.payChlTyp);
		requestInfo.setTotalFee(this.txnAmt);
		requestInfo.setPayType(this.tradeType);
		requestInfo.setNotifyUrl(this.notifyUrl);
		requestInfo.setSubAppid("wxe933db7eee5e92b8");
		requestInfo.setThirdBuyerId("o1ChZsxBCPs3xTjdnJxzC6OjKnGo");
		requestInfo.setTimeStart(DateUtil.format2(new Date(), DateUtil.yyyyMMddHHmmss));
		LKLPayTradeOperDefaultImpl payOper = new LKLPayTradeOperDefaultImpl();
		payOper.prePay(requestInfo);
	}

}
