package cn.com.upcard.mgateway.channel.bank.cib.common;

import cn.com.upcard.mgateway.common.enums.ResponseResult;

/**
 * 兴业的错误码与cul的错误码的转换
 * 
 * @author chenliang
 *
 */
public enum CibResponseCode {

	SUCCESS("0", ResponseResult.OK), 
	APPID_OPENID_NOT_MATCH("sub_appid and sub_openid not match",ResponseResult.APPID_NOT_MATCH_OPENID), 
	SUB_OPENID_IS_INVALID("sub_openid is invalid", ResponseResult.OPENID_IS_INVALID), 
	VALID_ERROR("valid error",ResponseResult.OPENID_IS_INVALID), 
	TRANSACTION_INTERNAL_ERROR("transaction internal error",ResponseResult.TRADE_EXCEPTION),
	UNSUPPORTAPI("unsupportapi",ResponseResult.UNSUPPORT_API), 
	VALID_SIGN_FAIL("valid sign fail", ResponseResult.INVALID_SIGN), 
	VALID_SERVICE_FAIL( "valid service fail", ResponseResult.NO_INVOKE_PERMISSION), 
	VALID_MCH_STATUS_FAIL("valid mch status fail",ResponseResult.COMMERICAL_NOT_REGISTERED),
	REFUNDINFO_IS_EXISTS("refundInfo is exists",ResponseResult.OUT_TRADE_NO_IS_EXISTS), 
	VALID_FLOW_FAIL("valid flow fail",ResponseResult.REQUEST_TOO_FREQUENTLY), 
	ORDER_NOT_EXISTS("Order not exists",ResponseResult.ORDER_NOT_EXISTS), 
	AUTH_VALID_FAIL("Auth valid fail",ResponseResult.AUTH_CODE_INVALID), 
	SIGNATURE_ERROR("Signature error",ResponseResult.INVALID_SIGN), 
	BALANCE_NOT_ENOUGH("BALANCE_NOT_ENOUGH",ResponseResult.BALANCE_NOT_ENOUGH), 
	THI_MCH_ID_IS_REQUIRED("thi_mch_id is required",ResponseResult.COMMERICAL_NOT_REGISTERED), 
	USERPAYING("USERPAYING",ResponseResult.WAIT_PAYING),
	PAYING("10003",ResponseResult.WAIT_PAYING),
	NOTENOUGH("NOTENOUGH",ResponseResult.BALANCE_NOT_ENOUGH), 
	ORDERPAID("ORDERPAID",ResponseResult.OUT_TRADE_NO_IS_EXISTS), 
	REFUNDINFOTEXIST("REFUNDINFOTEXIST",ResponseResult.OUT_TRADE_NO_IS_EXISTS),
	ORDERNOTEXIST("ORDERNOTEXIST", ResponseResult.ORDER_NOT_EXISTS),
	ACQ_TRADE_NOT_EXIST("ACQ.TRADE_NOT_EXIST", ResponseResult.ORDER_NOT_EXISTS), 
	AUTH_CODE_INVALID("AUTH_CODE_INVALID",ResponseResult.AUTH_CODE_INVALID), 
	OUT_TRADE_NO_USED("OUT_TRADE_NO_USED",ResponseResult.OUT_TRADE_NO_IS_EXISTS),
	ORDERSTATUSERROR("ORDERSTATUSERROR",ResponseResult.ORDER_STATUS_ERROR),
	ORDERISEXIST("ORDERISEXIST", ResponseResult.OUT_TRADE_NO_IS_EXISTS),
	ORDERREVERSED("ORDERREVERSED", ResponseResult.ORDER_REVERSED), 
	SYSTEMERROR("ORDER_SUCCESS_PAY_INPROCESS",ResponseResult.TRADE_EXCEPTION),
	REFUND_FEE_INVALID("REFUND_FEE_INVALID", ResponseResult.REFUND_FEE_INVALID),
	AUTH_CODE_EXPIRE("AUTHCODEEXPIRE",ResponseResult.AUTH_CODE_INVALID),
	AUTH_CODE_ERROR("AUTH_CODE_ERROR",ResponseResult.AUTH_CODE_INVALID),
	AUTH_CODE_NOT_VALID("Auto code invalid",ResponseResult.AUTH_CODE_INVALID), 
	BUYER_BALANCE_NOT_ENOUGH("ACQ.BUYER_BALANCE_NOT_ENOUGH",ResponseResult.BALANCE_NOT_ENOUGH), 
    PAYMENT_AUTH_CODE_INVALID("ACQ.PAYMENT_AUTH_CODE_INVALID",ResponseResult.AUTH_CODE_INVALID);
	
	private String resultCode;
	private ResponseResult responseResult;

	public String getResultCode() {
		return resultCode;
	}

	public ResponseResult getResponseResult() {
		return responseResult;
	}

	private CibResponseCode() {
	}

	private CibResponseCode(String resultCode, ResponseResult responseResult) {
		this.resultCode = resultCode;
		this.responseResult = responseResult;
	}

	public static ResponseResult transferCode(String resultCode) {
		if (resultCode == null || resultCode.equals("")) {
			return ResponseResult.ORDER_STATUS_ERROR;
		}
		for (CibResponseCode cibCode : CibResponseCode.values()) {
			if (cibCode.getResultCode().equals(resultCode)) {
				return cibCode.getResponseResult();
			}
		}
		return ResponseResult.ORDER_STATUS_ERROR;
	}
}
