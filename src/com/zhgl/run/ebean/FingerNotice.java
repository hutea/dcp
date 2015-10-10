package com.zhgl.run.ebean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 指纹通知，用于司机变更后通知终端
 * 
 * <pre>
 *  过程：（1）司机变更产生对应的记录，（2）在与终端交互中如有对应的记录，则发送通知更新
 *  （3）终端发起更新后，删除此条记录
 * </pre>
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_FingerNotice")
public class FingerNotice {
	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String tcid;// 塔机ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTcid() {
		return tcid;
	}

	public void setTcid(String tcid) {
		this.tcid = tcid;
	}
}
