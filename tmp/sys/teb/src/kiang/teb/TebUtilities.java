package kiang.teb;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Copyright (c) 2014 by kiang
 *
 * @author kiang
 * @version 0.1-pre
 */
public final class TebUtilities {

    /**
     * write info, not close stream, need outer close
     *
     * @param bw
     * @param info
     * @throws Exception
     */
    public static void print(BufferedWriter bw, String... info) throws Exception {
        for (int i = 0, n = info.length; i < n; i++) {
            bw.write(info[i]);
        }
    }

    /**
     * write info with new line, not close stream, need outer close
     *
     * @param bw
     * @param info
     * @throws Exception
     */
    public static void println(BufferedWriter bw, String... info) throws Exception {
        for (int i = 0, n = info.length; i < n; i++) {
            bw.write(info[i]);
            bw.newLine();
        }
    }

    public static void println(File file, String... info) throws Exception {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        final BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        try {
            println(bw, info);
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public static String[] splitToken(String source, String delimiter) {
        if (source == null) {
            return new String[0];
        }
        if (source.length() == 0) {
            return new String[]{""};
        }
        StringTokenizer tokenizer = new StringTokenizer(source, delimiter == null ? " \t\n\r\f" : delimiter, false);
        int total = tokenizer.countTokens();
        String[] results = new String[total];
        for (int i = 0; i < total; i++) {
            results[i] = tokenizer.nextToken();
        }
        return results;
    }

    public static Properties getProperties() throws Exception {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL psUrl = classLoader.getResource("teb.properties");
        getProperties(properties, psUrl.openStream());
        String psPath = psUrl.toString();
        int psPos = psPath.lastIndexOf("!/");
        if (psPos != -1) {
            psPath = psPath.substring(0, psPos).substring(10);
            properties.setProperty("locate", psPath.substring(0, psPath.lastIndexOf('/')).replace('/', File.separatorChar));
            File psFile = new File(psPath + File.separatorChar + "teb.properties");
            if (psFile.exists()) {
                getProperties(properties, new FileInputStream(psFile));
            }
        } else {
            properties.setProperty("locate", psPath.substring(6, psPath.lastIndexOf('/')).replace('/', File.separatorChar));
        }
        String libPath = classLoader.getResource("teb-location").toString();
        libPath = libPath.substring(0, libPath.lastIndexOf("!/")).substring(10);
        properties.setProperty("library", libPath.substring(0, libPath.lastIndexOf('/')).replace('/', File.separatorChar));
        properties.setProperty("version", System.getProperty("java.version") + 'x' + System.getProperty("sun.arch.data.model"));
        return properties;
    }

    private static void getProperties(Properties ps, InputStream inputStream) throws Exception {
        try {
            ps.load(inputStream);
        } finally {
            inputStream.close();
        }
    }
}
