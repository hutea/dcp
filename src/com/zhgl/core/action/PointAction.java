package com.zhgl.core.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhgl.core.ebean.Point;
import com.zhgl.core.service.PointService;
import com.zhgl.util.dao.PageView;

@RequestMapping("/manage/point")
@Controller
public class PointAction {
	@Resource
	private PointService pointService;
	private int maxresult = 10;

	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String queryContent) {
		PageView<Point> pageView = new PageView<Point>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		if (queryContent != null && !"".equals(queryContent)) {
			jpql.append(" and (o.name like?2 or o.address like?3 )");
			params.add("%" + queryContent + "%");
			params.add("%" + queryContent + "%");
		}
		pageView.setQueryResult(pointService.getScrollData(
				pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby));
		ModelAndView mav = new ModelAndView("core/point_list");
		mav.addObject("pageView", pageView);
		mav.addObject("page", page);
		mav.addObject("queryContent", queryContent);
		mav.addObject("m", 61);
		return mav;
	}

	// 添加导向
	@RequestMapping("/new")
	public ModelAndView addUI() {
		ModelAndView mav = new ModelAndView("core/point_new");
		return mav;
	}

	// 添加
	@RequestMapping("/save")
	public ModelAndView add(@ModelAttribute Point point) {
		pointService.save(point);
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}

	// 修改导向
	@RequestMapping("/edit/{pid}")
	public ModelAndView editUI(@PathVariable Long pid) {
		ModelAndView mav = new ModelAndView("core/point_edit");
		mav.addObject("point", pointService.find(pid));
		mav.addObject("m", 61);
		return mav;
	}

	// 修改
	@RequestMapping("/update")
	public ModelAndView edit(@ModelAttribute Point point) {
		Point entity = pointService.find(point.getId());
		entity.setName(point.getName());
		entity.setAddress(point.getAddress());
		entity.setModifyDate(new Date());
		pointService.update(entity);
		ModelAndView mav = new ModelAndView("redirect:list?page=1");
		return mav;
	}

	// 批量删除
	@RequestMapping("/delete/more")
	public @ResponseBody
	String deleteMore(@RequestParam long[] ids) {
		List<String> list = new ArrayList<String>();
		for (long id : ids) {
			Point point = pointService.find(id);
			point.setVisible(false);
			pointService.update(point);
			list.add(id + "");
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(list);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	@RequestMapping(value = "/query/{keyword}")
	public @ResponseBody
	String pointQuery(@PathVariable String keyword) {
		return pointQueryJson(keyword);
	}

	@RequestMapping(value = "/query/ajax/{keyword}.json")
	public @ResponseBody
	String pointQueryJson(@PathVariable String keyword) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and (o.name like?2 or o.address like?3)");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			List<Point> points = pointService.getScrollData(0, 10,
					jpql.toString(), params.toArray(), orderby).getResultList();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Point point : points) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", point.getId());
				dataMap.put("label", point.getName());
				dataMap.put("address", point.getAddress());
				list.add(dataMap);
			}
			String json = mapper.writeValueAsString(list);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
