package cn.itcast.demo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * xml工具类，使用dom4j解析
 * @author <a href="mailto:liangtong@itcast.cn">梁桐</a>
 */
public class XmlUtil {
	
	//保存文件公共变量
	private static File file = null;
	
	//如果文件不存在，需要创建文件，并生成相应的根元素
	static{
		file = new File("books.xml");
		if(!file.exists()){ //如果文件不存在
			try {
				//创建文件
				file.createNewFile();
				
				/*生成根元素books*/
				//创建一个新的文档
				Document document = DocumentHelper.createDocument();
				//添加根元素
				document.addElement("books");
				//保存
				saveXml(document);
				
			} catch (Exception e) {
			}
		}
	}
	/**
	 * 获得document对象
	 * @return
	 * @throws Exception 
	 */
	public static Document getDocument() throws Exception{
		//获得解析器
		SAXReader reader = new SAXReader();
		//解析指定的xml文件
		return reader.read(file);
	}

	/**
	 * 保存数据
	 * @param document
	 * @throws Exception 
	 */
	public static void saveXml(Document document) throws Exception {
		
		//格式化
		OutputFormat format = OutputFormat.createPrettyPrint();
		//获得输出流 --并确定输出文件位置
		XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
		//将document写入到指定文件
		writer.write(document);
		//关闭流
		writer.close();
	}

}
