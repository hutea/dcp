package com.zhgl.run.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zhgl.core.ebean.TowerCraneStatus;

/**
 * 运行时错误信息记录
 */
@Entity
@Table(name = "t_runDataLog")
public class RunDataLog {
	@Id
	private String id;
	@Lob
	private String data;// 错误的信息原始数据

	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "tcs_id")
	private TowerCraneStatus currentStatus;// 塔吊 当前“安用拆”状态表

	private int frameType;// 帧类型
	@Temporal(TemporalType.TIMESTAMP)
	private Date messDate;

	private int wrongType;// 错误数据类型:1=CRC错误；2=非法IP；3=报警类型越界；4=违章类型越界；5=工作循环起止时间错误

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public TowerCraneStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(TowerCraneStatus currentStatus) {
		this.currentStatus = currentStatus;
	}


	public int getFrameType() {
		return frameType;
	}

	public void setFrameType(int frameType) {
		this.frameType = frameType;
	}

	public Date getMessDate() {
		return messDate;
	}

	public void setMessDate(Date messDate) {
		this.messDate = messDate;
	}

	public int getWrongType() {
		return wrongType;
	}

	public void setWrongType(int wrongType) {
		this.wrongType = wrongType;
	}

}
