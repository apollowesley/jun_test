<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first + "DO">
package ${basepackage}.domain;
import ${basepackage}.domain.sqlprovider.${className}Provider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.util.List;
<@getImport/>
<#include "/java_copyright.include">
<#include "/java_imports.include">
@Mapper
public interface ${className}Mapper {

    @SelectProvider(type = ${className}Provider.class, method = "get")
    @Results(value = {
            <#list table.columns as column>
            @Result(property = "${column.columnNameLower}", column = "${column.sqlName}", jdbcType = JdbcType.${column.jdbcSqlTypeName?upper_case}, javaType = ${column.javaType ? split(".") ? last}.class)<#if column_has_next>, </#if>
            </#list>
            })
    ${className}DO get(Long id);

    @SelectProvider(type = ${className}Provider.class, method = "list")
    @Results(value= {
            <#list table.columns as column>
            @Result(property = "${column.columnNameLower}", column = "${column.sqlName}", jdbcType = JdbcType.${column.jdbcSqlTypeName?upper_case}, javaType = ${column.javaType ? split(".") ? last}.class)<#if column_has_next>, </#if>
            </#list>
            })
    List<${className}DO> list(${className}DO bean);

    @InsertProvider(type = ${className}Provider.class, method = "save")
    @Options(useGeneratedKeys = true)
    Integer save(${className}DO bean);

    @UpdateProvider(type = ${className}Provider.class, method = "update")
    Integer update(${className}DO bean);

    @DeleteProvider(type = ${className}Provider.class, method = "remove")
    Integer remove(${className}DO bean);

    @DeleteProvider(type = ${className}Provider.class, method = "delete")
    Integer delete(Long id);
}
