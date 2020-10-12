<#include "macro.include"/>
<#include "java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.api.feign.fallback;

import ${basepackage}.api.feign.I${className}FeignClient;
import ${basepackage}.api.properties.SearchFilter;
import ${basepackage}.entity.${className};
import com.amez.util.PageVo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

<#include "java_author.include">
@Component
public class ${className?cap_first}FeignClientFallbackFactory implements FallbackFactory<I${className}FeignClient> {
	
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public I${className}FeignClient create(Throwable casue) {
        return new I${className}FeignClient() {

            @Override
            public ${className} findById${className}(Integer id) {
                logger.error("${classNameLower}Id={},触发回退,错误的原因:", id, casue);
                ${className} ${classNameLower} = new ${className}();
                ${classNameLower}.setId(-1);
                return ${classNameLower};
            }

            @Override
            public PageVo<${className}> findPage${className}(SearchFilter<${className}> searchFilter) {
                logger.error("触发回退,错误的原因:", casue);
                return new PageVo<>();
            }

            @Override
            public ${className} save${className}(${className} ${classNameLower}) {
                logger.error("${classNameLower}Id={},触发回退,错误的原因:", ${classNameLower}.getId(), casue);
                ${classNameLower}.setId(-1);
                return ${classNameLower};
            }

            @Override
            public ${className} update${className}(${className} ${classNameLower}) {
                logger.error("${classNameLower}Id={},触发回退,错误的原因:", ${classNameLower}.getId(), casue);
                ${classNameLower}.setId(-1);
                return ${classNameLower};
            }

        };
    }
}