<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service.bo;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<@getImport/>
import javax.validation.constraints.NotNull;

<#include "/java_copyright.include">
<#include "/java_imports.include">
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("${className}UpdateBO")
public class ${className}UpdateBO implements Serializable{
    private static final long serialVersionUID = 1L;

<#list table.columns as column>
    @ApiModelProperty("${column.remarks!''}")
    <#if column.javaType?split(".")?last == "Date">
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    <#if column.columnNameLower == "id">
    @NotNull(message = "唯一键不能为空")
    </#if>
    private ${column.javaType?split(".")?last} ${column.columnNameLower};
</#list>

}