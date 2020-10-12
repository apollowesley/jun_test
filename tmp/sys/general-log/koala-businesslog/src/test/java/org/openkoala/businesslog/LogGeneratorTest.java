package org.openkoala.businesslog;

import static org.mockito.Mockito.*;

import business.ContractApplication;
import business.Invoice;
import business.Project;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openkoala.businesslog.config.*;
import org.openkoala.businesslog.impl.BusinessLogFreemarkerDefaultRender;
import org.openkoala.businesslog.impl.BusinessLogXmlConfigDefaultAdapter;
import org.openkoala.businesslog.impl.ConsoleBusinessLogExporter;
import org.openkoala.businesslog.utils.ThreadLocalBusinessLogContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.*;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:context.xml"})
public class LogGeneratorTest extends AbstractJUnit4SpringContextTests {

    @Inject
    private ContractApplication contractApplication;

    @Test
    public void test() {
        ThreadLocalBusinessLogContext.put("user", "张三");
        ThreadLocalBusinessLogContext.put("time", new Date());
        ThreadLocalBusinessLogContext.put("ip", "202.11.22.33");
        contractApplication.addInvoice("项目XXX", 1l, 2l);

        Map<String, Object> context = ThreadLocalBusinessLogContext.get();

        System.out.println("===========");
        System.out.println(context.get("1"));
        assert "项目XXX".equals(context.get("0"));
        assert new Long(1).equals(context.get("1"));
        assert new Long(2).equals(context.get("2"));
        assert "K-8999".equals(context.get(BusinessLogInterceptor.BUSINESS_METHOD_RETURN_VALUE_KEY));
        assert "张三".equals(context.get("user"));


    }



}
