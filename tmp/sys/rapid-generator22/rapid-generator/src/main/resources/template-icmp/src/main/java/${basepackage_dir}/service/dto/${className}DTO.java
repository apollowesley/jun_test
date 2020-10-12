<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service.dto;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<@getImport/>

<#include "/java_copyright.include">
<#include "/java_imports.include">
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("${className}DTO")
public class ${className}DTO implements Serializable{
    private static final long serialVersionUID = 1L;

<#list table.columns as column>
    @ApiModelProperty("${column.remarks!''}")
    private ${column.javaType?split(".")?last} ${column.columnNameLower};
</#list>
}