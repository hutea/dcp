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

	public int getSid() {
		return sid;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
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

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public TowerCraneStatus getTowerCraneStatus() {
		return towerCraneStatus;
	}

	public DeviceModel getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(DeviceModel deviceModel) {
		this.deviceModel = deviceModel;
	}

	public void setTowerCraneStatus(TowerCraneStatus towerCraneStatus) {
		this.towerCraneStatus = towerCraneStatus;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
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

}
