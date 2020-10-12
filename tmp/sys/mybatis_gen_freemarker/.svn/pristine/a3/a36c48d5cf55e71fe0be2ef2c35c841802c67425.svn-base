package ${domain.packageName};


public class ${domain.className}  {
<#assign fieldList=domain.fields>
<#-- 生成实体的field -->
<#list fieldList as field>
    /**
     * ${field.comment}.
     */
    private ${field.fieldType} ${field.fieldName};
</#list>

<#list fieldList as field>
    public ${field.fieldType} get${(field.fieldName)?cap_first}() {
        return ${field.fieldName};
    }

    public void set${(field.fieldName)?cap_first}(${field.fieldType} ${field.fieldName}) {
        this.${field.fieldName} = ${field.fieldName};
    }
</#list>
}