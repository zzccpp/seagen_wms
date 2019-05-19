var date_start;
var date_end;
var update_flag;

$(function() {
	
    var update_flag_options = [{text : "未上传", value : "0"},
                               {text : "已上传", value : "1"}];   
    $("#update_flag").combobox({
   		valueField: 'value',
           textField: 'text', 
           data : update_flag_options,
           value:'1',
           panelHeight:140,
   		onChange: function (n,o) {
   			update_flag = n;
   		}
   	});
    //判断当前时间，若当前时间过了02:00，则默认查询当前时间之前两个小时的分钟量统计数据，否则查询00:00到当前时间的分钟量数据；
    var nowDate = new Date;
    var gridmonth_end = formatDateTime("yyyy-MM-dd hh:mm", nowDate);
    var gridmonth_start;
    if(nowDate.getHours() > 1){
   	    nowDate = nowDate.setMinutes(nowDate.getMinutes()-60);
    	gridmonth_start = formatDateTime("yyyy-MM-dd hh:mm",new Date(parseInt(nowDate)));
    } else {
   	 	gridmonth_start = formatDateTime("yyyy-MM-dd 00:00", nowDate);
    }
    $('#griddate_start').val(gridmonth_start);
    $('#griddate_end').val(gridmonth_end);
   	date_start = gridmonth_start;
   	date_end = gridmonth_end; 
   	
   	update_flag = $('#update_flag').combobox('getValue');
	// 初使化 DataGrid组件与请求地址
	dataGrid = $("#dataList");
	dataGridUrl = "querySortdetail.do";

	// 设置分页控件
	dataGrid.datagrid({
		nowrap : false, // 是否会把数据显示在一行里
		striped : true, // 是否把行条纹化（即奇偶行使用不同背景色）
		collapsible : false, // 是否允许折叠
		border : false,// 是否有边框
		fit : true,// 是否自动拉伸填冲父框大小
		showFooter : true, // 是否显示页脚即所谓的汇总信息
		rownumbers : true,// 是否显示行号的列
		singleSelect : true,// 是否只允许选中一行
		fitColumns : false, // 是否自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
		autoRowHeight : false, // 是否自动行高
		pagination : true, // 是否在 datagrid 的底部显示分页栏
		pageNumber : 1,// 初使化时显示第几页
		pageSize : 200,// 每页显示的记录条数，默认为10
		pageList : [ 200, 500, 1000, 2000 ],// 可以设置每页记录条数的列表
		queryParams : getParameter(), // 请求的参数JSON
		toolbar : '#toolbar', // 查询工具栏
		// url : "",
		// onClickRow : onClickRow
		onDblClickRow : dblclickrow,
		frozenColumns : [ [ 
   		    {field : 'f_recno',title : '序号',width : 80,align:'center'}, 
		    {field : 'package_code',title : '包裹条号',width : 150,align:'center'} 
		] ],
		columns : [ [
		    {field : 'batch_id',title : '批次ID',width : 80,align:'center'}, 
		    {field : 'waybill_id',title : '追踪ID',width : 80,align:'center'}, 
		    {field : 'waybill_num',title : '分拣序号',width : 200,align:'center'},
		    {field : 'site_code',title : '目的地代码',width : 80,align:'center'},
		    {field : 'chute_id',title : '投递格口',width : 80,align:'center'},
		    {field : 'waybill_time',title : '运单生成时间',width : 150,formatter:formatTimeType,align:'center'},
		    {field : 'waybill_status',title : '运单状态',width : 80,formatter:formatWaybillStatus,align:'center'},
		    {field : 'package_weight',title : '运单重量(g)',width : 100,align:'center'},
	        {field : 'package_length',title : '运单长度(mm)',width : 100,align:'center'}, 
	        {field : 'package_width',title : '运单宽度(mm)',width : 100,align:'center'}, 
	        {field : 'package_height',title : '运单高度(mm)',width : 100,align:'center'}, 
		    {field : 'scan_cycle',title : '循环圈数',width : 80,align:'center'},
		    {field : 'supply_id',title : '导入台号',width : 100,align:'center'}, 
		    {field : 'supply_time',title : '导入时间',width : 150,formatter:formatTimeType,align:'center'},
		    {field : 'layer_id',title : '层级号',width : 80,align:'center'},
		    {field : 'car_id',title : '小车号',width : 80,align:'center'},
		    {field : 'scan_id',title : '龙门架号',width : 80,align:'center'},
		    {field : 'scan_time',title : '扫描时间',width : 150,formatter:formatTimeType,align:'center'}, 
		    {field : 'scan_status',title : '扫描状态',width : 80,formatter:formatScanStatus,align:'center'},
		    {field : 'sorting_time',title : '分拣时间',width : 150,formatter:formatTimeType,align:'center'}, 
		    {field : 'sorting_status',title : '分拣状态',width : 80,formatter:formatSortedStatus,align:'center'}, 
		    {field : 'update_flag',title : '更新标识',width : 80,formatter:formatUpdateStatus,align:'center'}, 
		    {field : 'update_time',title : '更新时间',	width : 150,formatter:formatTimeType,align:'center'},
		    {field : 'jb_status',title : '建包状态',width : 100,formatter:formatJbStatus,align:'center'}, 
		    {field : 'box_code',title : '箱(包)号',width : 100,align:'center'}, 
		    {field : 'jb_time',title : '建包时间',width : 150,formatter:formatTimeType,align:'center'},
		    {field : 'jb_update_flag',title : '建包更新标识',width : 80,formatter:formatUpdateStatus,align:'center'},
		    {field : 'jb_update_time',title : '建包更新时间',width : 150,formatter:formatTimeType,align:'center'},
		    {field : 'receive_time',title : '消息生成时间',width : 150,formatter:formatTimeType,align:'center'}
		] ]
	});

	document.onkeydown = function() {
		if (event.keyCode == 13) {
			gridLoad();
		}
	}
});

// 获取相关参数
function getParameter() {
	var parameter = {
		date : $('#griddate_start').val(),
		end_date : $('#griddate_end').val(),
		update_flag : $('#update_flag').combobox('getValue'),
		timestamp : tempPara()
	};
	return parameter;
}

// 双击行事件
function dblclickrow(index, row) {
	return;
}

// dataGrid数据加载
function gridLoad() {
	date_start = $('#griddate_start').val();
	date_end = $('#griddate_end').val();
	date_start = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date(Date.parse(date_start)));
	date_end = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date(Date.parse(date_end)));
	if(new Date(Date.parse(date_end)) - new Date(Date.parse(date_start)) <= 0) {
		closeprogress();
		parent.$.messager.alert("提示", "查询时间段:截止时间应该大于开始时间");
		return;
	}
	if(new Date(Date.parse(date_end)) - new Date(Date.parse(date_start)) > 24*60*60*1000){
		closeprogress();
		parent.$.messager.alert("提示", "查询时间段最大间隔为：24个小时");
		return;
	}
	gridExport(getParameter());
}
