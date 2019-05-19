// 全局的ajax访问，处理ajax清求时sesion超时
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : ajaxcomplete
});


function ajaxpost(url, data, successfun, errorfun, async, timeout) {
	ajaxfun("POST", url, data, successfun, errorfun, async, timeout);
}

function ajaxget(url, data, successfun, errorfun, async, timeout) {
	ajaxfun("GET", url, data, successfun, errorfun, async, timeout);
};

function ajaxfun(type, url, data, successfun, errorfun, async, timeout) {
	var sucfun = (typeof successfun == "undefined") ? ajaxsuccess : successfun;
	var errfun = (typeof errorfun == "undefined") ? ajaxerror : errorfun;
	var asyncs = (typeof async == "undefined") ? true : async;
	var timeouts = (typeof timeout == "undefined") ? 180000 : timeout;
	timeouts = (timeouts < 2000) ? 2000 : timeouts;
	timeouts = (timeouts > 180000) ? 180000 : timeouts;

	$.ajax({
		type : type,
		async : asyncs,
		cache : false,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		url : url,
		data : data,
		timeout : timeouts,
		error : errfun,
		success : sucfun,
		complete : ajaxcomplete
	});
};


function ajaxcomplete(XMLHttpRequest, textStatus) {
	if (httprequesttimeout(XMLHttpRequest) == true)
		return;
}

function ajaxerror(XMLHttpRequest, textStatus, errorThrown) {
	if (httprequesttimeout(XMLHttpRequest) == true)
		return;
	try {
		parent.$.messager.alert('网络请求错误:', XMLHttpRequest.responseText
				+ " 刷新页面或重新登陆再试！");
		parent.$.messager.progress('close');
	} catch (e) {
	}
};

function httprequesttimeout(XMLHttpRequest) {
	var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
	if (sessionstatus == "timeout") {
		try {
			if (online == false)
				return;
			online = false;
			alert('由于你长时间没有操作，连接已断开！');
			// 如果超时就处理 ，指定要跳转的页面
			window.location.href = "";
		} catch (e) {
		}
		return true;
	}
	online = true;
	return false;
}

function ajaxsuccess(data) {
	parent.$.messager.show({
		msg : data,
		title : '提示'
	});
};

/**
 * JSON值转成请求参数
 */
function getPara(parameters) {
	var queryParameter = "timestamp=" + tempPara();
	var parameter = (typeof parameters == "undefined") ? null : parameters;
	if (parameter != null) {
		for (para in parameter) {
			queryParameter = queryParameter + "&" + para.toString() + "="
					+ parameter[para].toString();
		}
	}
	return queryParameter;
}