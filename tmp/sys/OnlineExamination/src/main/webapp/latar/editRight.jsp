<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<h2 class="contentTitle">编辑权限</h2>
<div class="pageContent"
	style="display: block; overflow: hidden; padding: 0 10px; line-height: 21px;">
	<s:set var="haveRight"
		value="@com.evil.util.ValidateUtil@hasRight('/RightAction_doSaveOrUpdateRight')" />
	<form method="post"
		action="${haveRight?'RightAction_doSaveOrUpdateRight':'#' }"
		class="
		pageForm"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<div layoutH="80">
			<table>
				<tbody>
					<c:if test="${model.id!=null }">
						<tr>
							<td><label>权限id：</label></td>
							<td>
								<div class="unit">
									<input type="text" name="id" value="${model.id }"
										readonly="readonly" style="width: 200px" />
								</div> <br>
							</td>
						</tr>
					</c:if>
					<tr>
						<td>权限名称:</td>
						<td>
							<div>
								<div class="unit">
									<input type="text" name="rightName" value="${model.rightName }"
										style="width: 200px" />
								</div>
								<br>
							</div>
						</td>
					</tr>
					<tr>
						<td>权限URL:</td>
						<td>
							<div>
								<div class="unit">
									<input type="text" name="rightUrl" value="${model.rightUrl }"
										style="width: 200px" />
								</div>
								<br>
							</div>
						</td>
					</tr>
					<tr>
						<td><label>权限位：</label></td>
						<td>
							<div class="unit">
								<input type="text" name="rightPos" value="${model.rightPos }"
									readonly="readonly" style="width: 200px" />
							</div> <br>
						</td>
					</tr>
					<tr>
						<td><label>权限位：</label></td>
						<td>
							<div class="unit">
								<input type="text" name="rightCode" value="${model.rightCode }"
									readonly="readonly" style="width: 200px;" />
							</div> <br>
						</td>
					</tr>
					<tr>
						<td><label>公共资源:</label></td>
						<td><s:checkbox name="common" theme="simple" /><br></td>
					</tr>
					<tr>
						<td>权限描述:</td>
						<td>
							<div>
								<div class="unit">
									<textarea name="rightDesc" cols="80" rows="5" class="textInput">${model.rightDesc }</textarea>
								</div>
								<br>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><s:if test="#haveRight">
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div>
					</s:if> <s:else>
						<div class="buttonDisabled">
							<div class="buttonContent">
								<button type="button">提交</button>
							</div>
						</div>
					</s:else></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
</html>