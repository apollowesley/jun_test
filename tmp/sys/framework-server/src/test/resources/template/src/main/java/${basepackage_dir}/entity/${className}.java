<#include "/macro.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.entity;

<#include "/java_imports.include">
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

public class ${className} extends BaseEntity {
	
	<#--
	public static final String TABLE_ALIAS = "${table.tableAlias}";
	<#list table.columns as column>
	public static final String ALIAS_${column.constantName} = "${column.columnAlias}";
	</#list>
	-->
	
	<#--
	<#list table.columns as column>
	<#if column.isDateTimeColumn>
	public static final String FORMAT_${column.constantName} = DATE_FORMAT;
	</#if>
	</#list>
	-->
	
	<#list table.columns as column>
	${column.hibernateValidatorExprssion}
	private ${column.javaType} ${column.columnNameLower};
	</#list>

	<@generateConstructor className/>

	<#--
	<@generateObjectFunctions/>
	-->
	
	<@generateJavaColumns/>

	<#--
	<@generateJavaOneToMany/>
	<@generateJavaManyToOne/>
	-->
}