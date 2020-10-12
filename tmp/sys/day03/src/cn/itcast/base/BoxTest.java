package cn.itcast.base;

public class BoxTest {
	
	public static void main(String[] args) {
		
		// 5.0
		Integer i = 10;  //装箱
		int j = i; 		//拆箱
		
		System.out.println(i + " : " + j);
		
		
		// 1.4
		
		Integer ii = new Integer(20);
		int jj = ii.intValue();
		
		System.out.println(ii + " : "  + jj);
		
		
		Double d = new Double(2.0);
		double b = d.doubleValue();
		
		System.out.println("***********");
		print(3.0f);
		
		/*
		 * 3 int -- 1 int
		 * 3 int -- 注释第一个  print(int i)  --【 short -- int -- long -- float  -- double】  -- 3 double
		 * 3 int -- 注释 1 和 3  -- 2 Integer（自动装箱）
		 * 3 int -- 注释 1、2和3 --抛错
		 * */
		
		
		
	}
	
	public static void print(int i){
		System.out.println("1 int");
	}
	public static void print(Integer i){
		System.out.println("2 Integer");
	}
	public static void print(double i){
		System.out.println("3 double");
	}
	public static void print(Double i){
		System.out.println("4 Double");
	}
	public static void print(float i){
		System.out.println("5 float");
		
		
	}
	
	

}
