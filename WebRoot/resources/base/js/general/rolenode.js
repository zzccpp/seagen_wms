var initNode;
var currNode;
$(document).ready(
function() {
	$("#dg_rolenode").datagrid(
	{
		url : 'getAllRoles.do',// 请求地址
		loadMsg : '数据加载中请稍后……',
		rownumbers : true,
		nowrap : false, 
		striped : true,
		idField : 'id',
		fitColumns : true,
		singleSelect : true,
		fit : true,
		columns : [ [
				{
					field : 'roleName',
					title : '角色名称',
					width : 100,
					align : 'center'
				},
				{
					field : 'createTime',
					title : '创建时间',
					width : 100,
					align : 'center'
				},
				{
					field : 'id',
					title : '操作',
					width : 100,
					hidden:'true',
					align : 'center',
					formatter : function(value,
							rowData, index) {
						return "";
					}
				}
				] ],
		toolbar : [{
			text : '更新角色菜单信息',
			iconCls : 'icon-edit',
			handler : function() {
				update();
			}
		},'-',{
			text : '&nbsp;&nbsp;刷&nbsp;&nbsp;新&nbsp;&nbsp;',
			iconCls : 'icon-search',
			handler : function() {
				reload();
			}
		}],
	});
});
//重新加载、刷新
function reload(){
	$('#dg_rolenode').datagrid('clearSelections');
	$('#dg_rolenode').datagrid('reload');
}
//修改用户
function update() {
	var row = $('#dg_rolenode').datagrid('getSelected');
	if (!row){
		$.messager.alert('提示', "请选择一个角色进行编辑");
		return;
	}
	var row = $('#dg_rolenode').datagrid('getSelected');
	if(row.id == 4){
		parent.$.messager.alert("提示", 'wcs免登陆角色，不可修改');
		return;
	}
	
	//getRoleList();
	// 弹出添加面板
	$('#updateRoleNode').window({
		title : "更新角色菜单信息"
	});
	$('#dialogtitle').html("更新角色菜单信息");
	$("#submit").attr("href", "javascript:submitrolenode()");
	$("input[name='id']").val(row.id);

	$('#roleList').combogrid('setValue', row.roleId);
	getAllNodeList();
	getCurrNodeList();
	openWindow("updateRoleNode");
	// 设置表单action
}


//提交操作员数据
function submitrolenode() {
	var row = $('#dg_rolenode').datagrid('getSelected');
	var nodeList ='';
	$("[name='nodeCheckBox'][checked]").each(function(){ 
		nodeList+=$(this).val()+","; 
	});
	console.log(nodeList);
	var url = "updateRoleNode.do";
	var data = "";
	data += "&roleId=" +row.id+"&nodeList="+nodeList+"&timestamp=" + tempPara();
	var successfun = function(data) {
		var resdata = null;
		var json = null;
		try {
			resdata = (typeof data == "undefined") ? null : data;
			json = eval(resdata);
			if (json.result == 0) {
				parent.$.messager.alert("提示", "更新成功");
				addCancel();
				$('#dg_users').datagrid('reload');
			} else {
				parent.$.messager.alert("提示", "更新失败");
			}
		} catch (e) {
			parent.$.messager.alert("提示", "返回数据解析错误，请重试！");
		}
	};
	ajaxpost(url, data, successfun);
}



/**初始菜单节点*/
function getAllNodeList(){
	var roleId = $("#roleId").val();
	$("#nodeList").empty();
	var row = $('#dg_rolenode').datagrid('getSelected');
	$("#nodeList").append("<lable>角色名称："+row.roleName+"</lable><br/>");
	var loadallnodesuccess = function(data) {
		try {
			initNode = eval(data);
			console.log(initNode);
			var len = initNode.length;
			var pid = 0;
			for(var i =0;i< len;i++){
				if(initNode[i].pid == 0){
					pid = initNode[i].id;
					$("#nodeList").append( "<input type='checkbox' class="+initNode[i].id+" name ='nodeCheckBox' onchange='Check("+initNode[i].id+")' value = "+initNode[i].id
							+" /><label  data-options='width:90px;disply:inline-block;font-size:16px;font-weight:bold;'>"+initNode[i].nodeName+"</label></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					var num = 0;
					for(var j =0;j< len;j++){
						if(initNode[j].pid == pid){
							if(num!=0&&num%4==0)
								$("#nodeList").append("</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							$("#nodeList").append( "<input type='checkbox' class="+initNode[j].id+" name ='nodeCheckBox' onchange='Check("+initNode[j].id+")' value = "+initNode[j].id
									+" /><label  data-options='width:90px;disply:inline-block;'>"+initNode[j].nodeName+"</label>");
							num++;
						}
					}
					$("#nodeList").append("</br>");
				}
			}
		}catch (e) {
		}
		
	};
	var url = 'getAllNodes.do';
	var data = "timestamp=" + tempPara();
	ajaxpost(url, data, loadallnodesuccess);
}
/**获取当前角色的菜单节点*/
function getCurrNodeList(){
	var row = $('#dg_rolenode').datagrid('getSelected');
	var roleId = $("#roleId").val();
	var loadcurrnodesuccess = function(data) {
		try {
			currNode = eval(data);
			console.log(currNode);
			console.log(currNode[0].key);
			console.log(currNode.length);
			var len = currNode.length;
			for(var i =0;i< len;i++){
				$("."+currNode[i].key).attr("checked",true);
			}
		}catch (e) {
		}
		
	};
	var url = 'getCurrNodes.do';
	var data = "roleId="+row.id+"&timestamp=" + tempPara();
	ajaxpost(url, data, loadcurrnodesuccess);
}

function Check(id){
	if($("."+id).is(':checked')){
		$("."+id).attr("checked",true);
	}else{
		$("."+id).attr("checked",false);
	}
	
}
// 关闭添加窗口
function addCancel() {
	closeWindow("updateRoleNode");
}
