package com.qxzl.util;

import java.beans.BeanInfo;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.qxzl.util.string.StringUtils;

/**
 * @ClassName ObjectHelper
 * @Description 对象处理
 */
public class ObjectHelper {
	

	/**
	 * <p>说明： 判断两个对象是否相等 </p>
	 * @param object1
	 * @param object2
	 * @return boolean
	 */
	public static boolean isEquals(Object object1, Object object2) {
		boolean ret = false;
		try {
			if (object1 == null && object2 == null) {
				ret = true;
				return ret;
			}
			ret = object1.equals(object2);
		} catch (NullPointerException e) {
			ret = false;
		}
		return ret;

	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 *            -参数对象
	 * @return boolean -true:表示对象为空;false:表示对象为非空 集合：
	 * 		   Collection.isEmpty()
	 *         数组：判断数组每个元素，所有元素都为空，即判定数组为空
	 *         字符串：判断字符串等于"null"，或去除两端""字窜后返回String.isEmpty()的结果 其它类型返回 false
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		}

		if (obj instanceof String) {
			return ((String) obj).equalsIgnoreCase("null")
					| ((String) obj).trim().isEmpty();
		}

		if (obj instanceof StringBuffer) {
			return ((StringBuffer) obj).length() == 0;
		}

		if (obj.getClass().isArray()) {
			try {
				Object[] a = (Object[]) obj;

				boolean b = true;
				for (Object o : a) {
					b = b & ObjectHelper.isEmpty(o);

					if (!b)
						break;
				}

				return b;
			} catch (ClassCastException e) {
			}
		}

		return false;
	}

	/**
	 * 判断对象是否不为空
	 * @param obj
	 * @return boolean
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	

/*	public static boolean isEmailAdressFormat(String email) {
		boolean isExist = false;
		if (isEmpty(email)) {
			return isExist;
		}
		Pattern p = Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
		Matcher m = p.matcher(email);
		boolean b = m.matches();
		if (b) {
			isExist = true;
		}
		return isExist;
	}*/

	/**
	 * 在obj上获取指定属性(expr, 形如 field.attr)的值，filed是obj上的属性，并对应有getField()方法
	 * 
	 * @param obj
	 * @param expr
	 * @return Object
	 */
	public static Object getFieldValue(Object obj, String expr)throws Exception {
		if (obj == null) {
			throw new Exception(expr);
		}

		Object value = null;
		if (ObjectHelper.isEmpty(expr)) {
			value = obj;
		} else {
			if(isFiledName(obj, expr)){
				value = resolveVariableEL(obj, expr);
		}else{
			throw new Exception(expr+":this attribute not exist");
		}
		}

		return value;
	}

	/**
	 * 从objs中获取指定属性(expr,形如
	 * arg.field.attr)的值，arg对应Map中的key,field是key对应的value对象上的属性
	 * ，value上并对应有getField()方法
	 * 
	 * @param obj
	 * @param expr
	 * @return Object
	 */
	public static Object getFieldValue(Map<String, Object> objs, String expr) throws Exception{
		// 查找第一个值对象
		String targetNm = null;
		if (ObjectHelper.isNotEmpty(expr)) {
			int pos = expr.indexOf(".");
			if (pos > -1) {
				targetNm = expr.substring(0, pos);
				expr = expr.substring(pos + 1);
			} else {
				targetNm = expr;
				expr = null;
			}
		}

		Object targetObject = objs.get(targetNm);
		if (targetObject == null) {
			throw new Exception(targetNm);
		}

		Object value = null;
		if (ObjectHelper.isEmpty(expr)) {
			value = targetObject;
		} else {
			value = resolveVariableEL(targetObject, expr);
		}

		return value;
	}

	//2013-06-23 宋军庆
	/**  
	* 获取对象属性，返回一个字符串数组      
	*   
	* @param  o 对象  
	* @return String[] 字符串数组  
	* 
	*/  
	public static String[] getFiledName(Object o)   
	{     
	try    
	{   
	Field[] fields = o.getClass().getDeclaredFields();   
	String[] fieldNames = new String[fields.length];     
	for (int i=0; i < fields.length; i++)   
	{     
	    fieldNames[i] = fields[i].getName();     
	}     
	return fieldNames;   
	} catch (SecurityException e)    
	{   
	e.printStackTrace();   
	}   
	    return null;   
	}  
	
	/**
	 * 功能描述：判断对象是否具有属性
	 * @param o
	 * @param exp
	 * @return
	 */
	public static Boolean isFiledName(Object o,String exp){
		if(o==null){
			return false;
		}
		String[] fildeName= getFiledName(o);
		for(int i=0;i<fildeName.length;i++){
			if(fildeName[i].equals(exp)){
				return true;
			}
		}
		return false;
	}
	
	private static Object resolveVariableEL(Object target, String el) {
		int pos = el.indexOf(".");

		if (pos > -1) {
			String field = el.substring(0, pos);
			el = el.substring(pos + 1);

			Object value = getValue(target, field);
			if (ObjectHelper.isNotEmpty(el)) {
				value = resolveVariableEL(value, el);
			}

			return value;
		} else {
			return getValue(target, el);
		}
	}

	private static Object getValue(Object target, String field) {
		Method m;
		try {
			m = target.getClass().getMethod(
					"get" + StringUtils.capitalize(field));

			return m.invoke(target, new Object[] {});
		} catch (Exception e) {
			return null;
		}
	}
	
	// /**
	// * 判断Map<String,Object>里指定key的value值是否为空
	// *
	// * @param params
	// * @param key
	// * @return boolean -true:表示value为空;false:表示value为非空
	// */
	// public static boolean isEmpty(Map<String, Object> params, String key) {
	// if (isEmpty(params))
	// return true;
	// else {
	// if (params.containsKey(key) && !isEmpty(params.get(key)))
	// return false;
	// return true;
	// }
	// }
	//
	//
	// /**
	// * 使用模型驱动的时候将模型驱动vo进行字符串解码
	// *
	// * @param obj
	// * 待转码的vo
	// * @throws IllegalArgumentException
	// * 参数错误！参数不能为空。。。
	// * @throws IllegalAccessException
	// * 参数错误！
	// * @throws UnsupportedEncodingException
	// * 不支持的编码方式
	// */
	// public static void decodeObject(Object obj)
	// throws IllegalArgumentException, IllegalAccessException,
	// UnsupportedEncodingException {
	// if (StringUtil.isEmpty(obj)) {
	// return;
	// }
	// // 取得该对象里面所有定义的字段,并对每个字段进行转码
	// for (Field field : obj.getClass().getDeclaredFields()) {
	// // 将此对象的 accessible 标志设置为指示的布尔值。(即,当该字段为private时,也可以访问)
	// field.setAccessible(true);
	// // 回指定对象上此 Field 表示的字段的值。(即,取得传入对象中改字段的值)
	// Object fieldObj = field.get(obj);
	// if (!StringUtil.isEmpty(fieldObj)) {
	// // 只有在字段为String类型的时候才有中文乱码,因为如果是其他类型的话,在类型转换的时候就出错了
	// if (field.getType() == String.class) {
	// // 将指定对象变量上此 Field 对象表示的字段设置为指定的新值。(即,将传入的对象里面的这个字段设置为转码后的值)
	// field.set(
	// obj,
	// !fieldObj.toString().trim().isEmpty() ? URLDecoder
	// .decode(fieldObj.toString(),
	// CommonConstant.UTF8) : null);
	// }
	// }
	// }
	// }
	//
	// /**
	// * 字符串解码
	// *
	// * @param str
	// * 待转码的字符串
	// * @throws UnsupportedEncodingException
	// * 不支持的编码方式
	// */
	// public static void decodeString(String str)
	// throws UnsupportedEncodingException {
	// str = URLDecoder.decode(str, CommonConstant.UTF8);
	// }
	//
	// /**
	// * 将指定字符串的后4个字符替换成*
	// *
	// * @param source
	// * @param len
	// * @return
	// */
	// public static String replacementPartString(String source) {
	// return StringUtil.replacementPartString(source, 4, '*');
	// }
	//
	// /**
	// * 将指定字符串的后Len个字符替换成*
	// *
	// * @param source
	// * @param len
	// * @return
	// */
	// public static String replacementPartString(String source, int len) {
	// return StringUtil.replacementPartString(source, len, '*');
	// }
	//
	// /**
	// * 将指定字符串的后Len个字符替换成replaceStr
	// *
	// * @param source
	// * @param len
	// * @param replaceStr
	// * @return
	// */
	// public static String replacementPartString(String source, int len,
	// char replaceStr) {
	// if (StringUtil.isEmpty(source))
	// return "";
	// else {
	// if (source.length() < 4)
	// return source;
	// else {
	// String dest = source.substring(0, source.length() - 4);
	// for (int i = 0; i < len; i++) {
	// dest += replaceStr;
	// }
	// return dest;
	// }
	// }
	//
	// }
	//
	// /**
	// * 用于处理编号（应用与公文等）
	// *
	// */
	// public static String idCode(String profix, String type, String year,
	// String seq, int len) {
	// String idCode = profix + type + year;
	// for (int i = seq.length(); i < len; i++) {
	// seq = "0" + seq;
	// }
	// return idCode + seq;
	// }
	//
	// /**
	// * 将一个空的object 转换成""
	// *
	// * @param obj
	// * @return
	// */
	// public static String toChangeString(Object obj) {
	// if (obj == null) {
	// obj = "";
	// }
	// return String.valueOf(obj);
	// }
	//
	// /**
	// * 将验证码转换成小写
	// *
	// * @param str
	// * @return
	// */
	// public static String validateToLow(Object str) {
	// String validate = "";
	// if (str != null) {
	// validate = str.toString().toLowerCase();
	// }
	// return validate;
	// }
	//
	//
	// /**
	// * 验证传入的字符串是否是数字
	// *
	// * @Title: PartternValidateNm
	// * @Description:
	// * @param @return
	// * @return boolean
	// * @throws
	// */
	// public static boolean PartternValidateNm(String str) {
	// String pattern = "[0-9]*";
	// Pattern p = Pattern.compile(pattern);
	// Matcher m = p.matcher(str);
	// boolean b = m.matches();
	// return b;
	// }
	//
	// /**
	// * 将一个int数组转换成String
	// *
	// * @param count
	// * @return
	// */
	// public static String intsToString(int[] count) {
	// String str = "";
	// for (int value : count) {
	// str = str + String.valueOf(value) + "@";
	// }
	// return str;
	// }

	/**
	 * 将string 转换成 回调函数+名称
	 * 
	 * @param str
	 * @param jsonCallBack
	 * @return String
	 */
	public static String objectToJson(String str, String jsonCallBack) {
		return jsonCallBack + "('" + str + "')";
	}

	/**
	 * 将boolean 转换成 回调函数+名称
	 * 
	 * @param str
	 * @param jsonCallBack
	 * @return String
	 */
	public static String objectToJson(Boolean flag, String jsonCallBack) {
		return jsonCallBack + "(" + flag + ")";
	}


	
	

	/**
	 * 返回对象hash code的16进制字符串。
	 * 
	 * @param obj 输入对象。
	 * @return String 对象hash code的16进制字符串。
	 */
	public static String getIdentityHexString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}
	
	/**
	 * 将一个对象加入到已经存在的对象数组中（尾部）。<br>
	 * 如果对象数组为null，则创建一个新的对象数组。
	 * 
	 * @param array 已经存在的数组 (可以为null)。
	 * @param obj 需要加入数组的对象。
	 * @return Object[] 新的数组(一定不是null)。
	 */
	@SuppressWarnings("rawtypes")
	public static Object[] addObjectToArray(Object[] array, Object obj) {
		Class compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		}
		else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrLength = (array != null ? array.length + 1 : 1);
		Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, array.length);
		}
		newArr[newArrLength-1] = obj;//修改数组下标array.length为newArrLength-1   2013-06-24  宋军庆
		return newArr;
	}
	
	/**
	 * 返回某个值对象的元素个数。<br>
	 * 如果对象类型为集合对象（Collection、Map）时，则返回集合中元素的个数，对象为其他类型时返回1。<br>
	 * 如果表中不存在该对象，则放回0。
	 * @param value 值对象。
	 * @return 对象的元素个数。
	 */
	
	@SuppressWarnings("rawtypes")
	public static int getObjectSize(Object value) {
		// 如果值对象不存在，则返回0
		if (value == null) {
			return 0;
		}
		// 如果值对象的类型为Collection，则返回size
		else if (value instanceof Collection) {
			return ((Collection)value).size();
		}
		// 如果值对象的类型为Map，则返回size
		else if (value instanceof Map) {
			return ((Map)value).size();
		}
		// 否则，返回1
		else {
			return 1;
		}
	}
	
	/**
	 * <p>将对象转换成boolean类型。</p>
	 *
	 * @param source 原始对象。
	 * @return boolean boolean类型值。
	 */
	public static boolean parseBoolean(Object source) {
		if (source != null) {
			String str = source.toString();
			return ( StringUtils.equalsWithIgnoreCase(str, "true") || source.equals("1") );
		}
		
		return false;
	}
	
	/**
	 * <p>将对象转换成byte类型。</p>
	 *
	 * @param source 原始对象。
	 * @return byte byte类型值。
	 */
	public static byte parseByte(Object source) {
		return parseByte(source, (byte)0);
	}

	/**
	 * <p>将对象转换成byte类型。</p>
	 *
	 * @param source 原始对象。
	 * @param defaultValue 缺省值。
	 * @return byte byte类型值。
	 */
	public static byte parseByte(Object source, byte defaultValue) {
		try {
			return Byte.parseByte(source.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * <p>将对象转换成int类型。</p>
	 *
	 * @param source 原始对象。
	 * @return int int类型值。
	 */
	public static int parseInt(Object source) {
		return parseInt(source, 0);
	}

	/**
	 * <p>将对象转换成int类型。</p>
	 *
	 * @param source 原始对象。
	 * @param defaultValue 缺省值。
	 * @return int int类型值。
	 */
	public static int parseInt(Object source, int defaultValue) {
		try {
			return Integer.parseInt(source.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * <p>将对象转换成Integer类型。</p>
	 *
	 * @param source 原始对象。
	 * @return Integer Integer类型值。
	 */
	public static Integer parseInteger(Object source) {
		try {
			return Integer.valueOf(source.toString());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>将对象转换成Long类型。</p>
	 *
	 * @param source 原始对象。
	 * @return Long Long类型值。
	 */
	public static Long parseLong(Object source) {
		try {
			return Long.valueOf(source.toString());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>将对象转换成Float类型。</p>
	 *
	 * @param source 原始对象。
	 * @return Float Float类型值。
	 */
	public static Float parseFloat(Object source) {
		try {
			return Float.valueOf(source.toString());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <p>将对象转换成Double类型。</p>
	 *
	 * @param source 原始对象。
	 * @return Double Double类型值。
	 */
	public static Double parseDouble(Object source) {
		try {
			return Double.valueOf(source.toString());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * <p>将对象转换成Date类型。</p>
	 *
	 * @param source 原始对象。
	 * @param DateFormat 格式对象。
	 * @return Date Date类型值。
	 */
	public static Date parseDate(Object source, DateFormat dataFormat) {
		try {
			return dataFormat.parse(source.toString());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * <p>将对象转换成Date类型。</p>
	 *
	 * @param source 原始对象。
	 * @param DateFormat 格式字符串。
	 * @return Date Date类型值。
	 */
	public static Date parseDate(Object source, String dateFormat) {
		if (!StringUtils.hasLength(dateFormat) ) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		return parseDate(source, new SimpleDateFormat(dateFormat));
	}
	
	/** 
	* 方法: removeDuplicate <br>
	* 描述: 去除list中重复元素. <br>
	* @param list
	* @return
	*/ 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public   static   List  removeDuplicate(List list)   { 
		if(ObjectHelper.isEmpty(list)) return new ArrayList();
		    HashSet h  =   new  HashSet(list); 
		    list.clear(); 
		    list.addAll(h); 
	        return list;	
		
	 }

	/**
	* 方法: isAllFieldEmpty <p>
	* 描述: 判断传入的对象所有的属性是否为空. 20个属性只需要0毫秒<br>
	* 注意:只检测一个超类的所有属性<br>
	* @param bean 传入的对象只能是普通的bean对象.且bean的属性只判断普通类型(如:集合不做判断). <br>
	* @return 所有属性为空返回 true,否则为false,如果传入的对象为空 return true <br>
	* 
	* 
	 */
	public static boolean isAllFieldEmpty(Object bean) {
		//对象为空
		if(ObjectHelper.isEmpty(bean)) return true;
		//属性为空
		return  ObjectHelper.ergodicityAllField(bean);
	} 


	//遍历所有属性
//	class不是方法 class com.rainbow.db.query.yh.OwnerInfoQuery
	private static Boolean ergodicityAllField(Object bean){
	    BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(bean.getClass());
		    //PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		    for(PropertyDescriptor propertyDescriptor:beanInfo.getPropertyDescriptors()){
		    	//System.out.println(propertyDescriptor.getName());
		    	Method method=propertyDescriptor.getReadMethod();
		    	Object obj = method.invoke(bean);
		    	//System.out.println(obj.getClass().getName()+obj);
		    	if(ObjectHelper.isNotEmpty(obj) && !(obj instanceof Class)) 
		    		{
		    			return false;
		    		}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
