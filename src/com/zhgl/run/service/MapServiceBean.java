package com.zhgl.run.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.run.ebean.Map;
import com.zhgl.util.dao.DAOSupport;

@Service
public class MapServiceBean extends DAOSupport<Map> implements MapService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getMapsByNowRecords(String propertyNumbers) {
		return em.createNativeQuery(
				"select o from Map o where o.propertyNumbers = '"
						+ propertyNumbers + "'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getMapsByprojectDepartmentName(String projectDepartmentName) {
		return em.createNativeQuery(
				"select o from Map o where o.projectDepartmentName = '"
						+ projectDepartmentName + "'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getMapsByprojectTowerCrane(TowerCrane towerCrane) {
		return em.createQuery(
				"select o from Map o where o.tc_id = '" + towerCrane.getSbid()
						+ "'").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getMapsByprojectTowerCrane(String projid) {
		return em.createQuery(
				"select o from Map o where o.projectDepartment_id = '" + projid
						+ "'").getResultList();
	}

}
