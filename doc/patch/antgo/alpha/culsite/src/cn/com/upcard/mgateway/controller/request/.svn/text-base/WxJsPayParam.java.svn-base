package cn.com.upcard.mgateway.controller.request;

import cn.com.upcard.mgateway.annotion.HttpParamName;
import cn.com.upcard.mgateway.common.RequestParameter;
import cn.com.upcard.mgateway.common.Restrict;
import cn.com.upcard.mgateway.common.enums.Required;

public class WxJsPayParam extends BaseParam {

	private static final long serialVersionUID = 8573569448591204850L;

	@HttpParamName(name = RequestParameter.IS_RAW, param = Required.REQUIRE, restrict = Restrict.IS_RAW, maxLen = 1)
	private String isRaw;
	@HttpParamName(name = RequestParameter.IS_MINIPG, param = Required.REQUIRE, restrict = Restrict.IS_MINIPG, maxLen = 1)
	private String isMinipg;
	@HttpParamName(name = RequestParameter.OUT_TRADE_NO, param = Required.REQUIRE, maxLen = 32, restrict = Restrict.OUT_TRADE_NO)
	private String outTradeNo;
	@HttpParamName(name = RequestParameter.DEVICE_INFO, maxLen = 30)
	private String deviceInfo;
	@HttpParamName(name = RequestParameter.BODY, maxLen = 127)
	private String body;
	@HttpParamName(name = RequestParameter.SUB_OPENID, maxLen = 128)
	private String subOpenid;
	@HttpParamName(name = RequestParameter.SUB_APPID, maxLen = 32)
	private String subAppid;
	@HttpParamName(name = RequestParameter.EXTEND_INFO, maxLen = 127)
	private String extendInfo;
	@HttpParamName(name = RequestParameter.TOTAL_AMOUNT, param = Required.REQUIRE, maxLen = 10, maxValue = 99999999, unSignedInt = true)
	private String totalAmount;
	@HttpParamName(name = RequestParameter.REQ_IP, maxLen = 15)
	private String reqIp;
	@HttpParamName(name = RequestParameter.NOTIFY_URL, param = Required.REQUIRE, maxLen = 255, restrict = Restrict.URL)
	private String notifyUrl;
	@HttpParamName(name = RequestParameter.RETURN_URL, maxLen = 255, restrict = Restrict.URL)
	private String returnUrl;
	@HttpParamName(name = RequestParameter.TIME_START, maxLen = 14, minLen = 14, date = "yyyyMMddHHmmss")
	private String timeStart;
	@HttpParamName(name = RequestParameter.TIME_EXPIRE, maxLen = 14, minLen = 14, date = "yyyyMMddHHmmss")
	private String timeExpire;
	@HttpParamName(name = RequestParameter.GOOD_TAGS, maxLen = 32)
	private String goodTags;
	@HttpParamName(name = RequestParameter.PAY_TYPE_LIMIT, maxLen = 50)
	private String payTypeLimit;
	@HttpParamName(name = RequestParameter.OPER_USER, maxLen = 20)
	private String operUser;
	@HttpParamName(name = RequestParameter.MERCHANT_NO, maxLen = 20)
	private String merchantNo;

	public String getIsRaw() {
		return isRaw;
	}

	public void setIsRaw(String isRaw) {
		this.isRaw = isRaw;
	}

	public String getIsMinipg() {
		return isMinipg;
	}

	public void setIsMinipg(String isMinipg) {
		this.isMinipg = isMinipg;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubOpenid() {
		return subOpenid;
	}

	public void setSubOpenid(String subOpenid) {
		this.subOpenid = subOpenid;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
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

	public String getGoodTags() {
		return goodTags;
	}

	public void setGoodTags(String goodTags) {
		this.goodTags = goodTags;
	}

	public String getPayTypeLimit() {
		return payTypeLimit;
	}

	public void setPayTypeLimit(String payTypeLimit) {
		this.payTypeLimit = payTypeLimit;
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

	@Override
	public String toString() {
		return "WxJsPayParam [isRaw=" + isRaw + ", isMinipg=" + isMinipg + ", outTradeNo=" + outTradeNo
				+ ", deviceInfo=" + deviceInfo + ", body=" + body + ", subOpenid=" + subOpenid + ", subAppid="
				+ subAppid + ", extendInfo=" + extendInfo + ", totalAmount=" + totalAmount + ", reqIp=" + reqIp
				+ ", notifyUrl=" + notifyUrl + ", returnUrl=" + returnUrl + ", timeStart=" + timeStart + ", timeExpire="
				+ timeExpire + ", goodTags=" + goodTags + ", payTypeLimit=" + payTypeLimit + ", operUser=" + operUser
				+ ", merchantNo=" + merchantNo + "]";
	}

}
