package ${basePackage}.modules.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

/**
 * [${table.remarks!""}] 
 */
@Data
public class ${table.className} implements Serializable {
    private static final long serialVersionUID = 1L;
    <#list table.columns as column>
       <#if column.columnName == "create_data_time">
       <#elseif column.columnName == "create_data_username">
       <#elseif column.columnName == "update_data_time">
       <#elseif column.columnName == "update_data_username">
       <#else>
    private ${column.possibleType} ${column.fieldNameFirstLower}; // ${column.remarks!} 
        </#if>
   </#list>
}