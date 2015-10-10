package com.zhgl.run.service;

import java.util.HashMap;

import com.zhgl.run.ebean.EventFault;
import com.zhgl.util.dao.DAO;

public interface EventFaultService extends DAO<EventFault> {
	public HashMap<String, String> getTypeCount(String id);

	/**
	 * 查出在此时间范围内,此塔机未处理的故障的数量
	 */
	public int getCount(String tid);
}
