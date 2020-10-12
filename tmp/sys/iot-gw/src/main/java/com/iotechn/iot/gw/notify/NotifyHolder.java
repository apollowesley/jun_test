package com.iotechn.iot.gw.notify;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.iotechn.iot.gw.exception.GatewayServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.yeauty.pojo.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-12-24
 * Time: 下午12:19
 */
@Component
public class NotifyHolder {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String REDIS_NOTIFY_PREFIX = "GW_PARAM_NOTIFY";

    private Map<String,NotifyProcessor> holdMap = new ConcurrentHashMap<>();

    public boolean holdParam(String secretKey,NotifyProcessor processor) {
        if (StringUtils.isEmpty(secretKey)) {
            return false;
        }
        holdMap.put(secretKey,processor);
        stringRedisTemplate.opsForSet().add(REDIS_NOTIFY_PREFIX, secretKey);
        return true;
    }

    public boolean unholdParam(String secretKey) {
        if (StringUtils.isEmpty(secretKey)) {
            return false;
        }
        holdMap.remove(secretKey);
        stringRedisTemplate.opsForSet().remove(REDIS_NOTIFY_PREFIX, secretKey);
        return true;
    }

    public void process(String secretKey,String msg) {
        holdMap.get(secretKey).process(msg);
    }
}
