package cn.itcast.base;

public class VarargsTest {

	
	
	public static void main(String[] args) {
		
//		Integer[] ins = {1,2,3,4,5};
//		
//		sum(ins);
//		sum();
		sum(1,2,3,4,5);
		
		
	}
	
	/* 可变参数的格式：类型    ...  变量名
	 * 使用：
	 * 	* 在方法体内，形参可变参数，将被当成数组使用
	 *  * 实际参数的个数，将是形参可变参数，在方法体内使用的数组的长度
	 *  * 实际参数是数组，数组将会被打散
	 *  * 总结：可变参数只能放置在方法参数列表的最后一位
	 *  	* 一个方法中能否具有两个可变参数？
	 * 
	 */
	
//	public static void sum(Integer... ins,int s){  // 错误
	public static void sum(int s,Integer... ins){  // m[5]
		
		int sum = 0;
		for(int i = 0 ; i < ins.length ; i++){
			sum = sum + ins[i];
//			sum += ins[i];
		}
		System.out.println(sum);
		
	}
	
	//public static void sum(Integer[] ins){ }
	
}
