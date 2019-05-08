package cn.com.upcard.mgateway.channel.bank.lkl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Sha1 {
	
	public static String getSHA1(String str) {
		// SHA1签名生成
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(str.getBytes());
		byte[] digest = md.digest();
	
		StringBuffer hexstr = new StringBuffer();
		String shaHex = "";
		for (int i = 0; i < digest.length; i++) {
			shaHex = Integer.toHexString(digest[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexstr.append(0);
			}
			hexstr.append(shaHex);
		}
		return hexstr.toString();
} 
}
