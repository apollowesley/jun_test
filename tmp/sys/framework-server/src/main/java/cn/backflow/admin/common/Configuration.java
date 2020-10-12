package cn.backflow.admin.common;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 系统配置类, 启动时读取classpath下的configuration.properties
 */
public class Configuration {

    private static Properties prop = new Properties();

    public static String APP_HTTPS;

    public static String APP_ENV;
    public static String APP_NAME;
    public static String APP_VERSION;

    public static String DB_SERVER;
    public static String DB_ADMIN;
    public static String DB_BOKE28;
    public static String DB_ROOMS;

    /* upload config */
    public static String default_avatar;
    public static Integer max_img_uplaod_size; // 单位为 byte

    /* email config */
    public static String EMAIL_HOST; // 邮件发送服务器
    public static String EMAIL_ACCOUNT;
    public static String EMAIL_ADDRESS;
    public static String EMAIL_PASSWORD;
    public static String PASSWORD_RESET_URL;
    public static String PASSWORD_RESET_EMAIL_TEMPLATE; // 发送邮件内容

    static {
        try {
            reload();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 供修改了配置文件属性值时调用,无需重启
     */
    public static void reload() throws IllegalAccessException {
        try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream("configuration.properties")) {
            prop.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Field[] fields = Configuration.class.getDeclaredFields();
        // 读取配置中所有与本类中有相同名称的属性名并赋值
        for (Field field : fields) {
            String property_value = prop.getProperty(field.getName().toLowerCase());

            if (field.getType().isAssignableFrom(String.class)) {
                field.set(Configuration.class, property_value);
            } else if (field.getType().isAssignableFrom(Integer.class)) {
                field.set(Configuration.class, Integer.valueOf(property_value));
            } else if (field.getType().equals(int.class)) {
                field.set(Configuration.class, Integer.valueOf(property_value));
            } else if (field.getType().isAssignableFrom(Long.class)) {
                field.set(Configuration.class, Long.valueOf(property_value));
            } else if (field.getType().equals(long.class)) {
                field.setLong(Configuration.class, Long.valueOf(property_value));
            } else if (field.getType().isAssignableFrom(Double.class)) {
                field.set(Configuration.class, Double.valueOf(property_value));
            } else if (field.getType().equals(long.class)) {
                field.setDouble(Configuration.class, Double.valueOf(property_value));
            } else {
                System.out.printf("not supported type [%s]", field.getName());
            }
            System.out.println(field.getName() + " : " + field.get(Configuration.class));
        }
    }

    public static String get(String name) {
        return prop.getProperty(name);
    }
}