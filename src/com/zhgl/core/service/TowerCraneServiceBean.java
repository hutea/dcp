package com.zhgl.core.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.util.dao.DAOSupport;

@Service
public class TowerCraneServiceBean extends DAOSupport<TowerCrane> implements
		TowerCraneService {

	@SuppressWarnings("unchecked")
	@Override
	public List<TowerCrane> listUnRegMonitor() {
		Query query = em
				.createQuery(
						"select o from  Device o where o.visible=?1 and o.status=?2 "
								+ "and o.currentStatus.DeviceDevice is null")
				.setParameter(1, true).setParameter(2, 2);
		return query.getResultList();
	}

	public TowerCrane findByTCID(String tcid) {
		Query query = em
				.createQuery(
						"select o from  Device o where o.visible=?1 and o.id=?2")
				.setParameter(1, true).setParameter(2, tcid);
		try {
			return (TowerCrane) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据socketId返回塔机Device对象
	 * 
	 * @author hlzeng
	 * @param sockedId
	 *            塔机sockedId t_Device的通信ID，由SystemConfig创建，一直累加
	 * @return 塔机对象Device
	 */
	public TowerCrane findBySocketId(Integer socketId) {
		Query query = em
				.createQuery(
						"select o from  Device o where o.visible=?1 and o.socketId=?2")
				.setParameter(1, true).setParameter(2, socketId);
		try {
			return (TowerCrane) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public TowerCrane findBySocketId(Integer socketId, String ip) {
		Query query = em
				.createQuery(
						"select o from  Device o where o.visible=?1 and o.socketId=?2 and o.ip=?3")
				.setParameter(1, true).setParameter(2, socketId)
				.setParameter(3, ip);
		try {
			return (TowerCrane) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public TowerCrane findByImei(String imei) {
		Query query = em
				.createQuery(
						"select o from  Device o where o.visible=?1 and o.imei=?2")
				.setParameter(1, true).setParameter(2, imei);
		try {
			return (TowerCrane) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean bindIP(String imei, String ip) {
		Query query = em
				.createQuery(
						"update Device o  set o.ip=?1 where o.visible=?2 and o.imei=?3")
				.setParameter(1, ip).setParameter(2, true)
				.setParameter(3, imei);
		int i = query.executeUpdate();
		if (i > 0) {
			return true;
		} else {

			return true;
		}
	}

	public TowerCrane findByPSBIDAndPass(String psbid) {
		try {
			Query query = em.createQuery(
					"select o from Device o where o.psbid=?1 and o.state=3")
					.setParameter(1, psbid);
			return (TowerCrane) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public TowerCrane getTowerByPropertyNumber(String propertNumber) {
		Query q = em.createQuery(
				"select o from Device o where o.propertyNumbers=?1")
				.setParameter(1, propertNumber);
		if (q.getResultList().size() > 0) {
			return (TowerCrane) q.getResultList().get(0);
		} else {
			return null;
		}
	}

	/**
	 * 通过产权备案编号进行模糊查询，找到所有相关项目部名称的列表
	 * 
	 * @param propertyNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> listByPropertyNumber(String propertyNumber) {
		Query query = em
				.createQuery(
						"select t.currentStatus.projectDepartment.name from Device t where t.propertyNumbers like?1 and t.status>=1")
				.setParameter(1, "%" + propertyNumber + "%").setMaxResults(8);
		return query.getResultList();
	}

}
