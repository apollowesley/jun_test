/**
 * 
 */
package net.oschina.j2cache.hibernate4.cache;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.redis.J2CacheCache;
import net.oschina.j2cache.util.IocUtils;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年11月1日
 */
public class J2CacheFixedLazyCache extends J2CacheCache {
	
	private static SessionFactory sessionFactory;
	
	/**
	 * @param cacheName
	 * @param cacheChannel
	 */
	public J2CacheFixedLazyCache(String cacheName, CacheChannel cacheChannel) {
		super(cacheName, cacheChannel);
		if(sessionFactory == null)sessionFactory = (SessionFactory) IocUtils.getInstance(SessionFactory.class);
	}
	
	@Override
	public ValueWrapper get(Object key) {
		ValueWrapper valueWrapper = super.get(key);
		if(valueWrapper == null)return null;
		FixedLazyValueWrapper fixedLazyValueWrapper = new FixedLazyValueWrapper(valueWrapper);
//		Object object = fixedLazyValueWrapper.get();
//		if(fixedLazyValueWrapper.getLazyProperies() != null && fixedLazyValueWrapper.getLazyProperies().size() > 0){
//			//触发加载lazy的字段
//			ToStringBuilder.reflectionToString(object);
//			//
//			valueWrapper = new SimpleValueWrapper(object);
//			//重新放入缓存 
//			put(key, object);
//			return valueWrapper;
//		}
		return fixedLazyValueWrapper;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		return super.get(key, type);
	}
	
	
    static class FixedLazyValueWrapper implements ValueWrapper {
		private ValueWrapper wrapper;
		private List<PropertyDescriptor> lazyProperies;

		public FixedLazyValueWrapper(ValueWrapper wrapper) {
			this.wrapper = wrapper;
		}

		public List<PropertyDescriptor> getLazyProperies() {
			return lazyProperies;
		}

		@Override
		public Object get() {
			final Object object = wrapper.get();
			if (object == null || lazyProperies != null) {
				return object;
			}
			if (object instanceof Collection) {/** 缓存对象是list 需要对其列表数据项中的代理对象设置相应的session */
				@SuppressWarnings("rawtypes")
				Collection listObject = (Collection)object;
				for (Object subObject : listObject) {
					List<PropertyDescriptor> list = setSession2HibernateProxyField(subObject);
					if(lazyProperies == null)lazyProperies = list;
				}
			} else {/** 缓存对象是普通object 需要对其属性中类型为代理对象设置相应的session */
				lazyProperies = setSession2HibernateProxyField(object);
			}
			return object;
		}
		
	}
    
    
	/**
	 * 本项目中缓存使用redis,缓存内容只为当前实体的基本属性
	 * 并不缓存其对应的实体类属性
	 * 对于实体类属性 当使用时，通过当前线程的session去db取
	 * 例如:cityConfig对象 拥有一般属性cityConfig.id cityConfig.name
	 * 拥有实体属性 cityConfig.city(注:city为自定义class)
	 * 当缓存cityConfig时，本项目是不对city进行缓存的(原因:city对象更新 程序需对cityConfig处理)。
	 * 后期使用过程中，当时用到cityConfig缓存时并且需要city时，由于city未一起进行缓存
	 * 程序会抛出异常 原因是缺失session 故通过如下方法设置session
	 * 使程序在没有city时，自动去db查询
	 * @param object
	 */
	private static List<PropertyDescriptor> setSession2HibernateProxyField(Object object) {
		List<PropertyDescriptor> result = new ArrayList<>();
		try {			
			BeanInfo srcBeanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = srcBeanInfo.getPropertyDescriptors();
			
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				final Object invoke = propertyDescriptor.getReadMethod().invoke(object);
			if (invoke instanceof HibernateProxy) {
				((HibernateProxy) invoke).getHibernateLazyInitializer().setSession(
						(SessionImplementor) sessionFactory.getCurrentSession());
				result.add(propertyDescriptor);
			}
			}
		} catch (Exception e) {
			
		}
		return result;
	}

}
