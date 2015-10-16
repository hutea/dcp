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
	<title>俯视图</title>
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
											<a href="javascript:void(0)">系统管理</a>
										</li>
										<li>系统参数</li>
									</ul>
									<!-- /BREADCRUMBS -->
									<div class="clearfix">
										<h3 class="content-title pull-left">系统参数设置</h3>
									</div>
								</div>
							</div>
						</div>
						<!-- /PAGE HEADER -->
						
						<!-- 内容区 -->
						<div class="row mycontent">
							<div class="col-md-10">
							<form class="form-horizontal" id="pageList" action="${pageContext.request.contextPath}/manage/config/param/update" method="post">
				      			<div class="form-group">
								    <label  class="col-sm-2 control-label">项目根路径</label>
								    <div class="col-sm-10">
								      <input type="text" class="form-control" name="basePath" value="${basePath}">
								    </div>
								</div>
				      			<div class="form-group">
								    <label  class="col-sm-2 control-label">市代码</label>
								    <div class="col-sm-10">
								      <input type="text" class="form-control" name="code" value="${code}">
								    </div>
								</div>
				      			<div class="form-group">
								    <label  class="col-sm-2 control-label">地图中心点</label>
								    <div class="col-sm-10">
								      <input type="text" class="form-control" name="centerPoint" value="${centerPoint}">
								    </div>
								</div>
				      			<div class="form-group">
								    <label  class="col-sm-2 control-label">SocketID</label>
								    <div class="col-sm-10">
								      <input type="text" class="form-control" name="socketID" value="${socketID}" readonly="readonly">
								    </div>
								</div>
								<div class="text-center">
									<input type="submit" class="btn btn-primary" value="更新" />
								</div>
				      		</form>
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