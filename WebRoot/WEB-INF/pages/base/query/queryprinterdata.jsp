<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<title>打印记录查询</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/query/queryprinterdata.js?v=1"></script>
<style type="text/css">
.textbox{
	height: 22px;
}
</style>
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<table class="easyui-datagrid" id="dategrid_printerdate"><!-- fitColumns:false -->
		<thead>
	        <tr>
	            <th data-options="field:'chuteId',width:100,align:'center'">格口物理编号</th>
<!-- 	            <th data-options="field:'chuteNum',width:100,align:'center'">格口逻辑编号</th>  -->
	        	<th data-options="field:'boxCode',width:200,align:'center'">箱号</th>
	        	<th data-options="field:'packageCount',width:100,align:'center'">件数</th>
	            <th data-options="field:'printCmd',width:700">打印内容</th>
	            <th data-options="field:'createTime',width:180,align:'center'">打印机时间</th> 
	        </tr>
    	</thead>
	</table>
	<div id="filter" style="height: 40px; line-height: 40px;">
		物理格口号:<input type="text" size="20" class="easyui-numberbox" max="999999999" min="1" data-options="precision:'0'" id="chuteId">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		箱号:<input type="text" size="20" class="easyui-textbox" style="margin-right: 50px;" id="boxCode" value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <a id="" href="javascript:gridLoad()" class="easyui-linkbutton jbtn75" style="margin-bottom: 5px;"
		   data-options="iconCls:'icon-search'">查询</a>
		 <a id="" href="javascript:rePrint()" class="easyui-linkbutton jbtn100" style="margin-bottom: 5px;"
		  data-options="iconCls:'icon-print'">重新打印</a>
	</div>
</body>
</html>