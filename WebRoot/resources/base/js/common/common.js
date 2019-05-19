var online = true; // 是否在线状态
var windowEventOn = true; // 是否锁定右键等操作

// 设置cookie值
function setCookie(name, value) {
	var Days = 30; // 此 cookie 将被保存 30 天
	var exp = new Date(); // new Date("December 31, 9998");
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
};

/* 质朴长存法 */
function padNumToStr(num, len, padStr) {
	if (typeof padStr == "undefined")
		padStr = "0";
	var num_len = num.toString().length;
	while (num_len < len) {
		num = padStr + num;
		num_len++;
	}
	return num;
}

function pad(str, n) {
	var temp = (str == null) ? "" : str;
	var len = temp.length;
	while (len < n) {
		temp = "0" + temp;
		len++;
	}
	return temp;
}

function padstr(str, n) {
	var temp = (str == null) ? "" : str;
	var len = temp.length;
	while (len < n) {
		temp = " " + temp;
		len++;
	}
	return temp;
}

var btnEnable = function(btnID, enable) {
	if (enable)
		$('#' + btnID).linkbutton('enable');
	else
		$('#' + btnID).linkbutton('disable');
};

var inputDisabled = function(btnID, enable) {
	if (enable)
		$('#' + btnID).attr("disabled", "disabled");
	else
		$('#' + btnID).removeAttr("disabled");
};


function diffdays(date1, date2) {
	try {
		return parseInt(Math.abs(date1 - date2) / 1000 / 60 / 60 / 24);// 把相差的毫秒数转换为天数
	} catch (e) {
		return 0;
	}
}



var weekdaylist = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];

var weekdays = [ "周日", "周一", "周二", "周三", "周四", "周五", "周六", "节假日" ];

function getweekday(index) {
	try {
		var wk = (typeof index == "undefined") ? 0 : index;
		if ((wk < 0) || (wk > 6))
			wk = 0;
		return weekdaylist[wk];
	} catch (e) {
		return weekdaylist[0];
	}
}

function comboxdefault(obj) {
	try {
		var data = $(obj).combobox('getData');
		if (data.length > 0) {
			$(obj).combobox('select', data[0].id);
		}
	} catch (e) {
	}
}

function tempPara() {
	return ((new Date()).getTime()).toString();
}

// 获取cookie值
function getCookie(name) {
	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
};

function isNumStr(str) {
	var reg = /^\d*$/;
	return reg.test(str);
}

// input获焦点失焦事件
$.focusblur = function(focusid) {
	var focusblurid = $(focusid);
	var defval = focusblurid.val();
	focusblurid.focus(function() {
		var thisval = $(this).val();
		if (thisval == defval) {
			$(this).val("");
		}
	});
	focusblurid.blur(function() {
		var thisval = $(this).val();
		if (thisval == "") {
			$(this).val(defval);
		}
	});
};


var btnEnable = function(btnID, enable) {
	if (enable)
		$('#' + btnID).linkbutton('enable');
	else
		$('#' + btnID).linkbutton('disable');
};

var inputDisabled = function(btnID, enable) {
	if (enable)
		$('#' + btnID).attr("disabled", "disabled");
	else
		$('#' + btnID).removeAttr("disabled");
};

// 打开弹窗
var openWindow = function(id) {
	$('#' + id).dialog('open');
};
// 关闭弹窗
var closeWindow = function(id) {
	$('#' + id).dialog('close');
};
// 改变按钮文字
var changeLinkbutton = function(btnID, btnText) {
	$('#' + btnID).linkbutton({
		text : btnText
	});
};

// 休眠一定的时间,最小50ms
var sleep = function(sleeptime) {
	var time = (sleeptime || 50);
	aTime = (time < 50) ? 50 : time;
	var date = new Date().getTime();
	while (true) {
		if (((new Date()).getTime() - date) >= time)
			return;
	}
};

function showprogress() {
	try {
		parent.$.messager.progress({
			title : '提示',
			msg : '正在请求数据',
			text : '请稍后...'
		});
	} catch (e) {
	}
}

function closeprogress() {
	try {
		parent.$.messager.progress('close');
	} catch (e) {
	}
}


window.onload = function() {
	if (windowEventOn == true)
		return;
	with (document.body) {
		oncontextmenu = function() {
			return false;
		};
	}
};

// 将日期转换成字符串
// alert(formatDateTime("yyyy年MM月dd日"));
// alert(formatDateTime("MM/dd/yyyy"));
// alert(formatDateTime("yyyyMMdd"));
// alert(formatDateTime("yyyy-MM-dd hh:mm:ss"));
// alert(formatDateTime("yyyy年MM月dd日hh小时mm分ss秒"));
var formatDateTime = function(format, date) {
	date = date || new Date();
	var o = {
		"M+" : date.getMonth() + 1, // month
		"d+" : date.getDate(), // day
		"h+" : date.getHours(), // hour
		"m+" : date.getMinutes(), // minute
		"s+" : date.getSeconds(), // second
		"q+" : Math.floor((date.getMonth() + 3) / 3), // quarter
		"S" : date.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (date.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

var format_two_decimal = function(value, row, index) {
	if (typeof value == "undefined")
		return "0.00%";
	try {
		return value.toFixed(2) + "%";
	} catch (e) {
		return value + "%";
	}
};

// 排序
var number_sort = function(a, b) {
	var number1, number2;
	try {
		number1 = parseFloat(a);
	} catch (e) {
		number1 = -1;
	}
	try {
		number2 = parseFloat(b);
	} catch (e) {
		number2 = -1;
	}
	return (number1 > number2 ? 1 : -1);
};

// 排序
var str_sort = function(a, b) {
	try {
		return (a > b ? 1 : -1);
	} catch (e) {
		return -1;
	}
};

// 将字符串格式化成函数
var format_columns_formatter = function(columns) {
	try {
		for (var i = 0; i < columns.length; i++) {
			if (columns[i].formatter)
				columns[i].formatter = eval(columns[i].formatter);
			if (columns[i].sorter)
				columns[i].sorter = eval(columns[i].sorter);
			for (var j = 0; j < columns[i].length; j++) {
				if (columns[i][j].formatter)
					columns[i][j].formatter = eval(columns[i][j].formatter);
				if (columns[i][j].sorter)
					columns[i][j].sorter = eval(columns[i][j].sorter);
			}
		}
	} catch (e) {
	}
};


/**
 * 去掉字符两边的空格
 */
function trim(str) {
	if (str.length > 0) {
		return str.replace(/(^\s*)|(\s*$)/g, '');
	}
	return null;
}

/**
 * 去掉字符左边的空格
 */
function ltrim(str) {
	if (str.length > 0)
		return str.replace(/^\s*/g, '');
	return null;
}

/**
 * 去掉字符右边的空格
 */
function rtrim(str) {
	if (str.length > 0)
		return str.replace(/\s*$/g, '');
	return null;
}

/**
 * 十进制字符串转十六进制字符串
 */
function stringToHex(str) {
	return str.replace(/[\d\D]/g, function($) {
		return ("000" + $.charCodeAt(0).toString(16)).slice(-4);
	});
}
/**
 * 十六进制字符串转十进制字符串
 */
function hexToString(str) {
	return str.replace(/.{4}/g, function($) {
		return String.fromCharCode(parseInt($, 16));
	});
}



//报表中：分类统计时页面显示与隐藏设置 2017-3-29 18:05:34
function function_Type(value){
//	$("#type_value").val(value);
	var no_time;
	if(value == 0){
		date_start = $('#griddate_start').val();
		date_end = $('#griddate_end').val();
		document.getElementById("td_times").style.display = "";
		document.getElementById("td_day").style.display = "none";
		document.getElementById("td_month").style.display = "none";
		document.getElementById("td_year").style.display = "none";
	}
	if(value == 1){
		no_time = formatDateTime("yyyy-MM-dd", new Date);
		$('#griddate_start_day').val(no_time);
		date_start = no_time;
		document.getElementById("td_times").style.display = "none";
		document.getElementById("td_day").style.display = "";
		document.getElementById("td_month").style.display = "none";
		document.getElementById("td_year").style.display = "none";
	}
	if(value == 2){
		no_time = formatDateTime("yyyy-MM", new Date);
		$('#griddate_start_month').val(no_time);
		date_start = no_time;
		document.getElementById("td_times").style.display = "none";
		document.getElementById("td_day").style.display = "none";
		document.getElementById("td_month").style.display = "";
		document.getElementById("td_year").style.display = "none";
	}
	if(value == 3){
		no_time = formatDateTime("yyyy", new Date);
		$('#griddate_start_year').val(no_time);
		date_start = no_time;
		document.getElementById("td_times").style.display = "none";
		document.getElementById("td_day").style.display = "none";
		document.getElementById("td_month").style.display = "none";
		document.getElementById("td_year").style.display = "";
	}
}
//验证是否是合法的邮箱地址
function isEmail(str){
	if(($.trim(str) == "")||str.length == 0)
		return true;
	var regex = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
    return regex.test(str);
}
//验证是否是合法的手机号
function isPhone(str){
	if(($.trim(str) == "")||str.length == 0)
		return true;
	var regex = /^1(3|4|5|7|8)\d{9}$/;
	return regex.test(str);
}
//验证是否是合法的电话:固定电话
function isTel(str){
	if(($.trim(str) == "")||str.length == 0)
		return true;
	var regex = /\d{3}-\d{8}|\d{4}-\d{7}/;
	return regex.test(str);
}

//写cookies
function setCookie(name,value)
{
	var Days = 7;//保存七天
	var exp = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
//读cookies
function getCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
	return unescape(arr[2]);
	else
	return null;
}
//删除cookies
function delCookie(name)
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval=getCookie(name);
	if(cval!=null)
	document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
//格式化扫描状态
function formatScanStatus(value){
	var str = "";
	if(value == 0){
		str = "<font color='green'>正常扫描</font>";
	} else if(value == 1){
		str = "<font color='red'>无分拣方案</font>";
	} else if(value == 2){
		str = "<font color='red'>无数据</font>";
	} else if(value == 3){
		str = "<font color='red'>无读</font>";
	} else if(value == 4){
		str = "<font color='red'>取消</font>";
	} else if(value == 5){
		str = "<font color='red'>最大圈数</font>";
	} else if(value == 6){
		str = "<font color='red'>格口错</font>";
	} else if(value == 7){
		str = "<font color='red'>多条码</font>";
	} else if(value == 8){
		str = "<font color='red'>迷失</font>";
	} else {
		str = "<font color='red'>未知："+value+"</font>";
	}
	return str;
}
//格式化数据更新标志
function formatUpdateStatus(value){
	var str = "";
	if(value == 0){
		str = "<font color='red'>未更新</font>";
	} else if(value == 1){
		str = "<font color='green'>已更新</font>";
	} else if(value == 2){
		str = "<font color='red'>不可更新</font>";
	} else {
		str = "<font color='red'>未更新</font>";
	}
	return str;
}

//格式化封包更新标志
function formatJbStatus(value){
	var str = "";
	if(value == 0){
		str = "<font color='red'>未建包</font>";
	} else if(value == 1){
		str = "<font color='green'>已建包</font>";
	} else if(value == 2){
		str = "<font color='red'>不可建包(综合格口异常格口)</font>";
	} else {
		str = "<font color='red'>未建包</font>";
	}
	return str;
}
//事件上传状态格式化
function formatEventUploadStatus(value){
	var status = "";
	if(value==0){
		status="<font color='red'>上传失败</font>";
	} else if(value==1){
		status="<font color='green'>上传成功</font>";
	} else{
		status="<font color='black'>关闭上传</font>";
	} 
	return status;
}
$.cookie = function(key, value, options) {
	if (arguments.length > 1 && (value === null || typeof value !== "object")) {
		options = $.extend({}, options);
		if (value === null) {
			options.expires = -1;
		}
		if (typeof options.expires === 'number') {
			var days = options.expires, t = options.expires = new Date();
			t.setDate(t.getDate() + days);
		}
		return (document.cookie = [ encodeURIComponent(key), '=', options.raw ? String(value) : encodeURIComponent(String(value)), options.expires ? '; expires=' + options.expires.toUTCString() : '', options.path ? '; path=' + options.path : '', options.domain ? '; domain=' + options.domain : '', options.secure ? '; secure' : '' ].join(''));
	}
	options = value || {};
	var result, decode = options.raw ? function(s) {
		return s;
	} : decodeURIComponent;
	return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) {
     fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 }
 for (var k in o)
     if (new RegExp("(" + k + ")").test(fmt)) 
         fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
 
}

function formatTimeType(value){
	if(value == ''||value == null)
		return "";
    var oldTime = (new Date(value)).getTime();
    var curTime = new Date(oldTime).Format("yyyy-MM-dd hh:mm:ss");
    return curTime;
}

//格式化运单状态
function formatWaybillStatus(value){
	var str = "";
	if(value == 0){
		str = "<font color='green'>正常运单</font>";
	} else if(value == 1){
		str = "<font color='red'>运单取消</font>";
	} else if(value == 2){
		str = "<font color='red'>运单地址更改</font>";
	} else {
		str = "<font color='red'>未知</font>";
	} 
	return str;
}

//格式化分拣状态
function formatSortedStatus(value){
	var str = "";
	if(value == 0){
		str = "<font color='green'>正常分拣</font>";
	} else if(value == 1){
		str = "<font color='red'>无分拣方案</font>";
	} else if(value == 2){
		str = "<font color='red'>无数据</font>";
	} else if(value == 3){
		str = "<font color='red'>无读</font>";
	} else if(value == 4){
		str = "<font color='red'>取消</font>";
	} else if(value == 5){
		str = "<font color='red'>最大圈数</font>";
	} else if(value == 6){
		str = "<font color='red'>格口错</font>";
	} else if(value == 7){
		str = "<font color='red'>多条码</font>";
	} else if(value == 8){
		str = "<font color='red'>迷失</font>";
	} else {
		str = "<font color='red'>未知："+value+"</font>";
	}
	return str;
}