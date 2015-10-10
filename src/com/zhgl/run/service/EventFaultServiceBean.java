package com.zhgl.run.service;

import java.util.HashMap;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.EventFault;
import com.zhgl.util.dao.DAOSupport;

@Service
public class EventFaultServiceBean extends DAOSupport<EventFault> implements
		EventFaultService {

	@Override
	public HashMap<String, String> getTypeCount(String id) {
		Query query = em
				.createQuery(
						"select o.faultType,count(faultType) from EventFault o where o.towerCrane.id=?1 group by o.faultType")
				.setParameter(1, id);
		HashMap<String, String> map = new HashMap<String, String>();
		for (Object obj : query.getResultList()) {
			Object[] result = (Object[]) obj;
			map.put(result[0].toString(), result[1].toString());
			// [0]故障类型[1]故障类型对应的次数
		}
		return map;
	}

	/**
	 * 查出在此时间范围内,此塔机未处理的故障的数量
	 */
	public int getCount(String tid) {
		Query query = em
				.createNativeQuery(
						"select count(o.id) from  t_eventFault o where o.visible=?1 and o.tcstatus_id=?2 and o.type=?3")
				.setParameter(1, true).setParameter(2, tid).setParameter(3, 1);
		return Integer.parseInt(query.getSingleResult() + "");
	}
}
