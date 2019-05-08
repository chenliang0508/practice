package cn.com.upcard.mgateway.model;

import cn.com.upcard.mgateway.common.enums.ResponseResult;

public class CodeMsg<T> {
	private String code;
	private String msg;
	protected T info;

	public CodeMsg() {
	}

	public CodeMsg(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public CodeMsg(ResponseResult result) {
		this.code = result.getCode();
		this.msg = result.getMsg();
	}
	
	public CodeMsg(ResponseResult result, T t) {
		this.code = result.getCode();
		this.msg = result.getMsg();
		this.info = t;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "code=" + code + ",msg=" + msg;
	}
}
