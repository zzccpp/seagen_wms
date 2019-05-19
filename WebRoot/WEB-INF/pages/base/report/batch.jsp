<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欣视景自动分拣系统信息管理平台</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/report/batch.js?b=1"></script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?dd=1" />
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<div id="toolbar" class="toolbar">
		<table>
			<tr>
				<td>
					&nbsp;&nbsp;统计方式：<input  id="type" class="easyui-combobox" name="type"
					style="width: 80px;">
					&nbsp;&nbsp;显示类型：<input  id="more_type" class="easyui-combobox" name="more_type"
					style="width: 60px;">
					<!-- <a id="hideStatus" href="javascript:setHideStatus()" style="margin-left: 5px;" class="easyui-linkbutton jbtn90">详细</a> -->
					<a id="" href="javascript:showgriddata();" class="easyui-linkbutton jbtn75" data-options="iconCls:'icon-search'">查询</a>
					<a id="" href="javascript:excelExport()" style="margin-left: 15px;" class="easyui-linkbutton jbtn90"
					data-options="iconCls:'icon-excel-export'">Excel导出</a>
				</td>
			</tr>
		</table>
	</div>
	<!--表单数据DataGrid-->
	<table id="dataList" class="easyui-datagrid"
		style="padding: 0; height: 100%;"></table>
</body>
</html>