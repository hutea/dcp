package com.zhgl.core.service;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.util.dao.DAOSupport;

@Service
public class TowerCraneStatusServiceBean extends DAOSupport<TowerCraneStatus>
		implements TowerCraneStatusService {

}
