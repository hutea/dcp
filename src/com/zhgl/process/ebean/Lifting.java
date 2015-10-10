package com.zhgl.process.ebean;

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
 * �������
 * @author Administrator
 *
 */
@Entity
@Table(name="t_lifting")
public class Lifting {
	@Id
	private String id;
	private String beforeLiftingHeight;
	private String liftingHeigh;//����߶�
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;//����
	private String person;//��
	private String phone;//�绰
	private Boolean finalHeigh;//�Ƿ�Ϊ���ո߶�
	@Lob
	private String note;//��ע
	private String liftingUnit;//����λ
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "towerCraneStatus_id")
	private TowerCraneStatus towerCraneStatus;//����������¼

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLiftingHeigh() {
		return liftingHeigh;
	}
	public void setLiftingHeigh(String liftingHeigh) {
		this.liftingHeigh = liftingHeigh;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLiftingUnit() {
		return liftingUnit;
	}
	public void setLiftingUnit(String liftingUnit) {
		this.liftingUnit = liftingUnit;
	}
	public TowerCraneStatus getTowerCraneStatus() {
		return towerCraneStatus;
	}
	public void setTowerCraneStatus(TowerCraneStatus towerCraneStatus) {
		this.towerCraneStatus = towerCraneStatus;
	}
	public String getBeforeLiftingHeight() {
		return beforeLiftingHeight;
	}
	public void setBeforeLiftingHeight(String beforeLiftingHeight) {
		this.beforeLiftingHeight = beforeLiftingHeight;
	}
	public Boolean getFinalHeigh() {
		return finalHeigh;
	}
	public void setFinalHeigh(Boolean finalHeigh) {
		this.finalHeigh = finalHeigh;
	}
	
	
}
