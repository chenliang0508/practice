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
 * 银商商户支付类型，默认的支付通道
 * @author chenliang
 *
 */
@Entity
@Table(name = "TP_CUL_COMM_PAY_TYPE")
public class TpCulCommPayType implements Serializable {
	private static final long serialVersionUID = -7594991753907865586L;
	private String comPayId;
	private String commId;
	private String payTypeId;
	private String channelId;
	private String mobPayAppid;
	private String closingCostType;
	private BigDecimal closingCost;
	private String status;
	private String audDesc;
	private String auditStatus;
	private String auditor;
	private String adtOrgId;
	private String isAllowCreCard;
	private BigDecimal minTxnAmount;
	private BigDecimal maxTxnAmount;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String isDefChannel;
	private String culCommNo;

	/** default constructor */
	public TpCulCommPayType() {
	}

	/** minimal constructor */
	public TpCulCommPayType(String comPayId, String closingCostType, BigDecimal closingCost, String status,
			String auditStatus, String auditor, String adtOrgId, BigDecimal minTxnAmount, BigDecimal maxTxnAmount,
			String createUser, Date createTime, String isDefChannel, String culCommNo) {
		this.comPayId = comPayId;
		this.closingCostType = closingCostType;
		this.closingCost = closingCost;
		this.status = status;
		this.auditStatus = auditStatus;
		this.auditor = auditor;
		this.adtOrgId = adtOrgId;
		this.minTxnAmount = minTxnAmount;
		this.maxTxnAmount = maxTxnAmount;
		this.createUser = createUser;
		this.createTime = createTime;
		this.isDefChannel = isDefChannel;
		this.culCommNo = culCommNo;
	}

	/** full constructor */
	public TpCulCommPayType(String comPayId, String commId, String payTypeId, String channelId,
			String mobPayAppid, String closingCostType, BigDecimal closingCost, String status, String audDesc,
			String auditStatus, String auditor, String adtOrgId, String isAllowCreCard, BigDecimal minTxnAmount,
			BigDecimal maxTxnAmount, String createUser, Date createTime, String updateUser, Date updateTime,
			String isDefChannel, String culCommNo) {
		this.comPayId = comPayId;
		this.commId = commId;
		this.payTypeId = payTypeId;
		this.channelId = channelId;
		this.mobPayAppid = mobPayAppid;
		this.closingCostType = closingCostType;
		this.closingCost = closingCost;
		this.status = status;
		this.audDesc = audDesc;
		this.auditStatus = auditStatus;
		this.auditor = auditor;
		this.adtOrgId = adtOrgId;
		this.isAllowCreCard = isAllowCreCard;
		this.minTxnAmount = minTxnAmount;
		this.maxTxnAmount = maxTxnAmount;
		this.createUser = createUser;
		this.createTime = createTime;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
		this.isDefChannel = isDefChannel;
		this.culCommNo = culCommNo;
	}

	// Property accessors
	@Id
	@Column(name = "COM_PAY_ID", unique = true, nullable = false, length = 32)
	public String getComPayId() {
		return this.comPayId;
	}

	public void setComPayId(String comPayId) {
		this.comPayId = comPayId;
	}

	@Column(name = "PAY_TYPE_ID", length = 32)
	public String getPayTypeId() {
		return this.payTypeId;
	}

	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}

	@Column(name = "CHANNEL_ID", length = 32)
	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name = "MOB_PAY_APPID", length = 32)
	public String getMobPayAppid() {
		return this.mobPayAppid;
	}

	public void setMobPayAppid(String mobPayAppid) {
		this.mobPayAppid = mobPayAppid;
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

	@Column(name = "AUD_DESC", length = 400)
	public String getAudDesc() {
		return this.audDesc;
	}

	public void setAudDesc(String audDesc) {
		this.audDesc = audDesc;
	}

	@Column(name = "AUDIT_STATUS", nullable = false, length = 1)
	public String getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	@Column(name = "AUDITOR", nullable = false, length = 32)
	public String getAuditor() {
		return this.auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	@Column(name = "ADT_ORG_ID", nullable = false, length = 32)
	public String getAdtOrgId() {
		return this.adtOrgId;
	}

	public void setAdtOrgId(String adtOrgId) {
		this.adtOrgId = adtOrgId;
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

	@Column(name = "IS_DEF_CHANNEL", nullable = false, length = 1)
	public String getIsDefChannel() {
		return this.isDefChannel;
	}

	public void setIsDefChannel(String isDefChannel) {
		this.isDefChannel = isDefChannel;
	}

	@Column(name = "CUL_COMM_NO", nullable = false, length = 32)
	public String getCulCommNo() {
		return this.culCommNo;
	}

	public void setCulCommNo(String culCommNo) {
		this.culCommNo = culCommNo;
	}
	@Column(name = "COMM_ID")
	public String getCommId() {
		return commId;
	}

	public void setCommId(String commId) {
		this.commId = commId;
	}
}