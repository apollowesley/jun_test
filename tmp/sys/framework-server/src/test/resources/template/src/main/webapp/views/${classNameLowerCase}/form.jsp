<#include "/macro.include"/>
<#include "/custom.include"/>
<#assign className = table.className />
<#assign classNameLower = className?lower_case />
<#assign classNameFirstLower = className?uncap_first />
<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="/views/partials/taglibs.jsp" %>
<c:set var="_edit_" value="<@jspEl 'not empty '+classNameFirstLower+'.'+table.idColumn.columnNameFirstLower/>"/>
<ol class="breadcrumb">
  <li class="breadcrumb-item">系统管理</li>
  <li class="breadcrumb-item"><a href="/${classNameLower}">${table.tableAlias}</a></li>
  <li class="breadcrumb-item active"><@jspEl '_edit_ ? \'编辑\' : \'新增\' '/>${table.tableAlias}</li>
</ol>
<form:form modelAttribute="${classNameFirstLower}" method="post" class="row-alt px-1" action="/${classNameLower}/<@jspEl '_edit_ ? '+classNameFirstLower+'.'+table.idColumn.columnNameFirstLower +'.toString().concat(\'/update\') : \'create\'' />" data-parsley-validate="true" autocomplete="off">
  <#list table.columns as column>
  <#if column.htmlHidden>
    <form:hidden path="${column.columnNameLower}"/>
  </#if>
  </#list>
  <div class="card col-md-12 p-20">
    <div class="mb-20">
      <h4 class="card-title">必填项</h4>
      <span class="text-muted">These fileds are <code>required</code></span>
    </div>
    <#list table.columns as column>
    <#if column.htmlHidden>
    <#elseif column.isDateTimeColumn && !column.isNullable()>
    <div class="form-group row">
      <label class="col-form-label col-sm-2">${column.columnAlias }</label>
      <div class="col-sm-10">
        <form:input path="${column.columnNameLower}String" class="datepicker-here form-control ${column.validateString}" readonly="readonly" placeholder="请选择${column.columnAlias }"/>
      </div>
    </div>
    <#elseif !column.isNullable()>
    <div class="form-group row">
      <label class="col-form-label col-sm-2">${column.columnAlias }</label>
      <div class="col-sm-10">
        <form:input path="${column.columnNameLower}" placeholder="${column.columnAlias }" class="form-control required"/>
      </div>
    </div>
    </#if>
    <#if (column_index > 0) && (column_index % 6 == 0)></#if>
    </#list>
  </div>
  <div class="card col-md-12 p-20">
    <h4 class="card-title">选填项</h4>
    <span>These fileds are <code class="text-info">optional</code></span>
    <div class="py-20">
    <#list table.columns as column>
      <#if column.htmlHidden>
      <#elseif column.isDateTimeColumn && column.isNullable()>
      <div class="form-group row">
        <label class="col-form-label col-sm-2">${column.columnAlias }</label>
        <div class="col-sm-10">
          <form:input path="${column.columnNameLower}String" class="datepicker-here form-control ${column.validateString}" readonly="readonly" placeholder="请选择${column.columnAlias }"/>
        </div>
      </div>
      <#elseif column.isNullable()>
      <div class="form-group row">
        <label class="col-form-label col-sm-2">${column.columnAlias }</label>
        <div class="col-sm-10">
          <form:input path="${column.columnNameLower}" class="form-control ${column.validateString}" placeholder="${column.columnAlias }" maxlength="${column.size}"/>
        </div>
      </div>
      </#if>
      <#if (column_index > 0) && (column_index % 6 == 0)></#if>
    </#list>
    </div>
  </div>
  <div class="col-md-12">
  <shiro:hasPermission name="${classNameLower}:edit">
    <button class="btn btn-line btn-info btn-rounded">Submit</button>
  </shiro:hasPermission>
    <a class="btn btn-line btn-info btn-rounded" href="javascript:history.back()">Cancel</a>
  </div>
</form:form>
