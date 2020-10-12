<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发起搭伙 - ${websiteName}</title>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link href="${templatePath}/css/bills.css" rel="stylesheet" />

<script type="text/javascript">
var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};
</script>
</head>

<body>
	<%@include file="global_topbar.jsp"%>
<div class="container">
<div class="lcontent bgl">
<form name="joinusadd" method="post">
<div class="titles" id="titles">
<ul>
<li><lable>标题</lable><input type="text" name="title" /><span></span></li>
<li><lable>预计总费用</lable><input type="text" name="cost" /><span></span></li>
<li><lable>预计人数</lable><input type="text" name="thenumofperson" /><span></span></li>
<li style="display:none"><lable>可参与的人</lable><input type="text" name="jaappermission" value="0" /><span></span></li>
<li><lable>截止时间</lable><input type="text" name="jaapdeadline"  maxlength="10"
				onclick="new Calendar().show(this);" readonly="readonly" /><span></span></li>
</ul>
	<textarea id="editor_id" name="content" style="width:665px;height:500px;"></textarea>
	<p class="btn"><a class="btn_a" id="btn_a"><span class="btn_30px f14">发布</span></a></p>
</div>
</form>
</div>
  <div class="rcontent">
    <div class="calendar"><%@include file="../html/Calendar3.html" %></div>
  </div>
</div>
<link rel="stylesheet" href="editor/themes/default/default.css" />
<link rel="stylesheet" href="editor/plugins/code/prettify.css" />
<script charset="utf-8" src="${templatePath}/js/jquery-2.0.3.min.js"></script>
<script charset="utf-8" src="editor/kindeditor-min.js"></script>
<script charset="utf-8" src="editor/lang/zh_CN.js"></script>
<script charset="utf-8" src="editor/plugins/code/prettify.js"></script>
<script type="text/javascript" src="${templatePath}/js/Calendar3.js"></script>
<script charset="utf-8" src="${templatePath}/js/joinusadd.js"></script>
<script>
	var editor;
	KindEditor.ready(function(K) {
	    editor = K.create('#editor_id', {
			cssPath : 'editor/plugins/code/prettify.css',
			uploadJson : 'editor/jsp/upload_json.jsp',
			allowFileManager : false
	    });
    });
	$$('btn_a').onclick=function(){editor.sync();
	var content=$(document.joinusadd).serialize();
	if($("input[name='title']").val()==""||$("input[name='cost']").val()==""||$("input[name='thenumofperson']").val()==""||$("input[name='jaapdeadline']").val()==""||$("input[name='content']").val()=="")
	{
		alert("请先填写完整~");
		return false;
	}
	$.ajax({
		url:'JoinUs.yy?a=add&date='+new Date(),
		type:"POST",
		data: content,
		dataType: "json",
		success: function(data){
			if(data.realSucess==="true")
			{
				alert("发表成功！");
				location.href='/Bills.yy?';
			}else{
				alert(data.msg);            	            		
			}
		}
	});
	};
</script>

	<%@include file="../jsp/global_footer.jsp"%>
	<%@include file="../html/modbacktop.html"%>
	<div style="clear:both;"></div>
</body>
</html>
