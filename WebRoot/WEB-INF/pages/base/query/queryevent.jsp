<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欣视景自动分拣系统信息管理平台</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?v=1" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/query/queryevent.js?v=7"></script>
<script type="text/javascript">
	
</script>
</head>
<body class="easyui-layout"
	data-options="region:'center', fit:true, border:false"
	oncontextmenu="window.event.returnValue=false">
	<div id="toolbar" class="toolbar">
		<table>
			<tr>
				<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;起始日期：</td>
				<td><input id="griddate_start" name="griddate_start" class="Wdate dpdate" style="width: 150px;" 
					type="text" onClick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2018-01-01',maxDate:'#F{$dp.$D(\'griddate_end\');}'})"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2018-01-01',maxDate:'#F{$dp.$D(\'griddate_end\');}'})">
					--  截止时间:<input id="griddate_end"
					name="chardate_end" class="Wdate dpdate" style="width: 150px;" type="text"
					onClick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'griddate_start\');}',maxDate:'%y-%M-%d %H:%m:%s'})"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'griddate_start\');}',maxDate:'%y-%M-%d %H:%m:%s'})">
					
					&nbsp;&nbsp;事件类型：<input  id="event_type" class="easyui-combobox" name="event_type"
					style="width: 80px;">
					<input type="checkbox" id="sort" name="sort"
					onclick="checkboxOnclick(this)" />降序 <a id=""
					href="javascript:gridLoad()" class="easyui-linkbutton jbtn75"
					data-options="iconCls:'icon-search'">查询</a>
					<a id="" href="javascript:excelExport()" style="margin-left: 50px;" class="easyui-linkbutton jbtn90"
					data-options="iconCls:'icon-excel-export'">Excel导出</a></td>
			</tr>
		</table>
	</div>
	<!--表单数据DataGrid-->
	<table id="dataList" class="easyui-datagrid"
		style="padding: 0; height: 100%;">
		<thead>
			<tr>
				<th data-options="halign:'center',field:'f_recno',width:40">序号</th>
				<th data-options="halign:'center',field:'event_id',width:50">代号</th>
				<th data-options="halign:'center',field:'event_level',width:80">级别</th>
				<th data-options="halign:'center',field:'event_type',width:110">类型</th>
				<th data-options="halign:'center',field:'event_name',width:120">名称</th>
				<th data-options="halign:'center',field:'event_val',width:200">内容</th>		
				<th data-options="halign:'center',field:'event_status',align:'center',width:50,formatter:formatEventUploadStatus">上传状态</th>				
				<th data-options="halign:'center',field:'upload_time',align:'center',width:100">上传时间</th>			
				<th data-options="halign:'center',field:'event_mark',width:150">备注</th>
				<th data-options="halign:'center',field:'create_time',width:100">生成时间</th>
			</tr>
		</thead>
	</table>
</body>
</html>