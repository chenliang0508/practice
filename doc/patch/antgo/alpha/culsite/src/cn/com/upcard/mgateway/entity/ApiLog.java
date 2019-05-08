package cn.com.upcard.mgateway.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * <pre>
 * 收银通道api调用返回结果.
 * 
 * 使用3个精度为4000的varchar记录,若api返回结果超过12000个字节,超过的部分直接丢弃.
 * 
 * </pre>
 * @author huatingzhou
 * @version 1.0
 */
@Entity
@Table(name = "API_LOG")
public class ApiLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8876523032710711071L;
	
	@Id
	@GenericGenerator(name = "apiLogId", strategy = "uuid")
	@GeneratedValue(generator = "apiLogId")
	private String id;
	
	@Column(name="ORDER_ID")
	private String orderId;
	@Column(name="API_TYPE")
	private String apiType;
	@Column(name="RES_INFO_I")
	private String resInfoI;
	@Column(name="RES_INFO_II")
	private String resInfoIi;
	@Column(name="RES_INFO_III")
	private String resInfoIii;
	@Column(name="CREATE_TIME")
	private  Date createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getApiType() {
		return apiType;
	}
	public void setApiType(String apiType) {
		this.apiType = apiType;
	}
	public String getResInfoI() {
		return resInfoI;
	}
	public void setResInfoI(String resInfoI) {
		this.resInfoI = resInfoI;
	}
	public String getResInfoIi() {
		return resInfoIi;
	}
	public void setResInfoIi(String resInfoIi) {
		this.resInfoIi = resInfoIi;
	}
	public String getResInfoIii() {
		return resInfoIii;
	}
	public void setResInfoIii(String resInfoIii) {
		this.resInfoIii = resInfoIii;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
