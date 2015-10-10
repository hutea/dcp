package com.zhgl.core.ebean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 用于运行监控 分组
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_point")
public class Point {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/** 工程名称 */
	private String name;

	/** 地址 */
	private String address;

	private Date createDate = new Date();

	private Date modifyDate;

	private int state = 1;

	private Boolean visible = true;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
