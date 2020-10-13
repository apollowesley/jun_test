<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>
package ${basepackage}.controller.module.${modulename};

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schrodinger.zbdp.controller.framework.ZbdpBaseController;
import com.schrodinger.zbdp.dao.page.ZPage;
import com.schrodinger.zbdp.util.date.DateTimeUtils;
import com.schrodinger.zbdp.model.framework.BaseDTO;

import ${basepackage}.model.module.${modulename}.${className}PO;
import ${basepackage}.service.module.${modulename}.I${className}Service;


@Controller
@RequestMapping("${classNameFirstLower}Controller")
public class ${className}Controller extends ZbdpBaseController {

    @Resource
    private I${className}Service ${classNameFirstLower}ServiceImpl;
    
    private final String JSP_PATH = "/zbdp/module/${classNameFirstLower}/";

    @RequestMapping("to${className}.do")
    public ModelAndView to${className}() {
        ModelAndView mav = super.getDefaultModelAndView();
        mav.setViewName(JSP_PATH + "${classNameFirstLower}");
        return mav;
    }

    @RequestMapping("get${className}Page.do")
    @ResponseBody
    public ZPage<${className}PO> get${className}Page(BaseDTO baseDTO) {
        Gson gson = new GsonBuilder().setDateFormat(
                DateTimeUtils.DATETIME_FORMAT).create();
        ${className}PO ${classNameFirstLower}DTO = gson.fromJson(baseDTO.getParamStr(),
                ${className}PO.class);
        ZPage<${className}PO> page = ${classNameFirstLower}ServiceImpl
                .get${className}Page(${classNameFirstLower}DTO);
        return page;
    }

    @RequestMapping("delete${className}s.do")
    @ResponseBody
    public BaseDTO delete${className}s(BaseDTO baseDTO) {
        String oidStrs = baseDTO.getParamStr();
        List<${className}PO> list = new ArrayList<${className}PO>();
        String[] oidArr = oidStrs.split(";");
        for (String oid : oidArr) {
            if (!StringUtils.isEmpty(oid)) {
                ${className}PO ${classNameFirstLower}PO = new ${className}PO();
                ${classNameFirstLower}PO.setOid(oid);
                list.add(${classNameFirstLower}PO);
            }
        }
        ${classNameFirstLower}ServiceImpl.delete(list);
        return baseDTO;
    }

    @RequestMapping("save${className}.do")
    @ResponseBody
    public ${className}PO save${className}(BaseDTO baseDTO) {
        Gson gson = new GsonBuilder().setDateFormat(
                DateTimeUtils.DATETIME_FORMAT).create();
        ${className}PO ${classNameFirstLower}PO = gson.fromJson(baseDTO.getParamStr(),
                ${className}PO.class);
        ${classNameFirstLower}ServiceImpl.save(${classNameFirstLower}PO);
        return ${classNameFirstLower}PO;
    }

}