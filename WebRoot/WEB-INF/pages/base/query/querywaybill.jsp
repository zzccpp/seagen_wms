<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欣视景自动分拣系统信息管理平台</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?v=31" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/query/querywaybill.js?ver=1"></script>
<script type="text/javascript">
	
</script>
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<div id="toolbar" class="toolbar">
		<table>
			<tr>
				<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;运单号码</td>
				<td><input size="30"
					class="easyui-textbox" style="width: 150px;" id="waybillcode"
					value=""> <a id="" href="javascript:gridLoad()"
					class="easyui-linkbutton jbtn75"
					data-options="iconCls:'icon-search'">查询</a></td>
			</tr>
		</table>
	</div>
	<!--表单数据DataGrid-->
	<table id="dataList" class="easyui-datagrid"
		style="padding: 0; height: 100%;">
		<thead>
			<tr>
				<th data-options="halign:'center',field:'f_recno',width:80">序号</th>
				<th data-options="halign:'center',field:'waybill_id',width:80">追踪id</th>
				<th data-options="halign:'center',field:'waybill_code',width:150">运单号</th>
				<th data-options="halign:'center',field:'waybill_status',width:80">运单状态</th>
				<th data-options="halign:'center',field:'waybill_weight',width:130">运单重量</th>
				<th data-options="halign:'center',field:'site_code',width:100">运单目的地代码</th>				
				<th data-options="halign:'center',field:'waybill_time',width:150">运单生成时间</th>
				<th data-options="halign:'center',field:'create_time',width:150">本地接收时间</th>
			</tr>
		</thead>
	</table>
</body>
</html>