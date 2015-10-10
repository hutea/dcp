<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>俯视点信息修改</title>
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
										<li>俯视点信息修改</li>
									</ul>
									<!-- /BREADCRUMBS -->
									<div class="clearfix">
										<h3 class="content-title pull-left">俯视点信息修改</h3>
									</div>
									<div class="description"></div>
								</div>
							</div>
						</div>
						<!-- /PAGE HEADER -->
						<!-- 内容区 -->
						<div class="row mycontent">
							<form class="form-horizontal" style="padding: 0 30px;" action="${pageContext.request.contextPath}/manage/point/update" method="post">
		                    	<input type="hidden" name="id" value="${point.id}" />
		                    	<div class="form-group">
								    <label for="inputEmail3" class="col-sm-2 control-label">俯视点名称</label>
								    <div class="col-sm-6">
						                <input type="text"  name="name" value="${point.name }" class="form-control"  placeholder="俯视点名称"  >
								    </div>
								</div>
		                    	<div class="form-group">
								    <label for="inputEmail3" class="col-sm-2 control-label">俯视点详细地址</label>
								    <div class="col-sm-6">
		                         		<input type="text"  name="address" value="${point.address}"  class="form-control"  placeholder="俯视点详细地址"  >
								    </div>
								</div>
		                    	<div class="form-group">
								    <div class="col-sm-offset-2 col-sm-6">
		                       			<input type="submit"  class="btn btn-primary"   value="保 存" >
								    </div>
								</div>
				      		</form>
						</div>
						<!-- /内容区 -->
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