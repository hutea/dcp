package com.zhgl.api.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhgl.core.ebean.SocketIdRecord;
import com.zhgl.core.ebean.SocketImei;
import com.zhgl.core.ebean.TowerCraneDevice;
import com.zhgl.core.ebean.TowerCraneStatus;
import com.zhgl.core.service.SocketImeiService;
import com.zhgl.core.service.TowerCraneDeviceService;
import com.zhgl.run.ebean.EventAlarm;
import com.zhgl.run.ebean.EventViolation;
import com.zhgl.run.ebean.EventWorkCycle;
import com.zhgl.run.server.ActiveTowers;
import com.zhgl.run.server.SocketUtil;
import com.zhgl.run.server.Tower;
import com.zhgl.run.service.EventAlarmService;
import com.zhgl.run.service.EventViolationService;
import com.zhgl.run.service.EventWorkCycleService;
import com.zhgl.util.HelperUtil;
import com.zhgl.util.dao.PageView;
import com.zhgl.util.dao.QueryResult;

@RequestMapping("/api")
@Controller
public class ApiServerAction {
	@Resource
	private SocketImeiService socketImeiService;
	@Resource
	private EventWorkCycleService eventWorkCycleService;
	@Resource
	private EventAlarmService eventAlarmService;
	@Resource
	private EventViolationService eventViolationService;
	@Resource
	private TowerCraneDeviceService towerCraneDeviceService;
	@Autowired
	private HttpServletRequest request;
	private int maxresult = 10;

	public @RequestMapping(value = "/device/list/{pid}", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	String listDevice(@PathVariable long pid) {
		try {
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer(
					"o.visible=?1 and o.point.id=?2");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			params.add(pid);
			List<SocketImei> list = socketImeiService.getScrollData(
					jpql.toString(), params.toArray(), orderby).getResultList();

			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			int onlineNumber = 0;
			for (SocketImei si : list) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("online", ActiveTowers.isOnline(si.getSocketIdRecord()
						.getSid()) ? "在线" : "离线");
				if (ActiveTowers.isOnline(si.getSocketIdRecord().getSid())) {
					onlineNumber = onlineNumber + 1;
				}
				TowerCraneStatus towerCraneStatus = si.getTowerCraneStatus();
				if (towerCraneStatus != null) {
					map.put("fRecordNumber",
							towerCraneStatus.getfRecordNumber());
					map.put("fuid", towerCraneStatus.getFuid());
				} else {
					map.put("fRecordNumber", "");
					map.put("fuid", "");
				}
				TowerCraneDevice towerCraneDevice = si.getTowerCraneDevice();
				if (towerCraneDevice != null) {
					map.put("sceneNumber", towerCraneDevice.getDnumber());
				} else {
					map.put("sceneNumber", "");
				}
				map.put("deviceModel", si.getDeviceModel().getModel());
				// 今日累积吊重
				Date now = new Date();
				Date endDate = HelperUtil.addDays(now, 1);
				Date startDate = HelperUtil.reduceDays(endDate, 1);
				double countWeight = eventWorkCycleService.countWeight(
						startDate, endDate, si.getId());
				map.put("totalWeight", countWeight + "");
				// 今日报警
				long countAlarm = eventAlarmService.countToday(si.getId());
				map.put("totalAlarm", countAlarm + "");
				// 今日违章
				long countVio = eventViolationService.countToday(si.getId());
				map.put("totalVio", countVio + "");
				// 实时报警
				map.put("currentAlarm", "无");
				// url地址
				String baseUrl = request.getServletContext()
						.getAttribute("basePath").toString();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				map.put("baseInfoUrl",
						baseUrl + "api/data/baseInfo/" + si.getId()); // 基本信息
				map.put("todayWeightyUrl",
						baseUrl + "api/data/weight/" + si.getId()
								+ "?queryBeginDate=" + sdf.format(startDate)
								+ "&queryEndDate=" + sdf.format(endDate));// 今日吊重
				map.put("todayAlarmUrl",
						baseUrl + "api/data/alarm/" + si.getId()
								+ "?queryBeginDate=" + sdf.format(startDate)
								+ "&queryEndDate=" + sdf.format(endDate)
								+ "&queryType=0");// 今日报警
				map.put("todayVioUrl", baseUrl + "api/data/vio/" + si.getId()
						+ "?queryBeginDate=" + sdf.format(startDate)
						+ "&queryEndDate=" + sdf.format(endDate)
						+ "&queryType=0");// 今日违章
				map.put("newDataUrl",
						baseUrl + "api/data/newData/" + si.getId());// 实时数据
				map.put("historyDataUrl",
						baseUrl + "api/data/historyData/" + si.getId()
								+ "?queryBeginDate=" + sdf.format(startDate)
								+ "&queryEndDate=" + sdf.format(endDate));// 历史数据
				map.put("runDataUrl",
						baseUrl + "api/data/runData/" + si.getId());// 运行图
				dataList.add(map);
			}
			Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			dataMap.put("totalNumber", dataList.size());
			dataMap.put("onlineNumber", onlineNumber);
			dataMap.put("list", dataList);
			String json = mapper.writeValueAsString(dataMap);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@RequestMapping("/data/overlook/{sid}")
	public ModelAndView overlook(@PathVariable long sid) {
		ModelAndView mav = new ModelAndView("data/api/overlook");
		return mav;
	}

	/**
	 * 设备基本信息图
	 * 
	 * @param sid
	 * @return
	 */
	@RequestMapping("/data/baseInfo/{sid}")
	public ModelAndView baseInfo(@PathVariable long sid) {
		SocketImei si = socketImeiService.find(sid);
		SocketIdRecord idRecord = si.getSocketIdRecord();
		Tower tower = null;
		if (idRecord != null) {
			tower = ActiveTowers.getTower(idRecord.getSid());
		}
		TowerCraneDevice towerCraneDevice = si.getTowerCraneDevice();
		if (tower == null) {// 不在线，从来没有登录过
			tower = new Tower();
			tower.setId(si.getId() + ""); // 设置塔机ID
			TowerCraneStatus towerCraneStatus = si.getTowerCraneStatus();
			if (towerCraneStatus != null) {
				tower.setPropertyNumbers(towerCraneStatus.getfRecordNumber()); // 产权编号
			} else {
				tower.setPropertyNumbers(""); // 产权编号
			}
			if (towerCraneDevice.getDnumber() != null
					&& !towerCraneDevice.getDnumber().equals("")) {
				String newStr = towerCraneDevice.getDnumber().replaceAll("#",
						"＃");
				String newStr1 = newStr.replaceAll(",", "，");
				String newStr2 = newStr1.replaceAll("\"", "");
				tower.setOnSiteNum(newStr2);// 现场编号
			} else {
				tower.setOnSiteNum(towerCraneDevice.getDnumber() == null ? ""
						: towerCraneDevice.getDnumber()); // 现场编号
			}
			tower.setMultiple(towerCraneDevice.getMagnification()); // 吊绳倍率
			tower.setX(towerCraneDevice.getXcoord() == null ? 0
					: towerCraneDevice.getXcoord()); // x坐标
			tower.setY(towerCraneDevice.getYcoord() == null ? 0
					: towerCraneDevice.getYcoord()); // y坐标
			tower.setFixAngle(towerCraneDevice.getInstallZero() == null ? 0
					: towerCraneDevice.getInstallZero()); // 安装零点（塔机顶升归零方向与坐标系+X轴夹角）
			tower.setForearm(towerCraneDevice.getArmLengthFront() == null ? 0
					: towerCraneDevice.getArmLengthFront()); // 起重臂长（前臂）
			tower.setBackarm(towerCraneDevice.getArmLengthBack() == null ? 0
					: towerCraneDevice.getArmLengthBack()); // 平衡臂长（后臂）
			tower.setArmHeight(towerCraneDevice.getArmHeight() == null ? 0
					: towerCraneDevice.getArmHeight()); // 塔臂高度
			tower.setCap(towerCraneDevice.getTowerTop() == null ? 0
					: towerCraneDevice.getTowerTop()); // 塔帽高度
			tower.setLeftLimit(towerCraneDevice.getLeftLimit() == null ? 0
					: towerCraneDevice.getLeftLimit());// 右限位
			tower.setRightLimit(towerCraneDevice.getRightLimit() == null ? 0
					: towerCraneDevice.getRightLimit());// 右限位
			tower.setFarLimit(towerCraneDevice.getFarLimit() == null ? 0
					: towerCraneDevice.getFarLimit());// 远限位
			tower.setNearLimit(towerCraneDevice.getNearLimit() == null ? 0
					: towerCraneDevice.getNearLimit());// 近限位
			tower.setHeightLimit(towerCraneDevice.getHightLimit() == null ? 0
					: towerCraneDevice.getHightLimit());// 高限位
			tower.setWeightLimit(towerCraneDevice.getLiftLimit() == null ? 0
					: towerCraneDevice.getLiftLimit());// 起重限位
			tower.setTorqueLimit(towerCraneDevice.getTorqueLimit() == null ? 0
					: towerCraneDevice.getTorqueLimit());// 力矩限位
			// 动态参数设置为0，不在线
			tower.setAngle(0); // 回转角度
			tower.setWidth(0);// 幅度
			tower.setHeight(0);// 吊勾高度
			tower.setWeight(0);// 称重
			tower.setTorque(0);// 力矩
			tower.setBrakeAngle(0); // 刹车角度
			tower.setWind(0); // 风速
			tower.setInclineX(0);// 塔身倾斜度X向
			tower.setInclineY(0);// 塔身倾斜度Y向
			tower.setLimitAlarm(0);// 限位报警编码
			tower.setOtherAlarm(0);// 其他报警编码
			tower.setBumpAlarm(0);// 塔机碰撞报警编码
			tower.setForbidAlarm(0);// 禁行区碰撞报警编码
			tower.setBaffleAlarm(0);// 障碍物碰撞报警编码
			tower.setRelay(0);// 继电输出编码
			tower.startTimer(); // 开启定时器，检查在线状态
			ActiveTowers.update(si.getSocketIdRecord().getSid(), tower);// 则根据imei查询出塔机ID和通信ID,添加到ActiveTowers中
		}

		ModelAndView mav = new ModelAndView("data/api/baseInfo");
		mav.addObject("tower", tower);
		mav.addObject("si", si);
		mav.addObject("refresh", 1);
		mav.addObject("towerCraneDevice", towerCraneDevice);
		return mav;
	}

	/**
	 * 工作循环吊重
	 */
	@RequestMapping("/data/weight/{sid}")
	public ModelAndView weightList(@PathVariable long sid,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String queryBeginDate,
			@RequestParam(required = false) String queryEndDate) {

		PageView<EventWorkCycle> pageView = new PageView<EventWorkCycle>(
				maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer(
				"o.visible=?1 and o.socketImei.id=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(sid);
		if (queryBeginDate != null) {
			jpql.append(" and o.startTime>=?" + (params.size() + 1));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				params.add(sdf.parse(queryBeginDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (queryEndDate != null) {
			jpql.append(" and o.startTime<=?" + (params.size() + 1));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				params.add(sdf.parse(queryEndDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		pageView.setQueryResult(eventWorkCycleService.getScrollData(
				pageView.getFirstResult(), 11, jpql.toString(),
				params.toArray(), orderby));
		ModelAndView mav = new ModelAndView("data/api/weight_list");
		mav.addObject("pageView", pageView);
		mav.addObject("sid", sid);
		mav.addObject("page", page);
		mav.addObject("queryBeginDate", queryBeginDate);
		mav.addObject("queryEndDate", queryEndDate);
		return mav;
	}

	/**
	 * 报警
	 */
	@RequestMapping("/data/alarm/{sid}")
	public ModelAndView alarmList(@PathVariable long sid,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String queryBeginDate,
			@RequestParam(required = false) String queryEndDate,
			@RequestParam(defaultValue = "0") int queryType) {
		PageView<EventAlarm> pageView = new PageView<EventAlarm>(maxresult,
				page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer(
				"o.visible=?1 and o.socketImei.id=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(sid);
		if (queryBeginDate != null) {
			jpql.append(" and o.createTime>=?" + (params.size() + 1));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				params.add(sdf.parse(queryBeginDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (queryEndDate != null) {
			jpql.append(" and o.createTime<=?" + (params.size() + 1));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				params.add(sdf.parse(queryEndDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (queryType != 0) {
			jpql.append(" and o.alarmType=?" + (params.size() + 1));
			params.add(queryType);
		}
		pageView.setQueryResult(eventAlarmService.getScrollData(
				pageView.getFirstResult(), 11, jpql.toString(),
				params.toArray(), orderby));
		ModelAndView mav = new ModelAndView("data/api/alarm_list");
		mav.addObject("pageView", pageView);
		mav.addObject("sid", sid);
		mav.addObject("page", page);
		mav.addObject("queryBeginDate", queryBeginDate);
		mav.addObject("queryEndDate", queryEndDate);
		return mav;
	}

	/**
	 * 违章
	 */
	@RequestMapping("/data/vio/{sid}")
	public ModelAndView vioList(@PathVariable long sid,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String queryBeginDate,
			@RequestParam(required = false) String queryEndDate,
			@RequestParam(defaultValue = "0") int queryType) {
		PageView<EventViolation> pageView = new PageView<EventViolation>(
				maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer(
				"o.visible=?1 and o.socketImei.id=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(sid);
		if (queryBeginDate != null) {
			jpql.append(" and o.createTime>=?" + (params.size() + 1));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				params.add(sdf.parse(queryBeginDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (queryEndDate != null) {
			jpql.append(" and o.createTime<=?" + (params.size() + 1));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				params.add(sdf.parse(queryEndDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (queryType != 0) {
			jpql.append(" and o.alarmType=?" + (params.size() + 1));
			params.add(queryType);
		}
		pageView.setQueryResult(eventViolationService.getScrollData(
				pageView.getFirstResult(), 11, jpql.toString(),
				params.toArray(), orderby));
		ModelAndView mav = new ModelAndView("data/api/vio_list");
		mav.addObject("pageView", pageView);
		mav.addObject("sid", sid);
		mav.addObject("page", page);
		mav.addObject("queryBeginDate", queryBeginDate);
		mav.addObject("queryEndDate", queryEndDate);
		return mav;
	}

	@RequestMapping("/data/newData/{sid}")
	public ModelAndView newData(@PathVariable long sid) {
		ModelAndView mav = this.baseInfo(sid);
		mav.setViewName("data/api/newData");
		mav.addObject("refresh", 1);
		return mav;
	}

	@RequestMapping("/data/historyData/{sid}")
	public ModelAndView historyData(@PathVariable long sid,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String queryBeginDate,
			@RequestParam(required = false) String queryEndDate) {

		ModelAndView mav = new ModelAndView("data/api/historyData_list");
		mav.addObject("sid", sid);
		mav.addObject("page", page);
		mav.addObject("queryBeginDate", queryBeginDate);
		mav.addObject("queryEndDate", queryEndDate);

		PageView<Tower> pageView = new PageView<Tower>(maxresult, page);
		// System.out.print("进入读写文件算法");
		pageView.setPageBarSize(3);
		Date startDate = null;
		Date endDate = null;
		Long startDateLong = null;
		Long endDateLong = null;
		QueryResult<Tower> qr = new QueryResult<Tower>();// 查询结果集
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf02 = new SimpleDateFormat("yyyyMMdd");
		try {
			if (queryBeginDate != null && !"".equals(queryBeginDate)) {
				startDate = sdf.parse(queryBeginDate);
			}
			if (queryEndDate != null && !"".equals(queryEndDate)) {
				endDate = sdf.parse(queryEndDate);
			}
			startDateLong = startDate.getTime();
			endDateLong = endDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String path = ActiveTowers.path + "\\" + sid; // 目录名称为SoceketImei 主键ID
		File dir = new File(path);
		if (!dir.exists()) {
			return mav;
		}

		List<Tower> list = new ArrayList<Tower>();

		long totalSize = 0; // 若干个文件的总大小
		File[] files = dir.listFiles();// 列出该塔机目录下的所有日志文件，默认按照文件名时间倒序排列
		// //System.out.print(files.length);
		List<File> fileList = new ArrayList<File>();
		for (File file : files) {
			// totalSize += file.length();// 计算出所有文件的总大小
			String str = file.getName().substring(0, 8);
			Long time = 0l;
			try {
				if (str != null && !"".equals(str)) {
					time = sdf02.parse(str).getTime();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if ((time >= startDateLong && time <= endDateLong)
					|| (time <= startDateLong && time >= endDateLong)) {
				fileList.add(file);
			}
		}
		if (fileList.size() < 1) {// 没有改时间锻的历史记录
			return mav;
		}
		for (File file : fileList) {
			totalSize += file.length();
		}
		long totalRecords = totalSize / ActiveTowers.dataSize;// 计算出总记录条数
		qr.setTotalrecords(totalRecords);
		long offset = pageView.getFirstResult() * ActiveTowers.dataSize;// 页数和每页记录数确定后的起始记录的位置：0、maxresult*dataSize、maxresult*2*dataSize、maxresult*3*dataSize......
		totalSize = 0;// 清零使用
		int resultSize = maxresult * ActiveTowers.dataSize;// 该页读取的最大字节数,数组最大字节数为最大记录数×每条记录大小
		for (int i = fileList.size() - 1; i >= 0; i--) { // 从最新文件开始计算
			long fSize = fileList.get(i).length(); // 当前文件的大小
			totalSize += fSize; // 累计文件的大小
			long leftSize = totalSize - offset; // 当前文件剩余需要读取的部分，大于零则可以读了
			if (leftSize > 0) { // 循环过的文件的总量大于读取起始点，当前文件剩下部分，可以从offset位置开始读取
				if (leftSize >= resultSize) {// 在本文件中一次可以读取完
					SocketUtil.addList(
							list,
							readFile(leftSize - resultSize, resultSize,
									fileList.get(i)));// 一次性读取该页所有数据
				} else { // 一次读取不完，要继续读下一个文件
					SocketUtil.addList(list,
							readFile(0, (int) leftSize, fileList.get(i))); // 读取完本文件剩下的数据
					leftSize = resultSize - leftSize; // 还剩下需要读取的数据大小
					while (leftSize >= 0 && i >= 0) { // i--下一个文件
						long fileSize = fileList.get(i).length(); // 当前文件的大小
						if (leftSize <= fileSize) { // 文件大小比要读取的量要大，当次文件够读
							String str = readFile(fileSize - leftSize,
									(int) leftSize, fileList.get(i)); // 读取完本文件剩下的数据
							SocketUtil.addList(list, str);
							break;// 读取完毕退出while循环
						} else { // 当次文件不够读，文件大小比要读取的量要小
							SocketUtil
									.addList(
											list,
											readFile(0, (int) fileSize,
													fileList.get(i))); // 读取完本文件剩下的数据
							leftSize = leftSize - fileSize;// 还剩下需要读取的数据大小
						}
					}
				}
				break;// 读取完毕退出for循环
			}
		}
		qr.setResultList(list);
		pageView.setQueryResult(qr);
		pageView.setTotalPage(pageView.getTotalPage() - 1);
		mav.addObject("pageView", pageView);
		return mav;
	}

	@RequestMapping("/data/runData/{sid}")
	public ModelAndView rundData(@PathVariable long sid) {
		ModelAndView mav = new ModelAndView("data/api/runData");
		return mav;
	}

	@RequestMapping("/data/map")
	public ModelAndView mapData(@RequestParam(required = false) Long sid) {
		ModelAndView mav = new ModelAndView("data/api/map");
		try {
			List<TowerCraneDevice> towerCraneDevices = new ArrayList<TowerCraneDevice>();
			if (sid != null && sid > 0) {// 在地图上展示某一个塔机
				TowerCraneDevice towerCraneDevice = towerCraneDeviceService
						.findBySID(sid);
				towerCraneDevices.add(towerCraneDevice);
			} else {// 展示所有
				LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
				orderby.put("id", "asc");
				StringBuffer jpql = new StringBuffer(
						"o.longitude!=null and o.latitude!=null and o.socketImei.state=?1");
				List<Object> params = new ArrayList<Object>();
				params.add(1);
				towerCraneDevices = towerCraneDeviceService.getScrollData(
						jpql.toString(), params.toArray(), orderby)
						.getResultList();
			}
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			for (TowerCraneDevice device : towerCraneDevices) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				// 拼接提示框内容START
				StringBuffer content = new StringBuffer(
						"<div style='font-size:12px;color:#123456'>");
				TowerCraneStatus towerCraneStatus = device.getSocketImei()
						.getTowerCraneStatus();
				if (towerCraneStatus != null) {
					content.append("<div>产权编号："
							+ towerCraneStatus.getfRecordNumber() + "</div>");
					content.append("<div>现场编号：" + device.getDnumber()
							+ "</div>");
					content.append("<div>工程名称："
							+ towerCraneStatus.getfProjectName() + "</div>");
					content.append("<div>工程地址："
							+ towerCraneStatus.getfProjectAdress() + "</div>");
				} else {
					content.append("<div>产权编号：" + "</div>");
					content.append("<div>现场编号：" + "</div>");
					content.append("<div>俯视点名称："
							+ device.getSocketImei().getPoint().getName()
							+ "</div>");
					content.append("<div>俯视点地址："
							+ device.getSocketImei().getPoint().getAddress()
							+ "</div>");
				}
				content.append("<div style='color:blue'>********防碰撞参数********</div>");
				content.append("<div>无线频点：" + device.getWlpoint() + "</div>");
				content.append("<div>节点地址：" + device.getPointAddress()
						+ "</div>");
				content.append("<div>网络ID：" + device.getNetId() + "</div>");
				content.append("<div>塔吊ID：" + device.getTowerCraneId()
						+ "</div>");
				content.append("<div>塔群ID：" + device.getTowerCraneGroupId()
						+ "</div>");
				content.append("<div>X坐标：" + device.getXcoord() + "</div>");
				content.append("<div>Y坐标：" + device.getYcoord() + "</div>");
				float armFront = device.getArmLengthFront() == null ? 0
						: device.getArmLengthFront();
				float armBack = device.getArmLengthBack() == null ? 0 : device
						.getArmLengthBack();
				content.append("<div>起重臂长：" + (armFront + armBack) + "</div>");
				content.append("<div>塔身高度：" + device.getArmHeight() + "</div>");
				content.append("<div>塔帽高度：" + device.getTowerTop() + "</div>");
				content.append("</div>");

				map.put("lng", device.getLongitude());
				map.put("lat", device.getLatitude());
				map.put("content", content.toString());
				dataList.add(map);
				// 拼接提示框内容END
			}
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(dataList);
			System.out.println(json);
			mav.addObject("json", json);
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("json", "[]");
		}
		mav.addObject("centerPoint",
				request.getServletContext().getAttribute("centerPoint")
						.toString());
		return mav;
	}

	/**
	 * 从文件中读取值
	 * 
	 * @param offset文件偏移量
	 * @param size读取大小
	 * @param file文件对象
	 * 
	 * @return 读取的数据转换为字符串
	 */
	public String readFile(long offset, int size, File file) {
		byte[] datas = new byte[size];
		BufferedInputStream bis = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file.getPath());
			bis = new BufferedInputStream(fis);
			bis.skip(offset);
			bis.read(datas, 0, size);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new String(datas);
	}
}
