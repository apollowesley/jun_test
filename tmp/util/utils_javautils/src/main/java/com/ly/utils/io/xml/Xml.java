package com.ly.utils.io.xml;

import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * xml增删改查
 * 
 * @version 1.1
 */
public class Xml {
	
	private Document document;
	private String path;
	
	/**
	 * 实例化xml文件
	 * @param xmlPath
	 * @param srcIsTrue
	 */
	public Xml(String xmlPath,Boolean srcIsTrue){
		if(srcIsTrue){
			document = XmlUtil.getSrcXml(xmlPath);
		}else{
			document = XmlUtil.getXml(xmlPath);
		}
		path = XmlUtil.getPath();
	}
	
	/**
	 * 在某个元素中查找元素
	 * @param element
	 * @param name
	 * @param attr
	 * @return
	 */
	public Element find(Element element,String name, String attr){
		return XmlUtil.find(element, name, attr);
	}
	
	/**
	 * 查找元素
	 * @param name
	 * @param attr
	 * @return
	 */
	public Element find(String name, String attr){
		return XmlUtil.find(document.getRootElement(), name, attr);
	}
	
	/**
	 * 删除元素
	 * @param name
	 * @param attr
	 */
	public void deleteElement(String name, String attr){
		Element find = find(name, attr);
		find.getParent().remove(find);
	}
	
	/**
	 * 删除元素属性
	 * @param name		元素名
	 * @param attr		元素属性（查询用）
	 * @param delete	用“,”隔开
	 */
	public void deleteElementAttribute(String name, String attr,String delete){
		Element find = find(name, attr);
		String[] attributes = delete.split(",");
		for (String attribute : attributes) {
			find.remove(find.attribute(attribute));
		}
		find.getParent().remove(find);
	}
	
	/**
	 * 想跟元素添加元素
	 * @param name
	 * @param attr
	 * @param text
	 */
	public void addElement(String name, String attr,String text){
		Element element = document.getRootElement();
		addElement(element , name, attr, text);
	}
	
	/**
	 * 添加元素
	 * @param element
	 * @param name
	 * @param attr
	 * @param text
	 */
	public void addElement(Element element,String name, String attr,String text){
		Element addElement = element.addElement(name);
		if(text != null){
			addElement.setText(text);
		}
		if(attr==null || attr.trim().equals("")){
			return;
		}
		Map<String, String> attrMap = XmlUtil.getAttr(attr);
		Set<String> keySet = attrMap.keySet();
		for (String key : keySet) {
			addElement.addAttribute(key, attrMap.get(key));
		}
	}
	
	/**
	 * 修改元素属性
	 * @param element
	 * @param attr
	 */
	@SuppressWarnings("deprecation")
	public void updateElementAttributes(Element element, String attr){
		Map<String, String> attrMap = XmlUtil.getAttr(attr);
		Set<String> keySet = attrMap.keySet();
		for (String key : keySet) {
			element.setAttributeValue(key, attrMap.get(key));
		}
	}
	
	/**
	 * 写入
	 * 更新xml文件
	 */
	public void write(){
		XmlUtil.write(document, path);
	}
}
