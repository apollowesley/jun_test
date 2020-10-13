<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.repository;

<#include "/java_imports.include">

import java.util.Map;

import org.springframework.stereotype.Repository;
import ${basepackage}.common.ui.pagination.*;
import ${basepackage}.repository.base.*;
import ${basepackage}.entity.*;

@Repository
public class ${className}Dao extends BaseMyBatisDao<${className},${table.idColumn.javaType}>{
	
	public Class<${className}> getEntityClass() {
		return ${className}.class;
	}
	
	public int saveOrUpdate(${className} ${classNameLower}) {
		return ${classNameLower}.get${table.idColumn.columnName}() == null ? save(${classNameLower}) : update(${classNameLower});
	}
	
	public Page<${className}> findByPageRequest(PageRequest pageRequest) {
		return pageQuery("${className}.pageSelect",pageRequest);
	}
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return getSqlSession().selectOne("${className}.getBy${column.columnName}",v);
	}	
	</#if>
	</#list>
}