package cn.com.upcard.mgateway.controller.response;

import java.io.Serializable;

import cn.com.upcard.mgateway.common.enums.ResponseResult;
import cn.com.upcard.mgateway.model.CodeMsg;

/**
 * 对外返回参数的基类
 * @author chenliang
 *
 */
public class BaseResponse implements Serializable {
	private static final long serialVersionUID = -2499782139170972878L;
	private String status = "0";// 0表示成功非0表示失败此字段是通信标识，非交易标识
	private String code;// 00接口调用成功，01无权限
	private String msg;
//	private String sign;
	
	public BaseResponse() {
	}
	public BaseResponse(ResponseResult result) {
		this.status = "0";
		this.code = result.getCode();
		this.msg = result.getMsg();
	}
	public BaseResponse(String code, String msg) {
		this.status = "0";
		this.code = code;
		this.msg = msg;
	}
	
	public BaseResponse(CodeMsg<?> codeMsg) {
		this.status = "0";
		this.code = codeMsg.getCode();
		this.msg = codeMsg.getMsg();
	}
	// 业务、订单状态由trade_state交易状态控制
	public String getStatus() {
		return status;
	}

	public BaseResponse setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getCode() {
		return code;
	}

	public BaseResponse setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public BaseResponse setMsg(String msg) {
		this.msg = msg;
		return this;
	}

//	public String getSign() {
//		return sign;
//	}
//
//	public BaseResponse setSign(String sign) {
//		this.sign = sign;
//		return this;
//	}

	@Override
	public String toString() {
		return "BaseResponse[status:" + status + ",code:" + code + ",msg:" + msg + "]";
	}
}
