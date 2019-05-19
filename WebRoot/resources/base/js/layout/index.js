var layout_west_tree;
var canSaveSelMenuIDCookie = false;
var index_tabs;
var index_tabsMenu;
var index_layout;
var loadmenuncount = 0;
var userName = getCookie("userName");

$(function() {
	
	userName = userName == null ? "NULL" : userName;
	$('#id-myself').menubutton({
		text : userName
	});
	if(userName == "NULL"){
		userName = $("input[name=username]").val(); 
	}
//	alert(userName);
	if(userName == 'wcs_user'){
		$("#index_layout_right").css("display","none");
	} else {
		$("#index_layout_right").css("display","block");
	}

	// 重新输入密码校验
	$('#renpwd').validatebox({
		required : true,
		validType : 'eqPwd["#npwd"]',
		missingMessage : "请再次输入新密码"
	});

	// 布局
	index_layout = $('#index_layout').layout({
		fit : true
	});

	// 选项卡
	index_tabs = $('#index_tabs').tabs({
		fit : true,
		border : false,
		onContextMenu : function(e, title) {
			e.preventDefault();
			index_tabsMenu.menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data('tabTitle', title);
		}
	});
	// 右键菜单
	index_tabsMenu = $('#index_tabsMenu').menu(
			{
				onShow : function() {
				},
				onClick : function(item) {
					var curTabTitle = $(this).data('tabTitle');
					var type = $(item.target).attr('title');

					if (type === 'refresh') {
						var panel = index_tabs.tabs('getTab', curTabTitle);
						var content = panel.panel('options').content;// 获取面板内容
						index_tabs.tabs('update', {// 更新
							tab : panel,
							options : content
						});
						return;
					}

					if (type === 'close') {
						var t = index_tabs.tabs('getTab', curTabTitle);
						if (t.panel('options').closable)
							index_tabs.tabs('close', curTabTitle);
						return;
					}

					var allTabs = index_tabs.tabs('tabs');
					var closeTabsTitle = [];

					$.each(allTabs, function() {
						var opt = $(this).panel('options');
						if (opt.closable && opt.title != curTabTitle
								&& type === 'closeOther') {
							closeTabsTitle.push(opt.title);
						} else if (opt.closable && type === 'closeAll') {
							closeTabsTitle.push(opt.title);
						}
					});

					for (var i = 0; i < closeTabsTitle.length; i++) {
						index_tabs.tabs('close', closeTabsTitle[i]);
					}
				}
			});

	// 加载主菜单
	loadMenunList();
	// changeThemeFun("");
});

function setsitename(sitename) {
	$('#sitename').html(sitename);
}

function changeThemeFun(themeName) {
	themeName = "default";
	if ($.cookie('easyuiThemeName')) {
		$('#layout_north_pfMenu').menu(
				'setIcon',
				{
					target : $('#layout_north_pfMenu div[title='
							+ $.cookie('easyuiThemeName') + ']')[0],
					iconCls : 'emptyIcon'
				});
	}

	$('#layout_north_pfMenu').menu('setIcon', {
		target : $('#layout_north_pfMenu div[title=' + themeName + ']')[0],
		iconCls : 'tick'
	});

	var $easyuiTheme = $('#easyuiTheme');
	// alert("easyuiTheme:" + easyuiTheme);
	var url = $easyuiTheme.attr('href');
	// alert("url:" + url);
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName
			+ '/easyui.css';
	// alert("css:" + href);
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for (var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			try {
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			} catch (e) {
				try {
					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
				} catch (e) {
				}
			}
		}
	}

	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});

};

function logout() {
	parent.$.messager.confirm('提示', '确定退出系统么?', function(r) {
		if (r) {
			var successfun = function(data) {
				location.replace("login.jsp");
			};
			ajaxpost("logout.do", null, successfun, successfun);
		}
	});
};

function editPassWord() {
	// addTab({
	// url : 'stadium.do?para=system_modpassword',
	// title : '修改密码',
	// iconCls : 'shape_square'
	// });
	$("input[name=pwd]").val("");
	$("input[name=npwd]").val("");
	$('input[name=renpwd]').val("");
	openWindow("restPassWord_dialog");
}
//展示系统版本信息
function showVersion() {
	openWindow("showVersion_dialog");
}


function showsupperphone() {
	parent.$.messager.alert("服务", "技术支持电话:  400-825-1225");
}

function gotohome() {
	window.open("http://www.seagen.cn");
}

function saveRestPassWord() {

}

function loadMenunListSuccess(data) {
	var memudata = null;
	var jData = null;
	var jDataLen = 0;

	try {
		memudata = (typeof data == "undefined") ? null : data;
		jData = eval(memudata);
		jDataLen = jData.length;
	} catch (e) {
		alert("界面初使化时不完全，请刷新本页面");
		return;
	}

	var menuIdList = new Array();
	// 获取主菜单
	for (var i = 0; i < jDataLen; i++) {
		if (jData[i].pid == 0) {
			menuIdList.push(jData[i]);
		}
	}

	// 主菜单排序
	var temp = null;
	for (var i = menuIdList.length - 1; i >= 0; i--) {
		for (var j = menuIdList.length - 1; j >= 0; j--) {
			if (menuIdList[i].index < menuIdList[j].index) {
				temp = menuIdList[i];
				menuIdList[i] = menuIdList[j];
				menuIdList[j] = temp;
			}
		}
	}

	// 加载主菜单
	for (var i = menuIdList.length - 1; i >= 0; i--) {
		$('#accordion-menun').accordion(
				'add',
				{
					title : menuIdList[i].name,
					selected : true,
					iconCls : menuIdList[i].iconCls,
					content : '<ul class="easyui-tree" id="layout_main_menu_'
							+ menuIdList[i].key + '"></ul>'
				});
	}

	// 加载子菜单 目前设计子菜单只有两级
	var popupMenun = new Array();
	for (var i = menuIdList.length - 1; i >= 0; i--) {
		popupMenun.length = 0;
		for (var j = 0; j < jDataLen; j++) {
			// 第一级
			if (jData[j].pid == menuIdList[i].key) {
				popupMenun.push(jData[j]);
				// 第二级
				for (var m = 0; m < jDataLen; m++) {
					if (jData[m].pid == jData[j].key)
						popupMenun.push(jData[m]);
				}
			}
		}
		if (popupMenun.length == 0)
			continue;

		// 添加到子菜单中 在此处直接写不成功，
		// 最大的可能性是函数封装后popupMenun以字符串点的值解决了数据转换的问题
		loadPopupMenun(popupMenun, menuIdList[i].key);
	}

	// 选择第2个
	$('#accordion-menun').accordion('select', 1);
	// 取最后操作选择的菜单ID
	$('#accordion-menun').accordion('select', parseInt($.cookie('menunIndex')));
	canSaveSelMenuIDCookie = true;
	// 添加主页
	addhouse();
}

function loadPopupMenun(msg, key) {
	layout_west_tree = $('#layout_main_menu_' + key).tree({
		data : msg,
		lines : true,
		parentField : "pid",
		textFiled : "name",
		idFiled : "key",
		onClick : function(node) {
			if (node.attributes && node.attributes.url) {
				var url = node.attributes.url;
				// alert(url);
				if (isNotAddTab(url)) {
					if (url == "#editPassWord"){
						editPassWord();
					}
					if (url == "#version"){
						showVersion();
					}
				}
				else{
					addTab({
						url : url,
						title : node.text,
						iconCls : node.iconCls
					});
				}
			}
		}
	});
}

// 这里判断是否不用增选项卡，只是当前页面操作
function isNotAddTab(url) {
	if (url == "#editPassWord") {
		return true;
	} else if (url == "#version") {
		return true;
	} else
		return false;
}

// 增加选项卡
function addTab(params) {
	var iframe = '<iframe src="'
			+ params.url
			+ '" frameborder="0" style="border:0; width:100%;height:99%;overflow: hidden;"></iframe>';
	var t = $('#index_tabs');
	var opts = {
		title : params.title,
		closable : true,
		iconCls : params.iconCls,
		content : iframe,
		border : false,
		fit : true
	};
	if (t.tabs('exists', opts.title)) {
		t.tabs('select', opts.title);
		parent.$.messager.progress('close');
	} else {
		var tabs = $('#index_tabs').tabs('tabs'); // 获得所有的Tab选项卡

		// 关闭已打开可关闭的页面---------
		var closeTabsTitle = [];
		$.each(tabs, function() {
			var opt = $(this).panel('options');
			if (opt.closable)
				closeTabsTitle.push(opt.title);
		});
		for (var i = 0; i < closeTabsTitle.length; i++)
			index_tabs.tabs('close', closeTabsTitle[i]);
		// -------------------------

		var tabcount = tabs.length; // Tab选项卡的个数
		if (tabcount >= 7) {
			parent.$.messager.alert('提示', '你已经打开了' + tabcount
					+ '个窗体，请关闭一些不用的窗体，再操作！', 'info');
			return;
		}
		t.tabs('add', opts);
	}
}

// 添加主页
function addhouse() {
	var iframe = '<iframe src="pages.do?para=base_layout_about" frameborder="0" style="border:0; width:100%; height:99%;overflow: hidden;"></iframe>';
	var t = $('#index_tabs');
	var opts = {
		title : "公司简介",
		closable : false,
		iconCls : 'house',
		content : iframe,
		border : false,
		fit : true
	};
	t.tabs('add', opts);
}

// 存当前选择的菜单ID
$('#accordion-menun').accordion({
	onSelect : function(title, index) {
		if (canSaveSelMenuIDCookie == true) {
			$.cookie('menunIndex', index, {
				expires : 7
			});
		}
	}
});

function loadMenunList() {
	canSaveSelMenuIDCookie = false;
	parent.$.messager.progress({
		title : '提示',
		msg : '正在加载菜单列表',
		text : '请稍后...'
	});
	setCookie('MenunList', null);

	$.ajax({
		type : "POST",
		async : true,
		cache : false,
		timeout : 2000,
		url : 'getMenu.do',
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		success : function(data) {
			loadMenunListSuccess(data);
			parent.$.messager.progress('close');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			if (httprequesttimeout(XMLHttpRequest) == true)
				return;
			if (loadmenuncount < 3) {
				loadmenuncount += 1;
				loadMenunList();
				// alert(url);
			} else {
				parent.$.messager.progress('close');
				parent.$.messager.alert("提示", "由于网络原因，菜单未加载完成，请退出页面，重新登陆!",
						"question");
			}
		}
	});
}

// 主界面页面关闭
window.onunload = function(e) {
	logout();
};

var postRestPassWord = function() {
	var oid = $("input[name=userId]").val();
	var oname = $("input[name=userName]").val();
	var opwd = $("input[name=pwd]").val();
	var npwd = $("input[name=npwd]").val();

//	var data = {
//		operatornum : 0,
//		operatorid : oid,
//		userName : oname,
//		oldpassword : opwd,
//		newpassword : npwd
//	};
	var data = "userId=" + oid + "&pwd=" + opwd + "&npwd="
	+ npwd + "&timestamp=" + tempPara();
	var url = 'updatepwd.do';
	var successfun = function(data) {
		// alert(data);
		var resdata = null;
		var json = null;
		try {
			resdata = (typeof data == "undefined") ? null : data;
			json = eval(resdata);
			if (json.res == 0) {
				parent.$.messager.alert("提示", "修改密码成功, 请牢记你的密码，下次登陆时生效");
				closeWindow('restPassWord_dialog');
			} else {
				parent.$.messager.alert("提示", "修改密码失败(" + json.res + ")"
						+ json.msg);
			}
		} catch (e) {
			parent.$.messager.alert("提示", "返回数据解析错误，请重试！");
		}

	};
	ajaxpost(url, data, successfun);
};

// 修改密码
var saveRestPassWord = function(flag) {
	try {
		if (!($('#restPassWord_form').form('validate'))) {
			parent.$.messager.alert("提示", "输入有误，请重新填写！");
			return;
		}
		var npwd = $("input[name=npwd]").val();
		var snpwd = $('input[name=renpwd]').val();
		if (npwd != snpwd) {
			parent.$.messager.alert("提示", "两次密码不一致！");
			$('#npwd').focus();
			return;
		}
		if ((npwd.length < 6) || (npwd.length > 16)) {
			parent.$.messager.alert("提示", "密码长度应该在6到16之间,请重新输入！");
			$('#npwd').focus();
			return;
		}

		var rsfun = function(rs) {
			if (rs) {
				postRestPassWord();
			}
		};
		parent.$.messager.confirm('提示', '确定修改密码？', rsfun);
	} catch (e) {
		parent.$.messager.alert("提示", e);
	}

};
