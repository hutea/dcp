package com.zhgl.run.service;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.core.service.TowerCraneService;
import com.zhgl.run.ebean.EquipmentFileNotice;
import com.zhgl.run.server.DataParse;
import com.zhgl.util.IdentityGenerator;
import com.zhgl.util.dao.DAOSupport;

@Service
public class EquipmentFileNoticeServiceBean extends
		DAOSupport<EquipmentFileNotice> implements EquipmentFileNoticeService {

	@Resource
	private TowerCraneService towerCraneService;

	public void produce(String tcid, int type) {
		EquipmentFileNotice notice = findByTCID(tcid, type);
		if (notice == null) {
			notice = new EquipmentFileNotice();
			notice.setId(IdentityGenerator.generatorID());
			notice.setTcid(tcid);
			notice.setDate(new Date());
			notice.setFileType(type);
			this.save(notice);
		} else {
			notice.setDate(new Date());
			this.update(notice);
		}
	}

	public boolean isUpdate(DataParse data16Parse, int type) {
		try {
			int socketId = Integer.parseInt(data16Parse.getDeviceCode(), 16);
			TowerCrane towerCrane = towerCraneService.findBySocketId(socketId);
			if (towerCrane != null) {
				EquipmentFileNotice notice = findByTCID(towerCrane.getSbid(),
						type);
				if (notice != null) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delByTCID(String tcid, int type) {
		Query query = em
				.createQuery(
						"delete  from EquipmentFileNotice o where o.tcid=?1 and o.fileType=?2")
				.setParameter(1, tcid).setParameter(2, type);
		int result = query.executeUpdate();
		if (result >= 1) {
			return true;
		} else {
			return false;
		}
	}

	private EquipmentFileNotice findByTCID(String tcid, int type) {
		Query query = em
				.createQuery(
						"select o from EquipmentFileNotice o where o.tcid=?1 and o.fileType=?2")
				.setParameter(1, tcid).setParameter(2, type);
		try {
			return (EquipmentFileNotice) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
