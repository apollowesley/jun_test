<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.dang.rocket.core.exception.BusinessException;
import ${basepackage}.dto.${className};
import ${basepackage}.dao.${className}Dao;
import ${basepackage}.service.${className}Service;

<#include "/java_imports.include">

/**
 * @description <#if table.baseds?? >${table.baseds}库 </#if> ${table.sqlName} 表 服务实现
 * @version  Ver 1.0
 * @author   xinxinjs
 *
 */
@Service
public class ${className}ServiceImpl implements ${className}Service{
	@Resource
	private ${className}Dao ${classNameLower}Dao;
	
}
