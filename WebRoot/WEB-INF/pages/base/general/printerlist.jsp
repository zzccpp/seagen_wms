<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<title>打印列表</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/general/printer.js?v=10">
</script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/base/css/table.css" />
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<table id="dg_printers"></table>
	<%--添加面板 --%>
	<div id="add_printer" class="easyui-dialog"
		style="width: 450px; height: 380px; padding: 0px 10px 0px 10px; background: #fafafa;"
		data-options="closed:true,iconCls:'icon-save',modal:true">
		
			<h1 style="text-align: center;">
				<span id="dialogtitle">添加打印机</span>
			</h1>
			<table class="table table-hover">
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">打印机编码</div></td>
					<td><input class="defaultInput easyui-validatebox" type="text" id="printerNum"
						name="printerNum" data-options="required:true" onblur="checkPrinterNum()"/>
						<span id="checknum_result"></span>
						<input name="fRecno" id="fRecno" type="hidden"></td>
				</tr>
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">IP&nbsp;&nbsp;地&nbsp;&nbsp;址</div></td>
					<td><input class="defaultInput easyui-validatebox" type="text" id="printerIp"
						name="printerIp" data-options="required:true" />
						<span id="checkip_result"></span></td>
				</tr>
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">附属格口号</div></td>
					<td>
						<select id="chuteNumList" name="chuteNumList"  class="easyui-combogrid" style="height: 31px; width: 185px"
					data-options="  
								editable:false,   
					            panelWidth: 280, 
					            panelHeight: 120,
					            multiple: true,
					            idField: 'id',
					            textField: 'value',					            
					            method: 'get',
					            columns: [[
					                {field:'id',checkbox:true},
					                {field:'value',title:'格口',width:250}
					            ]],
					            fitColumns: true">
						</select>
					</td>
				</tr>
				<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">备&emsp;&emsp;注</div></td>
					<td><textarea class="defaultInput easyui-validatebox" rows="3"
							cols="14" id="reMark" name="reMark"></textarea></td>
				</tr>
				<tr>
					<td class="title"><div class="defaultTitle">操&#12288;&#12288;作</div></td>
					<td><a href="javascript:void(0)" id="submit"
						class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
						style="width: 80px">提交</a>&nbsp;&nbsp;<a href="javascript:void(0)"
						class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
						style="width: 80px" onclick="closeWindow('add_printer');"
						id="btn">取消</a></td>
				</tr>
			</table>
	</div>
</body>
</html>