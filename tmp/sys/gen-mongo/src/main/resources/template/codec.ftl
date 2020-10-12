package ${codec.codecPackage};

import org.bson.Document;
import ${codec.klassName};
<#list codec.importList as ip>
import ${ip};
</#list>
import org.springframework.stereotype.Service;
<#assign uncapName>${codec.klassSimpleName?uncap_first}</#assign>

@Service
public class ${codec.klassSimpleName}Codec {

    public Document encoder(${codec.klassSimpleName}  ${uncapName}) {
        return new Document()
        <#list codec.fields as f>
        <#if f_has_next>
        .append("${f}", ${uncapName}.get${f?cap_first}())
        <#else>
        .append("${f}", ${uncapName}.get${f?cap_first}());
        </#if>
        </#list>
    }

    public ${codec.klassSimpleName} decoder(Document doc) {
        ${codec.klassSimpleName}  ${uncapName} = new ${codec.klassSimpleName}();
        ${uncapName}.setId(doc.getObjectId("_id"));
        <#list codec.encoderMetas as cm>
        ${uncapName}.set${cm.name?cap_first}(doc.get${cm.type?cap_first}("${cm.name}"));
        </#list>
        return  ${uncapName};
    }

 }