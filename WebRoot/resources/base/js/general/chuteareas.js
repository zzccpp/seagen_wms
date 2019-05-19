$(document).ready(function() {
	$("#dg_chuteareas").datagrid({
		url : 'getChuteAreas.do',// 请求地址
		fitColumns : true,
		loadMsg : '数据加载中请稍后……',
		rownumbers : true,
		nowrap : false,
		striped : true,
		idField : 'fRecno',
		fitColumns : true,
		singleSelect : true,
		fit : true,
		columns : [ [ {
			field : 'areaName',
			title : '区域名称',
			width : 130,
			align : 'center'
		},

		{
			field : 'chuteNumList',
			title : '附属格口号',
			width : 360
		}, {
			field : 'reMark',
			title : '备注',
			width : 150,
			align : 'center'
		}, {
			field : 'createTime',
			title : '创建时间',
			width : 150,
			align : 'center'
		}, {
			field : 'fRecno',
			title : '操作',
			width : 160,
			hidden : 'true',
			align : 'center',
			formatter : function(value, rowData, index) {
				/*var str = "<span class='icon-edit' style='padding-left:40px;'><a href='javascript:update(\""
						+ index
						+ "\")'>修改</a></span>"
						+ "<span class='icon-remove' style='padding-left:45px;margin-right:10px;'><a href='javascript:delChuteArea(\""
						+ index
						+ "\")'>删除</a></span>";
				return str;*/
				return "";
			}
		} ] ],
		toolbar : [ {
			text : '添加区域',
			iconCls : 'icon-add',
			handler : function() {
				// 弹出添加面板
				$('#add_chuteareas').window({
					title : "添加区域"
				});
				$('#dialogtitle').html("添加区域");
				$("#submit").attr("href", "javascript:submitAdd()");
				$("input[name='areaName']").val("");
				$("input[name='fRecno']").val("-1");
				$('#chuteNumList').combogrid('setValues', '');
				$("textarea[name='reMark']").val("");
				$('#check_result').html("");
				downdata("");
				openWindow("add_chuteareas");
			}
		}, '-', {
			text : '修改区域',
			iconCls : 'icon-edit',
			handler : function() {
				update();
			}
		}, '-', {
			text : '删除区域',
			iconCls : 'icon-remove',
			handler : function() {
				delChuteArea();
			}
		} ],
	});
});

var model = {
	/** 格口个数 */
	chute_num : 0,
	chute_used : ""
};
function downdata(args) {
	// showprogress();
	var loaddataerr = function(XMLHttpRequest, textStatus, errorThrown) {
	};
	var loaddatasuccess = function(data) {
		// closeprogress();
		if (typeof data.chute_num == "undefined")
			return;
		try {
			model = data;

			// 初使化值
			var chuteGridList = [];
			$('#chuteNumList').combogrid('grid').datagrid({
				data : chuteGridList
			});

			for ( var i = 1; i <= model.chute_num; i++) {

				if (args == "") {
					if (("," + model.chute_used + ",").indexOf("," + i + ",") >= 0) {
						continue;
					}
				} else {
					if (!(("," + args + ",").indexOf("," + i + ",") >= 0)) {

						if (("," + model.chute_used + ",").indexOf("," + i
								+ ",") >= 0) {
							continue;
						}
					}
				}

				chuteGridList.push({
					id : i,
					value : (padNumToStr(i, 3, '0') + '格口')
				});

			}
			$('#chuteNumList').combogrid('grid').datagrid({
				data : chuteGridList
			});

		} catch (e) {
		}
	};
	var url = 'getChuteNum.do';
	var data = "timestamp=" + tempPara();
	ajaxpost(url, data, loaddatasuccess, loaddataerr);
}

function submitAdd() {
	// 添加区域
	var res = $('#check_result').html();
	var areaName = $("#areaName").val();
	var reMark = $("#reMark").val();
	var chuteNumList = $('#chuteNumList').combogrid('getValues');

	if (($.trim(areaName) == "") || (areaName.length > 10)) {
		$.messager.alert('提示', "区域名称长度1-10之间");
		return;
	}
	if (res == "格式错误!") {
		$.messager.alert('提示', "区域名称格式错误！");
		return;
	}
	if (res != "通过" && $.trim(res) != "") {
		$.messager.alert('提示', "区域名称已被使用");
		return;
	}

	if ($.trim(chuteNumList) == "") {
		$.messager.alert('提示', "请选择格口");
		return;
	}
	var postdata = function() {

		var data = "area_name=" + areaName + "&chute_num_list=" + chuteNumList
				+ "&re_mark=" + reMark + "&timestamp=" + tempPara();

		console.log("data:" + data);

		var url = 'addChuteArea.do';
		var successfun = function(data) {
			var resdata = null;
			var json = null;
			try {
				resdata = (typeof data == "undefined") ? null : data;
				json = eval(resdata);
				if (json.result == 0) {
					parent.$.messager.alert("提示", json.message);
					closeWindow("add_chuteareas");
					$('#dg_chuteareas').datagrid('reload');
				} else {
					parent.$.messager.alert("提示", json.message);
				}
			} catch (e) {
				parent.$.messager.alert("提示", "返回数据解析错误，请重试！");
			}
		};
		ajaxpost(url, data, successfun);
	};
	postdata();

}

function submitUpdate() {
	// 修改区域
	var res = $('#check_result').html();
	var fRecno = $("#fRecno").val();
	var areaName = $("#areaName").val();
	var reMark = $("#reMark").val();
	var chuteNumList = $('#chuteNumList').combogrid('getValues');

	if (($.trim(areaName) == "") || (areaName.length > 10)) {
		$.messager.alert('提示', "区域名称长度1-10之间");
		return;
	}
	if (res == "格式错误!") {
		$.messager.alert('提示', "区域名称格式错误！");
		return;
	}
	if (res != "通过" && res != "") {
		$.messager.alert('提示', "区域名称已被使用");
		return;
	}

	if ($.trim(chuteNumList) == "") {
		$.messager.alert('提示', "请选择格口");
		return;
	}
	var postdata = function() {

		var data = "f_recno=" + fRecno + "&area_name=" + areaName
				+ "&chute_num_list=" + chuteNumList + "&re_mark=" + reMark
				+ "&timestamp=" + tempPara();

		console.log("data:" + data);

		var url = 'updateChuteArea.do';
		var successfun = function(data) {
			var resdata = null;
			var json = null;
			try {
				resdata = (typeof data == "undefined") ? null : data;
				json = eval(resdata);
				if (json.result == 0) {
					parent.$.messager.alert("提示", json.message);
					closeWindow("add_chuteareas");
					$('#dg_chuteareas').datagrid('reload');
				} else {
					parent.$.messager.alert("提示", json.message);
				}
			} catch (e) {
				parent.$.messager.alert("提示", "返回数据解析错误，请重试！");
			}
		};
		ajaxpost(url, data, successfun);
	};
	postdata();
}
// 修改区域
function update() {

	//var row = $('#dg_chuteareas').datagrid('getData').rows[index];
	var row = $('#dg_chuteareas').datagrid('getSelected');
	if (!row) {
		$.messager.alert('提示', "请选择一个区域进行修改");
		return;
	}
	downdata(row.chuteNumList);
	$('#add_chuteareas').window({
		title : "修改区域"
	});
	$('#dialogtitle').html("修改区域");
	$("#submit").attr("href", "javascript:submitUpdate()");
	$("input[name='fRecno']").val(row.fRecno);
	$("input[name='areaName']").val(row.areaName);
	$('#chuteNumList').combogrid('setValues', row.chuteNumList.split(","));
	$("textarea[name='reMark']").val(row.reMark);
	$('#check_result').html("");

	openWindow("add_chuteareas");
}

//删除区域信息
function delChuteArea() {
	var row = $('#dg_chuteareas').datagrid('getSelected');
	if (!row)
		$.messager.alert('提示', "请选择一个区域进行删除");
	else
		$.messager.confirm('删除区域', '确定删除吗?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : "delChuteArea.do",
					data : "f_recno=" + row.fRecno,
					success : function(data) {
						if (data.result == 0) {
							$('#dg_chuteareas').datagrid('reload');
						} else {
							$.messager.alert('提示', data.message);
						}
					}
				});
			}
		});

}
//检查区域名称是否重复
function checkAreaName() {
	var areaName = $("input[name='areaName']").val();
	if (($.trim(areaName) == "") || (areaName.length > 10)) {
		$('#check_result').css("color", "red");
		$('#check_result').html("长度1-10之间");
		return;
	}
	var reg = /^[a-zA-Z0-9]{1,9}$/;
	if (!reg.test(areaName)) {
		$('#check_result').css("color", "red");
		$('#check_result').html("格式错误!字母+数字");
		return;
	}
	var fRecno = $("input[name='fRecno']").val();
	$.ajax({
		type : "POST",
		url : "checkChuteArea.do",
		data : "area_name=" + areaName + "&f_recno=" + fRecno,
		success : function(data) {
			if (data.result == 0) {
				$('#check_result').css("color", "red");
			} else {
				$('#check_result').css("color", "green");
			}
			$('#check_result').html(data.message);
		}
	});
}
//关闭添加窗口
function addCancel() {
	closeWindow("add_chuteareas");
}