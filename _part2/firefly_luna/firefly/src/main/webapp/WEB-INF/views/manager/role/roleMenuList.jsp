<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<body>
	<div class="pageContent">
		<form id="form" name="form"  method="post" action="roleManage/roleMenu"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="strId" id="strId"/>
			<input type="hidden" name="roleId" value="${roleId}" />
			<div layoutH="50">
				<ul id='popedom-tree' class="tree treeFolder treeCheck expand">
				    <c:forEach var="menu" items="${menus}">
					<li><a name="c" tvalue="${menu.id}"
						<c:if test="${menu.checked==true}" >checked</c:if>>${menu.name}</a>
						<c:if test="${fn:length(menu.childMenus)>0}">
							<ul>
								<c:forEach var="childs" items="${menu.childMenus}">
									<li><a name="c" tvalue="${childs.id}"
										<c:if test="${childs.checked==true}" >checked</c:if>>${childs.name}</a>
											<c:if test="${fn:length(childs.childButton)>0}">
												<ul>
													<c:forEach var="childButton" items="${childs.childButton}">
														<li><a name="c" tvalue="button_${childButton.buttonCode}"
															<c:if test="${childButton.checked==true}" >checked</c:if>>${childButton.buttonName}</a>
														</li>
													</c:forEach>
												</ul>
											</c:if>
									</li>
								</c:forEach>
							</ul>
						</c:if>
					</li>
					</c:forEach>
				</ul>
			</div>
			<div class="pageHeader">
				<div class="formBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" onclick="kkk()">保存</button>
								</div>
							</div></li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		function kkk() {
			var strId = "";
			$("div.ckbox.checked >input").each(
					function() {
						strId += $(this).val() + ",";
					});
			$("div.ckbox.indeterminate >input").each(
					function() {
						strId += $(this).val() + ",";
					});
			$("#strId").val(strId);
		}
	</script>
</body>
</html>