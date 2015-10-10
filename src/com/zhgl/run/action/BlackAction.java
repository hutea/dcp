package com.zhgl.run.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/test")
@Controller
public class BlackAction {
	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String queryContent) {
		ModelAndView mav = new ModelAndView("data/blank_page");
		mav.addObject("m", 1);
		return mav;
	}

}
