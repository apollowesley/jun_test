package ivyy.taobao.com.domain.fackjson;

import ivyy.taobao.com.entity.ResultInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *@Author:liangjl
 *@Date:2015-1-5
 *@Version:1.0
 *@Description:http://jilongliang.iteye.com/blog/1924212
 */
public class JackJson2 {
	private static ObjectMapper mapper=new ObjectMapper();
	
	public static void main(String[] args)throws Exception {
		
		/***
		 * ��һ������
		 */
		Map<String,String> about=new HashMap<String, String>();
		about.put("weixin", "YFDSBUYI");
		about.put("weibo", "http://weibo.com/resourcesljl");
		about.put("email", "buyee_hr@126.com");
		about.put("qq", "1302128216");
		about.put("address", "�㶫ʡ�Ƹ����Ƴ�����ݺ·39��");
		
 
		/***
		 * �ڶ�������
		 */
		Map<String,String> user=new HashMap<String, String>();
		user.put("age", "25");
		user.put("sex", "Ůʿ");
		user.put("userName", "С��");
		user.put("taobao", "http://ivyy.taobao.com");
		user.put("address", "�㶫ʡ�Ƹ����Ƴ�����ݺ·39��");
		
		
		Map<Object,Object> listObj=new HashMap<Object, Object>();
		
		
		listObj.put("about", about);
		listObj.put("user", user);
		
		Object obj=listObj;//ת���ɶ���
		
		Map<Object, Object> info = new HashMap <Object, Object>();   
        info.put("info", obj);//json�ĸ��ڵ�  
         
		//String jsonStr=listObj.toString().replaceAll("=", ":");
		
	 	String path="d:/test/json1.txt";
		
		File f=new File(path);
		
		if(!f.exists()){
			f.createNewFile();
			mapper.writeValue(f, listObj);//jackjson����json��ʽд���ļ�
		}
		 
		//String newJson=new Gson().toJson(info);  
		String newJson=mapper.writeValueAsString(info);
	    int start=newJson.indexOf(":")+1;  
	    int end=newJson.lastIndexOf("}");  
	    String jsonsText="["+newJson.substring(start, end)+"]";//��װJackSon֧�ֵĸ�ʽ.   
        
		JsonFactory factory = new JsonFactory();//ʵ��JSON���̶���  
        JsonParser jp = factory.createJsonParser(jsonsText);  
        jp.nextToken();//��һ��JsonToken  
        while (jp.nextToken() == JsonToken.START_OBJECT) {  
        	ResultInfo inf = mapper.readValue(jp, ResultInfo.class);//����  
            System.out.println(inf.getAbout().getAddress());//��ȡAddress�����ֵ  
            System.out.println(inf.getUser().getUserName());//��ȡUser��ֵ  
        }    
		
	}
}
