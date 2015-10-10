package com.zhgl.core.service;

import org.springframework.stereotype.Service;

import com.zhgl.core.ebean.Point;
import com.zhgl.util.dao.DAOSupport;

@Service
public class PointServiceBean extends DAOSupport<Point> implements PointService {

}
