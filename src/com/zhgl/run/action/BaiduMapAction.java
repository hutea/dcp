package com.zhgl.run.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhgl.core.ebean.TowerCraneDevice;
import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.core.service.TowerCraneDeviceService;
import com.zhgl.util.dao.PageView;

/**
 * 百度电子地图
 */
@RequestMapping("/manage/run/")
@Controller
public class BaiduMapAction {
	@Resource
	private TowerCraneDeviceService towerCraneDeviceService;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/map")
	public ModelAndView map() {
		ModelAndView mav = new ModelAndView("data/run_map");
		return mav;
	}

}
