package cn.itcast.singleton;

public class SingleTestDemo {
	
	public static void main(String[] args) {
		
		
		SingleTest test = SingleTest.getInstance();
		SingleTest test2 = SingleTest.getInstance();
		System.out.println(test);
		System.out.println(test2);
		
		
	}

}
