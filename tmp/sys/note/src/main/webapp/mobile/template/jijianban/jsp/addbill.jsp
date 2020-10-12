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
<meta name="viewport" content="width=device-width,initial-scale=0.67,maximum-scale=0.67,minimum-scale=0.67" />
<title>我的欢乐生活 - ${websiteName}</title>
<link href="${templatePathMobile}/css/common.css" rel="stylesheet" type="text/css" />
<!--  <script src="${templatePathMobile}/js/iscroll.js"></script>-->

<script type="text/javascript">
var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};
function addtravel(){
	if(!$$("addtravelbox").checked){
		$$("travelleader").setAttribute("disabled","disabled");
	}else if($$("addtravelbox").checked){
			$$("travelleader").removeAttribute("disabled");
		}
}
function addsubmit(){
	if($$("addtravelbox").checked&&$$("dataInput").value==""){
		alert("请选择一次行程！");
			return false;
		}else{return true;}
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
			<div class="g_middle_nav_toolbar">新记录</div>
			<div class="g_right_nav_toolbar">
				<ul>
				</ul>
			</div>
		</div>
		<div id="g_tips" onclick="closeTipsExp();"></div>
		<div id="g_isrcollWrapper" class="addContent">
			<div data-role="content">
				<div id="g_publishbox">
					<form name="addform" method="post" action="mobile/Bills.yy">
		<div class="bill_add">
			<%-- <div class="startnew">开始你的新记账</div> --%>
			<div class="addbody">
			<ul>
				<li>
					因为：<input class="b_type" type="text" id="btype" name="btype" value="吃饭 购物等等" maxlength="50" readonly onclick="new ejectBtypeDiv().show(this)"/><input type="hidden" id="btypename" name="btypename" value="27"/>
				</li>
				<li>出入：<select class="b_io" id="bio" name="bio">
						<option value="">&nbsp;</option>
						<option value="1">支出</option>
						<option value="2">收入</option>
						<option value="3">债务</option>
						<option value="4">债权</option>
            			<option value="5">无金钱</option>
					</select>
				</li>
				<li>金额：<input class="b_amount" type="text" id="bamount" name="bamount"
						value="金额" />元
				</li>
				<li>
					<span title="你可以添加一次行程，并向行程中添加记账">添加到行程：</span><input
						class="inputcb" type="checkbox" id="addtravelbox" name="travel"
						value="travel" title="添加到行程" onchange="addtravel();" />
						<input class="b_amount"
						id="travelleader" name="travelleader" disabled="" readonly=""
						value="添加行程的名称" type="text" onclick="new ejectDiv().show(this)" />
					<input id="dataInput" type="hidden" name="btid" />
				</li><li>再说点什么：</li>
				<li class="txt">
					<textarea class="b_caption" type="text" id="bcaption" name="bcaption"/></textarea>
				</li>
				</ul>
			</div>
			<div class="bill_add_s">
				<input type="hidden" name="a" value="add" /><%--<input type="button"
					value="保存" onclick="javascript:if(checknull()&&addsubmit()){document.addform.submit();}" /> --%> <span
					id="checknullprompt" style="color:#d64f44"></span>
			</div>
		</div>
	</form>
			</div>
		</div>
	</div>
	<div id="g_toolbar" class="g_toolbar">
		<div class="right_block">
			<button class="publish_btn" onclick="javascript:if(checknull()&&addsubmit()){document.addform.submit();}" id="publish_btn">添加</button>
		</div>
	</div>
	</div>
<script>
function addlistennerMain(obj, eve, fun) {
	if (document.addEventListener) {// 如果是Firefox
		obj.addEventListener(eve, fun, true);
	} else {
		obj.attachEvent("on"+eve, fun);
	}
}
addlistennerMain($$("bio"),"change",bioChange);
function bioChange(){
	/*无金钱时，自动为0并不可编辑*/
	if($$("bio").value==5){$$("bamount").value=0;$$("bamount").setAttribute("type","hidden");}
	else{$$("bamount").setAttribute("type","text");}
}
</script>
<script>
var templatePath="${templatePath}";
</script>
<script type="text/javascript" src="${templatePathMobile}/js/modify.js"></script>
<script src="${templatePathMobile}/js/ejectdiv.js"></script>
</body>
</html>
