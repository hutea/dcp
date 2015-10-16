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
				报警类型：	<select  name="queryType" >
			    		        <option value="0" ${queryType=='0'?"selected":" " }>全部</option>
			    		        <option value="1" ${queryType=='1'?"selected":" " }>起重量超载</option>
			    		        <option value="2" ${queryType=='2'?"selected":" " }>区域限制</option>
			    		        <option value="3" ${queryType=='3'?"selected":" " }>障碍物碰撞</option>
			    		        <option value="4" ${queryType=='4'?"selected":" " }>设备相互碰撞</option>
			    		        <option value="5" ${queryType=='5'?"selected":" " }>指纹认证失败</option>
		  		</select>
    			开始时间：<input type="text"  name="queryBeginDate"  value="${queryBeginDate }"   class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
    			结束时间：<input type="text"  name="queryEndDate"  value="${queryEndDate }"  class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
    			<input type="submit" value="查 询"  onclick="javascript:confirmQuery()" class="btn btn-primary"/>
		</form>
  		</div>
		<div class="table-responsive">
    	<table class="table table-striped table-bordered table-hover">
    	<thead>
    		<tr class="info" >
	    		<th rowspan="2" >序号</th>
	    		<th rowspan="2" >时间</th>
	    		<th rowspan="2" >违章原因</th>
	    		<th colspan="5" class="text-center">违章时运行状态</th>
	    		<th rowspan="2" >回放</th>
	    	</tr>
	    	<tr class="info" >
	    		<th >幅度(m)</th>
	    		<th >角度(°)</th>
	    		<th >高度(m)</th>
	    		<th >称重(t)</th>
	    		<th >力矩(%)</th>
	    	</tr>
    	</thead>
    	
	   <c:forEach items="${pageView.records}" var="entity" varStatus="s"> 
		   <tr>
		   		<td>${s.index+1 }</td>
				<td id="time_${entity.id}"><fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		    	<td>
					<c:if test="${entity.type==1}">起重量超载</c:if>
					<c:if test="${entity.type==2}">区域限制</c:if>
					<c:if test="${entity.type==3}">障碍物碰撞</c:if>
					<c:if test="${entity.type==4}">设备相互碰撞</c:if>
					<c:if test="${entity.type==5}">指纹认证失败</c:if>
		    	</td>
		    	<td>${entity.amplitude }</td>
		    	<td>${entity.rotion }</td>
		    	<td>${entity.height }</td>
		    	<td
		    	<c:if test="${entity.type==1}"> style="color: red;"  </c:if> >${entity.weight }</td>
		    	<td>${entity.torque }</td>
		    	<td><a href="javascript:palyBack('${entity.id }')" >回放</a></td>
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
