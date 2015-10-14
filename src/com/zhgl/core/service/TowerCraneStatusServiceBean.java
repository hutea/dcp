package com.zhgl.core.service;

import org.hibernate.id.uuid.Helper;
import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.IdentityGenerator;
import com.zhgl.util.dao.DAOSupport;

@Service
public class TowerCraneStatusServiceBean extends DAOSupport<TowerCraneStatus>
		implements TowerCraneStatusService {

	/***
	 * 同步省平台数据，如果没有则插入新数据，有则对数据进行更新
	 * 
	 * @param fuid
	 * @param si
	 */
	public void syncData(String fuid, SocketImei si) {
		TowerCraneStatus towerCraneStatus = this.find(fuid);
		boolean isSave = false;
		if (towerCraneStatus == null) {
			isSave = true;
			towerCraneStatus = new TowerCraneStatus();
			towerCraneStatus.setId(IdentityGenerator.generatorID());
		}
		towerCraneStatus.setSocketImei(si);
		if (isSave) {
			this.save(towerCraneStatus);
		} else {
			this.update(towerCraneStatus);
		}
	}

	private TowerCraneStatus findByFuid(String fuid) {
		try {
			return (TowerCraneStatus) em
					.createQuery(
							"select o form TowerCraneStatus o where o.fuid=?1")
					.setParameter(1, fuid).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
