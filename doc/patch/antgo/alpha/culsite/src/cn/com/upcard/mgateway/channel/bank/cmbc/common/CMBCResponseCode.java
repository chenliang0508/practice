package cn.com.upcard.mgateway.channel.bank.cmbc.common;

import cn.com.upcard.mgateway.common.enums.ResponseResult;

public enum CMBCResponseCode {


	SUCCESS("CMBCPAY0000", ResponseResult.OK),
	PARAM_IS_ERROR("APPS0001",ResponseResult.PARAM_IS_ERROR),
	MESSAGE_CONVERSION_ERROR("APPS0002",ResponseResult.MESSAGE_CONVERSION_ERROR),
	PARAM_FORMAT_ERROR("APPS0003",ResponseResult.PARAM_FORMAT_ERROR),
	UNSUPPORTED_SERVICE("APPS0004",ResponseResult.UNSUPPORTED_SERVICE),
	NON_RESP("APPS0005",ResponseResult.NON_RESP),
	
	PARAM_IS_DATE("APPS0006",ResponseResult.PARAM_IS_DATE),
	INVALID_SIGN("APPS0007",ResponseResult.INVALID_SIGN),
	AUTH_CODE_INVALID("APPS0008",ResponseResult.AUTH_CODE_INVALID),
	COMMERICAL_NOT_REGISTERED("APPS0013",ResponseResult.COMMERICAL_NOT_REGISTERED),
	NO_INVOKE_PERMISSION("APPS0014",ResponseResult.NO_INVOKE_PERMISSION),
	PARAM_IS_NULL("CMBCPAY0002", ResponseResult.PARAM_IS_NULL);

	private String resultCode;
	private ResponseResult responseResult;
	
	public String getResultCode() {
		return resultCode;
	}
	public ResponseResult getResponseResult() {
		return responseResult;
	}
	private CMBCResponseCode() {
	}
	
	private CMBCResponseCode(String resultCode, ResponseResult responseResult) {
		this.resultCode = resultCode;
		this.responseResult = responseResult;
	}


}
