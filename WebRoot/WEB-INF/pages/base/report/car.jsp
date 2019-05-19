<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欣视景自动分拣系统信息管理平台</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/report/car.js?b=13"></script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?dd=4" />
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<div id="toolbar" class="toolbar">
		<table>
			<tr>
				<td id="td_times" style="text-align: right;">
					&nbsp;&nbsp;开始时间:<input id="griddate_start"
					name="chardate_start" class="Wdate dpdate" style="width: 125px;" type="text"
					onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})"
					onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})">
					&nbsp;&nbsp;--&nbsp;&nbsp;截止时间:<input id="griddate_end"
					name="chardate_end" class="Wdate dpdate" style="width: 125px;" type="text"
					onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})"
					onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})">
				</td>
				<td id="td_day" style="text-align: right;display:none">
					&nbsp;&nbsp;查询时间:<input id="griddate_start_day"
					 class="Wdate dpdate" style="width: 88px;" type="text"
					 onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})"
					 onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})"
					>
				</td>
				<td id="td_month" style="text-align: right;display:none">
					&nbsp;&nbsp;查询时间:<input id="griddate_start_month"
					 class="Wdate dpdate" style="width: 80px;" type="text"
					 onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})"
					>
				</td>
				<td id="td_year" style="text-align: right;display:none">
					&nbsp;&nbsp;查询时间:<input id="griddate_start_year"
					 class="Wdate dpdate" style="width: 55px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2018'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2018'})"
					>
				</td>
					 
				<td>
					&nbsp;&nbsp;统计方式：<input  id="type" class="easyui-combobox" name="type"
					style="width: 80px;">
					&nbsp;&nbsp;显示类型：<input  id="more_type" class="easyui-combobox" name="more_type"
					style="width: 60px;">
					&nbsp;&nbsp;层级：<input  id="layerId" class="easyui-combobox" name="layerId"
					style="width: 65px;">
					<a id="" href="javascript:showgriddata()" class="easyui-linkbutton jbtn75" data-options="iconCls:'icon-search'">查询</a>
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