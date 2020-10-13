<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>
package ${basepackage}.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schrodinger.zbdp.framework.base.controller.ZbdpBaseController;
import com.schrodinger.zcommon.model.impl.BaseDTO;
import com.schrodinger.zcommon.page.ZPage;
import com.schrodinger.zutil.date.DateTimeUtils;

import ${basepackage}.model.${className}PO;
import ${basepackage}.service.I${className}Service;

@Controller
@RequestMapping("${classNameFirstLower}Controller")
public class ${className}Controller  extends ZbdpBaseController {
	
	@Resource
	private I${className}Service ${classNameFirstLower}ServiceImpl;
	
	private final String JSP_PATH = "/${namespace_dir}/";
	
	@RequestMapping("to${className}.do")
	public ModelAndView to${className}() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(JSP_PATH + "${classNameFirstLower}");
		return mav;
	}

	@RequestMapping("get${className}Page.do")
    @ResponseBody
    public ZPage<${className}PO> get${className}Page(BaseDTO baseDTO) {
    	Gson gson = new GsonBuilder().setDateFormat(DateTimeUtils.DATETIME_FORMAT).create();
        ${className}PO ${classNameFirstLower}PO = gson.fromJson(baseDTO.getParamStr(), ${className}PO.class);
        ZPage<${className}PO> page = ${classNameFirstLower}ServiceImpl.get${className}Page(${classNameFirstLower}PO);
        return page;
    }
	
    @RequestMapping("delete${className}s.do")
    @ResponseBody
    public BaseDTO delete${className}s(BaseDTO baseDTO) {
        String oidStrs = baseDTO.getParamStr();
        List<${className}PO> list = new ArrayList<${className}PO>();
        String [] oidArr = oidStrs.split(";");
        for(String oid: oidArr){
            if(StringUtils.isNotEmpty(oid)){
                ${className}PO ${classNameFirstLower}PO = new ${className}PO();
                ${classNameFirstLower}PO.setOid(oid);
                list.add(${classNameFirstLower}PO);
            }
        }
        ${classNameFirstLower}ServiceImpl.remove(list);
        return baseDTO;
    }
    
    @RequestMapping("save${className}.do")
    @ResponseBody
    public ${className}PO save${className}(BaseDTO baseDTO) {
        Gson gson = new GsonBuilder().setDateFormat(DateTimeUtils.DATETIME_FORMAT).create();
        ${className}PO ${classNameFirstLower}PO = gson.fromJson(baseDTO.getParamStr(), ${className}PO.class);
        ${classNameFirstLower}ServiceImpl.saveOrUpdateIsNew(${classNameFirstLower}PO);
        return ${classNameFirstLower}PO;
    }
    
}
