package com.zhgl.core.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.zhgl.core.ebean.DeviceModel;
import com.zhgl.core.service.DeviceModelService;
import com.zhgl.util.dao.PageView;

@RequestMapping("/manage/device/model")
@Controller
public class DeviceModelAction {
	@Resource
	private DeviceModelService deviceModelService;
	private int maxresult = 10;

	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String queryContent) {
		PageView<DeviceModel> pageView = new PageView<DeviceModel>(maxresult,
				page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		if (queryContent != null && !"".equals(queryContent)) {
			jpql.append(" and (o.model like?2 or o.manufacturer like?3 )");
			params.add("%" + queryContent + "%");
			params.add("%" + queryContent + "%");
		}
		pageView.setQueryResult(deviceModelService.getScrollData(
				pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby));
		ModelAndView mav = new ModelAndView("core/deviceModel_list");
		mav.addObject("pageView", pageView);
		mav.addObject("page", page);
		mav.addObject("queryContent", queryContent);
		mav.addObject("m", 51);
		return mav;
	}

	// 添加
	@RequestMapping("/save")
	public ModelAndView add(@ModelAttribute DeviceModel deviceModel) {
		deviceModelService.save(deviceModel);
		ModelAndView mav = new ModelAndView("redirect:list");
		return mav;
	}

	// 修改导向
	@RequestMapping("/edit/{mid}")
	public ModelAndView editUI(@PathVariable Long mid) {
		ModelAndView mav = new ModelAndView("core/deviceModel_edit");
		mav.addObject("model", deviceModelService.find(mid));
		mav.addObject("m", 51);
		return mav;
	}

	// 修改
	@RequestMapping("/update")
	public ModelAndView edit(@ModelAttribute DeviceModel deviceModel) {
		DeviceModel entity = deviceModelService.find(deviceModel.getId());
		entity.setManufacturer(deviceModel.getManufacturer());
		entity.setModel(deviceModel.getModel());
		entity.setNote(deviceModel.getNote());
		deviceModelService.update(entity);
		ModelAndView mav = new ModelAndView("redirect:list?page=1");
		return mav;
	}

	// 批量删除
	@RequestMapping("/delete/more")
	public @ResponseBody
	String deleteMore(@RequestParam long[] ids) {
		List<String> list = new ArrayList<String>();
		for (long id : ids) {
			DeviceModel model = deviceModelService.find(id);
			model.setVisible(false);
			deviceModelService.update(model);
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
}
