package cn.com.upcard.mgateway.util;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import cn.com.upcard.mgateway.common.Restrict;

public class StringToolTest {

	@Test
	public void test() {
		String str = "怎么样？strlen中，中文是三个字节的长度，英文则是一个字节的长度！mb_strlen中，都被计算为一字节的长度！"
				+ "所以，我们有时候用substr来截取UTF-8中文字符串的时候，经常会出现乱码，就是这个原因了！\r\n"
				+ "下面提供一个截取UTF-8字符串的函数：";
		
		System.out.println(StringTool.subStrByByteSize(null, 10, 11));
	}

	@Test
	public void testEndoce() {
		int t = Integer.parseInt("000000000012");
		System.out.println(t);
	}
	
	@Test
	public void testWiteSpace() {
		String m = "11111111\n11111";
		Pattern p = Pattern.compile("\\s");
		Matcher matcher = p.matcher(m);
		if (matcher.find()) {
			System.out.println("ssss");
		}
	}
	
	@Test
	public void testOutTradeNo() {
		if ("jlll_".matches(Restrict.OUT_TRADE_NO)) {
			System.out.println("成功");
		}
	}
}
