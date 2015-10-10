package com.zhgl.run.ebean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 设备文件上传记录：目前包括两类文件：数据文件和软件文件
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_EquipmentFile")
public class EquipmentFile {
	@Id
	private String id;

	private String fileAbsoultePath;// 文件地址：绝对路径地址

	private String fileName; // 文件名

	private String uploadfileName;// 上传时用户用到的文件名称

	private int fileType;// 文件类型：1=软件文件 2=数据文件

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadTime;// 上传时间

	private Boolean visible = true;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileAbsoultePath() {
		return fileAbsoultePath;
	}

	public void setFileAbsoultePath(String fileAbsoultePath) {
		this.fileAbsoultePath = fileAbsoultePath;
	}

	public String getUploadfileName() {
		return uploadfileName;
	}

	public void setUploadfileName(String uploadfileName) {
		this.uploadfileName = uploadfileName;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
