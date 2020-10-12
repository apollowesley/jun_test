package site.zhouinfo.file;

import site.zhouinfo.stringutils.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * java配置文件操作
 *
 * @author zhouinfo
 * @Create Date 2016-08-18 22:20
 */
public class PropertiesUtils {


    /**
     * 第一种方法 静态获取固定位置名称的配置文件
     */
    static String propertiesFile = "src/main/resources/ip.properties";

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(propertiesFile));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * 读取静态获取固定位置名称的配置文件属性中相应键的值
     *
     * @param key 主键
     * @return String
     */
    public static String getKeyValue(String key) {
        return properties.getProperty(key);
    }


    /**
     * 更新（或插入）一对properties信息(主键及其键值)
     * 如果该主键已经存在，更新该主键的值；
     * 如果该主键不存在，则插件一对键值。
     * <p/>
     * 保存或更新 位置不定
     *
     * @param keyname  键名
     * @param keyvalue 键值
     */
    public static void writeProperties(String keyname, String keyvalue) {
        try {
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(propertiesFile);
            properties.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            properties.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }

    /**
     * 更新properties文件的键值对
     * 如果该主键已经存在，更新该主键的值；
     * 如果该主键不存在，则插件一对键值。
     * <p/>
     * 保存或更新 位置不定
     *
     * @param keyname  键名
     * @param keyvalue 键值
     */
    public static void updateProperties(String keyname, String keyvalue) {
        try {
            properties.load(new FileInputStream(propertiesFile));
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(propertiesFile);
            properties.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            properties.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }


    /**
     * 第二种方法 获取到properties 这样就可以随便获取到 key value了
     *
     * @param file
     * @return properties
     */
    public static Properties getProperties(String file) throws Exception {
        if (StringUtils.isEmpty(file)) {
            throw new Exception("String file 空异常");
        }
        //方便获取文件
        InputStream is = PropertiesUtils.class.getClassLoader().getResourceAsStream(file);
        Properties p = new Properties();
        try {
            p.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }


    /**
     * 根据主键key读取主键的值value
     *
     * @param filePath 属性文件路径  "log4j.properties"
     *                 相对 E:\\workproject\\utils\\target\\classes\\ 来说
     * @param key      键名
     */
    public static String readValue(String filePath, String key) throws Exception {
        Properties props = getProperties(filePath);
        String value = props.getProperty(key);
        System.out.println(key + "键的值是：" + value);
        return value;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getKeyValue("timer"));
        System.out.println(getKeyValue("t"));
        writeProperties("zhou", "test");
        updateProperties("test", "zhou");
        System.out.println(getKeyValue("zhou"));
        System.out.println(getKeyValue("test"));

        readValue("ip.properties", "timer");
        readValue("", "timer");
    }
}
