var dataGrid = null;// 请求的参数JSON
var dataGridUrl = null;// 请求的地址
var ispagination = false; //是否分页

/**
 * 给dataGrid赋值方便页面其它地方调用
 */
function initDataGrid(dataGridId, url, ispages) {
	dataGrid = $("#" + dataGridId);
	dataGridUrl = url;

	var ispagination = (typeof ispages == "undefined") ? false : ispages;

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
		fitColumns : true, // 是否自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
		autoRowHeight : false, // 是否自动行高
		pagination : ispagination, // 是否在 datagrid 的底部显示分页栏
		pageNumber : 1,// 初使化时显示第几页
		pageSize : 30,// 每页显示的记录条数，默认为10
		pageList : [ 30, 50, 75, 100 ],// 可以设置每页记录条数的列表
		queryParams : getParameter(), // 请求的参数JSON
		toolbar : '#toolbar', // 查询工具栏
		// url : "",
		// onClickRow : onClickRow
		onDblClickRow: dblclickrow
	});
}

/**
 * 载入dataGrid数据，第一次是载入，以后是重载
 */
var intInit = 0;
function gridExport(para) {
	if (intInit == 0) {
		dataGrid.datagrid({
			// 请求的参数JSON
			queryParams : para,
			// 请求的地址
			url : dataGridUrl

		});
		intInit = 1;
	} else
		dataGrid.datagrid('load', para);
}

/**
 * easyUI 提示消息
 */
function openMessager(imsg) {
	var rMsg = imsg || '正在处理你的请求...';
	rMsg = trim(rMsg);
	$.messager.progress({
		title : '提示',
		msg : rMsg,
		text : '请稍后...'
	});
}

/**
 * easyUI 关闭提示消息
 */
function closeMessager() {
	$.messager.progress('close');
}