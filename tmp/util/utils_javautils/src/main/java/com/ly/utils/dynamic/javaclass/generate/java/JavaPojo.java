package com.ly.utils.dynamic.javaclass.generate.java;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.ly.utils.dynamic.javaclass.generate.Generate;

/**
 * java实体类生成器
 * 
 * <br>
 * <em style="color:green">
 * 示例1：File a = JavaModel.create("com.A", "String:a","Integer:b=0");
 * <br>
 * 示例2：File b = JavaModel.create("com.B ex A", "com.A:aa=new A()");
 * </em>
 * @version 1.0
 */
public class JavaPojo {
	private static Map<String,Object> map = new HashMap<>();
	private static List<String> names = new ArrayList<>();
	private static List<String> types = new ArrayList<>();
	private static List<String> values = new ArrayList<>();
	private static List<String> imports = new ArrayList<>();
	        
	private static List<String> imports2 = new ArrayList<>(new TreeSet<String>(imports));//去重复
	private static List<String> defaultImports = new ArrayList<>(new TreeSet<String>(types));//去除重复
	
	/**
	 * 生成java文件
	 * 
	 * @param path 
	 * @param str	类型要写全名,不用导包的可以不写。
	 * @return 
	 */
	public static File create(String className,String...strs){
		//继承
		int num = className.indexOf(" ex ");
		String extend = "";
		if(num>0){
			extend = "extends "+className.split(" ex ")[1];
			className = className.substring(0,num);
		}
		
		String pack = className.substring(0,className.lastIndexOf("."));
		className = className.substring(className.lastIndexOf(".")+1);
		
		for (String str : strs) {
			//值
			if(str.indexOf("=") > 0){
				String value = str.split("=")[1];
				values.add(" = "+value);
				str = str.split("=")[0];
			}else{
				values.add("");
			}
			//返回值类型
			String type = str.split(":")[0];
			//通过全名获取import
			if(type.indexOf(".")>0){
				imports.add(type);
				type = type.substring(type.lastIndexOf(".")+1);
			}
			types.add(type);
			
			//名字
			String name = str.split(":")[1];
			names.add(name);
		}

		
		map.put("package", pack);
		map.put("className", className);
		map.put("extends", extend);
		map.put("names", names);
		map.put("types", types);
		map.put("values", values);
		map.put("defaultImports", defaultImports);//不是必须
		map.put("imports", imports2);//不是必须

		return write(pack,className);
	}
	/**
	 * 写入
	 * @param pack
	 * @param className
	 * @return
	 */
	private static File write(String pack, String className){
		try {
			String path = "src/main/java/"+pack.replace(".","/")+"/"+className+".java";
			Generate.exampleOut("java/pojo", map, path);
			return new File(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
