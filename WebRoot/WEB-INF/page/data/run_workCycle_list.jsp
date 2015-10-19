<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>工作循环</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<%@ include file="/WEB-INF/page/common/pack-style.jsp" %>
	<style type="text/css">
		.layui-layer-border{
			border: none;
		}
	</style>
</head>
<body>
	<!-- HEADER -->
	<%@ include file="/WEB-INF/page/common/header.jsp" %>
	<!--/HEADER -->
	
	<!-- PAGE -->
	<section id="page">
				<!-- SIDEBAR -->
				<%@ include file="/WEB-INF/page/common/sidebar.jsp" %>
				<!-- /SIDEBAR -->
		<div id="main-content">
			<div class="container">
				<div class="row">
					<div id="content" class="col-lg-12">
						<!-- PAGE HEADER-->
						<div class="row">
							<div class="col-sm-12">
								<div class="page-header">
									<!-- STYLER -->
									
									<!-- /STYLER -->
									<!-- BREADCRUMBS -->
									<ul class="breadcrumb">
										<li>
											<i class="fa fa-home"></i>
											<a href="index.html">Home</a>
										</li>
										<li>
											<a href="javascript:void(0)">分析统计</a>
										</li>
										<li>工作循环</li>
									</ul>
									<!-- /BREADCRUMBS -->
									<div class="clearfix">
										<h3 class="content-title pull-left">设备工作循环数据分析</h3>
									</div>
									<div class="description"></div>
								</div>
							</div>
						</div>
						<!-- /PAGE HEADER -->
						
						<!-- 内容区 -->
						<div class="row mycontent">
							<form id="pageList" action="${pageContext.request.contextPath}/manage/workCycle/list" method="post">
				      			<input type="hidden" name="page" value="${page}">
				      		 	<div class="form-group col-md-3" > 
		                         	<input type="text"  name="queryContent" value="${queryContent}" class="form-control col-md-3"  placeholder="对名称，地址进行模糊查询"  >
	                         	</div>
				      		 	<div class="form-group col-md-2" > 
				      		 		<select id="sel" name="queryDateType" onchange="change()" class="form-control">
					    		        <option value="0" ${queryDateType=='0'?"selected":" " }>今天</option>
					    		        <option value="1" ${queryDateType=='1'?"selected":" " }>昨天</option>
					    		        <option value="2" ${queryDateType=='2'?"selected":" " }>前天</option>
					    		        <option value="3" ${queryDateType=='3'?"selected":" " }>一周内</option>
					    		        <option value="4" ${queryDateType=='4'?"selected":" " }>一月内</option>
					    		        <option value="5" ${queryDateType=='5'?"selected":" " }>一年内</option>
					    		        <option value="6" ${queryDateType=='6'?"selected":" " }>全部</option>
					    		        <option value="7" ${queryDateType=='7'?"selected":" " }>自定义</option>
				  		       		</select>
	                         	</div>
	                         	
				      		 	<div class="form-group col-md-7" > 
									 <span id="date" ${queryDateItem==7?'style="display:;"':'style="display:none;"'} >
										 <input type="text" id="queryBeginDate"  name="queryBeginDate" value="${queryBeginDate }" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
									   	   至
									 	 <input type="text" id="queryEndDate" name="queryEndDate" value="${queryEndDate }" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
									 </span>
									 	                         	
		                       		<input type="button"  style="margin-left: 5px;" class="btn btn-primary"   value="查 询" onclick="confirmQuery()"  >
	                         	</div>
				      		</form>
				      		
				      		<table class="table table-bordered table-striped mytable" >
						    <thead>
						    	<tr>
						    		<th  >序号</th>
						    		<th  >使用备案号</th>
						    		<th  >设备现场编号</th>
						    		<th  >使用单位</th>
						    		<th  >俯视点</th>
						    		<th  >累计吊重</th>
						    		<th  >累计次数</th>
						    		<th  >累计时间(分)</th>
						    		<th  >统计</th>
						    		<th  >详细</th>
						    	</tr>
						    </thead>
						    <tbody>
				      		<c:forEach items="${pageView.records }" var="entry" varStatus="s">
				      			<tr>
				      				<td>${s.index+1 }</td>
				      				<td>${entry.towerCraneStatus.fUseRecordNUmber }</td>
				      				<td>${entry.towerCraneDevice.dnumber}</td>
				      				<td>${entry.towerCraneStatus.fUseUnitName }</td>
				      				<td>${entry.point.name}</td>
				      				<td>${entry.totalWeight}</td>
				      				<td>${entry.totalWeightNumber}</td>
				      				<td>${entry.totalWeightTime}</td>
									<td><a href="javascript:loadWeigthCount('${entry.id}')" title="统计信息">查看</a></td>
									<td><a href="javascript:loadWeight('${entry.id}')" title="详细数据">查看</a></td>
				      			</tr>
							</c:forEach>
						    </tbody>
						    </table>
				      		
	                        <div class="col-md-12"> 
                        		<%@ include file="/WEB-INF/page/common/fenye.jsp" %>
                        	</div>
				    	</div>
						<!--/内容区 -->
						<!-- 底部 -->
                        	<%@ include file="/WEB-INF/page/common/bottom.jsp" %>
						<!-- 底部 -->
					</div>
				</div>
			</div>
		</div>
	</section>
	 <div class="modal fade" id="myModal"  role="dialog" aria-label="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
            </div>
        </div>
     </div>
	<!--/PAGE -->
	<!-- JAVASCRIPTS -->
	<%@ include file="/WEB-INF/page/common/pack-js.jsp" %>
	<!-- /JAVASCRIPTS -->
	<script	src="${pageContext.request.contextPath}/resource/js/workCycle.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/my97/WdatePicker.js"></script>
	
</body>
</html>