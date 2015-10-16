package com.zhgl.run.service;

import java.util.Date;
import java.util.HashMap;

import com.zhgl.run.ebean.EventViolation;
import com.zhgl.util.dao.DAO;

public interface EventViolationService extends DAO<EventViolation> {

	public HashMap<String, String> getTypeCount(long sid);

	public HashMap<String, String> getTypeCount(Date beginDate, Date endDate,
			long sid);

	/**
	 * 统计指定塔机今日违章数量
	 * 
	 * @param tcsid
	 * @return
	 */
	public long countToday(long sid);

	/**
	 * 查出在此时间范围内,此塔机违章的数量
	 */
	public int getCount(Date beginDate, Date endDate, long sid);
}
