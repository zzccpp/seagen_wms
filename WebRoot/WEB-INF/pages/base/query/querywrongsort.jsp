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
	src="${pageContext.request.contextPath}/resources/base/js/query/querywrongsort.js?v=3"></script>
<script type="text/javascript">
	
</script>
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<div id="toolbar" style="padding: 20px;" >
		<table>
			<tr>
				<td style="text-align: right;">运单号码</td>
				<td>
					<input size="30" class="easyui-textbox" style="width: 200px;" id="packageCode" value=""> 
					<a id="" href="javascript:gridLoad()" class="easyui-linkbutton jbtn75" data-options="iconCls:'icon-search'">分析</a>
				</td>
				<td style="color: red;font-weight:bold;font-size:20px;padding-left:20px;">注意：建议停机后查询分析，以免影响正常生产</td>
			</tr>
		</table>
	</div>
	<hr style=" height:2px;border:none;border-top:2px dotted #185598;" />
	<%--显示扫描记录 --%>
	<div id="result_scan" style="padding-left: 20px;height: 40%;width:100%;overflow-y:scroll;"></div>
	<%--显示分拣记录 --%>
	<div id="result_sort" style="padding-left: 20px;margin-top: 10px;height: 45%;width:100%;overflow-y:scroll;margin-bottom: 50px;"></div>
	
</body>
</html>