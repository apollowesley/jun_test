package com.flypigs.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.Map;

public class LockManager {
    static final String JEDIS_CLUSTER_CLASS = "redis.clients.jedis.JedisCluster";
    static final String REDIS_TEMPLATE_CLASS = "org.springframework.data.redis.core.RedisTemplate";
    @Autowired
    ApplicationContext applicationContext;
    private Object dependObj;

    @PostConstruct
    private void init(){
        Class clazz = null;
        try {
            clazz = Class.forName(JEDIS_CLUSTER_CLASS);
            Map<String,Object> objs = applicationContext.getBeansOfType(clazz);
            if(objs != null && objs.size() > 0){
                dependObj = objs.values().toArray()[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dependObj == null) {
            try {
                clazz = Class.forName(REDIS_TEMPLATE_CLASS);
                Map<String,Object> objs = applicationContext.getBeansOfType(clazz);
                if(objs != null && objs.size() > 0){
                    dependObj = objs.values().toArray()[0];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public DistributedLock getLock(){
        if(dependObj == null){
            return null;
        }
        if(dependObj.getClass().getName().equals(JEDIS_CLUSTER_CLASS)){
            return new JedisClusterLock((JedisCluster) dependObj);
        }else if(dependObj.getClass().getName().equals(REDIS_TEMPLATE_CLASS)){
            return new RedisTemplateLock((RedisTemplate) dependObj);
        }
        return null;
    }


}
