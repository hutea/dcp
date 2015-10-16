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
	<title>电子地图</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<title></title>
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
						<!-- /PAGE HEADER -->
						
						<!-- 内容区 -->
						<div class="row mycontent" style="margin-top: 8px;">
							<div style="border: 1px solid #dedede;">
							<iframe width="100%" height="500px" src="${pageContext.request.contextPath}/api/data/map" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
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
	<script	src="${pageContext.request.contextPath}/resource/js/run.js"></script>
	
<script type="text/javascript">

</script>
</body>
</html>