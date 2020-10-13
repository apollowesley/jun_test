<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${targetType=='dialog' }">
		<c:set var="page_targetType" value="dialog"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="page_targetType" value="navTab"></c:set>
	</c:otherwise>
</c:choose>
<div class="panelBar">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<div class="pages">
		<span>显示</span><select id="numPerPage" name="numPerPage"
			onchange="${page_targetType }PageBreak({numPerPage:this.value},'${refreshDivId }')">
			<option value="20" <c:if test="${numPerPage==20}">selected</c:if>>20</option>
			<option value="50" <c:if test="${numPerPage==50}">selected</c:if>>50</option>
			<option value="100" <c:if test="${numPerPage==100}">selected</c:if>>100</option>
		</select> <span>条，共${totalCount}条</span>
	</div>
	<div id="pagination" class="pagination" targetType="${page_targetType }"
		totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="3" rel="${refreshDivId }"
		currentPage="${pageNum}"></div>		
</div>