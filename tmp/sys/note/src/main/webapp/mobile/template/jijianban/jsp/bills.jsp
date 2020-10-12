<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.util.*,com.hibernate.voDao.Bills" errorPage=""%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=0.67,maximum-scale=0.67,minimum-scale=0.67" />
<title>我的欢乐生活 - ${websiteName}</title>
<link href="${templatePathMobile}/css/bills.css" rel="stylesheet" type="text/css" />
<link href="${templatePathMobile}/css/common.css" rel="stylesheet" type="text/css" />
<script src="${templatePathMobile}/js/iscroll.js"></script>
<%-- <script src="${templatePathMobile}/js/common.js?build+20130408"></script>--%>

<script type="text/javascript">
//tab类型
var TAB_HOME = 101;
var TAB_MESSAGE = 102;
var TAB_PROFILE = 103;
var TAB_SQUARE = 104;
var TAB_MORE = 105;
var PAGE_ADD = 201;
var PAGE_ADDMOOD = 202;
function changeTab(type){
	var url = "";
	var urlnum = "";
	switch (type) {
		case TAB_HOME:
			url = "mobile/Bills.yy";
			urlnum="1";
			break;
		case TAB_MESSAGE:
			url = "mobile/index.jsp";
			urlnum="2";
			break;
		case TAB_PROFILE:
			url = "mobile/index.jsp";
			urlnum="3";
			break;
		case TAB_SQUARE:
			url = "mobile/index.jsp";
			urlnum="4";
			break;
		case TAB_MORE:
			url = "${templatePathMobile}/jsp/more.jsp";
			urlnum="5";
			break;
		case PAGE_ADD:
			url = "${templatePathMobile}/jsp/addbill.jsp";
			urlnum="5";
			break;
		case PAGE_ADDMOOD:
			url = "${templatePathMobile}/jsp/addmood.jsp";
			urlnum="5";
			break;
	}
	location.href = "<%=basePath%>mobile/PageTab.yy?ur="+url+"&urlnum="+urlnum;
}
</script>
</head>
<body>
	 <script type="text/javascript">
		//一些初始化操作
		var PerPage_MBlog = parseInt("20");
		var PerPage_Pm = parseInt("20");
		var PerPage_Member = parseInt("20");
		var PerPage_Def = parseInt("20");
		var Code = "home";
		var Module = "topic";
		var Uid = "2";
		var MobileClient = false;
		var myScroll;
		function loaded() {
			var scrollName = "g_isrcollWrapper";
			if (Module == "topic" && Code == "detail") {
				//scrollName = "";
			}
			if (scrollName != "") {
				myScroll = new iScroll(scrollName, {
					checkDOMChanges : true
				});
			}
		}
		document.addEventListener('touchmove', function(e) {
			e.preventDefault();
		}, false);
		/*document.addEventListener('DOMContentLoaded', function() {
			setTimeout(loaded, 200);
		}, false);*/
		 window.onload=function() {
				setTimeout(loaded, 200);
			}
	</script>
	<div data-role="page" data-theme="f" class="page">
		<div id="g_header">
			<div class="g_left_nav_toolbar"><ul><li><button class="g_nav_btn_edit" onclick="changeTab(PAGE_ADD);">&nbsp;</button></li>
			<li><button class="g_nav_btn_edit_mood" onclick="changeTab(PAGE_ADDMOOD);">&nbsp;</button></li></ul></div>
			<div class="g_middle_nav_toolbar"><%=request.getSession().getAttribute("UName")%></div>
			<div class="g_right_nav_toolbar"><ul><li><button class="g_nav_btn_ref" onclick="location.reload();">&nbsp;</button></li></ul></div>
		</div>
		<div id="g_tips" onclick="closeTipsExp();"></div>
		<div id="g_isrcollWrapper">
			<div id="topic_list_wp" data-role="content">
				<div class="" id="bill_list">
				<%-- <c:forEach items="${list}" var="list" varStatus="status">
					<div class="<c:if test="${status.index%2!=0}">split</c:if>" id="">
						<div class="weibo" data-tid="" data-login="" data-uid=""这里是用户id,js用
							data-huifu="">
							<div class="wb_l">
								<div>
									<img class="author" src="<%@include file="./../uploadavatar/getAvatar.jsp" %>"
										onclick="goToUserInfo(2);" />
								</div>
							</div>
							<div class="wb_r">
								<div class="user_info">
									<span class="fl p_u"><%=request.getSession().getAttribute("UName")%></span> <span class="fr p_t">
										<span><fmt:formatDate value="${list.bdate}"
											pattern="yyyy-MM-dd HH:mm:ss" /></span>
									</span>
								</div>
								<div class="wb_c_wp">
									<div class="wb_c">由于 <span class="wordColor" <c:if test="${list.bbetravelleader}"> style="font-weight:bold;"</c:if><c:if test="${list.btid!=0}"> style="color:#aaa;font-weight:bold;"</c:if>>${list.btypename}</span>
									<c:if test="${list.bio==0}">行程</c:if>
										<c:if test="${list.bio==1}">支出</c:if><c:if test="${list.bio==2}">收入</c:if><c:if test="${list.bio==3}">债务</c:if><c:if test="${list.bio==4}">债权</c:if>
									<span class="wordColor">人民币<c:if test="${list.bio==1}">-</c:if><c:if test="${list.bio==2}">+</c:if><c:if test="${list.bio==3}">--</c:if><c:if test="${list.bio==4}">++</c:if>${list.bamount}元。</span>
									</div>
									<div class="from">
										<span class="fl">说明：${list.bcaption}</span> <span class="fr num"> </span>
									</div>
									<c:if test="${list.bidir!=null}"><div style="position:relative1;" <c:if test="${status.index%2!=0}">class="split"</c:if>><img class="locateimg_img" id="locateimg_img_${status.index}" data="${list.bidir}"/></div></c:if>
								</div>
							</div>
						</div>
						<div class="wb_line"></div>
					</div>
					</c:forEach> --%>					
				</div>
				<div class="wb_more" id="btn_more">更多...</div>
				<div style="margin-bottom:80px;"></div>
				<%-- <script language="javascript">
					$(document).ready(function() {
						setMBlogListEvent();
					});
				</script>--%>
			</div>
		</div>
		<div id="g_footer">
			<ul class="g_tabbar">
				<li onclick="changeTab(TAB_HOME);">
					<div class="g_tab <c:if test="${urlnum==1}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_1_n.png" /> <span
							class="g_tabbar_tips">首页</span>
					</div>
				</li>
				<li onclick="changeTab(TAB_MESSAGE);">
					<div class="g_tab <c:if test="${urlnum==2}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_2_n.png" /> <span
							class="g_tabbar_tips">信息</span>
					</div>
				</li>
				<li onclick="changeTab(TAB_PROFILE);">
					<div class="g_tab <c:if test="${urlnum==3}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_3_n.png" /> <span
							class="g_tabbar_tips">我的资料</span>
					</div>
				</li>
				<li onclick="changeTab(TAB_SQUARE);">
					<div class="g_tab <c:if test="${urlnum==4}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_4_n.png" /> <span
							class="g_tabbar_tips">广场</span>
					</div>
				</li>
				<li onclick="changeTab(TAB_MORE);">
					<div class="g_tab <c:if test="${urlnum==5}">g_tabbar_on</c:if>">
						<img src="${templatePathMobile}/img/tabbar/icon_5_n.png" /> <span
							class="g_tabbar_tips">更多</span>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<script>
	/**用ID获取元素*/
	var $$ = function (id) {
		return "string" == typeof id ? document.getElementById(id) : id;
	};
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

	var templatePath="${templatePath}";
	/*点击小图显示大图*/
	document.write('<div id="displayorgimg" style="position:fixed;width:100%;height:100%;display:none;top:0;z-index:100;overflow:auto;" ><img title="点击隐藏"  style="position:relative;display:block;z-index:100;"/><div style="background-color:#000000;height: 100%; position: fixed; width: 100%; left: 0px; top: 0px; z-index: 99;"></div></div>');
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
				//(obj.width*1-window.screen.availWidth*1)>(obj.height*1-window.screen.availHeight*1)?(obj.width*1>window.screen.availWidth*1?t.style.width=(window.screen.availWidth+"px"):t.style.width=(obj.width+"px")):(obj.height*1>window.screen.availHeight*1?t.style.height=(window.screen.availHeight+"px"):t.style.height=(obj.height+"px"));
				(obj.width*1>window.screen.availWidth*1?t.style.width=(window.screen.availWidth+"px"):t.style.width=(obj.width+"px"));
				t.setAttribute("src",imgpath);
				(obj.height*1-window.screen.availHeight*1)<0?t.style.top=(window.screen.availHeight*1-obj.height*1)/3+"px":t.style.top=0+"px";
			}
		}
	}
	</script>
<script>
/*这里的变量来自于后台，是提供给下面的ejectdiv.js和weibolist.js使用的*/
var templatePath="${templatePath}";
var listjson='${listjson}';
</script>
<script type="text/javascript" src="${templatePathMobile}/js/weibolist.js"></script>
</body>
</html>