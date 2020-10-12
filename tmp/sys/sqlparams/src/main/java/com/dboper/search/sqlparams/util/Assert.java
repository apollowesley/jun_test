package com.dboper.search.sqlparams.util;

public class Assert {
	
	public static void notNull(Object obj,String message){
		isTrue(obj!=null, message);
	}
	
	public static void notEmpty(Object[] arr,String message){
		isTrue(arr!=null && arr.length>0, message);
	}
	
	public static void isEqual(Object s,Object t,String message){
		isTrue(s.equals(t), message);
	}
	
	public static void isLarger(int s,int t,String message){
		isTrue(s>t, message);
	}
	
	public static void isLargerEqual(int s,int t,String message){
		isTrue(s>=t, message);
	}
	
	public static <T> void isInstanceof(Object obj,Class<T> clazz,String message){
		notNull(clazz,"clazz must not be null");
		isTrue(clazz.isInstance(obj), message);
	}
	
	private static void isTrue(boolean isTrue,String message){
		if(!isTrue){
			throw new IllegalArgumentException(message);
		}
	}
}
