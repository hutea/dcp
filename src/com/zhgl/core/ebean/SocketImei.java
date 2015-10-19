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
	private long id;

	/** 在每次使用完后，给IMEI号+日期后缀； */
	@Column(unique = true)
	private String imei;

	/** 是否禁用该IMEI，如果禁用数据则不能发送 */
	private Boolean enable = false;

	/** state=1:使用中 */
	private int state = 1;

	/** 加入时间 */
	private Date joinDate;

	/** 激活时间 */
	private Date activeDate;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "socketImei")
	private TowerCraneStatus towerCraneStatus;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "socketImei")
	private TowerCraneDevice towerCraneDevice;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "socketImei")
	private SocketIdRecord socketIdRecord;

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
	private Double totalWeight;// 累计吊重
	@Transient
	private int totalWeightTime;// 累计时间
	@Transient
	private int totalWeightNumber;// 累计次数
	@Transient
	private Long totalAlarm;// 报警次数
	@Transient
	private Long totalVio;// 违章次数
	@Transient
	private String curretnAlarm; // 实时报警

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

	public int getTotalWeightTime() {
		return totalWeightTime;
	}

	public void setTotalWeightTime(int totalWeightTime) {
		this.totalWeightTime = totalWeightTime;
	}

	public int getTotalWeightNumber() {
		return totalWeightNumber;
	}

	public void setTotalWeightNumber(int totalWeightNumber) {
		this.totalWeightNumber = totalWeightNumber;
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

	public SocketIdRecord getSocketIdRecord() {
		return socketIdRecord;
	}

	public void setSocketIdRecord(SocketIdRecord socketIdRecord) {
		this.socketIdRecord = socketIdRecord;
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

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Long getTotalAlarm() {
		return totalAlarm;
	}

	public void setTotalAlarm(Long totalAlarm) {
		this.totalAlarm = totalAlarm;
	}

	public Long getTotalVio() {
		return totalVio;
	}

	public void setTotalVio(Long totalVio) {
		this.totalVio = totalVio;
	}

	public String getCurretnAlarm() {
		return curretnAlarm;
	}

	public void setCurretnAlarm(String curretnAlarm) {
		this.curretnAlarm = curretnAlarm;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
