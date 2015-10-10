package com.zhgl.project.service;

import org.springframework.stereotype.Service;

import com.zhgl.project.ebean.Project;
import com.zhgl.util.dao.DAOSupport;

@Service
public class ProjectServiceBean extends DAOSupport<Project> implements ProjectService{

}
