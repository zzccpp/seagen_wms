$(function() {
	// 初使化 DataGrid组件与请求地址
	dataGrid = $("#dataList");
	dataGridUrl = "queryScan.do";
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
		pageSize : 100,// 每页显示的记录条数，默认为10
		pageList : [ 100, 200, 500, 1000 ],// 可以设置每页记录条数的列表
		queryParams : getParameter(), // 请求的参数JSON
		toolbar : '#toolbar', // 查询工具栏
		// url : "",
		// onClickRow : onClickRow
		onDblClickRow: dblclickrow,
		frozenColumns:[[    
	        {field:'f_recno',title:'序号',width:80,align:'center'},    
	        {field:'package_code',title:'包裹条号',width:150,align:'center'}   
		]],    
	    columns:[[    
	        {field:'batch_id',title:'批次ID',width:80,align:'center'},    
	        {field:'waybill_id',title:'追踪ID',width:100,align:'center'},    
	        {field:'waybill_num',title:'分拣序号',width:200,align:'center'},    
	        {field:'site_code',title:'目的地代码',width:80,align:'center'} ,    
	        {field:'chute_id',title:'投递格口',width:80,align:'center'} ,   
	        {field:'waybill_time',title:'运单生成时间',width:150,formatter:formatTimeType,align:'center'},    
	        {field:'waybill_status',title:'运单状态',width:80,formatter:formatWaybillStatus,align:'center'},    
	        {field:'scan_cycle',title:'循环圈数',width:80,align:'center'}, 
	        {field:'package_weight',title:'运单重量(g)',width:100,align:'center'}, 
	        {field:'package_length',title:'运单长度(mm)',width:100,align:'center'}, 
	        {field:'package_width',title:'运单宽度(mm)',width:100,align:'center'}, 
	        {field:'package_height',title:'运单高度(mm)',width:100,align:'center'}, 
	        {field:'supply_id',title:'导入台号',width:80,align:'center'},    
	        {field:'supply_time',title:'导入时间',width:150,formatter:formatTimeType,align:'center'},  
	        {field:'layer_id',title:'层级号',width:80,align:'center'},
	        {field:'car_id',title:'小车号',width:80,align:'center'} ,     
	        {field:'scan_id',title:'龙门架号',width:80,align:'center'},   
	        {field:'scan_time',title:'扫描时间',width:150,formatter:formatTimeType,align:'center'}, 
	        {field:'scan_status',title:'扫描状态',width:80,formatter:formatScanStatus,align:'center'}, 
	        {field:'update_flag',title:'更新标识',width:80,formatter:formatUpdateStatus,align:'center'},     
	        {field:'update_time',title:'更新时间',width:150,formatter:formatTimeType,align:'center'}, 
	        {field:'receive_time',title:'消息生成时间',width:150,formatter:formatTimeType,align:'center'},
	    ]]    
	}); 
	
	document.onkeydown = function() {
		if (event.keyCode == 13) {
			gridLoad();
		}
	};
	
	// 首次查询
	// gridExport(getParameter());
});

// 获取相关参数
function getParameter() {
	var parameter = {
		packagecode : $('#packagecode').val(),
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
	if (($('#packagecode').val() == null) || ($('#packagecode').val() == "")) {
		//alert("请输入条码信息！！！");
		 $.messager.show({
            title:'提示',
            msg:'请输入条码信息！！！',
            showType:'fade',
            style:{
                right:'',
                bottom:''
            }
        });
		return;
	} 
	gridExport(getParameter());
}
