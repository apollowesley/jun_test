package cn.kiwipeach.ws.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

/**
 * Create Date: 2017/11/12
 * Description: Schema工具类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class SchemaUtil {
    private static Logger logger = LoggerFactory.getLogger(SchemaUtil.class);
    public static boolean validateXMLSchema(String xsdPath, String xmlPath){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (Exception e) {
            logger.warn("Schema文件解析异常：{}",e.getMessage());
            return false;
        }
        return true;
    }
}
