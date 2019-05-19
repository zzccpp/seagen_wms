/**
 * 查询同一导入台上件的前后件
 * @param supply_Id 导入台号
 * @param supply_time 上件时间
 * @param index 当前件的主键
 */
function queryScanBeforeOrAfter(supplyId,supplyTime,index){
	if($.trim($("#scan_"+index).html())!=''){
		return;//如果已经有结果,不在进行查询
	}
	$.ajax({
		async: false,//同步
		type:"POST",
		url:"queryScanBeforeOrAfter.do",
		data:"supplyId="+supplyId+"&supplyTime="+supplyTime,
		success:function(jsonArr){
			//console.info(jsonArr);
			var message="";
			var firstArr="";
			var SecondArr="";
			var before = "<div style='margin-top: 3px;margin-left: 20px;color:#6699ff'>前一单 :";
			var after = "<div style='margin-top: 3px;margin-left: 20px;color:#6699ff'>后一单 :";
			
			if(jsonArr.length==0){//都没有记录
				before +="无记录!";
				after += "无记录!"
			}else if(jsonArr.length==1){//只有一条记录
				if(jsonArr[0].type==1){
					before += parseCollage(jsonArr[0]);
					after += "无记录!"
				}else if(jsonArr[0].type==2){
					before +="无记录!";
					after += parseCollage(jsonArr[0]);
				}
			}else if(jsonArr.length==2){//有前后记录
				before += parseCollage(jsonArr[0]);
				after += parseCollage(jsonArr[1]);
			}
			before+="</div>";
			after+="</div>";
			$("#scan_"+index).html(before+after);
		}
	});
}
/**
 *扫描原消息解析拼装
 * 返回 拼装后的语句
 */
function parseCollage(json){
	return " 运单号【  "+json.package_code+"  】,导入台【   "+json.supply_id+"  】,目标格口【  "+json.chute_id+"  】,上件时间【  "+json.supply_time+"  】,站点编码【  "+json.site_code+"  】";
}
/**
 * 查询当前小车前后的小车，在临近的扫描时间的分拣记录
 * @param carId
 * @param scanTime
 */
function queryCarBeforeOrAfter(carId,scanTime,index){
	if($.trim($("#sort_"+index).html())!=''){
		return;//如果已经有结果,不在进行查询
	}
	//2017-10-24 17:10:19.864545986 截取2017-10-24 17:10:19
	if(scanTime.indexOf(".")!=-1){
		scanTime=scanTime.substring(0,scanTime.indexOf("."));
		//console.info("截取后的数据:"+scan_time);
	}
	$.ajax({
		async: false,//同步
		type:"POST",
		url:"queryCarBeforeOrAfter.do",
		data:"carId="+carId+"&scanTime="+scanTime,
		success:function(jsonArr){
			//console.info(jsonArr);
			var before = "";
			var after = "";
			for(var i=0;i<jsonArr.length;i++){
				var temp="运单号【  "+jsonArr[i].package_code+"  】,小车号【  "+jsonArr[i].car_id+"  】,分拣格口【  "+jsonArr[i].chute_id+"  】,扫描时间【"+jsonArr[i].scan_time+"】,分拣时间【  "+jsonArr[i].sorting_time+"  】,最大圈数【  "+jsonArr[i].scan_cycle+"  】";
				if(jsonArr[i].type==11 || jsonArr[i].type==12){
					before+="<div style='margin-top: 3px;margin-left: 20px;color:#6699ff'>前一小车相邻扫描时间分拣记录:";
					before+=temp+"</div>";
				}else if(jsonArr[i].type==21 || jsonArr[i].type==22){
					after+="<div style='margin-top: 3px;margin-left: 20px;color:#6699ff'>后一小车相邻扫描时间分拣记录:";
					after+=temp+"</div>";
				}
			}
			$("#sort_"+index).html(before+after);
		}
	});
}
/**
 * 查询分析
 */
function gridLoad(){
	//获取运单号
	var packageCode = $.trim($('#packageCode').val());
	if(''==packageCode){
		$.messager.show({
			title:'提示消息',
			msg:'请输入你要查询的运单号',
			timeout:1000,
			showType:'slide',
			style:{
				right:'',
				top:document.body.scrollTop+document.documentElement.scrollTop,
				bottom:''
			}
		});
		return false;
	}
	var isContinue = false;
	//查询快手扫描消息
	$.ajax({
		async: false,//同步
		type:"POST",
		url:"queryScanWaybill.do",
		data:"packageCode="+packageCode,
		success:function(jsonArr){
			//console.info(jsonArr);
			if(jsonArr.length>0){
				var resultList = "";
				var temp = "";
				var count = 1;
				//解析数据
				for(var i=0;i<jsonArr.length;i++){
						temp = "<div style='margin-top: 10px;'>第"+(count++)+"次扫描: 运单号【  "+jsonArr[i].package_code+"  】,导入台【  "+jsonArr[i].supply_id+"  】,目标格口【  "+jsonArr[i].chute_id+"  】,上件时间【  "+jsonArr[i].supply_time+"  】,站点编码【  "+jsonArr[i].site_code+"  】";
						temp += "<a href='javascript:void(0);' style='text-decoration:underline;color:blue;padding-left: 20px;' onclick='queryScanBeforeOrAfter("+jsonArr[i].supply_id+",\""+jsonArr[i].supply_time+"\","+count+")'>显示前后快件扫描</a></div>";
						//显示前后的div
						temp += "<div id='scan_"+count+"'></div>";
					resultList+=temp;
				}
				$("#result_scan").html("扫描记录:"+resultList);
				$("#result_scan").css("color","black");
				isContinue = true;
			}else{
				isContinue = false;
				$("#result_scan").html("未查询到记录");
				$("#result_scan").css("color","red");
				$("#result_sort").html("");
			}
		}
	});
	//查询分拣记录{package_code,car_id,chute_id,scan_time,sorting_time,scan_cycle}
	if(isContinue){
		$.ajax({
			async: false,//同步
			type:"POST",
			url:"querySortWaybill.do",
			data:"packageCode="+packageCode,
			success:function(jsonArr){
				//console.info(jsonArr);
				if(jsonArr.length>0){
					//解析数据
					var temp="";
					var resultList="";
					var count=1
					for(var i=0;i<jsonArr.length;i++){
						temp="<div style='margin-top: 10px;'>第"+(count++)+"次分拣:"
						temp += " 运单号【  "+jsonArr[i].package_code+"  】,小车号【  "+jsonArr[i].car_id+"  】,分拣格口【  "+jsonArr[i].chute_id+"  】,扫描时间【"+jsonArr[i].scan_time+"】,分拣时间【  "+jsonArr[i].sorting_time+"  】,最大圈数【  "+jsonArr[i].scan_cycle+"  】";
						temp += "<a href='javascript:void(0);' style='text-decoration:underline;color:blue;padding-left: 20px;' onclick='queryCarBeforeOrAfter("+jsonArr[i].car_id+",\""+jsonArr[i].scan_time+"\","+count+")'>显示前后小车分拣信息</a></div>";
						//显示前后的div
						temp += "<div id='sort_"+count+"'></div>";
						resultList+=temp;
					}
					$("#result_sort").html("分拣记录:"+resultList);
					isContinue = true;
				}else{
					$("#result_sort").html("未查询到分拣记录");
					$("#result_sort").attr("color","red");
				}
			}
		});
	}
}