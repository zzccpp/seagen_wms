<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>���Ӿ����ܿ���ϵͳ��Ϣ����ƽ̨</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/layout/index.js?v=7"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/base/css/table.css?v=0" />
</head>
<body>
	<!-- ���� -->
	<div id="index_layout">
		<!-- ����  -->
		<div data-options="region:'north'"
			style="height: 70px; border: 0px; overflow: hidden;" class="logo">
			<div style="height: 70px; border: 0px; overflow: hidden;">
				<div
					style="position: absolute; left: 5px; top: 5px; height: 70px; width: 230px; border: 0px;">
					<img
						src="${pageContext.request.contextPath}/resources/base/css/images/logo.png"
						style="position: absolute; left: 1px; top: 10px; height: 40px; width: 160px; cursor: pointer;"
						onclick="gotohome();"></img>
				</div>
				<div
					style="position: absolute; left: 210px; top: 22px; color: white; font-size: 32px; font-family: '΢���ź�';">
					<strong><span id="sitename">���ܿ���ϵͳ��Ϣ����ƽ̨</span></strong>
				</div>
				<div style="position: absolute; right: 5px; top: 5px;" id="index_layout_right">
					<div style="right: 0px; top: 0px;">
						<a href="javascript:void(0)" class="easyui-menubutton"
							data-options="plain:true, menu:'#layout_myself_pfMenu',iconCls:'status_online'"
							id="id-myself">��</a>
						<!-- <a href="javascript:void(0)" class="easyui-menubutton" data-options="plain:true, menu:'#layout_north_pfMenu',iconCls:'color_wheel'">����</a>  -->
						<a href="javascript:void(0)" class="easyui-menubutton"
							data-options="plain:true, menu:'#layout_supper_pfMenu',iconCls:'help'">����֧��</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="plain:true, iconCls:'bullet_key'"
							onclick="editPassWord()" id="edit">�޸�����</a> <a
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="plain:true, iconCls:'door_out'" onclick="logout()"
							id="logo">�˳�</a>
					</div>
				</div>
			</div>
		</div>

		<!-- �м���� ���ܵ����� -->
		<div data-options="region:'west', split: true" title="���ܵ���"
			style="width: 170px; overflow: hidden;">
			<div id="accordion-menun" class="easyui-accordion"
				data-options="fit:true,border:false, animate: false"
				style="padding: 0px; border: 0px; margin: 0px;"></div>
		</div>

		<!-- �м��Ҳ� ��ʾ�� FrameBorder=0 -->
		<div data-options="region:'center'" title="" style="overflow: hidden;">
			<div id="index_tabs" class="easyui-tabs" data-options="fit:true"
				style="overflow: hidden;">
				<!-- 				<div title="���ڹ�˾" class="panel-body" data-options="border:false, iconCls:'house'" -->
				<!-- 					style="overflow: hidden; border: 0px; pading: 0; margin: 0"> -->
				<!-- 					<iframe src="contrl.do?para=layout_about" -->
				<!-- 						style="border: 0; width: 100%; height: 99%; margin: 0; pading: 0; overflow: hidden;"> -->
				<!-- 					</iframe> -->
				<!-- 				</div> -->
			</div>
		</div>

		<!-- �ײ� -->
		<div data-options="region:'south'"
			style="font-size: 12px; height: 22px; text-align: center; line-height: 24px; overflow: hidden; margin-top: 2px;">
			&nbsp;&nbsp;@CopyRight&nbsp;by&nbsp;���������Ӿ��Ƽ��ɷ����޹�˾<input type="text" name="username" style="display:none" value="<%=session.getAttribute("userName")%>" /></div>
	</div>


	<!-- �Ҽ��˵�-->
	<div id="index_tabsMenu" class="easyui-menu"
		style="width: 120px; display: none;">
		<div title="refresh">ˢ��</div>
		<div class="menu-sep"></div>
		<div title="close">�رձ�ҳ</div>
		<div title="closeOther">�ر�����ҳ��</div>
		<div title="closeAll">�ر�����ҳ��</div>
	</div>

	<!-- ��������ѡ��˵�-->
	<!-- 	<div id="layout_north_pfMenu" style="width: 120px; display: none;"> -->
	<!-- 		<div onclick="changeThemeFun('default');" title="default">Ĭ��</div> -->
	<!-- 		<div onclick="changeThemeFun('gray');" title="gray">̼��</div> -->
	<!-- 		<div onclick="changeThemeFun('metro');" title="metro">����</div> -->
	<!-- 		<div onclick="changeThemeFun('black');" title="black">ī��</div> -->
	<!-- 	</div> -->
	<!-- �����ҵĲ˵�-->
	<div id="layout_myself_pfMenu" style="width: 120px; display: none;">
		<div onclick="editPassWord();" title="">�޸�����</div>
		<div onclick="logout();" title="">��&nbsp;&nbsp;&nbsp;&nbsp;��</div>
	</div>
	<!-- ��������֧�ֵĲ˵�-->
	<div id="layout_supper_pfMenu" style="width: 120px; display: none;">
		<div onclick="showsupperphone();" title="">����绰</div>
		<div onclick="gotohome();" title="">������ҳ</div>
	</div>



	<!-- �޸�����-->
	<%
		HttpSession sessions = request.getSession();
		Object userName = sessions.getAttribute("userName");
		Object userId = sessions.getAttribute("userId");
		Object SysDBVer = sessions.getAttribute("SysDBVer");
		Object SysFileVer = sessions.getAttribute("SysFileVer");
		Object ClientDBVer = sessions.getAttribute("ClientDBVer");
		Object ClientFileVer = sessions.getAttribute("ClientFileVer");
	%>
	<div id="restPassWord_dialog" class="easyui-dialog"
		style="width: 400px; height: 350px; padding: 3px" title="�޸�����"
		data-options="closed:true, modal:true, closable:true">
		<h1 style="text-align: center;">
			<span id="dialogtitle">�޸�����</span>
		</h1>
		<table class="table table-hover">
			<tr>
				<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
						class="fr">��½����</div></td>
				<td><input class="defaultInput easyui-validatebox " type="text"
					name="userName" id="userName" value="<%=userName%>"
					disabled="disabled" /> <!-- &nbsp;<span	style="color: red">�û����Ʋ������޸�</span> -->
				</td>
				<td style="display: none"><input name="userId"
					id="userId" value="<%=userId%>" /></td>
			</tr>
			<tr>
				<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
						class="fr">��½����</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="password" name="pwd" id="pwd"
					data-options="required:true,validType:['stringCheck','LimtLength[1,20]']" /></td>
			</tr>
			<tr>
				<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
						class="fr">�µ�����</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="password" name="npwd" id="npwd"
					data-options="required:true,validType:['stringCheck','LimtLength[1,20]']" /></td>
			</tr>
			<tr>
				<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
						class="fr">ȷ������</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="password" name="renpwd" id="renpwd" /></td>
			</tr>
			<tr>
				<td class="title"><div class="defaultTitle">��&#12288;&#12288;��</div></td>
				<td><a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" style="width: 80px"
					onclick="saveRestPassWord()">����</a>&nbsp;&nbsp;<a
					href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-undo'" style="width: 80px"
					onclick="closeWindow('restPassWord_dialog');">����</a></td>
			</tr>
		</table>
	</div>
	<div id="showVersion_dialog" class="easyui-dialog"
		style="width: 400px; height: 350px; padding: 3px" title="ϵͳ�汾"
		data-options="closed:true, modal:true, closable:true">
		<h1 style="text-align: center;">
			<span id="dialogtitle">ϵͳ�汾</span>
		</h1>
		<table class="table table-hover">
			<tr>
				<td style="width: 30%;"><div
						class="fr">ϵͳ���ݰ汾��</div></td>
				<td><input class="defaultInput easyui-validatebox " type="text" style="border:0"
					name="SysDBVer" id="SysDBVer" value="<%=SysDBVer%>"
					disabled="disabled" /> 
				</td>
			</tr>
			<tr>
				<td style="width: 30%;"><div
						class="fr">ϵͳ�ļ��汾��</div></td>
				<td><input class="defaultInput easyui-validatebox "  type="text" style="border:0" name="SysFileVer" id="SysFileVer"  value="<%=SysFileVer%>" disabled="disabled"/></td>
			</tr>
			<tr>
				<td style="width: 30%;"><div
						class="fr">�ͻ����ݰ汾��</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="text" style="border:0" name="ClientDBVer" id="ClientDBVer" value="<%=ClientDBVer%>" disabled="disabled" /></td>
			</tr>
			<tr>
				<td style="width: 30%;"><div
						class="fr">�ͻ��ļ��汾��</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="text" style="border:0" name="ClientFileVer" id="ClientFileVer" value="<%=ClientFileVer%>"  disabled="disabled" /></td>
			</tr>
			<tr>
				<td class="title"><div class="defaultTitle">��&#12288;&#12288;��</div></td>
				<td>&nbsp;&nbsp;<a
					href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-undo'" style="width: 100px"
					onclick="closeWindow('showVersion_dialog');">�ر�</a></td>
			</tr>
		</table>
	</div>
</body>
</html>