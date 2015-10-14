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
											<a href="#">运行监控</a>
										</li>
										<li>俯视图</li>
									</ul>
									<!-- /BREADCRUMBS -->
									<div class="clearfix">
										<h3 class="content-title pull-left">运行监控数据分析</h3>
									</div>
									<div class="description">俯视图和实时运行图</div>
								</div>
							</div>
						</div>
						<!-- /PAGE HEADER -->
						
						<!-- 内容区 -->
						<div class="row mycontent">
							<form id="pageList" action="${pageContext.request.contextPath}/manage/run/point/list" method="post">
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
				      		<c:forEach items="${pageView.records }" var="entry">
				      		<div class="col-md-12 box-container ui-sortable">
								<!-- BOX WITH TOOLBOX-->
								<div class="box border blue" id="box_${entry.id }">
									<div class="box-title">
										<h4><i class="fa fa-flag"></i>${entry.name }</h4>
										<div class="tools">
											<a href="javascript:loadDevice('${entry.id}');" title="俯视图">
												<i class="fa fa-eye" ></i>
											</a>
											<a href="javascript:loadDevice('${entry.id}');" >
												<i class="fa fa-refresh"></i>
											</a>
											<a href="javascript:;" class="collapse">
												<i class="fa fa-chevron-up"></i>
											</a>
											<a href="javascript:;" class="remove">
												<i class="fa fa-times"></i>
											</a>
										</div>
									</div>
									<div class="box-body" id="data_${entry.id}" >
										
									</div>
								</div>
								<!-- /BOX WITH TOOLBOX-->
							</div>
							</c:forEach>
				      		
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
	
	<script type="text/javascript">
		$(function() {
			var ids = $(".box-body");
			for(var i=0;i<ids.length;i++){
				var pid = ids[i].id.split("_")[1];
				console.log(pid);
				loadDevice(pid);
			}
			
		});
		
		function loadDevice(pid){
			$("#box_"+pid).block({
                message: '<img src="resource/template/img/loaders/12.gif" align="absmiddle">',
                css: {
                    border: 'none',
                    padding: '2px',
                    backgroundColor: 'none'
                },
                overlayCSS: {
                    backgroundColor: '#000',
                    opacity: 0.05,
                    cursor: 'wait'
                }
            });
			
			$.post("manage/run/point/device", {
				pid : pid,
			}, function(data) {
				setTimeout(function() {$("#box_"+pid).unblock();}, 200); 
				$("#data_"+pid).html(data);
			});
		}
		
		//运行图
		function loadRun(sid){
		 layer.open({
	            type: 2,
	            title: '塔机实时运行图',
	            shadeClose: true,
	            shade: false,
	            scrollbar: false, //屏蔽浏览器滚动条
	            maxmin: true, //开启最大化最小化按钮
	            //area: ['800px', '500px'],
	            content: 'http://localhost:8080/dcp/manage/point/list?sid='+sid
	        });
		}
	</script>
</body>
</html>