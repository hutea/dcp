package com.zhgl.run.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.core.service.TowerCraneService;
import com.zhgl.run.ebean.FingerNotice;
import com.zhgl.run.server.DataParse;
import com.zhgl.util.IdentityGenerator;
import com.zhgl.util.dao.DAOSupport;

@Service
public class FingerNoticeServiceBean extends DAOSupport<FingerNotice> implements
		FingerNoticeService {
	@Resource
	private TowerCraneService deviceService;

	public void produce(String tcid) {
		FingerNotice notice = findByTCID(tcid);
		if (notice == null) {
			notice = new FingerNotice();
			notice.setId(IdentityGenerator.generatorID());
			notice.setTcid(tcid);
			notice.setDate(new Date());
			this.save(notice);
		} else {
			notice.setDate(new Date());
			this.update(notice);
		}
	}

	public boolean isUpdate(DataParse dataParse) {
		try {
			int socketId = Integer.parseInt(dataParse.getDeviceCode(), 16);
			TowerCrane towerCrane = deviceService.findBySocketId(socketId);
			if (towerCrane != null) {
				FingerNotice notice = findByTCID(towerCrane.getSbid());
				if (notice != null) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delByTCID(String tcid) {
		Query query = em.createQuery(
				"delete  from FingerNotice o where o.tcid=?1").setParameter(1,
				tcid);
		int result = query.executeUpdate();
		if (result >= 1) {
			return true;
		} else {
			return false;
		}
	}

	public FingerNotice findByTCID(String tcid) {
		Query query = em.createQuery(
				"select o from FingerNotice o where o.tcid=?1").setParameter(1,
				tcid);
		try {
			return (FingerNotice) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
