<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >
<html>
  <head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="${pageContext.request.contextPath}/resource/bootstarp/bootstrap.min.css" rel="stylesheet"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/my97/WdatePicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
  	<script	src="${pageContext.request.contextPath}/resource/bootstarp/jquery-2.1.1.min.js"></script>
  	<script	src="${pageContext.request.contextPath}/resource/bootstarp/bootstrap.min.js"></script>
  
  </head>
  	
  <body onload="heigthAuto()">
  	<div class="container-fluid">
  		<div style="margin: 5px ;">
    	<form action="${pageContext.request.contextPath}/api/data/alarm/${sid}" class="form-inline" id="pageList"> 
				<input type="hidden" name="page" value="${page}"  /> 
				报警类型：<select  name="queryType" >
			    		        <option value="0" ${queryType=='0'?"selected":" " }>全部</option>
			    		        <option value="1" ${queryType=='1'?"selected":" " }>设备相互碰撞</option>
			    		        <option value="2" ${queryType=='2'?"selected":" " }>区域限制</option>
			    		        <option value="3" ${queryType=='3'?"selected":" " }>障碍物碰撞</option>
			    		        <option value="4" ${queryType=='4'?"selected":" " }>限位报警</option>
			    		        <option value="5" ${queryType=='5'?"selected":" " }>起重量超载</option>
			    		        <option value="6" ${queryType=='6'?"selected":" " }>力矩超载</option>
			    		        <option value="7" ${queryType=='7'?"selected":" " }>风速过大</option>
			    		        <option value="8" ${queryType=='8'?"selected":" " }>塔身倾斜</option>
		  		</select>
    			开始时间：<input type="text"  name="queryBeginDate"  value="${queryBeginDate }"   class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
    			结束时间：<input type="text"  name="queryEndDate"  value="${queryEndDate }"  class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
    			<input type="submit" value="查 询"  onclick="javascript:confirmQuery()" class="btn btn-primary"/>
		</form>
  		</div>
		<div class="table-responsive">
    	<table class="table table-striped table-bordered table-hover">
    	<thead>
    		<tr class="info">
	    		<th rowspan="2" >序号</th>
	    		<th rowspan="2" >时间</th>
	    		<th rowspan="2" >报警原因</th>
	    		<th colspan="7" class="text-center" >报警时运行状态</th>
	    		<th rowspan="2" >回放</th>
	    	</tr>
	    	<tr  class="info">
	    		<td >幅度(m)</td>
	    		<td >角度(°)</td>
	    		<td >高度(m)</td>
	    		<td >称重(t)</td>
	    		<td >力矩(%)</td>
	    		<td >风速(m/s)</td>
	    		<td >倾角(°)</td>
	    	</tr>
    	</thead>
    	
	   <c:forEach items="${pageView.records}" var="entity" varStatus="s"> 
		   <tr>
		   		<td>${s.index+1 }</td>
		    	<td  id="time_${entity.id}"><fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		    	<td>
		    		<c:if test="${entity.alarmType==1}">相互干涉报警</c:if>
		    		<c:if test="${entity.alarmType==2}">禁行区保护报警</c:if>
		    		<c:if test="${entity.alarmType==3}">障碍物碰撞报警</c:if>
		    		<c:if test="${entity.alarmType==4}">${entity.message}</c:if>
		    		<c:if test="${entity.alarmType==5}">起重量报警</c:if>
		    		<c:if test="${entity.alarmType==6}">力矩报警</c:if>
		    		<c:if test="${entity.alarmType==7}">风速报警</c:if>
		    		<c:if test="${entity.alarmType==8}">倾斜报警</c:if>
		    	</td>
		    	<td 
		    	<c:if test="${fn:contains(entity.message,'远限位')}" > style="color: red;" </c:if> 
		    	<c:if test="${fn:contains(entity.message,'近限位')}" > style="color: red;" </c:if> 
		    	>${entity.amplitude }</td>
		    	
		    	<td
		    	<c:if test="${entity.rotion >= 500 }" > style="color: red;" </c:if>
		    	<c:if test="${entity.rotion <= -500 }" > style="color: red;" </c:if>
		    	>${entity.rotion }</td>
		    	
		    	<td
		    	<c:if test="${fn:contains(entity.message,'高限位')}" > style="color: red;" </c:if>
		    	<c:if test="${fn:contains(entity.message,'低限位')}" > style="color: red;" </c:if> 
		    	>
		    	${entity.height }
		    	</td>
		    	
		    	<td
		    	<c:if test="${fn:contains(entity.message,'起重量')}" > style="color: red;" </c:if>
		    	<c:if test="${entity.alarmType==5}" > style="color: red;" </c:if>
		    	>${entity.lift }</td>
		    	
		    	<td
		    	<c:if test="${entity.alarmType==6}" > style="color: red;" </c:if>
		    	>${entity.torque }</td>
		    	
		    	<td
		    	<c:if test="${entity.alarmType==7}" > style="color: red;" </c:if>
		    	>${entity.windVelocity }</td>
		    	
		    	<td
		    	<c:if test="${entity.alarmType==8}" > style="color: red;" </c:if>
		    	>${entity.dipangleX }</td>
		    	<td><a href="javascript:playBack('${entity.id }')" >回放</a></td>
	    	</tr>
	    </c:forEach>
    	</table>
    	</div>
	  	<div class="fenye">
	  		<%@ include file="/WEB-INF/page/common/fenye.jsp" %>
	  	</div>
	</div>
  <script type="text/javascript">
  	function heigthAuto(){
  		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
  	    parent.layer.iframeAuto(index);
  	}
  </script>
  </body>
</html>
