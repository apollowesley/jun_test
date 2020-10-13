<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;





import ${basepackage}.query.${className}Query;

import com.github.pagehelper.PageInfo;
import ${basepackage}.util.ActResult;
import ${basepackage}.web.controller.${className}Controller;

import ${basepackage}.model.${className};
import ${basepackage}.service.${className}Service;

@Controller
@RequestMapping("${classNameLower}")
@ResponseBody 
public class ${className}Controller {
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Autowired
	@Qualifier("${classNameLower}Service")
	private ${className}Service ${classNameLower}Service;
	
	
	private static Logger logger=LoggerFactory.getLogger(${className}Controller.class);
	
	
	/** 
	 * 根据id查看
	 **/
	@RequestMapping(value="show",method=RequestMethod.GET)
	public ${className} show(HttpServletRequest request,HttpServletResponse response)   {
		<@generateIdParameter/>
		${className} ${classNameLower} = ${classNameLower}Service.getById(id);
		return ${classNameLower};
	}
	
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public ActResult list(HttpServletRequest request,${className}Query  query)   {
		
		PageInfo  page =${classNameLower}Service.findPage(query);
		return ActResult.success(page);
	}
	
	
	
	/** 
	 * 保存新增对象
	 **/
	@RequestMapping(value="save",method=RequestMethod.POST)
	public ActResult save(HttpServletRequest request,HttpServletResponse response,${className} ${classNameLower}) throws Exception {
		try {
			${classNameLower}Service.save(${classNameLower});	
			return ActResult.success(${classNameLower});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ActResult.fail(e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * 更新对象
	 **/
	@RequestMapping(value="update",method=RequestMethod.POST)
	public ActResult update(HttpServletRequest request,${className} ${classNameLower}) throws Exception {
		try {
			${classNameLower}Service.update(${classNameLower});		
			return ActResult.success(${classNameLower});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ActResult.fail(e.getLocalizedMessage());
		}
	}
	
	
	
}

<#macro generateIdParameter>
	<#if table.compositeId>
		${className}Id id = new ${className}Id();
		bind(request, id);
	<#else>
		<#list table.compositeIdColumns as column>
		${column.javaType} id = new ${column.javaType}(request.getParameter("${column.columnNameLower}"));
		</#list>
	</#if>
</#macro>