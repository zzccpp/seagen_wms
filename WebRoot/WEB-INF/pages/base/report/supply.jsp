<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>���Ӿ��Զ��ּ�ϵͳ��Ϣ����ƽ̨</title>
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
					<td style="text-align: right;">&nbsp;&nbsp;��ʼʱ��:<input
						id="stime" name="time_start" class="Wdate dpdate"
						style="width: 125px;" type="text"
						onClick="WdatePicker({readOnly:true,isShowClear:false,highLineWeekDay:true,dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'etime\',{H:-1})}',minDate:'#F{$dp.$D(\'etime\',{d:-3})}'})">
						&nbsp;&nbsp;--&nbsp;&nbsp;��ֹʱ��:<input id="etime" name="time_end"
						class="Wdate dpdate" style="width: 125px;" type="text"
						onClick="WdatePicker({isShowClear:false,readOnly:true,highLineWeekDay:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'stime\',{H:+1})}',maxDate:'#F{$dp.$D(\'stime\',{d:+3})}'})">
					</td>
					<td>
					&nbsp;&nbsp;�㼶��<input  id="layerId_1" class="easyui-combobox" name="layerId"
					style="width: 60px;">
					<a id="" href="javascript:showgriddata(1)"
						class="easyui-linkbutton jbtn75"
						data-options="iconCls:'icon-search'">��ѯ</a> <a id=""
						href="javascript:excelExport(1)" style="margin-left: 15px;"
						class="easyui-linkbutton jbtn90"
						data-options="iconCls:'icon-excel-export'">Excel����</a></td>
					<td><input type="button" onclick="type_click(2)" value="�л�������">
					</td>
				</tr>
			</table>
		</div>

		<!--������DataGrid-->
		<table id="chart_dataList" class="easyui-datagrid easyui-panel"
			style="padding: 0; display: block; height: 100%; border: 0px;"></table>
	</div>
	<div class="supply_chart" style="float: left; width: 50%;height: 70%; ">
		<div class="easyui-panel" id="lineChart"
			data-options="fit:true,title:'����60���ӷ�ֵ[�����Ҫ��ʾ�ĵ���̨]'" data-options="border:false">
			<%--��ߵ���ϸ --%>
		</div>
	</div>
	<div class="supply_chart" style="float: left; width: 50%; height: 70%;">
		<div class="easyui-panel" data-options="fit:true,title:'����̨����������ռ��'"
			data-options="border:false">
			<%--�ұߵ�ͼ�� --%>
			<div id="pieChart"
				style="min-width: 400px; height: 400px; border: 0px;"></div>
		</div>
	</div>
	<div  id="supply_table" style="float: left;width: 100%; height: 100%; border: 0px; display:none">
		<div id="toolbar2" class="toolbar">
			<table>
				<tr>
					<td id="td_times" style="text-align: right;">
						&nbsp;&nbsp;��ʼʱ��:<input id="griddate_start" name="chardate_start"
						class="Wdate dpdate" style="width: 125px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})">
						&nbsp;&nbsp;--&nbsp;&nbsp;��ֹʱ��:<input id="griddate_end"
						name="chardate_end" class="Wdate dpdate" style="width: 125px;"
						type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})">
					</td>
					<td id="td_day" style="text-align: right; display: none">
						&nbsp;&nbsp;��ѯʱ��:<input id="griddate_start_day"
						class="Wdate dpdate" style="width: 88px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})">
					</td>
					<td id="td_month" style="text-align: right; display: none">
						&nbsp;&nbsp;��ѯʱ��:<input id="griddate_start_month"
						class="Wdate dpdate" style="width: 80px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})">
					</td>
					<td id="td_year" style="text-align: right; display: none">
						&nbsp;&nbsp;��ѯʱ��:<input id="griddate_start_year"
						class="Wdate dpdate" style="width: 55px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2018'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2018'})">
					</td>

					<td>&nbsp;&nbsp;ͳ�Ʒ�ʽ��<input id="type" class="easyui-combobox"
						name="type" style="width: 80px;"> <!-- <option value="0">ʱ���</option>
							<option value="1">����</option>
							<option value="2">����</option>
							<option value="3">����</option> --> &nbsp;&nbsp;��ʾ���ͣ�<input
						id="more_type" class="easyui-combobox" name="more_type"
						style="width: 60px;"> <!-- <a id="hideStatus" href="javascript:setHideStatus()" style="margin-left: 5px;" class="easyui-linkbutton jbtn90">��ϸ</a> -->
						&nbsp;&nbsp;�㼶��<input  id="layerId_2" class="easyui-combobox" name="layerId"
					style="width: 60px;">
						<a id="" href="javascript:showgriddata(2)"
						class="easyui-linkbutton jbtn75"
						data-options="iconCls:'icon-search'">��ѯ</a> <a id=""
						href="javascript:excelExport(2)" style="margin-left: 15px;"
						class="easyui-linkbutton jbtn90"
						data-options="iconCls:'icon-excel-export'">Excel����</a>
					</td>
					<td><input type="button" onclick="type_click(1)" value="�л���ͼ��">
					</td>
				</tr>
			</table>
		</div>
		<!--������DataGrid-->
		<table id="table_dataList" class="easyui-datagrid"
		style="padding: 0; height: 100%;">
		</table>
		
	</div>
</body>
</html>