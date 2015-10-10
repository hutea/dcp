package com.zhgl.run.service;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.run.ebean.DeviceConfig;
import com.zhgl.util.dao.DAOSupport;

@Service
public class DeviceConfigServiceBean extends DAOSupport<DeviceConfig> implements
		DeviceConfigService {

	@Override
	public DeviceConfig findBytowerstate(TowerCraneStatus towerCraneStatus) {
		Query q = em.createQuery(
				"select o from DeviceConfig o "
						+ "where o.towerCraneStatus.id=?1").setParameter(1,
				towerCraneStatus.getId());
		try {
			return (DeviceConfig) q.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
