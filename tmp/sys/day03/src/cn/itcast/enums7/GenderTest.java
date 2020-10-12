package cn.itcast.enums7;

public class GenderTest {
	
	
	public static void main(String[] args) {
		
		Gender male = Gender.male;
		Gender female = Gender.female;
		
		//返回名称
//		System.out.println(male);
		
		//java --> xml
		System.out.println(male.name());  //male
		System.out.println(female.ordinal());  // 1
		
		
		//xml --> java
		//通过字符串名称获得枚举对象
		String name = "female";
		Gender g = Gender.valueOf(name);
		System.out.println("### " + g.ordinal());
		
		//通过枚举下标获得枚举对象
		int i = 0;
		Gender[] gs = Gender.values();
		Gender s = gs[i];
		System.out.println("*** " + s.name());
		
		
	}

}
