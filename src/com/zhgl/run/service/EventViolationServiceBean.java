package com.zhgl.run.service;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.EventViolation;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.dao.DAOSupport;

@Service
public class EventViolationServiceBean extends DAOSupport<EventViolation>
		implements EventViolationService {

	@Override
	public long countToday(String tcsid) {
		Date now = new Date();
		Date endDate = HelperUtil.addDays(now, 1);
		Date startDate = HelperUtil.reduceDays(endDate, 1);
		Query query = em
				.createQuery(
						"select count(o.id) from EventViolation o where o.towerCraneStatus.id=?1 and o.createTime>=?2 and o.createTime<?3")
				.setParameter(1, tcsid).setParameter(2, startDate)
				.setParameter(3, endDate);
		try {
			return (Long) query.getSingleResult();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public HashMap<String, String> getTypeCount(String id) {
		Query query = em
				.createQuery(
						"select o.type,count(type) from EventViolation o where o.towerCraneStatus.id=?1 group by o.type")
				.setParameter(1, id);
		HashMap<String, String> map = new HashMap<String, String>();
		for (Object obj : query.getResultList()) {
			Object[] result = (Object[]) obj;
			map.put(result[0].toString(), result[1].toString());// [0]类型[1]对应的次数
		}
		return map;
	}

	@Override
	public HashMap<String, String> getTypeCount(Date beginDate, Date endDate,
			String id) {
		Query query = em
				.createQuery(
						"select o.type,count(type) from EventViolation o where o.towerCraneStatus.id=?1 and o.createTime>=?2 and o.createTime<?3  group by o.type")
				.setParameter(1, id).setParameter(2, beginDate)
				.setParameter(3, endDate);
		HashMap<String, String> map = new HashMap<String, String>();
		for (Object obj : query.getResultList()) {
			Object[] result = (Object[]) obj;
			map.put(result[0].toString(), result[1].toString());// [0]类型[1]对应的次数
		}
		return map;
	}

	@Override
	public int getCount(Date beginDate, Date endDate, String tid) {
		Query query = em
				.createNativeQuery(
						"select count(o.id) from t_eventViolation o where o.visible=?1 and o.tcstatus_id=?2 and o.createTime<?3 and o.createTime>=?4")
				.setParameter(1, true).setParameter(2, tid)
				.setParameter(3, endDate).setParameter(4, beginDate);
		return Integer.parseInt(query.getSingleResult() + "");
	}

}
