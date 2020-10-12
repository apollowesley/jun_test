<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#list table.pkColumns as column>
<#assign pkJavaType = column.javaType>
</#list>
package ${basepackage}.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ${className}  extends BaseModel{

<@generateFields/>
}
<#macro generateFields>
<#list table.columns as column>
    /**
     * ${column.columnAlias!}
     */
    private ${column.simpleJavaType} ${column.columnNameLower};
</#list>

</#macro>
