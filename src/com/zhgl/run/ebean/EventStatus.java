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
 * 事件上报：5人员身份上报
 */
@Entity
@Table(name = "t_eventStatus")
public class EventStatus {
	@Id
	private String id;
	private int eventSign; // 本次事件:0x01：登录 0x02：未登录（塔机工作中未按规定输入指纹）
	private int mode; // 识别方式 0x01：指纹 0x02：射频卡（保留）

	/**
	 * @ManyToOne(cascade = { CascadeType.REFRESH }, optional = true)
	 * @JoinColumn(name = "person_id") private Person person;//
	 *                  身份特征对应的人员ID（即person表中的Id）来映射peron表
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; // 人员上报上报时间
	private boolean visible = true; // 数据是否有效

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "soimei_id")
	private SocketImei socketImei;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getEventSign() {
		return eventSign;
	}

	public void setEventSign(int eventSign) {
		this.eventSign = eventSign;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
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
		return " 本次事件:" + this.eventSign + " 识别方式:" + this.mode + "人员上报上报时间:"
				+ this.createTime;

	}
}
