<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/topbar.css" />
<script>
var doSearch=function(){
	var s_wd=document.getElementById("search_word").value;

	if(s_wd==''){
		
	} else{
		location.href("Friends.yy?a=search&s_wd="+encodeURI(s_wd));
	}
}
</script>
<div class="topbar topbar_fixed">
	<div class="grid-990 topbar-wrap fn-clear">
	<p class="toplink">
		<a class="mobile" href="<%=basePath%>template/default/jsp/app.jsp" target="_blank"><i class="icon-mobile"></i>应用下载</a><a class="site_index" title="网站首页" href="<%=basePath%>" target="_self">网站首页</a><a class="site_index" title="网站首页" href="<%=basePath%>mobile" target="_self"> 移动版</a><a class="site_index" title="网站首页" href="<%=basePath%>template/default/jsp/code.jsp" target="_blank">源码</a>
		<input placeholder="搜人" id="search_word"/><input type="button" value="搜索" onclick="javascript:doSearch();"/>
	</p>
	<ul class="topmenu">
	<%-- <li class="seeworld"><a href="seeworld.yy?a=seeworld">看世界</a></li> --%>
	<li class="seeworld"><a href="Message.yy?uc=4&a=news">消息</a></li>
	<li class="seeworld"><a href="JoinUs.yy">搭伙</a></li>
		<c:if test="${sessionScope.UName==null}"><li class="topmenu-item topmenu-item-first"><a title="登录" href="Login.yy" target="_self">登录</a><b class="split">-</b><a title="注册" href="${templatePath}/jsp/register.jsp" target="_self">注册</a></li></c:if>
		<c:if test="${sessionScope.UName!=null}"><li class="topmenu-item topmenu-item-dropdown topmenu-item-userdropdown">
		<a href="Usercenter.yy?uc=1" target="_self">你好，<%=request.getSession().getAttribute("UName")%><i class="icon-dropdown"></i></a>
		<ul><li></li></ul>
		<div class="topmenu_user">
		<i class="iconfont" title="我的头像"><img src="${templatePath}/uploadavatar/getAvatar.jsp?au=<%=request.getSession().getAttribute("UOID")%>" width="80px" height="80px"/></i>
		<div class="topmenu_user_center">
		<a href="Usercenter.yy?uc=1">用户中心</a>
		<a href="Usercenter.yy?uc=2">我的头像</a>
		<a href="Usercenter.yy?uc=3">修改密码</a>
		<a href="Message.yy?uc=4&a=news">查看消息</a>
		<a href="Friends.yy?uc=5&a=myFriends">我的好友</a>
		<c:if test="${sessionScope.Adminid}"><a href="Admin.yy?" target="_blank">后台管理</a></c:if>
		</div>
		</div>
		</li>
		<li class="topmenu-item topmenu-item-mid"><a href="<%=basePath%>Login.yy?a=logout" title="退出">退出</a></li></c:if>
		<li class="topmenu-item topmenu-item-dropdown">
		<a>我的记账本<i class="icon-dropdown"></i></a>
		<ul>
		<li><a href="#" target="_self">记事</a></li>
		<li><a href="#" target="_self">消费记录</a></li>
		<li><a href="#" target="_self">收入记录</a></li>
		</ul>
		</li>
		<li class="topmenu-item topmenu-item-last topmenu-item-dropdown">
		<a>帮助中心<i class="icon-dropdown"></i></a>
		<ul>
		<li><a href="#" target="_blank">帮助中心</a></li>
		<li><a href="Feedback.yy" target="_blank">问题反馈</a></li>
		</ul>
		</li>
	</ul>
	</div>
</div>
<div class="topbar_fixed_hold"></div>
