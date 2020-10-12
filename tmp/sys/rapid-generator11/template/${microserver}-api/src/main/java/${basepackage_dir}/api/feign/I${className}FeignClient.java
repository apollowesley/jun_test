<#include "macro.include"/>
<#include "java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.api.feign;

import ${basepackage}.api.config.${shortname?cap_first}FeignConfiguration;
import ${basepackage}.api.feign.fallback.${className}FeignClientFallbackFactory;
import ${basepackage}.api.properties.SearchFilter;
import ${basepackage}.api.feign.IMicroserviceInstance;
import ${basepackage}.entity.${className};
import com.amez.util.PageVo;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

<#include "java_author.include">
@FeignClient(name = IMicroserviceInstance.${microserver_instance}, configuration = {
        ${shortname?cap_first}FeignConfiguration.class}, fallbackFactory = ${className}FeignClientFallbackFactory.class)
public interface I${className}FeignClient {

        /**
         * 指定ID获取
         * @param id
         * @return
         */
        @RequestLine("GET /${classNameLower}/findById/{id}")
        ${className} findById${className}(@Param("id") Integer id);

        /**
         * 查找分页筛选
         * @return
         */
        @RequestLine("POST /${classNameLower}/findPage")
        PageVo<${className}> findPage${className}(@RequestBody SearchFilter<${className}> searchFilter);

        /**
         * 新增保存
         * @param ${classNameLower}
         * @return
         */
        @RequestLine("POST /${classNameLower}/add")
        ${className} save${className}(@RequestBody ${className} ${classNameLower});

        /**
         * 更新保存
         * @param ${classNameLower}
         * @return
         */
        @RequestLine("POST /${classNameLower}/update")
        ${className} update${className}(@RequestBody ${className} ${classNameLower});

}
