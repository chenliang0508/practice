package cn.com.upcard.mgateway.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 银行收银通道表
 */
@Entity
@Table(name = "TP_PAY_CHANNEL")
public class TpPayChannel implements Serializable {
	private static final long serialVersionUID = 5523375596191948799L;
	@Id
	@Column(name = "CHANNEL_ID", unique = true, nullable = false)
	private String channelId;
	@Column(name = "ACQUIRER_ID")
	private String acquirerId;
	@Column(name = "PAYORG_ID")
	private String payorgId;
	@Column(name = "CHANNEL_NAME")
	private String channelName;
	@Column(name = "STATUS")
	private char status;
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getAcquirerId() {
		return acquirerId;
	}
	public void setAcquirerId(String acquirerId) {
		this.acquirerId = acquirerId;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public String getPayorgId() {
		return payorgId;
	}
	public void setPayorgId(String payorgId) {
		this.payorgId = payorgId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}