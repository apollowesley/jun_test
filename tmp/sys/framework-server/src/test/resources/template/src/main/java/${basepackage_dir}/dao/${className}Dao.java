<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

<#include "/java_imports.include">

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ${className}Dao extends BaseMyBatisDao<${className}, ${table.idColumn.javaType}>{
	
	public Page<${className}> findByPageRequest(PageRequest pr) {
		return pageQuery("${className}.paging", pr);
	}
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return getSqlSession().selectOne("${className}.getBy${column.columnName}",v);
	}	
	</#if>
	</#list>
}