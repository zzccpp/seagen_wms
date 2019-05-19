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
	src="${pageContext.request.contextPath}/resources/base/js/query/querywaybilltrace.js?ver=1"></script>
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
	<hr style=" height:2px;border:none;border-top:2px dotted #185598;" />
	<%--显示查询记录 --%>
	<div id="result_search" style="padding-left: 20px;height: 40%;width:100%;overflow-y:scroll;"></div>
</body>
</html>