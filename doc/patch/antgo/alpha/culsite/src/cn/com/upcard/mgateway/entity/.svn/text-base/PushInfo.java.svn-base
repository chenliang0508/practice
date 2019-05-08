package cn.com.upcard.mgateway.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Table(name = "PUSH_INFO")
@Entity
public class PushInfo implements Serializable {

	private static final long serialVersionUID = 1670734871452771944L;

	@Id
	@GenericGenerator(name = "pushInfoId", strategy = "uuid")
	@GeneratedValue(generator = "pushInfoId")
	private String id;

	@Column(name = "ORDER_ID")
	private String orderId;

	@Column(name = "NOTIFY_URL")
	private String notifyUrl;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "PUSH_TIME")
	private String pushTime;

	@Column(name = "PUSH_NUMBER")
	private String pushNumber;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public String getId() {
		return id;
	}

	public PushInfo setId(String id) {
		this.id = id;
		return this;
	}

	public String getOrderId() {
		return orderId;
	}

	public PushInfo setOrderId(String orderId) {
		this.orderId = orderId;
		return this;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public PushInfo setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
		return this;
	}

	public String getContent() {
		return content;
	}

	public PushInfo setContent(String content) {
		this.content = content;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public PushInfo setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getPushTime() {
		return pushTime;
	}

	public PushInfo setPushTime(String pushTime) {
		this.pushTime = pushTime;
		return this;
	}

	public String getPushNumber() {
		return pushNumber;
	}

	public PushInfo setPushNumber(String pushNumber) {
		this.pushNumber = pushNumber;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public PushInfo setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public PushInfo setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}
}
