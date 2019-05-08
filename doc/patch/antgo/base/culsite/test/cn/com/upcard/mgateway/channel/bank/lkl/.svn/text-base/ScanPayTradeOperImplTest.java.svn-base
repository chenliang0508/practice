package cn.com.upcard.mgateway.channel.bank.lkl;

import org.junit.Test;

import cn.com.upcard.mgateway.model.ChannelRequestInfo;

public class ScanPayTradeOperImplTest {

	@Test
	public void test() {
		ChannelRequestInfo channelRequestInfo = new ChannelRequestInfo();
		channelRequestInfo.setMchId("YSSM");
		channelRequestInfo.setOutTradeNo("1221567897836542");
		channelRequestInfo.setTimeStart("20170911160822");
		channelRequestInfo.setOpDeviceId("822121090010008");
		channelRequestInfo.setDeviceInfo("91007751");
		channelRequestInfo.setTotalFee("1");
		channelRequestInfo.setAuthCode("285563908762791679"
				+ ""
				+ "");
		ScanPayTradeOperImpl s = new ScanPayTradeOperImpl();
		s.pay(channelRequestInfo);
	}

}
