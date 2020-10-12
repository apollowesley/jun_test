<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<ul class="pagingFrom pagination">
	<input type="hidden" value="${page.currentPage }" name="currentPage" />
	<input type="hidden" value="${page.totalPage }" name="totalPage" />
	<input type="hidden" value="${page.currentPage }" name="curPage" />
	<c:if test="${page.currentPage == 1 }">
		<li><span>首页</span></li>
		<li><span> 上一页 </span></li>
	</c:if>

	<c:if test="${page.currentPage > 1 }">
		<li><span class="paging" data-currentPage="1">首页 </span></li>
		<li><span class="paging" data-currentPage="${page.currentPage - 1 }"> 上一页 </span></li>
	</c:if>

	<c:if test="${page.currentPage == page.totalPage }">
		<li><span> 下一页 </span></li>
		<li><span> 尾页 </span></li>
	</c:if>

	<c:if test="${page.currentPage < page.totalPage }">
		<li><span class="paging" data-currentPage="${page.currentPage + 1 }"> 下一页 </span></li>
		<li><span class="paging" data-currentPage="${page.totalPage }"> 尾页 </span></li>
	</c:if>
</ul>
<ul class="pagingFrom pagination">
	<li><span class="pagingIndex paging">跳转至 </span></li>
</ul>
<ul class="pagingFrom pagination">
	<span class="paging-input"><input class="input-mini-s" name="pagingIndex" /></span>
</ul>
<ul class="pagingFrom pagination">
	<li><span>页</span></li>
</ul>
<ul class="pagingFrom pagination">
	<li><span>每页</span></li>
</ul>
<ul class="pagingFrom pagination">
	<span class="paging-select"><select class="select-mini-s selectPageSize" name="pageSize" data-currentPage="${page.currentPage}">
			<c:choose>
				<c:when test="${page.pageSize eq 10}">
					<option value="10" selected="selected">10</option>
					<option value="50">50</option>
					<option value="100">100</option>
				</c:when>
				<c:when test="${page.pageSize eq 50}">
					<option value="10">10</option>
					<option value="50" selected="selected">50</option>
					<option value="100">100</option>
				</c:when>
				<c:otherwise>
					<option value="10">10</option>
					<option value="50">50</option>
					<option value="100" selected="selected">100</option>
				</c:otherwise>
			</c:choose>
	</select></span>
</ul>
<ul class="pagingFrom pagination">
	<li><span>条</span></li>
</ul>
<ul class="pagingFrom pagination">
	<li><span>共${page.totalCount }条记录 , 第${page.currentPage }页/共${page.totalPage }页</span></li>
</ul>
<ul class="pagingFrom pagination">
	<li><span class="paging exportCurrentPageData" aria-hidden="true">导出此页</span></li>
</ul>
