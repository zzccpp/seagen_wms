<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欣视景自动分拣系统信息管理平台</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/report/supply.js?v=6"></script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?v=1" />
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<div class="supply_chart"  style="height: 250px; border: 0px;">
		<div id="toolbar1" class="toolbar">
			<table>
				<tr>
					<td style="text-align: right;">&nbsp;&nbsp;开始时间:<input
						id="stime" name="time_start" class="Wdate dpdate"
						style="width: 125px;" type="text"
						onClick="WdatePicker({readOnly:true,isShowClear:false,highLineWeekDay:true,dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'etime\',{H:-1})}',minDate:'#F{$dp.$D(\'etime\',{d:-3})}'})">
						&nbsp;&nbsp;--&nbsp;&nbsp;截止时间:<input id="etime" name="time_end"
						class="Wdate dpdate" style="width: 125px;" type="text"
						onClick="WdatePicker({isShowClear:false,readOnly:true,highLineWeekDay:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'stime\',{H:+1})}',maxDate:'#F{$dp.$D(\'stime\',{d:+3})}'})">
					</td>
					<td>
					&nbsp;&nbsp;层级：<input  id="layerId_1" class="easyui-combobox" name="layerId"
					style="width: 60px;">
					<a id="" href="javascript:showgriddata(1)"
						class="easyui-linkbutton jbtn75"
						data-options="iconCls:'icon-search'">查询</a> <a id=""
						href="javascript:excelExport(1)" style="margin-left: 15px;"
						class="easyui-linkbutton jbtn90"
						data-options="iconCls:'icon-excel-export'">Excel导出</a></td>
					<td><input type="button" onclick="type_click(2)" value="切换到报表">
					</td>
				</tr>
			</table>
		</div>

		<!--表单数据DataGrid-->
		<table id="chart_dataList" class="easyui-datagrid easyui-panel"
			style="padding: 0; display: block; height: 100%; border: 0px;"></table>
	</div>
	<div class="supply_chart" style="float: left; width: 50%;height: 70%; ">
		<div class="easyui-panel" id="lineChart"
			data-options="fit:true,title:'连续60分钟峰值[点击需要显示的导入台]'" data-options="border:false">
			<%--左边的明细 --%>
		</div>
	</div>
	<div class="supply_chart" style="float: left; width: 50%; height: 70%;">
		<div class="easyui-panel" data-options="fit:true,title:'导入台供件总量所占比'"
			data-options="border:false">
			<%--右边的图标 --%>
			<div id="pieChart"
				style="min-width: 400px; height: 400px; border: 0px;"></div>
		</div>
	</div>
	<div  id="supply_table" style="float: left;width: 100%; height: 100%; border: 0px; display:none">
		<div id="toolbar2" class="toolbar">
			<table>
				<tr>
					<td id="td_times" style="text-align: right;">
						&nbsp;&nbsp;开始时间:<input id="griddate_start" name="chardate_start"
						class="Wdate dpdate" style="width: 125px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})">
						&nbsp;&nbsp;--&nbsp;&nbsp;截止时间:<input id="griddate_end"
						name="chardate_end" class="Wdate dpdate" style="width: 125px;"
						type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})">
					</td>
					<td id="td_day" style="text-align: right; display: none">
						&nbsp;&nbsp;查询时间:<input id="griddate_start_day"
						class="Wdate dpdate" style="width: 88px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})">
					</td>
					<td id="td_month" style="text-align: right; display: none">
						&nbsp;&nbsp;查询时间:<input id="griddate_start_month"
						class="Wdate dpdate" style="width: 80px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})">
					</td>
					<td id="td_year" style="text-align: right; display: none">
						&nbsp;&nbsp;查询时间:<input id="griddate_start_year"
						class="Wdate dpdate" style="width: 55px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2018'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2018'})">
					</td>

					<td>&nbsp;&nbsp;统计方式：<input id="type" class="easyui-combobox"
						name="type" style="width: 80px;"> <!-- <option value="0">时间段</option>
							<option value="1">按天</option>
							<option value="2">按月</option>
							<option value="3">按年</option> --> &nbsp;&nbsp;显示类型：<input
						id="more_type" class="easyui-combobox" name="more_type"
						style="width: 60px;"> <!-- <a id="hideStatus" href="javascript:setHideStatus()" style="margin-left: 5px;" class="easyui-linkbutton jbtn90">详细</a> -->
						&nbsp;&nbsp;层级：<input  id="layerId_2" class="easyui-combobox" name="layerId"
					style="width: 60px;">
						<a id="" href="javascript:showgriddata(2)"
						class="easyui-linkbutton jbtn75"
						data-options="iconCls:'icon-search'">查询</a> <a id=""
						href="javascript:excelExport(2)" style="margin-left: 15px;"
						class="easyui-linkbutton jbtn90"
						data-options="iconCls:'icon-excel-export'">Excel导出</a>
					</td>
					<td><input type="button" onclick="type_click(1)" value="切换到图形">
					</td>
				</tr>
			</table>
		</div>
		<!--表单数据DataGrid-->
		<table id="table_dataList" class="easyui-datagrid"
		style="padding: 0; height: 100%;">
		</table>
		
	</div>
</body>
</html>