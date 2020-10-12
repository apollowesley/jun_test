
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service;

<#include "/java_imports.include">
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ${className}Service extends BaseService<${className},${table.idColumn.javaType}>{

	@Autowired
	private ${className}Dao ${classNameLower}Dao;

<#list table.columns as column>
	<#if column.unique && !column.pk>
	@Transactional(readOnly=true)
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return ${classNameLower}Dao.getBy${column.columnName}(v);
	}	
	</#if>
</#list>
}