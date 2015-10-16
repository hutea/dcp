package com.zhgl.core.service;

import org.springframework.stereotype.Service;

import com.zhgl.api.parse.JsonUtil;
import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.util.IdentityGenerator;
import com.zhgl.util.dao.DAOSupport;

@Service
public class TowerCraneStatusServiceBean extends DAOSupport<TowerCraneStatus>
		implements TowerCraneStatusService {

	@Override
	public void syncData(String fuid, SocketImei si) {
		TowerCraneStatus towerCraneStatus = this.findBySid(si.getId());
		if (towerCraneStatus == null) {
			towerCraneStatus = JsonUtil.parseByfuid(fuid);
			towerCraneStatus.setId(IdentityGenerator.generatorID());
			towerCraneStatus.setSocketImei(si);
			towerCraneStatus.setFuid(fuid);
			this.save(towerCraneStatus);
		} else {
			TowerCraneStatus tcsNew = JsonUtil.parseByfuid(fuid);
			towerCraneStatus.setfAcceptDate(tcsNew.getfAcceptDate());
			// .....
			towerCraneStatus.setSocketImei(si);
			this.update(towerCraneStatus);
		}
	}

	private TowerCraneStatus findBySid(long sid) {
		try {
			return (TowerCraneStatus) em
					.createQuery(
							"select o from TowerCraneStatus o where o.socketImei.id=?1")
					.setParameter(1, sid).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
