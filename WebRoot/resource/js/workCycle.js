function change() {
	var sel = document.getElementById("sel");
	var selValue = sel.value;
	if (selValue == 7) {
		document.getElementById("date").style.display = "";
	}
	if (selValue != 7) {
		document.getElementById("date").style.display = "none";
	}
}

// ***************使用Layer层加载页面相关代码*****************/
var width = '900px';
if (document.body.clientWidth < 900) {
	width = (document.body.clientWidth - 10) + "px";
}
// 统计
function loadWeigthCount(sid) {
	layer.open({
		type : 2,
		title : '工作循环数据统计',
		skin : 'layui-layer-molv',// 皮肤
		scrollbar : true, // 屏蔽浏览器滚动条
		maxmin : true, // 开启最大化最小化按钮
		area : [ width, '500px' ],
		content : 'api/count/weight/' + sid,
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