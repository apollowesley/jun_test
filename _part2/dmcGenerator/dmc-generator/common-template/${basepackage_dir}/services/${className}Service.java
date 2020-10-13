<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ${basepackage}.dao.${className}Dao;


<#include "/java_imports.include">

@Service("${classNameLower}Service")
public class  ${className}Service {
	
	@Resource(name = "${classNameLower}Dao")
	private ${className}Dao ${classNameLower}Dao;
	
}