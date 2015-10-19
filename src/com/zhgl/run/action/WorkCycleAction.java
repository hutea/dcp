package com.zhgl.run.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.service.SocketImeiService;
import com.zhgl.run.service.EventWorkCycleService;
import com.zhgl.util.dao.PageView;

@RequestMapping("/manage/workCycle")
@Controller
public class WorkCycleAction {
	@Resource
	private SocketImeiService socketImeiService;
	@Resource
	private EventWorkCycleService eventWorkCycleService;
	private int maxresult = 10;

	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false) String queryContent,
			@RequestParam(required = false, defaultValue = "0") int queryDateType,
			@RequestParam(required = false) String queryBeginDate,
			@RequestParam(required = false) String queryEndDate) {

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

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		Date beginDate = null;
		Date endDate = null;
		switch (queryDateType) {
		case 0:// 今天
			cal.set(year, month, day, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day + 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 1:// 昨天
			cal.set(year, month, day - 1, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 2:// 前天
			cal.set(year, month, day - 2, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day - 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 3:// 一周内
			cal.set(year, month, day - 6, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day + 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 4:// 一月内
			cal.set(year, month - 1, day + 1, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day + 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 5:// 一年内
			cal.set(year - 1, month, day + 1, 0, 0, 0);
			beginDate = cal.getTime();
			cal.set(year, month, day + 1, 0, 0, 0);
			endDate = cal.getTime();
			break;
		case 6: // 全部
			break;
		case 7: // 自定义
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				beginDate = sdf.parse(queryBeginDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				endDate = sdf.parse(queryEndDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		if (queryDateType == 6) {// 查询全部
			for (SocketImei si : pageView.getRecords()) {
				si.setTotalWeight(eventWorkCycleService.countWeight(si.getId()));
				si.setTotalWeightNumber(eventWorkCycleService.countNumber(si
						.getId()));
				si.setTotalWeightTime(eventWorkCycleService.countTime(si
						.getId()));
			}
		} else {
			for (SocketImei si : pageView.getRecords()) {
				si.setTotalWeight(eventWorkCycleService.countWeight(si.getId(),
						beginDate, endDate));
				si.setTotalWeightNumber(eventWorkCycleService.countNumber(
						si.getId(), beginDate, endDate));
				si.setTotalWeightTime(eventWorkCycleService.countTime(
						si.getId(), beginDate, endDate));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			queryBeginDate = sdf.format(beginDate);
			queryEndDate = sdf.format(endDate);
		}

		ModelAndView mav = new ModelAndView("data/run_workCycle_list");
		mav.addObject("pageView", pageView);
		mav.addObject("m", 21);
		mav.addObject("queryDateType", queryDateType);
		mav.addObject("queryBeginDate", queryBeginDate);
		mav.addObject("queryEndDate", queryEndDate);
		return mav;
	}
}
