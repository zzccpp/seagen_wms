<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 网站图标 -->
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />

<%-- 引入JQuery --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jquery-easyui-1.5.3/jquery.min.js"></script>
<%-- 引入EasyUI --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<%-- 引入EasyUI的中文国际化js，让EasyUI支持中文 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
<%-- 引入EasyUI的样式文件--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/jquery-easyui-1.5.3/themes/default/easyui.css" type="text/css"/>
<%-- 引入EasyUI的图标样式文件--%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/jquery-easyui-1.5.3/themes/icon.css" type="text/css"/>

<%-- 引入 highcharts--%>
<script src="${pageContext.request.contextPath}/resources/plugins/Highcharts-6.0.4/code/highcharts.js?v=3"></script>

<%-- 引入 My97DatePicker--%>
<script src="${pageContext.request.contextPath}/resources/plugins/My97DatePicker/WdatePicker.js"></script>

<!-- 全局可共用的方法 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/common/common.js?v=3"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/common/ajaxcommon.js?v=1"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/common/datecommon.js?v=1"></script>

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/base/css/common.css?v=1">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/common/extEasyUI.js?v=3"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/base/js/common/EasyUIDataGrid.js?v=1"></script>