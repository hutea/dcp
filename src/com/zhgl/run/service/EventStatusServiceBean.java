package com.zhgl.run.service;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.EventStatus;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.dao.DAOSupport;

@Service
public class EventStatusServiceBean extends DAOSupport<EventStatus> implements
		EventStatusService {

	@Override
	public EventStatus countIn2Hour(String tcsid) {
		Date endDate = new Date();
		Date startDate = HelperUtil.reduceHours(endDate, 2);
		Query query = em
				.createQuery(
						"select o from  EventStatus o where o.visible=?1 and o.towerCraneStatus.id=?2 and o.createTime>=?3 and o.createTime<=?4 order by id desc")
				.setParameter(1, true).setParameter(2, tcsid)
				.setParameter(3, startDate).setParameter(4, endDate)
				.setMaxResults(1);
		try {
			return (EventStatus) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public EventStatus todayLastDataByTCID(Integer socketID) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		cal.set(year, month, day, 0, 0, 0);
		Date startDate = cal.getTime();
		Query query = em
				.createQuery(
						"select o from  EventStatus o where o.visible=?1 and o.socketImei.id=?2 and o.createTime>=?3 and o.createTime<=?4 order by createTime desc")
				.setParameter(1, true).setParameter(2, socketID)
				.setParameter(3, startDate).setParameter(4, new Date())
				.setMaxResults(1);
		try {
			return (EventStatus) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}
}
