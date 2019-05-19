<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欣视景自动分拣系统信息管理平台</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/base/css/table.css?v=1" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/query/querysortdetail.js?ver=5"></script>
<script type="text/javascript">
	
</script>
</head>
<body class="easyui-layout" data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<div id="toolbar" class="toolbar">
		<table>
			<tr>
				<td id="td_times" style="text-align: right; width:400px;" >
					&nbsp;&nbsp;开始时间:<input id="griddate_start"
					name="chardate_start" class="Wdate dpdate" style="width: 125px;" type="text"
					onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2012-01-01'})"
					onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2012-01-01'})">
					&nbsp;&nbsp;--&nbsp;&nbsp;截止时间:<input id="griddate_end"
					name="chardate_end" class="Wdate dpdate" style="width: 125px;" type="text"
					onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2012-01-01'})"
					onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2012-01-01'})">
				</td>
				<td style="width: 160px;">
					&nbsp;&nbsp;上传状态：<input id="update_flag" class="easyui-combobox" name="update_flag" style="width: 80px;">
				</td>
				<td style="width: 80px;"><a id="" href="javascript:gridLoad()" class="easyui-linkbutton jbtn75" data-options="iconCls:'icon-search' ">查询</a></td>
				<td style="color: red;font-weight:bold;font-size:16px;padding-left:10px;">注意：建议停机后查询，因数据量较大，以免影响正常生产；建议查询时间段不要跨度太大，影响生产以及查询效率！</td>
			</tr>
		</table>
	</div>
	<!--表单数据DataGrid-->
	<table id="dataList" class="easyui-datagrid" style="padding: 0; height: 100%;">
	</table>
</body>
</html>