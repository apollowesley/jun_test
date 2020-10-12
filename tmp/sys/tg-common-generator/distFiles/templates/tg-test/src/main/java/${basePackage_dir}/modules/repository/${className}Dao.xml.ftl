<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.2//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.modules.repository.${table.className}Dao">
    <!-- 通过id得到一个[${table.remarks!""}] -->
    <select id="get" parameterType="string" resultType="${basePackage}.modules.domain.${table.className}">
        SELECT * FROM ${table.tableName} WHERE id = <#noparse>#</#noparse>{id} LIMIT 1 
    </select>
    
    <!-- 插入数据[${table.remarks!""}] -->
    <insert id="insert" parameterType="${basePackage}.modules.domain.form.${table.className}Form">
        INSERT INTO ${table.tableName} (
            <#list table.columns as column><#if column.columnName == "update_data_time"><#elseif column.columnName == "update_data_username"><#else>${column.columnName}<#if column_has_next>, </#if></#if></#list>
        )
        VALUES(
            <#list table.columns as column><#if column.columnName == "update_data_time"><#elseif column.columnName == "update_data_username"><#else><#noparse>#</#noparse>{${column.fieldNameFirstLower}}<#if column_has_next>, </#if></#if></#list>
        )
    </insert>

    <!-- 根据id删除一个[${table.remarks!""}] -->
    <delete id="delete" parameterType="string">
        DELETE FROM ${table.tableName} WHERE id = <#noparse>#</#noparse>{id}
    </delete>
</mapper>
