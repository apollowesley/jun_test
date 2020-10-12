package com.it13.utils.dbutil.DbBeanUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.it13.utils.dbutil.annotation.JavaBeanConfig;
import com.it13.utils.dbutil.annotation.JavaBeanField;
import com.it13.utils.dbutil.annotation.JavaBeanPrimaryKey;
import com.it13.utils.dbutil.annotation.JavaBeanTable;
import com.it13.utils.exception.CommonException;
import com.it13.utils.strutil.StrUtil;

/**
 * 数据库获取到的List<Map>结果集转化为List<JavaBean>工具类
 * @author yzChen
 * @date 2013-8-16 下午3:50:35
 * <pre>
 *	desc:
 * </pre>
 */
public class DbBeanUtils {
	
	/**
	 * 根据List<Map<String, Object>>数据转换为List<JavaBean>数据
	 * @param datas
	 * @param beanClass
	 * @return
	 * @throws CommonException
	 */
	public static <T> List<T> ListMap2JavaBean(List<Map<String, Object>> datas, Class<T> beanClass) throws CommonException  {
		// 返回数据集合
		List<T> list = null;
		// 对象字段名称
		String fieldname = "";
		// 对象方法名称
		String methodname = "";
		// 对象方法需要赋的值
		Object methodsetvalue = "";
		try {
			list = new ArrayList<T>();
			// 得到对象所有字段
			Field fields[] = beanClass.getDeclaredFields();
			// 遍历数据
			for (Map<String, Object> mapdata : datas) {
				// 创建一个泛型类型实例
				T t = beanClass.newInstance();
				// 遍历所有字段，对应配置好的字段并赋值
				for (Field field : fields) {
					// 获取注解配置
					JavaBeanField javaBeanField = field.getAnnotation(JavaBeanField.class);
					if(null != javaBeanField) {	// 有注解配置，下一步操作
						// 全部转化为大写
						String dbfieldname = javaBeanField.value().toUpperCase();
						// 获取字段名称
						fieldname = field.getName();
						// 拼接set方法
						methodname = "set" + StrUtil.capitalize(fieldname);
						// 获取data里的对应值
						methodsetvalue = mapdata.get(dbfieldname);
						// 赋值给字段
						Method m = beanClass.getDeclaredMethod(methodname, field.getType());
						m.invoke(t, methodsetvalue);
					}
				}
				// 存入返回列表
				list.add(t);
			}
		} catch (InstantiationException e) {
			throw new CommonException(e, "创建beanClass实例异常");
		} catch (IllegalAccessException e) {
			throw new CommonException(e, "创建beanClass实例异常");
		} catch (SecurityException e) {
			throw new CommonException(e, "获取[" + fieldname + "] getter setter 方法异常");
		} catch (NoSuchMethodException e) {
			throw new CommonException(e, "获取[" + fieldname + "] getter setter 方法异常");
		} catch (IllegalArgumentException e) {
			throw new CommonException(e, "[" + methodname + "] 方法赋值异常");
		} catch (InvocationTargetException e) {
			throw new CommonException(e, "[" + methodname + "] 方法赋值异常");
		}
		// 返回
		return list;
	}
	
	/**
	 * 根据实体类字段，获取对应数据字段，并以键值对方式保存到map中
	 * @param beanClass
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-21 下午10:52:34
	 */
	public static <T> Map<String, String> getTableColumns(Class<T> beanClass) {
		Map<String, String> map = new HashMap<String, String>();
		// 对象字段名称
		String fieldname = "";
		// 得到对象所有字段
		Field fields[] = beanClass.getDeclaredFields();
		// 遍历所有字段，对应配置好的字段并赋值
		for (Field field : fields) {
			// 获取注解配置
			JavaBeanField javaBeanField = field.getAnnotation(JavaBeanField.class);
			if(null != javaBeanField) {	// 有注解配置，下一步操作
				// 全部转化为大写
				String dbfieldname = javaBeanField.value().toUpperCase();
				// 获取字段名称
				fieldname = field.getName();
				// 保存返回值
				map.put(fieldname, dbfieldname);
			}
		}
		return map;
	}
	
	/**
	 * 根据实体类注解配置，生成queryAll操作的SQL语句
	 * @param beanClass
	 * @return
	 * @throws CommonException    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-22 下午5:49:04
	 */
	public static <T> String generateQueryAllSql(Class<T> beanClass) throws CommonException {
		// 表名称
		String tablename = "";
		// 获取对象配置的表名称
		for (Annotation annotation : beanClass.getDeclaredAnnotations()) {
			if(annotation instanceof JavaBeanTable) {
				tablename = ((JavaBeanTable) annotation).value().toUpperCase();
			}
		}
		
		// 拼接完整添加sql
		// 为了兼容区分大小写，所以一致改为小写
		return StrUtil.appendSbf("select * from ", tablename).toLowerCase();
	}
	
	/**
	 * 根据实体类注解配置，生成insert操作的SQL语句
	 * @param beanClass
	 * @return
	 * @throws CommonException    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-22 下午5:49:04
	 */
	public static <T> String generateInsertSql(Class<T> beanClass) throws CommonException {
		// 表名称
		String tablename = "";
		// 表字段拼接
		String tablecolumns = "";
		// 字段值拼接
		String fieldparams = "";
		// 父类表字段拼接
		String parenttablecolumns = "";
		// 父类字段值拼接
		String parentfieldparams = "";
		// 获取对象配置的表名称
		for (Annotation annotation : beanClass.getDeclaredAnnotations()) {
			if(annotation instanceof JavaBeanTable) {
				tablename = ((JavaBeanTable) annotation).value().toUpperCase();
			}
		}
		
		// 得到对象本身所有字段
		Field fields[] = beanClass.getDeclaredFields();
		// 遍历所有字段，对应配置好的字段并赋值
		for (Field field : fields) {
			// 获取注解配置
			JavaBeanField javaBeanField = field.getAnnotation(JavaBeanField.class);
			if(null != javaBeanField) {	// 有注解配置，下一步操作
				// 获取表字段名称
				tablecolumns += javaBeanField.value().toUpperCase() + ", ";
				// 判断是否有默认值配置
				if(JavaBeanConfig.JAVABEAN_COLUMN_DEFAULT_VAL.equals(javaBeanField.defaultVal())) {
					fieldparams += "?, ";
				} else {
					fieldparams += javaBeanField.defaultVal() + ", ";
				}
			}
		}
		// 判断是否有注解
		if(StrUtil.isBlank(tablecolumns)) {
			throw new CommonException(null, "对象没有配置表字段注解");
		}
		// 截断最后一个逗号
		tablecolumns = tablecolumns.substring(0, tablecolumns.length() - 2);
		// 截断最后一个逗号
		fieldparams = fieldparams.substring(0, fieldparams.length() - 2);
		
		// 得到父类本身所有字段
		fields = beanClass.getSuperclass().getDeclaredFields();
		// 遍历所有字段，对应配置好的字段并赋值
		for (Field field : fields) {
			// 获取注解配置
			JavaBeanField javaBeanField = field.getAnnotation(JavaBeanField.class);
			if(null != javaBeanField) {	// 有注解配置，下一步操作
				// 获取表字段名称
				parenttablecolumns += javaBeanField.value().toUpperCase() + ", ";
				// 判断是否有默认值配置
				if(JavaBeanConfig.JAVABEAN_COLUMN_DEFAULT_VAL.equals(javaBeanField.defaultVal())) {
					parentfieldparams += "?, ";
				} else {
					parentfieldparams += javaBeanField.defaultVal() + ", ";
				}
			}
		}
		// 判断是否有注解，若有，则拼接。反之，则不操作
		if(!StrUtil.isBlank(parenttablecolumns)) {
			// 截断最后一个逗号
			parenttablecolumns = ", " + parenttablecolumns.substring(0, parenttablecolumns.length() - 2);
			// 截断最后一个逗号
			parentfieldparams = ", " + parentfieldparams.substring(0, parentfieldparams.length() - 2);
		}

		// 拼接完整添加sql
		// 为了兼容区分大小写，所以一致改为小写
		return StrUtil.appendSbf("insert into ", tablename, "(", tablecolumns, parenttablecolumns, ")", " values(", fieldparams, parentfieldparams, ")").toLowerCase();
	}
	
	/**
	 * 根据实体类注解配置，生成update操作的SQL语句
	 * @param beanClass
	 * @return
	 * @throws CommonException    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-22 下午5:49:04
	 */
	public static <T> String generateUpdateByPrimarykeySql(Class<T> beanClass) throws CommonException {
		// 表名称
		String tablename = "";
		// 表字段拼接
		String tablecolumns = "";
		// 父类表字段拼接
		String parenttablecolumns = "";
		// 表的主键字段
		String primarykey = "";
		// 获取对象配置的表名称
		for (Annotation annotation : beanClass.getDeclaredAnnotations()) {
			if(annotation instanceof JavaBeanTable) {
				tablename = ((JavaBeanTable) annotation).value().toUpperCase();
			}
		}
		
		// 得到对象本身所有字段
		Field fields[] = beanClass.getDeclaredFields();
		// 遍历所有字段，对应配置好的字段并赋值
		for (Field field : fields) {
			// 获取注解配置
			JavaBeanField javaBeanField = field.getAnnotation(JavaBeanField.class);
			if(null != javaBeanField) {	// 有注解配置，下一步操作
				// 判断是否为主键配置
				JavaBeanPrimaryKey javaBeanPrimaryKey = field.getAnnotation(JavaBeanPrimaryKey.class);
				if(null != javaBeanPrimaryKey && javaBeanPrimaryKey.value()) {
					System.out.println(javaBeanPrimaryKey.value());
					// 判断是否配置了多个主键，是，则抛出异常
					if(StrUtil.isNotEmpty(primarykey)) {
						throw new CommonException(null, "对象配置了多个字段为主键");
					} else {
						// 设置主键字段
						primarykey = javaBeanField.value();
					}
				}
				// 获取表字段名称
				tablecolumns += javaBeanField.value().toUpperCase() + " = ?, ";
			}
		}
		// 判断是否有注解
		if(StrUtil.isBlank(tablecolumns)) {
			throw new CommonException(null, "对象没有配置表字段注解");
		}
		// 截断最后一个逗号
		tablecolumns = tablecolumns.substring(0, tablecolumns.length() - 2);
		
		// 得到父类本身所有字段
		fields = beanClass.getSuperclass().getDeclaredFields();
		// 遍历所有字段，对应配置好的字段并赋值
		for (Field field : fields) {
			// 获取字段基本内容配置
			JavaBeanField javaBeanField = field.getAnnotation(JavaBeanField.class);
			if(null != javaBeanField) {	// 有注解配置，下一步操作
				// 判断是否为主键配置
				JavaBeanPrimaryKey javaBeanPrimaryKey = field.getAnnotation(JavaBeanPrimaryKey.class);
				if(null != javaBeanPrimaryKey && javaBeanPrimaryKey.value()) {
					// 判断是否配置了多个主键，是，则抛出异常
					if(StrUtil.isNotEmpty(primarykey)) {
						throw new CommonException(null, "对象配置了多个字段为主键");
					} else {
						// 设置主键字段
						primarykey = javaBeanField.value();
					}
				}
				// 判断是否更新时操作该字段配置
				if(javaBeanField.upadtebase()) {
					// 判断是否有数据默认值配置，并且值不为程序默认值
					if(!JavaBeanConfig.JAVABEAN_COLUMN_DEFAULT_VAL.equals(javaBeanField.defaultVal())) {
						parenttablecolumns += javaBeanField.value().toUpperCase() + " = " + javaBeanField.defaultVal() + ", ";
					} else {
						parenttablecolumns += javaBeanField.value().toUpperCase() +" = ?, ";
					}
				}
			}
		}
		// 判断是否有注解，若有，则拼接。反之，则不操作
		if(!StrUtil.isBlank(parenttablecolumns)) {
			// 截断最后一个逗号
			parenttablecolumns = ", " + parenttablecolumns.substring(0, parenttablecolumns.length() - 2);
		}

		// 拼接完整添加sql
		// 为了兼容区分大小写，所以一致改为小写
		return StrUtil.appendSbf("update ", tablename, " set ", tablecolumns, parenttablecolumns, " where ", primarykey, " = ?").toLowerCase();
	}
	
	/**
	 * 生成添加SQL的入参
	 * @param obj
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @throws CommonException 
	 * @date 2013-9-23 上午11:46:21
	 */
	public static <T> Object[] generateInsertParams(Object obj) throws CommonException {
		// 入参动态数组存储对象
		List<Object> list = new ArrayList<Object>();
		// 对象字段名称
		String fieldname = "";
		try {
			// 获取对象的类型
			Class<? extends Object> beanClass = obj.getClass();
			// 遍历对象所有字段，对应配置好的字段并赋值
			for (Field field : beanClass.getDeclaredFields()) {
				// 获取注解配置
				JavaBeanField javaBeanField = field.getAnnotation(JavaBeanField.class);
				if(null != javaBeanField) {	// 有注解配置，下一步操作
					// 获取字段名称
					fieldname = field.getName();
					// 获取字段信息
					PropertyDescriptor pd = new PropertyDescriptor(fieldname, beanClass);
					//获得get方法
					Method getMethod = pd.getReadMethod();
					//执行get方法返回一个Object
					Object o = getMethod.invoke(obj);
					// 保存到返回数组中
					list.add(o);
				}
			}
	    } catch (IllegalAccessException e) {  
	        throw new CommonException(e, "获取[" + fieldname + "]get方法返回值异常");
	    } catch (InvocationTargetException e) {  
	        throw new CommonException(e, "获取[" + fieldname + "]get方法返回值异常");
	    } catch (IntrospectionException e) {
	        throw new CommonException(e, "获取[" + fieldname + "]字段信息异常");
		}
		// 返回数组
		return list.toArray();
	}
	
	/**
	 * 生成修改SQL的入参
	 * @param obj
	 * @return    
	 * @author yzChen  cyzshenzhen@163.com
	 * @throws CommonException 
	 * @date 2013-9-23 上午11:46:44
	 */
	public static Object[] generateUpdateParams(Object obj) throws CommonException {
		// 入参动态数组存储对象
		List<Object> list = new ArrayList<Object>();
		// 对象字段名称
		String fieldname = "";
		// 对象的主键字段
		String primarykey = "";
		try {
			// 获取对象的类型
			Class<? extends Object> beanClass = obj.getClass();
			// 遍历对象所有字段，对应配置好的字段并赋值
			for (Field field : beanClass.getDeclaredFields()) {
				// 获取注解配置
				JavaBeanField javaBeanField = field.getAnnotation(JavaBeanField.class);
				if(null != javaBeanField) {	// 有注解配置，下一步操作
					// 获取字段名称
					fieldname = field.getName();
					// 获取字段信息
					PropertyDescriptor pd = new PropertyDescriptor(fieldname, beanClass);
					//获得get方法
					Method getMethod = pd.getReadMethod();
					//执行get方法返回一个Object
					Object o = getMethod.invoke(obj);
					// 保存到返回数组中
					list.add(o);
				}
			}
			
			// 得到父类本身所有字段
			// 遍历所有字段，对应配置好的字段并赋值
			for (Field field : beanClass.getSuperclass().getDeclaredFields()) {
				// 获取字段基本内容配置
				JavaBeanField javaBeanField = field.getAnnotation(JavaBeanField.class);
				if(null != javaBeanField) {	// 有注解配置，下一步操作
					// 判断是否为主键配置
					JavaBeanPrimaryKey javaBeanPrimaryKey = field.getAnnotation(JavaBeanPrimaryKey.class);
					if(null != javaBeanPrimaryKey && javaBeanPrimaryKey.value()) {
						// 判断是否配置了多个主键，是，则抛出异常
						if(StrUtil.isNotEmpty(primarykey)) {
							throw new CommonException(null, "对象配置了多个字段为主键");
						} else {	
							// 设置对象的主键字段
							primarykey = field.getName();
						}
					}
				}
			}
			// 设置当前操作字段为主键字段
			fieldname = primarykey;
			// 获取主键字段信息
			PropertyDescriptor pd = new PropertyDescriptor(primarykey, beanClass);
			//获得get方法
			Method getMethod = pd.getReadMethod();
			//执行get方法返回一个Object
			Object o = getMethod.invoke(obj);
			// 保存到返回数组中
			list.add(o);
	    } catch (IllegalAccessException e) {  
	        throw new CommonException(e, "获取[" + fieldname + "]get方法返回值异常");
	    } catch (InvocationTargetException e) {  
	        throw new CommonException(e, "获取[" + fieldname + "]get方法返回值异常");
	    } catch (IntrospectionException e) {
	        throw new CommonException(e, "获取[" + fieldname + "]字段信息异常");
		}
		// 返回数组
		return list.toArray();
	}
	
	
	/**
	 * 测试
	 * @param args    
	 * @author yzChen  cyzshenzhen@163.com
	 * @date 2013-9-21 下午10:51:52
	 */
	public static void main(String[] args) {
		try {
			System.out.println(DbBeanUtils.ListMap2JavaBean(PersonData.datas(), Person.class));
			System.out.println(DbBeanUtils.getTableColumns(Person.class));
			System.out.println(DbBeanUtils.generateInsertSql(Person.class));
			System.out.println(DbBeanUtils.generateUpdateByPrimarykeySql(Person.class));
		} catch (CommonException e) {
			e.printStackTrace();
		}
	}
	
	
}










/***********************************************
 ***************** 测试数据 *********************
 ***********************************************/


@JavaBeanTable("t_person")
class Person implements Serializable {

	private static final long serialVersionUID = -5733530536004048706L;

	@JavaBeanField("I_ID")
	@JavaBeanPrimaryKey
	private Long id;

	@JavaBeanField("VC_NAME")
	private String name;

	@JavaBeanField("vc_idcard")
	private String idcard;

	@JavaBeanField("VC_ADDRESS")
	private String address;

	@JavaBeanField("vc_home_addfress")
	private String homeaddfress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHomeaddfress() {
		return homeaddfress;
	}

	public void setHomeaddfress(String homeaddfress) {
		this.homeaddfress = homeaddfress;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", idcard=" + idcard
				+ ", address=" + address + ", homeaddfress=" + homeaddfress
				+ "]";
	}

}


class PersonData {

	public static List<Map<String, Object>> datas() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("I_ID", 1L);
		map.put("VC_NAME", "per11");
		map.put("VC_IDCARD", "icard111");
		map.put("VC_ADDRESS", "add1111");
		map.put("VC_HOME_ADDFRESS", "homeadd11111");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("I_ID", 2L);
		map.put("VC_NAME", "per22");
		map.put("VC_IDCARD", "icard222");
		map.put("VC_ADDRESS", "add2222");
		map.put("VC_HOME_ADDFRESS", "homeadd22222");
		list.add(map);
		
		return list;
	}

}