package com.zhgl.core.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 塔机基本信息、限位信息
 */
@Entity
@Table(name = "t_towerCraneDevice")
public class TowerCraneDevice {
	@Id
	private String id;
	/* 塔机监控设备信息START */
	private String imei;// 监控设备编号（IMEI）
	// @ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	// @JoinColumn(name = "devicemodel_id")
	// private DeviceModel deviceModel;
	private String longitude;// 经度
	private String latitude;// 纬度
	private String wlpoint;// 无线频点
	private String pointAddress;// 节点地址
	private String netId; // 网络ID
	private String mobileNumber;// DTU手机号码
	private String dnumber;// 项目塔机编号（现场编号）

	private String runTime;// 运行时长
	@Temporal(TemporalType.TIMESTAMP)
	private Date recentOnline;// 最近上线
	@Temporal(TemporalType.TIMESTAMP)
	private Date recentOffline;// 最近下线
	@Temporal(TemporalType.TIMESTAMP)
	private Date regTime;// 生成时间
	/* 塔机监控设备信息END */

	/* 塔机基本信息START */
	private String baseVersion;// 基本信息:信息版本
	private String towerCraneName;// 塔机名称
	private String towerCraneId;// 塔机ID
	private int towerCraneGroupId;// 塔机群ID
	private Integer towerCraneType;// 塔机类型
	private int magnification;// 倍率
	private Float xcoord; // X坐标 0.1米
	private Float ycoord;// Y坐标 0.1米
	private Float armLengthFront;// 前臂 0.1米
	private Float armLengthBack;// 后臂 0.1米
	private Float armHeight;// 塔臂高度 0.1米
	private Float towerTop;// 塔帽高度 0.1米
	private Float installZero;// 安装零点
	private Float monitorZero;// 设备监控零点
	/* 塔机基本信息END */

	/* 限位信息START */
	private String limitVersion;// 基本信息:信息版本
	private Float leftLimit;// 左限位
	private Float rightLimit;// 右限位
	private Float farLimit;// 远限位
	private Float nearLimit;// 近限位
	private Float hightLimit;// 高限位
	private Float liftLimit;// 起重限位
	private Float torqueLimit;// 力矩限位
	private Boolean towerRotationSensor;// 回转传感器
	private Boolean variableSensor;// 幅度传感器
	private Boolean tallSensor;// 高度传感器
	private Boolean weightSensor;// 称重传感器
	private Boolean walkSensor;// 行走传感器
	private Boolean fieldWindSensor;// 风速传感器
	private Boolean xdipangleSensor;// 塔身倾斜传感器
	private Boolean ydipangleSensor;// 塔臂倾斜传感器
	/* 限位信息END */
	/* 记录相干塔机的TowerCraneDevice的ID，格式为：#id1#id1 */
	@Lob
	private String coherentContent;

	@OneToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "socm_id")
	private SocketImei socketImei;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getWlpoint() {
		return wlpoint;
	}

	public void setWlpoint(String wlpoint) {
		this.wlpoint = wlpoint;
	}

	public String getPointAddress() {
		return pointAddress;
	}

	public void setPointAddress(String pointAddress) {
		this.pointAddress = pointAddress;
	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDnumber() {
		return dnumber;
	}

	public void setDnumber(String dnumber) {
		this.dnumber = dnumber;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public Date getRecentOnline() {
		return recentOnline;
	}

	public void setRecentOnline(Date recentOnline) {
		this.recentOnline = recentOnline;
	}

	public Date getRecentOffline() {
		return recentOffline;
	}

	public void setRecentOffline(Date recentOffline) {
		this.recentOffline = recentOffline;
	}

	public String getBaseVersion() {
		return baseVersion;
	}

	public void setBaseVersion(String baseVersion) {
		this.baseVersion = baseVersion;
	}

	public String getTowerCraneName() {
		return towerCraneName;
	}

	public void setTowerCraneName(String towerCraneName) {
		this.towerCraneName = towerCraneName;
	}

	public String getTowerCraneId() {
		return towerCraneId;
	}

	public void setTowerCraneId(String towerCraneId) {
		this.towerCraneId = towerCraneId;
	}

	public int getTowerCraneGroupId() {
		return towerCraneGroupId;
	}

	public void setTowerCraneGroupId(int towerCraneGroupId) {
		this.towerCraneGroupId = towerCraneGroupId;
	}

	public Integer getTowerCraneType() {
		return towerCraneType;
	}

	public void setTowerCraneType(Integer towerCraneType) {
		this.towerCraneType = towerCraneType;
	}

	public int getMagnification() {
		return magnification;
	}

	public void setMagnification(int magnification) {
		this.magnification = magnification;
	}

	public Float getXcoord() {
		return xcoord;
	}

	public void setXcoord(Float xcoord) {
		this.xcoord = xcoord;
	}

	public Float getYcoord() {
		return ycoord;
	}

	public void setYcoord(Float ycoord) {
		this.ycoord = ycoord;
	}

	public Float getArmLengthFront() {
		return armLengthFront;
	}

	public void setArmLengthFront(Float armLengthFront) {
		this.armLengthFront = armLengthFront;
	}

	public Float getArmLengthBack() {
		return armLengthBack;
	}

	public void setArmLengthBack(Float armLengthBack) {
		this.armLengthBack = armLengthBack;
	}

	public Float getArmHeight() {
		return armHeight;
	}

	public void setArmHeight(Float armHeight) {
		this.armHeight = armHeight;
	}

	public Float getTowerTop() {
		return towerTop;
	}

	public void setTowerTop(Float towerTop) {
		this.towerTop = towerTop;
	}

	public String getLimitVersion() {
		return limitVersion;
	}

	public void setLimitVersion(String limitVersion) {
		this.limitVersion = limitVersion;
	}

	public Float getLeftLimit() {
		return leftLimit;
	}

	public void setLeftLimit(Float leftLimit) {
		this.leftLimit = leftLimit;
	}

	public Float getRightLimit() {
		return rightLimit;
	}

	public void setRightLimit(Float rightLimit) {
		this.rightLimit = rightLimit;
	}

	public Float getFarLimit() {
		return farLimit;
	}

	public void setFarLimit(Float farLimit) {
		this.farLimit = farLimit;
	}

	public Float getNearLimit() {
		return nearLimit;
	}

	public void setNearLimit(Float nearLimit) {
		this.nearLimit = nearLimit;
	}

	public Float getHightLimit() {
		return hightLimit;
	}

	public void setHightLimit(Float hightLimit) {
		this.hightLimit = hightLimit;
	}

	public Float getLiftLimit() {
		return liftLimit;
	}

	public void setLiftLimit(Float liftLimit) {
		this.liftLimit = liftLimit;
	}

	public Float getTorqueLimit() {
		return torqueLimit;
	}

	public void setTorqueLimit(Float torqueLimit) {
		this.torqueLimit = torqueLimit;
	}

	public Boolean getTowerRotationSensor() {
		return towerRotationSensor;
	}

	public void setTowerRotationSensor(Boolean towerRotationSensor) {
		this.towerRotationSensor = towerRotationSensor;
	}

	public Boolean getVariableSensor() {
		return variableSensor;
	}

	public void setVariableSensor(Boolean variableSensor) {
		this.variableSensor = variableSensor;
	}

	public Boolean getTallSensor() {
		return tallSensor;
	}

	public void setTallSensor(Boolean tallSensor) {
		this.tallSensor = tallSensor;
	}

	public Boolean getWeightSensor() {
		return weightSensor;
	}

	public void setWeightSensor(Boolean weightSensor) {
		this.weightSensor = weightSensor;
	}

	public Boolean getWalkSensor() {
		return walkSensor;
	}

	public void setWalkSensor(Boolean walkSensor) {
		this.walkSensor = walkSensor;
	}

	public Boolean getFieldWindSensor() {
		return fieldWindSensor;
	}

	public void setFieldWindSensor(Boolean fieldWindSensor) {
		this.fieldWindSensor = fieldWindSensor;
	}

	public Boolean getXdipangleSensor() {
		return xdipangleSensor;
	}

	public void setXdipangleSensor(Boolean xdipangleSensor) {
		this.xdipangleSensor = xdipangleSensor;
	}

	public Boolean getYdipangleSensor() {
		return ydipangleSensor;
	}

	public void setYdipangleSensor(Boolean ydipangleSensor) {
		this.ydipangleSensor = ydipangleSensor;
	}

	public SocketImei getSocketImei() {
		return socketImei;
	}

	public void setSocketImei(SocketImei socketImei) {
		this.socketImei = socketImei;
	}

	public String toString() {
		return "左限位" + this.leftLimit + "\n" + "右限位" + this.rightLimit + "\n"
				+ "远限位" + this.farLimit + "\n" + "近限位" + this.nearLimit + "\n"
				+ "高限位" + this.hightLimit + "\n" + "起重限位" + this.liftLimit
				+ "\n" + "力矩限位" + this.torqueLimit + "\n";
	}

	public String getCoherentContent() {
		return coherentContent;
	}

	public void setCoherentContent(String coherentContent) {
		this.coherentContent = coherentContent;
	}

	public Float getInstallZero() {
		return installZero;
	}

	public void setInstallZero(Float installZero) {
		this.installZero = installZero;
	}

	public Float getMonitorZero() {
		return monitorZero;
	}

	public void setMonitorZero(Float monitorZero) {
		this.monitorZero = monitorZero;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

}
