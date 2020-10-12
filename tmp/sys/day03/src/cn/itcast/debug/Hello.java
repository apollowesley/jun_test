package cn.itcast.debug;

public class Hello {
	
	
	public static void main(String[] args) {
		
		System.out.println("1");  //断点 debug调试
		
		System.out.println("2");
		
		print(1,1234);
		
		
		System.out.println("3");
		
		System.out.println("4");
		
		
		
		
	}

	private static void print(int i , int j) {
		int n = 789;
		
		int m = 567;
		
		int sum = n + m + i + j + 10;
		
		System.out.println(sum);
		
	}

}
