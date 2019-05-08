package cn.com.upcard.mgateway.controller.request;

import javax.servlet.http.HttpServletRequest;

public class QrCodeOrderPayParam extends BaseParam {

	private static final long serialVersionUID = 1776768482883251155L;
	private String qrToken;
	private String totalAmount;
	private String deviceInfo;
	private String subject;
	private String body;
	private String extendInfo;
	private String timeStart;
	private String timeExpire;
	private String operUser;
	private String merchantNo;
	private String payTypeLimit;
	private String notifyUrl;
	private String returnUrl;
	private String goodTags;
	private String reqIp;
	private String thirdBuyerId;
	
	public String getQrToken() {
		return qrToken;
	}
	public void setQrToken(String qrToken) {
		this.qrToken = qrToken;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getExtendInfo() {
		return extendInfo;
	}
	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
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
	public String getOperUser() {
		return operUser;
	}
	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getPayTypeLimit() {
		return payTypeLimit;
	}
	public void setPayTypeLimit(String payTypeLimit) {
		this.payTypeLimit = payTypeLimit;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getGoodTags() {
		return goodTags;
	}
	public void setGoodTags(String goodTags) {
		this.goodTags = goodTags;
	}
	public String getReqIp() {
		return reqIp;
	}
	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}
	
	public String getThirdBuyerId() {
		return thirdBuyerId;
	}
	public void setThirdBuyerId(String thirdBuyerId) {
		this.thirdBuyerId = thirdBuyerId;
	}
	public static QrCodeOrderPayParam instance(HttpServletRequest request) {
		QrCodeOrderPayParam param = new QrCodeOrderPayParam();
		param.setQrToken(request.getParameter("qrToken"));
		param.setTimestamp(request.getParameter("timestamp"));
		param.setTotalAmount(request.getParameter("totalAmount"));
		param.setDeviceInfo(request.getParameter("deviceInfo"));
		param.setSubject(request.getParameter("subject"));
		param.setBody(request.getParameter("body"));
		param.setExtendInfo(request.getParameter("extendInfo"));
		param.setTimeStart(request.getParameter("timeStart"));
		param.setTimeExpire(request.getParameter("timeExpire"));
		param.setOperUser(request.getParameter("operUser"));
		param.setMerchantNo(request.getParameter("merchantNo"));
		param.setPayTypeLimit(request.getParameter("payTypeLimit"));
		param.setNotifyUrl(request.getParameter("notiryUrl"));
		param.setReturnUrl(request.getParameter("returnUrl"));
		param.setGoodTags(request.getParameter("goodTags"));
		param.setReqIp(request.getParameter("reqIp"));
		param.setMchId(request.getParameter("mchId"));
		param.setService(request.getParameter("service"));
		param.setSign(request.getParameter("sign"));
		param.setThirdBuyerId(request.getParameter("thirdBuyerId"));
		return param;
	}
	@Override
	public String toString() {
		return "QrCodeOrderPayParam [qrToken=" + qrToken + ", totalAmount=" + totalAmount + ", deviceInfo=" + deviceInfo
				+ ", subject=" + subject + ", body=" + body + ", extendInfo=" + extendInfo + ", timeStart=" + timeStart
				+ ", timeExpire=" + timeExpire + ", operUser=" + operUser + ", merchantNo=" + merchantNo
				+ ", payTypeLimit=" + payTypeLimit + ", notifyUrl=" + notifyUrl + ", returnUrl=" + returnUrl
				+ ", goodTags=" + goodTags + ", reqIp=" + reqIp + ", thirdBuyerId=" + thirdBuyerId + ", service="
				+ service + ", signType=" + signType + ", returnType=" + returnType + ", sign=" + sign + ", mchId="
				+ mchId + "]";
	}
			
}
