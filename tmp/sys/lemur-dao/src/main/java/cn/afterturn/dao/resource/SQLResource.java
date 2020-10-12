package cn.afterturn.dao.resource;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.springframework.util.StringUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 所有的SQL 资源用来存放 SQL的Cache
 * 
 * @author JueYue 2014年12月7日
 * @version 1.1
 */
public final class SQLResource {

    private static final Configuration        config          = new Configuration();

    private static final StringTemplateLoader loader          = new StringTemplateLoader();

    private static final int                  INDEX_NOT_FOUND = -1;

    static {
        config.setEncoding(Locale.CHINA, "UTF-8");
        config.setTemplateLoader(loader);
    }

    /**
     * 根据类名和方法名获取 动态 SQL
     * 
     * @return SQL
     */
    public static String get() {
        return get(getMethodUrl(), null);
    }
    /**
     * 根据类名和方法名获取 动态 SQL
     * 
     * @return SQL
     */
    public static String get(String key) {
        return get(getMethodUrl(), null);
    }

    /**
     * 根据类名和方法名获取 动态 SQL
     * 
     * @return SQL
     */
    public static String get(Map<String, Object> map) {
        return get(getMethodUrl(), map);
    }

    /**
     * 根据类名和方法名获取 动态 SQL
     * 
     * @return SQL
     */
    public static String get(String key, Map<String, Object> map) {
        try {
            Template template = config.getTemplate(key);
            if (template == null) {
                throw new RuntimeException(key + "---------不存在该Key");
            }
            Writer write = new StringWriter();
            template.process(map, write);
            String sql = write.toString();
            sql = sql.replaceAll("\\n", " ").replaceAll("\\t", " ").replaceAll("\\s{1,}", " ").trim();
            if (StringUtils.endsWithIgnoreCase(sql, "where")) {
                sql = sql.substring(0, sql.length() - 5);
            }
            int index = 0;
            while ((index = indexOfIgnoreCase(sql, "where and", index)) != -1) {
                sql = sql.substring(0, index + 5) + sql.substring(index + 9, sql.length());
            }
            return sql;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    private static int indexOfIgnoreCase(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        int endLimit = (str.length() - searchStr.length()) + 1;
        if (startPos > endLimit) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }
        for (int i = startPos; i < endLimit; i++) {
            if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 根据类名和方法名获取 动态 SQL
     * 
     * @return SQL
     */
    public static void put(String key, String sql) {
        loader.putTemplate(key, sql);
    }

    /**
     * 判断是否含有
     * 
     * @return SQL
     */
    public static boolean containsKey(String key) {
        return loader.findTemplateSource(key) != null;
    }

    /**
     * 返回上一步执行的方法路径
     * 
     * @return
     */
    private static String getMethodUrl() {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        sb.append(stacks[2].getClassName()).append(".").append(stacks[2].getMethodName());
        return sb.toString();
    }

}
