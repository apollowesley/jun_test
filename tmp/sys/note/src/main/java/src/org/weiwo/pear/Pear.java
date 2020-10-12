/**
 * 
 * @auther wuwang
 * @createTime 2014-7-7 下午9:49:09
 */
package org.weiwo.pear;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * 
 * @author peaches
 */
public class Pear {
	private static Pear instance = new Pear();;
	private final HashMap<String, String> beanMap;

	private Pear() {
		beanMap = new HashMap<String, String>();
		init();
	}

	public static Pear getInstance() {
		return instance;
	}

	/**
	 * 在单例模式下，把XML放在HashMap中
	 * 
	 * @createTime 2014-7-7 下午9:57:16
	 */
	private void init() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(this.getClass().getClassLoader().getResource("").getPath() + "pear.xml");
			NodeList nodeList = document.getElementsByTagName("bean");
			Element element = null;
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (Element) nodeList.item(i);
				beanMap.put(element.getAttribute("name"), element.getAttribute("value"));
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据bean的名称获取bean类名
	 * 
	 * @param name
	 * @return
	 * @createTime 2014-7-7 下午9:35:43
	 */
	public String getBean(String name) {
		return beanMap.get(name);
	}
}
