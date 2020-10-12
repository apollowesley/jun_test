<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="customJs/questionTemples.js"></script>
<link rel="stylesheet" href="css/style.css">
<style type="text/css">
</style>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			showQuestionTemplate('statistics');
		});
	</script>
	<div class="pageContent"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<s:if test="#application['paperTypes'].list.isEmpty()">
		没用模板
		</s:if>
		<s:else>
			<div class="panelBar">
				<ul class="toolBar">
					<li>试题情况统计</li>
				</ul>
			</div>
			<div id="classsify">
				<table class="list" width="85%" targetType="navTab" layoutH="110">
					<thead>
						<tr align="center">
							<th width="100">模板名称</th>
							<th width="100">单选题</th>
							<th width="100">多选题</th>
							<th width="100">判断题</th>
						</tr>
					</thead>
					<tbody id="statistics">
					</tbody>
				</table>
			</div>
		</s:else>
	</div>
</body>
</html>