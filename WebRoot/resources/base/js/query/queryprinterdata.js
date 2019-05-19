$(function() {
	$("#dategrid_printerdate").datagrid({
		url : 'getPrinterRecord.do',
		fitColumns : true,
		nowrap : false, 
		singleSelect : true,
		rownumbers : true,
		fit : true,
		pagination : true,
		pagePosition : 'bottom',
		toolbar : '#filter',
		queryParams : {
			chuteId : -1,
			boxCode : '',
		}

	});
});
//获取相关参数
function gridLoad() {
	var chuteId = $('#chuteId').val();
	if (chuteId == "")
		chuteId = -1;
	$('#dategrid_printerdate').datagrid({
		queryParams : {
			chuteId : chuteId,
			boxCode : $('#boxCode').val(),
		}
	});
}
//重新打印
function rePrint() {
	var row = $("#dategrid_printerdate").datagrid('getSelected');
	if(row==null){
		$.messager.alert('提示', "请选择记录!");
		return;
	}
	$.messager.confirm('提示','确定重新打印['+row.boxCode+']标签吗?',function(r){   
	    if (r){
	    	// 删除打印机记录
	    	$.ajax({
	    		type: "POST",
	    		url : "rePrint.do",
	    		data: "boxCode="+row.boxCode,
	    		success : function(data) {
	    			$.messager.alert('提示', data.message);
	    		}
	    	});
	    }
	});
}


