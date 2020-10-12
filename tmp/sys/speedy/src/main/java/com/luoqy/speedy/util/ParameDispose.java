package com.luoqy.speedy.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author lumberer
 * 	对所有参数的处理
 */
public class ParameDispose {
	public static void main(String[] args) throws Exception {
		String className="AdminUser";
		//com.basic.entity.AdminUser
		//动态获取实体类
		Class entityClass=Class.forName("com.basic.entity."+className); 
		Object entity=entityClass.newInstance();
		Map<String,Object> maps=ParameDispose.getNameValue(entity);
		
		String mapStr=createLinkString(maps);
		System.out.println(mapStr);
	}

	public static Map<String,String> getPara(HttpServletRequest req){
		try{
			Enumeration params=req.getParameterNames();
			Map<String, String> map = new HashMap<String,String>();
			for(Enumeration<String> e=params;e.hasMoreElements();){
				String thisName=e.nextElement().toString();
				String thisValue=req.getParameter(thisName);
				map.put(thisName,thisValue);
			}
			return map;
		}catch (Exception e){
			return null;
		}
	}
	/** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key).toString();

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
	/**
	 * 获取实体类参数及值
	 * 并返回散列集合
	 * */
	public static Map<String,Object> getNameValue(Object entity) throws NoSuchMethodException, SecurityException, Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		//当前类
		String thisName=entity.getClass().getSimpleName();
		//父类
		String superName=entity.getClass().getSuperclass().getSimpleName();
		//当前类
		Field[] fields = entity.getClass().getDeclaredFields();
		Method[] thisV=entity.getClass().getMethods();
		Method[] superV=entity.getClass().getMethods();
		//如果存在父类
		if(!superName.equals("Object")){
			Field[] fieldss=entity.getClass().getSuperclass().getDeclaredFields();
			Field[] AllName=new Field[fields.length+fieldss.length];
			int len=0;
			//当前类的值
			for (int i = 0;fields.length>i&&i<fieldss.length; i++) {
				AllName[len]=fields[i];
				Method method = thisV[i];
				String name=fields[i].getName();
				Object value=(String)method.getDeclaringClass().getMethod("get" + getMethodName(fields[i].getName())).invoke(entity);
				len++;
				if(null==value){
					value="";
				}
					map.put(name, value);
	
			}
			//父类的值
			for (int i = 0;i<fieldss.length; i++) {
				AllName[len]=fieldss[i];
				Method methods = superV[i];
				String name=fieldss[i].getName();
				Object value=(String)methods.getDeclaringClass().getMethod("get" + getMethodName(fieldss[i].getName())).invoke(entity);
				len++;
				if(null==value){
					value="";
				}
					map.put(name, value);
	
			}
			/*for (Field field1 : AllName) {
				field1.setAccessible(true);
			}*/
			return map;
		}else{
			for (Field field : fields) {
				field.setAccessible(true);
				Method m = (Method) entity.getClass().getMethod("get" + getMethodName(field.getName()));
				String name=field.getName();
                Object value = (String) m.invoke(entity);
                map.put(name, value);
			}
			return map;
		}
	}
	//将首字母转化成大写
	private static String getMethodName(String fildeName) throws Exception{  
        byte[] items = fildeName.getBytes();  
        items[0] = (byte) ((char) items[0] - 'a' + 'A');  
        return new String(items);  
    }  
}
