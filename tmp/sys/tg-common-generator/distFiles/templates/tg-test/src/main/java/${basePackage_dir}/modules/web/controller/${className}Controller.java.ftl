package ${basePackage}.modules.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ${basePackage}.modules.domain.${table.className};
import ${basePackage}.modules.domain.form.${table.className}Form;
import ${basePackage}.modules.domain.query.${table.className}Query;
import ${basePackage}.modules.service.${table.className}Service;

/**
 * [${table.remarks!""}]Controller 
 */
@RestController
@RequestMapping("/m/${table.classNameFirstLower}")
public class ${table.className}Controller extends TgSpringBaseController {
    @Autowired
    private ${table.className}Service ${table.classNameFirstLower}Service;
    
    /**
     * 通过id得到一个[${table.remarks!""}]
     */
    @RequestMapping(value = "/get.app", method = { RequestMethod.GET, RequestMethod.POST })
    public TgResponseBean get(@RequestParam("id") final String id) throws TgBusinessException {
        ${table.className} data = ${table.classNameFirstLower}Service.get(id);
        return TgResponseBean.data(data);
    }

    /**
     * 新增[${table.remarks!""}]
     */
    @PostMapping(value = "/add.gsp")
    @ApiOperation(tags = {"${table.remarks!}"}, value = "新增[${table.remarks!''}]", notes = "新增一个[${table.remarks!''}]", httpMethod = "POST")
    public final TgResponseBean add(final ${table.className}Form form, final BindingResult bindingResult) throws TgBusinessException {
        ${table.classNameFirstLower}Service.add(form);
        return TgResponseBean.yes();
    }

    /**
     * 根据id删除一个[${table.remarks!""}]
     */
    @PostMapping(value = "/delete_{id}.gsp")
    public final TgResponseBean delete(@PathVariable("id") final String id) throws TgBusinessException {
        ${table.classNameFirstLower}Service.delete(id);
        return TgResponseBean.yes();
    }
}