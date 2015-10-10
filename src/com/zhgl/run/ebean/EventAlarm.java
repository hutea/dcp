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

/**
 * 事件上报：1.报警事件
 */
@Entity
@Table(name = "t_eventalarm")
public class EventAlarm {

	@Id
	private String id;

	private int alarmType; // 报警类型-
	private String message; // 报警信息
	private String num;// 报警信息对应的数字代码：
	private Float rotion; // 回转-
	private Float amplitude; // 幅度-
	private Float height; // 吊钩高度-
	private Float lift; // 吊重-
	private Float windVelocity; // 风速-
	private Float torquePercent; // 力矩百分比
	private Float dipangleX; // x方向倾角-
	private Float dipangleY; // y方向倾角-
	private Float torque; // 力矩-
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 报警时间
	private boolean visible = true; // 数据是否有效

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "soim_id")
	private SocketImei socketImei;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public SocketImei getSocketImei() {
		return socketImei;
	}

	public void setSocketImei(SocketImei socketImei) {
		this.socketImei = socketImei;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "报警类型：" + this.alarmType + " 报警信息:" + this.message + " 回转:"
				+ this.rotion + " 幅度:" + this.amplitude + " 吊钩高度:"
				+ this.height + " 吊重:" + this.lift + " 风速:" + this.windVelocity
				+ " 力矩百分比:" + this.torquePercent + " x方向倾角:" + this.dipangleX
				+ " y方向倾角:" + this.dipangleY + " 力矩:" + this.torque + " 报警时间:"
				+ this.createTime;
	}

}