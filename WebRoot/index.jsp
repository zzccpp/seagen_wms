<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
  <head>
    <title>主页</title>
  
	<jsp:include page="./WEB-INF/pages/base/common/common.jsp"/>
	<script type="text/javascript">
	$(function(){
		var chart = Highcharts.chart('container', {
		    title: {
		        text: '2010 ~ 2016 年太阳能行业就业人员发展情况'
		    },
		    subtitle: {
		        text: '数据来源：thesolarfoundation.com'
		    },
		    yAxis: {
		        title: {
		            text: '就业人数'
		        }
		    },
		    legend: {
		        layout: 'vertical',
		        align: 'right',
		        verticalAlign: 'middle'
		    },
		    plotOptions: {
		        series: {
		            label: {
		                connectorAllowed: false
		            },
		            pointStart: 2010
		        }
		    },
		    series: [{
		        name: '安装，实施人员',
		        data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
		    }, {
		        name: '工人',
		        data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
		    }, {
		        name: '销售',
		        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
		    }, {
		        name: '项目开发',
		        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
		    }, {
		        name: '其他',
		        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
		    }],
		    responsive: {
		        rules: [{
		            condition: {
		                maxWidth: 500
		            },
		            chartOptions: {
		                legend: {
		                    layout: 'horizontal',
		                    align: 'center',
		                    verticalAlign: 'bottom'
		                }
		            }
		        }]
		    }
		});
	});
	</script>
  </head>
  
  <body>
    <div id="cc" class="easyui-layout" style="width:100%;height:100%;">   
    <div data-options="region:'north',title:'North Title',split:true" style="height:100px;"></div>   
    <div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>   
    <div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>   
    <div data-options="region:'west',title:'West',split:true" style="width:100px;"></div>   
    <div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
    	<input type="text" class="Wdate" id="d122" onclick="WdatePicker({isShowWeek:true,onpicked:function() {$dp.$('d122_1').value=$dp.cal.getP('W','W');$dp.$('d122_2').value=$dp.cal.getP('W','WW');}})"/>
    	<div id="container" style="max-width:800px;height:400px"></div>
    </div>
	</div>
  </body>
</html>
