$(document).ready(
function() {
	$("#dg_users").datagrid(
	{
		url : 'getUsers.do',// 请求地址
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
					field : 'userName',
					title : '操作员名称',
					width : 100,
					align : 'center'
				},
				{
					field : 'telNo',
					title : '手机',
					width : 100,
					align : 'center'
				},
				{
					field : 'email',
					title : '邮件',
					width : 100,
					align : 'center'
				},	{
					field : 'roleId',
					title : '角色',
					width : 100,
					align : 'center',
					formatter : function(value,
							rowData, index) {
						var str = "";
						if (value == 2) {
							str += "管理员";
						} else if(value == 3) {
							str += "操作员";
						}
						return str;
					}
				},
				{
					field : 'remark',
					title : '备注',
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
					field : 'useFlag',
					title : '启用状态',
					width : 100,
					align : 'center',
					formatter : function(value,
							rowData, index) {
						var str = "";
						if (value == 0) {
							str += "启用";
						} else {
							str += "禁用";
						}
						return str;
					},
					styler : function(value,
							rowData, index) {
						if (value == 0) {
							return 'color:green;';
						} else {
							return 'color:red;';
						}
					}
				}
				,
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
				},
				{
					field : 'roleId',
					hidden:'true',
					align : 'center'
				}
				] ],
		toolbar : [ {
			text : '添加操作员',
			iconCls : 'icon-add',
			handler : function() {
				// 弹出添加面板
				$('#add_user').window({
					title : "添加操作员"
				});
				$('#dialogtitle').html("添加操作员");
				$("#submit").attr("href","javascript:submituser(0)");
				$("input[name='id']").val("-1");
				$("input[name='userName']").val("");
				$("input[name='pwd']").val("");
				$("input[name='pwd1']").val("");
				$("input[name='telNo']").val("");
				$("input[name='email']").val("");
				$("textarea[name='remark']").val("");
				getRoleList();
				openWindow("add_user");
			}
		},'-',{
			text : '修改操作员',
			iconCls : 'icon-edit',
			handler : function() {
				update();
			}
		},'-',{
			text : '禁用/启用',
			iconCls : 'icon-edit',
			handler : function() {
				changeState();
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
	$('#dg_users').datagrid('clearSelections');
	$('#dg_users').datagrid('reload');
}
//修改用户
function update() {
	var row = $('#dg_users').datagrid('getSelected');
	if (!row){
		$.messager.alert('提示', "请选择一位操作员进行编辑");
		return;
	}
	getRoleList();
	// 弹出添加面板
	$('#add_user').window({
		title : "修改操作员"
	});
	$('#dialogtitle').html("修改操作员");
	$("#submit").attr("href", "javascript:submituser(1)");
	$("input[name='id']").val(row.id);
	$("input[name='userName']").val(row.userName);
	$("input[name='pwd']").val(row.pwd);
	$("input[name='pwd1']").val(row.pwd);
	$("input[name='telNo']").val(row.telNo);
	$("input[name='telephone']").val(row.telephone);
	$("input[name='email']").val(row.email);
	$("textarea[name='remark']").val(row.remark);
	$('#roleList').combogrid('setValue', row.roleId);
	openWindow("add_user");
	// 设置表单action
}

//禁用与启用
function changeState() {
	var row = $('#dg_users').datagrid('getSelected');
	if (!row){
		$.messager.alert('提示', "请选择一位操作员进行编辑");
		return;
	}
	
	var useFlag = row.useFlag;
	var sms = "用户名:" + row.userName;
	var str = "";
	if (useFlag == 0) {
		//当前状态启用
		sms += ",禁用";
		str += "是否禁用："+row.userName;
		useFlag = 1;
	} else {
		//当前状态禁用
		sms += ",启用";
		str += "是否启用："+row.userName;
		useFlag = 0;
	}
	var url = 'lockOrUnlock.do';
	var data = "useFlag=" + useFlag + "&id=" + row.id + "&userName=" + row.userName;
	var successfun = function(data) {
		var resdata = null;
		var json = null;
		try {
			resdata = (typeof data == "undefined") ? null : data;
			json = eval(resdata);
			if (json.result == 0) {
				parent.$.messager.alert("提示", sms + ':操作成功!');
				$('#dg_users').datagrid('reload');
			} else {
				parent.$.messager.alert("提示", sms + ':操作失败!');
			}
		} catch (e) {
			parent.$.messager.alert("提示", "返回数据解析错误，请重试！");
		}
	};
	 
	$.messager.confirm('提示',str,function(r){
		 if (r){
			 ajaxpost(url, data, successfun);
		 }
	});
}

//提交操作员数据
function submituser(type) {
	obj= $('#roleList').combogrid('grid').datagrid('getSelected');
	if(obj == null){
		$.messager.alert('提示', "角色不可为空！");
		return;
	}
	var roleId = obj.id;
	var url = "";
	var data = "";
	if(type == 0){
		//新增操作员
		url = 'addUser.do';
		data = "id=-1&useFlag=0";
	} else {
		//修改操作员
		var row = $('#dg_users').datagrid('getSelected');
		url = 'updateUser.do';
		data = "id=" + row.id +"&useFlag=0";
	}
	var userName = $("input[name='userName']").val();
	if (($.trim(userName) == "") || (userName.length > 10)|| (userName.length < 2)) {
		$.messager.alert('提示', "名称不可为空，长度：2~10");
		return;
	}
	var pwd = $("input[name='pwd']").val();
	if (($.trim(pwd) == "") || (pwd.length > 30)|| (pwd.length < 6)) {
		$.messager.alert('提示', "密码不可为空，长度：6~30");
		return;
	}
	var pwd1 = $("input[name='pwd1']").val();
	if (($.trim(pwd1) == "") || (pwd1.length > 30)|| (pwd1.length < 6)) {
		$.messager.alert('提示', "确认密码不可为空，长度：6~30");
		return;
	}
	if(pwd1 != pwd){
		$.messager.alert('提示', "两次密码不一致!");
		return;
	}
	var telNo = $("input[name='telNo']").val();
	if(!isPhone(telNo)){
		$.messager.alert('提示', "手机号格式不正确，例如：13800138001");
		return;
	}
	
	var email = $("input[name='email']").val();
	if(!isEmail(email)){
		$.messager.alert('提示', "邮箱格式不正确，例如：seagen@seagen.cn");
		return;
	}
	var remark = $("textarea[name='remark']").val();
	
	data += "&userName=" +userName+"&pwd="+pwd
		+"&telNo="+telNo+"&email="+email+"&remark="+remark+"&roleId="+roleId;
	
	
	var successfun = function(data) {
		var resdata = null;
		var json = null;
		try {
			resdata = (typeof data == "undefined") ? null : data;
			json = eval(resdata);
			if (json.result == 0) {
				parent.$.messager.alert("提示", json.message);
				addCancel();
				$('#dg_users').datagrid('reload');
			} else {
				parent.$.messager.alert("提示", json.message);
			}
		} catch (e) {
			parent.$.messager.alert("提示", "返回数据解析错误，请重试！");
		}
	};
	ajaxpost(url, data, successfun);
	
}


function getRoleList(){
	var loadrolesuccess = function(data) {
		try {
			$('#roleList').combogrid('grid').datagrid({
				data : data
			});
		}catch (e) {
			
		}
	};
	var url = 'getRoles.do';
	var data = "timestamp=" + tempPara();
	ajaxpost(url, data, loadrolesuccess);
}


// 关闭添加窗口
function addCancel() {
	closeWindow("add_user");
}
