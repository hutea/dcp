package com.zhgl.run.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.ReservedArea;
import com.zhgl.util.dao.DAOSupport;

@Service
public class ReservedAreaServiceBean extends DAOSupport<ReservedArea> implements
		ReservedAreaService {

	@Override
	public ReservedArea findByAreaNumber(Integer socketId,
			int reservedAreaNumber) {
		Query query = em
				.createQuery(
						"select o from  ReservedArea o where o.towerCraneStatus.towerCrane.socketId=?1 and o.reservedAreaNumber=?2")
				.setParameter(1, socketId).setParameter(2, reservedAreaNumber);
		try {
			return (ReservedArea) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteByAreaNumber(String tcsid, int reservedAreaNumber) {
		Query query = em
				.createQuery(
						"delete from  ReservedArea o where o.towerCraneStatus.id=?1 and o.reservedAreaNumber=?2")
				.setParameter(1, tcsid).setParameter(2, reservedAreaNumber);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservedArea> listByTCSID(String tcsid) {
		Query query = em
				.createQuery(
						"select o from  ReservedArea o where o.towerCraneStatus.id=?1 order by o.reservedAreaNumber asc")
				.setParameter(1, tcsid);
		return query.getResultList();
	}

}
