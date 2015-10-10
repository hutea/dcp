package com.zhgl.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.DeviceModel;
import com.zhgl.util.dao.DAOSupport;

@Service
public class DeviceModelServiceBean extends DAOSupport<DeviceModel> implements
		DeviceModelService {

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceModel> listAll() {
		return em.createQuery("select o from DeviceModel o where o.visible=?1")
				.setParameter(1, true).getResultList();
	}

}
