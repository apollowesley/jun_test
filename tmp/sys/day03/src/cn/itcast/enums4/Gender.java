package cn.itcast.enums4;

public enum Gender {
	male,female;
	
	/* 枚举也是一个类
	 *  枚举的实例对象，默认 public static final
	 *  枚举构造方法，默认私有
	 *  枚举的实例对象的变量名，必须放在所有内容之前
	 */
	
	
	/* 功能等价于
	 * 
	 * public static Gender male = new Gender();
	public static Gender female = new Gender();
	
	private Gender(){
	}*/

}
