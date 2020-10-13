<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<base href="<%=basePath%>">
<link rel="stylesheet" href="static/jquery_easyui/themes/default/easyui.css">
<link rel="stylesheet" href="static/jquery_easyui/themes/icon.css">
<script type="text/javascript" src="static/jquery/jquery-1.7.2.min.js"> </script>
<script type="text/javascript" src="static/jquery/jquery.cookie.js"> </script>
<script type="text/javascript" src="static/common/common.js"> </script>
<script type="text/javascript" src="static/common/common_easyui.js"> </script>
<script type="text/javascript" src="static/common/common_jquery.js"> </script>
<script type="text/javascript" src="static/jquery_easyui/jquery.easyui.min.js"> </script>
<script type="text/javascript" src="static/jquery_easyui/locale/easyui-lang-zh_CN.js"> </script>
<script type="text/javascript" src="static/common/validatebox_rules.js"> </script>
<div id="dialogDiv" class="easyui-dialog"  data-options="resizable:true,modal:true,closed:true,onResize:function(){$(this).dialog('center');}">
      
</div>
<div id="dialogDivTwo" class="easyui-dialog"  data-options="resizable:true,modal:true,closed:true,onResize:function(){$(this).dialog('center');}">
   <iframe id="dialogIframeTwo" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>
</div>

<style>
<!--
.requiredField {
color:#F00;
}
-->
</style>

<script type="text/javascript">
jQuery.ajaxSettings.traditional = true;
var basePath = "${basePath }";
</script>