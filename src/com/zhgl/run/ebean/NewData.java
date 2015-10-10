package com.zhgl.run.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.zhgl.core.ebean.SocketImei;

/**
 * 塔吊最新工作状态数据：产生历史数据记录在status_SurveillanceData
 */
@Entity
@Table(name = "t_newData")
public class NewData {
	@Id
	private String id; // 监控数据ID
	@OneToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "soim_id")
	private SocketImei socketImei; // 外键相关塔吊
	private Float torque; // 力矩
	private Integer workMode; // 0——正常模式 1——安拆、顶升模式
	private Float ropeRate; // 吊绳倍率

	private Float walk; // 行走(用于行走塔吊)

	private Float dipangleX; // x方向倾角
	private Float dipangleY; // y方向倾角
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 监测时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime; // 待机的结束时间（动态更新）
	private Float rotion; // 回转角度 0.1度
	private Float amplitude; // 幅度 0.1米
	private Float height; // 高度 0.1米
	private Float lift; // 吊重 0.01吨
	private Float torquePercent; // 力矩百分比:无意义
	private Float brakeAngle; // 刹车角度
	private Float windVelocity; // 风速 0.1米/秒
	private Float towerInclinationX; // 塔身倾斜度X方向 0.1度
	private Float towerInclinationY; // 塔身倾斜度Y方向 0.1度
	private Long positionLimitAlert; // 限位报警编码
	private Long otherAlert; // 其他报警报警编码
	private Long pengZhuangAlert; // 塔机碰撞报警编码
	private Long forbiddenAlert; // 禁行区碰撞报警编码
	private Long obstacleAlert; // 障碍物碰撞报警编码
	private Long relayAlert; // 继电器输出编码

	@Transient
	private String nowAlarm; // 实时报警数据
	@Transient
	private int surveillanceDataId;

	public int getSurveillanceDataId() {
		return surveillanceDataId;
	}

	public void setSurveillanceDataId(int surveillanceDataId) {
		this.surveillanceDataId = surveillanceDataId;
	}

	public String getNowAlarm() {
		return nowAlarm;
	}

	public void setNowAlarm(String nowAlarm) {
		this.nowAlarm = nowAlarm;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SocketImei getSocketImei() {
		return socketImei;
	}

	public void setSocketImei(SocketImei socketImei) {
		this.socketImei = socketImei;
	}

	public Float getTorque() {
		return torque;
	}

	public void setTorque(Float torque) {
		this.torque = torque;
	}

	public Integer getWorkMode() {
		return workMode;
	}

	public void setWorkMode(Integer workMode) {
		this.workMode = workMode;
	}

	public Float getRopeRate() {
		return ropeRate;
	}

	public void setRopeRate(Float ropeRate) {
		this.ropeRate = ropeRate;
	}

	public Float getWalk() {
		return walk;
	}

	public void setWalk(Float walk) {
		this.walk = walk;
	}

	public Float getDipangleX() {
		return dipangleX;
	}

	public void setDipangleX(Float dipangleX) {
		this.dipangleX = dipangleX;
	}

	public Float getDipangleY() {
		return dipangleY;
	}

	public void setDipangleY(Float dipangleY) {
		this.dipangleY = dipangleY;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getRotion() {
		return rotion;
	}

	public void setRotion(Float rotion) {
		this.rotion = rotion;
	}

	public Float getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(Float amplitude) {
		this.amplitude = amplitude;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getLift() {
		return lift;
	}

	public void setLift(Float lift) {
		this.lift = lift;
	}

	public Float getTorquePercent() {
		return torquePercent;
	}

	public void setTorquePercent(Float torquePercent) {
		this.torquePercent = torquePercent;
	}

	public Float getBrakeAngle() {
		return brakeAngle;
	}

	public void setBrakeAngle(Float brakeAngle) {
		this.brakeAngle = brakeAngle;
	}

	public Float getWindVelocity() {
		return windVelocity;
	}

	public void setWindVelocity(Float windVelocity) {
		this.windVelocity = windVelocity;
	}

	public Float getTowerInclinationX() {
		return towerInclinationX;
	}

	public void setTowerInclinationX(Float towerInclinationX) {
		this.towerInclinationX = towerInclinationX;
	}

	public Float getTowerInclinationY() {
		return towerInclinationY;
	}

	public void setTowerInclinationY(Float towerInclinationY) {
		this.towerInclinationY = towerInclinationY;
	}

	public Long getPositionLimitAlert() {
		return positionLimitAlert;
	}

	public void setPositionLimitAlert(Long positionLimitAlert) {
		this.positionLimitAlert = positionLimitAlert;
	}

	public Long getOtherAlert() {
		return otherAlert;
	}

	public void setOtherAlert(Long otherAlert) {
		this.otherAlert = otherAlert;
	}

	public Long getPengZhuangAlert() {
		return pengZhuangAlert;
	}

	public void setPengZhuangAlert(Long pengZhuangAlert) {
		this.pengZhuangAlert = pengZhuangAlert;
	}

	public Long getForbiddenAlert() {
		return forbiddenAlert;
	}

	public void setForbiddenAlert(Long forbiddenAlert) {
		this.forbiddenAlert = forbiddenAlert;
	}

	public Long getObstacleAlert() {
		return obstacleAlert;
	}

	public void setObstacleAlert(Long obstacleAlert) {
		this.obstacleAlert = obstacleAlert;
	}

	public Long getRelayAlert() {
		return relayAlert;
	}

	public void setRelayAlert(Long relayAlert) {
		this.relayAlert = relayAlert;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
