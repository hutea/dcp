<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<table class="table table-bordered table-striped mytable">
	<thead>
		<tr>
			<th>#</th>
			<th>在线</th>
			<th>产权备案号</th>
			<th>现场编号</th>
			<th>监控设备型号</th>
			<th>今日累积吊重</th>
			<th>今日报警</th>
			<th>今日违章</th>
			<th>实时报警</th>
			<th>实时数据</th>
			<th>历史数据</th>
			<th>运行图</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="entry" varStatus="s">
			<tr>
				<td>${s.index+1 }${entry.sid }</td>
				<td>${entry.online?"在线":"离线" }${entry.sid }</td>
				<td>${entry.towerCraneStatus.towerCrane.babh }</td>
				<td>${entry.towerCraneDevice.dnumber}</td>
				<td>${entry.deviceModel.model}</td>
				<td>${entry.cycleTotalWeight }</td>
				<td>${entry.alarmCount }</td>
				<td>${entry.violationCount }</td>
				<td>${entry.curretnAlarm }</td>
				<td><a>Click me</a></td>
				<td><a href="javascript:loadRun('entry.sid')">注册</a></td>
				<td><a href="javascript:loadRun('entry.sid')" title="运行图查看">查看</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>