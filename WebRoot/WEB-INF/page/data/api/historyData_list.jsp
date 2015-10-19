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
    			开始时间：<input type="text"  name="queryBeginDate"  value="${queryBeginDate }"   class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
    			结束时间：<input type="text"  name="queryEndDate"  value="${queryEndDate }"  class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
    			<input type="submit" value="查 询"  onclick="javascript:confirmQuery()" class="btn btn-primary"/>
		</form>
  		</div>
		<div class="table-responsive">
    	<table class="table table-striped table-bordered table-hover">
    	<thead>
    		<tr>
	    		<th  >序号</th>
	    		<th  >采集时间</th>
	    		<th  >小车幅度（m）</th>
	    		<th  >回转角度(°)</th>
	    		<th  >吊钩高度（m）</th>
	    		<th  >吊重(T)</th>
	    		<th  >力矩(%)</th>
	    		<th  >风速(m/s)</th>
	    		<th  >实时报警</th>
    		</tr>
    	</thead>
    	
	    <c:forEach items="${pageView.records}" var="entity" varStatus="s"> 
	   		<tr>
		    	<td>${s.index+1 }</td>
			    	<td><fmt:parseDate value="${entity.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			    	<td>${entity.width }</td>
			    	<td>${entity.angle }</td>
			    	<td>${entity.height }</td>
			    	<td>${entity.weight }</td>
			    	<td>${entity.torque }</td>
			    	<td>${entity.wind }</td>
			    	<td>${entity.nowAlarm }</td>
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
