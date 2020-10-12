<#include "java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dao;

import ${basepackage}.entity.${className};
import org.springframework.stereotype.Repository;

<#include "java_author.include">
@Repository
public interface ${className}Repository extends BaseRepositoryTools<${className}, Integer> {

}