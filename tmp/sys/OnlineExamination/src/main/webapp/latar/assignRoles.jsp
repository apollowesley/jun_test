<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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

body {
	font: 12px/normal "microsoft yahei";
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
	font-size: 14px;
	font-family: "microsoft yahei";
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
	font-family: simsun;
	font-size: 14px;
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
</head>
<html>
<body>
	<s:set var="haveRight"
		value="@com.evil.util.ValidateUtil@hasRight('/AdminUserAction_doUpdateAmdinUserAuthorize')" />
	<form method="post"
		action="${haveRight?'AdminUserAction_doUpdateAmdinUserAuthorize':'#' }"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)">
		<input type="hidden" name="id" value="${id }" />
		<div class="selectbox">
			<div class="select-bar">
				<div style="float: none; margin: 5px;"> 拥有的角色</div>
				<s:select id="select1" name="ownRoleIds" list="roles" listKey="id"
					listValue="roleName" multiple="true" size="15"
					cssStyle="width:100px">
				</s:select>
			</div>
			<div class="btn-bar">
				<p>
					<span id="add"><input type="button" class="btn" value=">"
						title="移动选择项到右侧" /></span>
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
					<span id="remove_all"><input type="button" class="btn"
						value="<<" title=" 全部移到左侧"/></span>
				</p>
			</div>
			<div class="select-bar">
				<div style="float: none; margin: 5px;"> 可分配的角色</div>
				<s:select id="select2" list="noOwnRoles" name="noOwnRoleIds"
					listKey="id" listValue="roleName" multiple="true" size="15"
					cssStyle="width:100px">
				</s:select>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><s:if test="#haveRight">
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" onclick="submitForm1()">确定</button>
							</div>
						</div>
					</s:if> <s:else>
						<div class="buttonDisabled">
							<div class="buttonContent">
								<button type="button">确定</button>
							</div>
						</div>
					</s:else>
					</li>
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
</body>
</html>
