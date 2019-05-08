package cn.com.upcard.mgateway.util;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class RandomStringCreatorTest {

	@Test
	public void testGenerateString() {
		System.out.println("开始生成订单号");
		Set<String> tradeNo = new HashSet<String>();
		for (int i = 0; i < 10000000; i++) {
			String t = RandomStringCreator.generateString(10);
			if (tradeNo.contains(t)) {
				System.out.println(t + "订单号重复");
			} else {
				tradeNo.add(t);
			}
		}
		System.out.println("结束生成订单号");
	}

}
