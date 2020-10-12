<#include "macro.include"/>
<#include "java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.api.feign;

import ${basepackage}.api.properties.SearchFilter;
import ${basepackage}.entity.${className};
import com.amez.util.FeignClientBuilderUtil;
import com.amez.util.JsonUtils;
import com.amez.util.PageVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

<#include "java_author.include">
@RunWith(JUnit4.class)
@SpringBootTest
public class I${className}FeignClientTest {
    private String hostUrl = null;
    private I${className}FeignClient ${className}FeignClient = null;
    private static Logger logger = LoggerFactory.getLogger(I${className}FeignClientTest.class);

    @Before
    public void setup() {
        hostUrl = "http://127.0.0.1:${server_port}";
        ${className}FeignClient = FeignClientBuilderUtil.createClient(I${className}FeignClient.class, hostUrl);
    }

    //单条测试
    @Test
    public void findById${className}() {
        ${className} ${className} = ${className}FeignClient.findById${className}(1);
        logger.info("${className} = {}", JsonUtils.obj2json(${className}));
        Assert.assertEquals(new Integer(1), ${className}.getId());
    }

    //多条测试
    @Test
    public void findPage${className}() {
        SearchFilter<${className}> ${className}SearchFilter = new SearchFilter<>();
        ${className}SearchFilter.entity = new ${className}();
        PageVo<${className}> ${className}PageVo = ${className}FeignClient.findPage${className}(${className}SearchFilter);
        logger.info("${className}PageVo = {}", ${className}PageVo.getTotalElements());
        Assert.assertTrue(${className}PageVo.getTotalElements() > 0);
    }

    @Test
    public void save${className}() {
    }

    @Test
    public void update${className}() {
    }
}
