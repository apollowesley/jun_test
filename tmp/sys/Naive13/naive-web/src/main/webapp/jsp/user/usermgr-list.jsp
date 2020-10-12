<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.inc.jsp"%>
<html>
<head>
<title>用户管理-列表</title>
<%@ include file="/common/head.inc.jsp"%>
<link rel="stylesheet" type="text/css" href="${webroot}/styles/js/flexigrid/flexigrid.css" />
<link rel="stylesheet" type="text/css" href="${webroot}/styles/css/datagrid.css" />
</head>
<body style="margin: 0 auto; background: #eef;">
<div class="wrapper">
	<table id="usermgr_list" class="datalist" cellspacing="0">
	</table>
</div>
</div>
<script type="text/javascript" src="${webroot}/styles/js/jquery/jquery-1.7.2.min.js"></script>	
<script type="text/javascript" src="${webroot}/styles/js/flexigrid/flexigrid.js"></script>	
<script type="text/javascript" src="${webroot}/styles/js/flexigrid/datagrid-strings.js"></script>	
<script type="text/javascript" src="${webroot}/styles/js/usermgr-list.js"></script>	
</body>
</html>
