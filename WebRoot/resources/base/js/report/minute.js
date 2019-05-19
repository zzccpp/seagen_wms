var date_start;
var date_end;
var type;
var layer;

//var hidehtml ;
//var cookiename = 'sortminute';		
$(function() {
//	var gridmonth = formatDateTime("yyyy-MM", new Date);
//	$('#griddate').val(gridmonth);
	
    var type_options = [{text : "时间段", value : "0"},
                        {text : "按天", value : "1"}];   
    var layer_options = [{text : "全部", value : "0"},
                         {text : "第一层", value : "1"},
                         {text : "第二层", value : "2"}];   
    $("#type").combobox({
   		valueField: 'value',
           textField: 'text', 
           data : type_options,
           value:'0',
           panelHeight:140,
   		onChange: function (n,o) {
   			type = n;
   			function_Type(n);
   		}
   	});
    $("#layer_id").combobox({
   		valueField: 'value',
           textField: 'text', 
           data : layer_options,
           value:'0',
           panelHeight:140,
   		onChange: function (n,o) {
   			layer = n;
   		}
   	});
   //判断当前时间，若当前时间过了02:00，则默认查询当前时间之前两个小时的分钟量统计数据，否则查询00:00到当前时间的分钟量数据；
    var nowDate = new Date;
    var gridmonth_end = formatDateTime("yyyy-MM-dd hh:mm", nowDate);
    var gridmonth_start;
    if(nowDate.getHours() >= 2){
   	    nowDate = nowDate.setMinutes(nowDate.getMinutes()-29);
    	gridmonth_start = formatDateTime("yyyy-MM-dd hh:mm",new Date(parseInt(nowDate)));
    } else {
   	 	gridmonth_start = formatDateTime("yyyy-MM-dd 00:00", nowDate);
    }
    $('#griddate_start').val(gridmonth_start);
    $('#griddate_end').val(gridmonth_end);
   	date_start = gridmonth_start;
   	date_end = gridmonth_end;
       
   	type = $('#type').combobox('getValue');
   	layer = $('#layer_id').combobox('getValue');
	
	/*var isMore = getCookie(cookiename);
	if(isMore == '1'){
		hidehtml = '<span class="l-btn-left"><span class="l-btn-text">精简</span></span>';
		$('#hideStatus').html(hidehtml);
	}*/
	var more_type_options = [{text : "精简", value : "0"},{text : "详细", value : "1"}];   
    $("#more_type").combobox({
		valueField: 'value',
        textField: 'text', 
        data : more_type_options,
        value:'0',
        panelHeight:80
	});
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
		remoteSort : false,
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
	
	showprogress();
	var loaddatasuccess = function(datalist) {
		closeprogress();
		griddata = datalist;
		if (typeof griddata.frozenColumns == "undefined") {
			parent.$.messager.alert("提示", "数据加载异常或无数据");
			return;
		}
		// 格式化字符串成函数
		format_columns_formatter(griddata.frozenColumns);
		format_columns_formatter(griddata.columns);	
		$("#dataList").datagrid({
			frozenColumns : datalist.frozenColumns,
			columns : datalist.columns,
			data : datalist.data
		});
		$('#dataList').datagrid('reloadFooter', [ datalist.footer ]);
	};
	//var isMore = getCookie(cookiename);
	getReportTime(type);
	date_start = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date(Date.parse(date_start)));
	if(type != 0){
		date_end = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date(Date.parse(date_start)));
	} else {
		date_end = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date(Date.parse(date_end)));
		if(new Date(Date.parse(date_end)) - new Date(Date.parse(date_start)) <= 0) {
			closeprogress();
			parent.$.messager.alert("提示", "查询时间段:截止时间应该大于开始时间");
			return;
		}
		if(new Date(Date.parse(date_end)) - new Date(Date.parse(date_start)) > 120*60*1000){
			closeprogress();
			parent.$.messager.alert("提示", "查询时间段最大间隔为：2个小时");
			return;
		}
	}
	
	layer = $('#layer_id').combobox('getValue');
	var isMore = $('#more_type').combobox('getValue');
	gridday = $('#griddate_start_day').val();
	var url = 'reportMinute.do';
	var data = "id=806&date=" + date_start+"&end_date=" + date_end + "&type=" + type + "&layer=" + layer + '&more=' + (isMore == null? 0 : parseInt(isMore, 10)) + "&timestamp=" + tempPara();
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
}
*/
//Excel数据导出
function excelExport(){
	//var isMore = getCookie(cookiename);
	var isMore = $('#more_type').combobox('getValue');
	
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
	layer = $('#layer_id').combobox('getValue');
	
	var data = "id=806&date=" + date_start+"&end_date=" + date_end + "&type=" + type + "&layer=" + layer + '&more=' + (isMore == null? 0 : parseInt(isMore, 10)) + "&timestamp=" + tempPara();
	
	$.messager.confirm('提示',str,function(r){
		 if (r){
			 var url="exportMinute.do?"+data;
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
}


//报表中：分类统计时页面显示与隐藏设置
function function_Type(value){
	var no_time;
	if(value == 0){
		date_start = $('#griddate_start').val();
		date_end = $('#griddate_end').val();
		document.getElementById("td_times").style.display = "";
		document.getElementById("td_day").style.display = "none";
	}
	if(value == 1){
		no_time = formatDateTime("yyyy-MM-dd", new Date);
		$('#griddate_start_day').val(no_time);
		date_start = no_time;
		document.getElementById("td_times").style.display = "none";
		document.getElementById("td_day").style.display = "";
	}
}
