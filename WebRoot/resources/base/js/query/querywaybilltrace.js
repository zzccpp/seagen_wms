$(function() {
	// 初使化 DataGrid组件与请求地址
	
	document.onkeydown = function() {
		if (event.keyCode == 13) {
			gridLoad();
		}
	};
	
	// 首次查询
	// gridExport(getParameter());
});

$('#packagecode').bind('keypress', function (event) {
    if (event.keyCode == "13") {
    	gridLoad();
    }
});

// 获取相关参数
function getParameter() {
	var parameter = {
		waybillcode : $('#waybillcode').val(),
		timestamp : tempPara()
	};
	return parameter;
}

// 双击行事件
function dblclickrow(index, row) {
	return;
}

function sleep(n) {
    var start = new Date().getTime();
    while(true)  if(new Date().getTime()-start > n) break;
}

// dataGrid数据加载
function gridLoad() {
	//获取运单号
	var waybillCode = $.trim($('#waybillcode').val());
	if (($('#waybillcode').val() == null) || ($('#waybillcode').val() == "")) {
		//alert("请输入条码信息！！！");
		 $.messager.show({
             title:'提示',
             msg:'请输入条码信息！！！',
             showType:'fade',
             style:{
                 right:'',
                 bottom:''
             }
         });
		return;
	}
	showprogress();
	//查询分拣轨迹信息
	$.ajax({
		async: false,//同步
		type:"POST",
		url:"querywaybilltrace.do",
		data:"waybillCode="+waybillCode,
		success:function(r){
			closeprogress();
			var temp="";
			var routerStatus="";
			var scanStatus=0;
			var scanStatusStr="";
			var sortStatus="";
			var jbStatus="";
			var upLoadStatus="";
			if(r!=null&&r!=""){
				//解析数据
				if(r.waybill_status=="0"){
					routerStatus="<span style='color:blue;'>正常运单</span>";
				}else{
					routerStatus="<span style='color:red;'>取消运单</span>";
				}
				scanStatus=r.scan_status;
				switch (scanStatus)
				{
				case 0:
					scanStatusStr="<span style='color:blue;'>正常扫描</span>";
				  break;
				case 1:
					scanStatusStr="<span style='color:red;'>无分拣方案</span>";
				  break;
				case 2:
					scanStatusStr="<span style='color:red;'>无数据</span>";
				  break;
				case 3:
					scanStatusStr="<span style='color:red;'>无读</span>";
				  break;
				case 4:
					scanStatusStr="<span style='color:red;'>取消</span>";
				  break;
				case 5:
					scanStatusStr="最大圈数";
				  break;
				case 6:
					scanStatusStr="<span style='color:red;'>格口错</span>";
				  break;
				case 7:
					scanStatusStr="<span style='color:red;'>多条码</span>";
				  break;
				default:
					scanStatusStr="<span style='color:red;'>未知</span>";
					break;
				}
				if(r.sorting_status==0){
					sortStatus="<span style='color:blue;'>正常分拣</span>";
				}else{
					sortStatus="<span style='color:red;'>找不到格口</span>";
				}
				if(r.jb_status==0){
					jbStatus="未建包";
				}else if(r.jb_status==1){
					jbStatus="<span style='color:blue;'>已建包</span>";
				}else{
					jbStatus="<span style='color:red;'>不可建包(综合格口异常格口)</span>";
				}
				if(r.jb_update_flag==0){
					upLoadStatus="未上传";
				}else if(r.jb_update_flag==1){
					upLoadStatus="<span style='color:blue;'>已上传</span>";
				}else{
					upLoadStatus="<span style='color:red;'>不可上传</span>";
				}
				
				temp = "<div style='margin-top: 10px;'>1. 本地接收时间【"+r.receive_time+"】, 运单状态【"+routerStatus+"】, 匹配站点编码【"+r.site_code+"】, 当前分拣方案下预分配目标格口号【 <span style='color:blue;'>"+r.re_mark+"</span>】</div>";
				if(r.supply_time!=null&&r.supply_time!="null"){
					temp=temp+"<div>2. 导入台上件时间【"+r.supply_time+"】,【"+r.supply_num+"号】导入台上件</div>";
				}else{
					temp=temp+"<div style='color:red;'>2. 无导入记录</div>";
				}
				if(r.scan_time!=null && r.scan_time!="null"){
					temp=temp+"<div>3. 龙门架扫描时间【"+r.scan_time+"】, 扫描次数【"+r.scan_cycle+"】, 扫描状态【"+scanStatusStr+"】</div>";
				}else{
					temp=temp+"<div style='color:red;'>3. 无扫描记录</div>";
				}
				if(r.sorting_time!=null && r.sorting_time!="null"){
					temp=temp+"<div>4. 分拣时间【"+r.sorting_time+"】, 分拣落格格口号【<span style='color:blue;'>"+r.chute_num+"</span>】, 站点编码【"+r.site_code+"】, 分拣状态【"+sortStatus+"】</div>";
				}else{
					temp=temp+"<div style='color:red;'>4. 无分拣记录</div>";
				}
				if(r.jb_time!=null && r.jb_time != "null"){
					temp=temp+"<div>5. 建包时间【"+r.jb_time+"】,建包状态【"+jbStatus+"】, 站点编码【"+r.site_code+"】, 箱号【"+r.box_code+"】</div>";
				}else{
					temp=temp+"<div style='color:red;'>5. 无建包记录</div>";
				}
				if(r.jb_update_time!=null && r.jb_update_time != "null"){
					temp=temp+"<div>6. 上传时间【"+r.jb_update_time+"】,上传状态【"+upLoadStatus+"】, 站点编码【"+r.site_code+"】, 箱号【"+r.box_code+"】</div>";
				}else{
					temp=temp+"<div style='color:red;'>6. 无上传记录</div>";
				}
				var resultList=temp;
				$("#result_search").html("运单整个过程轨迹分析结果:"+resultList);
				$("#result_search").css("color","black");
			}else{
				$("#result_search").html("无运单轨迹");
				$("#result_search").css("color","red");
			}
		}
	});
}