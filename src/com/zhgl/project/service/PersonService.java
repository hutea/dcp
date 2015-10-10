package com.zhgl.project.service;

import java.util.List;

import com.zhgl.project.ebean.Person;
import com.zhgl.util.dao.DAO;

public interface PersonService extends DAO<Person> {

	/**
	 * 根据身份证号存储指纹数据
	 * 
	 * @param idcard
	 *            ：身份证号
	 * @param template
	 *            ：指纹模板数据
	 * @param num
	 *            ：指纹序号
	 * @return:存储成功返回指纹字串长度，失败返回0
	 */
	public int writeTemplate(String idcard, String name, String template,
			String template2, int cat, int subd);

	/**
	 * 根据司机当前塔机状态id返回塔机当前司机列表
	 * 
	 * @param tcsid
	 * @return
	 */
	public List<Person> listByTCSId(String tcsid);

	/**
	 * 检查所有特种人员RecordId(只查询ID 提高查询效率)
	 * 
	 * @param tcsid
	 * @return
	 */
	public Person findByRecordid(String recordid);

	/**
	 * 
	 * 
	 * @param perosn
	 */
	// public void updateForPro(Person perosn);

}
