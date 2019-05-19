var date_start;
var date_end;
var type;

$(function() {
	
	var layerId_options = [{text : "全部", value : "0"},{text : "第一层", value : "1"},{text : "第二层", value : "2"}];   
    $("#layerId_1").combobox({
		valueField: 'value',
        textField: 'text', 
        data : layerId_options,
        value:'0',
        panelHeight:80
	});
    $("#layerId_2").combobox({
		valueField: 'value',
        textField: 'text', 
        data : layerId_options,
        value:'0',
        panelHeight:80
	});
	
	//今天的时间
	 var today = new Date();
	 var stime=getInitDate(today);
	//明天的时间
	 today.setTime(today.getTime()+24*60*60*1000);
	 var etime = getInitDate(today);
	 $('#stime').val(stime);
	 $('#etime').val(etime);
	// 设置分页控件
	$('#chart_dataList').datagrid({
		nowrap : false, // 是否会把数据显示在一行里
		striped : true, // 是否把行条纹化（即奇偶行使用不同背景色）
		collapsible : false, // 是否允许折叠
		border : true,// 是否有边框
		fit : true,// 是否自动拉伸填冲父框大小
		showFooter : true, // 是否显示页脚即所谓的汇总信息
		rownumbers : true,// 是否显示行号的列
		singleSelect : true,// 是否只允许选中一行
		fitColumns : false, // 是否自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
		autoRowHeight : false, // 是否自动行高
		pagination : false, // 是否在 datagrid 的底部显示分页栏
		columns:[[
			{field:'supplyNo',title:'导入台号',width:100,align : 'center'},
			{field:'spikeNum',title:'峰值(1分钟内)',width:100,align : 'center'},
			{field:'sixSpikeNum',title:'峰值(连续60分钟)',width:100,align : 'center'},
			{field:'allCount',title:'供件总量',width:100,align : 'center'},
			{field:'validTime',title:'有效操作时间(小时)',width:150,align : 'center'},
			{field:'efficiency',title:'供件效率(件/小时)',width:150,align : 'center'}
		]],
		toolbar : '#toolbar1', // 查询工具栏
		onSelect:function(rowIndex,rowData){
			if(rowData.supplyNo=="合计"){
				$.messager.alert('提示消息','请选择有效导入台记录','info');
				return;
			}
			lineChart(rowData.supplyNo,rowData.sixSpikeNum);
		}
	});
	showgriddata(1);//chart图形模式
});
/**显示饼图*/
function showPieChart(rows){
	//获取导入台供件量数量,拼装数据
	var data = [];
	var maxIndex = 0;
	var tempCount=0;
	for(var i = 0;i<rows.length-1;i++){
		if(rows[i].allCount>tempCount){
			tempCount = rows[i].allCount;
			maxIndex = i;
		}
		var info = {name:rows[i].supplyNo,y:rows[i].allCount,selected: false, sliced: false};
		data.push(info);
	}
	//选中最大量导入台统计
	data[maxIndex].selected=true;
	data[maxIndex].sliced=true;
	//获取统计时间
	 var stime = $('#stime').val();
	 var etime = $('#etime').val();
	  $('#pieChart').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: true
	        },
	        title: {
	            text: stime+'至'+etime+'导入台供件所占比'
	        },
	        tooltip: {//加单位百分比
	            headerFormat: '{series.name}<br>',
	            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {//点击模块的时候分离
		        pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                },
	                states: {
	                    hover: {
	                        enabled: false
	                    }  
	                },
	                slicedOffset: 20,         // 突出间距
	                point: {                  // 每个扇区是数据点对象，所以事件应该写在 point 下面
	                    events: {
	                        // 鼠标滑过是，突出当前扇区
	                        mouseOver: function() {
	                            this.slice();
	                        },
	                        // 鼠标移出时，收回突出显示
	                        mouseOut: function() {
	                            this.slice();
	                        },
	                        // 默认是点击突出，这里屏蔽掉
	                        click: function() {
	                            return false;
	                        }
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '导入台供件总量所占比',
	            data: data
	        }]
	    });
}
/**
 * 显示折线图
 */
function lineChart(supplyNo,sixSpikeNum){
	 //获取统计时间
	 var stime = $('#stime').val();
	 var etime = $('#etime').val();
	 //拼装标题
	 var title = supplyNo+"("+stime+"至"+etime+")间内连续60分钟峰值";
	 //x坐标值
	 var units = new Array();
	 var vals = new Array();
	 var layerId = $('#layerId_1').combobox('getValue');
	 $.ajax({
			type : "POST",
			url : "querySpikeNum.do",
			async:false,//同步
			data : "stime="+stime+"&etime="+etime+"&supplyNo="+supplyNo+"&sixSpikeNum="+sixSpikeNum,
			success : function(jsons) {
				for(var i=0;i<jsons.length;i++){
					units[i] = jsons[i].reportDate;
					vals[i] = jsons[i].scanNum;
				}
			}
	 	});
	 
	 $('#lineChart').highcharts({
		 	title:{
		 		text:title
		 	},
	        chart:{
	            type:'line'
	        },
	        xAxis: {
	        	categories:units //[1, 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	        },
	        yAxis: {
	            title: {
	                text:'数量 (件)'
	            }
	        },tooltip: {//加单位百分比
	          // headerFormat: '',
	            pointFormat: '<b>{series.name}: {point.y}</b>'
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true          // 开启数据标签
	                },
	                enableMouseTracking: true // 关闭鼠标跟踪，对应的提示框、点击事件会失效
	            }
	        },
	        series: [{
	        	name: supplyNo,
	            data: vals//[29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
	        }]
	  });
}
/**
 * 查询
 */
function showgriddata(args) {
	showprogress();
	console.log(type);
	if(args==1){//图形
		var stime = $('#stime').val();
		var etime = $('#etime').val();
		var layerId = $('#layerId_1').combobox('getValue');
		$.ajax({
			type : "POST",
			url : "queryScanSupply.do",
			data : "date="+stime+"&end_date=" + etime+"&layer="+ layerId,
			success : function(r) {
				closeprogress();
				//var jsonstr ="[{'supplyNo':'1','spikeNum':'1','allCount':'1','validTime':'1s','efficiency':'1%'}]"; 
				//var jsonarray = eval('('+jsonstr+')');// $.parseJSON(jsonstr);//
				$('#chart_dataList').datagrid({
					data:r
				});
				showPieChart(r);//更新图表
			}
		});
	}
	if(args==2){//矩阵列表
		type = $('#type').combobox('getValue');
		var loaddatasuccess = function(datalist) {
			closeprogress();
			griddata = datalist;
			if (typeof griddata.frozenColumns == "undefined") {
				parent.$.messager.alert("提示", "数据加载异常或无数据");
				return;
			}
			
			// 格式化字符串成函数
			format_columns_formatter(griddata.frozenColumns);
			format_columns_formatter(griddata.columns);
			console.log(griddata.columns);
			griddata.columns[1][3].title = '无读量';
			if(griddata.columns[1].length > 5){
				griddata.columns[1][6].title = '无数据';
			}
			
			$("#table_dataList").datagrid({
				frozenColumns : [ datalist.frozenColumns ],
				columns : datalist.columns,
				data : datalist.data
			});
			$('#table_dataList').datagrid('reloadFooter', [ datalist.footer ]);
		};
		getReportTime(type);
		if(type != 0){
			date_start = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date(Date.parse(date_start)));
			date_end = formatDateTime("yyyy-MM-dd hh:mm:ss", new Date(Date.parse(date_start)));
		} else {
			if(new Date(Date.parse(date_end)) - new Date(Date.parse(date_start)) <= 0) {
				closeprogress();
				parent.$.messager.alert("提示", "查询时间段:截止时间应该大于开始时间");
				return;
			}
		}
		//var isMore = getCookie(cookiename);
		var isMore = $('#more_type').combobox('getValue');

		var layerId = $('#layerId_2').combobox('getValue');
		
		var url = 'reportsupply.do';
		var data = "id=810&date=" + date_start+"&end_date=" + date_end + "&type=" + type + '&more=' + (isMore == null? 0 : parseInt(isMore, 10)) +"&layer="+ layerId +"&timestamp=" + tempPara();
		ajaxpost(url, data, loaddatasuccess);
	}
}
//获取日期  yyyy-MM-dd HH:mm:ss
function getInitDate(date){
	var month = date.getMonth()+1;
	var day = date.getDate();
	month = month<10?('0'+month):month;
	day = day<10?('0'+day):day;
	return (date.getFullYear()+"-"+month+"-"+day+" 07:00");
}


//Excel数据导出
function excelExport(args){
	if(args==1){//图形
		var stime = $('#stime').val();
		var etime = $('#etime').val();
		var layerId = $('#layerId_1').combobox('getValue');
		$.messager.confirm('提示','确定导出从 '+stime+' 至 '+etime+' 的数据吗?',function(r){
			 if (r){
				 var url="exportScanSupply.do?date="+stime+"&end_date=" + etime+"&layer="+ layerId;
				 window.open(url);
			 }
		});
	}
	if(args==2){//矩阵列表
		var type = $('#type').combobox('getValue');
		var str = "";
		if(type == 0){
			str	= '确定导出从' + date_start + ' 到 '+date_end + '的数据吗?';
			if(new Date(Date.parse(date_end)) - new Date(Date.parse(date_start)) <= 0) {
				closeprogress();
				parent.$.messager.alert("提示", "查询时间段:截止时间应该大于开始时间");
				return;
			}
		}
		if(type == 1){
			str	= '确定导出'+date_start.substring(0,10)+'的数据吗?';
		}
		if(type == 2){
			str	= '确定导出'+date_start.substring(0,7)+'月份数据吗?';
		}
		if(type == 3){
			str	= '确定导出'+date_start.substring(0,4)+'年数据吗?';
		}
		var layerId = $('#layerId_2').combobox('getValue');
		//var isMore = getCookie(cookiename);
		var isMore = $('#more_type').combobox('getValue');
		var data = "id=810&date=" + date_start+"&end_date=" + date_end + "&type=" + type + '&more=' + (isMore == null? 0 : parseInt(isMore, 10))  +"&layer="+ layerId + "&timestamp=" + tempPara();
		
		$.messager.confirm('提示',str,function(r){
			 if (r){
				 var url="exportSupply.do?"+data;
				 window.open(url);
			 }
		});
	}
}
function type_click(args){
	if(args==1){
		$(".supply_chart").css("display","block");
		$("#supply_table").css("display","none");
		showgriddata(1);//chart图形模式
	}
	if(args==2){
		$(".supply_chart").css("display","none");
		$("#supply_table").css("display","block");
		tableinit();
	}
}

function tableinit(){
	var type_options = [{text : "时间段", value : "0"},
	                     {text : "按天", value : "1"},
	                     {text : "按月", value : "2"},
	                     {text : "按年", value : "3"}];   
	    $("#type").combobox({
			valueField: 'value',
	        textField: 'text', 
	        data : type_options,
	        value:'0',
	        panelHeight:140,
			onChange: function (n,o) {
				function_Type(n);
			}
		});
	    
	    var more_type_options = [{text : "精简", value : "0"},{text : "详细", value : "1"}];   
	    $("#more_type").combobox({
			valueField: 'value',
	        textField: 'text', 
	        data : more_type_options,
	        value:'0',
	        panelHeight:80
		});
	    
		var gridmonth_start = formatDateTime("yyyy-MM-dd 00:00", new Date);
		var gridmonth_end = formatDateTime("yyyy-MM-dd hh:mm", new Date);
		
		$('#griddate_start').val(gridmonth_start);
		$('#griddate_end').val(gridmonth_end);
		date_start = gridmonth_start;
		date_end = gridmonth_end;
	    
		type = $('#type').combobox('getValue');
		// 设置分页控件
		$('#table_dataList').datagrid({
			nowrap : false, // 是否会把数据显示在一行里
			striped : true, // 是否把行条纹化（即奇偶行使用不同背景色）
			collapsible : false, // 是否允许折叠
			border : true,// 是否有边框
			fit : true,// 是否自动拉伸填冲父框大小
			showFooter : true, // 是否显示页脚即所谓的汇总信息
			rownumbers : true,// 是否显示行号的列
			singleSelect : true,// 是否只允许选中一行
			fitColumns : false, // 是否自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
			autoRowHeight : false, // 是否自动行高
			pagination : false, // 是否在 datagrid 的底部显示分页栏
			remoteSort:false,//设置remoteSort（注：此属性默认为true，如果是对本地数据排序必须设置为false）
			// pageNumber : 1,// 初使化时显示第几页
			// pageSize : 30,// 每页显示的记录条数，默认为10
			// pageList : [ 30, 50, 75, 100 ],// 可以设置每页记录条数的列表
			// queryParams : null, // 请求的参数JSON
			toolbar : '#toolbar2' // 查询工具栏
		// onClickRow : onClickRow
		});
		showgriddata(2);
}

function getReportTime(type){
	if(type == 0){
		date_start = $('#griddate_start').val();
		date_end = $('#griddate_end').val();
	}
	if(type == 1){
		date_start = $('#griddate_start_day').val();
	}
	if(type == 2){
		date_start = $('#griddate_start_month').val();
	}
	if(type == 3){
		date_start = $('#griddate_start_year').val();
	}
}