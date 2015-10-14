package com.zhgl.run.service;

import java.util.Date;
import java.util.HashMap;

import com.zhgl.run.ebean.EventAlarm;
import com.zhgl.util.dao.DAO;

public interface EventAlarmService extends DAO<EventAlarm> {
	/**
	 * 根据塔机id找出，传入时间范围内的报警数据，并将报警数据中的报警类型，数量按键值对的方式储存进map
	 */
	public HashMap<String, String> getTypeCount(Date beginData, Date endDate,
			int sid);

	/**
	 * 根据塔机id找出，所有的报警数据，并将报警数据中的报警类型，数量按键值对的方式储存进map
	 */
	public HashMap<String, String> getTypeCount(int sid);

	/**
	 * 距当前时间2小时内的报警数据
	 * 
	 * @param tcsid
	 * @return
	 */
	public EventAlarm nowArarm(int sid);

	/**
	 * 统计指定塔机今日报警数量
	 * 
	 * @param tcsid
	 * @return
	 */
	public long countToday(int sid);

	/**
	 * 查出在此时间范围内,此塔机违章的数量
	 */
	public int getCount(Date beginDate, Date endDate, int sid);
}
