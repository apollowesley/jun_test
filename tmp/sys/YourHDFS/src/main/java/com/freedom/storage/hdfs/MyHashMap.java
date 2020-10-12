package com.freedom.storage.hdfs;

import java.util.HashMap;

public class MyHashMap extends HashMap {
	public MyDateFormat get(Object key) {
		MyDateFormat result = (MyDateFormat) super.get(key);	
			if (null == result) {
				// 之前不存在的，说明是定制的，动态构造
				MyDateFormat newObject = new MyDateFormat((String)key); 
				super.put((String)key, newObject);
				result=newObject;
			}
			return result;
		}
		

	}

