<#include "macro.include"/>
<#include "java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import io.swagger.annotations.*;
import java.io.Serializable;
import java.util.Date;

<#include "java_author.include">
@Entity
@Table(name = "${table.sqlName}")
@ApiModel(value="${table.sqlName}")
public class ${className} implements Serializable {
	
	private static final long serialVersionUID = 1L;
	<@generateFields/>
	
	/* ---------------- method ----------------*/
	
	<@generateConstructor className/>


	/* ---------------- getter/setter ----------------*/
	<@generatePkProperties/>
	<@generateNotPkProperties/>
}

<#function makeAllWordFirstLetterUpperCase words>
	<#assign temp="">
	<#list words?split("_") as word >
		<#if (word_index>0)>
		<#assign temp = temp+word?cap_first>
		</#if>
	</#list>
	<#return temp>
</#function>

<#macro generateFields>
	<#list table.columns as column>
	<#if column_index == 0>

	/**
	 ${column.columnComment}
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "自增")
	@Column(name = "${column.sqlName}")
	private ${column.javaType} ${column.columnNameLower};
<#else>

	/**
	 ${column.columnComment}
	 */
	<#if !column.nullable?contains("true")>
	@ApiModelProperty(value = "${column.columnComment}", required = true)
	@NotNull
	<#else>
	@ApiModelProperty(value = "${column.columnComment}")
	</#if>
	<#if column.size gte 65535>
	@Column(name = "${column.sqlName}")
	<#else>
	@Column(name = "${column.sqlName}", length = ${column.size})
	</#if>
	private ${column.javaType} ${column.columnNameLower};
	</#if>
	</#list>
	
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable     = foreignKey.sqlTable>
	<#assign fkPojoClass    = makeAllWordFirstLetterUpperCase(fkSqlTable.sqlName)>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	<#if fkPojoClassVar=='corporation'>
	<#assign fkPojoClassVar = 'corp'>
	</#if>
	/*@Column(column = "$\{column.sqlName}")
	private $\{fkPojoClass} $\{fkPojoClassVar};*/
	</#list>
	
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable     = foreignKey.sqlTable>
	<#assign fkPojoClass    = makeAllWordFirstLetterUpperCase(fkSqlTable.sqlName)>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	@Column()
	private List<${fkPojoClass}> ${fkPojoClassVar}List = new ArrayList<${fkPojoClass}>();
	</#list>
</#macro>

<#macro generatePkProperties>
	<#list table.columns as column>
	<#if column.pk>
	
	public ${column.javaType} get${column.columnName}() {
		return ${column.columnNameLower};
	}
	
	public void set${column.columnName}(${column.javaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	</#if>
	</#list>
</#macro>

<#macro generateNotPkProperties>
	<#list table.columns as column>
	<#if column_index gt 0>

	public ${column.javaType} get${column.columnName}() {
		return ${column.columnNameLower};
	}
	
	public void set${column.columnName}(${column.javaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	</#if>
	</#list>
	
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable     = foreignKey.sqlTable>
	<#assign fkPojoClass    = makeAllWordFirstLetterUpperCase(fkSqlTable.sqlName)>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	<#if fkPojoClassVar=='corporation'>
	
	@ManyToOne
	@JoinColumn(name="corp_id")
	public Corporation getCorp() {
		return corp;
	}
	public void setCorp(Corporation corp) {
		this.corp = corp;
	}
	<#else>
	@ManyToOne
	<#list foreignKey.parentColumns?values as fkColumn>
	@JoinColumn(name = "${fkColumn}")
    </#list>
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	</#if>
	</#list>
	
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable     = foreignKey.sqlTable>
	<#assign fkPojoClass    = makeAllWordFirstLetterUpperCase(fkSqlTable.sqlName)>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	@JsonIgnore
	@OneToMany(mappedBy = "${classNameLower}")
	public List<${fkPojoClass}> get${fkPojoClass}List() {
		return ${fkPojoClassVar}List;
	}
	
	public void set${fkPojoClass}List(List<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}List = ${fkPojoClassVar};
	}
	</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkPojoClass    = makeAllWordFirstLetterUpperCase(fkSqlTable.sqlName)>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private Set ${fkPojoClassVar}s = new HashSet(0);
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "${classNameLower}")
	public Set<${fkPojoClass}> get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkPojoClass    = makeAllWordFirstLetterUpperCase(fkSqlTable.sqlName)>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass} ${fkPojoClassVar};
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	<#list foreignKey.parentColumns?values as fkColumn>
	@JoinColumn(name = "${fkColumn}",nullable = false, insertable = false, updatable = false)
    </#list>
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>