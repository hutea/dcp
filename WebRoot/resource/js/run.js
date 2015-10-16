$(function() {
	var ids = $(".box-body");
	for (var i = 0; i < ids.length; i++) {
		var pid = ids[i].id.split("_")[1];
		console.log(pid);
		loadDevice(pid);
	}

});

function loadDevice(pid) {
	$("#box_" + pid)
			.block(
					{
						message : '<img src="resource/template/img/loaders/12.gif" align="absmiddle">',
						css : {
							border : 'none',
							padding : '2px',
							backgroundColor : 'none'
						},
						overlayCSS : {
							backgroundColor : '#000',
							opacity : 0.05,
							cursor : 'wait'
						}
					});

	$.post("manage/run/point/device", {
		pid : pid,
	}, function(data) {
		setTimeout(function() {
			$("#box_" + pid).unblock();
		}, 200);
		$("#data_" + pid).html(data);
	});
}

// ***************使用Layer层加载页面相关代码*****************/
var width = '900px';
if(document.body.clientWidth<900){
	width=(document.body.clientWidth-10)+"px";
}
// 俯视图
function loadOverlook(sid) {
	layer.open({
		type : 2,
		title : '俯视图',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '500px' ],
		content : 'api/data/overlook/' + sid,
	});
}

// 基本信息
function loadBaseInfo(sid) {
	layer.open({
		type : 2,
		title : '设备基本信息',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '500px'],
		content : 'api/data/baseInfo/' + sid,
	});
}
// 吊重
function loadWeight(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '塔机工作循环吊重数据',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '500px' ],
		content : 'api/data/weight/' + sid + "?queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}

// 报警
function loadAlarm(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '设备报警数据详情',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '500px' ],
		content : 'api/data/alarm/' + sid + "?queryAlarmType=0&queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}
// 违章
function loadVio(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '设备违章数据详情',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '500px' ],
		content : 'api/data/vio/' + sid + "?queryAlarmType=0&queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}

// 实时数据
function loadNewData(sid) {
	layer.open({
		type : 2,
		title : '设备运行实时数据',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '500px' ],
		content : 'api/data/newData/' + sid,
	});
}
// 历史数据
function loadHistoryData(sid) {
	var queryBeginDate = document.getElementById("queryBeginDate").value;
	var queryEndDate = document.getElementById("queryEndDate").value;
	layer.open({
		type : 2,
		title : '设备运行历史数据',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '500px' ],
		content : 'api/data/historyData/' + sid + "?queryBeginDate="
				+ queryBeginDate + "&queryEndDate=" + queryEndDate,
	});
}
// 运行图
function loadRunData(sid) {
	layer.open({
		type : 2,
		title : '设备运行图',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '500px' ],
		content : 'api/data/runData/' + sid,
	});
}
