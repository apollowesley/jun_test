package org.openkoala.businesslog.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 日志生成器
 * @author xmfang
 *
 */
public class LogGenerator {



	private static final String METHOD_ELEMENT = "method";
	private static final String METHOD_LOCATION_ELEMENT = "location";
	private static final String DESCRIPTION_ELEMENT = "description";
	
	public String generateLog(MethodInfo methodInfo) throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException {
		DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream input = new FileInputStream("src/test/resources/log-config.xml");//TODO 这里要改成从业务系统jar包中读取配置文件
        Document doc = domBuilder.parse(input);
        Element root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        
        if (nodeList != null) {
            for (int i = 0, size = nodeList.getLength(); i < size; i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                	if (!METHOD_ELEMENT.equals(node.getNodeName())) {
                		continue;
                	}
                	
                	NamedNodeMap namedNodeMap = node.getAttributes();
                	if (!namedNodeMap.getNamedItem(METHOD_LOCATION_ELEMENT).getNodeValue().equals(methodInfo.getMethodLocation())) {
                		continue;
                	}
                	
                    NodeList methodChildrenNodeList = node.getChildNodes();
                    for (int j = 0, theSize = methodChildrenNodeList.getLength(); j < theSize; j ++) {
                    	Node theNode = methodChildrenNodeList.item(j);
                    	if (theNode.getNodeType() == Node.ELEMENT_NODE) {
                    		if (DESCRIPTION_ELEMENT.equals(theNode.getNodeName())) {
                    			LogDescriptionParser descriptionParser = new LogDescriptionParser();
                        		return descriptionParser.parseDescription(theNode.getFirstChild().getNodeValue().trim(), methodInfo.getArgumentValues());
                        	}
                    	}
                    }
                }

            }
        }
        
		return null;
	}
	
}
