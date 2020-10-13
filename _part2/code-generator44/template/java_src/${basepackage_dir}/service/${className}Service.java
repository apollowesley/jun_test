<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import ${basepackage}.service.base.EntityService;
import ${basepackage}.query.${className}Query;
import ${basepackage}.model.${className};

import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;

public interface ${className}Service extends EntityService<${className},${table.idColumn.javaType}>{
	
	
	
	public PageInfo findPage(${className}Query query);
	
<#list table.columns as column>
	<#if column.unique && !column.pk>	
	public ${className} getBy${column.columnName}(${column.javaType} v);
	
	</#if>
</#list>
}
