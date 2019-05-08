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
@Table(name = "ORDER_DISCOUNT_INFO")
public class OrderDiscountInfo implements Serializable {

	private static final long serialVersionUID = 4029971386417900567L;

	@Id
	@GenericGenerator(name = "discountId", strategy = "uuid")
	@GeneratedValue(generator = "discountId")
	private String id;

	@Column(name = "ORDER_ID")
	private String orderId;

	@Column(name = "DISCOUNT_TYPE")
	private String discountType;

	@Column(name = "POINT")
	private Integer point;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	public String getId() {
		return id;
	}

	public OrderDiscountInfo setId(String id) {
		this.id = id;
		return this;
	}

	public String getOrderId() {
		return orderId;
	}

	public OrderDiscountInfo setOrderId(String orderId) {
		this.orderId = orderId;
		return this;
	}

	public String getDiscountType() {
		return discountType;
	}

	public OrderDiscountInfo setDiscountType(String discountType) {
		this.discountType = discountType;
		return this;
	}

	public Integer getPoint() {
		return point;
	}

	public OrderDiscountInfo setPoint(Integer point) {
		this.point = point;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
