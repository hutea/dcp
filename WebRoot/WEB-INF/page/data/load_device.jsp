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
				<td>${s.index+1 }${entry.id }</td>
				<td>${entry.online?"在线":"离线" }</td>
				<td>${entry.towerCraneStatus.towerCrane.babh }</td>
				<td>${entry.towerCraneDevice.dnumber}</td>
				<td><a href="javascript:loadBaseInfo('${entry.id }')" >${entry.deviceModel.model}</a></td>
				<td>
					<c:if test="${entry.totalWeight<=0}">${entry.totalWeight }</c:if>
					<c:if test="${entry.totalWeight>=0}"><a href="javascript:loadWeight('${entry.id}')" >${entry.totalWeight }</a></c:if>
				</td>
				<td>
					<c:if test="${entry.totalAlarm<=0}">${entry.totalAlarm }</c:if>
					<c:if test="${entry.totalAlarm>=0}"><a href="javascript:loadAlarm('${entry.id}')" >${entry.totalAlarm}</a></c:if>
				</td>
				<td>
					<c:if test="${entry.totalVio<=0}">${entry.totalVio }</c:if>
					<c:if test="${entry.totalVio>=0}"><a href="javascript:loadVio('${entry.id}')" >${entry.totalVio}</a></c:if>
				</td>
				<td>${entry.curretnAlarm }</td>
				<td><a href="javascript:loadNewData('${entry.id}')" title="实时数据">查看</a></td>
				<td><a href="javascript:loadHistoryData('${entry.id}')" title="历史数据">查看</a></td>
				<td><a href="javascript:loadRunData('${entry.id}')" title="运行图查看">查看</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>