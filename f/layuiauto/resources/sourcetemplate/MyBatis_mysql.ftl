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
        <#list columns as col>${col.columnName}<#if col_index lt columns?size-1>,</#if></#list>
    </sql>
    
	<!--增加数据-->
    <insert id="insertSelective" parameterType="${basePackage}.${entityPackage}.${entityCamelName}">
        insert into ${tableFullName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        	<#list columns as column>
        		<if test="${column.propertyName} != null">
                	${column.columnName}<#if column_has_next>,</#if>
            	</if>
        	</#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        	<#list columns as column>
        		<if test="${column.propertyName} != null">
        			<#if "${column.columnType}" == "DATE" >${r"#{"}${column.propertyName},jdbcType=TIMESTAMP}<#if column_has_next>,</#if><#else>${r"#{"}${column.propertyName},jdbcType=${column.columnType}}<#if column_has_next>,</#if></#if>
        		</if>
        	</#list>
        </trim>
    </insert>
    
    <!--删除数据-->
    <delete id="deleteByPrimaryKey" parameterType="java.lang.Integer">
        delete from ${tableFullName}
        where ${primaryKey} = ${r"#{"}${primaryProperty},jdbcType=${primaryKeyType}}
    </delete>
    
    <!--改实体有值的数据 -->
    <update id="updateByPrimaryKeySelective" parameterType="${basePackage}.${entityPackage}.${entityCamelName}">
        update ${tableFullName}
        <set>
        	<#list columns as column>
    		<if test="${column.propertyName} != null">
            	<#if "${column.columnType}" == "DATE" >${column.columnName} = ${r"#{"}${column.propertyName},jdbcType=TIMESTAMP}<#if column_has_next>,</#if><#else>${column.columnName} = ${r"#{"}${column.propertyName},jdbcType=${column.columnType}}<#if column_has_next>,</#if></#if>
        	</if>
        	</#list>
        </set>
        where   ${primaryKey} = ${r"#{"}${primaryProperty},jdbcType=${primaryKeyType}}
    </update>
    
    <!--改全量数据 -->
    <update id="updateByPrimaryKey" parameterType="${basePackage}.${entityPackage}.${entityCamelName}">
        update ${tableFullName}
        <set>
        	<#list columns as column>
    		<#if !column.primaryKey>
			<#if "${column.columnType}" == "DATE" >${column.columnName} = ${r"#{"}${column.propertyName},jdbcType=TIMESTAMP}<#if column_has_next>,</#if><#else>${column.columnName} = ${r"#{"}${column.propertyName},jdbcType=${column.columnType}}<#if column_has_next>,</#if></#if>
			</#if>
        	</#list>
        </set>
        where   ${primaryKey} = ${r"#{"}${primaryProperty},jdbcType=${primaryKeyType}}
    </update>
    
    <!--根据主键查询 -->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.Integer" >
	    select 
	    <include refid="Base_Column_List" />
	    from ${tableFullName!}
	     where ${primaryKey} = ${r"#{"}${primaryProperty},jdbcType=${primaryKeyType}}
	</select>    
	
    <!--查询分页 -->
    <select id="selectPageList" resultMap="BaseResultMap" parameterType="pd">
	    select 
	    <include refid="Base_Column_List" />
	    from ${tableFullName}
	    where 1=1 
	    <#list columns as column>
    		<if test="${column.propertyName} != null and ${column.propertyName} != ''   ">
            	and ${column.columnName} = ${r"#{"}${column.propertyName},jdbcType=${column.columnType}}
        	</if>
        </#list>
   </select>

</mapper>