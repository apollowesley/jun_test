<#include "macro.include"/>
<#include "java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service.impl;

import ${basepackage}.dao.${className}Repository;
import ${basepackage}.entity.${className};
import ${basepackage}.service.${className}Service;
import ${basepackage}.service.impl.BaseServiceTools;
import com.amez.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

<#include "java_author.include">
@Service
public class ${className}ServiceImpl extends BaseServiceTools<${className}, ${className}Repository, Integer> implements ${className}Service {

    /**
     * 更新一条
     *
     * @param ${classNameLower}
     * @return
     */
    @Override
    public ${className} update(${className} ${classNameLower}) {
        ${className} entity = dao.findOne(${classNameLower}.getId());
        BeanUtil.copyProperties(entity, ${classNameLower}, true);
        // entity.setUpdateTime(new Date());
        return dao.save(entity);
    }

}