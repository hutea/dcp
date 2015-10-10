<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>俯视点管理</title>
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
											<a href="index.html">Home</a>
										</li>
										<li>
											<a href="#">系统管理</a>
										</li>
										<li>俯视点管理</li>
									</ul>
									<!-- /BREADCRUMBS -->
									<div class="clearfix">
										<h3 class="content-title pull-left">俯视点管理</h3>
									</div>
									<div class="description">俯视点相关信息，用于辅助俯视图的显示。</div>
								</div>
							</div>
						</div>
						<!-- /PAGE HEADER -->
						
						<!-- 内容区 -->
						<div class="row mycontent">
							<form class="form-inline" action="${pageContext.request.contextPath}/manage/point/save" method="post">
		                         	<div class="form-group col-md-2">
						                <input type="text"  name="name" class="form-control"  placeholder="俯视点名称"  >
									</div>
		                         	<div class="form-group col-md-4">
		                         		<input type="text"  name="address"  class="form-control"  placeholder="俯视点详细地址"  >
									</div>
		                         	<div class="form-group col-md-6">
		                       			<input type="submit"  class="btn btn-primary"   value="保 存" >
									</div>
				      		</form>
				      		
							<form id="pageList" action="${pageContext.request.contextPath}/manage/point/list" method="post">
				      			<input type="hidden" name="page" value="${page}">
				      		 	<div class="form-group col-md-7" > 
	                         	</div>
				      		 	<div class="form-group col-md-3" > 
		                         	<input type="text"  name="queryContent" value="${queryContent}" class="form-control col-md-3"  placeholder="对名称，地址进行模糊查询"  >
	                         	</div>
				      		 	<div class="form-group col-md-2" > 
		                       		<input type="button"  class="btn btn-primary"   value="查 询" onclick="confirmQuery()"  >
	                         	</div>
				      		</form>
					      	<table class="table table-bordered table-striped mytable">
	                           <thead>
	                                <tr>
	                                    <th>#</th>
	                                    <th>名称</th>
	                                    <th>详细地址</th>
	                                    <th>创建日期</th>
	                                    <th>修改日期</th>
	                                    <th>操作</th>
	                                </tr>
	                           </thead>
	                           <tbody>
	                                  <c:forEach items="${pageView.records}" var="entry" varStatus="s">  
		                           	  	<tr id="tr_${entry.id}">
		                           		 <td>${s.index+1}</td> 
		                           		 <td>${entry.name}</td> 
		                           		 <td>${entry.address}</td> 
		                            	 <td title='<fmt:formatDate value="${entry.createDate}" pattern="hh:mm:ss "/>'><fmt:formatDate value="${entry.createDate}" pattern="yyyy-MM-dd"/></td> 
		                            	 <td title='<fmt:formatDate value="${entry.modifyDate}" pattern="hh:mm:ss "/>'><fmt:formatDate value="${entry.modifyDate}" pattern="yyyy-MM-dd"/></td> 
		                           		 <td>
		                           		 	<a href="javascript:del('${entry.id}','${pageContext.request.contextPath}/manage/point/delete/more')">删除</a>
		                           		 	<a href="${pageContext.request.contextPath}/manage/point/edit/${entry.id}">修改</a>
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