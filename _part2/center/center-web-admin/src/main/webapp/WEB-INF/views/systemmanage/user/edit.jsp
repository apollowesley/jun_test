<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp" %>
<div style="padding: 10px 0 10px 0px">  

    <%-- 设置 form 的action 地址，id为空，为添加的地址，否则为修改的地址 --%>
    <c:choose>
        <c:when test="${empty user.id}"><c:set var="action" value="user/add"></c:set></c:when>
        <c:otherwise><c:set var="action" value="user/update"></c:set></c:otherwise>
    </c:choose>
	<form:form id="form" method="post" action="${action }" modelAttribute="user" style="display: inline;" class="tab">
	    <form:hidden path="id"/>
		<table>
		<c:choose>
		     <c:when test="${empty user.id}">
			   <tr>
					<td class="bule" align="right" width="35%"><span class="requiredField">*</span>用户名:</td>
					<td align="left">
					   <form:input path="userName" class="easyui-validatebox"  data-options="required:true,validType:['length[0,20]']"/>
					</td>
				</tr>
			   <tr>
					<td class="bule" align="right" width="35%"><span class="requiredField">*</span>密码:</td>
					<td align="left">
					   <form:password path="password" class="easyui-validatebox"  data-options="required:true,validType:['length[0,20]']"/>
					</td>
				</tr>
				<tr>
					<td class="bule" align="right" width="35%"><span class="requiredField">*</span>确认密码:</td>
					<td align="left">
					   <input id="confirmPassword" name="confirmPassword" class="easyui-validatebox" data-options="required:true"  validType="equalTo['#password']"  type="password" invalidMessage="两次输入密码不匹配"/>
					</td>
				</tr>
			</c:when>
		        <c:otherwise>
                    <tr>
						<td align="right">用户名:</td>
						<td>
						   ${user.userName }
						</td>
					</tr>
                </c:otherwise>
		    </c:choose>
		   <tr>
				<td class="bule" align="right" width="35%">全名:</td>
				<td align="left">
				   <form:input path="fullName" class="easyui-validatebox"  data-options="required:false,validType:['length[0,20]']"/>
				</td>
			</tr>
		   <tr>
				<td class="bule" align="right" width="35%">性别:</td>
				<td align="left">
				   <input class="easyui-combobox" 
			            name="gender"
			            data-options="
			                    editable:false,
			                    url:'code/find?key=userGender&selected=${user.gender }',
			                    method:'get',
			                    valueField:'nodeKey',
			                    textField:'nodeValue',
			                    panelHeight:'auto'
			            ">
				</td>
			</tr>
            <tr>
				<td class="bule" align="right" width="35%">年龄:</td>
				<td align="left">
				  <form:input path="age" class="easyui-numberbox" min="0"  max="100"   data-options="required:false"/> 
				</td>
			</tr>
		   <tr>
				<td class="bule" align="right" width="35%">地址:</td>
				<td align="left">
				   <form:input path="address" class="easyui-validatebox"  data-options="required:false,validType:['length[0,20]']"/>
				</td>
			</tr>
		   <tr>
				<td class="bule" align="right" width="35%">移动电话:</td>
				<td align="left">
				   <form:input path="mobilePhone" class="easyui-numberbox"  data-options="required:false,validType:['length[11,11]']"/>
				</td>
			</tr>
		   <tr>
				<td class="bule" align="right" width="35%">邮箱:</td>
				<td align="left">
				   <form:input path="email" class="easyui-validatebox"  data-options="required:false,validType:['email', 'length[0,20]']"/>
				</td>
			</tr>
		   <tr>
				<td class="bule" align="right" width="35%">描述:</td>
				<td align="left">
				   <form:textarea path="description" class="easyui-validatebox"  data-options="required:false, validType:['length[0,50]']" />
				</td>
			</tr>
		</table>
	</form:form>
	
	<div
		style="position: absolute; bottom: 0px; right: 0px; background-color: #F4F4F4; height: 40px; width: 100%; text-align: center;">
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-ok'" style="margin-top: 10px;" onclick="submitForm();">保存</a> 
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'icon-reload'" style="margin-top: 10px;" onclick="resetForm();">重置</a>
	</div>
</div>