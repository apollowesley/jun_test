package cn.itcast.base;

import static java.lang.Math.*;

public class StaticImportTest {
	
	public static void main(String[] args) {
		
		//字段
		double pi = Math.PI;
		
		System.out.println(pi);
		System.out.println(PI);
		
		//方法
		int m = Math.max(10, 20);
		System.out.println(m);
		System.out.println(max(10,30));
		System.out.println(min(10,30));
		
		//所有
		
		
	}

}
