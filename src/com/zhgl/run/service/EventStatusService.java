package com.zhgl.run.service;

import com.zhgl.run.ebean.EventStatus;
import com.zhgl.util.dao.DAO;

public interface EventStatusService extends DAO<EventStatus> {
	/**
	 * 统计距当前时间的两小时内塔机身份上报情况
	 * 
	 * @param tcsid
	 * @return
	 */
	public EventStatus countIn2Hour(String tcsid);

	/**
	 * 作废：2小时未上报指纹，自动生成上报记录使用。 根据socketId查询出该塔基最后一条身份验证的信息
	 * 
	 * @param socketId
	 * @return
	 */
	public EventStatus todayLastDataByTCID(Integer socketId);
}
