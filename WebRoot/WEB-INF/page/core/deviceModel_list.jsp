<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>监控设备型号信息管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="author" content="">
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
											<a href="#">Home</a>
										</li>
										<li>
											<a href="#">系统管理</a>
										</li>
										<li>监控设备型号信息管理</li>
									</ul>
									<!-- /BREADCRUMBS -->
									<div class="clearfix">
										<h3 class="content-title pull-left">监控设备型号信息管理</h3>
									</div>
									<div class="description"></div>
								</div>
							</div>
						</div>
						<!-- /PAGE HEADER -->
						
						<!-- 内容区 -->
						<div class="row mycontent">
							<form class="form-inline" action="${pageContext.request.contextPath}/manage/device/model/save" method="post">
		                         	<div class="form-group col-md-2">
						                <input type="text"  name="manufacturer" class="form-control"  placeholder="设备厂家"  >
									</div>
		                         	<div class="form-group col-md-2">
		                         		<input type="text"  name="model"  class="form-control"  placeholder="设备型号"  >
									</div>
		                         	<div class="form-group col-md-3">
		                         		<input type="text"  name="note"  class="form-control"  placeholder="型号描述"  >
									</div>
		                         	<div class="form-group col-md-5">
		                       			<input type="submit"  class="btn btn-primary"   value="保 存" >
									</div>
				      		</form>
				      		
							<form  id="pageList" action="${pageContext.request.contextPath}/manage/device/model/list" method="post">
	                         	<input type="hidden" name="page" value="${page}">
				      		 	<div class="form-group col-md-8" > 
	                         	</div>
				      		 	<div class="form-group col-md-3" > 
		                         	<input type="text"  name="queryContent" value="${queryContent}" class="form-control col-md-3"  placeholder="对厂家，型号进行模糊查询" >
	                         	</div>
				      		 	<div class="form-group col-md-1" > 
		                       		<input type="button"  class="btn btn-primary"   value="查 询" onclick="confirmQuery()"  >
	                         	</div>
				      		</form>
					      	<table class="table table-bordered table-striped mytable">
	                           <thead>
	                                <tr>
	                                    <th>#</th>
	                                    <th>设备厂家</th>
	                                    <th>设备型号</th>
	                                    <th>型号描述</th>
	                                    <th>操作</th>
	                                </tr>
	                           </thead>
	                           <tbody>
	                                  <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
		                           	  	<tr id="tr_${entry.id}">
		                           		 <td>${s.index+1}</td> 
		                           		 <td>${entry.manufacturer}</td> 
		                           		 <td>${entry.model}</td> 
		                           		 <td>${entry.note}</td> 
		                           		 <td>
		                           		 	<a href="javascript:del('${entry.id}','${pageContext.request.contextPath}/manage/device/model/delete/more')">删除</a>
		                           		 	<a href="${pageContext.request.contextPath}/manage/device/model/edit/${entry.id}">修改</a>
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
	<!-- JAVASCRIPTS -->
	<%@ include file="/WEB-INF/page/common/pack-js.jsp" %>
	<!-- /JAVASCRIPTS -->
</body>
</html>