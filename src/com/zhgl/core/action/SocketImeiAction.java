package com.zhgl.core.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhgl.api.parse.JsonUtil;
import com.zhgl.api.parse.json.UseData;
import com.zhgl.api.parse.json.UseRecord;
import com.zhgl.core.ebean.DeviceModel;
import com.zhgl.core.ebean.Point;
import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.ebean.TowerCraneDevice;
import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.core.service.DeviceModelService;
import com.zhgl.core.service.PointService;
import com.zhgl.core.service.SocketImeiService;
import com.zhgl.core.service.TowerCraneDeviceService;
import com.zhgl.core.service.TowerCraneStatusService;
import com.zhgl.run.server.SocketUtil;
import com.zhgl.util.IdentityGenerator;
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
	@Resource
	private TowerCraneStatusService towerCraneStatusService;
	@Resource
	private TowerCraneDeviceService towerCraneDeviceService;
	private int maxresult = 10;
	@Autowired
	private HttpServletRequest request;

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

	/**
	 * 保存或修改设备注册相关数据
	 * 
	 * @param sid
	 *            通讯ID
	 * @param mid
	 *            设备型号ID
	 * @param pid
	 *            俯视点ID
	 * @param enable
	 *            是否启用
	 * @param fuid
	 *            省平台使用ID
	 * @param lnglat
	 *            经纬度
	 * @param scenenum
	 *            现场编号
	 * @return
	 */
	@RequestMapping(value = "/save/data")
	public @ResponseBody
	String saveData(long sid, long mid, long pid, int enable,
			@RequestParam(required = false) String fuid, String lnglat,
			String scenenum) {
		try {
			// 开始前进行数据校验：
			SocketImei entity = socketImeiService.find(sid);
			entity.setPoint(pointService.find(pid));
			entity.setDeviceModel(deviceModelService.find(mid));
			if (enable == 1) {
				entity.setEnable(true);
			} else {
				entity.setEnable(false);
			}
			if (entity.getActiveDate() == null) {
				entity.setActiveDate(new Date());
			}

			// 处理经纬度及现场编号
			TowerCraneDevice towerCraneDevice = entity.getTowerCraneDevice();
			String longitude = lnglat.split(",")[0];
			String latitude = lnglat.split(",")[1];
			if (towerCraneDevice == null) {
				towerCraneDevice = new TowerCraneDevice();
				towerCraneDevice.setDnumber(scenenum);
				towerCraneDevice.setLatitude(latitude);
				towerCraneDevice.setLongitude(longitude);
				towerCraneDevice.setId(IdentityGenerator.generatorID());
				towerCraneDevice.setSocketImei(entity);
				towerCraneDevice.setRegTime(new Date());
				towerCraneDeviceService.save(towerCraneDevice);
			} else {
				towerCraneDevice.setDnumber(scenenum);
				towerCraneDevice.setLatitude(latitude);
				towerCraneDevice.setLongitude(longitude);
				towerCraneDevice.setRegTime(new Date());
				towerCraneDeviceService.update(towerCraneDevice);
			}
			// 同步省平台相关数据
			System.out.println(fuid);
			if (fuid != null && !"".equals(fuid)) {
				towerCraneStatusService.syncData(fuid, entity);
				JsonUtil.postPoint(fuid, sid, entity.getPoint(), request
						.getServletContext().getAttribute("basePath") + "");
			}
			socketImeiService.update(entity);
			// 回传俯视点信息
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
	@RequestMapping(value = "/query/find/{sid}", produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String queryOneSocketImet(@PathVariable long sid) {
		try {
			System.out.println(sid);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			SocketImei si = socketImeiService.find(sid);
			dataMap.put("sid", si.getId());
			DeviceModel deviceModel = si.getDeviceModel();
			if (deviceModel != null) {
				dataMap.put("mid", deviceModel.getId());
			} else {
				dataMap.put("mid", 0);
			}
			if (si.getActiveDate() != null) {
				dataMap.put("enable", si.getEnable() ? 1 : 2);
			} else {
				dataMap.put("enable", 0);
			}
			Point point = si.getPoint();
			if (point != null) {
				dataMap.put("pid", point.getId());
				dataMap.put("pname", point.getName());
			} else {
				dataMap.put("pid", 0);
				dataMap.put("pname", "");
			}
			TowerCraneDevice towerCraneDevice = si.getTowerCraneDevice();
			if (towerCraneDevice != null) {
				dataMap.put("lnglat", towerCraneDevice.getLongitude() + ","
						+ towerCraneDevice.getLatitude());
				dataMap.put("scenenum", towerCraneDevice.getDnumber());
			} else {
				dataMap.put("lnglat", "");
				dataMap.put("scenenum", "");
			}
			TowerCraneStatus towerCraneStatus = si.getTowerCraneStatus();
			if (towerCraneStatus != null) {
				dataMap.put("fuid", towerCraneStatus.getFuid());
				dataMap.put("funum", towerCraneStatus.getfUseRecordNUmber());
			} else {
				dataMap.put("fuid", "");
				dataMap.put("funum", "");
			}
			dataMap.put("imei", si.getImei());
			dataMap.put("imeiASCII", SocketUtil.analyticASCII(si.getImei()));
			String json = mapper.writeValueAsString(dataMap);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	@RequestMapping(value = "/usenumber/query/{keyword}")
	public @ResponseBody
	String useNumberQuery(@PathVariable String keyword) {
		return useNumberQueryJson(keyword);
	}

	@RequestMapping(value = "/usenumber/query/ajax/{keyword}.json")
	public @ResponseBody
	String useNumberQueryJson(@PathVariable String keyword) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			UseData useData = JsonUtil.parseUseData(keyword, "");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (UseRecord useRecord : useData.getList()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", useRecord.getfID());
				dataMap.put("label", useRecord.getfUseRecordNUmber());// 塔机使用备案号
				dataMap.put("pnum", useRecord.getfRecordNUmber());// 塔机备案号
				dataMap.put("project", useRecord.getfProjectName());// 塔机备案号
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
