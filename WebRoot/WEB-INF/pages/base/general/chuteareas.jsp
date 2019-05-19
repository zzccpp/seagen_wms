<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>���Ӿ��Զ��ּ�ϵͳ��Ϣ����ƽ̨</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/general/chuteareas.js?v=5">
</script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/base/css/table.css" />
</head>
<body class="easyui-layout" data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<table id="dg_chuteareas"></table>
	<div id="add_chuteareas" class="easyui-dialog" style="width: 450px; height: 320px; padding: 3px" title="��������"
		data-options="closed:true, modal:true, closable:true">
		<h1 style="text-align: center;">
			<span id="dialogtitle">�������</span>
		</h1>
		<table class="table table-hover">
		<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">��������</div></td>
					<td><input class="defaultInput easyui-validatebox" type="text"
						id="areaName" name="areaName" data-options="required:true" onblur="checkAreaName()"/>
						<span id="check_result"></span>
						<input id="fRecno" name="fRecno" type="hidden"></td>
				</tr>
			<tr>
				<td style="width: 20%; padding-top: 5px; padding-bottom: 5px;"><div class="fr">���</div></td>
				<td><select id="chuteNumList" name="chuteNumList"  class="easyui-combogrid" style="height: 31px; width: 300px"
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
					                {field:'value',title:'���',width:250}
					            ]],
					            fitColumns: true">
				</select></td>
			</tr>
			<tr>
					<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
							class="fr">��&emsp;&emsp;ע</div></td>
					<td><textarea class="defaultInput easyui-validatebox" rows="3"
							cols="14" id="reMark" name="reMark"></textarea></td>
				</tr>
			<tr>
				<td class="title"><div class="defaultTitle">��&#12288;&#12288;��</div></td>
				<td>&#12288;<a href="javascript:submitAdd();" id="submit" class="easyui-linkbutton" data-options="iconCls:'icon-save'"
					style="width: 80px">����</a> &#12288;&#12288;<a id=""
					href="javascript:addCancel();" class="easyui-linkbutton jbtn75"
					data-options="iconCls:'icon-cancel'">����</a></td>
			</tr>
		</table>
	</div>
 		

</body>
</html>