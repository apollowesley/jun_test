package net.oschina.j2cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.oschina.j2cache.util.IocUtils;

/**
 * 缓存入口
 * @author winterlau
 */
public class J2Cache {

	private final static Logger log = LoggerFactory.getLogger(J2Cache.class);

	private final static String CONFIG_FILE = "/j2cache.properties";
	private static CacheChannel channel;
	private static Properties config;
	private static boolean bcastEnabled;//广播机制是否开启，集群场景才需要开启
	private static boolean level1CacheEnabled;
	
	public static CacheChannel getChannel(){
		if(channel == null){
			synchronized (J2Cache.class) {
				if(channel != null)return channel;
				getConfig();
				if (IocUtils.springRedisRegistered()){
					channel = IocUtils.getInstance(RedisCacheChannel.class);
					((RedisCacheChannel)channel).initSpringCacheProvider();
				}
				
			}
		}
		return channel;
	}

	public static Properties getConfig(){
		if(config == null){
			try {
				config = loadConfig();
				level1CacheEnabled = Boolean.parseBoolean(config.getProperty("cache.L1.enabled","false"));
				//启动了一级缓存后才需要判断是否需要开启事件广播
				if(level1CacheEnabled){
					bcastEnabled = Boolean.parseBoolean(config.getProperty("cache.broadcast.enabled","true"));
				}
			} catch (IOException e) {
				throw new CacheException("Unabled to load j2cache configuration " + CONFIG_FILE, e);
			}
		}
		return config;
	}

	/**
	 * 加载配置
	 * @return
	 * @throws IOException
	 */
	private static Properties loadConfig() throws IOException {
		log.info("Load J2Cache Config File : [{}].", CONFIG_FILE);
		InputStream configStream = J2Cache.class.getClassLoader().getParent().getResourceAsStream(CONFIG_FILE);
		if(configStream == null)
			configStream = CacheManager.class.getResourceAsStream(CONFIG_FILE);
		Properties props = new Properties();
		if(configStream == null){
			log.warn("Cannot find " + CONFIG_FILE + " !!!");
		}else{			
			try{
				props.load(configStream);
			}finally{
				configStream.close();
			}
		}



		return props;
	}

	public static boolean isLevel1CacheEnabled() {
		return level1CacheEnabled;
	}

	public static boolean isBcastEnabled() {
		return bcastEnabled;
	}
}
