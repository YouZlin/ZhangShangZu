<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();//动态获得项目根目录
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />

<script type="text/javascript" src="<%=path %>/lib/html5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/respond.min.js"></script>
<script type="text/javascript" src="<%=path %>/lib/PIE_IE678.js"></script>

<link href="<%=path %>/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="<%=path %>/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/style.css" rel="stylesheet" type="text/css" />
<!-- [if IE 6]> -->
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<!-- <![endif] -->

<script type="text/javascript" src="<%=path %>/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path %>/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="<%=path %>/js/H-ui.js"></script> 
<script type="text/javascript" src="<%=path %>/js/H-ui.admin.js"></script> 