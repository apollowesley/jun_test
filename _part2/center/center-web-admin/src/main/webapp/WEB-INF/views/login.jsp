<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>系统登录</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@include file="common/common.jsp"%>
</head>

<body>
	<div class="easyui-window" title="登录窗口"
		data-options="iconCls:'icon-save'"
		style="width: 500px; height: 200px; padding: 10px;">
		<div align="center">
			<form id="form" action="sso/login" method="post">
				<table>
					<tr>
						<td>用户名:</td>
						<td><input class="easyui-validatebox" type="text"
							name="userName" validType="length[0,20]"
							data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><input class="easyui-validatebox" type="password"
							name="password" validType="length[0,20]"
							data-options="required:true"></input></td>
					</tr>
					<tr>
						<td>记住我：</td>
						<td><input type="checkbox" name="rememberMe"></td>
					</tr>
				</table>
			</form>
			<div>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
					href="javascript:void(0)" onclick="login();">登录</a> <a
					class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
					href="javascript:void(0)" onclick="$('#form').form('clear');">重置</a>
			</div>
		</div>
	</div>
	<c:if test="${!empty errorMsg}">
		<script type="text/javascript">
             var errorMsg = '${errorMsg }';
             if (isNotEmpty(errorMsg)) {
            	 showMsg(errorMsg);
             }
         </script>
	</c:if>
	<script type="text/javascript">
	    document.onkeydown = function(event){
	        if (event.keyCode == 13) {
	        	login();
	        }
	    };
    
	    function login() {
	    	if ($('#form').form('validate')) {
	    		$('#form').submit();
	    	}
	    }
	    
	    function initBackUrl() {
	    	var backUrl = "${param.backUrl }";
	    	$.cookie('backUrl', null);
	    	if (isNotEmpty(backUrl)) {
	    		$.cookie('backUrl', encodeURI(backUrl),{ expires: 1, path: '/' });
	    	}
	    }
	    
	    initBackUrl();
    </script>
</body>
</html>
