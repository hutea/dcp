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
 * 事件上报：2违章事件
 */
@Entity
@Table(name = "t_eventViolation")
public class EventViolation {
	@Id
	private String id;

	private int type; // 违章类型
	private String content; // 违章内容
	private int num;// 违章内容 数字代码
	private Float rotion; // 违章回转
	private Float amplitude; // 违章幅度
	private Float height; // 违章高度
	private Float weight; // 违章重量
	private Float torquePercent; // 违章力矩百分比
	private Float torque; // 违章力矩
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 违章时间
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getTorquePercent() {
		return torquePercent;
	}

	public void setTorquePercent(Float torquePercent) {
		this.torquePercent = torquePercent;
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

	@Override
	public String toString() {
		return "违章类型：" + this.type + " 违章内容:" + this.content + " 违章回转:"
				+ this.rotion + " 违章幅度:" + this.amplitude + " 违章高度:"
				+ this.height + " 违章重量:" + this.weight + " 违章力矩百分比:"
				+ this.torquePercent + " 违章力矩:" + this.torque + " 违章时间:"
				+ this.createTime;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
