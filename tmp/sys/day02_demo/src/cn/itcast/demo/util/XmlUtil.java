package cn.itcast.demo.util;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * xml工具类
 * @author <a href="mailto:liangtong@itcast.cn">梁桐</a>
 * @see java.lang.String
 */
public class XmlUtil {

	/**
	 * 获得document对象
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument() throws Exception{
		
		//获得解析流
		SAXReader reader = new SAXReader();
		
		return reader.read("books.xml");
	}
	
	
	
}
