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
    <title>编辑角色</title>
    
    <script type="text/javascript" src="${ctx}/resources/js/sys/role/edit.js"></script>
</head>
<body>
<div style="width: 600px; height: 500px; padding: 15px; overflow: auto;">
    <form class="form-horizontal" id="roleEditForm">
        <input type="hidden" value='${role.id}' id="id" name="id"/>
        <!-- 存放选中的权限id -->
        <input type="hidden" id="permissionIds" name="permissionIds" value=""/>
    
        <div class="form-group">
            <label class="col-xs-3 control-label">角色名称</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" placeholder="名称" name="name" id="name" value="${role.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">角色描述</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" placeholder="名称" name="description" id="description" value="${role.description}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">状态</label>
            <div class="col-xs-5">
                <div class="input-group">
                    <select class="form-control" name="status" id="status">
                        <c:forEach items="${status}" var="s">
                            <option value="${s}" <c:if test="${role.status == s}">selected="selected"</c:if> >${s.description}</option>
                        </c:forEach>
                    </select>
                </div>
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
        
    <!-- 更新角色信息 -->
    <input type="hidden" name="editRoleUrl"  id="editRoleUrl" value="<c:url value="/role/updateRole"/>"/>
    <!-- 权限列表json字符串，这里value必须是单引号，避免与permissions值中的双引号冲突，出现问题 -->
    <input type="hidden" name="permissions" value='${permissions}'/>
</div>

</body>
</html>
