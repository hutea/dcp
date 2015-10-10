package com.zhgl.run.ebean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_Map")
public class Map {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String propertyNumbers; // 产权编号

	private String nowRecords; // 现场编号

	private String projectDepartmentName;// 工程名称

	private String projectDepartmentAddress; // 工程地址

	private String projectDepartmentArea;// 工程所属区域

	private String wlpoint;// 无线频点

	private String pointAddress;// 节点地址

	private String netId;// 网络ID

	private String towerCraneId;// 塔吊id;

	private int towerCraneGroupId;// 塔群ID

	private double xcoord;// x坐标

	private double ycoord;// y坐标

	private double armLengthFront;// 前臂 0.1米

	private double armLengthBack;// 后壁长

	private double armHeight; // 塔身高度

	private double towerTop;// 塔帽高度

	private double longitude;// 经度

	private double latitude;// 纬度

	private String tc_id; // 塔基机id对应TowerCrane中id

	private String tcd_id; // 塔机基本信息、限位信息TowerCraneDevice中id

	private String propertyUnit_Id;// 账户:塔机所属的单位对应Unit中id

	private String area_id;// 监督机关id对应Area中id

	private String monitorUnit_id;// 监控单位id对应Unit中id

	private String projectDepartment_id;// 工程id对应ProjectDepartment中id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPropertyNumbers() {
		return propertyNumbers;
	}

	public String getTc_id() {
		return tc_id;
	}

	public void setTc_id(String tcId) {
		tc_id = tcId;
	}

	public String getTcd_id() {
		return tcd_id;
	}

	public void setTcd_id(String tcdId) {
		tcd_id = tcdId;
	}

	public void setPropertyNumbers(String propertyNumbers) {
		this.propertyNumbers = propertyNumbers;
	}

	public String getNowRecords() {
		return nowRecords;
	}

	public void setNowRecords(String nowRecords) {
		this.nowRecords = nowRecords;
	}

	public String getProjectDepartmentName() {
		return projectDepartmentName;
	}

	public void setProjectDepartmentName(String projectDepartmentName) {
		this.projectDepartmentName = projectDepartmentName;
	}

	public String getProjectDepartmentAddress() {
		return projectDepartmentAddress;
	}

	public void setProjectDepartmentAddress(String projectDepartmentAddress) {
		this.projectDepartmentAddress = projectDepartmentAddress;
	}

	public String getProjectDepartmentArea() {
		return projectDepartmentArea;
	}

	public void setProjectDepartmentArea(String projectDepartmentArea) {
		this.projectDepartmentArea = projectDepartmentArea;
	}

	public String getWlpoint() {
		return wlpoint;
	}

	public void setWlpoint(String wlpoint) {
		this.wlpoint = wlpoint;
	}

	public String getPointAddress() {
		return pointAddress;
	}

	public void setPointAddress(String pointAddress) {
		this.pointAddress = pointAddress;
	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getTowerCraneId() {
		return towerCraneId;
	}

	public void setTowerCraneId(String towerCraneId) {
		this.towerCraneId = towerCraneId;
	}

	public int getTowerCraneGroupId() {
		return towerCraneGroupId;
	}

	public void setTowerCraneGroupId(int towerCraneGroupId) {
		this.towerCraneGroupId = towerCraneGroupId;
	}

	public double getXcoord() {
		return xcoord;
	}

	public void setXcoord(double xcoord) {
		this.xcoord = xcoord;
	}

	public double getYcoord() {
		return ycoord;
	}

	public void setYcoord(double ycoord) {
		this.ycoord = ycoord;
	}

	public double getArmHeight() {
		return armHeight;
	}

	public void setArmHeight(double armHeight) {
		this.armHeight = armHeight;
	}

	public double getTowerTop() {
		return towerTop;
	}

	public void setTowerTop(double towerTop) {
		this.towerTop = towerTop;
	}

	public double getArmLengthFront() {
		return armLengthFront;
	}

	public void setArmLengthFront(double armLengthFront) {
		this.armLengthFront = armLengthFront;
	}

	public double getArmLengthBack() {
		return armLengthBack;
	}

	public void setArmLengthBack(double armLengthBack) {
		this.armLengthBack = armLengthBack;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getPropertyUnit_Id() {
		return propertyUnit_Id;
	}

	public void setPropertyUnit_Id(String propertyUnitId) {
		propertyUnit_Id = propertyUnitId;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String areaId) {
		area_id = areaId;
	}

	public String getMonitorUnit_id() {
		return monitorUnit_id;
	}

	public void setMonitorUnit_id(String monitorUnitId) {
		monitorUnit_id = monitorUnitId;
	}

	public String getProjectDepartment_id() {
		return projectDepartment_id;
	}

	public void setProjectDepartment_id(String projectDepartmentId) {
		projectDepartment_id = projectDepartmentId;
	}

}
