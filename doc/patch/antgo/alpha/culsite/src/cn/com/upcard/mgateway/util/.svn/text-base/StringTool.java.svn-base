package cn.com.upcard.mgateway.util;

public class StringTool {
	/**
	 * <pre>
	 * 截取最多maxByteSize个字节的字符串,可以避免最后一个字符乱码。
	 * 
	 * 若字符串str中本身包含乱码,则不能保证子字符串不包换乱码。
	 * </pre>
	 * @param str 被截取的字符串，不能为空
	 * @param maxByteSize 子字符串的最大字节长度
	 * @return
	 */
	public static String subStrByByteSize(String str, int maxByteSize) {
		if (maxByteSize <= 0) {
			throw new RuntimeException("invalid maxByteSize :" + maxByteSize);
		}
		
		int realSubLength = Math.min(str.length(), maxByteSize);
		String tmp = str.substring(0, realSubLength);
		int byteRealSize = tmp.getBytes().length;
		
		int tailSize = 0;
		for(int i =  tmp.length() - 1; i >= 0 ; i--) {
			if (byteRealSize - tailSize <= maxByteSize) {
				return str.substring(0, i + 1);
			}
			tailSize += (str.charAt(i) + "").getBytes().length;
		}
		return "";
	}
	
	public static String subStrByByteSize(String str, int beginCharIndex, int maxByteSize) {
		if (beginCharIndex <= 0 || beginCharIndex >= str.length()) {
			throw new RuntimeException("invalid beginIndex :" + beginCharIndex);
		}
		
		return subStrByByteSize(str.substring(beginCharIndex), maxByteSize);
	}
}
