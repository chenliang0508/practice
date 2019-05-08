package cn.com.upcard.mgateway.controller;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MicroPayTest extends ControllerTest {
	private static final String service = "unified.trade.micropay";
	private static final String signType = "md5";
	private static final String returnType = "json";
	
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] data = {
				{"1507700120950"}
		};
		return Arrays.asList(data);
	}
	
//	public MicroPayTest
}
