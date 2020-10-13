<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>
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
    
    <title>添加角色</title>
    
    <!-- 静态css、js资源 -->
    <%-- <%@ include file="/WEB-INF/view/common/common.jsp"%> --%>
    
    <script type="text/javascript" src="${ctx}/resources/js/sys/role/add.js"></script>
    
</head>
<body>

<input type="hidden" id="getRoleListUrl" value="${ctx}/role/list"/>
<input type="hidden" id="editColumnUrl" value="${ctx}/role/editTableColumn"/>
<input type="hidden" id="toAddRoleUrl" value="${ctx}/role/toAddView"/>

<div style="width: 600px; height: 500px; padding: 15px; overflow: auto;">
    <form class="form-horizontal" id="roleAddForm">
        <div class="form-group">
            <label class="col-xs-3 control-label">角色名称</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" placeholder="名称" name="name" id="name" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">角色描述</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" placeholder="名称" name="description" id="description" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">权限</label>
            <div class="col-xs-5">
                <div id="frmt">
                    <!-- 权限tree  start -->
                    <ul id="permissionTree" class="ztree" style="padding-left: 0px;padding-top: 5px;"></ul>
                    <!-- 权限tree  end -->
                </div>
            </div>
        </div>
    </form>
    
    
    <!-- 添加角色信息 -->
    <input type="hidden" name="addRoleUrl"  id="addRoleUrl" value="<c:url value="/role/addRole"/>"/>
    <!-- 存放选中的权限id -->
    <input type="hidden" id="permissionIds" name="permissionIds" value=""/>
    <!-- 权限列表json字符串，这里value必须是单引号，避免与permissions值中的双引号冲突，出现问题 -->
    <input type="hidden" name="permissions" value='${permissions}'/>
</div>

</body>
</html>
