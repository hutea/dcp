package com.zhgl.project.ebean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zhgl.process.ebean.Lifting;

/**
 * 顶升与人员中间表
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_liftingPerson")
public class LiftingPerson {
	@Id
	private String id;
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "person_id")
	private Person person;
	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "lifting_id")
	private Lifting lifting;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Lifting getLifting() {
		return lifting;
	}

	public void setLifting(Lifting lifting) {
		this.lifting = lifting;
	}
}
