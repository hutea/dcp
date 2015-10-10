package com.zhgl.core.service;

import java.util.List;

import com.zhgl.core.ebean.DeviceModel;
import com.zhgl.util.dao.DAO;

public interface DeviceModelService extends DAO<DeviceModel> {

	public List<DeviceModel> listAll();

}
