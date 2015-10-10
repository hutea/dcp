package com.zhgl.process.ebean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.zhgl.core.ebean.TowerCraneStatus;

/**
 * 记录当前故障处理次数表
 */
@Entity
@Table(name = "t_faultDealList")
public class FaultDealList {
	@Id
	private String id;
	private int type;// 限位故障类型
	private int count;// 次数
	@Temporal(TemporalType.TIMESTAMP)
	private Date firstDate;// 首次检测时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date finalDealDate;// 最后处理时间
	private int result = 0;// 最后处理结果 1. 未完全处理 2.完全处理
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "towerCraneStatus_id")
	private TowerCraneStatus towerCraneStatus;

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Date getFinalDealDate() {
		return finalDealDate;
	}

	public void setFinalDealDate(Date finalDealDate) {
		this.finalDealDate = finalDealDate;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public TowerCraneStatus getTowerCraneStatus() {
		return towerCraneStatus;
	}

	public void setTowerCraneStatus(TowerCraneStatus towerCraneStatus) {
		this.towerCraneStatus = towerCraneStatus;
	}

}
