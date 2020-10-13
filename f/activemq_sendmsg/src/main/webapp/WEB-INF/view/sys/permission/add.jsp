<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ctx" value="<%=basePath %>"/>

<!DOCTYPE>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    
    <title>权限添加</title>
    
    <script type="text/javascript" src="${ctx}/resources/js/sys/permission/add.js"></script>
    
</head>
<body>

<!-- 添加权限信息 -->
<input type="hidden" name="addPermissionUrl"  id="addPermissionUrl" value="<c:url value="/permission/addPermission"/>"/>
<!-- 存放选中的权限id -->
<input type="hidden" id="parentId" name="parentId" value=""/>
<!-- 权限列表json字符串，这里value必须是单引号，避免与permissions值中的双引号冲突，出现问题 -->
<input type="hidden" name="permissions" value='${permissions}'/>

<div style="width: 600px;padding: 15px; overflow: auto;">
	<form class="form-horizontal" id="permissionAddForm">
	    <div class="form-group">
	        <label class="col-xs-3 control-label">权限名称</label>
	        <div class="col-xs-5">
	            <input type="text" class="form-control" placeholder="权限名称" name="name" id="name" value="">
	        </div>
	    </div>
	    <div class="form-group">
	        <label class="col-xs-3 control-label">权限描述</label>
	        <div class="col-xs-5">
	            <input type="text" class="form-control" placeholder="权限描述" name="description" id="description" value="">
	        </div>
	    </div>
	    <div class="form-group">
	        <label class="col-xs-3 control-label">权限代码</label>
	        <div class="col-xs-5">
	            <input type="text" class="form-control" placeholder="权限代码" name="code" id="code" value="">
	        </div>
	    </div>
	    <div class="form-group">
	        <label class="col-xs-3 control-label">权限状态</label>
	        <div class="col-xs-5">
	            <div class="input-group">
	                <select class="form-control" name="status" id="status">
	                    <c:forEach items="${status}" var="s">
	                        <option value="${s}">${s.description}</option>
	                    </c:forEach>
	                </select>
	            </div>
	        </div>
	    </div>
	    <div class="form-group">
	        <label class="col-xs-3 control-label">权限级别</label>
	        <div class="col-xs-5">
	            <div id="frmt">
	                <!-- 权限tree  start -->
	                <ul id="permissionTree" class="ztree" style="padding-left: 0px;"></ul>
	                <!-- 权限tree  end -->
	            </div>
	        </div>
	    </div>
	</form>
</div>
</body>
</html>
