package com.laycms.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  

public class SignUtils {
	private static Logger logger = LoggerFactory.getLogger(SignUtils.class);
	private static final String API_KEY = "a43a3ba9bc0621e250aaf66b9a9e1f5d";
	//参数名ASCII码从小到大排序（字典序）；如果参数的值为空不参与签名；参数名区分大小写；  key1=value1&key2=value2&key=xxxxx
    public static String getSign(Map<String, Object> params) {
       
    	List<String> keyList = new ArrayList<String>(params.keySet());
    	Collections.sort(keyList);
    
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < keyList.size(); i++) {
        	String key = keyList.get(i);
        	String value = params.get(key)+"";
        	if (StringUtils.isNotEmpty(value)) {
        		if (i==0) {
        			buf.append(key).append("=").append(params.get(key));
        		}else{
        			buf.append("&").append(key).append("=").append(params.get(key));
        		}
				
			}
        }
        buf.append("&key=").append(API_KEY);
        logger.info("sign="+buf.toString());
        return DigestUtils.md5Hex(buf.toString());
    }
   
    public static String getSign(String name,Object value) {
    	Map<String, Object> params = new HashMap<>();
		params.put(name, value);
		return SignUtils.getSign(params);
    	
    }
    public static void main(String[] args) {
    	
		String sign = SignUtils.getSign("memberId","2");
		System.out.println(sign);
		
	}
}
