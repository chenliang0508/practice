package cn.com.upcard.mgateway.channel.enums;

public enum ChannelResponseResult {

	/**
	 * 接口调用成功
	 */
	SUCCESS("00"),
	/**
	 * 基础参数或者权限错误
	 */
	STATUS_FAIL("01"),
	/**
	 * 支付相关接口，业务错误
	 */
	RESULT_FAIL("02"),
	/**
	 * 返回签名错误
	 */
	SIGNATURE_ERROR("03"),
	
	/**
	 *用户支付中,需要输入支付密码
	 */
	PAYING("20"),
	/**
	 * 支付失败
	 */
	PAYMENT_FAIL("21"),
	/**
	 * 业务处理超时
	 */
	HANDLE_TIME_EXPIRE("22"),
	/**
	 * 更换其他支付方式，重新下单支付
	 */
	TRADE_ERROR("23"),
	
	
	
	
	/**
	 * APPID不存在
	 */
	APPID_NOT_EXIST("40"),
	/**
	 *买家付款金额超限度
	 */
	BUYER_OVER_LIMIT_ERROR("41"), 
	/**
	 * 商户收款额度超限
	 */
	SELLER_OVER_LIMIT_ERROR("42"),
	/**
	 * 余额不足
	 */
	BALANCE_NOT_ENOUGH("43"),
	/**
	 * 授权码错误
	 */
	AUTO_CODE_INVALID("44"),
	/**
	 *appid和mch_id不匹配
	 */
	APPID_MCHID_NOT_MATCH("45"),
	/**
	 * 买家账户支付状态异常
	 */
	PAY_FUNCTION_CLOSED("46"),
	/**
	 * 商户门店编号无效
	 */
	SHOP_ID_INVALID("47"),
	/**
	 * 商户合约状态非法
	 */
	MERCHANT_CONTRACT_STATUS_ILLEGAL("48"),
	/**
	 * 余额支付功能关闭
	 */
	ERROR_BALANCE_PAYMENT_DISABLE("49"),
	
	/**
	 * 无此卡号
	 */
	CARD_NOT_EXISTS("50"),
	
	/**
	 * 原交易不存在
	 */
	OGR_TRADE_NOT_EXISTS("51"),
	/**
	 * 密码错误
	 */
	PASSWORD_ERROR("52"),
	/**
	 * 无效终端
	 */
	INVALID_TIEM("53"),
	/**
	 * 当前交易异常
	 */
	TRADE_EXCEPTION("70"),
	/**
	 * 系统异常，重试
	 */
	SYSTEM_ERROR("71"),
	/**
	 * 未知系统异常
	 */
	UNKNOWN_SYSTEM_ERROR("72"),
	/**
	 * 当前时段禁止此交易
	 */
	TRADE_FORBIDDEN("73");




	private String code;


	private ChannelResponseResult(String code) {
		this.code = code;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
