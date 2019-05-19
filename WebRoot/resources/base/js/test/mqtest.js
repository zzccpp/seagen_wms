function sendAjax(data,url){
	//发送ajax请求
	$.ajax({
		type:"post",
		url:url,
		data:data,
		success:function(data){
			if(data.code==200){
				//$.messager.alert('提示',data.message,'info');
				$.messager.show({
					title:'提示',
					msg:data.message,
					showType:'show',
					timeout:1000,
					style:{
						right:'',
						top:document.body.scrollTop+document.documentElement.scrollTop,
						bottom:''
					}
				});

			}
		}
	});
}
/**
 * 1.发送开关机MQ
 */
function sendOnAndOffMQ(){
	var flag = $('#flag').combobox('getValue');
	var reset = $('#reset').combobox('getValue');
	var data = "flag="+flag+"&reset="+reset;
	sendAjax(data,"onAndOffMQTest.do");
}
/**
 * 2.设置分拣模式MQ
 */
function sendSetSortModeMQ(){
	var sortName = $("#sortModeName").val();
	var data = "sortName="+sortName;
	sendAjax(data,"setSortModeMQTest.do");
}
/**
 * 3.设置流水号MQ
 */
function sendSetlineNumMQ(){
	var pipeline = $("#lineNum").val();
	var data = "pipeline="+pipeline;
	sendAjax(data,"setlineNumMQTest.do");
}
/**
 * 4.发送开关特性设备控制 MQ测试
 */
function sendCtrlComMQ(){
	var devId = $("#devId").combobox('getValue');
	var ctrlData = $("#ctrlData").val();
	var data = "devId="+devId+"&ctrlData="+ctrlData;
	sendAjax(data,"sendCtrlComMQTest.do");
}
/**
 * 5.发送扫描与分拣MQ消息
 */
function sendScanAndSortMQ(){
	//获取到发送次数
	var count = $("#ss_count").val();
	var doCount = $("#ss_do_count").val();
	var data="count="+count+"&doCount="+doCount;
	sendAjax(data,"scanAndSortMQTest.do");
}
/**
 * 6.建包请求 MQ测试
 */
function sendJBMQ(){
	var type = $("#type").combobox('getValue');
	var jbtype = $("#jbtype").combobox('getValue');
	var chuteNum = $("#chuteNum").val();
	var data = "jbtype="+jbtype+"&type="+type+"&chuteNum="+chuteNum;
	sendAjax(data,"sendJBMQTest.do");
}
/**
 * 7.称重量方MQ测试
 */
function sendVolumeMQ(){
	var count = $("#volume_count").val();
	var data="count="+count;
	sendAjax(data,"sendVolumeMQTest.do");
}
/**
 * 8.事件 MQ测试
 */
function sendEventMQ(){
	var eventId = $("#eventId").combobox('getValue');
	var target = $("#eventId").combobox('getText');
	var val = $("#eventVal").val();
	var data = "eventId="+eventId+"&target="+target+"&val="+val;
	sendAjax(data,"sendEventMQTest.do");
}
/**
 * 9.日志 MQ测试
 */
function sendLogMQ(){
	var logId = $("#logId").combobox('getValue');
	var target = $("#logId").combobox('getText');
	var val = $("#logVal").val();
	var data = "logId="+logId+"&target="+target+"&val="+val;
	sendAjax(data,"sendLogMQTest.do");
}

/**
 * 10.清空数据表：mq_err_data, report_car, report_chute, report_minute, report_scan,
 *  report_site, report_sorting, report_supply, scan, sorting
 */
function sendEmptyTable(){
	sendAjax(null,"emptyTableData.do");
}
function sendApiAjax(data,url){
	//发送ajax请求
	$.ajax({
		type:"post",
		url:url,
		data:data,
		success:function(data){
			$("#result").text(JSON.stringify(data));
		}
	});
}
function testReportApi() {
	var data ;
	var id = $('#reportid').val();
	var startdate = $('#startdate').val();
	var enddate = $('#enddate').val();
	var rule = $('#rule').val();
	var type = $('#apitype').val();
	var username = $('#username').val();
	var password = $('#password').val();
	//var data = 'id='+id+'&date='+startdate+'&end_date='+enddate+'&app_code=sea_ipms&rule='+rule;
	if (type == '0') {
		data = '{id:'+id+',date:\''+startdate+'\',end_date:\''+enddate+'\',app_code:\'sea_ipms\',rule:'+rule+'}';
		sendApiAjax(data,"/sea_scs/service/api/report");
	}else {
		data = '{id:'+id+',app_code:\'sea_crtl\',user_name:\''+username+'\',user_password:\''+password+'\'}';
		sendApiAjax(data,"/sea_scs/service/api/authorization");
	}
	
	console.log(data);
	
	//sendApiAjax(data,"testReportApi.do");
	
}
/**测试根据运单号获取格口*/
function sendGetChuteMQ(){
	var package_code = $("#package_code").val();
	var data = "package_code="+package_code;
	sendAjax(data,"sendGetChute.do");
}
/**测试推送格口状态*/
function sendChuteStateMQ(){
	var chuteState = $("#chuteState").val();//combobox('getValue');
	var chuteNum = $("#stateChuteNum").val();
	var data = "chuteState="+chuteState+"&chuteNum="+chuteNum;
	sendAjax(data,"sendChuteState.do");
}
