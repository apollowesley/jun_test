<%@ page language="java" contentType="text/xml"	pageEncoding="UTF-8" import="java.util.*"%>
<%@ include file="/common/taglibs.inc.jsp"%>
<rows>
	<page>${pager.pageNo}</page>
	<total>${pager.totalRows}</total>
	<c:forEach items="${pager.resultList}" var="item" varStatus="i">
	<row>
		<cell><![CDATA[<input type="radio" name="user" value="${item.userId}" required="required"/>]]></cell>
		<cell><![CDATA[${item.userNum}]]></cell>
		<cell><![CDATA[${item.userName}]]></cell>
		<cell><![CDATA[${item.sex}]]></cell>
		<cell><![CDATA[
	        <span class="fl">${item.phone}</span>
			<ul class="tip_list">
	        <c:forEach items="${item.emails}" var="mail">
                 <li>${mail}</li>
	        </c:forEach>
			</ul>
	        ]]></cell>
		<cell><![CDATA[<a class="mapping_detail" href="${webroot}/ExHostAction.do?method=toVolumeDetai" 
		target="_volume_detail">${item.email}</a>]]></cell>
	</row>
	</c:forEach>
</rows>