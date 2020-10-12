package cn.itcast;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *  1、Map的遍历
 *  	data.get(fieldName);
 *  2、 properties文件的读取
 *  	ResourceBundle bundle = ResourceBundle.getBundle("data");
		String className = bundle.getString("className");
 *  3、反射实例化
 *  	Class clazz = Class.forName(className);
		Object obj = clazz.newInstance();
	4、反射字段的获取
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
	5、遍历数组 foreach
		for(Field f : fields){}
	6、反射字段的修改
		f.setAccessible(true);
		f.set(obj, value);
		
 */
public class TestMain {
	
	public static void main(String[] args) throws Exception {
		//资料库
		Map<String,String> data = new HashMap<String,String>();
		data.put("name", "fengjie");
		data.put("age", "18");
		data.put("tel", "911");
		data.put("address", "八大**");
		
		//获得properties文件中key为className的值
		ResourceBundle bundle = ResourceBundle.getBundle("data"); //通过getBundle获得src下properties文件
		//获得值
		String className = bundle.getString("className");
		//获得className字符串的实例对象
		Class clazz = Class.forName(className);
		//获得实例对象
		Object obj = clazz.newInstance();
		
		//自动填充数据  
		autoFire(obj,data);
		
		//输出获得的实例对象
		System.out.println(obj);
		
	}

	/**
	 * 自动填充数据  
	 * @param obj 填充对象
	 * @param data 数据源，资料库
	 */
	private static void autoFire(Object obj, Map<String, String> data) throws Exception {
		//获得填充对象的所有字段，然后将此字段在数据源data中查询，如果有则填充
		// * 获得字节码对象
		Class clazz = obj.getClass();
		// * 获得所有的私有字段
		Field[] fields = clazz.getDeclaredFields();
		// * 遍历所有的字段
		for(Field f : fields){
			//f每一个字段对象
			// * 通过字段名称 在数据源data中获得相应的值
			String fieldName = f.getName();
			String value = data.get(fieldName);
			// * 如果有就填充
			if(value != null){
				//强制设置权限
				f.setAccessible(true);
				// 填充 -- 给填充对象obj的字段填充相应的值
				f.set(obj, value);
			}
		}
		
		
		
		
	}

}
