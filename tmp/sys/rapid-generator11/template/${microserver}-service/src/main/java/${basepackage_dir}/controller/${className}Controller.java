<#include "macro.include"/>
<#include "java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.controller;

import ${basepackage}.entity.${className};
import ${basepackage}.service.${className}Service;
import ${basepackage}.api.properties.SearchFilter;
import com.amez.util.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

<#include "java_author.include">
@RestController
@RefreshScope // 此注解支持配置文件修改刷新功能
@Api(value = "${className}Controller", description = "${className}平台相关api")
@RequestMapping("/${classNameLower}")
public class ${className}Controller extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ${className}Service ${classNameLower}Service;

    @ApiOperation(value = "查看某一条", httpMethod = "GET", notes = "数据库单条数据")
    @GetMapping("/findById/{id}")
    public ${className} findById(@PathVariable Integer id) {
        ${className} findOne = ${classNameLower}Service.findOne(id);
        return findOne;
    }

    @ApiOperation(value = "查看筛选", httpMethod = "POST", notes = "数据库筛选数据")
    @PostMapping("/findPage")
    public Page<${className}> findPage${className}(@RequestBody SearchFilter<${className}> searchFilter) {
        if (searchFilter.entity == null) {
            searchFilter.entity = new ${className}();
        }
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching();
		//.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
		//.withIgnoreCase(true)
		//.withIgnorePaths("focus")
		//.withMatcher("goodsName", ExampleMatcher.GenericPropertyMatchers.startsWith());
        return ${classNameLower}Service.findByPage(Example.of(searchFilter.entity, matcher), searchFilter.pageNo, searchFilter.pageSize, searchFilter.sortType, searchFilter.direction);
    }

    @ApiOperation(value = "插入一条", httpMethod = "POST", notes = "数据库插入数据")
    @PostMapping("/add")
    public ${className} add${className}(@RequestBody @Valid ${className} ${classNameLower}) {
        return ${classNameLower}Service.save(${classNameLower});
    }

    @ApiOperation(value = "更新一条", httpMethod = "POST", notes = "数据库更新数据")
    @PostMapping("/update")
    public ${className} update${className}(@RequestBody @Valid ${className} ${classNameLower}) {
        return ${classNameLower}Service.update(${classNameLower});
    }
}
