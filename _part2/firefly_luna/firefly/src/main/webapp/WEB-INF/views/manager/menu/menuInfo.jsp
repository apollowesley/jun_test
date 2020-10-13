<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
	<div class="pageContent">
		<form id="inputForm" method="post" name="inputForm"
			action="sysMenu/saveMenu"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" value="${menu.id}" />
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>菜单名称：</label> <input id="name" name="name" type="text"
						class="required" size="30" value="${menu.name}" maxlength="30" />
				</p>
				<p>
					<label>菜单描述：</label> <input type="text" name="description"
						size="30" value="${menu.description}" />
				</p>
				<p>
					<label>菜单别名：</label> <input type="text" name="alias" size="30"
						value="${menu.alias}" maxlength="30" />
				</p>
				<p>
					<label>菜单链接：</label> <input type="text" name="link" size="30"
						value="${menu.link}" maxlength="150" />
				</p>
				<p>
					<label>序号：</label> <input type="text" name="sort" size="30"
						maxlength="10" value="${menu.sort}" class="digits" />
				</p>

				<p>
					<label>所属模块</label> <select name="parentId" class="combox "
						selectDisabled="false" style="width: 120px;"
						remoteUrl="sysMenu/getAllMenuList"
						defaultVal="${(empty flag) ?0: flag}">
					</select> </select>
				</p>

			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
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
		</form>
	</div>
</body>
</html>
