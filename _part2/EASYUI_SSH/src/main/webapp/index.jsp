<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>

<link href="easyui/themes/bootstrap/easyui.css" rel="stylesheet" type="text/css" />
<link href="easyui/themes/icon.css" rel="stylesheet" type="text/css" />


<script src="easyui/jquery.min.js" type="text/javascript"></script>
<script src="easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="easyui/jquery.portal.js" type="text/javascript"></script>
<script src="css/echarts-all.js" type="text/javascript"></script>


</head>
<body class="easyui-layout" >

	<div data-options="region:'north',border:'true',height:'80px',href:'views/layout/north.jsp'" style="overflow: hidden;" split="true" ></div>
    <div  split="true" title="导航菜单" data-options="region:'west',href:'views/layout/west.jsp'" style="width: 200px;"></div>
	<div id="mainPanle" data-options="region:'center',href:'views/layout/center.jsp'" style="overflow: hidden;"></div>
	<div align="center" data-options="region:'south',href:'views/layout/south.jsp'" border="true" split="true" style="overflow: hidden; height: 30px;"></div>
</body>
</html>
