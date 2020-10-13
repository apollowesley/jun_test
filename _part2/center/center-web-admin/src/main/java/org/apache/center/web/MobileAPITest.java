package org.apache.center.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.playframework.util.HttpNetUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MobileAPITest {

	public static void main(String[] args) throws Exception {
		try {
			JSONObject jo = JSON.parseObject(HttpNetUtils.login("http://sso.test.com:8080/sso/loginPost", "userName=admin&password=123"));
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Cookie", "token="+jo.getString("token"));
			headers.put("X-Requested-With", "XMLHttpRequest");
			System.out.println(HttpNetUtils.post("http://sso.test.com:8080/user/add", "userName=admin1&password=123", headers));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
