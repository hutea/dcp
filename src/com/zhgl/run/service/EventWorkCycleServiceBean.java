package com.zhgl.run.service;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.EventWorkCycle;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.dao.DAOSupport;

@Service
public class EventWorkCycleServiceBean extends DAOSupport<EventWorkCycle>
		implements EventWorkCycleService {

	@Override
	public int countEventById(long sid) {
		Query query = em
				.createQuery(
						"select count(o.id) from  EventWorkCycle o where o.visible=?1 and o.socketImei.id=?2")
				.setParameter(1, true).setParameter(2, sid);
		if (query.getSingleResult() == null) {
			return 0;
		}
		return Integer.parseInt(query.getSingleResult() + "");
	}

	@SuppressWarnings("unchecked")
	@Override
	public double countWeightEvent(long id) {
		Query query = em
				.createQuery(
						"select o.largestWeight from  EventWorkCycle o where o.visible=?1 and o.socketImei.id=?2")
				.setParameter(1, true).setParameter(2, id);
		double weight = 0;
		List<Double> list = query.getResultList();
		for (Object obj : list) {
			weight = HelperUtil.add(weight, Double.parseDouble(obj.toString()));
		}
		return weight;
	}

	/**
	 * 根据塔机状态id查询此塔机所有的工作循环数据的总工作时间
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int countTimeEvent(long sid) {
		Query query = em
				.createQuery(
						"select o from  EventWorkCycle o where o.visible=?1 and o.socketImei.id=?2")
				.setParameter(1, true).setParameter(2, sid);
		List<EventWorkCycle> list = query.getResultList();
		int time = 0;
		for (EventWorkCycle workCycle : list) {
			time += Integer
					.parseInt((workCycle.getOverTime().getTime() - workCycle
							.getStartTime().getTime()) / 1000 + "");
		}
		return time;
	}

	/**
	 * 根据塔机状态id,与起始时间，查询此塔机在此起始时间范围内的所有的工作循环数据的总吊重
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double countWeight(Date beginDate, Date endDate, long sid) {
		Query query = em
				.createQuery(
						"select o.largestWeight from EventWorkCycle o where o.visible=?1 and o.socketImei.id=?2 and o.startTime<?3 and o.startTime>=?4")
				.setParameter(1, true).setParameter(2, sid)
				.setParameter(3, endDate).setParameter(4, beginDate);
		List<Object> arr = query.getResultList();
		double weight = 0;
		for (Object obj : arr) {
			weight = HelperUtil.add(weight, Double.parseDouble(obj.toString()));
		}
		return weight;
	}

	/**
	 * 根据塔机状态id,与起始时间，查询此塔机在此起始时间范围内的所有的工作循环数据的条数
	 */
	@Override
	public int countEvent(Date beginDate, Date endDate, long sid) {
		Query query = em
				.createNativeQuery(
						"select count(o.largestWeight) from  t_eventWorkCycle o where o.visible=?1 and o.socketImei.id=?2 and o.startTime<?3 and o.startTime>=?4")
				.setParameter(1, true).setParameter(2, sid)
				.setParameter(3, endDate).setParameter(4, beginDate);

		if (query.getSingleResult() == null) {
			return 0;
		}

		return Integer.parseInt(query.getSingleResult() + "");
	}

	@SuppressWarnings("unchecked")
	@Override
	public int countTime(Date beginDate, Date endDate, long sid) {
		Query query = em
				.createQuery(
						"select o from EventWorkCycle o "
								+ "where o.visible=?1 and o.socketImei.id=?2 and o.createTime<?3 and o.createTime>=?4")
				.setParameter(1, true).setParameter(2, sid)
				.setParameter(3, endDate).setParameter(4, beginDate);
		if (query == null) {
			return 0;
		}
		List<EventWorkCycle> list = query.getResultList();
		long time = 0;
		for (EventWorkCycle workCycle : list) {
			time += workCycle.getOverTime().getTime()
					- workCycle.getStartTime().getTime();
		}
		int result = (int) time / 1000;
		return result;
	}
}
