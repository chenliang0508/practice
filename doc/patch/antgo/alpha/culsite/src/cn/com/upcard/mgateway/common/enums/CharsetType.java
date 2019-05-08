package cn.com.upcard.mgateway.common.enums;

public enum CharsetType {
	 UTF8("UTF-8"), 
	 GB2312("GB2312"), 
	 GBK("GBK");
     private String charset;    
	  // 构造方法  
     private CharsetType(String  charset) {  
	        this.setCharset(charset);  
	  }
   	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	 
}
