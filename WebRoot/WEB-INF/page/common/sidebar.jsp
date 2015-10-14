<%@ page language="java" pageEncoding="UTF-8"%>
<!-- SIDEBAR -->
<div id="sidebar" class="sidebar">
	<div class="sidebar-menu nav-collapse">
		<div class="divide-20"></div>
		<!-- SEARCH BAR -->
		<!-- /SEARCH BAR -->
		
		<!-- SIDEBAR MENU -->
		<ul>
			<li >
				<a href="index.html">
					<i class="fa fa-tachometer fa-fw"></i> <span class="menu-text">Dashboard</span>
				</a>					
			</li>
			<li class='has-sub ${(m>10&&m<20)?"active":"" }'><a class="" href="${pageContext.request.contextPath}/manage/run/point/list"><i class="fa fa-desktop fa-fw"></i> <span class="menu-text">运行监控</span></a></li>
			<li class="has-sub">
				<a href="javascript:;" class="">
				<i class="fa fa-table fa-fw"></i> <span class="menu-text">分析统计</span>
				<span class="arrow"></span>
				</a>
				<ul class="sub">
					<li><a class="" href="simple_table.html"><span class="sub-menu-text">工作循环</span></a></li>
					<li><a class="" href="dynamic_tables.html"><span class="sub-menu-text">报警信息</span></a></li>
					<li><a class="" href="jqgrid_plugin.html"><span class="sub-menu-text">违章信息</span></a></li>
				</ul>
			</li>
			<li class="has-sub">
				<a href="javascript:;" class="">
				<i class="fa fa-pencil-square-o fa-fw"></i> <span class="menu-text">维护保养</span>
				<span class="arrow"></span>
				</a>
				<ul class="sub">
					<li><a class="" href="forms.html"><span class="sub-menu-text">故障处理</span></a></li>
					<li><a class="" href="wizards_validations.html"><span class="sub-menu-text">保养管理</span></a></li>
				</ul>
			</li>
			<li><a class="" href="inbox.html"><i class="fa fa-map-marker fa-fw"></i> <span class="menu-text">电子地图</span></a></li>
			
			<li class='has-sub ${(m>50&&m<60)?"active":"" }' >
				<a href="javascript:;" class="">
				<i class="fa fa-th-large fa-fw"></i> <span class="menu-text">设备管理</span>
				<span class="arrow"></span>
				</a>
				<ul class="sub">
					<li  class='${m==51?"current":"" }'><a class="" href="${pageContext.request.contextPath}/manage/device/model/list"><span class="sub-menu-text">设备型号管理</span></a></li>
					<li  class='${m==52?"current":"" }'><a class="" href="${pageContext.request.contextPath}/manage/device/list"><span class="sub-menu-text">设备注册</span></a></li>
				</ul>
			</li>
			<li class='has-sub ${(m>60&&m<70)?"active":"" }' >
				<a href="javascript:;" class="">
				<i class="fa fa-briefcase fa-fw"></i> <span class="menu-text">系统管理</span>
				<span class="arrow open"></span>
				<span class="selected"></span>
				</a>
				<ul class="sub">
					<li  class='${m==61?"current":"" }'><a class="" href="${pageContext.request.contextPath}/manage/point/list"><span class="sub-menu-text">俯视点管理</span></a></li>
					<li><a class="" href="email_templates.html"><span class="sub-menu-text">帐户管理</span></a></li>
					<li><a class="" href="email_templates.html"><span class="sub-menu-text">参数设置</span></a></li>
				</ul>
			</li>
		</ul>
		<!-- /SIDEBAR MENU -->
	</div>
</div>
<!-- /SIDEBAR -->