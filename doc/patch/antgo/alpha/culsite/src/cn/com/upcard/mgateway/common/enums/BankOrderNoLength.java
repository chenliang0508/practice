package cn.com.upcard.mgateway.common.enums;

public enum BankOrderNoLength {
	//BankCode
	DEFAULT(30, null),
	LKL(20, BankCode.LKL);
	private int length;
	private BankCode bankCode;
	private BankOrderNoLength() {
	}
	private BankOrderNoLength(int length, BankCode bankCode) {
		this.length = length;
		this.bankCode = bankCode;
	}
	public int getLength() {
		return length;
	}
	public BankCode getBankCode() {
		return bankCode;
	}
	
	public static int getOrderLength(BankCode bankCode) {
		if(bankCode == null) 
			return DEFAULT.getLength();
		for(BankOrderNoLength orderNo : BankOrderNoLength.values()) {
			if(orderNo.getBankCode() == bankCode) 
				return orderNo.getLength();
		}
		return DEFAULT.getLength();
	}
}
