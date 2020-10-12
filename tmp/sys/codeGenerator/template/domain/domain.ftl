package ${pakageName}.domain;

import  ${pakageName}.BaseDomain;
<#if importClass?exists>
    <#list  importClass?keys as key>
import  ${importClass[key]};
    </#list>
</#if>

    /**
     *@author liuyonghui
     *
     */
public class ${className} extends BaseDomain{
    <#list  attrs as attr>
      /** ${attr.comment} **/
    private ${attr.type} ${attr.name};
    </#list>

    <#list attrs as attr>
    public void set${attr.name?cap_first}(${attr.type} ${attr.name}){
        this.${attr.name} = ${attr.name};
    }
    public ${attr.type} get${attr.name?cap_first}(){
        return this.${attr.name};
    }

    </#list>

}