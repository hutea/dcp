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
 * 保养记录
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_maintain")
public class Maintain {
	@Id
	private String id;
	private String content;// 内容
	private String unit;// 单位
	private String person;// 保养人
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;// 保养时间
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "towerCraneStatus_id")
	private TowerCraneStatus towerCraneStatus; // 账户:塔机所属的单位(塔机必须关联制造单位或者产权单位)这个应该改成关联单位

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TowerCraneStatus getTowerCraneStatus() {
		return towerCraneStatus;
	}

	public void setTowerCraneStatus(TowerCraneStatus towerCraneStatus) {
		this.towerCraneStatus = towerCraneStatus;
	}

}
