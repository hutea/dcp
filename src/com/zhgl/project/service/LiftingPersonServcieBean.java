package com.zhgl.project.service;

import org.springframework.stereotype.Service;

import com.zhgl.project.ebean.LiftingPerson;
import com.zhgl.util.dao.DAOSupport;

@Service
public class LiftingPersonServcieBean extends DAOSupport<LiftingPerson> implements LiftingPersonService{

}
