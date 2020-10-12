package com.itmuch.gen.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties prop = null;

    private PropertiesUtil() {
    }

    public static Properties loadConfig() throws IOException {
        if (prop == null) {
            prop = new Properties();
            InputStream in = Object.class.getResourceAsStream("/config/config-default.properties");
            prop.load(in);
            return prop;
        } else {
            return prop;
        }

    }

}
