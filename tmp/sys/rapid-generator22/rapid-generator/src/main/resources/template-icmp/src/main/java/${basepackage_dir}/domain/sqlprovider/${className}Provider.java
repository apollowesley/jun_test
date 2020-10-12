<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.domain.sqlprovider;
import ${basepackage}.domain.${className}DO;
import org.apache.ibatis.jdbc.SQL;
import cn.winner.beans.base.BaseDynaSqlProvider;

<#include "/java_copyright.include">
<#include "/java_imports.include">
public class ${className}Provider extends BaseDynaSqlProvider{

    //alias
    public static final String TABLE_ALIAS = "${table.sqlName}";

    <@Field/>

    /**
     * 获取单个结果集
     */
    public String get(final Long id){
        return new SQL(){{
            SELECT(getField(Fields));
            FROM(TABLE_ALIAS);
            WHERE("l_id=${r"#{"}id${r"}"}");
        }}.toString();
    }

    /**
     * 查询多个结果集
     */
    public String list(final ${className}DO bean){
        return new SQL(){{
            SELECT(getField(Fields));
            FROM(TABLE_ALIAS);
            if (null != bean){
        <#list table.columns as column>
                if(bean.get${column.columnNameLower?cap_first}()!=null){
                    WHERE("${column.underscoreName}=${r"#{"}${column.columnNameLower}${r"}"}");
                }
        </#list>
                if(bean.getSort() != null){
                    ORDER_BY(bean.getSort());
                }
            }

        }}.toString();
    }

    /**
     *  保存单个对象
     */
    public String save(final ${className}DO bean){
        return new SQL() {{
            INSERT_INTO(TABLE_ALIAS);
            <#list table.columns as column>
            <#if column != "id">
            if (bean.get${column.columnNameLower?cap_first}() != null) {
                VALUES("${column.underscoreName}", "${r"#{"}${column.columnNameLower}${r"}"}");
            }
            </#if>
            </#list>
        }}.toString();
    }

    /**
     * 更新对象
     */
    public String update(final ${className}DO bean){
        return new SQL(){{
            UPDATE(TABLE_ALIAS);
            <#list table.columns as column>
            <#if column != "id">
            if (bean.get${column.columnNameLower?cap_first}() != null) {
                SET("${column.underscoreName}=${r"#{"}${column.columnNameLower}${r"}"}");
            }
            </#if>
            </#list>
            WHERE("l_id = ${r"#{"}id${r"}"}");
        }}.toString();
    }


    /**
     * 按条件移除对象
     */
    public String remove(final ${className}DO bean){
        return new SQL(){{
            DELETE_FROM(TABLE_ALIAS);
            <#list table.columns as column>
            if(bean.get${column.columnNameLower?cap_first}()!=null){
                WHERE("${column.underscoreName}=${r"#{"}${column.columnNameLower}${r"}"}");
            }
            </#list>
            }}.toString();
    }

    /**
     * 删除单个对象
     */
    public String delete(final Long id){
        return new SQL(){{
            DELETE_FROM(TABLE_ALIAS);
            WHERE("l_id=${r"#{"}id${r"}"}");
        }}.toString();
    }


}