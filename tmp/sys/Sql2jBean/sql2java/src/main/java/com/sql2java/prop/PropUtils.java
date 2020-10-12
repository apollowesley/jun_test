/**
 * 
 */
package com.sql2java.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author P0007963
 * 
 */
public class PropUtils {
    /** configuration */
    private final Properties config = new Properties();

    public PropUtils(final String dir) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(dir);
            config.load(fin);

        } catch (final IOException e) {

        }
        closeQuietly(fin);
    }

    public String getValue(final String itemName) {
        return config.getProperty(itemName);
    }

    /**
     * 
     * @param resourceName
     * @return
     */
    public static String getResource(String resourceName) {

        resourceName = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + resourceName;
        return resourceName;
    }

    public static void closeQuietly(final FileInputStream input) {

        if (input == null) {
            return;
        }

        try {
            input.close();
        } catch (final IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
