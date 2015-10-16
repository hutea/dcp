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
    	<form action="${pageContext.request.contextPath}/api/data/weight/${sid}" class="form-inline" id="pageList"> 
				<input type="hidden" name="page" value="${page}"  /> 
    			开始时间：<input type="text"  name="queryBeginDate"  value="${queryBeginDate }"   class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
    			结束时间：<input type="text"  name="queryEndDate"  value="${queryEndDate }"  class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
    			<input type="submit" value="查 询"  onclick="javascript:confirmQuery()" class="btn btn-primary"/>
		</form>
  		</div>
		<div class="table-responsive">
    	<table class="table table-striped table-bordered table-hover">
    	<thead>
    		<tr class="info">
	    		<th  rowspan="2">序号</th>
	    		<th  rowspan="2">开始时间</th>
	    		<th  rowspan="2">结束时间</th>
	    		<th  rowspan="2">吊重(T)</th>
	    		<th  colspan="5" class="text-center">工作循环统计</th>
	    		<th  colspan="3" class="text-center">起吊点状态</th>
	    		<th  colspan="3" class="text-center">卸吊点状态</th>
    		</tr>
	    	<tr class="info">
	    		<td>最大力矩(T*m)</td>
	    		<td>最大高度(m)</td>
	    		<td>最小高度(m)</td>
	    		<td>最大幅度(m)</td>
	    		<td>最小幅度(m)</td>
	    		<td>角度(°)</td>
	    		<td>幅度(m)</td>
	    		<td>高度(m)</td>
	    		<td>角度(°)</td>
	    		<td>幅度(m)</td>
	    		<td>高度(m)</th>
	    	</tr>
    	</thead>
    	
	   <c:forEach items="${pageView.records}" var="entity" varStatus="s"> 
		   <tr>
			   	<td>${s.index+1 }</td>
			   	<td>${entity.startTime }</td>
			   	<td>${entity.overTime }</td>
			   	<td>${entity.largestWeight }</td>
			   	<td>${entity.largestTorque }</td>
			   	<td>${entity.largestHeight }</td>
			   	<td>${entity.smallestHeight }</td>
			   	<td>${entity.largestAmplitude }</td>
			   	<td>${entity.smallestAmplitude }</td>
			   	<td>${entity.liftingPointAngle }</td>
			   	<td>${entity.liftingPointAmplitude }</td>
			   	<td>${entity.liftingPointHeight }</td>
			   	<td>${entity.unloadingPointAngle }</td>
			   	<td>${entity.unloadingPointAmplitude }</td>
			   	<td>${entity.unloadingPointHeight }</td>
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
