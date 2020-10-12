package org.openkoala.businesslog;

import org.junit.Ignore;
import org.junit.Test;
import org.openkoala.businesslog.config.BusinessLogConfigAdapter;
import org.openkoala.businesslog.impl.BusinessLogXmlConfigDefaultAdapter;

/**
 * User: zjzhai
 * Date: 12/4/13
 * Time: 3:00 PM
 */
public class BusinessLogXmlConfigDefaultAdapterTest {


    @Test
    public void testName() throws Exception {

        String method = "String business.ContractApplication.addInvoice(String,long,long)";

        BusinessLogConfigAdapter adapter = new BusinessLogXmlConfigDefaultAdapter();

        adapter.findConfigByBusinessOperator(method);

        assert "${user}:${ip}:${time!Date}".equals(adapter.getPreTemplate());


        assert "合同添加入项目${project.name}".equals(adapter.getTemplate());


    }

    private void testGetQuerys() {



    }


}