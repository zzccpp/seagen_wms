/**
 * 检验两时间段是否开始时间小于结束时间
 */
function checkdate(dtbegin, dtend) {
	try {
		var s = new Date(Date.parse(dtbegin.replace(/-/g, "/")));
		var e = new Date(Date.parse(dtend.replace(/-/g, "/")));
		return (s.getTime() > e.getTime()) ? true : false;
	} catch (e) {
		return true;
	}
}

function isDate(str) {
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
	var result = str.match(reg);
	if (result == null)
		return false;
	var dt = new Date(result[1], result[3] - 1, result[4]);
	if (Number(dt.getFullYear()) != Number(result[1]))
		return false;
	if (Number(dt.getMonth()) + 1 != Number(result[3]))
		return false;
	if (Number(dt.getDate()) != Number(result[4]))
		return false;
	return true;
}

//网页dateBox时间控件用到
var incDays = function(days, date) {
	date = date || new Date();
	date.setDate(date.getDate() + days);
	return date;
};
var incMonths = function(months, date) {
	date = date || new Date();
	date.setDate(1);
	date.setMonth(date.getMonth() + months);
	return date;
};
