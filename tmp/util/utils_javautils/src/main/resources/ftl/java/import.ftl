<#-- import导入 v1.0
---------------------------------------------
是否必须		  需要的值			注释				类型
	否		defaultImports	类类型，类名		list<String>
	否		imports 		类类型，类名		list<String>
----------------------------------------------
	子ftl；
-->
<#-- 导入包 -->
<#assign importMap={
	"String1":"java.lang.String"
}/>
<#if defaultImports ??>
	<#list defaultImports as defaultImport>
		<#list importMap?keys as key>
			<#if defaultImport = "${key}">
import ${importMap["${key}"]};
			</#if>
		</#list>
	</#list>
</#if>
<#if imports ??>
	<#list imports as import>
import ${import};
	</#list>
</#if>