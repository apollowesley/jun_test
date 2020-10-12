package test;

import java.util.HashMap;
import java.util.Map;

import common.TestBase;

public class SdkTest extends TestBase{
	
	public static void main(String[] args) {
		// Map作为参数
		Map<String,String> map = new HashMap<String, String>();
		
		String schName = "Jim";
		
		map.put("schName", schName);
		
		String resp = getClient().post("http://localhost/openTest/listApiStudent.do", map);
		
		System.out.println(resp);
	}
	
}
