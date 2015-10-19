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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/myform.js"></script>
  	<script	src="${pageContext.request.contextPath}/resource/bootstarp/jquery-2.1.1.min.js"></script>
  	<script	src="${pageContext.request.contextPath}/resource/bootstarp/bootstrap.min.js"></script>
  
  </head>
  	
  <body onload="heigthAuto()">
  	<div class="container-fluid">
  		<div class="row" style="padding: 8px;">
			<div class="col-md-12 form-inline">设备传感器实时数据
				<input type="checkbox" style="width: 30px;height: 30px;vertical-align: bottom;" id="checkboxID" <c:if test="${refresh==1}">checked="checked"</c:if> />
				<select name="refreshTime" id="selectID" class="form-control"> 
					<option value="1" <c:if test="${refreshTime==1}">selected="selected"</c:if> >1秒</option>
					<option value="2" <c:if test="${refreshTime==2}">selected="selected"</c:if> >2秒</option>
					<option value="3" <c:if test="${refreshTime==3}">selected="selected"</c:if> >3秒</option>
					<option value="4" <c:if test="${refreshTime==4}">selected="selected"</c:if> >4秒</option>
					<option value="5" <c:if test="${refreshTime==5}">selected="selected"</c:if> >5秒</option>
					<option value="10" <c:if test="${refreshTime==10}">selected="selected"</c:if> >10秒</option>
				</select><span style="font-size: 13px;color: #007cdc;"><strong>自动刷新</strong></span>
			</div>
  		</div>
		<div class="table-responsive">
		<table class="table table-striped table-bordered table-condensed">
	  		<tr>
	  			<th  colspan="18">» 设备传感器参数</th>
	  		</tr>
	  		<tr>
	  			<td colspan="3" width="20%">产权备案号</td>
	  			<td colspan="6" width="30%">${tower.propertyNumbers}</td>
	  			<td colspan="3" width="20%">采集时间</td>
	  			<td colspan="6" width="30%"><fmt:formatDate value="${tower.createTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
	  		</tr>
	  		<tr>
	  			<td colspan="3" width="20%">起重量(t)</td>
	  			<td colspan="6" width="30%">${tower.weight}</td>
	  			<td colspan="3" width="20%">小车变幅(m)</td>
	  			<td colspan="6" width="30%">${tower.width}</td>
	  		</tr>
	  		<tr>
	  			<td colspan="3" width="20%">回转角度(°)</td>
	  			<td colspan="6" width="30%">${tower.angle}</td>
	  			<td colspan="3" width="20%">吊钩高度(m)</td>
	  			<td colspan="6" width="30%">${tower.height}</td>
	  		</tr>
	  		<tr>
	  			<td colspan="2" width="13%">塔机现场编号</td>
	  			<td colspan="4" width="20%">${tower.onSiteNum}</td>
	  			<td colspan="2" width="13%">力距(%)</td>
	  			<td colspan="4" width="20%">${tower.torque}</td>
	  			<td colspan="2" width="13%">风速(m/s)</td>
	  			<td colspan="4" width="20%">${tower.wind}</td>
	  		</tr>
	  		<tr>
	  			<th colspan="18">» 设备基本数据</th>
	  		</tr>
	  		<tr>
	  			<td colspan="3" width="20%">前臂长度(m)</td>
	  			<td colspan="6" width="30%">${tower.forearm}</td>
	  			<td colspan="3" width="20%">后臂长度(m)</td>
	  			<td colspan="6" width="30%">${tower.backarm}</td>
	  		</tr>
	  		<tr>
	  			<td colspan="3" width="20%">塔臂高度(m)</td>
	  			<td colspan="6" width="30%">${tower.armHeight}</td>
	  			<td colspan="3" width="20%">塔帽高度(m)</td>
	  			<td colspan="6" width="30%">${tower.cap}</td>
	  		</tr>
	  		<tr>
	  			<td colspan="2" width="13%">坐标X(m)</td>
	  			<td colspan="4" width="20%">${tower.x}</td>
	  			<td colspan="2" width="13%">坐标Y(m)</td>
	  			<td colspan="4" width="20%">${tower.y}</td>
	  			<td colspan="2" width="13%">吊绳倍率(倍绳)</td>
	  			<td colspan="4" width="20%">${tower.multiple}</td>
	  		</tr>
	  		<tr>
	  			<th colspan="18">» 设备限位信息</th>
	  		</tr>
	  		<tr>
	  			<td colspan="3" width="20%">左限位(°)</td>
	  			<td colspan="6" width="30%">${tower.leftLimit}</td>
	  			<td colspan="3" width="20%">右限位(°)</td>
	  			<td colspan="6" width="30%">${tower.rightLimit}</td>
	  		</tr>
	  		<tr>
	  			<td colspan="3" width="20%">远限位(m)</td>
	  			<td colspan="6" width="30%">${tower.farLimit}</td>
	  			<td colspan="3" width="20%">近限位(m)</td>
	  			<td colspan="6" width="30%">${tower.nearLimit}</td>
	  		</tr>
	  		<tr>
	  			<td colspan="2" width="13%">起重量限位(t)</td>
	  			<td colspan="4" width="20%">${tower.weightLimit}</td>
	  			<td colspan="2" width="13%">力矩限位(%)</td>
	  			<td colspan="4" width="20%">${tower.torqueLimit}</td>
	  			<td colspan="2" width="13%">高限位(m)</td>
	  			<td colspan="4" width="20%">${tower.heightLimit}</td>
	  		</tr>
    	</table>
    	</div>
	</div>
  <script type="text/javascript">
	var t=1;
	function heigthAuto(){
		myrefresh();
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	    parent.layer.iframeAuto(index);
	}
	
	function myrefresh() { 
		var sel = document.getElementById("selectID");
		var refreshTime = sel.options[sel.selectedIndex].value;
		var isrefresh = document.getElementById("checkboxID").checked ;
		if(isrefresh && t>1){
			var url = window.location.href;
			if(url.indexOf("?") >= 0){
				url=url.substring(0,url.indexOf('?'))+"?refresh=1&refreshTime="+refreshTime;
			}else{
				url = url+"?refresh=1&refreshTime="+refreshTime;
			}
			window.location.href=convertURL(url);
		}
		t++;
		setTimeout('myrefresh()',refreshTime*1000); //指定1秒刷新一次 
	} 
	
	//给URL地址加上时间戳
	function convertURL(url) {
		// 获取时间戳
		var timstamp = (new Date()).valueOf();
		// 将时间戳信息拼接到url上
		if (url.indexOf("?") >= 0) {
			url = url + "&t=" + timstamp;
		} else {
			url = url + "?t=" + timstamp;
		}
		return url;
	}
  </script>
  </body>
</html>
