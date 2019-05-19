<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>欣视景智能控制系统信息管理平台</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/layout/index.js?v=7"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/base/css/table.css?v=0" />
</head>
<body>
	<!-- 布局 -->
	<div id="index_layout">
		<!-- 顶部  -->
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
					style="position: absolute; left: 210px; top: 22px; color: white; font-size: 32px; font-family: '微软雅黑';">
					<strong><span id="sitename">智能控制系统信息管理平台</span></strong>
				</div>
				<div style="position: absolute; right: 5px; top: 5px;" id="index_layout_right">
					<div style="right: 0px; top: 0px;">
						<a href="javascript:void(0)" class="easyui-menubutton"
							data-options="plain:true, menu:'#layout_myself_pfMenu',iconCls:'status_online'"
							id="id-myself">我</a>
						<!-- <a href="javascript:void(0)" class="easyui-menubutton" data-options="plain:true, menu:'#layout_north_pfMenu',iconCls:'color_wheel'">主题</a>  -->
						<a href="javascript:void(0)" class="easyui-menubutton"
							data-options="plain:true, menu:'#layout_supper_pfMenu',iconCls:'help'">技术支持</a>
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="plain:true, iconCls:'bullet_key'"
							onclick="editPassWord()" id="edit">修改密码</a> <a
							href="javascript:void(0)" class="easyui-linkbutton"
							data-options="plain:true, iconCls:'door_out'" onclick="logout()"
							id="logo">退出</a>
					</div>
				</div>
			</div>
		</div>

		<!-- 中间左侧 功能导航栏 -->
		<div data-options="region:'west', split: true" title="功能导航"
			style="width: 170px; overflow: hidden;">
			<div id="accordion-menun" class="easyui-accordion"
				data-options="fit:true,border:false, animate: false"
				style="padding: 0px; border: 0px; margin: 0px;"></div>
		</div>

		<!-- 中间右侧 显示栏 FrameBorder=0 -->
		<div data-options="region:'center'" title="" style="overflow: hidden;">
			<div id="index_tabs" class="easyui-tabs" data-options="fit:true"
				style="overflow: hidden;">
				<!-- 				<div title="关于公司" class="panel-body" data-options="border:false, iconCls:'house'" -->
				<!-- 					style="overflow: hidden; border: 0px; pading: 0; margin: 0"> -->
				<!-- 					<iframe src="contrl.do?para=layout_about" -->
				<!-- 						style="border: 0; width: 100%; height: 99%; margin: 0; pading: 0; overflow: hidden;"> -->
				<!-- 					</iframe> -->
				<!-- 				</div> -->
			</div>
		</div>

		<!-- 底部 -->
		<div data-options="region:'south'"
			style="font-size: 12px; height: 22px; text-align: center; line-height: 24px; overflow: hidden; margin-top: 2px;">
			&nbsp;&nbsp;@CopyRight&nbsp;by&nbsp;深圳市欣视景科技股份有限公司<input type="text" name="username" style="display:none" value="<%=session.getAttribute("userName")%>" /></div>
	</div>


	<!-- 右键菜单-->
	<div id="index_tabsMenu" class="easyui-menu"
		style="width: 120px; display: none;">
		<div title="refresh">刷新</div>
		<div class="menu-sep"></div>
		<div title="close">关闭本页</div>
		<div title="closeOther">关闭其它页面</div>
		<div title="closeAll">关闭所有页面</div>
	</div>

	<!-- 顶部主题选择菜单-->
	<!-- 	<div id="layout_north_pfMenu" style="width: 120px; display: none;"> -->
	<!-- 		<div onclick="changeThemeFun('default');" title="default">默认</div> -->
	<!-- 		<div onclick="changeThemeFun('gray');" title="gray">碳灰</div> -->
	<!-- 		<div onclick="changeThemeFun('metro');" title="metro">银灰</div> -->
	<!-- 		<div onclick="changeThemeFun('black');" title="black">墨黑</div> -->
	<!-- 	</div> -->
	<!-- 顶部我的菜单-->
	<div id="layout_myself_pfMenu" style="width: 120px; display: none;">
		<div onclick="editPassWord();" title="">修改密码</div>
		<div onclick="logout();" title="">退&nbsp;&nbsp;&nbsp;&nbsp;出</div>
	</div>
	<!-- 顶部技术支持的菜单-->
	<div id="layout_supper_pfMenu" style="width: 120px; display: none;">
		<div onclick="showsupperphone();" title="">服务电话</div>
		<div onclick="gotohome();" title="">官网主页</div>
	</div>



	<!-- 修改密码-->
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
		style="width: 400px; height: 350px; padding: 3px" title="修改密码"
		data-options="closed:true, modal:true, closable:true">
		<h1 style="text-align: center;">
			<span id="dialogtitle">修改密码</span>
		</h1>
		<table class="table table-hover">
			<tr>
				<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
						class="fr">登陆名称</div></td>
				<td><input class="defaultInput easyui-validatebox " type="text"
					name="userName" id="userName" value="<%=userName%>"
					disabled="disabled" /> <!-- &nbsp;<span	style="color: red">用户名称不可以修改</span> -->
				</td>
				<td style="display: none"><input name="userId"
					id="userId" value="<%=userId%>" /></td>
			</tr>
			<tr>
				<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
						class="fr">登陆密码</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="password" name="pwd" id="pwd"
					data-options="required:true,validType:['stringCheck','LimtLength[1,20]']" /></td>
			</tr>
			<tr>
				<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
						class="fr">新的密码</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="password" name="npwd" id="npwd"
					data-options="required:true,validType:['stringCheck','LimtLength[1,20]']" /></td>
			</tr>
			<tr>
				<td style="width: 30%; padding-top: 5px; padding-bottom: 5px;"><div
						class="fr">确认密码</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="password" name="renpwd" id="renpwd" /></td>
			</tr>
			<tr>
				<td class="title"><div class="defaultTitle">操&#12288;&#12288;作</div></td>
				<td><a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" style="width: 80px"
					onclick="saveRestPassWord()">保存</a>&nbsp;&nbsp;<a
					href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-undo'" style="width: 80px"
					onclick="closeWindow('restPassWord_dialog');">返回</a></td>
			</tr>
		</table>
	</div>
	<div id="showVersion_dialog" class="easyui-dialog"
		style="width: 400px; height: 350px; padding: 3px" title="系统版本"
		data-options="closed:true, modal:true, closable:true">
		<h1 style="text-align: center;">
			<span id="dialogtitle">系统版本</span>
		</h1>
		<table class="table table-hover">
			<tr>
				<td style="width: 30%;"><div
						class="fr">系统数据版本号</div></td>
				<td><input class="defaultInput easyui-validatebox " type="text" style="border:0"
					name="SysDBVer" id="SysDBVer" value="<%=SysDBVer%>"
					disabled="disabled" /> 
				</td>
			</tr>
			<tr>
				<td style="width: 30%;"><div
						class="fr">系统文件版本号</div></td>
				<td><input class="defaultInput easyui-validatebox "  type="text" style="border:0" name="SysFileVer" id="SysFileVer"  value="<%=SysFileVer%>" disabled="disabled"/></td>
			</tr>
			<tr>
				<td style="width: 30%;"><div
						class="fr">客户数据版本号</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="text" style="border:0" name="ClientDBVer" id="ClientDBVer" value="<%=ClientDBVer%>" disabled="disabled" /></td>
			</tr>
			<tr>
				<td style="width: 30%;"><div
						class="fr">客户文件版本号</div></td>
				<td><input class="defaultInput easyui-validatebox "
					type="text" style="border:0" name="ClientFileVer" id="ClientFileVer" value="<%=ClientFileVer%>"  disabled="disabled" /></td>
			</tr>
			<tr>
				<td class="title"><div class="defaultTitle">操&#12288;&#12288;作</div></td>
				<td>&nbsp;&nbsp;<a
					href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-undo'" style="width: 100px"
					onclick="closeWindow('showVersion_dialog');">关闭</a></td>
			</tr>
		</table>
	</div>
</body>
</html>