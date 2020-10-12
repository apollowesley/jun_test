<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#list table.pkColumns as column>
<#assign pkJavaType = column.simpleJavaType>
</#list>
package ${basepackage}.service.impl;

import ${basepackage}.service.${className}Service;
import ${basepackage}.model.${className};
import ${basepackage}.mapper.${className}Mapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ${className}ServiceImpl implements ${className}Service{

    @Autowired
    private ${className}Mapper ${classNameLower}Mapper;

    /**
     *  根据ID查询实体
     * @param id 实体ID
     * @return
     */
    @Override
    public ${className} findById(Integer id){
        return ${classNameLower}Mapper.getById(id);
    }

    /**
     *  新增实体
     * @param ${classNameLower}
     * @return
     */
    @Override
    public int insert(${className} ${classNameLower}){
        return ${classNameLower}Mapper.insert(${classNameLower});
     }

    /**
     *  更新实体
     * @param ${classNameLower}
     * @return
     */
    @Override
    public int update(${className} ${classNameLower}){
        return ${classNameLower}Mapper.update(${classNameLower});
    }

    /**
     *  根据ID删除实体
     * @param id 实体ID
     * @return
     */
    @Override
    public int deleteById(Integer id){
        return ${classNameLower}Mapper.deleteById(id);
    }

}
