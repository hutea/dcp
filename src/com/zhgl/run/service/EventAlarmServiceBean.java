package com.zhgl.run.service;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.EventAlarm;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.dao.DAOSupport;

@Service
public class EventAlarmServiceBean extends DAOSupport<EventAlarm> implements
		EventAlarmService {

	@Override
	public long countToday(int sid) {
		Date now = new Date();
		Date endDate = HelperUtil.addDays(now, 1);
		Date startDate = HelperUtil.reduceDays(endDate, 1);
		Query query = em
				.createQuery(
						"select count(o.id) from EventAlarm o where o.socketImei.sid=?1 and o.createTime>=?2 and o.createTime<?3 and o.visible=true")
				.setParameter(1, sid).setParameter(2, startDate)
				.setParameter(3, endDate);
		try {
			return (Long) query.getSingleResult();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public EventAlarm nowArarm(int sid) {
		Date endDate = new Date();
		Date startDate = HelperUtil.reduceHours(endDate, 2);
		Query query = em
				.createQuery(
						"select o from EventAlarm o where o.socketImei.sid=?1 and o.createTime>=?2 and o.createTime<=?3 and o.visible=true order by id desc")
				.setParameter(1, sid).setParameter(2, startDate)
				.setParameter(3, endDate);
		try {
			return (EventAlarm) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据塔机id找出，传入时间范围内的报警数据，并将报警数据中的报警类型，数量按键值对的方式储存进map
	 */
	@Override
	public HashMap<String, String> getTypeCount(Date beginDate, Date endDate,
			int sid) {
		Query query = em
				.createQuery(
						"select o.alarmType,count(alarmType) from EventAlarm o where o.socketImei.sid=?1 and o.createTime>=?2 and o.createTime<?3 and o.visible=true group by o.alarmType")
				.setParameter(1, sid).setParameter(2, beginDate)
				.setParameter(3, endDate);
		HashMap<String, String> map = new HashMap<String, String>();
		for (Object obj : query.getResultList()) {
			Object[] result = (Object[]) obj;
			map.put(result[0].toString(), result[1].toString());
			// [0]报警类型[1]报警类型对应的次数
		}
		return map;
	}

	/**
	 * 根据塔机id找出，所有的报警数据，并将报警数据中的报警类型，数量按键值对的方式储存进map
	 */
	@Override
	public HashMap<String, String> getTypeCount(int sid) {
		Query query = em
				.createQuery(
						"select o.alarmType,count(alarmType) from EventAlarm o where o.socketImei.sid=?1 and o.visible=true group by o.alarmType")
				.setParameter(1, sid);
		HashMap<String, String> map = new HashMap<String, String>();
		for (Object obj : query.getResultList()) {
			Object[] result = (Object[]) obj;
			map.put(result[0].toString(), result[1].toString());
			// [0]报警类型[1]报警类型对应的次数
		}
		return map;
	}

	@Override
	public int getCount(Date beginDate, Date endDate, int sid) {
		Query query = em
				.createNativeQuery(
						"select count(o.id) from  t_eventAlarm o where o.visible=?1 and o.socketImei.sid=?2 and o.createTime<?3 and o.createTime>=?4")
				.setParameter(1, true).setParameter(2, sid)
				.setParameter(3, endDate).setParameter(4, beginDate);
		return Integer.parseInt(query.getSingleResult() + "");
	}

}
