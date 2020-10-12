<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="user_cent_r">
<script type="text/javascript" src="${templatePath}/js/ajax.js"></script>
<script>
var markRead=function(url,msg){
	var urls=url.split('?');
	var result=new Ajax(urls[0],urls[1]).get();
	result=(new Function('return '+result))();
	if(result.realSucess=='true'){
		alert(msg+"成功");
	}else{
		alert(msg+"失败");
	}
	location.reload(true);
};
</script>

消息列表：
<c:if test="${list1.size()>0}">
<c:forEach items="${list1}" var="vo">
消息：${vo.title}<br/>
摘要：${vo.message}<br/>
<a href="###" onclick="javascript:markRead('${vo.action}','${vo.actionName }');return false;">${vo.actionName }</a>
<hr />
</c:forEach>
</c:if>
</div>

