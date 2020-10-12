<#include "/custom.include">
<#assign className = table.className>
<#assign classNameFirstLower = className?uncap_first>
<#assign classNameLowerCase = className?lower_case>
<#assign pkJavaType = table.idColumn.javaType>

package ${basepackage}.controller;

<#include "/java_imports.include">

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.backflow.admin.common.secure.Permissions;;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@RequestMapping("${classNameLowerCase}")
public class ${className}Controller extends BaseRestSpringController<${className},${pkJavaType}>{
	private static final String DEFAULT_SORT_COLUMNS = "id desc"; // 默认排序, 如: id desc, created asc

	@Autowired
	private ${className}Service ${classNameFirstLower}Service;

	/* 列表 */
	@Override
	@Permissions("${classNameLowerCase}.view")
	public Object index(${className} ${classNameFirstLower}, HttpServletRequest request) throws Exception {
		PageRequest pr = pageRequest(request, DEFAULT_SORT_COLUMNS);
		Page<${className}> page = ${classNameFirstLower}Service.findByPageRequest(pr);

		toModelMap(page, pr, json);
		return "${classNameLowerCase}/list";
	}

	/* 显示 */
	@Override
	@Permissions("${classNameLowerCase}.view")
	public String view(JsonMap json,@PathVariable ${pkJavaType} id, HttpServletRequest request) throws Exception {
		${className} ${classNameFirstLower} = ${classNameFirstLower}Service.getById(id);
		json.put("${classNameFirstLower}",${classNameFirstLower});
		return "${classNameLowerCase}/form";
	}

	/* 进入新增 */
	@Override
	@Permissions("${classNameLowerCase}.edit")
	public String _new(JsonMap json,HttpServletRequest request,HttpServletResponse response,${className} ${classNameFirstLower}) throws Exception {
		json.put("${classNameFirstLower}",${classNameFirstLower});
		return "${classNameLowerCase}/form";
	}



	/* 编辑 */
	@Override
	@Permissions("${classNameLowerCase}.view")
	public String edit(JsonMap json,@PathVariable ${pkJavaType} id, HttpServletRequest request) throws Exception {
		${className} ${classNameFirstLower} = ${classNameFirstLower}Service.getById(id);
		json.put("${classNameFirstLower}",${classNameFirstLower});
		return "${classNameLowerCase}/form";
	}


	/* 保存新增 */
	@Override
	@Permissions("${classNameLowerCase}.edit")
	public Object create(@Valid ${className} ${classNameFirstLower}, BindingResult errors, HttpServletRequest request)
			throws Exception {
        JsonMap json = JsonMap.succeed();
		if (errors.hasErrors()) {
			return filedErrors(errors, json);
		}
		${classNameFirstLower}Service.save(${classNameFirstLower});
		return json;
	}

	/* 保存更新 */
	@Override
	@Permissions("${classNameLowerCase}.edit")
	public Object update(@PathVariable ${pkJavaType} id, @Valid ${className} ${classNameFirstLower}, BindingResult errors, HttpServletRequest request) throws Exception {
        JsonMap json = JsonMap.succeed();
		if (errors.hasErrors()) {
			return filedErrors(errors, json);
		}
		${classNameFirstLower}Service.updateSelective(${classNameFirstLower});
		return json;
	}


	/* 删除 */
	@Override
	@Permissions("${classNameLowerCase}.del")
	public Object delete(@PathVariable ${pkJavaType} id,HttpServletRequest request) {
		${classNameFirstLower}Service.deleteById(id);
		return JsonMap.succeed();
	}

	/* 批量删除 */
	@Override
	@Permissions("${classNameLowerCase}.del")
	public Object delete(@RequestParam("items") Set<${pkJavaType}> items,HttpServletRequest request) {
		${classNameFirstLower}Service.deleteBatch(items);
		return JsonMap.succeed();
	}
}