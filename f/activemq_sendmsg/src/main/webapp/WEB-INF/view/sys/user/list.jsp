<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta charset="utf-8">
    
    <title>人员列表</title>
	
    <!-- 静态css、js资源 -->
    <%@ include file="/WEB-INF/view/common/common.jsp"%>
    
    <script type="text/javascript" src="${ctx}/resources/js/sys/user/user.js"></script>
</head>
<body>
<input type="hidden" id="getUserListUrl" value="${ctx}/user/list"/>
<input type="hidden" id="editColumnUrl" value="${ctx}/user/editTableColumn"/>

<div class="wrapper wrapper-content animated fadeInRight " id="userListDiv" style="width: 100%; height: 100%;">
    <!-- 添加用户url -->
    <input type="hidden" name="toAddUserUrl" id="toAddUserUrl" value="<c:url value="/user/toAddView"/>" />
    <!-- 删除用户url -->
    <input type="hidden" name="deleteUserUrl" id="deleteUserUrl" value="<c:url value="/user/deleteUser"/>" />
    <!-- 编辑用户url -->
    <input type="hidden" name="toEditUserUrl" id="toEditUserUrl" value="<c:url value="/user/toEditView"/>" />
    <!-- 公司部门列表json字符串，这里value必须是单引号，避免与depList值中的双引号冲突，出现问题 -->
    <input type="hidden" name="depList" value='${depList}'/>
    
    <!-- 标识是否有用户删除、编辑权限 -->
    <shiro:hasPermission name="user:edit">
        <input type="hidden" id="haveUserEditPermission" value="true"/>
    </shiro:hasPermission>
    <shiro:hasPermission name="user:delete">
        <input type="hidden" id="haveUserDeletePermission" value="true"/>
    </shiro:hasPermission>
    
    <div class="ibox">
        <div class="ibox-content">
            <form class="form-horizontal" id="userSearchForm" method="post" onsubmit="return false">
                <!-- 是否拥有查看所有用户列表权限 -->
                <shiro:hasPermission name="user:list">
			        <input type="hidden" id="haveUserListPermission" value="true"/>
                </shiro:hasPermission>
                 <input type="hidden" name="customerId" id="customerId" value="${sessionScope.curUser.customerId}" />
                 <!-- 存放选中的部门id -->
                <input type="hidden" id="depId" name="depId" value=""/>
                <div class="ibox-title">
                    <!-- <button type="button" class="btn btn-primary" id="userAddButton">创建人员</button> -->
                    <button type="button" class="btn btn-primary" id="userSearchButton">查询</button>
                </div>
                <br />
                <div class="row">
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">角色：</span> 
                            <select class="form-control" name="roleId" id="roleId">
                                <option value="">全部</option>
                                <c:forEach items="${roles}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
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
                    <div class="col-md-3">
                        <div class="input-group">
                            <span class="input-group-addon">人员姓名：</span>
                            <input type="text" class="form-control" name="realName" id="realName">
                        </div>
                    </div>
                </div>
            </form>

            <div class="row">
                <!-- 表格 -->
                <div class="col-md-10 col-lg-10">
                    <table class="table table-bordered table-hover" id="userListTable" data-row-style="rowStyle" data-use-row-attr-func="true" data-reorderable-rows="true"></table>
                </div>
                
                <!-- toolbat工具栏 -->
                <div id="toolbar">
                    <shiro:hasPermission name="user:add">
                        <div class="btn btn-primary" data-toggle="modal" id="userAddButton">新增人员</div>
                    </shiro:hasPermission>
                    <!-- <div class="btn btn-primary" data-toggle="modal" data-target="#addModal">导出类型</div> -->
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
