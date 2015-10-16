package com.zhgl.config.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zhgl.config.ebean.SystemParam;
import com.zhgl.config.service.SystemParamService;

@RequestMapping("/manage/config")
@Controller
public class SystemParamAction {
	@Resource
	private SystemParamService systemParamService;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/param")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("config/systemParam");
		mav.addObject("socketID", systemParamService.find("socketID")
				.getContent());
		mav.addObject("basePath", systemParamService.find("basePath")
				.getContent());
		mav.addObject("code", systemParamService.find("code").getContent());
		mav.addObject("centerPoint", systemParamService.find("centerPoint")
				.getContent());
		mav.addObject("m", 63);
		return mav;
	}

	@RequestMapping("/param/update")
	public ModelAndView update(String socketID, String basePath, String code,
			String centerPoint) {
		SystemParam socketParam = systemParamService.find("socketID");
		socketParam.setContent(socketID);
		// systemParamService.update(socketParam);

		SystemParam pathParam = systemParamService.find("basePath");
		pathParam.setContent(basePath);
		systemParamService.update(pathParam);

		SystemParam codeParam = systemParamService.find("code");
		codeParam.setContent(code);
		systemParamService.update(codeParam);

		SystemParam pointParam = systemParamService.find("centerPoint");
		pointParam.setContent(centerPoint);
		systemParamService.update(pointParam);

		request.getServletContext().setAttribute("basePath", basePath); // 项目根路径
		request.getServletContext().setAttribute("code", code); // 地区代码
		request.getServletContext().setAttribute("centerPoint", centerPoint); // 中心点经纬度

		ModelAndView mav = new ModelAndView("redirect:/manage/config/param");
		return mav;
	}
}
