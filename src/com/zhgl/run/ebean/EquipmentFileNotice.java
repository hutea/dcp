package com.zhgl.run.ebean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 文件通知，用于文件更新后通知终端
 * 
 * <pre>
 *  过程：（1）厂商上传变更记录，（2）在与终端交互中如有对应的记录，则发送通知更新
 *  （3）终端发起更新后，删除此条记录
 * </pre>
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_EquipmentFileNotice")
public class EquipmentFileNotice {
	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String tcid;// 塔机ID
	private int fileType;// 文件类型：1=软件文件 2=数据文件

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

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
