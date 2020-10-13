<#include "/macro.include"/>
<#include "/custom.include"/>
<#assign className = table.className>
<#assign classNameLower = className?lower_case>
<%@page language="java" contentType="text/html;charset=utf-8"%>
<%@page import="${basepackage}.entity.${className}" %>
<%@include file="/comm/taglibs.jsp"%>
<div class="container">
  <section class="page-title">
    <div class="row">
      <div class="col-sm-8">
        <h1 class="main-title">${table.tableAlias}</h1>
        <span class="main-description">Permissions Maintain. Define permissions for both resources on the menu and resources that are not on the menu.
          <small class="block">Webopedia - Online Tech Dictionary for IT Professionals</small>
        </span>
      </div>
      <ol class="breadcrumb">
        <li><span>System management</span></li>
        <li class="active"><span>${table.tableAlias}</span></li>
      </ol>
    </div>
  </section>
  <div class="row row-fullw bg-white">
    <form action="<@jspEl 'ctx'/>${classNameLower}" class="col-md-12 form-inline">
      <div class="panel panel-white panel-shadowed">
        <div class="panel-heading">
          <div class="panel-title">Actions</div>
          <small>create delete search etc.</small>
        </div>
        <div class="panel-body">
        <#list table.notPkColumns?chunk(4) as row>
        <#list row as column>
        <#if !column.htmlHidden>
        <#if column.isDateTimeColumn>
          <div class="input-group input-datepicker">
            <input type="text" class="datepicker form-control" value="<@jspEl 'param.'+column.columnNameLower+'Begin'/>" name="${column.columnNameLower}Begin" maxlength="0" placeholder="${column.columnAlias}-起始" />
            <span class="input-group-addon bg-primary">to</span>
            <input type="text" class="datepicker form-control" value="<@jspEl 'param.'+column.columnNameLower+'End'/>" name="${column.columnNameLower}End" maxlength="0" placeholder="${column.columnAlias}-结束" />
          </div>
        <#else>
          <div class="form-group">
            <input type="text" name="${column.columnNameLower}" value="<@jspEl 'param.${column.columnNameLower }'/>" placeholder="${column.columnAlias}" class="form-control">
          </div>
        </#if>
        </#if>
        </#list>
        </#list>
          <a class="btn btn-o btn-primary" rel="query">Search</a>
          <a class="btn btn-primary pull-right" href="<@jspEl 'ctx'/>${classNameLower }/new"><i class="ti-plus"></i> Create</a>
        </div>
      </div>
      <table class="table table-hover table-striped">
        <thead>
          <tr>
            <th class='center'>#</th>
          <#list table.columns as column>
          <#if !column.htmlHidden>
            <th class="hidden-xs">${column.columnAlias}</th>
          </#if>
          </#list>
            <th class="center">操作</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="item" items="<@jspEl 'page.result'/>" varStatus="status">
            <tr>
              <td class="center"><@jspEl 'page.firstResult + status.count'/></td>
            <#list table.columns as column><#if !column.htmlHidden>
            <#if column.columnNameLower == 'name'>
              <td><a href="<@jspEl 'ctx'/>${classNameLower}/<@jspEl 'item.id'/>/edit"><@jspEl 'item.${column.columnNameLower}'/></a></td>
            <#elseif column.isDateTimeColumn>
              <td class="hidden-xs"><@jspEl 'item.${column.columnNameLower}String'/></td>
            <#else>
              <td class="hidden-xs"><@jspEl 'item.${column.columnNameLower}'/></td>
            </#if>
            </#if>
            </#list>
              <td class="center ">
                <div class="visible-md visible-lg hidden-sm hidden-xs">
                  <a href="<@jspEl 'ctx'/>${classNameLower}/<@jspEl 'item.id'/>/edit" class="btn-xs hint--top" data-hint="Edit"><i class="fa fa-edit"></i> 编辑</a>
                  <a href="<@jspEl 'ctx'/>${classNameLower}/<@jspEl 'item.id'/>/delete" rel="delete" class="btn-xs hint--top" data-hint="Delete"><i class="fa fa-times"></i> 删除</a>
                </div>
                <div class="visible-xs visible-sm hidden-md hidden-lg">
                  <div class="btn-group dropdown">
                    <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown"><i class="fa fa-cog"></i>&nbsp;<span class="caret"></span></button>
                    <ul class="dropdown-menu pull-right dropdown-light" role="menu">
                      <li><a href="<@jspEl 'ctx'/>${classNameLower}/<@jspEl 'item.id'/>/edit">Edit</a></li>
                      <li><a href="<@jspEl 'ctx'/>${classNameLower}/<@jspEl 'item.id'/>/add_child">Add child permisstion</a></li>
                      <li><a href="<@jspEl 'ctx'/>${classNameLower}/<@jspEl 'item.id'/>/delete" rel="delete">Delete</a></li>
                    </ul>
                  </div>
                </div>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <ug:pagination page="<@jspEl 'page'/>" />
    </form>
  </div>
</div>
