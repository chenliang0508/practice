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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 银行收银通道表
 */
@Entity
@Table(name = "TP_ACQUIRER")
public class TpAcquirer implements Serializable {
	private static final long serialVersionUID = -8257927458606692316L;
	private String acquirerId;
	private String acquirerName;
	private String bankIdtCode;
	private String bankName;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String contractPath;
	private String isDefault;
	private Set<TpCommPayTypes> tpCommPayTypeses = new HashSet<TpCommPayTypes>(0);
	private Set<TpPaymentType> tpPaymentTypes = new HashSet<TpPaymentType>(0);

	// Constructors

	/** default constructor */
	public TpAcquirer() {
	}

	/** minimal constructor */
	public TpAcquirer(String acquirerId, String acquirerName, String bankIdtCode, String bankName, String createUser,
			Date createTime, String isDefault) {
		this.acquirerId = acquirerId;
		this.acquirerName = acquirerName;
		this.bankIdtCode = bankIdtCode;
		this.bankName = bankName;
		this.createUser = createUser;
		this.createTime = createTime;
		this.isDefault = isDefault;
	}

	/** full constructor */
	public TpAcquirer(String acquirerId, String acquirerName, String bankIdtCode, String bankName, String createUser,
			Date createTime, String updateUser, Date updateTime, String contractPath, String isDefault,
			Set<TpCommPayTypes> tpCommPayTypeses, Set<TpPaymentType> tpPaymentTypes) {
		this.acquirerId = acquirerId;
		this.acquirerName = acquirerName;
		this.bankIdtCode = bankIdtCode;
		this.bankName = bankName;
		this.createUser = createUser;
		this.createTime = createTime;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
		this.contractPath = contractPath;
		this.isDefault = isDefault;
		this.tpCommPayTypeses = tpCommPayTypeses;
		this.tpPaymentTypes = tpPaymentTypes;
	}

	// Property accessors
	@Id
	@Column(name = "ACQUIRER_ID", unique = true, nullable = false, length = 32)
	public String getAcquirerId() {
		return this.acquirerId;
	}

	public void setAcquirerId(String acquirerId) {
		this.acquirerId = acquirerId;
	}

	@Column(name = "ACQUIRER_NAME", nullable = false, length = 60)
	public String getAcquirerName() {
		return this.acquirerName;
	}

	public void setAcquirerName(String acquirerName) {
		this.acquirerName = acquirerName;
	}

	@Column(name = "BANK_IDT_CODE", nullable = false, length = 10)
	public String getBankIdtCode() {
		return this.bankIdtCode;
	}

	public void setBankIdtCode(String bankIdtCode) {
		this.bankIdtCode = bankIdtCode;
	}

	@Column(name = "BANK_NAME", nullable = false, length = 50)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	@Column(name = "CONTRACT_PATH", length = 300)
	public String getContractPath() {
		return this.contractPath;
	}

	public void setContractPath(String contractPath) {
		this.contractPath = contractPath;
	}

	@Column(name = "IS_DEFAULT", nullable = false, length = 1)
	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tpAcquirer")
	public Set<TpCommPayTypes> getTpCommPayTypeses() {
		return this.tpCommPayTypeses;
	}

	public void setTpCommPayTypeses(Set<TpCommPayTypes> tpCommPayTypeses) {
		this.tpCommPayTypeses = tpCommPayTypeses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tpAcquirer")
	public Set<TpPaymentType> getTpPaymentTypes() {
		return this.tpPaymentTypes;
	}

	public void setTpPaymentTypes(Set<TpPaymentType> tpPaymentTypes) {
		this.tpPaymentTypes = tpPaymentTypes;
	}
}