<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="user_cent_r">
<h1>我的头像<span>( <i>*</i> 必须填写项 )</span></h1>
<div class="Menulink"><a class="selected" href="#">我的头像</a></div>
<h2 class="myAvatarText">当前我的头像</h2>
<p>如果您还没有设置自己的头像，系统会显示为默认头像，您需要自己上传一张新照片来作为自己的个人头像</p> 
<img alt="头像" src="${templatePath}/uploadavatar/getAvatar.jsp?au=<%=request.getSession().getAttribute("UOID")%>" width="100" height="100"/>
<%@include file="./../uploadavatar/avatar.jsp" %>
</div>