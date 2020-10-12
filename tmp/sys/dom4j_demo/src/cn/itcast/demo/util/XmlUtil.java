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
 * xml�����࣬ʹ��dom4j����
 * @author <a href="mailto:liangtong@itcast.cn">��ͩ</a>
 */
public class XmlUtil {
	
	//�����ļ���������
	private static File file = null;
	
	//����ļ������ڣ���Ҫ�����ļ�����������Ӧ�ĸ�Ԫ��
	static{
		file = new File("books.xml");
		if(!file.exists()){ //����ļ�������
			try {
				//�����ļ�
				file.createNewFile();
				
				/*���ɸ�Ԫ��books*/
				//����һ���µ��ĵ�
				Document document = DocumentHelper.createDocument();
				//��Ӹ�Ԫ��
				document.addElement("books");
				//����
				saveXml(document);
				
			} catch (Exception e) {
			}
		}
	}
	/**
	 * ���document����
	 * @return
	 * @throws Exception 
	 */
	public static Document getDocument() throws Exception{
		//��ý�����
		SAXReader reader = new SAXReader();
		//����ָ����xml�ļ�
		return reader.read(file);
	}

	/**
	 * ��������
	 * @param document
	 * @throws Exception 
	 */
	public static void saveXml(Document document) throws Exception {
		
		//��ʽ��
		OutputFormat format = OutputFormat.createPrettyPrint();
		//�������� --��ȷ������ļ�λ��
		XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
		//��documentд�뵽ָ���ļ�
		writer.write(document);
		//�ر���
		writer.close();
	}

}
