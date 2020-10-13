<#include "/macro.include"/>
<#include "/custom.include"/>
<#assign className = table.className />
<#assign classNameLower = className?lower_case />
<#assign classNameFirstLower = className?uncap_first />
<%@page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/comm/taglibs.jsp"%>
<div class="container">
  <section class="page-title">
    <div class="row">
      <div class="col-sm-8">
        <h1 class="main-title">编辑${table.tableAlias}</h1>
        <span class="main-description">We set out to create an easy, powerful and versatile form layout system. A combination of form styles and the Bootstrap grid means you can do almost anything.</span>
      </div>
      <ol class="breadcrumb">
        <li><span>系统管理</span></li>
        <li class="active"><span>${table.tableAlias}</span></li>
      </ol>
    </div>
  </section>
  <div class="row row-fullw bg-white">
    <form:form modelAttribute="${classNameFirstLower}" action="<@jspEl 'ctx'/>${classNameLower}/update" method="post" class="validation-form" role="form">
      <div class="col-md-12"><div class="alert-message alert no-display"></div></div>
      <#list table.columns as column><#if column.htmlHidden>
      <form:hidden path="${column.columnNameLower}" />
      </#if></#list>
      <div class="col-lg-6 col-md-12">
        <div class="panel panel-white">
          <div class="panel-heading">
            <h5 class="panel-title">必填项</h5>
            <p class="text-small margin-top-15">These fileds are <code>required</code></p>
          </div>
          <div class="panel-body">
          <#list table.columns as column>
          <#if column.htmlHidden>
          <#elseif column.isDateTimeColumn && !column.isNullable()>
            <div class="form-group">
              <label><form:input path="${column.columnNameLower}String" class="datepicker form-control ${column.validateString}" readonly="readonly" placeholder="请选择${column.columnAlias }" />></label>
            </div>
          <#elseif !column.isNullable()>
            <div class="form-group">
              <label>${column.columnAlias }<i class="symbol required"></i><form:input path="${column.columnNameLower}" placeholder="${column.columnAlias }" class="form-control required" aria-required="true" /></label>
            </div>
          </#if>
          <#if (column_index > 0) && (column_index % 6 == 0)></#if>
          </#list>
          </div>
        </div>
      </div>
      <div class="col-lg-6 col-md-12">
        <div class="panel panel-white">
          <div class="panel-heading">
            <h5 class="panel-title">选填项</h5>
            <p class="text-small margin-top-15">These fileds are <code class="text-info">optional</code></p>
          </div>
          <div class="panel-body">
          <#list table.columns as column>
          <#if column.htmlHidden>
          <#elseif column.isDateTimeColumn && column.isNullable()>
            <div class="form-group">
              <label>${column.columnAlias }<form:input path="${column.columnNameLower}String" class="datepicker form-control ${column.validateString}" readonly="readonly" placeholder="请选择${column.columnAlias }" /></label>
            </div>
          <#elseif column.isNullable()>
            <div class="form-group">
              <label>${column.columnAlias }<form:input path="${column.columnNameLower}" class="form-control ${column.validateString}" placeholder="${column.columnAlias }" maxlength="${column.size}" /></label>
            </div>
          </#if>
          <#if (column_index > 0) && (column_index % 6 == 0)></#if>
          </#list>
          </div>
        </div>
      </div>
      <div class="col-md-12">
        <button class="btn btn-o btn-primary margin-left-15 margin-right-30 ">Submit</button>
        <a class="btn btn-o btn-primary" href="<@jspEl 'ctx' />/${classNameLower}">Cancel</a>
      </div>
    </form:form>
  </div>
</div>