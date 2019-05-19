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
	src="${pageContext.request.contextPath}/resources/base/js/general/rolenode.js?v=5">
	
</script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?v=1" />
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<table id="dg_rolenode"></table>
	<%--添加面板 --%>
	<div id="updateRoleNode" class="easyui-dialog"
		style="width: 460px; height: 500px; padding: 0px 10px 0px 10px; background: #fafafa;"
		data-options="closed:true,iconCls:'icon-save',modal:true">
		<div id="nodeList">
			
		</div>
		<div style="margin-left:120px;margin-top:50px;">
			<a href="javascript:void(0)" id="submit"
						class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
						style="width: 80px">提交</a>&nbsp;&nbsp;<a href="javascript:void(0)"
						class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
						style="width: 80px" onclick="closeWindow('updateRoleNode');"
						id="btn">取消</a>
		</div>
	</div>
</body>
</html>