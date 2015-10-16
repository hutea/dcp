package com.zhgl.util.sfl;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import com.zhgl.config.ebean.SystemParam;
import com.zhgl.config.service.SystemParamService;

@Component
public class SystemInitListener implements ServletContextAware,
		ApplicationListener<ContextRefreshedEvent> {
	@Resource
	private SystemParamService systemParamService;

	@Override
	public void setServletContext(ServletContext context) {
		System.out.println("初始化系统配置参数......");
		context.setAttribute("basePath", systemParamService.find("basePath")
				.getContent()); // 项目根路径
		context.setAttribute("code", systemParamService.find("code")
				.getContent()); // 地区代码
		context.setAttribute("centerPoint",
				systemParamService.find("centerPoint").getContent()); // 中心点经纬度
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

	}

}
