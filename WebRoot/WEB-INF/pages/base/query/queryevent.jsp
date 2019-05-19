<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>���Ӿ��Զ��ּ�ϵͳ��Ϣ����ƽ̨</title>
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
				<td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;��ʼ���ڣ�</td>
				<td><input id="griddate_start" name="griddate_start" class="Wdate dpdate" style="width: 150px;" 
					type="text" onClick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2018-01-01',maxDate:'#F{$dp.$D(\'griddate_end\');}'})"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2018-01-01',maxDate:'#F{$dp.$D(\'griddate_end\');}'})">
					--  ��ֹʱ��:<input id="griddate_end"
					name="chardate_end" class="Wdate dpdate" style="width: 150px;" type="text"
					onClick="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'griddate_start\');}',maxDate:'%y-%M-%d %H:%m:%s'})"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'griddate_start\');}',maxDate:'%y-%M-%d %H:%m:%s'})">
					
					&nbsp;&nbsp;�¼����ͣ�<input  id="event_type" class="easyui-combobox" name="event_type"
					style="width: 80px;">
					<input type="checkbox" id="sort" name="sort"
					onclick="checkboxOnclick(this)" />���� <a id=""
					href="javascript:gridLoad()" class="easyui-linkbutton jbtn75"
					data-options="iconCls:'icon-search'">��ѯ</a>
					<a id="" href="javascript:excelExport()" style="margin-left: 50px;" class="easyui-linkbutton jbtn90"
					data-options="iconCls:'icon-excel-export'">Excel����</a></td>
			</tr>
		</table>
	</div>
	<!--������DataGrid-->
	<table id="dataList" class="easyui-datagrid"
		style="padding: 0; height: 100%;">
		<thead>
			<tr>
				<th data-options="halign:'center',field:'f_recno',width:40">���</th>
				<th data-options="halign:'center',field:'event_id',width:50">����</th>
				<th data-options="halign:'center',field:'event_level',width:80">����</th>
				<th data-options="halign:'center',field:'event_type',width:110">����</th>
				<th data-options="halign:'center',field:'event_name',width:120">����</th>
				<th data-options="halign:'center',field:'event_val',width:200">����</th>		
				<th data-options="halign:'center',field:'event_status',align:'center',width:50,formatter:formatEventUploadStatus">�ϴ�״̬</th>				
				<th data-options="halign:'center',field:'upload_time',align:'center',width:100">�ϴ�ʱ��</th>			
				<th data-options="halign:'center',field:'event_mark',width:150">��ע</th>
				<th data-options="halign:'center',field:'create_time',width:100">����ʱ��</th>
			</tr>
		</thead>
	</table>
</body>
</html>