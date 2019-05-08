package cn.com.upcard.mgateway.controller.request;

import javax.servlet.http.HttpServletRequest;

public class MicroPayRequest extends BaseParam {
	private static final long serialVersionUID = 1812120708039338693L;
	// 商户系统内部的订单号 ,5到32个字符、 只能包含字母数字或者下划线，区分大小写，确保在商户系统唯一
	private String outTradeNo;
	// 终端设备号，商户自定义。特别说明：对于QQ钱包支付，此参数必传，否则会报错。
	private String deviceInfo;
	// 商品描述
	private String body;
	// 单品信息,单品优惠活动该字段必传，且必须按照规范上传，JSON格式
	private String goodsDetail;
	// 商户附加信息，可做扩展参数
	private String extendParam;
	// 总金额，以分为单位，不允许包含任何字、符号
	private String totalFee;
	// 订单生成的机器 IP
	private String mchCreateIp;
	// 扫码支付授权码， 设备读取用户展示的条码或者二维码信息
	private String authCode;
	// 订单生成时间，格式为yyyymmddhhmmss，如2009年12月25日9点10分10秒表示为20091225091010。时区为GMT+8
	// beijing。该时间取自商户服务器
	private String timeStart;
	// 订单失效时间，格式为yyyymmddhhmmss，如2009年12月27日9点10分10秒表示为20091227091010。时区为GMT+8
	// beijing。该时间取自商户服务器
	private String timeExpire;
	// 操作员帐号,默认为商户号
	private String opUser;
	// 门店编号
	private String merchantNo;
	// 商品标记
	private String goodsTag;
	// 是否限制信用卡
	private String payTypeLimit;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "MicroPay [service=" + service + ", signType=" + signType + ", mchId=" + mchId + ", returnType="
				+ returnType + ", outTradeNo=" + outTradeNo + ", deviceInfo=" + deviceInfo + ", body=" + body
				+ ",goodsDetail=" + goodsDetail + ", extendParam=" + extendParam + ", totalFee=" + totalFee
				+ ", mchCreateIp=" + mchCreateIp + ", authCode=" + authCode + ", timeStart=" + timeStart
				+ ", timeExpire=" + timeExpire + ", opUser=" + opUser + ", merchantNo=" + merchantNo + ", goodsTag="
				+ goodsTag + ", sign=" + sign + ",payTypeLimit=" + payTypeLimit + "]";
	}

	public static MicroPayRequest instance(HttpServletRequest request) {
		MicroPayRequest bean = new MicroPayRequest();
		bean.setService(request.getParameter("service"));
		bean.setSignType(request.getParameter("signType"));
		bean.setMchId(request.getParameter("mchId"));
		bean.setReturnType(request.getParameter("returnType"));
		bean.setSign(request.getParameter("sign"));
		bean.setGoodsTag(request.getParameter("goodTags"));
		bean.setTimestamp(request.getParameter("timestamp"));
		bean.setOutTradeNo(request.getParameter("outTradeNo"));
		bean.setTotalFee(request.getParameter("totalAmount"));
		bean.setAuthCode(request.getParameter("authCode"));
		bean.setDeviceInfo(request.getParameter("deviceInfo"));
		bean.setBody(request.getParameter("body"));
		bean.setGoodsDetail(request.getParameter("goodsDetail"));
		bean.setExtendParam(request.getParameter("extendInfo"));
		bean.setMchCreateIp(request.getParameter("reqIp"));
		bean.setTimeStart(request.getParameter("timeStart"));
		bean.setTimeExpire(request.getParameter("timeExpire"));
		bean.setOpUser(request.getParameter("operUser"));
		bean.setMerchantNo(request.getParameter("merchantNo"));
		bean.setPayTypeLimit(request.getParameter("payTypeLimit"));
		return bean;

	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public String getExtendParam() {
		return extendParam;
	}

	public void setExtendParam(String extendParam) {
		this.extendParam = extendParam;
	}

	public String getMchCreateIp() {
		return mchCreateIp;
	}

	public void setMchCreateIp(String mchCreateIp) {
		this.mchCreateIp = mchCreateIp;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public String getPayTypeLimit() {
		return payTypeLimit;
	}

	public void setPayTypeLimit(String payTypeLimit) {
		this.payTypeLimit = payTypeLimit;
	}
}
