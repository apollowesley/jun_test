package org.beetl.json.performance;

import org.beetl.json.JsonTool;
import org.beetl.json.util.NoLockStringWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestAnnotation {
	static JsonTool tool = new JsonTool();
	public static void main(String[] args){
		
		System.out.println("test ignore attributes");
		testAnnotation(100000,false);
		testAnnotation(10000000,true);
		
	}
	
	public static void testAnnotation(int max,boolean log){
		
		AnnotationModel so = new AnnotationModel();
		
		TimeLog.key1Start();
		String json = null;
		for(int i=0;i<max;i++){
			 json = tool.serialize(so);
		}
		TimeLog.key1End();
		
		SerializeConfig mapping = new SerializeConfig();
        TimeLog.key2Start();
		for(int i=0;i<max;i++){
			 json = JSON.toJSONString(so,mapping);
		}
		TimeLog.key2End();

	
		
		ObjectMapper objectMapper = new ObjectMapper();
		 TimeLog.key3Start();
			for(int i=0;i<max;i++){
				NoLockStringWriter sw = new NoLockStringWriter();
				try {
					objectMapper.writeValue(sw, so);
					 json = sw.toString();
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			TimeLog.key3End();
		
			GsonBuilder builder = new GsonBuilder();
	        Gson gson = builder.create();
	        TimeLog.key4Start();
			for (int i = 0; i < max; i++) {
				 json =gson.toJson(so);
			}
			TimeLog.key4End();

			if (log) {
				TimeLog.display("beetl-json", "fastjson", "jackson","gson");

			} else {
				TimeLog.reset();
			}

	}

	


}
