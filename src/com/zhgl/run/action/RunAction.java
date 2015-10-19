package com.zhgl.run.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zhgl.core.ebean.Point;
import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.service.PointService;
import com.zhgl.core.service.SocketImeiService;
import com.zhgl.run.ebean.EventAlarm;
import com.zhgl.run.server.ActiveTowers;
import com.zhgl.run.service.EventAlarmService;
import com.zhgl.run.service.EventViolationService;
import com.zhgl.run.service.EventWorkCycleService;
import com.zhgl.run.service.NewDataService;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.dao.PageView;

@RequestMapping("/manage/run")
@Controller
public class RunAction {
	@Resource
	private SocketImeiService socketImeiService;
	@Resource
	private PointService pointService;
	@Resource
	private EventWorkCycleService eventWorkCycleService;
	@Resource
	private EventAlarmService eventAlarmService;
	@Resource
	private EventViolationService eventViolationService;
	@Resource
	private NewDataService newDataService;
	private int maxresult = 10;

	@RequestMapping("/point/list")
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
		ModelAndView mav = new ModelAndView("data/run_point_list");
		mav.addObject("pageView", pageView);
		mav.addObject("page", page);
		mav.addObject("queryContent", queryContent);
		mav.addObject("m", 11);
		Date now = new Date();
		Date endDate = HelperUtil.addDays(now, 1);
		Date startDate = HelperUtil.reduceDays(endDate, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mav.addObject("startDate", sdf.format(startDate));
		mav.addObject("endDate", sdf.format(endDate));
		return mav;
	}

	@RequestMapping("/point/device")
	public ModelAndView deviceList(long pid) {
		ModelAndView mav = new ModelAndView("data/load_device");
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.point.id=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(pid);
		List<SocketImei> dataList = socketImeiService.getScrollData(
				jpql.toString(), params.toArray(), orderby).getResultList();
		int deviceOnlineNumber = 0;
		for (SocketImei si : dataList) {
			boolean online = false;
			if (si.getSocketIdRecord() != null) {
				online = ActiveTowers.isOnline(si.getSocketIdRecord().getSid());
			}
			si.setOnline(online);
			if (online) {
				deviceOnlineNumber = deviceOnlineNumber + 1;
			}
			// 今日累积吊重
			Date now = new Date();
			Date endDate = HelperUtil.addDays(now, 1);
			Date startDate = HelperUtil.reduceDays(endDate, 1);
			double countWeight = eventWorkCycleService.countWeight(si.getId(),
					startDate, endDate);
			si.setTotalWeight(countWeight);
			// 今日报警
			long countAlarm = eventAlarmService.countToday(si.getId());
			si.setTotalAlarm(countAlarm);
			// 今日违章
			long countVio = eventViolationService.countToday(si.getId());
			si.setTotalVio(countVio);
			// 实时报警
			si.setCurretnAlarm("无");
		}
		mav.addObject("dataList", dataList);
		mav.addObject("deviceTotalNumber", dataList.size());
		mav.addObject("deviceOnlineNumber", deviceOnlineNumber);
		return mav;
	}
}
