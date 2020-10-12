package cn.kiwipeach.ws.utils;

import org.junit.Test;

import java.io.File;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2017/11/12
 **/

public class SchemaUtilTest {

    @Test
    public void validateXMLSchema() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File fileXsd = new File(classLoader.getResource("schema-2/email.xsd").getFile());
        File fileXml = new File(classLoader.getResource("schema-2/email.xml").getFile());
        boolean b = SchemaUtil.validateXMLSchema(fileXsd.getPath(), fileXml.getPath());
        System.out.println(b);
    }



}
