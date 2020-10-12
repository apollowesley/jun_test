<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#list table.pkColumns as column>
<#assign pkJavaType = column.simpleJavaType>
</#list>
package ${basepackage}.service;
import ${basepackage}.model.${className};

public interface ${className}Service {

    /**
     *  根据ID查询实体
     * @param id 实体ID
     * @return
     */
    ${className} findById(Integer id);

    /**
     *  新增实体
     * @param ${classNameLower}
     * @return
     */
    int insert(${className} ${classNameLower});

    /**
     *  更新实体
     * @param ${classNameLower}
     * @return
     */
    int update(${className} ${classNameLower});

    /**
     *  根据ID删除实体
     * @param id 实体ID
     * @return
     */
    int deleteById(Integer id);

}
