<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
package ${basepackage}.model.module.${modulename};

import javax.persistence.Entity;
import javax.persistence.Table;

<#if table.hasDateTimeColumn>
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.schrodinger.zutil.date.DateTimeUtils;
</#if>
import com.schrodinger.zbdp.model.framework.BasePO;

@Entity
@Table(name = "${table.sqlName}")
public class ${className}PO extends BasePO{
	
	private static final long serialVersionUID = 1L;
	<@generateFields/>
	<@generateNotPkProperties/>
		
}

<#macro generateFields>
<#list table.columns as column>
<#if !column.pk>

	<#if column.isDateTimeColumn>
	private Date ${column.columnNameLower};
    <#elseif column.isIntegerColumn>
    private int ${column.columnNameLower};
    <#elseif column.isStringColumn>
    private String ${column.columnNameLower};
    <#else>
	private ${column.javaType} ${column.columnNameLower};
    </#if>
</#if>
</#list>
</#macro>

<#macro generateNotPkProperties>
<#list table.columns as column>
<#if !column.pk>

    <#if column.isDateTimeColumn>
    @DateTimeFormat(pattern=DateTimeUtils.DATETIME_FORMAT)
    @JsonFormat(pattern=DateTimeUtils.DATETIME_FORMAT,timezone = "GMT+8")
    public Date get${column.columnName}() {
        return this.${column.columnNameLower};
    }
    
    public void set${column.columnName}(Date value) {
        this.${column.columnNameLower} = value;
    }
    <#elseif column.isIntegerColumn>
    public int get${column.columnName}() {
        return this.${column.columnNameLower};
    }
    
    public void set${column.columnName}(int value) {
        this.${column.columnNameLower} = value;
    }
    <#elseif column.isStringColumn>
    public String get${column.columnName}() {
        return this.${column.columnNameLower};
    }
    
    public void set${column.columnName}(String value) {
        this.${column.columnNameLower} = value;
    }
    <#else>
    public ${column.javaType} get${column.columnName}() {
        return this.${column.columnNameLower};
    }
    
    public void set${column.columnName}(${column.javaType} value) {
        this.${column.columnNameLower} = value;
    }
    </#if>
</#if>
</#list>
</#macro>