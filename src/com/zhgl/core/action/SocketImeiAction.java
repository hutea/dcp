package com.zhgl.core.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhgl.core.ebean.DeviceModel;
import com.zhgl.core.ebean.Point;
import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.ebean.TowerCrane;
import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.core.service.DeviceModelService;
import com.zhgl.core.service.PointService;
import com.zhgl.core.service.SocketImeiService;
import com.zhgl.util.dao.PageView;

@RequestMapping("/manage/device")
@Controller
public class SocketImeiAction {
	@Resource
	private SocketImeiService socketImeiService;
	@Resource
	private PointService pointService;
	@Resource
	private DeviceModelService deviceModelService;
	private int maxresult = 10;

	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String queryContent) {
		PageView<SocketImei> pageView = new PageView<SocketImei>(maxresult,
				page);
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
		pageView.setQueryResult(socketImeiService.getScrollData(
				pageView.getFirstResult(), maxresult, jpql.toString(),
				params.toArray(), orderby));
		ModelAndView mav = new ModelAndView("core/socketImei_list");
		mav.addObject("pageView", pageView);
		mav.addObject("page", page);
		mav.addObject("queryContent", queryContent);
		mav.addObject("models", deviceModelService.listAll());
		mav.addObject("m", 52);
		return mav;
	}

	@RequestMapping(value = "/save/data")
	public @ResponseBody
	String saveData(int sid, long mid, long pid, int enable,
			@RequestParam(required = false) String fuid) {
		try {
			SocketImei entity = socketImeiService.find(sid);
			entity.setPoint(pointService.find(pid));
			entity.setDeviceModel(deviceModelService.find(mid));
			if (enable == 1) {
				entity.setEnable(true);
			}
			if (entity.getActiveDate() == null) {
				entity.setActiveDate(new Date());
			}
			socketImeiService.update(entity);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	/**
	 * 获取SocketImet数据信息，用作设备注册时对模态框数据的还原
	 * 
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/query/find/{sid}")
	public @ResponseBody
	String queryOneSocketImet(@PathVariable int sid) {
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			SocketImei si = socketImeiService.find(sid);
			dataMap.put("sid", si.getSid());
			DeviceModel deviceModel = si.getDeviceModel();
			if (deviceModel != null) {
				dataMap.put("mid", deviceModel.getId());
			} else {
				dataMap.put("mid", 0);
			}
			dataMap.put("enabel", si.getEnable() ? 1 : 2);
			Point point = si.getPoint();
			if (point != null) {
				dataMap.put("pid", point.getId());
				dataMap.put("pname", point.getName());
			} else {
				dataMap.put("pid", 0);
				dataMap.put("pname", "");
			}
			String json = mapper.writeValueAsString(dataMap);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	@RequestMapping(value = "/usenumber/query/{keyword}")
	public @ResponseBody
	String pointQuery(@PathVariable String keyword) {
		return pointQueryJson(keyword);
	}

	@RequestMapping(value = "/usenumber/query/ajax/{keyword}.json")
	public @ResponseBody
	String pointQueryJson(@PathVariable String keyword) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<TowerCraneStatus> tcsList = new ArrayList<TowerCraneStatus>();
			for (int i = 0; i < 3; i++) {
				TowerCraneStatus tcs = new TowerCraneStatus();
				tcs.setId(i + "_id");
				tcs.setUseNumber("num-" + i);
				tcsList.add(tcs);
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (TowerCraneStatus point : tcsList) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", point.getId());
				dataMap.put("label", point.getUseNumber());
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
