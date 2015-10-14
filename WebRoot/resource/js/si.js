$(".act-atag").click(function() {
	$("#myModal-active").modal({
		backdrop : false,// 点击空白处模态框消失
	});
});

$('#myModal-active').on('show.bs.modal', function(event) {
	var element_a = $(event.relatedTarget); // Button that triggered the modal
	var sid = element_a.data('whatever');
	if (sid>0) { 
		$("#sid").val(sid);
		var t = new Date().getTime();
		// 对页面数据进行还原
		$.post("manage/device/query/find/" + sid, {
			t : t,
		}, function(data) {
			console.log(data);
			var jsonObj =eval('(' + data + ')');  
			$("#pid").val(jsonObj.pid);
			$("#pname").val(jsonObj.pname);
			$("#enable").val(jsonObj.enable);
			$("#mid").val(jsonObj.mid);
			$("#imei").val(jsonObj.imeiASCII+"("+jsonObj.imei+")");
			$("#latlng").val(jsonObj.imeiASCII+"("+jsonObj.imei+")");
			console.log("~~~"+jsonObj.sid);
			//alert(obj.sid);
			//console.log(obj.sid);
		});
	}
});
// 自动补全
$(function() {
	// 俯视点数据自动补全
	var pointData = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('label'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : 'manage/point/query/s',
		remote : {
			url : 'manage/point/query/ajax/%QUERY.json',
			wildcard : '%QUERY'
		}
	});

	$('.pname')
			.typeahead(
					null,
					{
						name : 'point-Data',
						display : 'label',
						source : pointData,
						templates : {
							empty : [ '<div class="empty-message">',
									'您的输入未能匹配到俯视点', '</div>' ].join('\n'),
							suggestion : Handlebars
									.compile('<div><strong>{{label}}</strong>({{address}})</div>')
						},
						classNames : {
							input : 'Typeahead-input',
							hint : 'Typeahead-hint',
							selectable : 'Typeahead-selectable'
						}
					});

	$('.pname').bind('typeahead:select', function(ev, suggestion) {
		$("#pid").val(suggestion.id);
		// console.log('Selection: ' + suggestion.id);
		console.log('pid: ' + $("#pid").val());
	});

	// 使用备案编号数据自动补全
	var usenumberData = new Bloodhound({
		datumTokenizer : Bloodhound.tokenizers.obj.whitespace('label'),
		queryTokenizer : Bloodhound.tokenizers.whitespace,
		prefetch : 'manage/device/usenumber/query/s',
		remote : {
			url : 'manage/device/usenumber/query/ajax/%QUERY.json',
			wildcard : '%QUERY'
		}
	});

	$('.funum')
			.typeahead(
					{
						minLength : 4,// 字符长度>=5时才进行数据查询匹配
						hint : true, // 启用提示
					},
					{
						name : 'usenumber-Data',
						display : 'label',
						source : usenumberData,
						limit:5,
						templates : {
							empty : [ '<div class="empty-message">',
									'您的输入未能匹配使用备案编号', '</div>' ].join('\n'),
							suggestion : Handlebars
									.compile('<div style="border-bottom:1px solid blue;"><span style="color:green;">{{label}}</span><br>{{project}}</div>')
						},
						classNames : {
							input : 'Typeahead-input',
							hint : 'Typeahead-hint',
							selectable : 'Typeahead-selectable'
						}
					});

	$('.funum').bind('typeahead:select', function(ev, suggestion) {
		$("#fuid").val(suggestion.id);
		$("#futext").html(suggestion.project);
		// console.log('Selection: ' + suggestion.id);
		console.log('fuid: ' + $("#fuid").val());
	});
});

function act_save() {
	// $("#act-save").addClass("disabled");// 禁用保存按钮
	var sid = $("#sid").val();
	var mid = $("#mid").val();
	var pid = $("#pid").val();
	var fuid = $("#fuid").val();
	var lnglat = $("#lnglat").val();
	var scenenum = $("#scenenum").val();
	var enable = $("#enable").val();
	$.post("manage/device/save/data", {
		sid : sid,
		mid : mid,
		pid : pid,
		fuid : fuid,
		lnglat : lnglat,
		scenenum : scenenum,
		enable : enable
	}, function(data) {
		alert(data);
	});
}
