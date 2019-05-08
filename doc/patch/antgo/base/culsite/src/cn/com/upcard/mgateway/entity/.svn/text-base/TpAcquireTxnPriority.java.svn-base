package cn.com.upcard.mgateway.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 收单机构交易顺序表
 * 
 * @author chenliang
 *
 */
@Entity
@Table(name = "TP_ACQUIRE_TXN_PRIORITY")
public class TpAcquireTxnPriority implements Serializable {
	private static final long serialVersionUID = -6662857483792780328L;
	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "PAY_TYPE_CODE")
	private String payTypeCode;

	@Column(name = "ACQUIRER_ID")
	private String acquirerId;

	@Column(name = "PRIORITY_NUM")
	private Integer priorityNum;

	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "CREATE_USER")
	private String createUser;
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	@Column(name = "UPDATE_USER")
	private String updateUser;

	public TpAcquireTxnPriority() {
		super();
	}

	public TpAcquireTxnPriority(String id, String payTypeCode, String acquirerId, Integer priorityNum, Date createTime,
			String createUser, Date updateTime, String updateUser) {
		super();
		this.id = id;
		this.payTypeCode = payTypeCode;
		this.acquirerId = acquirerId;
		this.priorityNum = priorityNum;
		this.createTime = createTime;
		this.createUser = createUser;
		this.updateTime = updateTime;
		this.updateUser = updateUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayTypeCode() {
		return payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}

	public String getAcquirerId() {
		return acquirerId;
	}

	public void setAcquirerId(String acquirerId) {
		this.acquirerId = acquirerId;
	}

	public Integer getPriorityNum() {
		return priorityNum;
	}

	public void setPriorityNum(Integer priorityNum) {
		this.priorityNum = priorityNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public String toString() {
		return "TpAcquireTxnPriority [id=" + id + ", payTypeCode=" + payTypeCode + ", acquirerId=" + acquirerId
				+ ", priorityNum=" + priorityNum + ", createTime=" + createTime + ", createUser=" + createUser
				+ ", updateTime=" + updateTime + ", updateUser=" + updateUser + "]";
	}
	
	public static class PriortyType {
		private static int priorty = 1;

		public static int getPriorty() {
			return priorty;
		}
	}
}
