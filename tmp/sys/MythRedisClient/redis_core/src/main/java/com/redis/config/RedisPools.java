package com.redis.config;

import com.redis.common.exception.ExceptionInfo;
import com.redis.common.exception.NoticeInfo;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 17-6-9  上午9:56
 * 一个连接redis的连接池实例, TODO 需要检查性能
 */
@Getter
@Setter
public class RedisPools{

    private static Logger logger = LoggerFactory.getLogger(RedisPools.class);
    // 是否加static,静态就只有一个了。会被覆盖
    private JedisPool jedisPool = null;
    private RedisPoolProperty property=null;
//     * redis过期时间,以秒为单位


    /**
     * 初始化Redis连接池
     */
    protected void initialPool(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(property.getMaxActive());
            config.setMaxIdle(property.getMaxIdle());
            config.setMaxWaitMillis(property.getMaxWaitMills());
            config.setTestOnBorrow(property.isTestOnBorrow());
//            System.out.println("密码："+property.getPassword()+property.getPassword().size());
            if(property.getPassword() != null && property.getPassword().length() > 0){
                jedisPool = new JedisPool(config, property.getHost(), property.getPort(),
                        property.getTimeout(),property.getPassword());
            }else{
                jedisPool = new JedisPool(config, property.getHost(), property.getPort(),
                        property.getTimeout());
            }
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("create JedisPool error : "+e);
        }
    }

    /**
     * 初始化连接池
     * @param property 属性对象
     */
    protected void initialPool(RedisPoolProperty property){
        setProperty(property);
        initialPool();
    }

//    /**
//     * 在多线程环境同步初始化
//     * 关于这个加锁的问题要考虑，目前还没有合适的场景
//     */
//    public synchronized void poolInit() {
//        if (jedisPool == null) {
//            initialPool();
//        }
//    }

    /**
     * 同步获取Jedis实例
     * @return Jedis
     * jedis.close直接放在finally里，用完了自动关闭
     */
    public Jedis getJedis(){
        if (jedisPool == null) {
            logger.info("连接池为空重新建立");
            initialPool();
        }
        Jedis jedis = null;
        try{
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
                logger.debug("使用连接池"+jedisPool+"得到连接"+jedis);
            }
        }catch (Exception e){
//            e.printStackTrace();
            logger.error(ExceptionInfo.POOL_NOT_AVAILABLE);
            logger.debug(NoticeInfo.ERROR_INFO,e);
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
        // 上面的方法是设置redis将会关闭超时超过30秒的空闲连接。而不是设置读取数据的超时时间。
        // 超时重连
//        jedis.configSet("timeout", "300");
//        System.out.println("密码是"+property.getPassword());
        return jedis;
    }
    // 测试当前连接池是否可用
    public boolean available(){
        Jedis jedis = getJedis();
        if(jedis!=null){
            jedis.select(0);
            String key = "myth.kuang@outlook.com";
            jedis.set(key,"available");
            if("available".equals(jedis.get(key))){
                jedis.del(key);
                return true;
            }
        }else{
            return false;
        }
        return false;
    }

    /**
     * 得到最大的数据库数
     * @return 整型值
     */
    public Integer getDatabaseNum(){
        List config = jedisPool.getResource().configGet("*");
        return Integer.parseInt(config.get(config.indexOf("databases")+1).toString());
    }
    /**
     * 销毁连接池，有用的
     * @return 销毁结果
     */
    public boolean destroyPool(){
//        System.out.println("销毁连接池："+jedisPool);
        logger.debug(NoticeInfo.DESTROY_POOL+this.getProperty());
        if(jedisPool!=null){
            jedisPool.destroy();
            return true;
        }
        return false;
    }
    /**
     * 获取连接池的当前状态信息
     * @return map集合
     */
    public Map<String,Integer> getStatus(){
        Map<String,Integer> status = new HashMap<>();
        status.put("NumActive",jedisPool.getNumActive());//活跃的连接数
        status.put("NumIdle",jedisPool.getNumIdle());//闲置的连接数
        status.put("NumWaiters",jedisPool.getNumWaiters());//等待的连接数
        return status;
    }
}