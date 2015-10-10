package com.zhgl.run.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zhgl.core.service.PointService;

@RequestMapping("/run")
@Controller
public class RunAction {
	@Resource
	private PointService pointService;

	@RequestMapping("/point/list")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String queryContent) {
		ModelAndView mav = new ModelAndView("data/point_list");
		mav.addObject("m", 1);
		return mav;
	}

}
