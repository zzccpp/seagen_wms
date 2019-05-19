$(function() {
	$("#dg_sortDetail").datagrid({
		url : 'queryRunSortSchemeDetail.do',
		fitColumns : true,
		singleSelect : true,
		rownumbers : true,
		fit : true,
		pagination : true, // 是否在 datagrid 的底部显示分页栏
		pageNumber : 1,// 初使化时显示第几页
		pageSize : 30,// 每页显示的记录条数，默认为10
		pageList : [ 30, 50, 100, 200, 350 ],// 可以设置每页记录条数的列表		
		//pagePosition : 'bottom',
		toolbar : '#filter',
		
		queryParams : {
			chute_no : -1,
			site_code : '',
			box_site_name:''
		}
	});
});

//获取相关参数
function gridLoad() {
	var chute_no = $('#chute_num').val();
	if (chute_no == "")
		chute_no = -1;
	$('#dg_sortDetail').datagrid({
		queryParams : {
			chute_no : chute_no,
			site_code : $('#site_code').val(),
			box_site_name : $('#box_site_name').val()
		}
	});
}