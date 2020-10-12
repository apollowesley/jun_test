package cn.itcast.reflect;

public class HelloWorld {
	
	
	public HelloWorld(){
		System.out.println("默认构造方法");
	}

	private HelloWorld(String str,Integer i){
		System.out.println("构造" + str + ":" + i);
	}
	
	public String name = "jack";
	
	private Integer age = 23;
	
	
	public void print(){
		System.out.println("print");
	}
	
	private void print(String str , int i){
		System.out.println(str + ":" + i);
	}
	
	
}
