<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import ${basepackage}.repository.base.*;
import ${basepackage}.common.ui.pagination.*;
import java.util.Map;
import org.springframework.stereotype.Service;
import ${basepackage}.service.base.BaseManager;
import org.springframework.transaction.annotation.Transactional;

<#include "/java_imports.include">
@Service
public class ${className}Manager extends BaseManager<${className},${table.idColumn.javaType}>{

	private ${className}Dao ${classNameLower}Dao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void set${className}Dao(${className}Dao ${classNameLower}Dao) {
		this.${classNameLower}Dao = ${classNameLower}Dao;
	}
	public EntityDao<${className}, ${table.idColumn.javaType}> getEntityDao() {
		return this.${classNameLower}Dao;
	}
	
	@Transactional(readOnly=true)
	public Page<${className}> findByPageRequest(PageRequest pr) {
		return ${classNameLower}Dao.findByPageRequest(pr);
	}
	
<#list table.columns as column>
	<#if column.unique && !column.pk>
	@Transactional(readOnly=true)
	public ${className} getBy${column.columnName}(${column.javaType} v) {
		return ${classNameLower}Dao.getBy${column.columnName}(v);
	}	
	</#if>
</#list>
}