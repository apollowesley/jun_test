package com.itmuch.gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.itmuch.gen.converter.DB2Entity;
import com.itmuch.gen.entity.Config;
import com.itmuch.gen.entity.ConfigDetail;
import com.itmuch.gen.util.PropertiesUtil;
import com.itmuch.gen.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Test1 {
    // 不同环境下结果不同, 不靠谱, 先顶上, 最好用getResource()来做
    public static final String WORKSPACE_PATH = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {
        Properties prop = PropertiesUtil.loadConfig();

        Test1 test1 = new Test1();
        Config config = test1.convertXMLConf2Bean("/config/config-default.xml", prop);

        // 将数据库的列名/类型/注释映射到实体中
        DB2Entity db2Entity = new DB2Entity(prop, config);

        db2Entity.entry(prop, config);
        // db2Entity.convertDB2Entity(prop, config);

    }

    /**
     * 将xml配置文件读取成string, 并解析其中的${}占位符
     * @param path 文件路径
     * @return 字符串形式的xml
     * @throws IOException
     */
    private String readXmlToString(String path, Properties prop) throws IOException {
        InputStream in = Object.class.getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder sb = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\r\n");
        }
        return StringUtil.resolvePlaceHolder(sb.toString(), prop);
    }

    /**
     * 将config-default.xml映射到实体中
     * @return
     * @throws IOException 
     */
    public Config convertXMLConf2Bean(String path, Properties prop) throws IOException {
        String xmlString = this.readXmlToString(path, prop);

        System.out.println(xmlString);
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("config", Config.class);
        xstream.alias("modelConfig", ConfigDetail.class);
        xstream.alias("mapperConfig", ConfigDetail.class);
        xstream.alias("xmlConfig", ConfigDetail.class);

        xstream.useAttributeFor(ConfigDetail.class, "targetPackage");
        xstream.useAttributeFor(ConfigDetail.class, "targetProject");

        return (Config) xstream.fromXML(xmlString);
    }
}
