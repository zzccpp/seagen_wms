<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<title>System列表</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/general/systemset.js?v=8">
</script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?v=1" />
</head>
<body >
	<div class="easyui-layout" style="padding: 0; height: 100%;"data-options="region:'center', fit:true, border:true"
	oncontextmenu="window.event.returnValue=false">
		<div data-options="region:'west'" title="参数设置列表" style="width:140px;height: 100%;">
			<table class="easyui-datagrid" id="dg_params_list"
					data-options="singleSelect:true,fitColumns:false">
			</table>
		</div>
		<div data-options="region:'center',title:'参数内容'">
			<table class="easyui-datagrid" id="dg_params">
			</table>
		</div>
	</div>
	<div id="add_scene_system_set" class="easyui-dialog"
		style="width: 400px; height: 350px; padding: 0px 10px 0px 10px; background: #fafafa;"
		data-options="closed:true,iconCls:'icon-save',modal:true, title:'参数设置'">
		<h1 style="text-align: center;">
			<span id="dialogtitle">设置参数</span>
		</h1>
		<table class="table table-hover">
			<tr>
				<td style="width: 20%; padding-top:20px; padding-bottom: 5px;"><div
						class="fr">参数名称</div></td>
				<!--  
				<td><input class="defaultInput easyui-validatebox" type="text"
					id="setNameCn" name="setNameCn" data-options="required:true" />
					<input id="fRecno" name="fRecno" type="hidden"></td>
					-->
						<td><textarea rows="2" cols="20" class="defaultInput easyui-validatebox" type="text"
					id="setNameCn" name="setNameCn" data-options="required:true"></textarea>
					<input id="fRecno" name="fRecno" type="hidden"></td>
			</tr>
			<tr>
				<td style="width: 20%; padding-top: 20px; padding-bottom: 5px;"><div
						class="fr">参数值</div></td>
				<!--  
				<td><input class="defaultInput easyui-validatebox" type="text"
					id="setValue" name="setValue" data-options="required:true" /></td>
					-->
					<td><textarea rows="2" cols="20" class="defaultInput easyui-validatebox" type="text"
					id="setValue" name="setValue" data-options="required:true" ></textarea></td>
			</tr>
			<tr>
				<td style="width: 20%; padding-top: 20px; padding-bottom: 5px;"><div
						class="fr">备注</div></td>
				<!--  
				<td><textarea rows="3" cols="20" class="defaultInput easyui-validatebox" 
					id="setMark" name="setMark"/></td>
					-->
					<td><textarea rows="2" cols="20" class="defaultInput easyui-validatebox" 
					id="setMark" name="setMark"></textarea></td>
			</tr>
			<tr>
				<td class="title"><div class="defaultTitle">操&#12288;&#12288;作</div></td>
				<td><a href="javascript:saveSceneDeploy()" id="submit"
					class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
					style="width: 80px">提交</a>&#12288;&#12288;<a
					href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-cancel'" style="width: 80px"
					onclick="closeWindow('add_scene_system_set');" id="btn">取消</a></td>
			</tr>
		</table>
	</div>
</body>
</html>