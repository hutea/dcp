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
	<title>监控设备注册</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<link href="${pageContext.request.contextPath}/resource/typeahead/mystyle.css"  rel="stylesheet"></link>
	<%@ include file="/WEB-INF/page/common/pack-style.jsp" %>
	
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
											<a href="#">系统管理</a>
										</li>
										<li>监控设备注册</li>
									</ul>
									<!-- /BREADCRUMBS -->
									<div class="clearfix">
										<h3 class="content-title pull-left">监控设备注册</h3>
									</div>
									<div class="description">监控设备注册包括：设备激活，设备与使用备案数据的绑定</div>
								</div>
							</div>
						</div>
						<!-- /PAGE HEADER -->
						
						<!-- 内容区 -->
						<div class="row mycontent">
							<form id="pageList" action="${pageContext.request.contextPath}/manage/point/list" method="post">
				      			<input type="hidden" name="page" value="${page}">
				      		 	<div class="form-group col-md-7" > 
	                         	</div>
				      		 	<div class="form-group col-md-3" > 
		                         	<input type="text" name="queryContent"  value="${queryContent}" class="form-control col-md-3"  placeholder="对名称，地址进行模糊查询"  >
		                         </div>
				      		 	<div class="form-group col-md-2" > 
		                       		<input type="button"  class="btn btn-primary"   value="查 询" onclick="confirmQuery()"  >
	                         	</div>
				      		</form>
					      	<table class="table table-bordered table-striped mytable">
	                           <thead>
	                                <tr>
	                                    <th>#</th>
	                                    <th>通讯ID</th>
	                                    <th>IMEI号</th>
	                                    <th>使用备案号</th>
	                                    <th>俯视点</th>
	                                    <th>设备型号</th>
	                                    <th>加入时间</th>
	                                    <th>激活时间</th>
	                                    <th>注册时间</th>
	                                    <th>操作</th>
	                                </tr>
	                           </thead>
	                           <tbody>
	                                  <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
		                           	  	<tr id="tr_${entry.sid}">
		                           		 <td>${s.index+1}</td> 
		                           		 <td>${entry.sid}</td> 
		                           		 <td>${entry.imei}</td> 
		                           		 <td>${entry.towerCraneStatus.towerCrane.babh}</td> 
		                           		 <td>${entry.point.name}</td> 
		                           		 <td>${entry.deviceModel.model}</td> 
		                            	 <td title='<fmt:formatDate value="${entry.joinDate}" pattern="hh:mm:ss "/>'><fmt:formatDate value="${entry.joinDate}" pattern="yyyy-MM-dd"/></td> 
		                            	 <td title='<fmt:formatDate value="${entry.activeDate}" pattern="hh:mm:ss "/>'><fmt:formatDate value="${entry.activeDate}" pattern="yyyy-MM-dd"/></td> 
		                            	 <td title='<fmt:formatDate value="${entry.towerCraneDevice.regTime}" pattern="hh:mm:ss "/>'><fmt:formatDate value="${entry.towerCraneDevice.regTime}" pattern="yyyy-MM-dd"/></td> 
		                           		 <td>
		                           		 	<a href="javascript:void(0)" class="act-atag"  data-toggle="modal" data-target="#myModal-active"  data-whatever="${entry.sid}">注册</a>
		                           		 </td> 
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
	<!--/PAGE -->
	
	<!-- 模态框 -->
	<%@ include file="/WEB-INF/page/core/modal/modal-active.jsp" %>
	
	<!-- JAVASCRIPTS -->
	<%@ include file="/WEB-INF/page/common/pack-js.jsp" %>
	<!-- /JAVASCRIPTS -->
	<script  src="${pageContext.request.contextPath}/resource/typeahead/handlebars.js"></script>
	<script  src="${pageContext.request.contextPath}/resource/typeahead/typeahead.bundle.js"></script>
	<script  src="${pageContext.request.contextPath}/resource/js/si.js"></script>

</body>
</html>