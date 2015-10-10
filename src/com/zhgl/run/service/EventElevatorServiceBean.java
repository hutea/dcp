package com.zhgl.run.service;

import org.springframework.stereotype.Service;

import com.zhgl.run.ebean.EventElevator;
import com.zhgl.util.dao.DAOSupport;

@Service
public class EventElevatorServiceBean extends DAOSupport<EventElevator>
		implements EventElevatorService {

}
