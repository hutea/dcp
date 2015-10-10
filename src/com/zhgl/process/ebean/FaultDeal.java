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

@Entity
@Table(name = "t_faultDeal")
public class FaultDeal {
	@Id
	private String id;
	private String content;// 处理内容
	private int result;// 结果
	private String person;// 处理人
	private String dealUnit;// 处理单位
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "faultDealList_id")
	private FaultDealList faultDealList;// 所处理的故障集合
	@Temporal(TemporalType.TIMESTAMP)
	private Date dealDate;// 处理日期

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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getDealUnit() {
		return dealUnit;
	}

	public void setDealUnit(String dealUnit) {
		this.dealUnit = dealUnit;
	}

	public FaultDealList getFaultDealList() {
		return faultDealList;
	}

	public void setFaultDealList(FaultDealList faultDealList) {
		this.faultDealList = faultDealList;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

}
