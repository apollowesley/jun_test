package cn.itcast.singleton;

/**
 * 懒汉:第一次访问的时候创建实例
 */
public class SingleTest {
	/**
	 * 单例：一个实例
	 * 分类：懒汉、饿汉
	 * 
	 *  构造私有
	 *  提供公共访问入口
	 *  提供一个私有的遍历，保存当前实例
	 */
	
	
	private static SingleTest demo;
	
	
	private SingleTest(){
		
	}
	
	public static SingleTest getInstance(){
		if(demo == null){
			demo = new SingleTest();
		}
		return demo;
	}

}
