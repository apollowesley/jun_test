package cn.itcast.singleton;

public class SingleTestDemo2 {
	public static void main(String[] args) {
		
		SingleTest2 test1 = SingleTest2.test;
		SingleTest2 test2 =  SingleTest2.test;
		SingleTest2 test3 =  SingleTest2.test2;
		SingleTest2 test4 =  SingleTest2.test2;
		
		
		System.out.println(test1);
		System.out.println(test2);
		System.out.println(test3);
		System.out.println(test4);
		
	}
}
