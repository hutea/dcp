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

	/** ************************安装信息END**************** */

	/** ************************使用信息START**************** */
	/** 塔机设备产权备案号 */
	private String fRecordNumber;
	/** 塔机设备使用备案号 */
	private String fUseRecordNUmber;
	/** 制造许可证 */
	private String zzxkz;
	/** 设备名称 */
	private String sbmc;
	/** 出厂编号 */
	private String ccbh;
	/** 规格型号 */
	private String ggxh;
	/** 工程名称 */
	private String fProjectName;
	/** 使用单位 */
	private String fUseUnitName;
	/** 工程地址 */
	private String fProjectAdress;
	/** 项目经理 */
	private String fConstructor;
	/** 联系电话 */
	private String fConstructorPhone;
	/** 安装单位 */
	private String fInstallUnitName;
	/** 安装单位资质等级 */
	private String fInstallUnitLevel;
	/** 安装日期 */
	private String fInstallDate;
	/** 验收日期 */
	private String fAcceptDate;
	/** 安装检测单位 */
	private String fInstallCheckUnit;
	/** 安装检测日期 */
	private String fInstallCheckDate;
	/** ************************使用信息END *****************/

	/** ************************拆卸信息START ****************/

	/** ************************拆卸信息END *****************/

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "towercrane_id")
	private TowerCrane towerCrane;

	@OneToOne
	@JoinColumn(name = "soim_id", unique = true)
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

	public String getfRecordNumber() {
		return fRecordNumber;
	}

	public void setfRecordNumber(String fRecordNumber) {
		this.fRecordNumber = fRecordNumber;
	}

	public String getfUseRecordNUmber() {
		return fUseRecordNUmber;
	}

	public void setfUseRecordNUmber(String fUseRecordNUmber) {
		this.fUseRecordNUmber = fUseRecordNUmber;
	}

	public String getZzxkz() {
		return zzxkz;
	}

	public void setZzxkz(String zzxkz) {
		this.zzxkz = zzxkz;
	}

	public String getSbmc() {
		return sbmc;
	}

	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}

	public String getCcbh() {
		return ccbh;
	}

	public void setCcbh(String ccbh) {
		this.ccbh = ccbh;
	}

	public String getGgxh() {
		return ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	public String getfProjectName() {
		return fProjectName;
	}

	public void setfProjectName(String fProjectName) {
		this.fProjectName = fProjectName;
	}

	public String getfUseUnitName() {
		return fUseUnitName;
	}

	public void setfUseUnitName(String fUseUnitName) {
		this.fUseUnitName = fUseUnitName;
	}

	public String getfProjectAdress() {
		return fProjectAdress;
	}

	public void setfProjectAdress(String fProjectAdress) {
		this.fProjectAdress = fProjectAdress;
	}

	public String getfConstructor() {
		return fConstructor;
	}

	public void setfConstructor(String fConstructor) {
		this.fConstructor = fConstructor;
	}

	public String getfConstructorPhone() {
		return fConstructorPhone;
	}

	public void setfConstructorPhone(String fConstructorPhone) {
		this.fConstructorPhone = fConstructorPhone;
	}

	public String getfInstallUnitName() {
		return fInstallUnitName;
	}

	public void setfInstallUnitName(String fInstallUnitName) {
		this.fInstallUnitName = fInstallUnitName;
	}

	public String getfInstallUnitLevel() {
		return fInstallUnitLevel;
	}

	public void setfInstallUnitLevel(String fInstallUnitLevel) {
		this.fInstallUnitLevel = fInstallUnitLevel;
	}

	public String getfInstallDate() {
		return fInstallDate;
	}

	public void setfInstallDate(String fInstallDate) {
		this.fInstallDate = fInstallDate;
	}

	public String getfAcceptDate() {
		return fAcceptDate;
	}

	public void setfAcceptDate(String fAcceptDate) {
		this.fAcceptDate = fAcceptDate;
	}

	public String getfInstallCheckUnit() {
		return fInstallCheckUnit;
	}

	public void setfInstallCheckUnit(String fInstallCheckUnit) {
		this.fInstallCheckUnit = fInstallCheckUnit;
	}

	public String getfInstallCheckDate() {
		return fInstallCheckDate;
	}

	public void setfInstallCheckDate(String fInstallCheckDate) {
		this.fInstallCheckDate = fInstallCheckDate;
	}

	public TowerCrane getTowerCrane() {
		return towerCrane;
	}

	public void setTowerCrane(TowerCrane towerCrane) {
		this.towerCrane = towerCrane;
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