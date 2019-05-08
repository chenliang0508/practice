package cn.com.upcard.mgateway.common.enums;

public enum ResponseResult {
	//00标识成功
	OK("00", "success"),
	//01 --- 20表示入参的校验的信息
	PARAM_IS_NULL("01", "{field} 不能为空"),
	PARAM_OVER_LENGTH("02", "{field} 长度超长"),
	PARAM_LENGTH_SHORT("03", "{field} 长度太短"),
	PARAM_POSITIVE_INTEGER("04", "{field} 为正整数"),
	PARAM_GREATER("05", "{field} 应该大于 {minvalue}"),
	PARAM_LESS("06", "{field} 应该大于 {maxvalue}"),
	PARAM_INCONFORMITY("07", "{field} 应该符合 {match}"),
	PARAM_IS_DATE("08", "{field} 应该符合 {match} 的时间格式"),
	PARAM_FORMAT_ERROR("09","{param} 格式不正确"),
	UNSUPPORTED_SERVICE("10", "不支持的服务类型"),
	PARAM_IS_ERROR("11","数据校验错误+具体描述"),
	MESSAGE_CONVERSION_ERROR("12","报文转换错误"),
	
	INVALID_SIGN("30", "无效的签名"),
	//31 --- 40表示商户相关的信息
	COMMERICAL_NOT_REGISTERED("31", "商户门店未注册或未生效"),
	INVALID_COMMERICAL_CHANNEL("32", "商户通道无效"),
	NO_INVOKE_PERMISSION("33", "没有调用权限"),
	UNREGISTERES_ACQUIRER("34", "未在收单机构注册"),
	
	UNDEFINED_TRADETYPE_OR_BANKCODE("41", "无效的交易方式或者通道代码"),
	UNSUPPORT_API("42", "不支持的api服务"),
	REQUEST_TOO_FREQUENTLY("43", "请求过于频繁"),
	
	//51 --- 90表示订单信息
	OUT_TRADE_NO_IS_EXISTS("51", "订单号重复"),
	ORDER_CLOSED("52", "订单已关闭"),
	ORDER_REVERSED("53", "订单已撤销"),
	ORDER_FIAL("50", "原订单已失败,不可操作"),
	ORDER_STATUS_ERROR("54", "订单状态异常"),
	WAIT_PAYING("55", "用户支付中"),
	ORDER_NOT_EXISTS("56", "无此订单"),
	ORDER_HAVA_PAID("57", "订单已支付"),
	ORDER_NOT_ALLOWED_CLOSE("58", "订单创建时间在5分钟以内，不允许关闭"),
	REFUND_AMOUNT_OUT_RANGE("59", "退款金额超过订单可退金额"),
	REFUND_FEE_INVALID("60", "退款金额无效"),
	WAIT_REFUND("61", "退款处理中"),
	CANCEL_TIME_OUT("62", "超过5分钟的订单不允许撤销"),
	AMOUNT_TOO_LARGE("63", "订单金额超限"),
	BALANCE_NOT_ENOUGH("65","余额不足"),
	
	AUTH_CODE_INVALID("66", "无效授权码"),
	INVALID_TOKEN("67", "无效的token"),
	APPID_NOT_MATCH_OPENID("68", "商户公众号appid和用户openid不匹配"),
	OPENID_IS_INVALID("69", "invalid openID"),
		
	TRADE_EXCEPTION("70","当前交易异常"),
	TRADE_FAILED("71", "交易失败"),
	NOT_SUPPORT_CARD("72", "不支持的卡类型"),
	CANCEL_TIME_SHORT("73","15秒以内的订单不允许撤销"),
	
	NON_RESP("90", "系统繁忙,请稍后查询"),
	
	//91--99表示系统的异常
	SYSTEM_ERROR("91", "系统接口错误,请稍后查询");
	
	ResponseResult(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	private String code;
	private String msg;
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
}
