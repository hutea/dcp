package com.zhgl.run.service;


import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.EventOnOff;
import com.zhgl.util.dao.DAOSupport;

@Service
public class EventOnOffServiceBean extends DAOSupport<EventOnOff> implements
		EventOnOffService {

}
