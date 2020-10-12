<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;
import com.dang.rocket.dao.annotation.Dao;
import com.dang.rocket.dao.mybatis.MyBatisBaseDao;
import java.util.Map;
import java.util.List;
import ${basepackage}.dto.${className};
<#include "/java_imports.include">

/**
 * @description <#if table.baseds?? >${table.baseds}库 </#if> ${table.sqlName} 表 DAO接口
 * @version  Ver 1.0
 * @author   xinxinjs
 *
 */
public interface ${className}Dao extends MyBatisBaseDao<${className}, <#if table.idColumn?? >${table.idColumn.javaType}<#else>java.lang.Object</#if>>  {
	
}
