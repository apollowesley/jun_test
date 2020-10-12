package tom.db.redis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import tom.kit.clazz.ReflectUtil;
import tom.kit.io.FileUtil;

public class RedisDao {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private JedisPool pool = null;
	JedisPoolConfig config = new JedisPoolConfig();
	
	private int port = 6379;
	private String host = "127.0.0.1";
	private String password;
	/*控制一个pool最多有多少个状态为idle(空闲的)的jedis实例*/
	private int maxActive = 200;
	/*控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。*/
	private int maxIdle = 10;
	/*表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；*/
	private int maxWait;
	/*在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；*/
	private boolean testOnBorrow;
	
	public RedisDao() {
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public JedisPoolConfig getConfig() {
		return config;
	}

	public void setConfig(JedisPoolConfig config) {
		this.config = config;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	
	public void init(){
		config.setMaxActive(maxActive);
		config.setMaxIdle(maxIdle);
		config.setMaxWait(maxWait);
		config.setTestOnBorrow(testOnBorrow);
		pool = new JedisPool(config, host, port, 100000);
		log.info("init Redis success::" + pool+" :"+host);
	}
	
	public void init(File file){
		Properties pro = new Properties();
		InputStream in = null;
		try{
			in =  new FileInputStream(file);
			pro.load(in);
			ReflectUtil.populate(this, pro);
		}catch(IOException e){
			log.info("read MongoDbconfig error", e);
		}finally{
			FileUtil.close(in, null);
		}
		init();
	}
	
	public void init(Properties pro){
		ReflectUtil.populate(this, pro);
		init();
	}
	
	public void cleanUp(){
		pool.destroy();
	}
	
	public Jedis getResource(){
		Jedis jedis = pool.getResource();
		if(password != null){
			jedis.auth(password);
		}
		return jedis;
	}
	
	public void returnResource(Jedis jedis){
		pool.returnResource(jedis);
	}
	
	public void returnBrokenResource(Jedis jedis){
		pool.returnBrokenResource(jedis);
	}

}
