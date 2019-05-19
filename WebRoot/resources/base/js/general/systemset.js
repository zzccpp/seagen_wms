//获取登录用户名
var operatorName = getCookie("userName");
// 输入的数据
var scene_deploy = {
		fRecno : -1,
		setNameCn : "",
		setName : "",
		setValue : "",
		setMark : ""
};

//
var sets = "";
//当前行号
var rowNum = -1;

$(function() {
	$("#dg_params_list").datagrid(
			{
				url : 'getTitleMapList.do',// 请求地址
				fitColumns : true,
				rownumbers : false,
				striped : true,
				idField : 'key',
				fitColumns : true,
				singleSelect : true,
				fit : true,
				onClickRow : function (index, row) { 
							if(rowNum == index)
								return;
							//取消所有选中
							$('#dg_params').datagrid('clearSelections');
							rowNum = index;
							sets = row.key;
							loadRightSets();
						},
				columns : [ [
						{field : 'key',title : '类对象名称',width : 20,hidden:'true',align : 'center'},	
						{field : 'value',title : '类型',width : 140,height:50,align : 'center'}
						] ],
				onLoadSuccess : function(data){
						sets = data.rows[0].key;
						loadRightSets();
						},
			});
});

//加载右侧参数列表
function loadRightSets(){
	$("#dg_params").datagrid(
			{
				url : 'getSystemSetList.do',// 请求地址
				fitColumns : true,
				loadMsg : '数据加载中请稍后……',
				rownumbers : true,
				striped : true,
				idField : 'fRecno',
				fitColumns : true,
				singleSelect : true,
				queryParams : getParameter(), // 请求的参数JSON
				fit : true,
				columns : [ [
						{field : 'fRecno',title : 'ID',width : 20,hidden:'true',align : 'center'},	
						{field : 'setNameCn',title : '参数中文名称',width : 70,align : 'left'},
						{field : 'setName',title : '参数英文名称 ',width : 100,hidden:'true',align : 'left'},
						{field : 'setValue',title : '参数值',width : 120,align : 'center'},
						{field : 'setMark',title : '备注',width : 140,align : 'left'},
						{field : 'createTime',title : '创建时间',width : 80,align : 'center'},
						{field : 'modifyTime',title : '修改时间',width : 80,align : 'center'}
				] ],
				toolbar : [ 
				    {
						text : '修改参数',
						iconCls : 'icon-edit',
						handler : function() {
							editParams();
						}
					} ,'-',
					{
						text : '&nbsp;&nbsp;刷&nbsp;&nbsp;新&nbsp;&nbsp;',
						iconCls : 'icon-search',
						handler : function() {
							loadRightSets();
						}
					} 
				],
			});
}

//获取相关参数
function getParameter() {
	var parameter = {
		sets : sets,
		timestamp : tempPara()
	};
	return parameter;
}

//修改
function editParams(){
	var row = $('#dg_params').datagrid('getSelected');
	if (!row){
		$.messager.alert('提示', "请选择一条数据进行编辑");
		return;
	}
	// 弹出添加面板
	$('#add_scene_system_set').window({
		title : "修改参数"
	});
	$('#dialogtitle').html("修改参数");
	$("#submit").attr("href", "javascript:saveSceneDeploy()");
	
	scene_deploy.fRecno = row.fRecno;
	scene_deploy.setNameCn = row.setNameCn;
	scene_deploy.setValue = row.setValue;
	scene_deploy.setMark = row.setMark;
	
	$("input[name='fRecno']").val(row.fRecno);
//	$("input[name='setNameCn']").val(""+row.setNameCn);
//	$("input[name='setValue']").val(""+row.setValue);
//	$("input[name='setMark']").val(""+row.setMark);
	$("#setNameCn").val(""+row.setNameCn);
	$("#setValue").val(""+row.setValue);
	$("#setMark").val(""+row.setMark);
	openWindow('add_scene_system_set');
}
//提交
function saveSceneDeploy() {
	var name = $('#setNameCn').val();
	if (name != null)
		name = ltrim(name);
	if ((name == null) || (name == "")) {
		$.messager.show({
			title : '提示',
			msg : '请输入参数名称！',
			showType : 'fade',
			timeout:3000,
			style : {
				right : '',
				bottom : ''
			}
		});
		return;
	}
	var value = $('#setValue').val();
	if ((value == null) || (value == "")) {
		$.messager.show({
			title : '提示',
			msg : '请输入参数值！',
			showType : 'fade',
			timeout:3000,
			style : {
				right : '',
				bottom : ''
			}
		});
		return;
	}
	scene_deploy.setNameCn = name;
	scene_deploy.setValue = value;
	scene_deploy.setMark = $('#setMark').val();
	
	var data = getPara(scene_deploy);
	console.log("data:" + data);

	var url = 'updateSystemSet.do';
	
	var successfun = function(data) {
		var resdata = null;
		var json = null;
		try {
			resdata = (typeof data == "undefined") ? null : data;
			json = eval(resdata);
			if (json.result == 0) {			
				$.messager.alert('提示', json.message);
				closeWindow('add_scene_system_set');
				loadRightSets();
			} else {
				$.messager.alert('提示', json.message);
			}
		} catch (e) {
			$.messager.alert('提示', "返回数据解析错误，请重试！");
		}
	};
	ajaxpost(url, data, successfun);
}
