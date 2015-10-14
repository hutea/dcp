package com.zhgl.core.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_socketimei")
public class SocketImei {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	/**用作通讯ID*/
	private int sid;

	/** 在每次使用完后，给IMEI号+日期后缀； */
	@Column(unique = true)
	private String imei;

	/** 是否禁用该IMEI，如果禁用数据则不能发送 */
	private Boolean enable = false;

	/** 加入时间 */
	private Date joinDate;

	/** 激活时间 */
	private Date activeDate;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "socketImei")
	private TowerCraneStatus towerCraneStatus;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "socketImei")
	private TowerCraneDevice towerCraneDevice;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "point_id")
	private Point point;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "devicemodel_id")
	private DeviceModel deviceModel;

	private Boolean visible = false;

	// 运行辅助数据
	@Transient
	private Boolean online = false;
	@Transient
	private Double cycleTotalWeight;// 累计吊重
	@Transient
	private Long alarmCount;// 报警次数
	@Transient
	private Long violationCount;// 违章次数
	@Transient
	private String curretnAlarm; // 实时报警

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public TowerCraneStatus getTowerCraneStatus() {
		return towerCraneStatus;
	}

	public void setTowerCraneStatus(TowerCraneStatus towerCraneStatus) {
		this.towerCraneStatus = towerCraneStatus;
	}

	public TowerCraneDevice getTowerCraneDevice() {
		return towerCraneDevice;
	}

	public void setTowerCraneDevice(TowerCraneDevice towerCraneDevice) {
		this.towerCraneDevice = towerCraneDevice;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public DeviceModel getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(DeviceModel deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public Double getCycleTotalWeight() {
		return cycleTotalWeight;
	}

	public void setCycleTotalWeight(Double cycleTotalWeight) {
		this.cycleTotalWeight = cycleTotalWeight;
	}

	public Long getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(Long alarmCount) {
		this.alarmCount = alarmCount;
	}

	public Long getViolationCount() {
		return violationCount;
	}

	public void setViolationCount(Long violationCount) {
		this.violationCount = violationCount;
	}

	public String getCurretnAlarm() {
		return curretnAlarm;
	}

	public void setCurretnAlarm(String curretnAlarm) {
		this.curretnAlarm = curretnAlarm;
	}

}
