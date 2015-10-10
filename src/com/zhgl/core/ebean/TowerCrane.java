package com.zhgl.core.ebean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 设备归档表
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_device")
public class TowerCrane {
	@Id
	private String id;

	private int socketId;

	private String sbid;
	/** 备案编号 */
	private String babh;
	/** 设备状态（0 空闲中 1 使用中 2 重点监控） */
	private int state;
	/** 企业ID */
	private String entID;
	/** 过期时间 */
	private Date Endtime;
	/** 是否是重点监控 */
	private int isZdjk;
	/** 是否删除 */
	private int isDelete;
	/** 是否注销 */
	private int isLogOut;
	/** 设备名称 */
	private String sbmc;
	/** 设备类型 */
	private String sblx;
	/** 设备购置时间 */
	private Date sbgzsj;
	/** 设备申报时间 */
	private Date sbsbsj; //
	/** 制造许可证 */
	private String zzxkz;
	/** 制造厂家 */
	private String zzcj;
	/** 出厂日期 */
	private Date ccrq;
	/** 规格型号 */
	private String ggxh;
	/** 起重量 */
	private String qzl;
	/** 起重量单位 */
	private String qzldw;
	/** 出厂编号 */
	private String ccbh;
	/** 联系人 */
	private String lxr;
	/** 联系电话 */
	private String lxdh;
	/** 原备案编号 */
	private String ybabh;
	/** 重点监控原因 */
	private String fzdjkMemo;
	/** 最后修改时间 */
	private Date lastUpdateTime;
	/** 手动注销原因 */
	private String fZhuXiaoMemo;

	@OneToOne(optional = true)
	@JoinColumn(name = "tcs_id")
	private TowerCraneStatus currentStatus;// 塔吊 当前“安用拆”状态表

	public String getSbid() {
		return sbid;
	}

	public TowerCraneStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(TowerCraneStatus currentStatus) {
		this.currentStatus = currentStatus;
	}

	public void setSbid(String sbid) {
		this.sbid = sbid;
	}

	public String getBabh() {
		return babh;
	}

	public void setBabh(String babh) {
		this.babh = babh;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getEntID() {
		return entID;
	}

	public void setEntID(String entID) {
		this.entID = entID;
	}

	public Date getEndtime() {
		return Endtime;
	}

	public void setEndtime(Date endtime) {
		Endtime = endtime;
	}

	public int getIsZdjk() {
		return isZdjk;
	}

	public void setIsZdjk(int isZdjk) {
		this.isZdjk = isZdjk;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getIsLogOut() {
		return isLogOut;
	}

	public void setIsLogOut(int isLogOut) {
		this.isLogOut = isLogOut;
	}

	public String getSbmc() {
		return sbmc;
	}

	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}

	public String getSblx() {
		return sblx;
	}

	public void setSblx(String sblx) {
		this.sblx = sblx;
	}

	public Date getSbgzsj() {
		return sbgzsj;
	}

	public void setSbgzsj(Date sbgzsj) {
		this.sbgzsj = sbgzsj;
	}

	public Date getSbsbsj() {
		return sbsbsj;
	}

	public void setSbsbsj(Date sbsbsj) {
		this.sbsbsj = sbsbsj;
	}

	public String getZzxkz() {
		return zzxkz;
	}

	public void setZzxkz(String zzxkz) {
		this.zzxkz = zzxkz;
	}

	public String getZzcj() {
		return zzcj;
	}

	public void setZzcj(String zzcj) {
		this.zzcj = zzcj;
	}

	public Date getCcrq() {
		return ccrq;
	}

	public void setCcrq(Date ccrq) {
		this.ccrq = ccrq;
	}

	public String getGgxh() {
		return ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	public String getQzl() {
		return qzl;
	}

	public void setQzl(String qzl) {
		this.qzl = qzl;
	}

	public String getQzldw() {
		return qzldw;
	}

	public void setQzldw(String qzldw) {
		this.qzldw = qzldw;
	}

	public String getCcbh() {
		return ccbh;
	}

	public void setCcbh(String ccbh) {
		this.ccbh = ccbh;
	}

	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getYbabh() {
		return ybabh;
	}

	public void setYbabh(String ybabh) {
		this.ybabh = ybabh;
	}

	public String getFzdjkMemo() {
		return fzdjkMemo;
	}

	public void setFzdjkMemo(String fzdjkMemo) {
		this.fzdjkMemo = fzdjkMemo;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getfZhuXiaoMemo() {
		return fZhuXiaoMemo;
	}

	public void setfZhuXiaoMemo(String fZhuXiaoMemo) {
		this.fZhuXiaoMemo = fZhuXiaoMemo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSocketId() {
		return socketId;
	}

	public void setSocketId(int socketId) {
		this.socketId = socketId;
	}

}