<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="ibox-content">
	<div class="table-responsive ">
		<table class="table table-centerbody table-striped table-condensed text-nowrap" id="editable-sample">
			<thead>
				<tr>
					<th>id</th>
					<th>名称</th>
					<th>品牌</th>
					<th>促销语</th>
					<th>关键字</th>
					<th class="text-right">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${page!=null && (page.content)!= null && fn:length(page.content) > 0 }">
					<c:forEach var="n" items="${page.content }">
						<tr>
							<td>${n.id }</td>
							<td>${n.goodsName }</td>
							<td>${n.brandName }</td>
							<td>${n.word }</td>
							<td>${n.gkey }</td>
							<td class="text-right text-nowrap">
								<div class="btn-group ">
									<button class="btn btn-white btn-sm edit" data-id="${n.id }" data-toggle="modal" data-target="#edit">
										<i class="fa fa-pencil"></i> 编辑
									</button>
									<button class="btn-white  btn btn-sm delete" data-id="${n.id }">
										<i class="fa fa-trash"></i> 删除
									</button>
								</div>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>

	<!-- 分页表单 -->
	<form action="${ctx }/item/list_page" id="itemPageForm">
		<!-- 查询条件，用隐藏表单域 -->
		<input type="hidden" value="${keywords }" name="keywords" />

		<!-- 分页控键 -->
		<!-- formId: 分页控件表单ID -->
		<!-- showPageId: ajax异步分页获取的数据需要加载到指定的位置 -->
		<jsp:include page="/WEB-INF/views/common/solr_page.jsp" flush="true">
			<jsp:param name="formId" value="itemPageForm" />
			<jsp:param name="showPageId" value="ibox" />
		</jsp:include>
	</form>

</div>