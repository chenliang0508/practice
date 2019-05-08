package cn.com.upcard.mgateway.util;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <pre>
 * 随机字符串生成器
 * 
 * </pre>
 * @author huatingzhou
 * @version 1.0
 */
public class RandomStringCreator {
	private static Logger logger = LoggerFactory.getLogger(RandomStringCreator.class);
	
	private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
	
	public static String generateString(int count) {
		if (count >= 18) {
			count = 18;
		}
		if (count < 6) {
			logger.warn("count is:" + count);
			return null;
		}
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < count; i++) {
			String str = uuid.substring(i * (32 / count), i * (32 / count) + (32 / count));
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}
	
	public static void main(String[] str) {
		String s = RandomStringCreator.generateString(16);
		logger.info(s);
	}
}
