package com.ly.utils.dynamic.javaclass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.CtField.Initializer;
import javassist.CtMethod;

/**
 * <strong style="font-size:20px;">
 * 		分析、编辑、修改、创建Java字节码工具
 * </strong>
 *  <br>
 *  <em style="color:green">
 *  例1：
 *  <ul>
 *		<li>JavassistUtil.createClass("com.Test");</li>
 *		<li>JavassistUtil.addConstructor("System.out.println(\"hello!\");");</li>
 *		<li>JavassistUtil.toClass().newInstance();</li>
 *  </ul>
 *  </em>
 * @version 1.0
 *  <em style="color:orange;">
 *  @author 注释：
 *  <ul>
 *     <li>$0 $1 $2 	$0代表是this, $1代表方法参数的第一个参数，$2代表方法参数的第二个参数，以此类推，$N代表方法参数的第N个</li>
 *     <li>$args	The type of $args is OBject[]. $args(0)对应的是$1，不是$0</li>
 *     <li>$$	一个方法调用的深度</li>
 *     <li>$r	方法返回值的类型</li>
 *     <li>$_	方法返回值。(修改方法体时不支持)</li>
 *     <li>addCatch()	方法中加入try catch块  $e代表 异常对象</li>
 *     <li>$class	this的类型(Class)。也就是$0的类型</li>
 *     <li>$sig	方法参数的类型(Class)数组，数组的顺序。</li>
 *  </ul>
 * </em>
 */
public class JavassistUtil {

	public static final int PUBLIC = Modifier.PUBLIC;
	public static final int PRIVATE = Modifier.PRIVATE;
	
	private static ClassPool pool;
	private static CtClass cls;

	static {
		pool = ClassPool.getDefault();
	}
	
	/**
	 * 获取连接
	 * @return
	 */
	public static ClassPool getPool(){
		return pool;
	}
	/**
	 * 创建类
	 * @param className		全名
	 */
	public static void createClass(String className) {
		cls = pool.makeClass(className);
	}

	/**
	 * 添加私有成员变量
	 * @param type		类型
	 * @param fieldName	名字
	 * @param value		默认值
	 * @param get		是否添加get方法
	 * @param set		是否添加set方法
	 * @throws CannotCompileException
	 * @throws NotFoundException
	 */
	public static void addPrivateField(String type, String fieldName, String value, boolean get, boolean set)
			throws CannotCompileException, NotFoundException {
		// 添加私有成员name及其getter、setter方法
		CtField param = new CtField(pool.get(type), fieldName, cls);
		param.setModifiers(PRIVATE);
		String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		if (set) {
			cls.addMethod(CtNewMethod.getter("set"+name, param));
		}
		if (get) {
			cls.addMethod(CtNewMethod.getter("get"+name, param));
		}
		cls.addField(param, Initializer.constant(value));
	}
	
	/**
	 * 添加构造器
	 * @param body			方法体
	 * @param type			参数类型
	 * @throws CannotCompileException
	 * @throws NotFoundException
	 */
	public static void addConstructor(String body,String...type) throws CannotCompileException, NotFoundException {
		CtClass[] ctc = new CtClass[type.length];
		for (int i=0;i<type.length;i++) {
			ctc[i] = pool.get(type[i]);
		}
		// 添加有参的构造体
		CtConstructor cons = new CtConstructor(ctc, cls);
		cons.setBody("{"+body+"}");
		cls.addConstructor(cons);
	}
	
	/**
	 * 编译并返回Class对象
	 * @return
	 * @throws Exception
	 */
	public static Class<?> toClass() throws Exception{
		return cls.toClass();
	}
	
	/**
	 * 编译并返回对象
	 * 使用有参构造器
	 * @param paramTypes	有参构造类型
	 * @param args			设置值
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object toObject(String[]paramTypes,Object...args) throws Exception{
		if(paramTypes==null){
			return toObject();
		}
		Class<?>[] classs= new Class[paramTypes.length];
		for(int i=0;i<paramTypes.length;i++){
			classs[i] = Class.forName(paramTypes[i]);
		}
		return cls.toClass().getConstructor(classs).newInstance(args);
	}
	
	/**
	 * 返回该类对象
	 * 使用无参构造器
	 * @return
	 * @throws Exception
	 */
	public static Object toObject() throws Exception{
		return cls.toClass().newInstance();
	}
	
	/**
	 * 把生成的class写入文件  
	 * @param file
	 * @throws IOException
	 * @throws CannotCompileException
	 */
	public static void write(File file) throws IOException, CannotCompileException{
        byte[] byteArr = cls.toBytecode();  
        FileOutputStream fos = new FileOutputStream(file);  
        fos.write(byteArr);  
        fos.close(); 
	}
	
	/**
	 * 添加方法
	 * 
	 * <br>
	 *  <em style="color:green">
	 *  例1：
	 *  <ul>
	 *		<li>JavassistUtil.createClass("com.A");</li>
	 *		<li>JavassistUtil.addMethod(JavassistUtil.PUBLIC,cls.voidType, "a", "System.out.println($1);", pool.get("java.lang.String"));</li>
	 *		<li>Object object = JavassistUtil.toObject();</li>
	 *		<li>ReflectMethod.invoke(object, "a", "方法a运行成功");</li>
	 *  </ul>
	 *  </em>
	 * @param qualifier		修饰符类型
	 * @param type			返回值类型
	 * @param methodName	方法名
	 * @param body			方法体
	 * @param types			参数类型
	 * @throws Exception
	 */
	public static void addMethod(int qualifier,CtClass type,String methodName,String body,CtClass...types) throws Exception{
		 //添加自定义方法  
        CtMethod ctMethod = new CtMethod(type,methodName,types,cls);  
        //为自定义方法设置修饰符  
        ctMethod.setModifiers(qualifier);  
        ctMethod.setBody(body);  
        cls.addMethod(ctMethod);  
	}
}