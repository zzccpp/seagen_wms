<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>���Ӿ����ܿ���ϵͳ��Ϣ����ƽ̨</title>
<!-- �������õ���js���ļ� -->
<jsp:include page="/WEB-INF/pages/base/common/common.jsp"></jsp:include>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/base/css/login.css?v=0" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/base/js/layout/login.js?v=4"></script>
<script type="text/javascript">
	if (window != top) //��ˢ��ǰ��ifream��� 
		top.location.href = location.href;

	$(document).ready(function() {
		$.focusblur("#input-u");
		$.focusblur("#input-p");
		$.focusblur("#input-c");
		var userName = getCookie("userName");
		if (userName != null)
			$("input[name=username]").val(userName);
	});

	document.onkeydown = function() {
		if (event.keyCode == 13) {
			judgeLogin();
		}
	};
</script>
</head>

<body class="login_background">
	<div class="login_index">
		<div class="login">
			<div class="login_yun1"></div>
			<div class="login_form" id="pli">
				<form action="" method="post" id="form">
					<div>
						<span id="biaoti"><strong>���ܿ���ϵͳ��Ϣ����ƽ̨</strong></span>
					</div>
					<ul>
						<li><span></span><input type="text" id="input-u"
							name="username" class="textinput" value="��  ��" maxlength="16"
							onkeyup="this.value=value.replace(/[\W]/g,'')" /></li>
						<li><span></span><input type="password" id="input-p"
							name="password" class="textinput" value="" maxlength="16"
							onkeyup="this.value=value.replace(/[\W]/g,'') " /></li>
						<li><span></span><input type="button" id="form_but"
							value="����ϵͳ" onclick="judgeLogin();" /></li>
					</ul>
					<div>
						<span id="company">���������Ӿ��Ƽ��ɷ����޹�˾</span>
					</div>

				</form>
			</div>
		</div>
	</div>
</body>
</html>
