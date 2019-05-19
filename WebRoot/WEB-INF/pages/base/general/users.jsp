<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<title>用户列表</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/general/users.js?v=20">
	
</script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?v=1" />
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<table id="dg_users"></table>
	<%--添加面板 --%>
	<div id="add_user" class="easyui-dialog"
		style="width: 450px; height: 500px; padding: 0px 10px 0px 10px; background: #fafafa;"
		data-options="closed:true,iconCls:'icon-save',modal:true">
		<form id="adduser_form" method="post">
			<h1 style="text-align: center;">
				<span id="dialogtitle">添加用户</span>
			</h1>
			<table class="table table-hover">
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">用&nbsp;&nbsp;户&nbsp;&nbsp;名</div></td>
					<td><input class="defaultInput easyui-validatebox" type="text"
						name="userName" data-options="required:true" /> <input
						name="id" type="hidden"></td>
				</tr>
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">密&emsp;&emsp;码</div></td>
					<td><input id="pwd"
						class="defaultInput easyui-validatebox" type="password"
						name="pwd"
						data-options="required:true,validType:'minLength[6,30]'" /></td>
				</tr>
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">确认密码</div></td>
					<td><input id="pwd1"
						class="defaultInput easyui-validatebox" type="password"
						name="pwd1" data-options="required:true" /></td>
				</tr>
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">手&emsp;&emsp;机</div></td>
					<td><input class="defaultInput easyui-validatebox" type="text"
						name="telNo" /></td>
				</tr>
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">邮&emsp;&emsp;件</div></td>
					<td><input class="defaultInput easyui-validatebox" type="text"
						name="email" data-options="validType:'email'" /></td>
				</tr>
				<tr>
					<td style="width: 20%; padding-top: 5px; padding-bottom: 5px;"><div class="fr">角&emsp;&emsp;色</div></td>
					<td><select id="roleList" name="roleList"  class="easyui-combogrid" style="height: 30px; width: 175px"
					data-options="  
								editable:false,   
					            panelWidth: 175, 
					            panelHeight: 100,
					            multiple: false,
					            idField: 'id',
					            textField: 'roleName',					            
					            method: 'get',
					            columns: [[
					                {field:'id',hidden:true},
					                {field:'roleName',width:180}
					            ]],
					            fitColumns: true">
					</select></td>
				</tr>
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">备&emsp;&emsp;注</div></td>
					<td><textarea class="defaultInput easyui-validatebox" rows="2"
							cols="14" name="remark" style="width: 170px;"></textarea></td>
				</tr>
				<tr>
					<td class="title"><div class="defaultTitle">操&#12288;&#12288;作</div></td>
					<td><a href="javascript:void(0)" id="submit"
						class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
						style="width: 80px">提交</a>&nbsp;&nbsp;<a href="javascript:void(0)"
						class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
						style="width: 80px" onclick="closeWindow('add_user');"
						id="btn">取消</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>