<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

import java.util.List;
import ${basepackage}.model.${className};
import ${basepackage}.query.${className}Query;
import ${basepackage}.service.base.EntityDao;

public interface ${className}Dao extends  EntityDao<${className},${table.idColumn.javaType}>{
	
	public List findPage(${className}Query query);
	


}
