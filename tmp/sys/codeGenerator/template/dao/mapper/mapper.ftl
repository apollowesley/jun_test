<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${pakageName}.dao.${className}${daoFileSuffix}" >


    <sql id="Base_Colum_List">
    <#list  attrs as attr>
        <#if attr_index == (attrs?size-1)>
            ${attr.name} as ${attr.name}
            <#else>
            ${attr.name} as ${attr.name},
        </#if>
    </#list>
    </sql>

    <select  id="queryOneById" resultType="${pakageName}.entity.${className}" parameterType="java.lang.String">
        select
        <include refid="Base_Colum_List"></include>
        from ${tableName}
        where
            id =${"#\{id"}${"}"}
    </select>


    <select  id="queryList"  parameterType="java.util.Map">
        select
        <include refid="Base_Colum_List"></include>
        from ${tableName}
        <trim prefix="WHERE" prefixOverrides="AND | OR">
        <#list  attrs as attr>
            <if test="condition.${attr.name}!=null">
                AND  ${attr.name} =${"#\{"}condtion.${attr.name}${"}"}
            </if>
        </#list>
        </trim>
        <if test="orderBy!=null">
            ORDER BY ${"$\{"}orderBy ${"}"}  ${"$\{"}sortBy}${"}"}
        </if>

    </select>

    <insert id="insert" parameterType="${pakageName}.entity.${className}">
        INSERT INTO ${tableName} (
        <#list  attrs as attr>
             ${attr.name}<#if attr_index != (attrs?size-1)>,</#if>
        </#list>
        ) VALUES (
            <#list  attrs as attr>
               ${"#\{"}${attr.name}${"}"}<#if attr_index != (attrs?size-1)>,</#if>
            </#list>
        )
    </insert>

    <update id="update" parameterType="${pakageName}.entity.${className}">
        UPDATE ${tableName}
        <set>
        <#list  attrs as attr>
            <#if  attr.name != "id">
            <if test="${attr.name} != null">
                ${attr.name} =  ${"#\{"}${attr.name}${"}"}<#if attr_index != (attrs?size-1)>,</#if>
             </if>
            </#if>
        </#list>
        </set>
        WHERE
            id =${"#\{id"}${"}"}

    </update>





</mapper>