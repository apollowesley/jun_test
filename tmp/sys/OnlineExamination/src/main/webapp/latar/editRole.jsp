<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	list-style-type: none;
	outline: none;
}

a, img {
	border: 0;
}

.selectbox {
	width: 500px;
	height: 220px;
	margin: 10px auto;
}

.selectbox div {
	float: left;
}

.selectbox .select-bar {
	padding: 0 20px;
}

.selectbox .select-bar select {
	width: 150px;
	height: 200px;
	border: 1px #A0A0A4 solid;
	padding: 4px;
	/* 	font-size: 14px;
	font-family: "microsoft yahei"; */
}

.btn-bar {
	
}

.btn-bar p {
	margin-top: 21px;
}

.btn-bar p .btn {
	width: 50px;
	height: 30px;
	cursor: pointer;
	/* 	font-family: simsun;
	font-size: 14px; */
}
</style>
<script type="text/javascript">
	$(function() {
		//移到右边
		$('#add').click(function() {
			//先判断是否有选中
			if (!$("#select1 option").is(":selected")) {
				alert("请选择需要移动的选项")
			}
			//获取选中的选项，删除并追加给对方
			else {
				$('#select1 option:selected').appendTo('#select2');
			}
		});

		//移到左边
		$('#remove').click(function() {
			//先判断是否有选中
			if (!$("#select2 option").is(":selected")) {
				alert("请选择需要移动的选项")
			} else {
				$('#select2 option:selected').appendTo('#select1');
			}
		});

		//全部移到右边
		$('#add_all').click(function() {
			//获取全部的选项,删除并追加给对方
			$('#select1 option').appendTo('#select2');
		});

		//全部移到左边
		$('#remove_all').click(function() {
			$('#select2 option').appendTo('#select1');
		});

		//双击选项
		$('#select1').dblclick(function() { //绑定双击事件
			//获取全部的选项,删除并追加给对方
			$("option:selected", this).appendTo('#select2'); //追加给对方
		});

		//双击选项
		$('#select2').dblclick(function() {
			$("option:selected", this).appendTo('#select1');
		});
	});
	function submitForm1() { //提交表单的时候全部选中已具备的角色 
		$('#select1').find("option").attr("selected", true);
	}
</script>
<style type="text/css">
.adtable, .adkuangTu {
	display: none !important;
	display: none
}

.a_mu, .wp.a_f, .wp.a_t, .notice, .a_pr, #a2, .conads1, .conads2 {
	display: none !important;
	display: none
}

#a22 {
	display: none !important;
	display: none
}
</style>
<h2 class="contentTitle">编辑角色</h2>
<div class="pageContent"
	style="display: block; overflow: hidden; padding: 0 10px; line-height: 21px;">
	<s:set var="haveRight"
		value="@com.evil.util.ValidateUtil@hasRight('/RoleAction_doSaveOrUpdateRole')" />
	<form method="post"
		action="${haveRight?'RoleAction_doSaveOrUpdateRole':'#' }"
		class="pageForm"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" value="${pid}" name="pid">
		<div layoutH="80">
			<table>
				<tbody>
					<tr>
						<c:if test="${model.id!=null }">
							<tr>
								<td><label>角色id：</label></td>
								<td>
									<div class="unit">
										<input type="text" name="id" value="${model.id }"
											readonly="readonly" style="width: 200px" />
									</div> <br>
								</td>

							</tr>
						</c:if>
						<td>角色名:</td>
						<td>
							<div>
								<div class="unit">
									<input type="text" name="roleName" value="${model.roleName }"
										style="width: 200px" />
								</div>
								<br>
							</div>
						</td>
					</tr>
					<tr>
						<td>角色值:</td>
						<td>
							<div>
								<div class="unit">
									<input type="text" name="roleValue" value="${roleValue }"
										style="width: 200px"> <span style="color: red;">(*角色值为-1时具备改角色的管理员为超级管理员)</span>
								</div>
								<br>
							</div>
						</td>
					</tr>
					<tr>
						<td>角色描述:</td>
						<td>
							<div>
								<div class="unit">
									<textarea name="roleDesc" cols="80" rows="5" class="textInput">${model.roleDesc }</textarea>
								</div>
								<br>
							</div>
						</td>
					</tr>
					<tr>
						<td>权限分配:</td>
						<td>
							<div>
								<div class="unit">
									<div class="selectbox">
										<div class="select-bar">
											<div style="float: none; margin: 5px;"> 拥有的权限</div>
											<s:select id="select1" name="OwnRightIds" list="rights"
												listKey="id" listValue="rightName" multiple="true" size="15"
												cssStyle="overflow:auto;" theme="simple">
											</s:select>
										</div>
										<div class="btn-bar">
											<p>
												<span id="add"><input type="button" class="btn"
													value=">" title="移动选择项到右侧" /></span>
											</p>
											<p>
												<span id="add_all"><input type="button" class="btn"
													value=">>" title="全部移到右侧" /></span>
											</p>
											<p>
												<span id="remove"><input type="button" class="btn"
													value="<" title=" 移动选择项到左侧"/></span>
											</p>
											<p>
												<span id="remove_all"><input type="button"
													class="btn" value="<<" title=" 全部移到左侧"/></span>
											</p>
										</div>
										<div class="select-bar">
											<div style="float: none; margin: 5px;"> 可分配的权限</div>
											<s:select id="select2" name="noOwnRightsIds"
												list="noOwnRights" listKey="id" listValue="rightName"
												multiple="true" cssStyle="overflow:auto;" size="10"
												theme="simple">
											</s:select>
										</div>
										<br>
									</div>
								</div>
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