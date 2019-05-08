package cn.com.upcard.mgateway.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 商户支付类型
 * 
 * @author chenliang
 * 
 */
@Entity
@Table(name = "TP_COMM_PAY_TYPES")
public class TpCommPayTypes implements Serializable {
	private static final long serialVersionUID = -3849380609034288163L;
	// Fields

	private String id;
	private String channelId;
	private TpAcquirer tpAcquirer;
	private TpPaymentType tpPaymentType;
	private TpCommericalInfo tpCommericalInfo;
	private String closingCostType;
	private BigDecimal closingCost;
	private String status;
	private String auditStatus;
	private String isAllowCreCard;
	private BigDecimal minTxnAmount;
	private BigDecimal maxTxnAmount;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String culCommNo;

	// Constructors

	/** default constructor */
	public TpCommPayTypes() {
	}

	/** minimal constructor */
	public TpCommPayTypes(String id, String closingCostType, BigDecimal closingCost, String status, String auditStatus,
			BigDecimal minTxnAmount, BigDecimal maxTxnAmount, String createUser, Date createTime, String culCommNo) {
		this.id = id;
		this.closingCostType = closingCostType;
		this.closingCost = closingCost;
		this.status = status;
		this.auditStatus = auditStatus;
		this.minTxnAmount = minTxnAmount;
		this.maxTxnAmount = maxTxnAmount;
		this.createUser = createUser;
		this.createTime = createTime;
		this.culCommNo = culCommNo;
	}

	/** full constructor */
	public TpCommPayTypes(String id, String channelId, TpAcquirer tpAcquirer, TpPaymentType tpPaymentType,
			TpCommericalInfo tpCommericalInfo, String closingCostType, BigDecimal closingCost, String status,
			String auditStatus, String isAllowCreCard, BigDecimal minTxnAmount, BigDecimal maxTxnAmount,
			String createUser, Date createTime, String updateUser, Date updateTime, String culCommNo) {
		this.id = id;
		this.channelId = channelId;
		this.tpAcquirer = tpAcquirer;
		this.tpPaymentType = tpPaymentType;
		this.tpCommericalInfo = tpCommericalInfo;
		this.closingCostType = closingCostType;
		this.closingCost = closingCost;
		this.status = status;
		this.auditStatus = auditStatus;
		this.isAllowCreCard = isAllowCreCard;
		this.minTxnAmount = minTxnAmount;
		this.maxTxnAmount = maxTxnAmount;
		this.createUser = createUser;
		this.createTime = createTime;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
		this.culCommNo = culCommNo;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CHANNEL_ID")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOB_PAY_APPID")
	public TpAcquirer getTpAcquirer() {
		return this.tpAcquirer;
	}

	public void setTpAcquirer(TpAcquirer tpAcquirer) {
		this.tpAcquirer = tpAcquirer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAY_TYPE_ID")
	public TpPaymentType getTpPaymentType() {
		return this.tpPaymentType;
	}

	public void setTpPaymentType(TpPaymentType tpPaymentType) {
		this.tpPaymentType = tpPaymentType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COM_ID")
	public TpCommericalInfo getTpCommericalInfo() {
		return this.tpCommericalInfo;
	}

	public void setTpCommericalInfo(TpCommericalInfo tpCommericalInfo) {
		this.tpCommericalInfo = tpCommericalInfo;
	}

	@Column(name = "CLOSING_COST_TYPE", nullable = false, length = 3)
	public String getClosingCostType() {
		return this.closingCostType;
	}

	public void setClosingCostType(String closingCostType) {
		this.closingCostType = closingCostType;
	}

	@Column(name = "CLOSING_COST", nullable = false, precision = 11, scale = 4)
	public BigDecimal getClosingCost() {
		return this.closingCost;
	}

	public void setClosingCost(BigDecimal closingCost) {
		this.closingCost = closingCost;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "AUDIT_STATUS", nullable = false, length = 1)
	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "IS_ALLOW_CRE_CARD", length = 1)
	public String getIsAllowCreCard() {
		return this.isAllowCreCard;
	}

	public void setIsAllowCreCard(String isAllowCreCard) {
		this.isAllowCreCard = isAllowCreCard;
	}

	@Column(name = "MIN_TXN_AMOUNT", nullable = false, precision = 15, scale = 4)
	public BigDecimal getMinTxnAmount() {
		return this.minTxnAmount;
	}

	public void setMinTxnAmount(BigDecimal minTxnAmount) {
		this.minTxnAmount = minTxnAmount;
	}

	@Column(name = "MAX_TXN_AMOUNT", nullable = false, precision = 15, scale = 4)
	public BigDecimal getMaxTxnAmount() {
		return this.maxTxnAmount;
	}

	public void setMaxTxnAmount(BigDecimal maxTxnAmount) {
		this.maxTxnAmount = maxTxnAmount;
	}

	@Column(name = "CREATE_USER", nullable = false, length = 30)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_USER", length = 30)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "CUL_COMM_NO", nullable = false, length = 32)
	public String getCulCommNo() {
		return this.culCommNo;
	}

	public void setCulCommNo(String culCommNo) {
		this.culCommNo = culCommNo;
	}

}