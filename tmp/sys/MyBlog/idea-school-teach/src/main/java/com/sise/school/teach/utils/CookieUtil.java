package com.sise.school.teach.utils;

import com.sise.school.teach.utils.redis.JedisFactory;
import jdk.nashorn.internal.scripts.JD;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author idea
 * @data 2018/11/30
 */
public class CookieUtil {

    private static final String TOKEN_KEY_NAME = "token";

    /**
     * 从cookie中拿到token
     * @param request
     * @return
     */
    public static Object getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_KEY_NAME);
        if(StringUtils.isBlank(token)){
            return null;
        }else{
            Jedis jedis=JedisFactory.getJedisConn();
            Object obj=jedis.get(token);
            if(obj!=null){
                jedis.expire(token,3600);
                return obj;
            }
            jedis.disconnect();
        }
        return null;
    }

}
