package com.ly.utils.io.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ly.utils.io.FileUtil;

/**
 * xml创建和读工具
 * 
 * @version 1.1
 */
public class XmlUtil {
	
	private static String path;
	
	/**
	 * 创建空的xml文件
	 * @param xmlPath
	 * @return
	 */
	public static Document create(String xmlPath,String root) {
		Document document = DocumentHelper.createDocument();
		//排版
		OutputFormat format = OutputFormat.createPrettyPrint();
		Element element = DocumentHelper.createElement(root);
		document.add(element);
		try {
			XMLWriter output = new XMLWriter(new FileWriter(xmlPath), format);
			output.write(document);
			output.close();
			path = xmlPath;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return document;
	}
	
	 /** 
     * 根据xml文件路径取得document对象 
     * @param xmlPath 
     * @return 
	 * @throws FileNotFoundException
     * @throws DocumentException 
     */  
    public static Document getXml(String xmlPath){  
    	Document document = null;
		try{
    		File file = FileUtil.getFile(xmlPath,"xml");
    		document = new SAXReader().read(file.getPath());
    		path = file.getPath();
    	}catch(DocumentException e){
    		System.err.println("xml内容错误！");
    		e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        return document;  
    }  
    
    /**
     * 取得src下document对象
     * 
     * @param xmlPath
     * @return
     */
    public static Document getSrcXml(String srcXmlPath){  
    	Document document = null;
		try{
    		File file = FileUtil.getSrcFile(srcXmlPath,"xml");
    		document = new SAXReader().read(file.getPath());
    		path = file.getPath();
    	}catch(DocumentException e){
    		System.err.println("xml内容错误！");
    		e.printStackTrace();
		}
		return document;  
    } 
    
    /**
     * 写入xml文件
     * @param document
     * @param path
     */
    public static void write(Document document,String path){ 
    	// 紧凑的格式  
        // OutputFormat format = OutputFormat.createCompactFormat();  
    	//文档中含有中文,设置编码格式写入的形式.
        OutputFormat format = OutputFormat.createPrettyPrint();// 创建文件输出的时候，自动缩进的格式                    
        format.setEncoding("UTF-8");//设置编码  
        try {
        	XMLWriter writer = new XMLWriter(new FileWriter(path),format);  
			writer.write(document);
			writer.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}  
    }
    
    /**
     * 将字符串转化为XML 
     * @param text
     * @return
     */
    public static Document parseText(String text){
		try {
			return DocumentHelper.parseText(text);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * 将元素转换为字符串
     * @param document
     * @param elementName
     * @return
     */
    public static String parseElement(Document document,String elementName){
        Element root=document.getRootElement();      
		return root.element(elementName).asXML(); 
    }
    
    /**
     * 寻找元素
     * @param root
     * @param name
     * @param attr
     * @return
     */
	public static Element find(Element root,String name,String attr){
		if(attr==null || attr.trim().equals("")){
			return root.element(name);
		}
		@SuppressWarnings("unchecked")
		List<Element> elements = root.elements();
		if(elements == null || elements.size()==0){
			return null;
		}
		Map<String, String> map = getAttr(attr);
		Set<String> keySet = map.keySet();
    	for (Element find : elements) {
    		int i=1;
	    	for (String key : keySet){
	    		Attribute attribute = find.attribute(key);
				if(attribute == null || !attribute.getText().equals(map.get(key))){
	    			break;
				}else if(i==map.size()){
	    			return find;
	    		}
				i++;
			}
	    	Element find2 = find(find,name,attr);
	    	if(find2 != null){
	    		return find2;
	    	}
    	}
		return null;
    }
	
	/**
	 * 处理属性
	 * @param attr
	 * @return
	 */
	public static Map<String,String> getAttr(String attr){
		Map<String,String> map = new HashMap<String,String>();
		attr = attr.replace(" ","");
    	String[] attrs = attr.split(",");
    	for (String att : attrs) {
    		map.put(att.split("=")[0],att.split("=")[1]);
		}
		return map;
	}
	
	/**
	 * 获取路径
	 * @return
	 */
	public static String getPath(){
		return path;
	}
}

