<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<title>查看当前分拣明细</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/query/queryscheme.js?v=2"></script>
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<table class="easyui-datagrid" id="dg_sortDetail">
		<thead>
	        <tr>
	            <th data-options="field:'chute_num',width:150,align:'center'">格口号</th>
	        	<th data-options="field:'site_code',width:150,align:'center'">站点编码</th>
	            <th data-options="field:'box_site_name',width:200,align:'center'">站点名称</th>
	            <th data-options="field:'box_site_code',width:150,align:'center'">站点箱号编码</th>
	            <th data-options="field:'weight',width:150,align:'center'">最小建包重量(g)</th> 
	        </tr>
    	</thead>
	</table>
	<div id="filter" style="height: 40px; line-height: 40px;">&nbsp;&nbsp;&nbsp;&nbsp;
		格口号<input type="text" size="20" class="easyui-numberbox" max="1000" min="1" data-options="precision:'0'" id="chute_num" style="height: 27px;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		站点编码<input type="text" size="20" class="easyui-textbox" style="margin-right: 50px;height: 27px;" id="site_code" value="" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		站点名称<input type="text" size="20" class="easyui-textbox" style="margin-right: 80px;height: 27px;" id="box_site_name" value="" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <a id="" href="javascript:gridLoad()" class="easyui-linkbutton jbtn75" style="margin-bottom: 5px;"
		   data-options="iconCls:'icon-search'">查询</a>
	</div>
</body>
</html>