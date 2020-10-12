package tom.cocook.ext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tom.cocook.core.CocookException;
import tom.kit.clazz.ReflectUtil;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;

public class ByteCodeFactory {
	
	public static Map<String, Object> BeanToMap(Object bean){
		try {
			Class<?> clazz = createByBean(bean);
			Method m = clazz.getDeclaredMethod(bean.getClass().getSimpleName()+"$javassisttoMap", bean.getClass());
			return (Map<String, Object>) m.invoke(null, bean) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static <T> T mapToBean(Map<String, ?> map, Class<T> _class){
		Class<?> clazz = null;
		String methodName = _class.getSimpleName()+"$javassisttoBean";
		try {
			clazz = Thread.currentThread().getContextClassLoader().loadClass("tom.cocook.ext.JavassistUtil");
			/***  加载后无法重新加载***/
			Method me = clazz.getDeclaredMethod(methodName, Map.class, Object.class);
			return (T) me.invoke(null, map, _class.newInstance());
		} catch (Exception e) {
			 try {
				 //创建方法 返回ctclass, toclass()的时候报错
				 clazz = createByMap(map, _class).toClass();
				Method me = clazz.getDeclaredMethod(methodName, Map.class, Object.class);
				return (T) me.invoke(null, map, _class.newInstance());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "panmg");
		map.put("age", 20);
	}
	

	
	public static CtClass  createByMap(Map<String, ?> map, Class<?> _class) throws Exception{
		ClassPool pool = ClassPool.getDefault();
		CtClass ctclass = pool.get("tom.cocook.ext.JavassistUtil");
		CtMethod ctmethod = null;
		
		String methodName = _class.getSimpleName()+"$javassisttoBean";
		pool.importPackage("java.util");
		pool.importPackage("tom.cocook.ext");
		ctmethod  = new CtMethod(pool.get("java.lang.Object"), methodName, 
				new CtClass[]{pool.get("java.util.Map"),pool.get("java.lang.Object") }, ctclass);
		
		ctmethod.setModifiers(AccessFlag.STATIC);
		
		StringBuffer buffer = new StringBuffer(); 
		buffer.append("{Map map = (Map)$1; \n"); 
		buffer.append(_class.getName()+" obj = ("+_class.getName()+")$2; \n"); 
		Field[] fields = _class.getDeclaredFields();
		buffer.append("Object vv=null; String value=null; \n");
		for(Field f: fields){
			String name = f.getName();
			Class<?> type = f.getType();
			buffer.append("vv = map.get(\""+name+"\"); \n");
			buffer.append("if(vv!=null) {\n ");
			buffer.append("	value = vv.toString(); \n");
			
			String setMethod = ReflectUtil.toSetMethod(name);
			
			// javassist 无法使用 任何的 强制转换, 包括拆箱 装箱之类
			String getMethod = getMethod(type);
			
			buffer.append("	obj."+setMethod+"("+getMethod+"); \n");
			
			buffer.append("}; \n");
		}
		
		buffer.append("return obj;}");
		ctmethod.setBody(buffer.toString());
		ctclass.addMethod(ctmethod);
		//ctclass.writeFile("D:\\eclipse_workspace\\cocook\\webapp\\WEB-INF\\classes\\");
		return ctclass;
	}
	
	public static String getMethod(Class<?> type){
		String method = null;
		if(type == String.class){
			method = "JavassistConverter.getStr(value)";
		}else if(type == Integer.class || type == int.class){
			method = "JavassistConverter.getInt(value)";
		}else if(type == Long.class || type == long.class){
			method = "JavassistConverter.getLong(value)";
		}else if(type == Double.class || type == double.class){
			method = "JavassistConverter.getDouble(value)";
		}else if(type == Float.class || type == float.class){
			method = "JavassistConverter.getFloat(value)";
		}else if(type == Short.class || type == short.class){
			method = "JavassistConverter.getShort(value)";
		}else if(type == Timestamp.class){
			method = "JavassistConverter.getTime(value)";
		}else if(type == Date.class){
			method = "JavassistConverter.getDate(value)";
		}
		if(method == null){
			throw new CocookException("no type match to "+ type);
		}
		return method;
	}
	
	
	public static  Class<?> createByBean(Object bean) throws NotFoundException, CannotCompileException, IOException{
		ClassPool pool = ClassPool.getDefault();
		CtClass ctclass = pool.get("tom.cocook.ext.JavassistUtil");
		String methodName = bean.getClass().getSimpleName()+"$javassisttoMap";
		CtMethod ctmethod = null;
		try{
			ctmethod=  ctclass.getDeclaredMethod(methodName);
			return ctclass.toClass();
		}catch(Exception e){
			ctmethod  = CtNewMethod.make("public static void "+methodName+"(){System.out.println(\"---------\");	}", ctclass);
			ctclass.addMethod(ctmethod);
			return ctclass.toClass();
		}
	}
	

	

}
