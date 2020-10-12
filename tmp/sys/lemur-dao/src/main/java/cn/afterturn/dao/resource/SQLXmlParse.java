package cn.afterturn.dao.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * SQL XML解析 
 * @author JueYue
 * @date 2014年12月7日 下午5:02:08
 */
@SuppressWarnings("unchecked")
public class SQLXmlParse {
    /**
     * 解析文件
     * @param ips
     * @return
     */
    public Map<String, String> parse(InputStream ips) {
        Map<String, String> map = new HashMap<String, String>();
        Document document = null;
        SAXReader saxReader = new SAXReader();
        try {
            document = saxReader.read(ips);
            Element root = document.getRootElement();
            List<Element> sqlElements = (List<Element>) root.selectNodes("//sql");
            for (Element sql : sqlElements) {
                map.put(root.attribute("namespace").getValue() + "."
                        + sql.attribute("key").getValue(), sql.getText());
            }
            return map;
        } catch (DocumentException e) {
            throw new RuntimeException("---------读取sql语句XML文件出错:" + e.getMessage());
        } finally {
            try {
                if (ips != null) {
                    ips.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
