<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shanhu.common.persistence.BaseEntity;
import com.shanhu.common.persistence.UseCase;

import com.shanhu.common.persistence.UseCase;
<#include "/java_imports.include">

/**
 * 
 * @author domainchen E-mail:cytxiamen@163.com
 * @version v1
 * @类说明   <#if table.baseds?? >${table.baseds}库 </#if> ${table.sqlName} 表
 *
 */
@Entity
@Table(name = "${table.sqlName}")
public class ${className} extends BaseEntity {
 
	private static final long serialVersionUID = 1L;
	
	<#list table.columns as column>
	 /**
     * desc:${column.columnAlias!} <br>    
     */	
	  private ${column.javaType} ${column.columnNameLower};
	</#list>
 
	<@generateConstructor className/>
	<@generateJavaColumns/>
	
}

<#macro generateJavaColumns>
	<#list table.columns as column>
	   
		<#if column.columnNameLower!="version"&&column.columnNameLower!="sysUpdateTime"&&column.columnNameLower!="sysUpdateUser"&&column.columnNameLower!="sysCreateTime"&&column.columnNameLower!="sysCreateUser"&&column.columnNameLower!="remarks">
		    <#if column.isPk()>
		    @Id
			@GeneratedValue(strategy = IDENTITY)
			</#if> 
		    @UseCase(desc = "${column.columnAlias!}")
		    @Column(name = "${column.sqlName}", length = ${column.size})
			public ${column.javaType} get${column.columnName}() {
				return this.${column.columnNameLower};
			}
			
			public void set${column.columnName}(${column.javaType} ${column.columnNameLower}) {
				this.${column.columnNameLower} = ${column.columnNameLower};
			}
	  </#if> 	
	</#list>
</#macro>

