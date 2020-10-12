package org.beetl.json.performance;

import org.beetl.json.JsonTool;
import org.beetl.json.util.NoLockStringWriter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestCycle {
	static JsonTool tool = new JsonTool();
	public static void main(String[] args){
		
		System.out.println("test cycle attributes");
		testCycle(100000,false);
		testCycle(1000000,true);
		
	}
	
	public static void testCycle(int max,boolean log){
		
		Org so = new Org();
		Depart dept = new Depart();
		so.setDept(dept);
		dept.setOrg(so);
		
		TimeLog.key1Start();
		for(int i=0;i<max;i++){
			String json = tool.serialize(so);
		}
		TimeLog.key1End();
		
		SerializeConfig mapping = new SerializeConfig();
        TimeLog.key2Start();
		for(int i=0;i<max;i++){
			String json = JSON.toJSONString(so,mapping);
		}
		TimeLog.key2End();

	
		
//		ObjectMapper objectMapper = new ObjectMapper();
//		 TimeLog.key3Start();
//			for(int i=0;i<max;i++){
//				NoLockStringWriter sw = new NoLockStringWriter();
//				try {
//					objectMapper.writeValue(sw, so);
//					String json = sw.toString();
//				
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//			TimeLog.key3End();
//		
		 if(log){
			 TimeLog.display("beetl-json","fastjson","jackson");
		 }else{
			 TimeLog.reset();
		 }
	}

	


}
