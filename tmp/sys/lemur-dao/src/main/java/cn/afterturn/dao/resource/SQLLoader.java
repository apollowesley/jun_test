package cn.afterturn.dao.resource;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * SQL 文件加载器,加载XML 配置文件中的SQL
 * @author JueYue
 * @date 2014年12月7日 下午12:18:37
 */
public class SQLLoader {

    // 使用内嵌的(?ms)打开单行和多行模式
    private final static Pattern notes           = Pattern.compile("(?ms)/\\*.*?\\*/|^\\s*//.*?$");

    public void load(String path) {
        Set<String> xmls = new SQLScanUtil().getXml(path);
        for (String xmlPath : xmls) {
            path = getRealPath(xmlPath);
            readSQLFromFile(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(path));

        }
    }

    public void load(String path[]) {
        for (int i = 0; i < path.length; i++) {
            load(path[i]);
        }
    }

    private void readSQLFromFile(InputStream ips) {
        Map<String, String> map = new SQLXmlParse().parse(ips);
        Iterator<String> it = map.keySet().iterator();
        String key, value;
        while (it.hasNext()) {
            key = it.next();
            value = getSqlText(map.get(key));
            SQLResource.put(key, value);
        }
    }

    /**
     * 除去无效字段，去掉注释 不然批量处理可能报错 去除无效的等于
     */
    private static String getSqlText(String sql) {
        // 将注释替换成""
        return notes.matcher(sql).replaceAll("");
    }

    /**
     * 把前面木有的.换成\
     * 
     * @date 2014年1月13日
     * @param path
     * @return
     */
    private String getRealPath(String path) {
        path = path.replaceAll("\\.", "/");
        return path.substring(0, path.lastIndexOf("/")) + "."
               + path.substring(path.lastIndexOf("/") + 1);
    }

}
