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
 * 银商商户门店
 * @author chenliang
 *
 */
@Entity
@Table(name = "TP_CUL_COMMERICAL", uniqueConstraints = @UniqueConstraint(columnNames = "CUL_COMM_NO"))
public class TpCulCommerical implements Serializable {

	private static final long serialVersionUID = -2715317263534835887L;

	private String commId;
	private String agentId;
	private TpCulCommerical tpCulCommerical;
	private String agtSaleId;
	private String culCommNo;
	private String commFullName;
	private String commShortName;
	private String commType;
	private String orgAuditStatus;
	private String banksAuditStatus;
	private String orgStatus;
	private String provinceCode;
	private String cityCode;
	private String countyCode;
	private String commAddress;
	private String industryId;
	private String commKey;
	private String etpsType;
	private String etpsLicenseNo;
	private String isRefundAudit;
	private String fax;
	private String prTelephone;
	private String prName;
	private String prEmail;
	private String createrType;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String auditOrgId;
	private String auditor;
	private String qrcodePicPath;
	private String extCommNo;
	private String qrcodeUrl;
	private String clearType;
	private String applyWay;
	private String acqVeriCode;
	private Set<TpCulCommerical> tpCulCommericals = new HashSet<TpCulCommerical>(0);
//	private Set<TpCulCommPayType> tpCulCommPayTypes = new HashSet<TpCulCommPayType>(0);
	// Constructors

	/** default constructor */
	public TpCulCommerical() {
	}

	/** minimal constructor */
	public TpCulCommerical(String commId, String culCommNo, String commFullName, String commShortName, String commType,
			String orgAuditStatus, String orgStatus, String provinceCode, String cityCode, String industryId,
			String commKey, String etpsType, String isRefundAudit, String createrType, String createUser,
			Date createTime, String clearType) {
		this.commId = commId;
		this.culCommNo = culCommNo;
		this.commFullName = commFullName;
		this.commShortName = commShortName;
		this.commType = commType;
		this.orgAuditStatus = orgAuditStatus;
		this.orgStatus = orgStatus;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.industryId = industryId;
		this.commKey = commKey;
		this.etpsType = etpsType;
		this.isRefundAudit = isRefundAudit;
		this.createrType = createrType;
		this.createUser = createUser;
		this.createTime = createTime;
		this.clearType = clearType;
	}

	/** full constructor */
	public TpCulCommerical(String commId, String agentId, TpCulCommerical tpCulCommerical, String agtSaleId,
			String culCommNo, String commFullName, String commShortName, String commType, String orgAuditStatus,
			String banksAuditStatus, String orgStatus, String provinceCode, String cityCode, String countyCode,
			String commAddress, String industryId, String commKey, String etpsType, String etpsLicenseNo,
			String isRefundAudit, String fax, String prTelephone, String prName, String prEmail, String createrType,
			String createUser, Date createTime, String updateUser, Date updateTime, String auditOrgId, String auditor,
			String qrcodePicPath, String extCommNo, String qrcodeUrl, String clearType, String applyWay,
			String acqVeriCode, Set<TpCulCommerical> tpCulCommericals) {
		this.commId = commId;
		this.agentId = agentId;
		this.tpCulCommerical = tpCulCommerical;
		this.agtSaleId = agtSaleId;
		this.culCommNo = culCommNo;
		this.commFullName = commFullName;
		this.commShortName = commShortName;
		this.commType = commType;
		this.orgAuditStatus = orgAuditStatus;
		this.banksAuditStatus = banksAuditStatus;
		this.orgStatus = orgStatus;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.countyCode = countyCode;
		this.commAddress = commAddress;
		this.industryId = industryId;
		this.commKey = commKey;
		this.etpsType = etpsType;
		this.etpsLicenseNo = etpsLicenseNo;
		this.isRefundAudit = isRefundAudit;
		this.fax = fax;
		this.prTelephone = prTelephone;
		this.prName = prName;
		this.prEmail = prEmail;
		this.createrType = createrType;
		this.createUser = createUser;
		this.createTime = createTime;
		this.updateUser = updateUser;
		this.updateTime = updateTime;
		this.auditOrgId = auditOrgId;
		this.auditor = auditor;
		this.qrcodePicPath = qrcodePicPath;
		this.extCommNo = extCommNo;
		this.qrcodeUrl = qrcodeUrl;
		this.clearType = clearType;
		this.applyWay = applyWay;
		this.acqVeriCode = acqVeriCode;
		this.tpCulCommericals = tpCulCommericals;
//		this.tpCulCommPayTypes = tpCulCommPayTypes;
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
	public TpCulCommerical getTpCulCommerical() {
		return this.tpCulCommerical;
	}

	public void setTpCulCommerical(TpCulCommerical tpCulCommerical) {
		this.tpCulCommerical = tpCulCommerical;
	}

	@Column(name = "AGT_SALE_ID", length = 32)
	public String getAgtSaleId() {
		return this.agtSaleId;
	}

	public void setAgtSaleId(String agtSaleId) {
		this.agtSaleId = agtSaleId;
	}

	@Column(name = "CUL_COMM_NO", unique = true, nullable = false, length = 32)
	public String getCulCommNo() {
		return this.culCommNo;
	}

	public void setCulCommNo(String culCommNo) {
		this.culCommNo = culCommNo;
	}

	@Column(name = "COMM_FULL_NAME", nullable = false, length = 300)
	public String getCommFullName() {
		return this.commFullName;
	}

	public void setCommFullName(String commFullName) {
		this.commFullName = commFullName;
	}

	@Column(name = "COMM_SHORT_NAME", nullable = false, length = 100)
	public String getCommShortName() {
		return this.commShortName;
	}

	public void setCommShortName(String commShortName) {
		this.commShortName = commShortName;
	}

	@Column(name = "COMM_TYPE", nullable = false, length = 3)
	public String getCommType() {
		return this.commType;
	}

	public void setCommType(String commType) {
		this.commType = commType;
	}

	@Column(name = "ORG_AUDIT_STATUS", nullable = false, length = 2)
	public String getOrgAuditStatus() {
		return this.orgAuditStatus;
	}

	public void setOrgAuditStatus(String orgAuditStatus) {
		this.orgAuditStatus = orgAuditStatus;
	}

	@Column(name = "BANKS_AUDIT_STATUS", length = 2)
	public String getBanksAuditStatus() {
		return this.banksAuditStatus;
	}

	public void setBanksAuditStatus(String banksAuditStatus) {
		this.banksAuditStatus = banksAuditStatus;
	}

	@Column(name = "ORG_STATUS", nullable = false, length = 1)
	public String getOrgStatus() {
		return this.orgStatus;
	}

	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}

	@Column(name = "PROVINCE_CODE", nullable = false, length = 10)
	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	@Column(name = "CITY_CODE", nullable = false, length = 10)
	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "COUNTY_CODE", length = 10)
	public String getCountyCode() {
		return this.countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	@Column(name = "COMM_ADDRESS", length = 120)
	public String getCommAddress() {
		return this.commAddress;
	}

	public void setCommAddress(String commAddress) {
		this.commAddress = commAddress;
	}

	@Column(name = "INDUSTRY_ID", nullable = false, length = 12)
	public String getIndustryId() {
		return this.industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	@Column(name = "COMM_KEY", nullable = false, length = 32)
	public String getCommKey() {
		return this.commKey;
	}

	public void setCommKey(String commKey) {
		this.commKey = commKey;
	}

	@Column(name = "ETPS_TYPE", nullable = false, length = 4)
	public String getEtpsType() {
		return this.etpsType;
	}

	public void setEtpsType(String etpsType) {
		this.etpsType = etpsType;
	}

	@Column(name = "ETPS_LICENSE_NO", length = 40)
	public String getEtpsLicenseNo() {
		return this.etpsLicenseNo;
	}

	public void setEtpsLicenseNo(String etpsLicenseNo) {
		this.etpsLicenseNo = etpsLicenseNo;
	}

	@Column(name = "IS_REFUND_AUDIT", nullable = false, length = 1)
	public String getIsRefundAudit() {
		return this.isRefundAudit;
	}

	public void setIsRefundAudit(String isRefundAudit) {
		this.isRefundAudit = isRefundAudit;
	}

	@Column(name = "FAX", length = 20)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "PR_TELEPHONE", length = 30)
	public String getPrTelephone() {
		return this.prTelephone;
	}

	public void setPrTelephone(String prTelephone) {
		this.prTelephone = prTelephone;
	}

	@Column(name = "PR_NAME", length = 30)
	public String getPrName() {
		return this.prName;
	}

	public void setPrName(String prName) {
		this.prName = prName;
	}

	@Column(name = "PR_EMAIL", length = 50)
	public String getPrEmail() {
		return this.prEmail;
	}

	public void setPrEmail(String prEmail) {
		this.prEmail = prEmail;
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

	@Column(name = "EXT_COMM_NO", length = 50)
	public String getExtCommNo() {
		return this.extCommNo;
	}

	public void setExtCommNo(String extCommNo) {
		this.extCommNo = extCommNo;
	}

	@Column(name = "QRCODE_URL", length = 450)
	public String getQrcodeUrl() {
		return this.qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	@Column(name = "CLEAR_TYPE", nullable = false, length = 3)
	public String getClearType() {
		return this.clearType;
	}

	public void setClearType(String clearType) {
		this.clearType = clearType;
	}

	@Column(name = "APPLY_WAY", length = 5)
	public String getApplyWay() {
		return this.applyWay;
	}

	public void setApplyWay(String applyWay) {
		this.applyWay = applyWay;
	}

	@Column(name = "ACQ_VERI_CODE", length = 10)
	public String getAcqVeriCode() {
		return this.acqVeriCode;
	}

	public void setAcqVeriCode(String acqVeriCode) {
		this.acqVeriCode = acqVeriCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tpCulCommerical")
	public Set<TpCulCommerical> getTpCulCommericals() {
		return this.tpCulCommericals;
	}

	public void setTpCulCommericals(Set<TpCulCommerical> tpCulCommericals) {
		this.tpCulCommericals = tpCulCommericals;
	}
	
//	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="tpCulCommerical")
//    public Set<TpCulCommPayType> getTpCulCommPayTypes() {
//        return this.tpCulCommPayTypes;
//    }
    
//    public void setTpCulCommPayTypes(Set<TpCulCommPayType> tpCulCommPayTypes) {
//        this.tpCulCommPayTypes = tpCulCommPayTypes;
//    }
}