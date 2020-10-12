<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>行程详细页 - ${websiteName}</title>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link href="${templatePath}/css/bills.css" rel="stylesheet" />
<link href="${templatePath}/css/operate.css" rel="stylesheet" />
<script type="text/javascript" src="${templatePath}/js/modify_r.js"></script>
</head>
<body>
<%@include file="global_topbar.jsp" %>
<div class="header">
</div>
<div class="container">
<div class="bills">
  <div class="lcontent">
    <h1 style="font-size:27px;text-align:center;">详细记录：您的旅行之“${leadername.btypename}”</h1>
    <form name="deleteform" method="post" action="Bills.yy">
    <input type="hidden" name="a" value="delete"/>
    <input type="hidden" id="b" name="b" value=""/></form>
    <div class="bill_list" id="bill_list">
<div class="ui_recent_footer">
<a title="查看所有交易记录" class="more" href="Records.yy" target="_blank">查看所有记录</a>
</div>
    </div>
  </div>
  <div class="rcontent">
    <div class="calendar"><%@include file="../html/Calendar3.html" %></div>
    <div class="userinfo"></div>
  </div></div>

    <div id="modify_form" class="modify_form">
	<div id="modify_add_block" class="bill_add_block">
	<form name="modifyform" method="post" action="Bills.yy">
        <div class="bill_add">
      <p>修改记账：</p><div class="bill_add_h"><p>
      您于&nbsp;<span id='thetime'>
    <input type="text" name="datetime_m" id="datetime_m" readonly="readonly" value=""/></span>&nbsp;因为<input class="b_type" type="text" id="btype_m" name="btype_m" value="吃饭 购物等等" readonly/><select class="b_io" id="bio_m" name="bio_m">
            <option value="">&nbsp;</option>
            <option value="1">支出</option>
            <option value="2">收入</option>
            <option value="3">债务</option>
            <option value="4">债权</option>
            <option value="5">无金钱</option></select><input class="b_amount" type="text" id="bamount_m" name="bamount_m" value="金额"/>元，特此记录！</p><div><textarea class="b_caption" type="text" id="bcaption_m" name="bcaption_m"/>再说点什么……</textarea></div>
        </div>
        <div class="bill_add_s">
    <input type="hidden" name="a" value="modify"/>
        <input type="button" value="取消" onclick="javascript:document.getElementById('modify_form').className='modify_form';	document.getElementById('modify_mask').className='modify_mask';"/>
       <input type="button" value="保存" onclick="javascript:if(checknull_m()){document.modifyform.submit();}"/>
         <span id="checknullprompt_m"></span>
        </div></div>
    <input type="hidden" id="b_modify" name="b" value=""/></form>
    </div>
	</div>
    <div id="modify_mask" class="modify_mask"></div>
</div>
<%@include file="../jsp/global_footer.jsp" %>
<%@include file="../html/modbacktop.html" %>
<script>
var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};
function addlistennerMain(obj, eve, fun) {
	if (document.addEventListener) {// 如果是Firefox
		obj.addEventListener(eve, fun, true);
	} else {
		obj.attachEvent("on"+eve, fun);
	}
}
addlistennerMain($$("bio_m"),"change",bio_mChange);
function bio_mChange(){
	/*无金钱时，自动为0并不可编辑*/
	if($$("bio_m").value==5){$$("bamount_m").value=0;$$("bamount_m").setAttribute("type","hidden");}
	else{$$("bamount_m").setAttribute("type","text");}
}

/**使用class名称获取元素*/
var $c=function getElementsByClassName(n) {
	   var classElements = [],allElements = document.getElementsByTagName('*');
	   for (var i=0; i< allElements.length; i++ )
	  {
	      if (allElements[i].className == n ) {
	          classElements[classElements.length] = allElements[i];
	       }
	  }
	  return classElements;
	}

/*用于把图片显示出来，默认先不显示图片。显示小图*/
function displayimage(){
	var locateimg_img_list=$c("locateimg_img");
	for(var i = 0; i < locateimg_img_list.length; i++) {
		var src=locateimg_img_list[i].getAttribute("data");
		var srcs=src.split("\.");
		src=srcs[0]+"small."+srcs[1];
		locateimg_img_list[i].setAttribute("src",src);
		locateimg_img_list[i].style.display="block";
		locateimg_img_list[i].onclick=function(){displayorgimg(this.getAttribute("data"));};
	}
}
displayimage();

/*这里的变量来自于后台，是提供给下面的ejectdiv.js和weibolist.js使用的*/
var templatePath="${templatePath}";
var listjson='${listjson}';
var u="${sessionScope.UOID}";
/*点击小图显示大图*/
document.write('<div id="displayorgimg" style="position:fixed;width:100%;height:100%;display:none;top:0;" ><img title="点击隐藏"  style="position:relative;display:block;z-index:100;"/><div style="background-color:#000000;height: 100%; position: fixed; width: 100%; left: 0px; top: 0px; z-index: 99;"></div></div>');
function displayorgimg(imgpath){
	var locateimg_img_list=$$("displayorgimg").getElementsByTagName("img");;
	for(var i = 0; i < locateimg_img_list.length; i++) {
		var t=locateimg_img_list[i];	
		t.setAttribute("src",templatePath+"/img/loading.gif");
		$$("displayorgimg").style.display="block";
		t.onclick=function(){$$("displayorgimg").style.display="none";};
		t.style.width="16px";
		t.style.top=(window.screen.availHeight*1-16)/3+"px";
		t.style.margin="auto";
		var whmax;
		var obj=new Image();
		obj.src=imgpath;
		obj.onload=function(){
			t.style.width="";
			/*需要把图片设置到可见区域*/
			(obj.width*1-window.screen.availWidth*1)>(obj.height*1-window.screen.availHeight*1)?(obj.width*1>window.screen.availWidth*1?t.style.width=(window.screen.availWidth+"px"):t.style.width=(obj.width+"px")):(obj.height*1>window.screen.availHeight*1?t.style.height=(window.screen.availHeight+"px"):t.style.height=(obj.height+"px"));
			t.setAttribute("src",imgpath);
			(obj.height*1-window.screen.availHeight*1)<0?t.style.top=(window.screen.availHeight*1-obj.height*1)/3+"px":t.style.top=0+"px";
		}
	}
}
</script>
<script type="text/javascript" src="${templatePath}/js/weibolist.js"></script>
</body>
</html>