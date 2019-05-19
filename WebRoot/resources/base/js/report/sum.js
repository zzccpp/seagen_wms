var date_start;
var date_end;
var type;
//var hidehtml;
//var cookiename = 'sortsum';
$(function() {
//	var gridmonth = formatDateTime("yyyy-MM", new Date);
//	$('#griddate').val(gridmonth);
	/*var isMore = getCookie(cookiename);
	if(isMore == '1'){
		hidehtml = '<span class="l-btn-left"><span class="l-btn-text">精简</span></span>';
		$('#hideStatus').html(hidehtml);
	}*/
    var type_options = [{text : "时间段", value : "0"},
                     {text : "按天", value : "1"},
                     {text : "按月", value : "2"},
                     {text : "按年", value : "3"}];   
    $("#type").combobox({
		valueField: 'value',
        textField: 'text', 
        data : type_options,
        value:'0',
        panelHeight:140,
		onChange: function (n,o) {
			function_Type(n);
		}
	});
    
    var more_type_options = [{text : "精简", value : "0"},{text : "详细", value : "1"}];   
       $("#more_type").combobox({
   		valueField: 'value',
           textField: 'text', 
           data : more_type_options,
           value:'0',
           panelHeight:80
   	});
	var gridmonth_start = formatDateTime("yyyy-MM-dd 00:00", new Date);
	var gridmonth_end = formatDateTime("yyyy-MM-dd hh:mm", new Date);
	
	$('#griddate_start').val(gridmonth_start);
	$('#griddate_end').val(gridmonth_end);
	date_start = gridmonth_start;
	date_end = gridmonth_end;
    
	type = $('#type').combobox('getValue');
	// 设置分页控件
	$('#dataList').datagrid({
		nowrap : false, // 是否会把数据显示在一行里
		striped : true, // 是否把行条纹化（即奇偶行使用不同背景色）
		collapsible : false, // 是否允许折叠
		border : true,// 是否有边框
		fit : true,// 是否自动拉伸填冲父框大小
		showFooter : true, // 是否显示页脚即所谓的汇总信息
		rownumbers : true,// 是否显示行号的列
		singleSelect : true,// 是否只允许选中一行
		fitColumns : false, // 是否自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
		autoRowHeight : false, // 是否自动行高
		pagination : false, // 是否在 datagrid 的底部显示分页栏
		// pageNumber : 1,// 初使化时显示第几页
		// pageSize : 30,// 每页显示的记录条数，默认为10
		// pageList : [ 30, 50, 75, 100 ],// 可以设置每页记录条数的列表
		// queryParams : null, // 请求的参数JSON
		toolbar : '#toolbar' // 查询工具栏
	// onClickRow : onClickRow
	});
	showgriddata();
});

function showgriddata() {
	type = $('#type').combobox('getValue');
	
	showprogress();
	var loaddatasuccess = function(datalist) {
		closeprogress();
		griddata = datalist;
		if (typeof griddata.frozenColumns == "undefined") {
			parent.$.messager.alert("提示", "数据加载异常或无数据");
			return;
		}
		$("#dataList").datagrid({
			frozenColumns : [ datalist.frozenColumns ],
			columns : [datalist.columns],
			data : datalist.data
		});
	};
	getReportTime(type);
	if(type != 0){
		date_start = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date(Date.parse(date_start)));
		date_end = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date(Date.parse(date_start)));
	} else {
		if(new Date(Date.parse(date_end)) - new Date(Date.parse(date_start)) <= 0) {
			closeprogress();
			parent.$.messager.alert("提示", "查询时间段:截止时间应该大于开始时间");
			return;
		}
	}
	//var isMore = getCookie(cookiename);
	var isMore = $('#more_type').combobox('getValue');
	var url = 'querySum.do';
	var data = "id=809&date=" + date_start+"&end_date=" + date_end + "&type=" + type + '&more=' + (isMore == null? 0 : parseInt(isMore, 10)) + "&timestamp=" + tempPara();
	ajaxpost(url, data, loaddatasuccess);
}

/*function setHideStatus(){
	var isMore = getCookie(cookiename);
	if(isMore == null){
		setCookie(cookiename,'1');
		hidehtml = '<span class="l-btn-left "><span class="l-btn-text">精简</span></span>';
		$("#hideStatus").html(hidehtml);
	}
	else if(isMore == '0'){
		setCookie(cookiename,'1');
		hidehtml = '<span class="l-btn-left "><span class="l-btn-text">精简</span></span>';
		$("#hideStatus").html(hidehtml);
	}else{
		setCookie(cookiename,'0');
		hidehtml = '<span class="l-btn-left "><span class="l-btn-text">详细</span></span>';
		$("#hideStatus").html(hidehtml);
	}
	showgriddata();
}*/
//Excel数据导出
function excelExport(){
	var type = $('#type').combobox('getValue');
	var str = "";
	if(type == 0){
		str	= '确定导出从' + date_start + ' 到 '+date_end + '的数据吗?';
		if(new Date(Date.parse(date_end)) - new Date(Date.parse(date_start)) <= 0) {
			closeprogress();
			parent.$.messager.alert("提示", "查询时间段:截止时间应该大于开始时间");
			return;
		}
	}
	if(type == 1){
		str	= '确定导出'+date_start.substring(0,10)+'的数据吗?';
	}
	if(type == 2){
		str	= '确定导出'+date_start.substring(0,7)+'月份数据吗?';
	}
	if(type == 3){
		str	= '确定导出'+date_start.substring(0,4)+'年数据吗?';
	}
	//var isMore = getCookie(cookiename);
	var isMore = $('#more_type').combobox('getValue');
	var data = "id=809&date=" + date_start+"&end_date=" + date_end + "&type=" + type + '&more=' + (isMore == null? 0 : parseInt(isMore, 10)) + "&timestamp=" + tempPara();
	
	$.messager.confirm('提示',str,function(r){
		 if (r){
			 var url="exportSum.do?"+data;
			 window.open(url);
		 }
	});
}

function getReportTime(type){
	if(type == 0){
		date_start = $('#griddate_start').val();
		date_end = $('#griddate_end').val();
	}
	if(type == 1){
		date_start = $('#griddate_start_day').val();
	}
	if(type == 2){
		date_start = $('#griddate_start_month').val();
	}
	if(type == 3){
		date_start = $('#griddate_start_year').val();
	}
}

