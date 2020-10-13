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
    
    <title>人员信息修改</title>
    
    <script type="text/javascript" src="${ctx}/resources/js/sys/user/edit.js"></script>
    
</head>
<body>

<div style="width: 600px;padding: 15px; overflow: auto;" id="userUpdateDiv">
	<!-- 修改用户信息 -->
	<input type="hidden" name="updateUserUrl"  id="updateUserUrl" value="<c:url value="/user/updateUser"/>"/>
    
    <form class="form-horizontal" id="userEditForm">
        <input type="hidden" value='${user.id}' id="userId" name="userId"/>
        <!-- 存放选中的角色id -->
        <input type="hidden" id="roleIds" name="roleIds" value="${roleIds}"/>
        <!-- 角色列表json字符串，这里value必须是单引号，避免与allRoles值中的双引号冲突，出现问题 -->
        <input type="hidden" name="allRoles" value='${allRoles}'/>
    
        <div class="form-group">
            <label class="col-xs-3 control-label">登录账户</label>
            <div class="col-xs-5">
                <input type="text" class="form-control" placeholder="登录账户" name="userName" id="userName" value="${user.userName}">
            </div>
        </div>
	    <div class="form-group">
	        <label class="col-xs-3 control-label">用户姓名</label>
	        <div class="col-xs-5">
	            <input type="text" class="form-control" placeholder="用户姓名" name="realName" id="realName" value="${user.realName}">
	        </div>
	    </div>
	    <div class="form-group">
	        <label class="col-xs-3 control-label">所属角色</label>
	        <div class="col-xs-5">
	            <!-- 角色tree  start -->
                <ul id="roleTree" class="ztree" style="padding-top: 5px;padding-left: 0px;"></ul>
                <!-- 角色tree  end -->
	        </div>
	    </div>
	    <div class="form-group">
            <label class="col-xs-3 control-label">账户状态</label>
            <div class="col-xs-5">
                <div class="input-group">
                    <select class="form-control" name="status" id="status">
                        <c:forEach items="${userStatus}" var="s">
                            <option value="${s}" <c:if test="${user.status == s}">selected="selected"</c:if> >${s.description}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
	</form>
</div>
</body>
</html>
