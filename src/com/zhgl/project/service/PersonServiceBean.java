package com.zhgl.project.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.zhgl.project.ebean.Person;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.dao.DAOSupport;

@Service
public class PersonServiceBean extends DAOSupport<Person> implements
		PersonService {

	@Override
	public int writeTemplate(String idcard, String name, String template,
			String template2, int cat, int subd) {
		try {
			int length = template.length() + template2.length();
			StringBuffer sql = new StringBuffer();
			sql.append("update Person set template=?1 ,template2=?2, name=?3,pyjm=?4,category=?5, subd=?6,version=version+1 where idcard=?7");
			Query query = em.createQuery(sql.toString())
					.setParameter(1, template).setParameter(2, template2)
					.setParameter(3, name)
					.setParameter(4, HelperUtil.converPinYin(name))
					.setParameter(5, cat).setParameter(6, subd)
					.setParameter(7, idcard);
			int result = query.executeUpdate();
			if (result == 0) {// 执行插入操作
				Person person = new Person();
				person.setName(name);
				person.setPyjm(HelperUtil.converPinYin(name));
				person.setIdcard(idcard);
				person.setCategory(cat);
				person.setSubd(subd);
				person.setVersion(1);// 设置指纹版本
				if (template.length() == 1112) {
					person.setTemplate(template);
				}
				if (template2.length() == 1112) {
					person.setTemplate2(template2);
				}
				this.save(person);
			}
			return length;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Person findByRecordid(String recordid) {
		try {
			/*
			 * 
			 * 
			 * Query query = em .createQuery( "select p from Person p where
			 * p.recordid=?1").setParameter(1, recordid); return (Person)
			 * query.getSingleResult() ;
			 */
			Query query = em.createQuery(
					"select p from Person p where p.recordid=?1").setParameter(
					1, recordid);
			return (Person) query.getSingleResult();
		} catch (Exception e) {

			return null;
		}
	}

	@Override
	public List<Person> listByTCSId(String tcsid) {
		return null;
	}
}
