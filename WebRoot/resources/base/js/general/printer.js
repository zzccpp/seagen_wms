$(document).ready(function() {
	$("#dg_printers").datagrid({
		url : 'getPrinters.do',// 请求地址
		fitColumns : true,
		loadMsg : '数据加载中请稍后……',
		rownumbers : true,
		nowrap : false,
		striped : true,
		idField : 'fRecno',
		singleSelect : true,
		fit : true,
		columns : [ [ {
			field : 'printerNum',
			title : '打印机编码',
			width : 130,
			align : 'center'
		}, {
			field : 'printerIp',
			title : '打印机IP',
			width : 130,
			align : 'center'
		}, {
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
		}] ],
		toolbar : [ {
			text : '添加打印机',
			iconCls : 'icon-add',
			handler : function() {
				// 弹出添加面板
				$('#add_printer').window({
					title : "添加打印机"
				});
				$('#dialogtitle').html("添加打印机");
				$("#submit").attr("href", "javascript:submitAdd()");
				$("input[name='fRecno']").val("-1");
				$("input[name='printerNum']").val("");
				$("input[name='printerIp']").val("");
				$("textarea[name='chuteNumList']").val("");
				$('#chuteNumList').combogrid('setValues', '');
				$("textarea[name='reMark']").val("");
				$('#checkip_result').html("");
				$('#checknum_result').html("");
				downdata("");
				openWindow("add_printer");
			}
		}, '-', {
			text : '修改打印机',
			iconCls : 'icon-edit',
			handler : function() {
				update();
			}
		}, '-', {
			text : '删除打印机',
			iconCls : 'icon-remove',
			handler : function() {
				delPrinter();
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
	var url = 'getPrinterChuteNum.do';
	var data = "timestamp=" + tempPara();
	ajaxpost(url, data, loaddatasuccess, loaddataerr);
}
function submitAdd() {
	// 添加区域
	var ipNum = $('#checknum_result').html();
	var printerNum = $("#printerNum").val();
	var printerIp = $("#printerIp").val();
	var reMark = $("#reMark").val();
	var chuteNumList = $('#chuteNumList').combogrid('getValues');

	if (($.trim(printerNum) == "") || (printerNum.length > 10)) {
		$.messager.alert('提示', "打印机编码长度1-10之间");
		return;
	}

	if (ipNum == "格式错误!") {
		$.messager.alert('提示', "打印机编码格式错误！");
		return;
	}
	if (ipNum != "通过" && $.trim(ipNum) != "") {
		$.messager.alert('提示', "打印机编码已被使用");
		return;
	}

	if (($.trim(printerIp) == "")) {
		$.messager.alert('提示', "IP地址不能为空！");
		return;
	}

	if ($.trim(chuteNumList) == "") {
		$.messager.alert('提示', "请选择格口");
		return;
	}
	var postdata = function() {

		var data = "printer_num=" + printerNum + "&printer_ip=" + printerIp
				+ "&chute_num_list=" + chuteNumList + "&re_mark=" + reMark
				+ "&timestamp=" + tempPara();

		console.log("data:" + data);

		var url = 'addPrinter.do';
		var successfun = function(data) {
			var resdata = null;
			var json = null;
			try {
				resdata = (typeof data == "undefined") ? null : data;
				json = eval(resdata);
				if (json.result == 0) {
					parent.$.messager.alert("提示", json.message);
					closeWindow("add_printer");
					$('#dg_printers').datagrid('reload');
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
	var ipNum = $('#checknum_result').html();
	var fRecno = $("#fRecno").val();
	var printerNum = $("#printerNum").val();
	var printerIp = $("#printerIp").val();
	var reMark = $("#reMark").val();
	var chuteNumList = $('#chuteNumList').combogrid('getValues');

	if (($.trim(printerNum) == "") || (printerNum.length > 10)) {
		$.messager.alert('提示', "打印机编码长度1-10之间");
		return;
	}

	if (ipNum == "格式错误!") {
		$.messager.alert('提示', "打印机编码格式错误！");
		return;
	}
	if (ipNum != "通过" && ipNum != "") {
		$.messager.alert('提示', "打印机编码已被使用");
		return;
	}
	if (($.trim(printerIp) == "")) {
		$.messager.alert('提示', "IP地址不能为空！");
		return;
	}
	if ($.trim(chuteNumList) == "") {
		$.messager.alert('提示', "请选择格口");
		return;
	}
	var postdata = function() {
		var data = "f_recno=" + fRecno + "&printer_num=" + printerNum
				+ "&printer_ip=" + printerIp + "&chute_num_list="
				+ chuteNumList + "&re_mark=" + reMark + "&timestamp="
				+ tempPara();

		console.log("data:" + data);

		var url = 'updatePrinter.do';
		var successfun = function(data) {
			var resdata = null;
			var json = null;
			try {
				resdata = (typeof data == "undefined") ? null : data;
				json = eval(resdata);
				if (json.result == 0) {
					parent.$.messager.alert("提示", json.message);
					closeWindow("add_printer");
					$('#dg_printers').datagrid('reload');
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
// 修改打印机
function update() {
	var row = $('#dg_printers').datagrid('getSelected');
	if (!row) {
		$.messager.alert('提示', "请选择一个打印机进行编辑");
		return;
	}
	downdata(row.chuteNumList);
	$('#add_printer').window({
		title : "修改打印机"
	});
	$('#dialogtitle').html("修改打印机");
	$("#submit").attr("href", "javascript:submitUpdate()");
	$("input[name='fRecno']").val(row.fRecno);
	$("input[name='printerNum']").val(row.printerNum);
	$("input[name='printerIp']").val(row.printerIp);
	$('#chuteNumList').combogrid('setValues', row.chuteNumList.split(","));
	$("textarea[name='reMark']").val(row.reMark);
	$('#checkip_result').html("");
	$('#checknum_result').html("");
	openWindow("add_printer");
}

// 删除打印信息
function delPrinter() {
	var row = $('#dg_printers').datagrid('getSelected');
	if (!row)
		$.messager.alert('提示', "请选择一个打印机进行删除");
	else
		$.messager.confirm('删除打印机', '确定删除吗?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : "delPrinter.do",
					data : "fRecno=" + row.fRecno,
					success : function(data) {
						if (data.result == 0) {
							$('#dg_printers').datagrid('reload');
						} else {
							$.messager.alert('提示', data.message);
						}
					}
				});
			}
		});
}

// 检查打印机编码是否重复
function checkPrinterNum() {
	var printerNum = $("input[name='printerNum']").val();
	if (($.trim(printerNum) == "") || (printerNum.length > 10)) {
		$('#checknum_result').css("color", "red");
		$('#checknum_result').html("长度1-10之间");
		return;
	}
	var reg = /^[a-zA-Z0-9]{1,9}$/;
	if (!reg.test(printerNum)) {
		$('#checknum_result').css("color", "red");
		$('#checknum_result').html("格式错误!");
		return;
	}
	var fRecno = $("input[name='fRecno']").val();
	$.ajax({
		type : "POST",
		url : "checkPrinterNum.do",
		data : "printerNum=" + printerNum + "&fRecno=" + fRecno,
		success : function(data) {
			if (data.result == 0) {
				$('#checknum_result').css("color", "red");
			} else {
				$('#checknum_result').css("color", "green");
			}
			$('#checknum_result').html(data.message);
		}
	});
}

// 关闭添加窗口
function addCancel() {
	closeWindow("add_printer");
}
