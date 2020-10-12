<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.web.vo;
import java.io.Serializable;
import cn.winner.beans.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<@getImport/>

<#include "/java_copyright.include">
<#include "/java_imports.include">
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("${className}VO")
public class ${className}VO extends BaseVO implements Serializable{
private static final long serialVersionUID = 1L;

<#list table.columns as column>
@ApiModelProperty("${column.remarks!''}")
private ${column.javaType?split(".")?last} ${column.columnNameLower};
</#list>

<@generateJavaColumns/>
<@generateToString className/>
}