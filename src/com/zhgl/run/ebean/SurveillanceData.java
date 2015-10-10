package com.zhgl.run.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zhgl.core.ebean.TowerCraneStatus;

/**
 * 塔吊历史工作状态数据: 本张表中的数据多、也进行垂直切分。 切分规则：t_SurveillanceData_塔机当前运行周期记录id、
 */
@Entity
@Table(name = "t_surveillanceData")
public class SurveillanceData {
	@Id
	private String id; // 监控数据ID
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "towerCrane_id")
	private TowerCraneStatus towerCraneStatus;// 外键相关塔吊
	private Float torque;// 力矩
	private Integer workMode;// 0——正常模式 1——安拆、顶升模式
	private Float ropeRate;// 吊绳倍率
	private Float walk;// 行走(用于行走塔吊)
	private Float dipangleX;// x方向倾角
	private Float dipangleY;// y方向倾角
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 监测时间
	private Float rotion;// 回转角度
	private Float amplitude;// 幅度
	private Float height;// 高度
	private Float lift; // 吊重
	private Float torquePercent;// 力矩百分比
	private Float brakeAngle;// 刹车角度
	private Float windVelocity;// 风速
	private Float towerInclinationX;// 塔身倾斜度X方向
	private Float towerInclinationY;// 塔身倾斜度Y方向
	private Long positionLimitAlert;// 限位报警编码
	private String positionLimitAlertMsg;// 限位报警
	private Long otherAlert;// 其他报警报警编码
	private String otherAlertMsg;// 其他报警
	private Long pengZhuangAlert;// 碰撞报警编码
	private String pengZhuangAlertMsg;// 碰撞报警
	private Long forbiddenAlert;// 禁行区碰撞报警编码
	private String forbiddenAlertMsg;// 禁行区碰撞报警
	private Long obstacleAlert;// 障碍物碰撞报警编码
	private String obstacleAlertMsg;// 障碍物碰撞报警
	private Long relayAlert;// 继电器输出编码
	private String relayAlertMsg;// 继电器输出

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TowerCraneStatus getTowerCraneStatus() {
		return towerCraneStatus;
	}

	public void setTowerCraneStatus(TowerCraneStatus towerCraneStatus) {
		this.towerCraneStatus = towerCraneStatus;
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

	public String getPositionLimitAlertMsg() {
		return positionLimitAlertMsg;
	}

	public void setPositionLimitAlertMsg(String positionLimitAlertMsg) {
		this.positionLimitAlertMsg = positionLimitAlertMsg;
	}

	public String getOtherAlertMsg() {
		return otherAlertMsg;
	}

	public void setOtherAlertMsg(String otherAlertMsg) {
		this.otherAlertMsg = otherAlertMsg;
	}

	public String getPengZhuangAlertMsg() {
		return pengZhuangAlertMsg;
	}

	public void setPengZhuangAlertMsg(String pengZhuangAlertMsg) {
		this.pengZhuangAlertMsg = pengZhuangAlertMsg;
	}

	public String getForbiddenAlertMsg() {
		return forbiddenAlertMsg;
	}

	public void setForbiddenAlertMsg(String forbiddenAlertMsg) {
		this.forbiddenAlertMsg = forbiddenAlertMsg;
	}

	public String getObstacleAlertMsg() {
		return obstacleAlertMsg;
	}

	public void setObstacleAlertMsg(String obstacleAlertMsg) {
		this.obstacleAlertMsg = obstacleAlertMsg;
	}

	public String getRelayAlertMsg() {
		return relayAlertMsg;
	}

	public void setRelayAlertMsg(String relayAlertMsg) {
		this.relayAlertMsg = relayAlertMsg;
	}

	@Override
	public String toString() {
		return "力矩：" + this.torque + " 模式:" + this.workMode + " 吊绳倍率:"
				+ this.ropeRate + " 行走:" + this.walk + " x方向倾角:"
				+ this.dipangleX + " y方向倾角:" + this.dipangleY + " 监测时间:"
				+ this.createTime + " 回转角度:" + this.rotion + " 幅度:"
				+ this.amplitude + " 高度:" + this.height + " 吊重:" + this.lift
				+ " 力矩百分比:" + this.torquePercent + " 刹车角度:" + this.brakeAngle
				+ " 风速:" + this.windVelocity + " 塔身倾斜度X方向:"
				+ this.towerInclinationX + " 塔身倾斜度Y方向:"
				+ this.towerInclinationY + " 限位报警编码:" + this.positionLimitAlert
				+ " 限位报警:" + this.positionLimitAlertMsg + " 其他报警报警编码:"
				+ this.otherAlert + " 其他报警:" + this.otherAlertMsg + " 碰撞报警编码:"
				+ this.pengZhuangAlert + " 碰撞报警:" + this.pengZhuangAlertMsg
				+ " 禁行区碰撞报警编码:" + this.forbiddenAlert + " 禁行区碰撞报警:"
				+ this.forbiddenAlertMsg + " 障碍物碰撞报警编码:" + this.obstacleAlert
				+ " 障碍物碰撞报警:" + this.obstacleAlertMsg + " 继电器输出编码:"
				+ this.relayAlert + " 继电器输出:" + this.relayAlertMsg;
	}
}
