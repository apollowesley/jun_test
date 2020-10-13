<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign pkJavaType = table.idColumn.javaType>   

package ${basepackage}.controller;

import java.util.Map;

import cn.fw.core.common.web.JsonMap;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import ${basepackage}.controller.base.*;
import ${basepackage}.common.ui.pagination.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


<#include "/java_imports.include">
@Controller
@RequestMapping("${classNameLowerCase}")
public class ${className}Controller extends BaseRestSpringController<${className},${pkJavaType}>{
	protected static final String DEFAULT_SORT_COLUMNS = null; 		//默认多列排序,example: username desc,createTime asc
	private final String LIST_VIEW = "redirect:/${classNameLowerCase}";
	
	private ${className}Manager ${classNameFirstLower}Manager;
	
	/** 列表 */
	@Override
	public String index(ModelMap model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		PageRequest pageRequest = pageRequest(request, DEFAULT_SORT_COLUMNS);
		Page<${className}> page = ${classNameFirstLower}Manager.findByPageRequest(pageRequest);
		
		toModelMap(page, pageRequest, model);
		return "${classNameLowerCase}/list";
	}
	
	/** 显示 */
	@Override
	public String show(ModelMap model,@PathVariable ${pkJavaType} id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		${className} ${classNameFirstLower} = (${className})${classNameFirstLower}Manager.getById(id);
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return "${classNameLowerCase}/form";
	}

	/** 进入新增 */
	@Override
	public String _new(ModelMap model,HttpServletRequest request,HttpServletResponse response,${className} ${classNameFirstLower}) throws Exception {
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return "${classNameLowerCase}/form";
	}
	


	/** 编辑 */
	@Override
	public String edit(ModelMap model,@PathVariable ${pkJavaType} id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		${className} ${classNameFirstLower} = (${className})${classNameFirstLower}Manager.getById(id);
		model.addAttribute("${classNameFirstLower}",${classNameFirstLower});
		return "${classNameLowerCase}/form";
	}

	
	/** 保存新增 */
	@Override
	public Object create(ModelMap model, @Valid ${className} ${classNameFirstLower}, BindingResult errors, HttpServletRequest request)
			throws Exception {
		if (errors.hasErrors()) {
			return filedErrors(errors);
		}
		${classNameFirstLower}Manager.save(${classNameFirstLower});
		return JsonMap.succeed();
	}

	/** 保存更新 */
	@Override
	public Object update(ModelMap model, @PathVariable ${pkJavaType} id, @Valid ${className} ${classNameFirstLower},
			BindingResult errors, HttpServletRequest request) throws Exception {
		if (errors.hasErrors()) {
			return filedErrors(errors);
		}
		${classNameFirstLower}Manager.update(${classNameFirstLower});
		return JsonMap.succeed();
	}
	

	/** 删除 */
	@Override
	public Object delete(@PathVariable ${pkJavaType} id,ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		${classNameFirstLower}Manager.deleteByIdLogical(id);
		return JsonMap.succeed();
	}

	/** 批量删除 */
	@Override
	public String batchDelete(@RequestParam("items") ${pkJavaType}[] items,ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		${classNameFirstLower}Manager.deleteBatchLogical(items);
		return JsonMap.succeed();
	}
	
	public void set${className}Manager(${className}Manager ${classNameFirstLower}Manager) {
		this.${classNameFirstLower}Manager = ${classNameFirstLower}Manager;
	}
}