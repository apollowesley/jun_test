package org.pan;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Created by Administrator on 2016/6/11.
 */
public class AppConfig {

    public static final String fileName = "appConfig.properties";

    public static final String key_ip = "ip";
    public static final String key_port = "port";
    public static final String key_username = "username";
    public static final String key_password = "password";
    public static final String key_database = "databaseName";

    public static final String default_ip = "127.0.0.1";
    public static final String default_port = "1433";
    public static final String default_username = "sa";
    public static final String default_password = "1";
    public static final String default_database = "onecardTable";

    private static Properties properties = new Properties();

    public void load(){
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        switch (key) {
            case key_ip:
                return properties.getProperty(key, default_ip);
            case key_port:
                return properties.getProperty(key, default_port);
            case key_username:
                return properties.getProperty(key, default_username);
            case key_password:
                return properties.getProperty(key, default_password);
            case key_database:
                return properties.getProperty(key, default_database);
            default:
                return "";
        }
    }

    public void set(String... values) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        try (PrintWriter printWriter = new PrintWriter(file,"utf-8")){
            printWriter.printf("%s=%s",key_ip,values[0]);
            printWriter.println();
            printWriter.printf("%s=%s",key_port,values[1]);
            printWriter.println();
            printWriter.printf("%s=%s",key_username,values[2]);
            printWriter.println();
            printWriter.printf("%s=%s",key_password,values[3]);
            printWriter.println();
            printWriter.printf("%s=%s",key_database,values[4]);
            printWriter.println();
            properties.store(printWriter, "");
            printWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
