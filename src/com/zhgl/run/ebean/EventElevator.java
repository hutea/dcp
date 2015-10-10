package com.zhgl.run.ebean;

import com.zhgl.core.ebean.SocketImei;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 升降上报：
 */
@Entity
@Table(name = "t_eventElevator")
public class EventElevator {

	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date upLoadTime; // 上报时间

	private Float rotion; // 回转-
	private Float amplitude; // 幅度-
	private Float height; // 高度-
	private Float lift; // 吊重-
	private int torque; // 力矩-
	private Float windVelocity; // 风速-
	private Float dipangleX; // x方向倾角-
	private Float dipangleY; // y方向倾角-

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "tcstatus_id")
	private SocketImei socketImei;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getUpLoadTime() {
		return upLoadTime;
	}

	public void setUpLoadTime(Date upLoadTime) {
		this.upLoadTime = upLoadTime;
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

	public int getTorque() {
		return torque;
	}

	public void setTorque(int torque) {
		this.torque = torque;
	}

	public SocketImei getSocketImei() {
		return socketImei;
	}

	public void setSocketImei(SocketImei socketImei) {
		this.socketImei = socketImei;
	}

	public Float getWindVelocity() {
		return windVelocity;
	}

	public void setWindVelocity(Float windVelocity) {
		this.windVelocity = windVelocity;
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

}
