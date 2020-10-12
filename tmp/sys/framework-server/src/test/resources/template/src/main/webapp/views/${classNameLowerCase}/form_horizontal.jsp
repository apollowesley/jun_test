<#include "/macro.include"/>
<#include "/custom.include"/>
<#assign className = table.className />
<#assign classNameLower = className?lower_case />
<#assign classNameFirstLower = className?uncap_first />
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/views/partials/taglibs.jsp"%>
<c:set var="_edit_" value="<@jspEl 'not empty '+classNameFirstLower+'.'+table.idColumn.columnNameFirstLower/>" />
<div class="p-20 bg-white">
  <h1 class="h3 m-0"><@jspEl '_edit_ ? \'编辑\' : \'新增\' '/>${table.tableAlias}</h1>
</div>
<ol class="breadcrumb">
  <li class="breadcrumb-item">系统管理</li>
  <li class="breadcrumb-item"><a href="/${classNameLower}">${table.tableAlias}</a></li>
  <li class="breadcrumb-item active"><@jspEl '_edit_ ? \'编辑\' : \'新增\' '/>${table.tableAlias}</li>
</ol>
<form:form modelAttribute="${classNameFirstLower}" method="post" class="row-alt bg-white p-y-1" action="<@jspEl 'ctx'/>/${classNameLower}/<@jspEl '_edit_ ? '+classNameFirstLower+'.'+table.idColumn.columnNameFirstLower +'.toString().concat(\'/update\') : \'create\'' />" data-parsley-validate="true" autocomplete="off">
  <#list table.columns as column><#if column.htmlHidden>
  <form:hidden path="${column.columnNameLower}" />
  </#if></#list>
  <div class="col-lg-6 col-md-12">
    <div class="card">
      <div class="pt-20 px-20">
        <h4 class="card-title">必填项</h4>
        <span class="text-muted">These fileds are <code>required</code></span>
      </div>
      <div class="p-20">
      <div class="row">
      <#list table.columns as column>
      <#if column.htmlHidden>
      <#elseif column.isDateTimeColumn && !column.isNullable()>
      <div class="form-group col-md-6">
        <label>
          <form:input path="${column.columnNameLower}String" class="datepicker-here form-control ${column.validateString}" readonly="readonly" placeholder="请选择${column.columnAlias }" />
        </label>
      </div>
      <#elseif !column.isNullable()>
      <div class="form-group col-md-6">
        <label>${column.columnAlias }
          <form:input path="${column.columnNameLower}" placeholder="${column.columnAlias }" class="form-control required" />
        </label>
      </div>
      </#if>
      <#if (column_index > 0) && (column_index % 6 == 0)></#if>
      </#list>
      </div>
      </div>
    </div>
  </div>
  <div class="col-lg-6 col-md-12">
    <div class="card">
      <div class="pt-20 px-20">
        <h4 class="card-title">选填项</h4>
        <span>These fileds are <code class="text-info">optional</code></span>
      </div>
      <div class="p-20">
        <div class="row">
        <#list table.columns as column>
      <#if column.htmlHidden>
      <#elseif column.isDateTimeColumn && column.isNullable()>
      <div class="form-group col-md-6">
        <label>${column.columnAlias }
          <form:input path="${column.columnNameLower}String" class="datepicker-here form-control ${column.validateString}" readonly="readonly" placeholder="请选择${column.columnAlias }" />
        </label>
      </div>
      <#elseif column.isNullable()>
      <div class="form-group col-md-6">
        <label>${column.columnAlias }
          <form:input path="${column.columnNameLower}" class="form-control ${column.validateString}" placeholder="${column.columnAlias }" maxlength="${column.size}" />
        </label>
      </div>
      </#if>
      <#if (column_index > 0) && (column_index % 6 == 0)></#if>
      </#list>
      </div>
    </div>
  </div>
  <div class="col-md-12">
    <shiro:hasPermission name="${classNameLower}:edit">
    <button class="btn btn-line btn-info btn-rounded">Submit</button>
    </shiro:hasPermission>
    <a class="btn btn-line btn-info btn-rounded" href="javascript:history.back()">Cancel</a>
  </div>
</div>
</form:form>
