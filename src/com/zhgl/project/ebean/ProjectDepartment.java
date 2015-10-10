package com.zhgl.project.ebean;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 项目部
 */
@Entity
@Table(name = "t_projectDepartment")
public class ProjectDepartment {
	@Id
	private String id;
	private String name;// 项目名称
	private String recordnumber; // 安监备案号
	private String address;// 地址
	@Temporal(TemporalType.DATE)
	private Date startDate;// 施工开始时间
	@Temporal(TemporalType.DATE)
	private Date endDate;// 施工结束时间
	// @ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch =
	// FetchType.EAGER)
	// @JoinColumn(name = "area_id")
	// private Area area;// 所属区域
	private String manager;// 项目经理
	private String phone; // 项目经理电话
	private String constructUnit; // 建设单位

	// @ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch =
	// FetchType.EAGER)
	// @JoinColumn(name = "unituse_id")
	// private Unit buildUnit;// 施工单位
	private String buildUnitName;// 施工单位名称
	private String buildPerson;// 施工负责人
	private String buildPhone;// 施工单位电话

//	@ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.EAGER)
//	@JoinColumn(name = "unitsupervision_id")
//	private Unit supervisionUnit;// 监理单位
	private String supervisionUnitName;// 监理单位名称
	private String supervisionPeron;// 总监
	private String supervisionPhone; // 总监电话
	private String safetyPerson; // 安全工程师
	private String safetyPhone; // 安全工程师电话
	private String xcdb;// 现场代表
	private String xcdbPhone;// 现场代表电话
	private String xcresPeople;// 现场负责人
	private String xcresPelplePhone;// 现场负责人电话
	@Lob
	private String note; // 备注
	private Boolean visible = true;// 逻辑符号
	private Boolean imp = false; // 是否从省平台导入 true为导入

	// 判断是否是Excel导入还是安装时产权单位填写的项目部
	private int state = 0;// 0：省平台导入 1： Excel 导入 2：安装时产权单位添加 3：注册添加
	// @ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch =
	// FetchType.EAGER)
	// @JoinColumn(name = "accountID")
	// private Account importAccount;// 导入者用户ID

	@ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Project project;// 所属工程

	private String fProjectID;// 同步到省上的项目部ID

	private int jdstate = 1;// 监督权限：1表示监督单位为市本级，2表示监督单位同行政区域。
	@Temporal(TemporalType.DATE)
	private Date createTime;
	@Transient
	private boolean signRegister;// 是否注册账号
	@Transient
	private String accountUsername;// 注册账号
	private int advance = 0;// 是否是提前服务,若是则不显示 0.正常 1.提前服务
	private Boolean proIf = false;// 是否省属

	private String oldDepartmentName;// 原项目部名称
	private Integer oldOrigin;// 原信息来源
	@Temporal(TemporalType.DATE)
	private Date creDate;// 原生成日期
	@Temporal(TemporalType.DATE)
	private Date updateDate;// 录入日期

	public String getStateVal() {
		String staVal = "";
		if (state == 0) {
			staVal = "省平台导入";
		}
		if (state == 1) {
			staVal = "Excel导入";
		}
		if (state == 2) {
			staVal = "安装时产权单位添加 ";
		}
		if (state == 3) {
			staVal = "注册添加";
		}
		return staVal;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getAdvance() {
		return advance;
	}

	public void setAdvance(int advance) {
		this.advance = advance;
	}

	public int getJdstate() {
		return jdstate;
	}

	public void setJdstate(int jdstate) {
		this.jdstate = jdstate;
	}

	public String getFProjectID() {
		return fProjectID;
	}

	public void setFProjectID(String projectID) {
		fProjectID = projectID;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRecordnumber() {
		return recordnumber;
	}

	public void setRecordnumber(String recordnumber) {
		this.recordnumber = recordnumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBuildPerson() {
		return buildPerson;
	}

	public void setBuildPerson(String buildPerson) {
		this.buildPerson = buildPerson;
	}

	public String getBuildPhone() {
		return buildPhone;
	}

	public void setBuildPhone(String buildPhone) {
		this.buildPhone = buildPhone;
	}

	public String getSupervisionPeron() {
		return supervisionPeron;
	}

	public void setSupervisionPeron(String supervisionPeron) {
		this.supervisionPeron = supervisionPeron;
	}

	public String getSupervisionPhone() {
		return supervisionPhone;
	}

	public void setSupervisionPhone(String supervisionPhone) {
		this.supervisionPhone = supervisionPhone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getConstructUnit() {
		return constructUnit;
	}

	public void setConstructUnit(String constructUnit) {
		this.constructUnit = constructUnit;
	}

	public String getSafetyPerson() {
		return safetyPerson;
	}

	public void setSafetyPerson(String safetyPerson) {
		this.safetyPerson = safetyPerson;
	}

	public String getSafetyPhone() {
		return safetyPhone;
	}

	public void setSafetyPhone(String safetyPhone) {
		this.safetyPhone = safetyPhone;
	}

	public String getBuildUnitName() {
		return buildUnitName;
	}

	public void setBuildUnitName(String buildUnitName) {
		this.buildUnitName = buildUnitName;
	}

	public String getSupervisionUnitName() {
		return supervisionUnitName;
	}

	public void setSupervisionUnitName(String supervisionUnitName) {
		this.supervisionUnitName = supervisionUnitName;
	}

	public Boolean getImp() {
		return imp;
	}

	public void setImp(Boolean imp) {
		this.imp = imp;
	}

	public String getXcdb() {
		return xcdb;
	}

	public void setXcdb(String xcdb) {
		this.xcdb = xcdb;
	}

	public String getXcdbPhone() {
		return xcdbPhone;
	}

	public void setXcdbPhone(String xcdbPhone) {
		this.xcdbPhone = xcdbPhone;
	}

	public String getXcresPeople() {
		return xcresPeople;
	}

	public void setXcresPeople(String xcresPeople) {
		this.xcresPeople = xcresPeople;
	}

	public String getXcresPelplePhone() {
		return xcresPelplePhone;
	}

	public void setXcresPelplePhone(String xcresPelplePhone) {
		this.xcresPelplePhone = xcresPelplePhone;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public boolean isSignRegister() {
		return signRegister;
	}

	public void setSignRegister(boolean signRegister) {
		this.signRegister = signRegister;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAccountUsername() {
		return accountUsername;
	}

	public void setAccountUsername(String accountUsername) {
		this.accountUsername = accountUsername;
	}

	public String getOldDepartmentName() {
		return oldDepartmentName;
	}

	public void setOldDepartmentName(String oldDepartmentName) {
		this.oldDepartmentName = oldDepartmentName;
	}

	public Integer getOldOrigin() {
		return oldOrigin;
	}

	public void setOldOrigin(Integer oldOrigin) {
		this.oldOrigin = oldOrigin;
	}

	public Date getCreDate() {
		return creDate;
	}

	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}

	public Boolean getProIf() {
		return proIf;
	}

	public void setProIf(Boolean proIf) {
		this.proIf = proIf;
	}

}
