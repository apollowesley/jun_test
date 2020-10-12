<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao.impl;

import com.dang.rocket.dao.annotation.Dao;
import com.dang.rocket.core.exception.BusinessException;
import com.dang.rocket.dao.mybatis.impl.MyBatisBaseDaoImpl;
import ${basepackage}.dao.${className}Dao;
import ${basepackage}.dto.${className};
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
<#include "/java_imports.include">

/**
 * @description <#if table.baseds?? >${table.baseds}库 </#if> ${table.sqlName} 表 DAO实现
 * @version  Ver 1.0
 * @author   xinxinjs
 *
 */
@Dao<#if table.baseds?? >(dsKey={"${table.baseds}"})</#if>
public class ${className}DaoImpl extends MyBatisBaseDaoImpl<${className},<#if table.idColumn?? >${table.idColumn.javaType}<#else>java.lang.Object</#if>> implements  ${className}Dao{	
	
}
