package tom.db.redis;


import java.util.Collections;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import tom.cocook.ext.BeanFactory;

/**
 * 使用 redis 作为缓存的问题
 * @author tomsun
 * 
 */
public class RedisUtil {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	RedisDao redisDao = BeanFactory.getInstance(RedisDao.class);
	
	private static RedisUtil self;
	
	public static RedisUtil getInstance(){
		if(self == null){
			self = new RedisUtil();
		}
		return self;
	}
	
	/*public Map<String, String> getUser(RequestContext context){
		Cookie cookie = context.getCookie("cid");
		if(cookie == null) return null;
		String cid = cookie.getValue();
		String[] ut = cid.split("_");
		String userid = cid;
		if(ut.length==2){
			userid = ut[0];
		}
		Map<String, String> user = gethash(userid);
		return user;
	}*/
	
	/*public String getUserId(RequestContext context){
		Cookie cookie = context.getCookie("cid");
		if(cookie == null) return null;
		String cid = cookie.getValue();
		String[] ut = cid.split("_");
		String userid = cid;
		if(ut.length==2){
			userid = ut[0];
		}
		return userid;
	}*/
	
	/*public void setUser(User user){
		Map<String, String> mUser  = new HashMap<String, String>();
		mUser.put("id", user.getId());
		mUser.put("nick", user.getNick());
		mUser.put("name", user.getName());
		mUser.put("tel", user.getTel()+"");
		mUser.put("right", ""+user.getRight());
		mUser.put("email", user.getEmail());
		mUser.put("avate", user.getAvate());
		mUser.put("status", ""+user.getStatus());
		sethash(user.getId(), mUser, 60 * 60 * 12);
	}*/
	

	public String get(String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = redisDao.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("get", e);
		} finally {
			redisDao.returnResource(jedis);
		}
		return value;
	}

	public String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.set(key, value);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("set", e);
			return "0";
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	public String set(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.setex(key, seconds, value);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("set", e);
			return "0";
		} finally {
			redisDao.returnResource(jedis);
		}
	}

	public Long del(String... keys) {
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.del(keys);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("del", e);
			return 0L;
		} finally {
			redisDao.returnResource(jedis);
		}
	}

	public boolean exists(String key){
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("exists", e);
			return false;
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	public void flushDB(){
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			jedis.flushDB();
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("flushDB", e);
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	public String sethash(String key,Map<String, String> hash){
		Jedis jedis = null;
		String res = null;
		try {
			jedis = redisDao.getResource();
			res = jedis.hmset(key, hash);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("sethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
		return res;
	}
	
	public String sethash(String key,Map<String, String> hash, int seconds){
		Jedis jedis = null;
		String res = null;
		try {
			jedis = redisDao.getResource();
			res = jedis.hmset(key, hash);
			jedis.expire(key, seconds);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("sethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
		return res;
	}
	
	public Map<String, String> gethash(String key){
		Jedis jedis = null;
		Map<String, String> res = null;
		try {
			jedis = redisDao.getResource();
			res = jedis.hgetAll(key);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("gethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
		return res;
	}
	
	public void set(String key, String... values){
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			jedis.lpush(key, values);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("gethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	public void set(String key, int seconds, String... values){
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			jedis.lpush(key, values);
			jedis.expire(key, seconds);
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("gethash", e);
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
	public List<String> getlist(String key){
		Jedis jedis = null;
		try {
			jedis = redisDao.getResource();
			return jedis.lrange(key, 0, jedis.hlen(key));
		} catch (Exception e) {
			redisDao.returnBrokenResource(jedis);
			log.error("gethash", e);
			return Collections.emptyList();
		} finally {
			redisDao.returnResource(jedis);
		}
	}
	
}
