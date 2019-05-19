<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欣视景自动分拣系统信息管理平台</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/report/sum.js?b=0"></script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?dd=0" />
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<div id="toolbar" class="toolbar">
		<table>
			<tr>
				<td id="td_times" style="text-align: right;">
					&nbsp;&nbsp;开始时间：<input id="griddate_start"
					name="chardate_start" class="Wdate dpdate" style="width: 130px;" type="text"
					onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})"
					onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})">
					&nbsp;&nbsp;--&nbsp;&nbsp;截止时间：<input id="griddate_end"
					name="chardate_end" class="Wdate dpdate" style="width: 130px;" type="text"
					onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})"
					onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})">
				</td>
				<td id="td_day" style="text-align: right;display:none">
					&nbsp;&nbsp;查询时间：<input id="griddate_start_day"
					 class="Wdate dpdate" style="width: 88px;" type="text"
					 onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})"
					 onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})"
					>
				</td>
				<td id="td_month" style="text-align: right;display:none">
					&nbsp;&nbsp;查询时间：<input id="griddate_start_month"
					 class="Wdate dpdate" style="width: 80px;" type="text"
					 onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})"
					>
				</td>
				<td id="td_year" style="text-align: right;display:none">
					&nbsp;&nbsp;查询时间：<input id="griddate_start_year"
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
						<!-- <option value="0">时间段</option>
						<option value="1">按天</option>
						<option value="2">按月</option>
						<option value="3">按年</option> -->
					<!-- <a id="hideStatus" href="javascript:setHideStatus()" style="margin-left: 5px;" class="easyui-linkbutton jbtn90">详细</a>-->
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