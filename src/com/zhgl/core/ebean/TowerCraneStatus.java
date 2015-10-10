package com.zhgl.core.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 塔机安用拆记录表
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_towerCraneStatus")
public class TowerCraneStatus {
	@Id
	private String id;
	/** ************************安装信息START**************** */
	/** 工程ID */
	private String projectId;
	/** 工程名称 */
	private String projectName;
	/** 工程地址 */
	private String projectAddress;
	/** 安装时间 */
	private Date installDate;
	/** 联系电话 */
	private String ContactPhone;
	/** 施工总承包单位 */
	private String contractUnit;
	/** 监理单位 */
	private String supervisionUnit;
	// /////////////////////////////////////////////////////////
	/** 联系人 */
	private String contactPerson;
	/** 安装单位ID */
	private String installUnitId;
	/** 安装单位名称 */
	private String installUnitName;
	/** 安装单位资质证 */
	private String installQualificationLicense;
	/** 安装单位安许证 */
	private String installPermitLicense;

	/** 有无安装方案 */
	private Boolean getInstallPlan;
	/** 首次安装高度 */
	private String firstInstallHeight;
	/** 最终使用高度 */
	private String finalUseHeight;
	/** 添加时间 */
	private Date createTime;
	/** 通过时间 */
	private Date approveTime;
	/** 资质类别 */
	private String zzlb;
	/** 资质等级 */
	private String zzdj;
	/** 安装单位联系人 */
	private String installPerson;
	/** 安装单位联系人 */
	private String installPhone;
	/** ************************安装信息END**************** */

	/** ************************使用信息START**************** */
	/** 使用编号 */
	@Column(unique = true)
	private String useNumber;
	/** 使用单位 */
	private String useUnitName;
	/** 项目经理 */
	private String pmName;
	/** 项目经理联系电话 */
	private String pmPhone;
	/** 验收日期 */
	private String acceptDate;
	/** 安装检测单位 */
	private String installCheckUnit;
	/** 检查日期 */
	private Date InstallCheckDate;
	/** 登记日期 */
	private Date useRecordDate;
	// /////////////////////////////////////////////////
	/** 使用登记编号 */
	private String useRecordNumber;
	/** 使用单位ID */
	private String useUnitID;
	/** 使用单位资质登记 */
	private String useQualificationLicense;
	/** 使用单位联系人 */
	private String useUnitContactPerson;
	/** 使用单位联系电话 */
	private String useUnitContactPersonPhone;
	/** 使用单位手机 */
	private String useUnitTelephone;
	/** 建造师 */
	private String constructor;
	/** 建造师联系电话 */
	private String constructorPhone;
	/** 安装检测报告编号 */
	private String installCheckReportNumber;
	/** 检测结论 */
	private String installCheckConclusion;
	/** 安全负责人 */
	private String safetyManager;
	/** 安全负责人联系电话 */
	private String safetyManagerPhone;
	/** 使用开始时间 */
	private String useStartDate;
	/** 使用结束时间 */
	private String useEndDate;
	/** ************************使用信息END *****************/

	/** ************************拆卸信息START ****************/

	/** ************************拆卸信息END *****************/

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "towercrane_id")
	private TowerCrane towerCrane;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "soim_id")
	private SocketImei socketImei;

	private String fiid; // 安装信息报省平台后返回的ID
	@Column(unique = true, nullable = false)
	private String fuid;// 使用信息报省平台后返回的ID
	private String fcid;// 拆卸信息报省平台后返回的ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TowerCrane getTowerCrane() {
		return towerCrane;
	}

	public void setTowerCrane(TowerCrane towerCrane) {
		this.towerCrane = towerCrane;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public String getContactPhone() {
		return ContactPhone;
	}

	public void setContactPhone(String contactPhone) {
		ContactPhone = contactPhone;
	}

	public String getContractUnit() {
		return contractUnit;
	}

	public void setContractUnit(String contractUnit) {
		this.contractUnit = contractUnit;
	}

	public String getSupervisionUnit() {
		return supervisionUnit;
	}

	public void setSupervisionUnit(String supervisionUnit) {
		this.supervisionUnit = supervisionUnit;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getInstallUnitId() {
		return installUnitId;
	}

	public void setInstallUnitId(String installUnitId) {
		this.installUnitId = installUnitId;
	}

	public String getInstallUnitName() {
		return installUnitName;
	}

	public void setInstallUnitName(String installUnitName) {
		this.installUnitName = installUnitName;
	}

	public String getInstallQualificationLicense() {
		return installQualificationLicense;
	}

	public void setInstallQualificationLicense(
			String installQualificationLicense) {
		this.installQualificationLicense = installQualificationLicense;
	}

	public String getInstallPermitLicense() {
		return installPermitLicense;
	}

	public void setInstallPermitLicense(String installPermitLicense) {
		this.installPermitLicense = installPermitLicense;
	}

	public Boolean getGetInstallPlan() {
		return getInstallPlan;
	}

	public void setGetInstallPlan(Boolean getInstallPlan) {
		this.getInstallPlan = getInstallPlan;
	}

	public String getFirstInstallHeight() {
		return firstInstallHeight;
	}

	public void setFirstInstallHeight(String firstInstallHeight) {
		this.firstInstallHeight = firstInstallHeight;
	}

	public String getFinalUseHeight() {
		return finalUseHeight;
	}

	public void setFinalUseHeight(String finalUseHeight) {
		this.finalUseHeight = finalUseHeight;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getZzlb() {
		return zzlb;
	}

	public void setZzlb(String zzlb) {
		this.zzlb = zzlb;
	}

	public String getZzdj() {
		return zzdj;
	}

	public void setZzdj(String zzdj) {
		this.zzdj = zzdj;
	}

	public String getInstallPerson() {
		return installPerson;
	}

	public void setInstallPerson(String installPerson) {
		this.installPerson = installPerson;
	}

	public String getInstallPhone() {
		return installPhone;
	}

	public void setInstallPhone(String installPhone) {
		this.installPhone = installPhone;
	}

	public String getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(String useNumber) {
		this.useNumber = useNumber;
	}

	public String getUseUnitName() {
		return useUnitName;
	}

	public void setUseUnitName(String useUnitName) {
		this.useUnitName = useUnitName;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public String getPmPhone() {
		return pmPhone;
	}

	public void setPmPhone(String pmPhone) {
		this.pmPhone = pmPhone;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getInstallCheckUnit() {
		return installCheckUnit;
	}

	public void setInstallCheckUnit(String installCheckUnit) {
		this.installCheckUnit = installCheckUnit;
	}

	public Date getInstallCheckDate() {
		return InstallCheckDate;
	}

	public void setInstallCheckDate(Date installCheckDate) {
		InstallCheckDate = installCheckDate;
	}

	public Date getUseRecordDate() {
		return useRecordDate;
	}

	public void setUseRecordDate(Date useRecordDate) {
		this.useRecordDate = useRecordDate;
	}

	public String getUseRecordNumber() {
		return useRecordNumber;
	}

	public void setUseRecordNumber(String useRecordNumber) {
		this.useRecordNumber = useRecordNumber;
	}

	public String getUseUnitID() {
		return useUnitID;
	}

	public void setUseUnitID(String useUnitID) {
		this.useUnitID = useUnitID;
	}

	public String getUseQualificationLicense() {
		return useQualificationLicense;
	}

	public void setUseQualificationLicense(String useQualificationLicense) {
		this.useQualificationLicense = useQualificationLicense;
	}

	public String getUseUnitContactPerson() {
		return useUnitContactPerson;
	}

	public void setUseUnitContactPerson(String useUnitContactPerson) {
		this.useUnitContactPerson = useUnitContactPerson;
	}

	public String getUseUnitContactPersonPhone() {
		return useUnitContactPersonPhone;
	}

	public void setUseUnitContactPersonPhone(String useUnitContactPersonPhone) {
		this.useUnitContactPersonPhone = useUnitContactPersonPhone;
	}

	public String getUseUnitTelephone() {
		return useUnitTelephone;
	}

	public void setUseUnitTelephone(String useUnitTelephone) {
		this.useUnitTelephone = useUnitTelephone;
	}

	public String getConstructor() {
		return constructor;
	}

	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}

	public String getConstructorPhone() {
		return constructorPhone;
	}

	public void setConstructorPhone(String constructorPhone) {
		this.constructorPhone = constructorPhone;
	}

	public String getInstallCheckReportNumber() {
		return installCheckReportNumber;
	}

	public void setInstallCheckReportNumber(String installCheckReportNumber) {
		this.installCheckReportNumber = installCheckReportNumber;
	}

	public String getInstallCheckConclusion() {
		return installCheckConclusion;
	}

	public void setInstallCheckConclusion(String installCheckConclusion) {
		this.installCheckConclusion = installCheckConclusion;
	}

	public String getSafetyManager() {
		return safetyManager;
	}

	public void setSafetyManager(String safetyManager) {
		this.safetyManager = safetyManager;
	}

	public String getSafetyManagerPhone() {
		return safetyManagerPhone;
	}

	public void setSafetyManagerPhone(String safetyManagerPhone) {
		this.safetyManagerPhone = safetyManagerPhone;
	}

	public String getUseStartDate() {
		return useStartDate;
	}

	public void setUseStartDate(String useStartDate) {
		this.useStartDate = useStartDate;
	}

	public String getUseEndDate() {
		return useEndDate;
	}

	public void setUseEndDate(String useEndDate) {
		this.useEndDate = useEndDate;
	}

	public SocketImei getSocketImei() {
		return socketImei;
	}

	public void setSocketImei(SocketImei socketImei) {
		this.socketImei = socketImei;
	}

	public String getFiid() {
		return fiid;
	}

	public void setFiid(String fiid) {
		this.fiid = fiid;
	}

	public String getFuid() {
		return fuid;
	}

	public void setFuid(String fuid) {
		this.fuid = fuid;
	}

	public String getFcid() {
		return fcid;
	}

	public void setFcid(String fcid) {
		this.fcid = fcid;
	}

}