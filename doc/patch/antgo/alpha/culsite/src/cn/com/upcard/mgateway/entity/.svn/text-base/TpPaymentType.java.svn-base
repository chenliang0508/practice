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

/**
 * 收银通道交易类型表
 */
@Entity
@Table(name = "TP_PAYMENT_TYPE")
public class TpPaymentType implements Serializable {
	private static final long serialVersionUID = 5523375596191948799L;
	// Fields

	private String payTypeId;
	private String payorgId;
	private String channelId;
	private TpAcquirer tpAcquirer;
	private String payTypeCode;
	private String payTypeName;
	private String status;
	private Date createTime;
	private Date updateTime;
	private Set<TpCommPayTypes> tpCommPayTypeses = new HashSet<TpCommPayTypes>(0);

	// Constructors

	/** default constructor */
	public TpPaymentType() {
	}

	/** minimal constructor */
	public TpPaymentType(String payTypeId, String payTypeCode, String payTypeName, String status, Date createTime) {
		this.payTypeId = payTypeId;
		this.payTypeCode = payTypeCode;
		this.payTypeName = payTypeName;
		this.status = status;
		this.createTime = createTime;
	}

	/** full constructor */
	public TpPaymentType(String payTypeId, String payorgId, String channelId, TpAcquirer tpAcquirer,
			String payTypeCode, String payTypeName, String status, Date createTime, Date updateTime,
			Set<TpCommPayTypes> tpCommPayTypeses) {
		this.payTypeId = payTypeId;
		this.payorgId = payorgId;
		this.channelId = channelId;
		this.tpAcquirer = tpAcquirer;
		this.payTypeCode = payTypeCode;
		this.payTypeName = payTypeName;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.tpCommPayTypeses = tpCommPayTypeses;
	}

	// Property accessors
	@Id
	@Column(name = "PAY_TYPE_ID", unique = true, nullable = false, length = 32)
	public String getPayTypeId() {
		return this.payTypeId;
	}

	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}

	@Column(name = "PAYORG_ID")
	public String getPayorgId() {
		return payorgId;
	}

	public void setPayorgId(String payorgId) {
		this.payorgId = payorgId;
	}

	@Column(name = "CHANNEL_ID")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACQUIRER_ID")
	public TpAcquirer getTpAcquirer() {
		return this.tpAcquirer;
	}

	public void setTpAcquirer(TpAcquirer tpAcquirer) {
		this.tpAcquirer = tpAcquirer;
	}

	@Column(name = "PAY_TYPE_CODE", nullable = false, length = 20)
	public String getPayTypeCode() {
		return this.payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}

	@Column(name = "PAY_TYPE_NAME", nullable = false, length = 50)
	public String getPayTypeName() {
		return this.payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tpPaymentType")
	public Set<TpCommPayTypes> getTpCommPayTypeses() {
		return this.tpCommPayTypeses;
	}

	public void setTpCommPayTypeses(Set<TpCommPayTypes> tpCommPayTypeses) {
		this.tpCommPayTypeses = tpCommPayTypeses;
	}
}