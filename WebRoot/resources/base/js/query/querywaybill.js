$(function() {
	// 初使化 DataGrid组件与请求地址
	initDataGrid("dataList", "querywaybill.do", true);
	
	// 设置分页控件
	$("#dataList").datagrid({
	    columns:[[       
	        {field:'f_recno',title:'序号',width:60,align : 'center'},    
	        {field:'waybill_id',title:' 追踪id',width:60,align : 'center'},  
	        {field:'waybill_code',title:'运单号',width:100,align:'center'},  
	        {field:'waybill_status',title:'运单状态',width:100,align:'center'
	        	,formatter : function(value,rowdata, index) {
					var str = "";
					if (value == 0) {
						str = "<font color='green'>正常运单</font>";
					} else if (value==1) {
						str = "<font color='red'>运单取消</font>";
					} else if (value==2) {
						str = "<font color='red'>运单地址更改</font>";
					} else if (value==3) {
						str = "<font color='red'>无数据</font>";
					}
					return str;
				}},
	        {field:'waybill_weight',title:'运单重量(g)',width:80,align:'center'},  
	        {field:'site_code',title:'运单目的地代码',width:250,align:'center'}, 
	        {field:'waybill_time',title:'运单生成时间',width:150,align:'center'},
	        {field:'create_time',title:'本地接收时间',width:150,align:'center'}
	    ]]
	});
	
	document.onkeydown = function() {
		if (event.keyCode == 13) {
			gridLoad();
		}
	}
	
	// 首次查询
	// gridExport(getParameter());
});

$('#packagecode').bind('keypress', function (event) {
    if (event.keyCode == "13") {
    	gridLoad();
    }
});

// 获取相关参数
function getParameter() {
	var parameter = {
		waybillcode : $('#waybillcode').val(),
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
	if (($('#waybillcode').val() == null) || ($('#waybillcode').val() == "")) {
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