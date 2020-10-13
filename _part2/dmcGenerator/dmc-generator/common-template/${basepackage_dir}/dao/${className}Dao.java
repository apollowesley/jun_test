<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

import ${basepackage}.common.dao.BaseDao;
import ${basepackage}.model.bo.${className};

import org.springframework.stereotype.Repository;


<#include "/java_imports.include">


@Repository("${classNameLower}Dao")
public class ${className}Dao extends  BaseDao<${className}> {
	
}
