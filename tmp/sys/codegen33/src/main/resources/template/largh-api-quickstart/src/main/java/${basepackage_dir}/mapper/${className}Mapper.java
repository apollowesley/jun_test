<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#list table.pkColumns as column>
<#assign pkJavaType = column.simpleJavaType>
</#list>
package ${basepackage}.mapper;

import ${basepackage}.model.${className};
import org.apache.ibatis.annotations.*;


public interface ${className}Mapper {

    /**
     * 根据ID查询${className}
     **/
    @Select("select * from ${classNameLower} where id = <#noparse>#{</#noparse>id<#noparse>}</#noparse>")
    @Results({
            <#list table.columns as column>
                @Result(column="${column.sqlName}",property="${column.columnNameLower}"),
            </#list>
    })
    ${className} getById(Integer id);

    /**
     * 创建${className}
     **/
    @Insert("insert into ${classNameLower} (<#list table.columns as column>${column.sqlName},</#list>)" +
            "values " +
            "(<#list table.columns as column> <#noparse>#{</#noparse>${column.columnNameLower}<#noparse>}</#noparse>,</#list>)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(${className} ${classNameLower});


    /**
     * 更新${className}
     **/
    @Update("update ${classNameLower} " +
            "set  <#list table.columns as column>${column.sqlName} = <#noparse>#{</#noparse>${column.columnNameLower}<#noparse>}</#noparse>,</#list>"+
            "where "+
            "id = <#noparse>#{</#noparse>id<#noparse>}</#noparse>"
    )
    int update(${className} ${classNameLower});


    /**
     * 根据ID删除${className}
     **/
    @Select("delete from ${classNameLower} where id = <#noparse>#{</#noparse>id<#noparse>}</#noparse>")
    int deleteById(Integer id);


}