<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="user_cent_r">
<script type="text/javascript" src="${templatePath}/js/ajax.js"></script>
<script>
var deleteFriends=function(uid){
	var result=new Ajax('Friends.yy','a=deleteFriends&uid1='+uid).get();
	result=(new Function('return '+result))();
	if(result.realSucess=='true'){
		alert("删除成功，你们已经不再是朋友关系");
	}else{
		alert("删除失败，你们依然是朋友关系");
	}
	location.reload(true);
};
</script>

好友列表：<br/>
<c:if test="${list1.size()>0}">
<c:forEach items="${list1}" var="vo">
昵称：${vo.username}&nbsp;<a href="###" onclick="javascript:deleteFriends(${vo.uid});return false;">删除好友</a><br/>
</c:forEach>
</c:if>
</div>

