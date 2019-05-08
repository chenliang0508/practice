package cn.com.upcard.mgateway.common;


/**
 * 所有接口出参名称
 * 
 * @author zhoudi
 *
 */
public class ResponseParameter {

	public final String STATUS = "status"; // (16) 返回状态码，0表示成功非0表示失败此字段是通信标识，非交易标识
	public final String CODE = "code"; // (2) 业务结果
	public final String MSG = "msg"; // (512) 返回信息
	public final String SIGN = "sign"; // (256) 签名

	public final String AUTH_CODE_URL = "authCodeUrl"; // (64) 二维码链接 此参数可直接生成二维码展示出来进行扫码支付
	public final String AUTH_CODE_IMG_URL = "authCodeImgUrl"; // (256) 用此链接请求二维码图片

	public final String PAY_INFO = "payInfo"; // 支付宝(64) 微信(?) 原生支付信息
	public final String PAY_URL = "payUrl"; // (128) 直接使用此链接请求支付宝支付

	public final String TRADE_STATE = "tradeState"; // (20) 交易状态
	public final String MCH_ID = "mchId"; // (30) 商户号
	public final String SUB_APPID = "subAppid"; // (?) 子商户appid
	public final String SUB_IS_SUBSCRIBE = "subIsSubscribe"; // (1) 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效（子商户公众账号）
	public final String TRANSACTION_ID = "transactionId"; // (40) 平台交易号
	public final String OUT_TRADE_NO = "outTradeNo"; // (32) 商户系统内部的定单号，32个字符内、可包含字母
	public final String TOTAL_AMOUNT = "totalAmount"; // (10) 订单总金额，单位分
	public final String DISCOUNT_AMOUNT = "discountAmount"; // (10) 折扣金额，单位分
	public final String EXTEND_INFO = "extendInfo"; // (127) 商家数据包，原样返回
	public final String TIME_END = "timeEnd"; // (14) 支付完成时间，格式为yyyyMMddhhmmss
	public final String MERCHANT_NO = "merchantNo"; // (20) 门店号
	public final String DEVICE_INFO = "deviceInfo"; // (30) 设备号
	public final String BUYER_ACCOUNT = "buyerAccount"; // (128) 买家账户,微信是subOpenid，支付宝是openid
	public final String OUT_TRANSACTION_ID = "outTransactionId"; // (50) 第三方支付机构流水号
	public final String TRADE_TYPE = "tradeType"; // (25) 交易类型

	public final String OUT_REFUND_NO = "outRefundNo"; // (32) 商户退款单号
	public final String REFUND_ID = "refundId"; // (40) 银商退款流水号
	public final String REFUND_AMOUNT = "refundAmount"; // (10) 退款金额（分）
	public final String REFUND_TIME = "refundTime"; // (14) 退款时间
	
	

}
