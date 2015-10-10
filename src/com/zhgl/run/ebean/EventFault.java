package com.zhgl.run.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zhgl.core.ebean.SocketImei;
import com.zhgl.process.ebean.FaultDealList;

/**
 * 事件上报：3故障事件
 */
@Entity
@Table(name = "t_eventFault")
public class EventFault {
	@Id
	private String id;

	private int faultType; // 故障类型
	private int num;// 故障信息 数字代码
	private Float rotion; // 回转
	private Float amplitude; // 幅度
	private Float height; // 吊钩高度
	private Float walk; // 行走
	private Float lift; // 吊重
	private Float windVelocity; // 风速
	private Float torquePercent; // 力矩百分比
	private Float dipangleX; // x方向倾角
	private Float dipangleY; // y方向倾角
	private Float boomTilt; // 塔臂倾斜度
	private Float torque; // 力矩
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 故障时间
	private int type = 1;// 故障是否被处理 用于故障处理 记录当前故障是否处理 以便下次查询未统计故障数量 1.未处理2.
							// 已做处理，但故障尚未完全解决3.完全处理
	private Boolean visible = true;
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "faultDealList_id")
	private FaultDealList faultDealList;// 应对哪次处理的集合

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "soim_id")
	private SocketImei socketImei;

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

	public int getFaultType() {
		return faultType;
	}

	public void setFaultType(int faultType) {
		this.faultType = faultType;
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

	public Float getWalk() {
		return walk;
	}

	public void setWalk(Float walk) {
		this.walk = walk;
	}

	public Float getLift() {
		return lift;
	}

	public void setLift(Float lift) {
		this.lift = lift;
	}

	public Float getWindVelocity() {
		return windVelocity;
	}

	public void setWindVelocity(Float windVelocity) {
		this.windVelocity = windVelocity;
	}

	public Float getTorquePercent() {
		return torquePercent;
	}

	public void setTorquePercent(Float torquePercent) {
		this.torquePercent = torquePercent;
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

	public Float getBoomTilt() {
		return boomTilt;
	}

	public void setBoomTilt(Float boomTilt) {
		this.boomTilt = boomTilt;
	}

	public Float getTorque() {
		return torque;
	}

	public void setTorque(Float torque) {
		this.torque = torque;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SocketImei getSocketImei() {
		return socketImei;
	}

	public void setSocketImei(SocketImei socketImei) {
		this.socketImei = socketImei;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "故障类型：" + this.faultType + " 回转" + this.rotion + " 幅度"
				+ this.amplitude + " 吊钩高度" + this.height + " 行走" + this.walk
				+ " 吊重" + this.lift + " 风速" + this.windVelocity + " 力矩百分比"
				+ this.torquePercent + " x方向倾角" + this.dipangleX + " y方向倾角"
				+ this.dipangleY + " 塔臂倾斜度" + this.boomTilt + " 力矩"
				+ this.torque + " 故障时间" + this.createTime + " 故障是否被处理"
				+ this.type;
	}

	public FaultDealList getFaultDealList() {
		return faultDealList;
	}

	public void setFaultDealList(FaultDealList faultDealList) {
		this.faultDealList = faultDealList;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
