package cn.uncode.session.data.redis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import cn.uncode.util.config.UncodePropertyPlaceholderConfigurer;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory implements FactoryBean<JedisClusterCustom>, InitializingBean {

	private static Logger log = LoggerFactory.getLogger(JedisClusterFactory.class);

	private static final int POOL_MAX_IDLE = 100;
	private static final int POOL_MIN_IDLE = 8;
	private static final int POOL_MAX_WAIT_MILLIS = 1000;
	private static final int POOL_MAX_TOTAL = 600;
	private static final int TIME_OUT = 10000;
	private static final int MAX_REDIRECTIONS = 6;

	private Resource resource;
	// redis集群的urls。多个用英文分号分隔。例：192.168.1.13:6379;192.168.1.13:6389
//	private String clusterUrls;

	private JedisClusterCustom JedisCluster;

	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

	@Override
	public JedisClusterCustom getObject() throws Exception {

		return JedisCluster;
	}

	@Override
	public Class<? extends JedisCluster> getObjectType() {
		return (this.JedisCluster != null ? this.JedisCluster.getClass() : JedisClusterCustom.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			Set<String> set = null;
			if (null != resource) {
				UncodePropertyPlaceholderConfigurer.loadPorperties(resource);
			}
			String urls = UncodePropertyPlaceholderConfigurer.getProperty("uncode_cache_redis_cluster_ipport");
			if(StringUtils.isBlank(urls)){
				throw new IllegalArgumentException("解析 jedis ip和prot失败");
			}
			set = new HashSet(Arrays.asList(urls.split(";")));
			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			if (null != set) {
				for (String val : set) {
					boolean isIpPort = p.matcher(val).matches();
					if (!isIpPort) {
						throw new IllegalArgumentException("ip 或 port 不合法");
					}
					String[] ipAndPort = val.split(":");
					HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
					haps.add(hap);
				}
			}
			return haps;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		 init();
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void init() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		if (null == haps || haps.size() == 0) {
			throw new Exception("解析 jedis 配置文件失败");
		}
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(
				UncodePropertyPlaceholderConfigurer.getProperty4Int("uncode_cache_redis_pool_max_idle", POOL_MAX_IDLE));
		genericObjectPoolConfig.setMinIdle(
				UncodePropertyPlaceholderConfigurer.getProperty4Int("uncode_cache_redis_pool_min_idle", POOL_MIN_IDLE));
		genericObjectPoolConfig.setMaxWaitMillis(UncodePropertyPlaceholderConfigurer
				.getProperty4Int("uncode_cache_redis_pool_max_wait_millis", POOL_MAX_WAIT_MILLIS));
		genericObjectPoolConfig.setMaxTotal(UncodePropertyPlaceholderConfigurer
				.getProperty4Int("uncode_cache_redis_pool_max_total", POOL_MAX_TOTAL));
		JedisCluster = new JedisClusterCustom(haps,
				UncodePropertyPlaceholderConfigurer.getProperty4Int("uncode_cache_redis_cluster_time_out", TIME_OUT),
				UncodePropertyPlaceholderConfigurer.getProperty4Int("uncode_cache_redis_cluster_max_redirections", MAX_REDIRECTIONS),
				genericObjectPoolConfig);

	}

}
