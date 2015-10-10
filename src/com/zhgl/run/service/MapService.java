package com.zhgl.run.service;

import java.util.List;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.run.ebean.Map;
import com.zhgl.util.dao.DAO;

public interface MapService extends DAO<Map> {

	/**
	 * 根据产权编号查询
	 * 
	 * @param propertyNumbers
	 * @return
	 */
	public List<Map> getMapsByNowRecords(String propertyNumbers);

	/**
	 * 根据项目部名称查询
	 * 
	 * @param projectDepartmentName
	 * @return
	 */
	public List<Map> getMapsByprojectDepartmentName(String projectDepartmentName);

	/**
	 * 根据塔机id查询
	 * 
	 * @param projectDepartmentName
	 * @return
	 */
	public List<Map> getMapsByprojectTowerCrane(TowerCrane towerCrane);

	/**
	 * 根据塔机项目部id查询
	 * 
	 * @param projectDepartmentName
	 * @return
	 */
	public List<Map> getMapsByprojectTowerCrane(String projid);

}
