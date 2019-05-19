function judgeLogin() {
	var userName = $("input[name=username]").val();
	if (!userName) {
		parent.$.messager.alert("提示", "操作员名称不能为空!");
		return;
	}
	var password = $("input[name=password]").val();
	if (!password) {
		parent.$.messager.alert("提示", "操作员密码不能为空!");
		return;
	}

	var url = "login.do";
	var data = "userName=" + userName + "&pwd=" + password
			+ "&timestamp=" + tempPara();

	var loginsuccessfun = function(data) {
		var da = eval(data);
		if (da.res == 0) {
			setCookie("userName", userName);
			location.replace("pages.do?para=base_layout_index");
		} else {
			$('input[name=password]').focus();
			parent.$.messager.alert("提示", da.msg);
		}
	};
	// 请求
	ajaxpost(url, data, loginsuccessfun);
};