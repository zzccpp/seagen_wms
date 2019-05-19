<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欣视景自动分拣系统信息管理平台</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?v=1" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/query/queryscan.js?v=2"></script>
<script type="text/javascript">
	
</script>
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<div id="toolbar" class="toolbar">
		<table>
			<tr>
				<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;包裹条码</td>
				<td><input size="30"
					class="easyui-textbox" style="width: 150px;" id="packagecode"
					value=""> <a id="" href="javascript:gridLoad()"
					class="easyui-linkbutton jbtn75"
					data-options="iconCls:'icon-search'">查询</a></td>
					<td style="color: red;font-weight:bold;font-size:16px;padding-left:10px;">注意：因数据量较大，查询耗时较长，请勿多次查询，以免影响正常生产！</td>
			</tr>
		</table>
	</div>
	<!--表单数据DataGrid-->
	<table id="dataList" class="easyui-datagrid"
		style="padding: 0; height: 100%;">		
	</table>
</body>
</html>