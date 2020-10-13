<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
 
</script>
</head>
<body>
	<div class="pageContent">
		<form id="systemDic" name="systemDic" method="post" 
			action="systemDic/saveInfo" class="pageForm required-validate" 
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" value="${systemDic.id}" />
			<div class="pageFormContent" layoutH="56">
				<p>
					<label>类型：</label> <input id="type" name="type" 
						class="required" placeholder="请输入类型" type="text" size="30"
						value="${systemDic.type}" maxlength="30"/>
				</p>
				<p>
					<label>值：</label> <input name="value" class="required" 
						placeholder="请输入数值" type="text" size="30" 
						value="${systemDic.value}" maxlength="30"/>
				</p>
				<p>
					<label>名称：</label> <input name="name" class="required" 
						placeholder="请输入名称" type="text" size="30" maxlength="30"
						value="${systemDic.name}" />
				</p>
				<p>
					<label>描述：</label> <input name="desc" 
						placeholder="请输入描述信息" type="text" size="30" 
						value="${systemDic.desc}" maxlength="150"/>
				</p>
				<p>
					<label>排序号：</label> <input name="sortNum" class="digits" 
						max="${maxValue}" placeholder="请输入排序号" type="text" size="30" 
						value="${systemDic.sortNum}" maxlength="8"/>
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
