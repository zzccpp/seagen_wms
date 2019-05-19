$(function() {
	var event_type_options = [{text : "全部", value : "0"},
	                     {text : "线体运行", value : "1"},
	                     {text : "远程连接", value : "2"},
	                     {text : "小车", value : "3"},
	                     {text : "格口", value : "4"},
	                     {text : "龙门架", value : "5"},
	                     {text : "导入台", value : "6"}];
	$("#event_type").combobox({
		valueField : 'value',
		textField : 'text',
		data : event_type_options,
		value : '0',
		panelHeight : 80
	});
	var griddate_start = formatDateTime("yyyy-MM-dd 00:00:00", new Date);
	var griddate_end = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date);
	$('#griddate_start').val(griddate_start);
	$('#griddate_end').val(griddate_end);
	// 初使化 DataGrid组件与请求地址
	initDataGrid("dataList", "querySystemEvents.do", true);
	// 首次查询
	gridLoad();
});

var selsort = 0;
// 获取相关参数
function getParameter() {
	var event_type = $('#event_type').combobox('getValue');
	var parameter = {
		startTime : $('#griddate_start').val(),
		endTime : $('#griddate_end').val(),
		eventType: event_type,
		sort : selsort,
		timestamp : tempPara()
	};
	return parameter;
}

//
function checkboxOnclick(checkbox) {
	if (checkbox.checked)
		selsort = 1;
	else
		selsort = 0;
}

// 双击行事件
function dblclickrow(index, row) {
	return;
}

// dataGrid数据加载
function gridLoad() {
	gridExport(getParameter());
}
//Excel数据导出
function excelExport() {
	var date_start = $('#griddate_start').val();
	var date_end = $('#griddate_end').val();
	var event_type = $('#event_type').combobox('getValue');
	rows = dataGrid.datagrid('getPager').data("pagination").options.total;//每页的记录数（行数）
	$.messager.confirm('提示', '确定导出' + date_start + '到'+date_end+'的数据吗?', function(r) {
		if (r) {
			var url = "exportEvent.do?id=701&date=" + date_start+"&end_date=" + date_end + "&sort=" + selsort+ "&type=" + event_type +"&rows=" + rows + "&timestamp=" + tempPara();
			window.open(url);
		}
	});
}