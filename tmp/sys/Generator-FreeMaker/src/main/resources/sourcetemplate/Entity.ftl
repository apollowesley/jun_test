package ${basePackage}.${entityPackage};

import ${basePackage}.base.BaseEntity;
import java.io.Serializable;
import java.util.Date;

/**
* ${remark!}
*/
<#if module.persistance=="hibernate" || module.persistance=="jpa">
@Entity
@Table(name="${tableFullName!}")
</#if>
public class ${entityCamelName!} extends BaseEntity<${entityCamelName!}> {

	private static final long serialVersionUID = 1L;

	<#if columns??>
	<#list columns as col>
	/**
	 * ${col.remark!}
	 */
	<#assign type=col.propertyType>
	<#assign type=type?replace("java.util.","")>
	<#assign type=type?replace("java.math.","")>
	<#assign defaultValue=col.defaultValue!>
	<#if defaultValue?length gt 0>
		<#if type=="Date">
			<#assign defaultValue="new Date()">
		<#elseif type=="Long">
			<#assign defaultValue=defaultValue+"L">
		<#elseif type=="Double">
			<#assign defaultValue=defaultValue+"d">
		</#if>
	</#if>
	<#if col.nullable==false && (module.persistance=="hibernate" || module.persistance=="jpa")>
		<#if type=="String">
	@NotEmpty(message="${col.remark!}不能为空")
		<#else>
	@NotNull(message="${col.remark!}不能为空")
		</#if>
	</#if>
	<#if "DATETIME"==type ||"TIMESTAMP"==type ||"Date"==type>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8" )
	</#if>
	private ${type!} ${col.propertyName}${(defaultValue?length>0)?string("="+defaultValue,"")};
	</#list>
	<#-- 生成子表属性 -->
	<#if subTables??>
		<#list subTables as sub>
		<#if sub.refType=="OneToOne">
			<#if "DATETIME"==type ||"TIMESTAMP"==type ||"Date"==type>
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8" )
			</#if>
	private ${sub.entityCamelName} ${sub.entityName?uncap_first};
		<#else>
			<#if "DATETIME"==type ||"TIMESTAMP"==type ||"Date"==type>
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8" )
			</#if>
	private List<${sub.entityCamelName}> ${sub.entityName?uncap_first}List;
		</#if>
		</#list>
	</#if>

	<#list columns as col>
	<#assign type=col.propertyType>
	<#assign type=type?replace("java.util.","")>
	<#assign type=type?replace("java.math.","")>
	public void set${col.propertyCamelName}(${type} ${col.propertyName}){
		this.${col.propertyName}=${col.propertyName};
	}
	<#if module.persistance=="hibernate" || module.persistance=="jpa">
	<#if col.primaryKey>
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	</#if>
	@Column(name="${col.columnName}",columnDefinition="${col.columnType}")
	</#if>
	public ${type} get${col.propertyCamelName}(){
		return this.${col.propertyName};
	}

	</#list>
	<#-- 生成子表属性 -->
	<#if subTables??>
		<#list subTables as sub>
		<#if sub.refType=="OneToOne"><#-- 一对一 -->
	public void set${sub.entityCamelName}(${sub.entityCamelName} ${sub.entityName?uncap_first}){
		this.${sub.entityName?uncap_first}=${sub.entityName?uncap_first};
	}
	<#if module.persistance=="hibernate" || module.persistance=="jpa">
	@Transient
	</#if>
	public ${sub.entityCamelName} get${sub.entityCamelName}(){
		return this.${sub.entityName?uncap_first};
	}
		<#else><#-- 一对多 -->
	public void set${sub.entityCamelName}List(List<${sub.entityCamelName}> ${sub.entityName?uncap_first}List){
		this.${sub.entityName?uncap_first}List=${sub.entityName?uncap_first}List;
	}
	<#if module.persistance=="hibernate" || module.persistance=="jpa">
	@Transient
	</#if>
	public List<${sub.entityCamelName}> get${sub.entityCamelName}List(){
		return this.${sub.entityName?uncap_first}List;
	}
		</#if>
		</#list>
	</#if>
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("${entityCamelName!}[");
		<#list columns as col>
		sb.append("${(col_index gt 0)?string(",","")}${col.propertyName}=");
		sb.append(${col.propertyName});
		</#list>
		sb.append("]");
		return sb.toString();
	}
	</#if>

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
