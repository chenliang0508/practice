package cn.com.upcard.mgateway.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TRADE_ORDER")
public class TradeOrder implements Serializable {

	private static final long serialVersionUID = 9086024372888349868L;
	@Id
	@GenericGenerator(name = "tradeOrderId", strategy = "uuid")
	@GeneratedValue(generator = "tradeOrderId")
	@Column(name = "ID", length = 32)
	private String id;
	@Column(name = "TRADE_NO")
	private String tradeNo;
	@Column(name = "ORDER_NO")
	private String orderNo;
	@Column(name = "CUL_COMM_NO")
	private String culCommNo;
	@Column(name = "CHANNEL_ID")
	private String channelId;
	@Column(name = "ORDER_POINT")
	private Integer orderPoint;
	@Column(name = "PAY_POINT")
	private Integer payPoint;
	@Column(name = "DISCOUNT_POINT")
	private Integer discountPoint;
	@Column(name = "ORDER_TYPE")
	private String orderType;
	@Column(name = "TRADE_TYPE")
	private String tradeType;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "MERCHANT_NO")
	private String merchantNo;
	@Column(name = "DEVICE_INFO")
	private String deviceInfo;
	@Column(name = "ORIG_ORDER_NO")
	private String origOrderNo;
	@Column(name = "AUTH_CODE")
	private String authCode;
	@Column(name = "REFUND_AUDIT_STATUS")
	private String refundAuditStatus;
	@Column(name = "TRADE_DATE")
	private String tradeDate;
	@Column(name = "TRADE_TIME")
	private String tradeTime;
	@Column(name = "VALID_START_TIME")
	private String validStartTime;
	@Column(name = "EXPIRE_TIME")
	private String expireTime;
	@Column(name = "SUBJECT")
	private String subject;
	@Column(name = "COMMODITY_DESC")
	private String commodityDesc;
	@Column(name = "NOTIFY_URL")
	private String notifyUrl;
	@Column(name = "RETURN_URL")
	private String returnUrl;
	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "CREATE_USER")
	private String createUser;
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	@Column(name = "UPDATE_USER")
	private String updateUser;
	@Column(name = "CHANNEL_TXN_NO")
	private String channelTxnNo;
	@Column(name = "THIRD_PAYMENT_TXNID")
	private String thirdPaymentTxnid;
	@Column(name = "THIRD_SELLER_ACCOUNT")
	private String thirdSellerAccount;
	@Column(name = "THIRD_BUYER_ACCOUNT")
	private String thirdBuyerAccount;
	@Column(name = "GOOD_TAGS")
	private String goodTags;
	@Column(name = "EXTEND_INFO")
	private String extendInfo;
	@Column(name = "FAIL_MSG")
	private String failMsg;
	@Column(name = "PAY_TYPE_LIMIT")
	private String payTypeLimit;
	@Column(name = "REMARK")
	private String remark;

	// Constructors

	/** default constructor */
	public TradeOrder() {
	}

	/** minimal constructor */
	public TradeOrder(String id, String tradeNo, String orderNo, String culCommNo) {
		this.id = id;
		this.tradeNo = tradeNo;
		this.orderNo = orderNo;
		this.culCommNo = culCommNo;
	}

	/** full constructor */
	public TradeOrder(String id, String tradeNo, String orderNo, String culCommNo, String channelId, Integer orderPoint,
			Integer payPoint, Integer discountPoint, String orderType, String tradeType, String status,
			String merchantNo, String deviceInfo, String origOrderNo, String authCode, String refundAuditStatus,
			String tradeDate, String tradeTime, String validStartTime, String expireTime, String subject,
			String commodityDesc, String notifyUrl, String returnUrl, Date createTime, String createUser,
			Date updateTime, String updateUser, String channelTxnNo, String thirdPaymentTxnid,
			String thirdSellerAccount, String thirdBuyerAccount, String goodTags, String extendInfo, String failMsg,
			String payTypeLimit, String remark) {
		this.id = id;
		this.tradeNo = tradeNo;
		this.orderNo = orderNo;
		this.culCommNo = culCommNo;
		this.channelId = channelId;
		this.orderPoint = orderPoint;
		this.payPoint = payPoint;
		this.discountPoint = discountPoint;
		this.orderType = orderType;
		this.tradeType = tradeType;
		this.status = status;
		this.merchantNo = merchantNo;
		this.deviceInfo = deviceInfo;
		this.origOrderNo = origOrderNo;
		this.authCode = authCode;
		this.refundAuditStatus = refundAuditStatus;
		this.tradeDate = tradeDate;
		this.tradeTime = tradeTime;
		this.validStartTime = validStartTime;
		this.expireTime = expireTime;
		this.subject = subject;
		this.commodityDesc = commodityDesc;
		this.notifyUrl = notifyUrl;
		this.returnUrl = returnUrl;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
		this.channelTxnNo = channelTxnNo;
		this.thirdPaymentTxnid = thirdPaymentTxnid;
		this.thirdSellerAccount = thirdSellerAccount;
		this.thirdBuyerAccount = thirdBuyerAccount;
		this.goodTags = goodTags;
		this.extendInfo = extendInfo;
		this.failMsg = failMsg;
		this.payTypeLimit = payTypeLimit;
		this.remark = remark;
	}

	public String getId() {
		return this.id;
	}

	public TradeOrder setId(String id) {
		this.id = id;
		return this;
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public TradeOrder setTradeNo(String tradeNo) {
		if (tradeNo != null && !tradeNo.equals("")) {
			this.tradeNo = tradeNo;
		}
		return this;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public TradeOrder setOrderNo(String orderNo) {
		if (orderNo != null && !orderNo.equals("")) {
			this.orderNo = orderNo;
		}
		return this;
	}

	public String getCulCommNo() {
		return this.culCommNo;
	}

	public TradeOrder setCulCommNo(String culCommNo) {
		if (culCommNo != null && !culCommNo.equals("")) {
			this.culCommNo = culCommNo;
		}
		return this;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public TradeOrder setChannelId(String channelId) {
		if (channelId != null && !channelId.equals("")) {
			this.channelId = channelId;
		}
		return this;
	}

	public Integer getOrderPoint() {
		return this.orderPoint;
	}

	public TradeOrder setOrderPoint(Integer orderPoint) {
		if (orderPoint != null) {
			this.orderPoint = orderPoint;
		}
		return this;
	}

	public Integer getPayPoint() {
		return this.payPoint;
	}

	public TradeOrder setPayPoint(Integer payPoint) {
		if (payPoint != null) {
			this.payPoint = payPoint;
		}
		return this;
	}

	public Integer getDiscountPoint() {
		return this.discountPoint;
	}

	public TradeOrder setDiscountPoint(Integer discountPoint) {
		if (discountPoint != null) {
			this.discountPoint = discountPoint;
		}
		return this;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public TradeOrder setOrderType(String orderType) {
		if (orderType != null && !orderType.equals("")) {
			this.orderType = orderType;
		}
		return this;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public TradeOrder setTradeType(String tradeType) {
		if (tradeType != null && !tradeType.equals("")) {
			this.tradeType = tradeType;
		}
		return this;
	}

	public String getStatus() {
		return this.status;
	}

	public TradeOrder setStatus(String status) {
		if (status != null && !status.equals("")) {
			this.status = status;
		}
		return this;
	}

	public String getMerchantNo() {
		return this.merchantNo;
	}

	public TradeOrder setMerchantNo(String merchantNo) {
		if (merchantNo != null && !merchantNo.equals("")) {
			this.merchantNo = merchantNo;
		}
		return this;
	}

	public String getDeviceInfo() {
		return this.deviceInfo;
	}

	public TradeOrder setDeviceInfo(String deviceInfo) {
		if (deviceInfo != null && !deviceInfo.equals("")) {
			this.deviceInfo = deviceInfo;
		}
		return this;
	}

	public String getOrigOrderNo() {
		return this.origOrderNo;
	}

	public TradeOrder setOrigOrderNo(String origOrderNo) {
		if (origOrderNo != null && !origOrderNo.equals("")) {
			this.origOrderNo = origOrderNo;
		}
		return this;
	}

	public String getAuthCode() {
		return this.authCode;
	}

	public TradeOrder setAuthCode(String authCode) {
		if (authCode != null && !authCode.equals("")) {
			this.authCode = authCode;
		}
		return this;
	}

	public String getRefundAuditStatus() {
		return this.refundAuditStatus;
	}

	public TradeOrder setRefundAuditStatus(String refundAuditStatus) {
		if (refundAuditStatus != null && !refundAuditStatus.equals("")) {
			this.refundAuditStatus = refundAuditStatus;
		}
		return this;
	}

	public String getTradeDate() {
		return this.tradeDate;
	}

	public TradeOrder setTradeDate(String tradeDate) {
		if (tradeDate != null && !tradeDate.equals("")) {
			this.tradeDate = tradeDate;
		}
		return this;
	}

	public String getTradeTime() {
		return this.tradeTime;
	}

	public TradeOrder setTradeTime(String tradeTime) {
		if (tradeTime != null && !tradeTime.equals("")) {
			this.tradeTime = tradeTime;
		}
		return this;
	}

	public String getValidStartTime() {
		return this.validStartTime;
	}

	public TradeOrder setValidStartTime(String validStartTime) {
		if (validStartTime != null && !validStartTime.equals("")) {
			this.validStartTime = validStartTime;
		}
		return this;
	}

	public String getExpireTime() {
		return this.expireTime;
	}

	public TradeOrder setExpireTime(String expireTime) {
		if (expireTime != null && !expireTime.equals("")) {
			this.expireTime = expireTime;
		}
		return this;
	}

	public String getSubject() {
		return this.subject;
	}

	public TradeOrder setSubject(String subject) {
		if (subject != null && !subject.equals("")) {
			this.subject = subject;
		}
		return this;
	}

	public String getCommodityDesc() {
		return this.commodityDesc;
	}

	public TradeOrder setCommodityDesc(String commodityDesc) {
		if (commodityDesc != null && !commodityDesc.equals("")) {
			this.commodityDesc = commodityDesc;
		}
		return this;
	}

	public String getNotifyUrl() {
		return this.notifyUrl;
	}

	public TradeOrder setNotifyUrl(String notifyUrl) {
		if (notifyUrl != null && !notifyUrl.equals("")) {
			this.notifyUrl = notifyUrl;
		}
		return this;
	}

	public String getReturnUrl() {
		return this.returnUrl;
	}

	public TradeOrder setReturnUrl(String returnUrl) {
		if (returnUrl != null && !returnUrl.equals("")) {
			this.returnUrl = returnUrl;
		}
		return this;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public TradeOrder setCreateTime(Date createTime) {
		if (createTime != null) {
			this.createTime = createTime;
		}
		return this;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public TradeOrder setCreateUser(String createUser) {
		if (createUser != null && !createUser.equals("")) {
			this.createUser = createUser;
		}
		return this;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public TradeOrder setUpdateTime(Date updateTime) {
		if (updateTime != null) {
			this.updateTime = updateTime;
		}
		return this;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public TradeOrder setUpdateUser(String updateUser) {
		if (updateUser != null && !updateUser.equals("")) {
			this.updateUser = updateUser;
		}
		return this;
	}

	public String getChannelTxnNo() {
		return channelTxnNo;
	}

	public TradeOrder setChannelTxnNo(String channelTxnNo) {
		if (channelTxnNo != null && !channelTxnNo.equals("")) {
			this.channelTxnNo = channelTxnNo;
		}
		return this;
	}

	public String getThirdPaymentTxnid() {
		return thirdPaymentTxnid;
	}

	public TradeOrder setThirdPaymentTxnid(String thirdPaymentTxnid) {
		if (thirdPaymentTxnid != null && !thirdPaymentTxnid.equals("")) {
			this.thirdPaymentTxnid = thirdPaymentTxnid;
		}
		return this;
	}

	public String getThirdSellerAccount() {
		return thirdSellerAccount;
	}

	public TradeOrder setThirdSellerAccount(String thirdSellerAccount) {
		if (thirdSellerAccount != null && !thirdSellerAccount.equals("")) {
			this.thirdSellerAccount = thirdSellerAccount;
		}
		return this;
	}

	public String getThirdBuyerAccount() {
		return thirdBuyerAccount;
	}

	public TradeOrder setThirdBuyerAccount(String thirdBuyerAccount) {
		if (origOrderNo != null && !origOrderNo.equals("")) {
			this.thirdBuyerAccount = thirdBuyerAccount;
		}
		return this;
	}

	public String getGoodTags() {
		return goodTags;
	}

	public TradeOrder setGoodTags(String goodTags) {
		if (goodTags != null && !goodTags.equals("")) {
			this.goodTags = goodTags;
		}
		return this;
	}

	public String getExtendInfo() {
		return extendInfo;
	}

	public TradeOrder setExtendInfo(String extendInfo) {
		if (extendInfo != null && !extendInfo.equals("")) {
			this.extendInfo = extendInfo;
		}
		return this;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public TradeOrder setFailMsg(String failMsg) {
		if (failMsg != null && !failMsg.equals("")) {
			this.failMsg = failMsg;
		}
		return this;
	}

	public String getPayTypeLimit() {
		return payTypeLimit;
	}

	public TradeOrder setPayTypeLimit(String payTypeLimit) {
		if (payTypeLimit != null && !payTypeLimit.equals("")) {
			this.payTypeLimit = payTypeLimit;
		}
		return this;
	}

	public String getRemark() {
		return this.remark;
	}

	public TradeOrder setRemark(String remark) {
		if (remark != null && !remark.equals("")) {
			this.remark = remark;
		}
		return this;
	}

	@Override
	public String toString() {
		return "TradeOrder [id=" + id + ", tradeNo=" + tradeNo + ", orderNo=" + orderNo + ", culCommNo=" + culCommNo
				+ ", channelId=" + channelId + ", orderPoint=" + orderPoint + ", payPoint=" + payPoint
				+ ", discountPoint=" + discountPoint + ", orderType=" + orderType + ", tradeType=" + tradeType
				+ ", status=" + status + ", merchantNo=" + merchantNo + ", deviceInfo=" + deviceInfo + ", origOrderNo="
				+ origOrderNo + ", authCode=" + authCode + ", refundAuditStatus=" + refundAuditStatus + ", tradeDate="
				+ tradeDate + ", tradeTime=" + tradeTime + ", validStartTime=" + validStartTime + ", expireTime="
				+ expireTime + ", subject=" + subject + ", commodityDesc=" + commodityDesc + ", notifyUrl=" + notifyUrl
				+ ", returnUrl=" + returnUrl + ", createTime=" + createTime + ", createUser=" + createUser
				+ ", updateTime=" + updateTime + ", updateUser=" + updateUser + ", channelTxnNo=" + channelTxnNo
				+ ", thirdPaymentTxnid=" + thirdPaymentTxnid + ", thirdSellerAccount=" + thirdSellerAccount
				+ ", thirdBuyerAccount=" + thirdBuyerAccount + ", goodTags=" + goodTags + ", extendInfo=" + extendInfo
				+ ", failMsg=" + failMsg + ", payTypeLimit=" + payTypeLimit + ", remark=" + remark + "]";
	}
}
