<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import ${basepackage}.service.base.EntityDao;
import ${basepackage}.service.base.BaseService;
import ${basepackage}.query.${className}Query;
import ${basepackage}.model.${className};
import ${basepackage}.dao.${className}Dao;
import ${basepackage}.service.${className}Service;

@Service("${classNameLower}Service")
public class ${className}ServiceImpl extends BaseService<${className},${table.idColumn.javaType}> implements  ${className}Service{
	@Autowired
	@Qualifier("${classNameLower}Dao")
	private ${className}Dao ${classNameLower}Dao;
	
	public EntityDao getEntityDao() {
		return this.${classNameLower}Dao;
	}
	
	public PageInfo findPage(${className}Query query) {
		PageHelper.startPage(query);
		return new PageInfo(${classNameLower}Dao.findPage(query));
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
