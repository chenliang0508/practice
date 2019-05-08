package cn.com.upcard.mgateway.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * 商户门店在第三方注册的信息
 * @author chenliang
 *
 */
@Entity
@Table(name = "TP_COMMERICAL_INFO", uniqueConstraints = @UniqueConstraint(columnNames = { "CUL_COMM_NO", "ACQUIRER_ID" }))
public class TpCommericalInfo implements Serializable {
	private static final long serialVersionUID = -5319372766208163350L;

	private String commId;
	private String agentId;
	private TpCommericalInfo tpCommericalInfo;
	private String culCommNo;
	private String acquirerId;
	private String commType;
	private String bankAuditStatus;
	private String commStatus;
	private String commKey;
	private String createrType;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String isRefundAudit;
	private String auditOrgType;
	private String auditOrgId;
	private String auditor;
	private String qrcodePicPath;
	private String qrcodeUrl;
	private String bankMerNo;
	private Set<TpCommericalInfo> tpCommericalInfos = new HashSet<TpCommericalInfo>(0);
	private Set<TpCommPayTypes> tpCommPayTypeses = new HashSet<TpCommPayTypes>(0);

	// Constructors

	/** default constructor */
	public TpCommericalInfo() {
	}

	/** minimal constructor */
	public TpCommericalInfo(String commId, String culCommNo, String acquirerId, String commType,
			String bankAuditStatus, String commStatus, String commKey, String createrType, String createUser,
			Date createTime, String isRefundAudit) {
		this.commId = commId;
		this.culCommNo = culCommNo;
		this.acquirerId = acquirerId;
		this.commType = commType;
		this.bankAuditStatus = bankAuditStatus;
		this.commStatus = commStatus;
		this.commKey = commKey;
		this.createrType = createrType;
		this.createUser = createUser;
		this.createTime = createTime;
		this.isRefundAudit = isRefundAudit;
	}

	/** full constructor */
	public TpCommericalInfo(String commId, String agentId, TpCommericalInfo tpCommericalInfo, String culCommNo,
			String acquirerId, String commType, String bankAuditStatus, String commStatus, String commKey,
			String createrType, String createUser, Date createTime, String updateUser, Date updateTime,
			String isRefundAudit, String auditOrgType, String auditOrgId, String auditor, String qrcodePicPath,
			String qrcodeUrl, String bankMerNo, Set<TpCommericalInfo> tpCommericalInfos,
			Set<TpCommPayTypes> tpCommPayTypeses) {
		this.commId = commId;
		this.agentId = agentId;
		this.tpCommericalInfo = tpCommericalInfo;
		this.culCommNo = culCommNo;
		this.acquirerId = acquirerId;
		this.commType = commType;
		this.bankAuditStatus = bankAuditStatus;
		this.commStatus = commStatus;
		this.commKey = commKey;
		this.createrType = createrType;
		this.createUser = createUser;
		this.createTime = createTime;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
		this.isRefundAudit = isRefundAudit;
		this.auditOrgType = auditOrgType;
		this.auditOrgId = auditOrgId;
		this.auditor = auditor;
		this.qrcodePicPath = qrcodePicPath;
		this.qrcodeUrl = qrcodeUrl;
		this.bankMerNo = bankMerNo;
		this.tpCommericalInfos = tpCommericalInfos;
		this.tpCommPayTypeses = tpCommPayTypeses;
	}

	// Property accessors
	@Id
	@Column(name = "COMM_ID", unique = true, nullable = false, length = 32)
	public String getCommId() {
		return this.commId;
	}

	public void setCommId(String commId) {
		this.commId = commId;
	}

	@Column(name = "AGENT_ID")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "P_COMM_ID")
	public TpCommericalInfo getTpCommericalInfo() {
		return this.tpCommericalInfo;
	}

	public void setTpCommericalInfo(TpCommericalInfo tpCommericalInfo) {
		this.tpCommericalInfo = tpCommericalInfo;
	}

	@Column(name = "CUL_COMM_NO", nullable = false, length = 32)
	public String getCulCommNo() {
		return this.culCommNo;
	}

	public void setCulCommNo(String culCommNo) {
		this.culCommNo = culCommNo;
	}

	@Column(name = "ACQUIRER_ID", nullable = false, length = 32)
	public String getAcquirerId() {
		return this.acquirerId;
	}

	public void setAcquirerId(String acquirerId) {
		this.acquirerId = acquirerId;
	}

	@Column(name = "COMM_TYPE", nullable = false, length = 3)
	public String getCommType() {
		return this.commType;
	}

	public void setCommType(String commType) {
		this.commType = commType;
	}

	@Column(name = "BANK_AUDIT_STATUS", nullable = false, length = 2)
	public String getBankAuditStatus() {
		return this.bankAuditStatus;
	}

	public void setBankAuditStatus(String bankAuditStatus) {
		this.bankAuditStatus = bankAuditStatus;
	}

	@Column(name = "COMM_STATUS", nullable = false, length = 1)
	public String getCommStatus() {
		return this.commStatus;
	}

	public void setCommStatus(String commStatus) {
		this.commStatus = commStatus;
	}

	@Column(name = "COMM_KEY", nullable = false, length = 32)
	public String getCommKey() {
		return this.commKey;
	}

	public void setCommKey(String commKey) {
		this.commKey = commKey;
	}

	@Column(name = "CREATER_TYPE", nullable = false, length = 3)
	public String getCreaterType() {
		return this.createrType;
	}

	public void setCreaterType(String createrType) {
		this.createrType = createrType;
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

	@Column(name = "IS_REFUND_AUDIT", nullable = false, length = 1)
	public String getIsRefundAudit() {
		return this.isRefundAudit;
	}

	public void setIsRefundAudit(String isRefundAudit) {
		this.isRefundAudit = isRefundAudit;
	}

	@Column(name = "AUDIT_ORG_TYPE", length = 3)
	public String getAuditOrgType() {
		return this.auditOrgType;
	}

	public void setAuditOrgType(String auditOrgType) {
		this.auditOrgType = auditOrgType;
	}

	@Column(name = "AUDIT_ORG_ID", length = 32)
	public String getAuditOrgId() {
		return this.auditOrgId;
	}

	public void setAuditOrgId(String auditOrgId) {
		this.auditOrgId = auditOrgId;
	}

	@Column(name = "AUDITOR", length = 32)
	public String getAuditor() {
		return this.auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	@Column(name = "QRCODE_PIC_PATH", length = 500)
	public String getQrcodePicPath() {
		return this.qrcodePicPath;
	}

	public void setQrcodePicPath(String qrcodePicPath) {
		this.qrcodePicPath = qrcodePicPath;
	}

	@Column(name = "QRCODE_URL", length = 450)
	public String getQrcodeUrl() {
		return this.qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	@Column(name = "BANK_MER_NO", length = 50)
	public String getBankMerNo() {
		return this.bankMerNo;
	}

	public void setBankMerNo(String bankMerNo) {
		this.bankMerNo = bankMerNo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tpCommericalInfo")
	public Set<TpCommericalInfo> getTpCommericalInfos() {
		return this.tpCommericalInfos;
	}

	public void setTpCommericalInfos(Set<TpCommericalInfo> tpCommericalInfos) {
		this.tpCommericalInfos = tpCommericalInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tpCommericalInfo")
	public Set<TpCommPayTypes> getTpCommPayTypeses() {
		return this.tpCommPayTypeses;
	}

	public void setTpCommPayTypeses(Set<TpCommPayTypes> tpCommPayTypeses) {
		this.tpCommPayTypeses = tpCommPayTypeses;
	}
}