package com.zhgl.project.service;

import org.springframework.stereotype.Service;

import com.zhgl.project.ebean.ProjectDepartment;
import com.zhgl.util.dao.DAOSupport;

@Service
public class ProjectDepartmentServiceBean extends DAOSupport<ProjectDepartment>
		implements ProjectDepartmentService {

}
