<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MQ测试</title>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/test/mqtest.js?v=1"></script>
</head>
<body>
	1.发送开关机MQ测试<br/>
	<div>
		<span>
			<select id="flag" class="easyui-combobox" style="width:200px;">   
		    	<option value="0">开机</option>   
		     	<option value="1">关机</option>
			</select>
     	</span> 
     	<span>
			<select id="reset" class="easyui-combobox" style="width:200px;">   
		    	<option value="0">旧批次</option>   
		     	<option value="1">新批次</option>
			</select>
     	</span>
     	<a href="javascript:sendOnAndOffMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
	2.设置分拣模式MQ测试<br/>
	<div>
		<input id="sortModeName" class="easyui-textbox" value="sorting" style="width:100px"/> 
     	<a href="javascript:sendSetSortModeMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
	3.设置流水号MQ测试<br/>
	<div>
		<input id="lineNum" class="easyui-textbox" value="201704" style="width:100px"/> 
     	<a href="javascript:sendSetlineNumMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
	4.发送开关特性设备控制 MQ测试<br/>
	<div>
		<span>
			设备类型:
			<select id="devId" class="easyui-combobox" style="width:200px;">   
		    	<option value="0">未知设备</option>   
		     	<option value="1">小车</option>
		     	<option value="2">导入台</option>
		     	<option value="3">龙门架</option>
		     	<option value="4">格口</option>
			</select> 
     	</span>
     	问题描述:
     	<input id="ctrlData" class="easyui-textbox" style="width:200px"/>
     	<a href="javascript:sendCtrlComMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
	5.扫描与分拣MQ测试<br/>
	<div>
		发送次数：<input id="ss_count" type="text" class="easyui-numberbox" value="100" data-options="min:1"/>
		循环次数（循环执行，批次测试）：<input id="ss_do_count" type="text" class="easyui-numberbox" value="1" data-options="min:1"/>
		<a href="javascript:sendScanAndSortMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
	6.建包请求 MQ测试<br/>
	<div>
		<span>
			请求类型类型:
			<select id="type" class="easyui-combobox" style="width:200px;">   
		    	<option value="0">正常请求</option>   
		     	<option value="1">格口请求</option>
		     	<option value="2">箱号请求</option>
		    </select>
     	</span>
     	格口号:
     	<input id="chuteNum" class="easyui-numberbox" style="width:100px" data-options="min:1,max:88"/>
     	<span>
			是否全选:
			<select id="jbtype" class="easyui-combobox" style="width:200px;">   
		     	<option value="0">单一格口</option>
		    	<option value="1">全部格口</option>   
		    </select>
     	</span>
     	<a href="javascript:sendJBMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
	7.称重量方MQ测试<br/>
	<div>
		发送次数：<input id="volume_count" type="text" class="easyui-numberbox" data-options="min:1"/>
		<a href="javascript:sendVolumeMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
	8.事件 MQ测试<br/>
	<div>
		<span>
			事件类型:
			<select id="eventId" class="easyui-combobox" style="width:200px;">   
		    	<option value="1">线体运行开机</option>   
		     	<option value="2">线体运行关机</option>
		     	<option value="3">远程连接成功</option>
		     	<option value="4">远程连接失败</option> 
		     	<option value="5">小车禁用启用</option> 
		     	<option value="6">格口禁用启用</option> 
		     	<option value="7">龙门架禁用启用</option> 
		     	<option value="8">导入台禁用启用</option> 
		    </select>
     	</span>
     	具体描述:
     	<input id="eventVal" class="easyui-textbox" style="width:300px"/>
     	<a href="javascript:sendEventMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
	9.日志 MQ测试<br/>
	<div>
		<span>
			日志类型:
			<select id="logId" class="easyui-combobox" style="width:200px;">   
		    	<option value="1">开机</option>   
		     	<option value="2">关机</option>
		     	<option value="3">登录</option>
		     	<option value="4">退出</option> 
		     	<option value="5">下载分拣方案</option> 
		     	<option value="7">修改密码</option> 
		     	<option value="8">远程连接</option>
		     	<option value="9">箱号</option> 
		     	<option value="10">分拣模式</option> 
		     	<option value="11">流水线号</option> 
		     	<option value="12">业务队列</option> 
		     	<option value="13">内存数据</option> 
		     	<option value="14">下载运单数据</option> 
		     	<option value="15">HTTP数据请求</option> 
		     	<option value="16">报表查询</option> 
		     	<option value="17">建包请求</option> 
		     	<option value="18">设备控制</option> 
		    </select>
     	</span>
     	日志描述:
     	<input id="logVal" class="easyui-textbox" style="width:300px"/>
     	<a href="javascript:sendLogMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div>
	10.清空数据表，重新测试<br/>
	<div>
		<span>
			清空数据：
		</span>
			<a href="javascript:sendEmptyTable()" class="easyui-linkbutton">点击清空</a> 
	</div><br/>
	
	11.API测试<br/>
	<div>
		<span>
			测试类型：
		</span>
		<select id = "apitype" >
			<option value="0">报表数据测试</option>
			<option value="1">用户验证测试</option>
		</select>
		<span>
			id：
		</span>
		<input id="reportid" class="easyui-textbox" value ="801" style="width:50px"/>
		<span>
			开始时间：
		</span>
		<input id="startdate" class="easyui-textbox" value ="2017-04-25 15:53:10" style="width:150px"/>
		<span>
			结束时间：
		</span>
		<input id="enddate" class="easyui-textbox" value ="2017-04-25 17:53:10" style="width:150px"/>
		
		<span>
			rule：
		</span>
		<input id="rule" class="easyui-textbox" value ="0" style="width:50px"/>
		
		<span>
			用户名：
		</span>
		<input id="username" class="easyui-textbox" value ="admin" style="width:50px"/>
		<span>
			密码：
		</span>
		<input id="password" class="easyui-textbox" value ="123456" style="width:50px"/>
		
		<a href="javascript:testReportApi()" class="easyui-linkbutton">点击测试</a> 
		
		<br/>
		<span>
			返回结果：
		</span><br/>
		<textarea id = "result" rows="20" cols="100"></textarea>
	</div><br/>
	12.请求格口MQ测试<br/>
	<div>
		运单号：<input id="package_code" type="text" class="easyui-textbox" style="width:100px"/>
		<a href="javascript:sendGetChuteMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
	13.推送格口(满包,空载)MQ测试<br/>
	<div>
		格口号:
     	<input id="stateChuteNum" class="easyui-numberbox" style="width:100px" data-options="min:1,max:88"/>
		<span>	
			格口状态:
			<select id="chuteState" style="width:100px;">   
			    <option value="0">空载</option>
			    <option value="1">满包</option>
			</select>
		</span>
		<a href="javascript:sendChuteStateMQ()" class="easyui-linkbutton">发送MQ</a> 
	</div><br/>
</body>
</html>