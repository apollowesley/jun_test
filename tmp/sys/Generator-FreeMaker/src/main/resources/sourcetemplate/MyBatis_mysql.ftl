<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${basePackage}.${daoPackage}.${entityCamelName}Mapper" >
    <resultMap id="BaseResultMap" type="${basePackage}.${entityPackage}.${entityCamelName}" >
        <id column="${primaryKey}" property="${primaryProperty}" jdbcType="${primaryKeyType}" />
        <#list columns as col>
        <#assign jdbcType=col.columnType?replace(" UNSIGNED","")>
        <#if jdbcType=="INT">
        <#assign jdbcType="INTEGER">
        <#elseif jdbcType=="DATE">
        <#assign jdbcType="TIMESTAMP"></#if>
        <#if !col.primaryKey>
        <result column="${col.columnName}" property="${col.propertyName}" jdbcType="${jdbcType}" />
        </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List" >
        <#list columns as col> ${col.columnName}<#if col_index lt columns?size-1>,</#if></#list>
    </sql>

    <insert id="insert" parameterType="${basePackage}.${entityPackage}.${entityCamelName}">
        insert into ${tableFullName} (
        <#list columns as col> ${col.columnName}<#if col_index lt columns?size-1>,</#if></#list>
        )
        values(
        <#list columns as col>
            <#assign jdbcType=col.columnType?replace(" UNSIGNED","")>
            <#if jdbcType=="INT">
                <#assign jdbcType="INTEGER">
            <#elseif jdbcType=="DATE">
                <#assign jdbcType="TIMESTAMP">
            </#if>
            ${r'#{'}${col.propertyName}${r',jdbcType='}${jdbcType}${r'}'}<#if col_index lt columns?size-1>,</#if></#list>
        )
    </insert>

    <insert id="insertSelective" parameterType="${basePackage}.${entityPackage}.${entityCamelName}">
        insert into ${tableFullName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list columns as col>
            ${r'<if test="'}${col.propertyName}${r' != null'}<#assign jdbcType=col.columnType?replace(" UNSIGNED","")><#if jdbcType!="DATE"> and ${col.propertyName}${r' !='}''</#if>">
                ${col.columnName},
            ${r'</if>'}
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list columns as col>
                <#assign jdbcType=col.columnType?replace(" UNSIGNED","")>
                <#if jdbcType=="INT">
                    <#assign jdbcType="INTEGER">
                <#elseif jdbcType=="DATE">
                    <#assign jdbcType="TIMESTAMP">
                </#if>
            ${r'<if test="'}${col.propertyName}${r' != null and '}${col.propertyName}${r' !='}''">
                ${r'#{'}${col.propertyName}${r',jdbcType='}${jdbcType}${r'}'},
            ${r'</if>'}
            </#list>
        </trim>
    </insert>

    <insert id="insertList" parameterType="java.util.List">
        insert into ${tableFullName}(
        <include refid="Base_Column_List" />
        )
        values
        <foreach collection="list" item="item" index= "index" separator =",">
            (
            <#list columns as col>
                <#assign jdbcType=col.columnType?replace(" UNSIGNED","")>
                <#if jdbcType=="INT">
                    <#assign jdbcType="INTEGER">
                <#elseif jdbcType=="DATE">
                    <#assign jdbcType="TIMESTAMP">
                </#if>
                ${r'#{item.'}${col.propertyName}${r',jdbcType='}${jdbcType}${r'}'}<#if col_index lt columns?size-1>,</#if>
            </#list>
            )
        </foreach>
    </insert>

    <delete id="delete" parameterType="java.lang.${primaryPropertyType}">
        delete from ${tableFullName}
        where ${primaryKey} = ${r'#{'}${primaryProperty}${r',jdbcType='}${primaryKeyType}${r'}'}
    </delete>

    <delete id="deleteBatch" parameterType="java.util.List">
        delete from ${tableFullName}
        where id in
        <foreach collection="list" index="index" item="item" open="(" separator="," close=")">
            ${r'#{item}'}
        </foreach>
    </delete>

    <update id="update" parameterType="${basePackage}.${entityPackage}.${entityCamelName}">
        update ${tableFullName}
        <trim prefix="set" suffixOverrides="," >
            <#list columns as col>
            <#assign jdbcType=col.columnType?replace(" UNSIGNED","")>
            <#if jdbcType=="INT">
                <#assign jdbcType="INTEGER">
            <#elseif jdbcType=="DATE">
                <#assign jdbcType="TIMESTAMP">
            </#if>
            <#if !col.primaryKey>
            ${r'<if test="'}${col.propertyName}${r' != null and '}${col.propertyName}${r' !='}''">
                ${col.columnName} = ${r'#{'}${col.propertyName}${r',jdbcType='}${jdbcType}${r'}'},
            ${r'</if>'}
            </#if>
            </#list>
        </trim>
        where ${primaryKey} = ${r'#{'}${primaryProperty}${r',jdbcType='}${primaryKeyType}${r'}'}
    </update>

    <select id="selectOne" parameterType="${basePackage}.${entityPackage}.${entityCamelName}" resultMap="BaseResultMap">
        select <include refid="Base_Column_List" />
        from ${tableFullName} where 1=1
        <#list columns as col>
        <#assign jdbcType=col.columnType?replace(" UNSIGNED","")>
        <#if jdbcType=="INT">
            <#assign jdbcType="INTEGER">
        <#elseif jdbcType=="DATE">
            <#assign jdbcType="TIMESTAMP">
        </#if>
        ${r'<if test="'}${col.propertyName}${r' != null and '}${col.propertyName}${r' !='}''">
            AND ${col.columnName} = ${r'#{'}${col.propertyName}${r',jdbcType='}${jdbcType}${r'}'}
        ${r'</if>'}
        </#list>
        limit 1
    </select>

    <select id="selectById" parameterType="java.lang.${primaryPropertyType}">
        select <include refid="Base_Column_List" />
        from ${tableFullName}
        where ${primaryKey} = ${r'#{'}${primaryProperty}${r',jdbcType='}${primaryKeyType}${r'}'}
    </select>

    <select id="listAll" resultMap="BaseResultMap">
        select <include refid="Base_Column_List" /> from ${tableFullName}
    </select>

    <select id="listByEntity" parameterType="${basePackage}.${entityPackage}.${entityCamelName}" resultMap="BaseResultMap">
        select <include refid="Base_Column_List" />
        from ${tableFullName} where 1=1
        <#list columns as col>
        <#assign jdbcType=col.columnType?replace(" UNSIGNED","")>
        <#if jdbcType=="INT">
        <#assign jdbcType="INTEGER">
        <#elseif jdbcType=="DATE">
        <#assign jdbcType="TIMESTAMP">
        </#if>
        ${r'<if test="'}${col.propertyName}${r' != null and '}${col.propertyName}${r' !='}''">
            AND ${col.columnName} = ${r'#{'}${col.propertyName}${r',jdbcType='}${jdbcType}${r'}'}
        ${r'</if>'}
        </#list>
    </select>

</mapper>