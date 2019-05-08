package cn.com.upcard.mgateway.controller.request;

import cn.com.upcard.mgateway.annotion.HttpParamName;
import cn.com.upcard.mgateway.common.RequestParameter;
import cn.com.upcard.mgateway.common.Restrict;
import cn.com.upcard.mgateway.common.enums.Required;

public class WxNativePayParam extends BaseParam {

	private static final long serialVersionUID = -2575569141863122590L;

	@HttpParamName(name = RequestParameter.OUT_TRADE_NO, param = Required.REQUIRE, maxLen = 32, restrict = Restrict.OUT_TRADE_NO)
	private String outTradeNo;
	@HttpParamName(name = RequestParameter.DEVICE_INFO, maxLen = 30)
	private String deviceInfo;
	@HttpParamName(name = RequestParameter.BODY, param = Required.REQUIRE, maxLen = 127)
	private String body;
	@HttpParamName(name = RequestParameter.EXTEND_INFO, maxLen = 127)
	private String extendInfo;
	@HttpParamName(name = RequestParameter.TOTAL_AMOUNT, param = Required.REQUIRE, maxLen = 10, maxValue = 99999999, unSignedInt = true)
	private String totalAmount;
	@HttpParamName(name = RequestParameter.REQ_IP, param = Required.REQUIRE, maxLen = 15, restrict = Restrict.IP_V4)
	private String reqIp;
	@HttpParamName(name = RequestParameter.NOTIFY_URL, param = Required.REQUIRE, maxLen = 255, restrict = Restrict.URL)
	private String notifyUrl;
	@HttpParamName(name = RequestParameter.TIME_START, maxLen = 14, minLen = 14, date = "yyyyMMddHHmmss")
	private String timeStart;
	@HttpParamName(name = RequestParameter.TIME_EXPIRE, maxLen = 14, minLen = 14, date = "yyyyMMddHHmmss")
	private String timeExpire;
	@HttpParamName(name = RequestParameter.OPER_USER, maxLen = 20)
	private String operUser;
	@HttpParamName(name = RequestParameter.GOOD_TAGS, maxLen = 32)
	private String goodTags;
	@HttpParamName(name = RequestParameter.PAY_TYPE_LIMIT, maxLen = 50)
	private String payTypeLimit;
	@HttpParamName(name = RequestParameter.SUBJECT, maxLen = 50)
	private String subject;
	@HttpParamName(name = RequestParameter.MERCHANT_NO, maxLen = 20)
	private String merchantNo;

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
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

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getPayTypeLimit() {
		return payTypeLimit;
	}

	public void setPayTypeLimit(String payTypeLimit) {
		this.payTypeLimit = payTypeLimit;
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

	@Override
	public String toString() {
		return "WxNativePayParam [outTradeNo=" + outTradeNo + ", deviceInfo=" + deviceInfo + ", body=" + body
				+ ", extendInfo=" + extendInfo + ", totalAmount=" + totalAmount + ", reqIp=" + reqIp + ", notifyUrl="
				+ notifyUrl + ", timeStart=" + timeStart + ", timeExpire=" + timeExpire + ", operUser=" + operUser
				+ ", goodTags=" + goodTags + ", payTypeLimit=" + payTypeLimit + ", subject=" + subject + ", merchantNo="
				+ merchantNo + "]";
	}
}
