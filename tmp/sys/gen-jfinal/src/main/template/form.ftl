
<input type="hidden" name="${tableName}.id" value="${'$'}{(${tableName}.id)!}" />

  <#list columns as c>
    <#assign b = true>
    <#list foreigns as f>
      <#if f.forColName == c.columnName>
   <div class="control-group">
	<label class="control-label" for="prependedInput">${c.columnDesc}<#if c.isNullable == "NO"><span class="required">*</span></#if></label>
	<div class="controls">
	  <select class="m-wrap medium" ${(c.isNullable=='NO')?string('required','')} id="${c.columnName}" name="${tableName}.${c.columnName}">
	  	${'<#list'} ${f.refName?lower_case} as c>
	  	<option value="${'$'}{c.id}" ${'$'}{(((${tableName}.${f.refName?lower_case}.id)!1)?number == c.id?number)?string('selected','')}>${'$'}{c.id}</option>
		${r'</#list>'}
	  </select>
	</div>
  </div>
      <#assign b = false>
      </#if>
    </#list>
    <#if b>
  <div class="control-group">
	<label class="control-label" for="prependedInput">${c.columnDesc}<#if c.isNullable == 'NO'><span class="required">*</span></#if></label>
	<div class="controls">
		<input class="m-wrap medium" ${(c.isNullable=='NO')?string('required','')} id="${c.columnName}" name="${tableName}.${c.columnName}" value="${'$'}{(${tableName}.${c.columnName})!}" size="16" type="text">
	</div>
  </div>
    </#if>
  </#list>
         

	<div class="form-actions">

		<button type="submit" class="btn blue"><i class="icon-ok"></i>保存</button>

		<button type="button" class="btn">取消</button>

	</div>

