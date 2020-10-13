package tom.cache;


import java.io.Serializable;
import java.sql.SQLException;


/**
 * 自动缓存数据重加载 
 * @author Winter Lau
 */
public class CacheHelper {

	/**
	 * 获取缓存数据
	 * @param region
	 * @param key
	 * @param invoker
	 * @param args
	 * @return
	 * @throws Exception 
	 */
	public static Object get(final String region, final Serializable key,
			final CacheInvoker invoker, final Object... args)throws SQLException {
		//1. 从正常缓存中获取数据
		Object data = EHCacheManager.get(region, key);
		if(data==null){
			data = invoker.callback(args);
			EHCacheManager.set(region, key, (Serializable) data);
		}
		
		/*if(data == null) {
			final String global_key = key + "@" + region;
			//2. 从全局二级缓存中获取数据
			data = CacheManager.get(CACHE_GLOBAL, global_key);
			//2.1 取不到为第一次运行
			if(data == null){
				if(invoker != null) {
					data = invoker.callback(args);
					if(data != null){
						CacheManager.set(region, key, (Serializable)data);
						CacheManager.set(CACHE_GLOBAL, global_key, (Serializable)data);
					}
				}
			}*/
		return data;
	}
	
}
