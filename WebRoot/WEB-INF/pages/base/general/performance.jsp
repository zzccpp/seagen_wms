<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>���Ӿ��Զ��ּ�ϵͳ��Ϣ����ƽ̨</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/general/performance.js?ver=11"></script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/table.css?v=1" />
</head>
<body class="easyui-layout"
	data-options="fit:true, border:false, region:'center'"
	oncontextmenu="window.event.returnValue=false">
	<div class="charbar">
		<table>
			<tr>
				<td style="text-align: right; padding-left: 20px;"><input
					type="checkbox" id="vague" name="vague"
					onclick="checkboxOnclick(this)" />�Զ�&nbsp;&nbsp;&nbsp;&nbsp;<a
					id="" href="javascript:showdata()" class="easyui-linkbutton jbtn75"
					data-options="iconCls:'icon-search'">��ѯ</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target="_blank" class="easyui-linkbutton jbtn75"
					data-options="iconCls:'icon-search'" href="http://localhost:8080/seagen_wms/druid">���ݿ���</a></td>
			</tr>
		</table>
	</div>
	<div id="data_panel" class="easyui-panel" title="ҵ�����"
		style="width: 100%; height: 50%; background: #fafafa;"
		data-options="closable:false, collapsible:false,minimizable:false,maximizable:false">
	</div>
	
	<div id="runtimer_panel" class="easyui-panel" title="���ܼ���"
		style="width: 100%; height: 49%; background: #fafafa;"
		data-options="closable:false, collapsible:false,minimizable:false,maximizable:false">
	</div>
</body>
</html>