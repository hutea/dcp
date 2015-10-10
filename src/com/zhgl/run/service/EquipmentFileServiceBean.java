package com.zhgl.run.service;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.EquipmentFile;
import com.zhgl.util.dao.DAOSupport;

@Service
public class EquipmentFileServiceBean extends DAOSupport<EquipmentFile> implements EquipmentFileService {

	@Override
	public EquipmentFile lastFile(int type, int manuCode) {
		Query query = em.createQuery("select o from EquipmentFile o where o.fileType=?1 and o.unit.manuCode=?2 order by id desc").setParameter(1, type)
				.setParameter(2, manuCode).setMaxResults(1);
		try {
			return (EquipmentFile) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
