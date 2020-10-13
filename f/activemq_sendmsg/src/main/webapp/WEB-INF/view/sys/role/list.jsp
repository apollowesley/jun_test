<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    
    <title>角色列表</title>
	
    <!-- 静态css、js资源 -->
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    
    <script type="text/javascript" src="${ctx}/resources/js/sys/role/role.js"></script>
    
</head>
<body>
<input type="hidden" id="getRoleListUrl" value="${ctx}/role/list"/>
<input type="hidden" id="editColumnUrl" value="${ctx}/role/editTableColumn"/>
<input type="hidden" id="toAddRoleUrl" value="${ctx}/role/toAddView"/>
<input type="hidden" id="toEditRoleUrl" value="${ctx}/role/toEditView"/>

<!-- 标识是否有角色删除、编辑权限 -->
<shiro:hasPermission name="role:edit">
    <input type="hidden" id="haveRoleEditPermission" value="true"/>
</shiro:hasPermission>
<shiro:hasPermission name="role:delete">
    <input type="hidden" id="haveRoleDeletePermission" value="true"/>
</shiro:hasPermission>

<div class="wrapper wrapper-content animated fadeInRight " id="roleListDiv" style="width: 100%; height: 100%;">
    <input type="hidden" name="deleteRoleUrl" id="deleteRoleUrl" value="<c:url value="/role/deleteRole"/>" />
    <!-- 公司部门列表json字符串，这里value必须是单引号，避免与depList值中的双引号冲突，出现问题 -->
    <input type="hidden" name="depList" value='${depList}'/>
    <div class="ibox">
        <div class="ibox-content">
            <form class="form-horizontal" id="roleSearchForm" method="post" onsubmit="return false">
                <div class="ibox-title">
                    <!-- <button type="button" class="btn btn-primary" id="roleAddButton">创建角色</button> -->
                    <button type="button" class="btn btn-primary" id="roleSearchButton">查询</button>
                </div>
                <br />
                <div class="row">
                    <div class="col-md-3">
                       <div class="input-group">
                           <span class="input-group-addon">角色名称：</span>
                           <input type="text" class="form-control" name="roleName" id="roleName">
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
                    <table class="table table-bordered table-hover" id="roleListTable" data-row-style="rowStyle" data-use-row-attr-func="true" data-reorderable-rows="true"></table>
                </div>
                
                <!-- toolbat工具栏 -->
                <div id="toolbar">
                    <shiro:hasPermission name="role:add">
                        <button type="button" class="btn btn-primary" id="roleAddButton">新增角色</button>
                    </shiro:hasPermission>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
