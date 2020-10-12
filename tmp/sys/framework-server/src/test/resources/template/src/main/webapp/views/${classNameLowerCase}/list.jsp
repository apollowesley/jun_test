<#include "/macro.include"/>
<#include "/custom.include"/>
<#assign className = table.className>
<#assign classNameLower = className?lower_case>
<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="/views/partials/taglibs.jsp" %>
<ol class="breadcrumb">
  <li class="breadcrumb-item">系统管理</li>
  <li class="breadcrumb-item active">${table.tableAlias}</li>
</ol>
<form action="/${classNameLower}" class="form-inline card mx-15" role="form">
      <div class="p-20">
        <#list table.notPkColumns?chunk(4) as row>
        <#list row as column>
        <#if !column.htmlHidden>
        <#if column.isDateTimeColumn>
        <div class="input-group input-datepicker">
        <input name="${column.columnNameLower}Begin" value="<@jspEl 'param.'+column.columnNameLower+'Begin'/>" maxlength="0" placeholder="${column.columnAlias}-起始" class="datepicker form-control" />
        <span class="input-group-addon bg-primary">to</span>
        <input name="${column.columnNameLower}End" value="<@jspEl 'param.'+column.columnNameLower+'End'/>" maxlength="0" placeholder="${column.columnAlias}-结束" class="datepicker form-control" />
        </div>
        <#else>
        <input name="${column.columnNameLower}" value="<@jspEl 'param.${column.columnNameLower }'/>" placeholder="${column.columnAlias}" class="form-control form-group">
        </#if>
        </#if>
        </#list>
        </#list>
        <a class="btn btn-line btn-info" rel="query">Search</a>
        <shiro:hasPermission name="${classNameLower}:edit">
        <a class="btn btn-line btn-info btn-rounded float-xs-right" href="/${classNameLower }/new"><i class="fa fa-plus"></i> Create</a>
        </shiro:hasPermission>
      </div>
      <table class="table table-hover table-striped table-advanced m-0">
        <thead>
        <tr>
          <th class="text-xs-center">#</th>
          <#list table.columns as column>
            <#if !column.htmlHidden>
              <th class="hidden-md-down">${column.columnAlias}</th>
            </#if>
          </#list>
          <th class="text-xs-center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="<@jspEl 'page.items'/>" varStatus="status">
        <tr>
          <td class="text-xs-center"><@jspEl 'page.firstResult + status.count'/></td>
          <#list table.columns as column>
          <#if !column.htmlHidden>
          <#if column.columnNameLower == 'name'>
          <td><a href="/${classNameLower}/<@jspEl 'item.id'/>/edit"><@jspEl 'item.${column.columnNameLower}'/></a></td>
          <#elseif column.isDateTimeColumn>
          <td class="hidden-md-down"><@jspEl 'item.${column.columnNameLower}String'/></td>
          <#else>
          <td class="hidden-md-down"><@jspEl 'item.${column.columnNameLower}'/></td>
          </#if>
          </#if>
          </#list>
          <td class="text-xs-center">
            <div class="hidden-sm-down">
              <a href="${classNameLower}/<@jspEl 'item.id'/>/edit" title="Edit"><i class="fa fa-edit"></i>编辑</a>
              <a href="${classNameLower}/<@jspEl 'item.id'/>/delete" rel="delete" class="text-danger"><i class="fa fa-trash-o"></i>删除</a>
            </div>
            <div class="hidden-md-up btn-group-sm dropdown">
              <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown"><i class="fa fa-cog"></i></button>
              <div class="dropdown-menu dropdown-menu-right w-auto" role="menu">
                <a class="dropdown-item" href="/${classNameLower}/<@jspEl 'item.id'/>/edit"><i class="fa fa-edit"></i> 编辑</a>
                <a class="dropdown-item text-danger" href="/${classNameLower}/<@jspEl 'item.id'/>/delete" rel="delete"><i class="fa fa-trash-o"></i> 删除</a>
              </div>
            </div>
          </td>
        </tr>
        </c:forEach>
        </tbody>
      </table>
      <x:pagination page="<@jspEl 'page'/>" pageRequest="<@jspEl 'pageRequest'/>" class="pagination-sm float-sm-right" />
    </form>
