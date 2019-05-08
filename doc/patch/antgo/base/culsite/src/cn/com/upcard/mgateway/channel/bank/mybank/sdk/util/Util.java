package cn.com.upcard.mgateway.channel.bank.mybank.sdk.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Util {
	//消费币种的最小货币单位（分）,金额不足15位前补0，如金额为15.00，则000000000001500
	public static String formatMoney(Object amount){
		String out="";
		DecimalFormat df = new DecimalFormat("###########0.00");
		String s="";
		if(amount instanceof String){
			s=df.format(new BigDecimal((String)amount));
		}
		if(amount instanceof BigDecimal){
			s=df.format(amount);
		}
		if(amount instanceof Double){
			s=df.format(new BigDecimal((Double)amount));
		}
		s=s.replace(".", "");
		for(int i=s.length();i<15;i++){
			out+="0";
		}
		return out+s;
	}
	
	public static void main(String[] args){
		System.out.println(formatMoney("123"));
	}
	
}
