package cn.afterturn.http.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

/**
 * 获取配置文件
 *
 * @author by jueyue on 18-8-2.
 */
public class LemurHttpConfig {

    private static Properties props = null;
    private static File configFile = null;
    private static long fileLastModified = 0L;
    private static String CONFIG_FILE_NAME = "lemurhttp.properties";

    private static void init() {
        URL url = LemurHttpConfig.class.getClassLoader().getResource(CONFIG_FILE_NAME);
        configFile = new File(url.getFile());
        fileLastModified = configFile.lastModified();
        props = new Properties();
        load();
    }

    private static void load() {
        try {
            //注意清空,否则没有新值,会使用旧值
            props.clear();
            //获取属性
            props.load(new InputStreamReader(new FileInputStream(configFile), "UTF-8"));
            //获取文件修改时间
            fileLastModified = configFile.lastModified();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getConfig(String key) {
        key = key.trim();
        if ((configFile == null) || (props == null)) {
            init();
        }
        //当检测到文件被修改时重新加载配置文件
        if (configFile.lastModified() > fileLastModified) {
            load();
        }
        String temp = null;
        while (key.contains("${")) {
            temp = key.substring(key.indexOf("${") + 2, key.indexOf("}"));
            key = key.replace("${" + temp + "}", props.getProperty(temp));
        }
        return key;
    }

}
