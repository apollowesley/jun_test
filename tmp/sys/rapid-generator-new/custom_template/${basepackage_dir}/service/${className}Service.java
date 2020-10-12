<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import java.util.List;
import java.util.Map;

import ${basepackage}.dto.${className};

<#include "/java_imports.include">

/**
 * @description <#if table.baseds?? >${table.baseds}库 </#if> ${table.sqlName} 表 服务接口
 * @version  Ver 1.0
 * @author   xinxinjs
 *
 */
public interface ${className}Service {
	
}