<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}
#r-result{height:100%;width:20%;float:left;}
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<title>添加多个标注点</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/bootstarp/jquery-2.1.1.min.js"></script>
<style type="text/css">
	#table{
		text-align: center;
		display: none;
	}
	#select{
		position:absolute;
		text-align: center;
		z-index:10;
		width: 1000px;
		opacity:0.8;
		background-image:url(${pageContext.request.contextPath}/images/bg_03.png);
		background-position:center;
		background-repeat:X-repeat;
	}
	#shang{
		display: none;
	}
</style>
</head>
<body>

<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
  	var falg = $("#falg").val();
  	if(falg == 1){
  		$("#table").show(300);
	    $("#shang").show(300);
	    $("#xia").hide(300);	
  	}
  $("#xia").click(function(){
    $("#table").show(300);
    $("#shang").show(300);
    $("#xia").hide(300);
  });
  $("#shang").click(function(){
    $("#table").hide(300);
    $("#xia").show(300);
    $("#shang").hide(300);
  });
  
 });
var map = new BMap.Map("allmap");
map.enableScrollWheelZoom();
var point = new BMap.Point(104.072091,30.663543);  // 创建中心点坐标：成都市
map.centerAndZoom(point, 14);
// 编写自定义函数,创建标注
function addMarker(point,infoWindow,myIcon){
  var marker = new BMap.Marker(point,{icon: myIcon});
  map.addOverlay(marker);
  marker.addEventListener("click", function(){          
   this.openInfoWindow(infoWindow);
  });
}

var json = eval(${json});
//var json = eval("("+${json}+")");
for(var i=0;i<json.length;i++){
	var mpoint = new BMap.Point(json[i].lng,json[i].lat);
	var infoWindow = new BMap.InfoWindow(json[i].content);  // 创建信息窗口对象
	var online = json[i].online
	var myIcon ;
	if(online==1){//在线图标
		 myIcon= new BMap.Icon("${pageContext.request.contextPath}/resource/image/online.png", new BMap.Size(32, 32), {    
		  // offset: new BMap.Size(10, 25),    
		  // imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
		});   
	}else{//离线图标
		myIcon= new BMap.Icon("${pageContext.request.contextPath}/resource/image/offline.png", new BMap.Size(32, 32), {    
		  // offset: new BMap.Size(10, 25),    
		  // imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
		});
	}
	addMarker(mpoint,infoWindow,myIcon);
}
</script>
