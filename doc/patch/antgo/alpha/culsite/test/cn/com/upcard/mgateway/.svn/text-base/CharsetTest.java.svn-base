package cn.com.upcard.mgateway;

import java.io.FilterInputStream;
import java.util.Arrays;

import org.junit.Test;

public class CharsetTest {
	
	@Test
	public void testBytes() {
		String utfLog = "我的dfgdg也出现fgfhfghfgh了这个问题，我换了dfdfg低一点的版本就好了，我用的是4.4的。";
		byte[] logBytes = utfLog.getBytes();
		if (logBytes.length > 30) {
			System.out.println(new String(Arrays.copyOfRange(logBytes, 0, 10 - 1)));
			System.out.println(new String(Arrays.copyOfRange(logBytes, 10, 2 * 10 - 1)));
			System.out.println(new String(Arrays.copyOfRange(logBytes, 2 * 10, 30)));
		} else if (logBytes.length > 20 && logBytes.length <= 30) {
			System.out.println(new String(Arrays.copyOfRange(logBytes, 0, 10 - 1)));
			System.out.println(new String(Arrays.copyOfRange(logBytes, 10, 2 * 10 - 1)));
			System.out.println(new String(Arrays.copyOfRange(logBytes, 2 * 10, logBytes.length)));
		} else if (logBytes.length > 10 && logBytes.length <= 20) {
			System.out.println(new String(Arrays.copyOfRange(logBytes, 0, 10 - 1)));
			System.out.println(new String(Arrays.copyOfRange(logBytes, 10, logBytes.length)));
		} else {
			System.out.println(utfLog);
		}
		
	}
}
