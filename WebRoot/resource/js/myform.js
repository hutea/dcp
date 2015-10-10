//跳转分页
function topage(page) {
	var form = document.getElementById("pageList");
	form.page.value = page;
	form.submit();
}

// 跳转到指定页面
function go(totalPage) {
	var inputPageValue = document.getElementById("inputPage").value;
	if (inputPageValue > totalPage) {
		alert("超过最大页数: " + totalPage);
	} else if (inputPageValue < 1) {
		alert("页码数必须大于等于1");
	} else {
		var form = document.getElementById("pageList");
		form.page.value = inputPageValue;
		form.submit();
	}
}
// 设置页码为1
function confirmQuery() {
	var form = document.getElementById("pageList");
	form.page.value = 1;
	form.submit();
}

// 删除操作
function del(ids, url) {
	$.post(url, {
		ids : ids
	}, function(data) {
		if (data == "ERROR") {
			alert("网络数据异常，请稍后再试");
		} else {
			var dataObj=eval("("+data+")");//转换为json对象
			for(var i=0;i<dataObj.length;i++){  
				   alert(dataObj[i]);  
				   $("#tr_"+dataObj[i]).remove();
			}  
		}
	});
}
