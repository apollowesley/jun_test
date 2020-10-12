<#include "macro.include"/>
<#include "java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service.impl;

import ${basepackage}.entity.${className};
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;

<#include "java_author.include">
public interface ${className}Service {
    /**
     * 查看某一条
     *
     * @param id
     * @return
     */
    ${className} findOne(Integer id);

    /**
     * 分页查询（含排序功能：实现2）
     *
     * @param of
     * @param pageNo
     * @param pageSize
     * @param sortType
     * @param direction
     * @return
     */
    Page<${className}> findByPage(Example<${className}> of, int pageNo, int pageSize, String sortType, String direction);

    /**
     * 保存一条
     *
     * @param ${classNameLower}
     * @return
     */
    ${className} save(${className} ${classNameLower});

    /**
     * 更新一条
     *
     * @param ${classNameLower}
     * @return
     */
    ${className} update(${className} ${classNameLower});
}