<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=0.67,maximum-scale=0.67,minimum-scale=0.67" />
<title>我的欢乐生活 - ${websiteName}</title>
<link href="${templatePathMobile}/css/common.css" rel="stylesheet"
	type="text/css" />

<script type="text/javascript">
	var $$ = function(id) {
		return "string" == typeof id ? document.getElementById(id) : id;
	};
	function checknull(){
		if($$("bcaption").value.length>3)
			return true;
		else{
			$$("prompt").style.display="block";
			var t=setTimeout(function(){$$("prompt").style.display="none"},2000);
		}
	}
</script>
</head>
<body>
	<div data-role="page" data-theme="f" class="page">
		<div id="g_header">
			<div class="g_left_nav_toolbar">
				<ul>
					<li><button class="g_nav_btn" onclick="history.go(-1);">取消</button></li>
				</ul>
			</div>
			<div class="g_middle_nav_toolbar">写心情</div>
			<div class="g_right_nav_toolbar">
				<ul>
				</ul>
			</div>
		</div>
		<div id="g_tips" onclick="closeTipsExp();"></div>
		<div id="g_isrcollWrapper" class="addContent">
			<div data-role="content">
				<div id="g_publishbox">
				<div id="prompt" style="display:none;text-align:center;height:30px;line-height:30px;">内容太少了</div>
					<form name="addform" method="post" action="mobile/Bills.yy">
						<div class="bill_add width100" style="padding:0;">
									<textarea type="text" id="bcaption"
										name="bcaption" maxlength="" style="width:100%;height:700px;margin:0;padding:0;overflow:hidden;"/></textarea>
							<input type="hidden" name="onlyaddmood" value="onlyaddmood"/>
								<input type="hidden" name="a" value="add" />
						</div>
					</form>
				</div>
			</div>
		</div>
		<div id="g_toolbar" class="g_toolbar">
			<div class="right_block">
				<button class="publish_btn"
					onclick="javascript:if(checknull()){document.addform.submit();}"
					id="publish_btn">添加</button>
			</div>
		</div>
	</div>
	<script>
		function addlistennerMain(obj, eve, fun) {
			if (document.addEventListener) {// 如果是Firefox
				obj.addEventListener(eve, fun, true);
			} else {
				obj.attachEvent("on" + eve, fun);
			}
		}
		addlistennerMain($$("bio"), "change", bioChange);
		function bioChange() {
			/*无金钱时，自动为0并不可编辑*/
			if ($$("bio").value == 5) {
				$$("bamount").value = 0;
				$$("bamount").setAttribute("type", "hidden");
			} else {
				$$("bamount").setAttribute("type", "text");
			}
		}
	</script>
	<script>
		var templatePath = "${templatePath}";
	</script>
	<script src="${templatePathMobile}/js/ejectdiv.js"></script>
</body>
</html>
