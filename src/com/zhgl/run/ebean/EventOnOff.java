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
 * 事件上报：6开关机事件
 */
@Entity
@Table(name = "t_eventOnOff")
public class EventOnOff {
	@Id
	private String id;
	private Long numOnThisCycle; // 本次开机循环:自设备生产开始的开机次数
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastOffTime; // 上次关机时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date thisOnTime; // 本次开机时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 开关机上报时间

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "soim_id")
	private SocketImei socketImei;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getNumOnThisCycle() {
		return numOnThisCycle;
	}

	public void setNumOnThisCycle(Long numOnThisCycle) {
		this.numOnThisCycle = numOnThisCycle;
	}

	public Date getLastOffTime() {
		return lastOffTime;
	}

	public void setLastOffTime(Date lastOffTime) {
		this.lastOffTime = lastOffTime;
	}

	public Date getThisOnTime() {
		return thisOnTime;
	}

	public void setThisOnTime(Date thisOnTime) {
		this.thisOnTime = thisOnTime;
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

	@Override
	public String toString() {
		return "本次开机循环：" + this.numOnThisCycle + " 上次关机时间:" + this.lastOffTime
				+ " 本次开机时间:" + this.thisOnTime + " 开关机上报时间:" + this.createTime;
	}

}
