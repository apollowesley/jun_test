<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    
    <title>权限列表</title>
	
    <!-- 静态css、js资源 -->
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    
    <script type="text/javascript" src="${ctx}/resources/js/sys/permission/permission.js"></script>
    
</head>
<body>
<input type="hidden" id="getPermissionListUrl" value="${ctx}/permission/list"/>
<input type="hidden" id="toEditPermissionViewUrl" value="${ctx}/permission/editPermissionView"/>
<input type="hidden" id="toAddPermissionUrl" value="${ctx}/permission/toAddView"/>

<!-- 标识是否有权限删除、编辑权限 -->
<shiro:hasPermission name="permission:edit">
    <input type="hidden" id="havePermissionEditPermission" value="true"/>
</shiro:hasPermission>
<shiro:hasPermission name="permission:delete">
    <input type="hidden" id="havePermissionDeletePermission" value="true"/>
</shiro:hasPermission>

<div class="wrapper wrapper-content animated fadeInRight " id="permissionListDiv" style="width: 100%; height: 100%;">
    <input type="hidden" name="deletePermissionUrl" id="deletePermissionUrl" value="<c:url value="/permission/deletePermission"/>" />
    <div class="ibox">
        <div class="ibox-content">
            <form class="form-horizontal" id="permissionSearchForm" method="post" onsubmit="return false">
                <div class="ibox-title">
                    <button type="button" class="btn btn-primary" id="permissionSearchButton">查询</button>
                </div>
                <br />
                <div class="row">
                    <div class="col-md-3">
                       <div class="input-group">
                           <span class="input-group-addon">权限名称：</span>
                           <input type="text" class="form-control" name="name" id="name">
                       </div>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">状态：</span> 
                            <select class="form-control" name="status" id="status">
                                <option value="">全部</option>
                                <c:forEach items="${status}" var="s">
                                    <option value="${s}">${s.description}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </form>
            <div class="row">
                <!-- 表格 -->
                <div class="col-md-10 col-lg-10">
                    <table class="table table-bordered table-hover" id="permissionListTable" data-row-style="rowStyle" data-use-row-attr-func="true" data-reorderable-rows="true"></table>
                </div>
                
                <!-- toolbat工具栏 -->
                <div id="toolbar">
                    <button type="button" class="btn btn-primary" id="permissionAddButton">新增权限</button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
