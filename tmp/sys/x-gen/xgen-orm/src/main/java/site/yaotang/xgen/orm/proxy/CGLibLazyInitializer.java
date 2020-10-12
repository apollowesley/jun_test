package site.yaotang.xgen.orm.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodProxy;
import site.yaotang.xgen.orm.helper.DBHelper;
import site.yaotang.xgen.orm.mapping.Id;
import site.yaotang.xgen.orm.mapping.ManyToOne;
import site.yaotang.xgen.orm.mapping.Property;
import site.yaotang.xgen.orm.mapping.Table;
import site.yaotang.xgen.orm.sqltools.DetachCriteria;
import site.yaotang.xgen.orm.tools.Constant;
import site.yaotang.xgen.orm.utils.CollectionUtil;
import site.yaotang.xgen.orm.utils.LogUtil;
import site.yaotang.xgen.orm.utils.ReflectUtil;
import site.yaotang.xgen.orm.utils.TableUtil;

/**
 * 利用CGLib实现懒加载方法拦截
 * @author hyt
 * @date 2018年1月1日
 */
@Data
@Slf4j
public class CGLibLazyInitializer implements LazyInitializer {

	private Class<?> clazz;
	private DetachCriteria dc;
	private String condition;
	private Serializable id;

	private Object obj;
	private Table table;

	public CGLibLazyInitializer(Class<?> clazz, DetachCriteria dc) {
		this.clazz = clazz;
		this.dc = dc;
	}

	public CGLibLazyInitializer(Class<?> clazz, String condition) {
		this.clazz = clazz;
		this.condition = condition;
	}

	public CGLibLazyInitializer(Class<?> clazz, Serializable id) {
		this.clazz = clazz;
		this.id = id;
	}

	// 通过Session.load方法会传递部分参数，但是返回的只是一个空白的对象
	// 后续可能通过空白的对象尝试获取数据

	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		// 获取真实的方法名称
		String methodName = proxy.getSuperName().split("\\$")[1];
		// 如果是getset方法，获取方法名称对应的属性名称
		String fieldName = methodName.substring(3).substring(0, 1).toLowerCase() + methodName.substring(4);
		// 准备返回结果
		Object result = null;
		// 多对一集合
		table = Constant.TABLEMAP.get(clazz.getName());
		List<ManyToOne> manyList = table.getManyList();
		if (obj == null) {
			// 设置全局obj的值
			setObjValue(manyList);
		}
		// 如上设置了全局obj的值
		// 接着判断请求的方法名
		// 如果是请求obj本级属性
		Property property = TableUtil.getProperty(table, fieldName);
		if (property != null) {
			// 可以利用obj返回
			result = proxy.invoke(obj, args);
		}
		// 如果请求上级属性
		ManyToOne manyToOne = TableUtil.getManyToOne(table, fieldName);
		if (manyToOne != null) {
			// 先判断上级属性是否为空
			// 上级属性不为空则返回上级属性
			// 上级属性为空则查询并放射获取上级属性
			// 然后后返回
			// obj的查询已完成，接下来完成的多对一的一方
			// XXX 应该还需要一些标记，记录一方是否已经查询过数据库，如果已经查询过数据库则下次直接返回，不用再次查询数据库
			if (!CollectionUtil.isEmpty(manyList)) {
				// 查询的属性是上级对象
				if (fieldName.equals(manyToOne.getName())) {
					// 上级表信息
					Table mTable = Constant.TABLEMAP.get(manyToOne.getClazz());
					// 上级Id信息
					Id mId = mTable.getId();
					// 准备sql
					String sql = "select * from " + mTable.getTableName() + " where " + mId.getColumn() + "=";
					// 反射获取上级对象
					Object mObj = ReflectUtil.getValue(clazz, obj, manyToOne.getName());
					// 反射获取上级对象Id值
					Object mIdValue = ReflectUtil.getValue(mTable.getClassName(), mObj, mId.getName());
					// 完成SQL
					sql += String.valueOf(mIdValue);
					// 查询结果集
					ResultSet resultSet = DBHelper.executeRead(sql);
					List<Property> mProperties = mTable.getProperties();
					try {
						while (resultSet.next()) {
							// 每条结果集对应一个上级实例
							Class<?> mClazz = Class.forName(mTable.getClassName());
							Object mInstance = mObj;// mClazz.newInstance();
							// 反射设置id
							ReflectUtil.setValue(mClazz, mInstance, mId.getName(), resultSet.getObject(mId.getColumn()));
							// 反射设置Properties
							if (!CollectionUtil.isEmpty(mProperties)) {
								for (Property mProperty : mProperties) {
									ReflectUtil.setValue(mClazz, mInstance, mProperty.getName(), resultSet.getObject(mProperty.getColumn()));
								}
							}
							// 反射设置上级实例到全局obj
							ReflectUtil.setValue(clazz, obj, manyToOne.getName(), mObj);
						}
					} catch (Exception e) {
						LogUtil.error(log, e);
						throw new RuntimeException(e);
					}
				}
			}
		}
		// 如果既不是本级也不是上级则还是利用全局obj
		if (result == null) {
			// result = proxy.invokeSuper(object, args);
			result = proxy.invoke(obj, args);
		}
		return result;
	}

	/**
	 * 设置全局obj的值
	 */
	private void setObjValue(List<ManyToOne> manyList) {
		// 全局的obj为空，说明返回空白对象后第一次访问实例方法
		// 保存表信息
		table = Constant.TABLEMAP.get(clazz.getName());
		// 准备sql
		String sql = "select * from " + table.getTableName() + " where ";
		// 追加条件
		if (dc != null) {
			sql = dc.getSQLString();
		} else if (condition != null) {
			sql += condition;
		} else if (id != null) {
			sql += table.getId().getColumn() + "=" + id;
		}
		// 返回查询结果集
		ResultSet resultSet = DBHelper.executeRead(sql);
		try {
			while (resultSet.next()) {
				// 每条记录对应一个实例
				Class<?> c = Class.forName(table.getClassName());
				Object instance = c.newInstance();
				// 反射Id
				ReflectUtil.setIdValue(c, instance, table.getId(), resultSet);
				// 反射properties
				ReflectUtil.setPropertyValue(c, instance, table.getProperties(), resultSet);
				// 反射多对一，实际只是保存了一方的一个ID，并不是保存全部信息
				if (!CollectionUtil.isEmpty(manyList)) {
					for (ManyToOne manyToOne : table.getManyList()) {
						// 一方的类
						Class<?> oneClazz = Class.forName(manyToOne.getClazz());
						// 一方的实例
						Object oneInstance = ReflectUtil.newInstance(oneClazz);
						// 反射设置一方的值
						ReflectUtil.setValue(oneClazz, oneInstance, manyToOne.getField(), resultSet.getObject(manyToOne.getColumn()));
						// 反射设置多方的值
						ReflectUtil.setValue(c, instance, manyToOne.getName(), oneInstance);
					}
				}
				// 保存到全局对象
				this.obj = instance;
			}
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		}
	}
}
