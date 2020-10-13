<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<%@ taglib prefix="dt" uri="/dictionary" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>短信管理</title>
<script type="text/javascript">
	
</script>
</head>
<body>

	<form id="pagerForm" action="sms/list"
		onsubmit="return navTabSearch(this);" method="post">
		<!--【必须】value=1可以写死-->
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<!--【可选】每页显示多少条-->
		<input type="hidden" name="numPerPage" value="${numPerPage}" />

		<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td style="padding: 5px 5px 0 0">接收号码：<input type="text"
							name="search_LIKE_recNum"
							value="${param.search_LIKE_recNum}" />
						</td>
						<td><div class="buttonActive">
								<div class="buttonContent">
									<sms type="submit">检索</sms>
								</div>
							</div></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<shiro:hasPermission name="button_add">
					<li><a class="add" href="sms/add" target="dialog" mask="true"
						rel="dialog1" width="600" height="400"><span>添加</span></a></li>
				</shiro:hasPermission>
			</ul>
		</div>
		<table class="table" width="99%" layoutH="116">
			<thead>
				<tr>
					<th width="20"><input type="checkbox" group="ids"
						class="checkboxCtrl"></th>
					<th width="80">短信签名</th>
					<th  width="100">短信接收者</th>
					<th  width="500">短信内容</th>
					<th  width="100">短信发送人</th>
					<th width="130">短信接收号码</th>
					<th width="100">短信模板</th>
					<th  width="130">创建时间</th>
					<th  width="100">发送结果</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="sms" items="${smsList}">
					<tr target="id" rel="${sms.id}">
						<td><input name="ids" value="${sms.id}" type="checkbox"></td>
						<td>${sms.smsFreeSignName}</td>
						<td>${sms.smsParamCode }</td>
						<td>${sms.smsParamProduct }</td>
						<td>${sms.smsParamItem }</td>
						<td>${sms.recNum }</td>
						<td><dt:dict entity="SystemDic" key="SMS_TEMPLATE_${sms.smsTemplateCode}"/></td>
						<td>${sms.createTime }</td>
						<td>
						<c:set var="theString" value="${sms.smsResult }" />
                        
						<c:if test="${fn:contains(theString, 'true')}">
							成功
						</c:if>

						<c:if test="${fn:contains(theString, 'sub_msg')}">
							 失败 
						</c:if>
                        </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select id="numPerPage" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="20" <c:if test="${numPerPage==20}">selected</c:if>>20</option>
					<option value="50" <c:if test="${numPerPage==50}">selected</c:if>>50</option>
					<option value="100" <c:if test="${numPerPage==100}">selected</c:if>>100</option>

				</select> <span>条，共${totalCount}条</span>
			</div>
			<div id="pagination" class="pagination" targetType="navTab"
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				pageNumShown="10" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>