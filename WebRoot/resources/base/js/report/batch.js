var type;
var page;//当前页数
var rows;//显示行数即pagesize
//var hidehtml ;
//var cookiename = 'sortbatch';
$(function() {
	/*var isMore = getCookie(cookiename);
	if(isMore == '1'){
		hidehtml = '<span class="l-btn-left"><span class="l-btn-text">精简</span></span>';
		$('#hideStatus').html(hidehtml);
	}*/
	
	// 初使化 DataGrid组件与请求地址
	dataGrid = $("#dataList");
	dataGridUrl = "queryBatch.do";
    var type_options = [{text : "近一周", value : "0"},
                     {text : "近一个月", value : "1"},
                     {text : "近三个月", value : "2"}
//                     ,{text : "近半年", value : "3"}
                     ];   
    $("#type").combobox({
		valueField: 'value',
        textField: 'text', 
        data : type_options,
        value:'0',
        panelHeight:140,
		onChange: function (n,o) {
			type = n;
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
		remoteSort : false,
		pageNumber : 1,// 初使化时显示第几页
		pageSize : 30,// 每页显示的记录条数，默认为10
		pageList : [ 30, 50, 75, 100 ,200 ],// 可以设置每页记录条数的列表
		queryParams : getParameter(), // 请求的参数JSON
		toolbar : '#toolbar' // 查询工具栏
	}); 
	// 首次查询
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
			frozenColumns : [datalist.frozenColumns],
			columns : [datalist.columns]
		});
		// 首次查询
		gridLoad();
	};
	var url = 'getBatchColumns.do';
	ajaxpost(url, getParameter(), loaddatasuccess);
}
//dataGrid数据加载
function gridLoad() {
	gridExport(getParameter());
	
}

//获取相关参数
function getParameter() {
	//var isMore = getCookie(cookiename);
	var isMore = $('#more_type').combobox('getValue');
	var parameter = {
			id:802,
			type : $('#type').combobox('getValue'),
			more:isMore == null? 0 : parseInt(isMore, 10),
		timestamp : tempPara()
	};
	return parameter;
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
		str	= '确定导出近一周的数据吗?';
	}
	if(type == 1){
		str	= '确定导出近一个月的数据吗?';
	}
	if(type == 2){
		str	= '确定导出近三个月的数据吗?';
	}
	if(type == 3){
		str	= '确定导出近半年的数据吗?';
	}
	//var isMore = getCookie(cookiename);
	var isMore = $('#more_type').combobox('getValue');
	var options = dataGrid.datagrid('options');  
	page = options.pageNumber;//当前页数  
	rows = dataGrid.datagrid('getPager').data("pagination").options.total;//每页的记录数（行数）   
	var data = "id=802&type=" + type +"&page="+page+"&rows="+rows + '&more=' + (isMore == null? 0 : parseInt(isMore, 10)) + "&timestamp=" + tempPara();
	
	$.messager.confirm('提示',str,function(r){
		 if (r){
			 var url="exportBatch.do?"+data;
			 window.open(url);
		 }
	});
}
