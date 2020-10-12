package com.meiriyouke.easycode.test;

import java.util.List;
import java.util.Map;

import com.meiriyouke.easycode.context.EasyCodeContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meiriyouke.easycode.config.*;
import com.meiriyouke.easycode.utils.XmlParser;

/**
 * XmlParser 测试类
 * User: liyd
 * Date: 13-11-20
 * Time: 下午5:26
 */
public class XmlParserTest {

    private static final Logger LOG = LoggerFactory.getLogger(XmlParserTest.class);

    @Test
    public void parseConfigXml() {

        XmlParser.parseConfigXml("easyCode.xml");

        Map<String, String> allConstant = EasyCodeContext.getAllConstant();

        for (Map.Entry<String, String> entry : allConstant.entrySet()) {

            LOG.info("constant[key={},value={}]", entry.getKey(), entry.getValue());
        }

        LOG.info("-------------------------------------");
        Map<String, Table> tableConfig = EasyCodeContext.getAllTable();

        for (Map.Entry<String, Table> entry : tableConfig.entrySet()) {
            LOG.info("table[name={}]", entry.getKey());
            LOG.info("name:{}", entry.getValue().getName());
            List<Column> columns = entry.getValue().getColumns();
            for (Column column : columns) {
                LOG.info("--[name={},type={},comment={}]", column.getName(), column.getDbType(),
                    column.getComment());
            }
        }

        LOG.info("--------------------------------------");

        Map<String, Task> taskConfig = EasyCodeContext.getAllTask();

        for (Map.Entry<String, Task> entry : taskConfig.entrySet()) {
            LOG.info("task[name={}]", entry.getKey());
            Task task = entry.getValue();
            LOG.info("--[name={}]", task.getName());
            Map<String, Property> properties = task.getProperties();

            for (Map.Entry<String, Property> propertyEntry : properties.entrySet()) {
                LOG.info("----[name={},value={}]", propertyEntry.getValue().getName(),
                    propertyEntry.getValue().getValue());
            }
        }

        System.out.println("运行完成");
    }

}
