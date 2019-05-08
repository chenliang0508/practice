package cn.com.upcard.mgateway.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class RandomStringUtilsTest {
	private static Set<String> s = new  HashSet<String>();
	@Test
	public void testRandom() {
		int count = 100 * 10000;
		for (int i =  0; i < count; i++) {
//			String orderNo = "20170915" + "1234" + "2545514244114" + RandomStringUtils.randomNumeric(6);;
			if(!s.add(generate8Uuid(2))) {
				System.out.println("出现重复");
			}
		}
	}
	
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };
	
	public static String generate8Uuid(int count) {
		if (count >= 18) {
			count = 18;
		}
		if (count < 5) {
			System.out.println("count is:" + count);
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

public static void main(String[] args) {
	int count = 10 * 10000;
	long begin = System.currentTimeMillis();
	System.out.println("begin -->" + begin);
	for (int i =  0; i < count; i++) {
//		String orderNo = "20170915" + "1234" + "2545514244114" + RandomStringUtils.randomNumeric(6);;
		if(!s.add(generate8Uuid(18))) {
			System.out.println("出现重复");
		}
	}
	System.out.println(System.currentTimeMillis() - begin);
}
	
	@Test
	public void testMac() {
		System.out.println(this.createOrderNo("17091311000001"));
	}
	private synchronized String createTradeNo() {
		// yyyyMMdd + 商户号后四位 + 12位mac + 6位随机数
//		String date = DateUtil.formatCompactDate(DateUtil.now());
//		String commonNo = commonUnionNo;
//		if (commonUnionNo.length() > 4) {
//			commonNo = commonUnionNo.substring(commonUnionNo.length() - 4, commonUnionNo.length());
//		}
//		String random = RandomStringUtils.randomNumeric(6);
//		if(StringUtils.isEmpty(localMac)) {
//			localMac = getLocalMac();
//		}
		String tradeNo = "20170915" + "1234" + "2545514244114" + RandomStringUtils.randomNumeric(6);
//		logger.info("产生的系统流水号tradeNo：" + tradeNo);
		
//		this.
		return tradeNo;
	}
	
	private synchronized String createOrderNo(String commonUnionNo) {
		// yyyyMMdd + 商户号后四位 + 12位mac + 6位随机数
		String date = DateUtil.formatCompactDate(DateUtil.now());
		String commonNo = commonUnionNo;
		if (commonUnionNo.length() > 4) {
			commonNo = commonUnionNo.substring(commonUnionNo.length() - 4, commonUnionNo.length());
		}
		String random = RandomStringUtils.randomNumeric(6);
		String localMac = getLocalMac();
		String tradeNo = date + commonNo + localMac + random;
		System.out.println("产生的系统流水号tradeNo：" + tradeNo);
		return tradeNo;
	}
	
	private String getLocalMac() {
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			// 获取网卡，获取地址
			byte[] mac = NetworkInterface.getByInetAddress(address).getHardwareAddress();
			if(mac == null || mac.length < 1) {
				return RandomStringUtils.randomNumeric(12);
			}
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < mac.length; i++) {
				// 字节转换为整数
				int temp = mac[i] & 0xff;
				String str = Integer.toHexString(temp);
				if (str.length() == 1) {
					sb.append("0" + str);
				} else {
					sb.append(str);
				}
			}
		    System.out.println("本机MAC地址:" + sb.toString().toUpperCase());
			return sb.toString().toUpperCase();

		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "";
	}
}
