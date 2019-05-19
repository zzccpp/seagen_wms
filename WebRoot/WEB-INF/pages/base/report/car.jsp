<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>���Ӿ��Զ��ּ�ϵͳ��Ϣ����ƽ̨</title>
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
					&nbsp;&nbsp;��ʼʱ��:<input id="griddate_start"
					name="chardate_start" class="Wdate dpdate" style="width: 125px;" type="text"
					onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})"
					onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2018-01-01'})">
					&nbsp;&nbsp;--&nbsp;&nbsp;��ֹʱ��:<input id="griddate_end"
					name="chardate_end" class="Wdate dpdate" style="width: 125px;" type="text"
					onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})"
					onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'griddate_start\',{H:1});}'})">
				</td>
				<td id="td_day" style="text-align: right;display:none">
					&nbsp;&nbsp;��ѯʱ��:<input id="griddate_start_day"
					 class="Wdate dpdate" style="width: 88px;" type="text"
					 onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})"
					 onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'2018-01-01'})"
					>
				</td>
				<td id="td_month" style="text-align: right;display:none">
					&nbsp;&nbsp;��ѯʱ��:<input id="griddate_start_month"
					 class="Wdate dpdate" style="width: 80px;" type="text"
					 onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy-MM',minDate:'2018-01'})"
					>
				</td>
				<td id="td_year" style="text-align: right;display:none">
					&nbsp;&nbsp;��ѯʱ��:<input id="griddate_start_year"
					 class="Wdate dpdate" style="width: 55px;" type="text"
						onClick="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2018'})"
						onFocus="WdatePicker({autoShowQS:false,isShowClear:false,readOnly:true,dateFmt:'yyyy',minDate:'2018'})"
					>
				</td>
					 
				<td>
					&nbsp;&nbsp;ͳ�Ʒ�ʽ��<input  id="type" class="easyui-combobox" name="type"
					style="width: 80px;">
					&nbsp;&nbsp;��ʾ���ͣ�<input  id="more_type" class="easyui-combobox" name="more_type"
					style="width: 60px;">
					&nbsp;&nbsp;�㼶��<input  id="layerId" class="easyui-combobox" name="layerId"
					style="width: 65px;">
					<a id="" href="javascript:showgriddata()" class="easyui-linkbutton jbtn75" data-options="iconCls:'icon-search'">��ѯ</a>
					<a id="" href="javascript:excelExport()" style="margin-left: 15px;" class="easyui-linkbutton jbtn90"
					data-options="iconCls:'icon-excel-export'">Excel����</a>
				</td>
			</tr>
		</table>
	</div>
	<!--������DataGrid-->
	<table id="dataList" class="easyui-datagrid"
		style="padding: 0; height: 100%;"></table>
</body>
</html>