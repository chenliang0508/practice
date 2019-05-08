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
@Table(name = "ORDER_OPER")
public class OrderOper implements Serializable {

	private static final long serialVersionUID = -8126605181239604684L;

	@Id
	@GenericGenerator(name = "orderOperId", strategy = "uuid")
	@GeneratedValue(generator = "orderOperId")
	@Column(name = "ID", length = 32)
	private String id;

	@Column(name = "ORDER_ID")
	private String orderId;

	@Column(name = "ORDER_NO")
	private String orderNo;

	@Column(name = "COMM_UNION_NO")
	private String commUnionNo;

	@Column(name = "ORDER_POINT")
	private Integer orderPoint;

	@Column(name = "PAY_POINT")
	private Integer payPoint;

	@Column(name = "OPER_TYPE")
	private String operType;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "AUTH_CODE")
	private String authCode;

	@Column(name = "MERCHANT_NO")
	private String merchantNo;

	@Column(name = "DEVICE_INFO")
	private String deviceInfo;

	@Column(name = "APPLY_TYPE")
	private String applyType;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "CREATE_USER")
	private String createUser;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	@Column(name = "UPDATE_USER")
	private String updateUser;

	@Column(name = "THIRD_PAYMENT_TXNID")
	private String thirdPaymentTxnid;

	@Column(name = "REQ_IP")
	private String reqIp;

	@Column(name = "CHANNEL_TXN_NO")
	private String channelTxnNo;

	public String getId() {
		return id;
	}

	public OrderOper setId(String id) {
		this.id = id;
		return this;
	}

	public String getOrderId() {
		return orderId;
	}

	public OrderOper setOrderId(String orderId) {
		if (orderId != null && !orderId.equals("")) {
			this.orderId = orderId;
		}
		return this;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public OrderOper setOrderNo(String orderNo) {
		if (orderNo != null && !orderNo.equals("")) {
			this.orderNo = orderNo;
		}
		return this;
	}

	public String getCommUnionNo() {
		return commUnionNo;
	}

	public OrderOper setCommUnionNo(String commUnionNo) {
		if (commUnionNo != null && !commUnionNo.equals("")) {
			this.commUnionNo = commUnionNo;
		}
		return this;
	}

	public Integer getOrderPoint() {
		return orderPoint;
	}

	public OrderOper setOrderPoint(Integer orderPoint) {
		if (orderPoint != null) {
			this.orderPoint = orderPoint;
		}
		return this;
	}

	public Integer getPayPoint() {
		return payPoint;
	}

	public OrderOper setPayPoint(Integer payPoint) {
		if (payPoint != null) {
			this.payPoint = payPoint;
		}
		return this;
	}

	public String getOperType() {
		return operType;
	}

	public OrderOper setOperType(String operType) {
		if (operType != null && !operType.equals("")) {
			this.operType = operType;
		}
		return this;
	}

	public String getStatus() {
		return status;
	}

	public OrderOper setStatus(String status) {
		if (status != null && !status.equals("")) {
			this.status = status;
		}
		return this;
	}

	public String getAuthCode() {
		return authCode;
	}

	public OrderOper setAuthCode(String authCode) {
		if (authCode != null && !authCode.equals("")) {
			this.authCode = authCode;
		}
		return this;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public OrderOper setMerchantNo(String merchantNo) {
		if (merchantNo != null && !merchantNo.equals("")) {
			this.merchantNo = merchantNo;
		}
		return this;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public OrderOper setDeviceInfo(String deviceInfo) {
		if (deviceInfo != null && !deviceInfo.equals("")) {
			this.deviceInfo = deviceInfo;
		}
		return this;
	}

	public String getApplyType() {
		return applyType;
	}

	public OrderOper setApplyType(String applyType) {
		if (applyType != null && !applyType.equals("")) {
			this.applyType = applyType;
		}
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public OrderOper setCreateTime(Date createTime) {
		if (createTime != null) {
			this.createTime = createTime;
		}
		return this;
	}

	public String getCreateUser() {
		return createUser;
	}

	public OrderOper setCreateUser(String createUser) {
		if (createUser != null && !createUser.equals("")) {
			this.createUser = createUser;
		}
		return this;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public OrderOper setUpdateTime(Date updateTime) {
		if (updateTime != null && !updateTime.equals("")) {
			this.updateTime = updateTime;
		}
		return this;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public OrderOper setUpdateUser(String updateUser) {
		if (updateUser != null && !updateUser.equals("")) {
			this.updateUser = updateUser;
		}
		return this;
	}

	public String getThirdPaymentTxnid() {
		return thirdPaymentTxnid;
	}

	public void setThirdPaymentTxnid(String thirdPaymentTxnid) {
		if (thirdPaymentTxnid != null && !thirdPaymentTxnid.equals("")) {
			this.thirdPaymentTxnid = thirdPaymentTxnid;
		}
	}

	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		if (reqIp != null && !reqIp.equals("")) {
			this.reqIp = reqIp;
		}
	}

	public String getChannelTxnNo() {
		return channelTxnNo;
	}

	public void setChannelTxnNo(String channelTxnNo) {
		if (channelTxnNo != null && !channelTxnNo.equals("")) {
			this.channelTxnNo = channelTxnNo;
		}
	}

	@Override
	public String toString() {
		return "OrderOper [id=" + id + ", orderId=" + orderId + ", orderNo=" + orderNo + ", commUnionNo=" + commUnionNo
				+ ", orderPoint=" + orderPoint + ", payPoint=" + payPoint + ", operType=" + operType + ", status="
				+ status + ", authCode=" + authCode + ", merchantNo=" + merchantNo + ", deviceInfo=" + deviceInfo
				+ ", applyType=" + applyType + ", createTime=" + createTime + ", createUser=" + createUser
				+ ", updateTime=" + updateTime + ", updateUser=" + updateUser + ", thirdPaymentTxnid="
				+ thirdPaymentTxnid + ", reqIp=" + reqIp + ", channelTxnNo=" + channelTxnNo + "]";
	}
}
