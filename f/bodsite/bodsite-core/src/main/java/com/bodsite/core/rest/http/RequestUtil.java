package com.bodsite.core.rest.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RequestUtil {

	public static HttpServletRequest getRequest(){
		ServletRequestAttributes holder = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = holder.getRequest();
		return request;
	}
	
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String[]> ms = request.getParameterMap();
		Map<String, String> result = new HashMap<String, String>();
		if (ms == null || ms.size() == 0)
			return result;
		for (Map.Entry<String, String[]> m : ms.entrySet()) {
			result.put(m.getKey(), m.getValue()[0]);
			if(m.getValue().length > 1){
				result.put(m.getKey(), join(m.getValue()));
			}
		}
		return result;
	}
	
	public static Map<String,String> getInputStreamMap(HttpServletRequest request) throws SAXException, IOException, ParserConfigurationException{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(request.getInputStream());
        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, String> map = new HashMap<String, String>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;
	}
	
	private static String join(String[] ss){
		StringBuilder sb = new StringBuilder();
		for(String s:ss){
			sb.append(s);
			sb.append(',');
		}
		return sb.substring(0, sb.length()-1);
	}
	
	//当参数是多个数组时，组成maplist
	public static List<Map<String, String>> getParameterMapList(HttpServletRequest request, String primaryKey) {
		Map<String, String[]> ms = request.getParameterMap();
		
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		
		String[] values = ms.get(primaryKey);
		for(String val : values){
			Map<String, String> map = new HashMap<String, String>();
			map.put(primaryKey, val);
			maplist.add(map);
		}
		
		for (Map.Entry<String, String[]> m : ms.entrySet()) {
			String name = m.getKey();
			String[] val = m.getValue();
			
			for(int i=0;i<maplist.size();i++){
				Map<String, String> map = maplist.get(i);
				map.put(name, val[i]);
			}
		}
		return maplist;
	}
}
