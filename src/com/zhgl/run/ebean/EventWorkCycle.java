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
 * 事件上报：4工作循环事件
 */
@Entity
@Table(name = "t_eventWorkCycle")
public class EventWorkCycle {
	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime; // 工作循环开始时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date overTime; // 工作循环结束时间
	private Float largestWeight; // 最大起重
	private Float largestTorque; // 最大力矩
	private Float largestHeight; // 最大高度
	private Float smallestHeight; // 最小高度
	private Float largestAmplitude; // 最大幅度
	private Float smallestAmplitude; // 最小幅度
	private Float walkPosition; // 行走位置
	private Float liftingPointAngle; // 起吊点角度
	private Float liftingPointAmplitude; // 起吊点幅度
	private Float liftingPointHeight; // 起吊点高度
	private Float unloadingPointAngle; // 卸吊点角度
	private Float unloadingPointAmplitude; // 卸吊点幅度
	private Float unloadingPointHeight; // 卸吊点高度
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 工作循环上报时间
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public Float getLargestWeight() {
		return largestWeight;
	}

	public void setLargestWeight(Float largestWeight) {
		this.largestWeight = largestWeight;
	}

	public Float getLargestTorque() {
		return largestTorque;
	}

	public void setLargestTorque(Float largestTorque) {
		this.largestTorque = largestTorque;
	}

	public Float getLargestHeight() {
		return largestHeight;
	}

	public void setLargestHeight(Float largestHeight) {
		this.largestHeight = largestHeight;
	}

	public Float getSmallestHeight() {
		return smallestHeight;
	}

	public void setSmallestHeight(Float smallestHeight) {
		this.smallestHeight = smallestHeight;
	}

	public Float getLargestAmplitude() {
		return largestAmplitude;
	}

	public void setLargestAmplitude(Float largestAmplitude) {
		this.largestAmplitude = largestAmplitude;
	}

	public Float getSmallestAmplitude() {
		return smallestAmplitude;
	}

	public void setSmallestAmplitude(Float smallestAmplitude) {
		this.smallestAmplitude = smallestAmplitude;
	}

	public Float getWalkPosition() {
		return walkPosition;
	}

	public void setWalkPosition(Float walkPosition) {
		this.walkPosition = walkPosition;
	}

	public Float getLiftingPointAngle() {
		return liftingPointAngle;
	}

	public void setLiftingPointAngle(Float liftingPointAngle) {
		this.liftingPointAngle = liftingPointAngle;
	}

	public Float getLiftingPointAmplitude() {
		return liftingPointAmplitude;
	}

	public void setLiftingPointAmplitude(Float liftingPointAmplitude) {
		this.liftingPointAmplitude = liftingPointAmplitude;
	}

	public Float getLiftingPointHeight() {
		return liftingPointHeight;
	}

	public void setLiftingPointHeight(Float liftingPointHeight) {
		this.liftingPointHeight = liftingPointHeight;
	}

	public Float getUnloadingPointAngle() {
		return unloadingPointAngle;
	}

	public void setUnloadingPointAngle(Float unloadingPointAngle) {
		this.unloadingPointAngle = unloadingPointAngle;
	}

	public Float getUnloadingPointAmplitude() {
		return unloadingPointAmplitude;
	}

	public void setUnloadingPointAmplitude(Float unloadingPointAmplitude) {
		this.unloadingPointAmplitude = unloadingPointAmplitude;
	}

	public Float getUnloadingPointHeight() {
		return unloadingPointHeight;
	}

	public void setUnloadingPointHeight(Float unloadingPointHeight) {
		this.unloadingPointHeight = unloadingPointHeight;
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
		return "工作循环开始时间：" + this.startTime + " 工作循环结束时间:" + this.overTime
				+ " 最大起重:" + this.largestWeight + " 最大力矩:" + this.largestTorque
				+ " 最大高度:" + this.largestHeight + " 最小高度:"
				+ this.smallestHeight + " 最大幅度:" + this.largestAmplitude
				+ " 最小幅度:" + this.smallestAmplitude + " 行走位置:"
				+ this.walkPosition + " 起吊点角度:" + this.liftingPointAngle
				+ " 起吊点幅度:" + this.liftingPointAmplitude + " 起吊点高度:"
				+ this.liftingPointHeight + " 卸吊点角度:"
				+ this.unloadingPointAngle + " 卸吊点幅度:"
				+ this.unloadingPointAmplitude + " 卸吊点高度:"
				+ this.unloadingPointHeight + " 工作循环上报时间:" + this.createTime;
	}
}
