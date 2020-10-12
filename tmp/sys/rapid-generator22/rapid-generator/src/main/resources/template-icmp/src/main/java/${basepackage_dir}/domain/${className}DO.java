<#include "/macro.include"/>
<#assign className = table.className + "DO">
<#assign classNameLower = className?uncap_first + "DO">
package ${basepackage}.domain;
import java.io.Serializable;
import cn.winner.beans.base.BaseQueryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<@getImport/>

<#include "/java_copyright.include">
<#include "/java_imports.include">
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ${className} extends BaseQueryEntity implements Serializable {

private static final long serialVersionUID = 1L;

<#list table.columns as column>
    /**
     *@Fields ${column.underscoreName}:${column.remarks!''}
     */
    private ${column.javaType?split(".")?last} ${column.columnNameLower};
</#list>