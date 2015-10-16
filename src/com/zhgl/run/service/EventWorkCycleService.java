package com.zhgl.run.service;

import java.util.Date;

import com.zhgl.run.ebean.EventWorkCycle;
import com.zhgl.util.dao.DAO;

public interface EventWorkCycleService extends DAO<EventWorkCycle> {
	/**
	 * 通过塔机id统计工作循环次数 id 塔机id
	 */
	public int countEventById(long sid);

	/**
	 * 通过塔机id统计工作循环累计吊重 id 塔机id
	 */
	public double countWeightEvent(long sid);

	/**
	 * 通过塔机id统计工作循环累计吊重时间 id 塔机id
	 */
	public int countTimeEvent(long sid);

	/**
	 * 统计一定时间内工作循环的总量
	 * 
	 * @return
	 */
	public double countWeight(Date beginDate, Date endDate, long sid);

	/**
	 * 统计一定时间内工作循环的次数
	 * 
	 * @return
	 */
	public int countEvent(Date beginDate, Date endDate, long sid);

	/**
	 * 统计一定时间内工作循环的工作时间
	 * 
	 * @return：秒数
	 */
	public int countTime(Date beginDate, Date endDate, long sid);

}
