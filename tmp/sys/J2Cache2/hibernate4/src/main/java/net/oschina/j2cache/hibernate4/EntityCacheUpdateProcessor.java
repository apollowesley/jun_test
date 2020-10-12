package net.oschina.j2cache.hibernate4;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheConst;
import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.annotation.DbOperType;

/**
 * 实体缓存控制器
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月19日
 */
@Component
@Lazy(true)
public class EntityCacheUpdateProcessor implements InitializingBean{

	private final static Logger log = LoggerFactory.getLogger(EntityCacheUpdateProcessor.class);
	
	private static Map<Class<?>, CachePolicy> cachePolicys = new HashMap<Class<?>, CachePolicy>();
	
	//当前操作的entity class调用HibernateDao.realTableName写入
	private static ThreadLocal<Class<?>> currentEntityClass = new ThreadLocal<>();
	
   private CacheChannel cacheChannel;

	public CacheChannel getCacheChannel() {
		if(cacheChannel == null)cacheChannel = J2Cache.getChannel();
		return cacheChannel;
	}
	
	private String scanBasePackage;
	
	public void setScanBasePackage(String scanBasePackage) {
		this.scanBasePackage = scanBasePackage;
	}

	public static void setCurrentEntityClass(Class<?> type){
		currentEntityClass.set(type);
	}
	
	public static class CachePolicy{

		public Class<?> entityClass;
		public String nameSpace;
		public String pkKeyPrefix;
		public Field pkField;//主键字段
		public String groupKeysKey;
		

		public CachePolicy(Class<?> entityClass) {
			super();
			this.entityClass = entityClass;
			this.nameSpace = entityClass.getSimpleName();
			this.pkKeyPrefix = entityClass.getSimpleName() + CacheConst.PK_KEY_JOIN_STR;
			this.groupKeysKey = this.nameSpace + CacheConst.GROUPKEY_SUFFIX;
			Field[] fields = entityClass.getDeclaredFields();
			for (Field field : fields) {
				if(field.isAnnotationPresent(javax.persistence.Id.class)){
					this.pkField = field;
					this.pkField.setAccessible(true);
					break;
				}
			}
		}
	}
	
	
	public <T> T tryCache(Serializable id, Class<?> clazz,Callable<T> dataLoader) throws Exception{
		CachePolicy cachePolicy;
		if((cachePolicy = cachePolicys.get(clazz)) == null){
			return dataLoader.call(); 
		}
		
		String pkKey = cachePolicy.pkKeyPrefix + id;
		T entity = getCacheChannel().get(cachePolicy.nameSpace, pkKey);
		if(entity != null)return entity;
		//实际查询
		entity = dataLoader.call();
		//写入缓存
		if(entity != null)getCacheChannel().set(cachePolicy.nameSpace, pkKey, entity);
		return entity;
		
	}
	
	public void evictCache(DbOperType dbOperType,Object entity){
		Class<?> clazz = entity == null ? currentEntityClass.get() : entity.getClass();
		CachePolicy cachePolicy;
		if(clazz == null || (cachePolicy = cachePolicys.get(clazz)) == null){
			return; 
		}
		//DELETE cache
		removeAllRefCache(cachePolicy, entity);
	}
	
	public void evictCache(Class<?> entityClass){
		CachePolicy cachePolicy;
		if((cachePolicy = cachePolicys.get(entityClass)) == null){
			return; 
		}
		//DELETE cache
		getCacheChannel().clear(cachePolicy.nameSpace);
	}
	
	/**
	 * 移除所有关联的key（即除了按主键缓存key外所有同一组名下的缓存）
	 * @param cachePolicy
	 */
	@SuppressWarnings("unchecked")
	private void removeAllRefCache(CachePolicy cachePolicy,Object entity) {
		
		Object pkKey = null;
		//按实体操作，获取主键的值（复合主键要重写toString方法）
		if(entity != null && cachePolicy.pkField != null){
			try {				
				pkKey = cachePolicy.pkKeyPrefix + cachePolicy.pkField.get(entity);
			} catch (Exception e) {//反射获取主键id值失败避免脏数据强制移除所有缓存
			}
		}
		
		if(pkKey != null){
			getCacheChannel().evict(cachePolicy.nameSpace, pkKey);
			//该组下所有非主键缓存key
			List<String> keys = getCacheChannel().keys(cachePolicy.nameSpace);
			if(keys == null || keys.isEmpty())return;
			getCacheChannel().batchEvict(cachePolicy.nameSpace, keys);
		}else{
			getCacheChannel().clear(cachePolicy.nameSpace);
		}
	
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(scanBasePackage);
		registerAutoCacheEntitys();
	}
	
	private void registerAutoCacheEntitys(){
    	String RESOURCE_PATTERN = "/**/*.class";
    	
    	ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    	try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(scanBasePackage)
                    + RESOURCE_PATTERN;
            org.springframework.core.io.Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (org.springframework.core.io.Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
                    Class<?> clazz = Class.forName(className);
                    
                    Method[] methods = clazz.getDeclaredMethods();
                    outter:for (Method method : methods) {
//						if(!method.isAnnotationPresent(CacheDef.class))continue;
//						CacheDef cacheDef = method.getAnnotation(CacheDef.class);
                    	if(method.isAnnotationPresent(Cacheable.class)){
                    		Field[] fields = clazz.getDeclaredFields();
                    		for (Field field : fields) {
                    			if("dao".equals(field.getName())){                    				
                    				Type genericType = field.getGenericType();
                    				Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();   
                    			    Class<?>  entityClass =  (Class)params[0]; 
                    			    cachePolicys.put(entityClass, new CachePolicy(entityClass));
                    			    log.info("add entity [{}] to autoCache Set",entityClass.getName());
                    			    break outter;
                    			}
								
							}
                    	}
					}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	           
	
	}
	
}
