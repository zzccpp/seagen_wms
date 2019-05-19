$(function() {
	showdata();
});

var interval_id = null;
function checkboxOnclick(checkbox) {
	if (checkbox.checked) {
		interval_id = window.setInterval("showdata()", 5000);
	} else {
		if (interval_id != null) {
			window.clearInterval(interval_id);
			interval_id = null;
		}
	}
}

function showdata() {
	// showprogress();
	var loaddataerr = function(XMLHttpRequest, textStatus, errorThrown) {
	};
	var loaddatasuccess = function(datalist) {
		// closeprogress();
		// JSON.parse()和JSON.stringify()
		// var datalist = JSON.parse(data);
		if (typeof datalist.threads == "undefined") {
			// parent.$.messager.alert("提示", "数据加载异常或无数据");
			return;
		}
		$('#data_panel').panel('clear');

		var showval = "";

		showval = "<table style='border-collapse: collapse; border: none; margin:5px;'>";
		showval += "<tr><td style='width:90px; border: solid #000 1px;background: #ffd18d;' align='center' colspan = 2>总业务线程数</td><td style='width:360px; border: solid #000 1px; padding-left: 13px;'  colspan = 8 >"
				+ datalist.threads + "</td>";
		showval += "<td style='width:90px; border: solid #000 1px;background: #ffd18d;' align='center' colspan = 2>总任务数</td><td style='width:360px; border: solid #000 1px; padding-left: 13px;' colspan = 8>"
			+ datalist.mesages + "</td></tr>";
		
		if (datalist.threads > 0) {
			showval += "<tr style='background: #ffd18d;'>";
			for (var i = 0; i < 10; i++) {
				showval += "<td style=' width:45px; border: solid #000 1px;' align='center'>线程</td><td style=' width:45px; border: solid #000 1px ' align='center'>任务数</td>";
			}
			showval += "</tr>";

			var i = 0;
			for (var j = 0; j < datalist.data.length; j++) {
				var info = datalist.data[j];
				if (i == 0)
					showval += "<tr>";
				showval += "<td style= 'border: solid #000 1px;color:white;background: #069012;'  align='center'>"
						+ padNumToStr(info.chuteid, 3, '0') + "</td>";
				if (info.count > 3)
					showval += "<td style= 'border: solid #000 1px; color:red' align='center'>"
							+ info.count + " </td>";
				else
					showval += "<td style= 'border: solid #000 1px;' align='center'>"
							+ info.count + " </td>";
				i = i + 1;
				if (i >= 10) {
					showval += "</tr>";
					i = 0;
				}
			}
			while (i>0 && i < 10) {
				showval += "<td style= 'border: solid #000 1px;background: #069012;'> </td><td style= 'border: solid #000 1px;'> </td>";
				i = i + 1;
				if (i >= 10)
					showval += "</tr>";
			}
			
		}
		showval += "</table>";
		//console.log(showval);
		$("#data_panel").html(showval);
	};
	var url = 'querytasklist.do';
	var data = "timestamp=" + tempPara();
	ajaxpost(url, data, loaddatasuccess, loaddataerr);
	
	var loaddatasuccess_runtimer = function(datalist){
		if (typeof datalist.count == "undefined") {
			return;
		}
		$('#runtimer_panel').panel('clear');

		var showval = "";

		showval = "<table style='border-collapse: collapse; border: none; margin:5px;'>";
		
		if (datalist.count > 0) {
			showval += "<tr style='background: #ffd18d;'>";
			showval += "<td style=' width:189px; border: solid #000 1px;' align='center'>通道名称</td>";
			showval += "<td style=' width:189px; border: solid #000 1px;' align='center'>执行最小时间(ms)</td>";
			showval += "<td style=' width:189px; border: solid #000 1px;' align='center'>执行最大时间(ms)</td>";
			showval += "<td style=' width:189px; border: solid #000 1px;' align='center'>执行平均时间(ms)</td>";
			showval += "<td style=' width:189px; border: solid #000 1px;' align='center'>执行记录条数</td>";
			showval += "</tr>";

			var bg = '';
			for (var j = 0; j < datalist.data.length; j++) {
				var info = datalist.data[j];
				if(j%2 == 1)
					bg = " style= 'background: lavender;'";
				
				showval += "<tr"+bg+">";
				showval += "<td style= 'border: solid #000 1px;color:white;background: #069012; padding-right:10px;'  align='right'>" + info.title + "</td>";
				showval += "<td style= 'border: solid #000 1px;' align='center'>"+ info.min + " </td>";
				showval += "<td style= 'border: solid #000 1px;' align='center'>"+ info.max + " </td>";
				showval += "<td style= 'border: solid #000 1px;' align='center'>"+ info.avg + " </td>";
				showval += "<td style= 'border: solid #000 1px;' align='center'>"+ info.size + " </td>";
				showval += "</tr>";
				bg = '';
			};
			
		};
		showval += "</table>";
		//console.log(showval);
		$("#runtimer_panel").html(showval);
	};
	var url_runtimer = 'queryruntimerlist.do';
	ajaxpost(url_runtimer, data, loaddatasuccess_runtimer, loaddataerr);
}
