package cn.itcast.base;

public class GenericTest2<K,V> {
	
	public static void main(String[] args) {
		
		String s = print("");
		Integer t = print(123);
		
		
	}
	
	/*
	 * 自定义泛型：jvm在运行时确定，当前执行方法的实际参数的类型
	 * 格式：<T> 返回类型之前
	 */
	
	public static <T> T print(T t){
		return t;
	}
	
	

}
