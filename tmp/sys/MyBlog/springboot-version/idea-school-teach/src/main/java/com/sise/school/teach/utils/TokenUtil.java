package com.sise.school.teach.utils;

import com.sise.school.teach.bussiness.student.po.StudentPO;
import com.sise.school.teach.bussiness.teacher.po.TeacherPO;
import com.sise.school.teach.utils.redis.JedisFactory;
import com.sise.school.teach.utils.redis.SerializeUtil;
import com.sise.school.teach.utils.redis.dto.RedisTokenDTO;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * @author idea
 * @data 2018/11/30
 */
public class TokenUtil {

    private static final String TOKEN_KEY_NAME = "token";

    /**
     * 从header中拿到token
     *
     * @param request
     * @return
     */
    public static Object getTokenFromHeader(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isBlank(token)) {
            return null;
        } else {
            Jedis jedis = JedisFactory.getJedisConn();
            Object obj = jedis.get(DecodeUtil.decodeStr(token));
            if (obj != null) {
                jedis.expire(token, 3600);
                return obj;
            }
            jedis.disconnect();
        }
        return null;
    }

    /**
     * 通过token获取对象字节数组
     *
     * @param token
     * @return
     */
    public static byte[] getToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        } else {
            Jedis jedis = JedisFactory.getJedisConn();
            byte[] obj = jedis.get(DecodeUtil.decodeStr(token).getBytes());
            if (obj != null) {
                jedis.expire(token, 3600);
                return obj;
            }
            jedis.disconnect();
        }
        return null;
    }

    /**
     * 从请求头部中获取token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        return request.getHeader(TOKEN_KEY_NAME);
    }

    public static RedisTokenDTO getObjByToken(String token) {
        byte[] result = getToken(token);
        RedisTokenDTO redisTokenDTO = getObjMessageFromSerializeObj(SerializeUtil.unserialize(result));
        return redisTokenDTO;
    }

    /**
     * 通过token获取对象信息
     *
     * @param object
     * @return
     */
    public static RedisTokenDTO getObjMessageFromSerializeObj(Object object) {
        if (object instanceof StudentPO) {
            StudentPO studentPO = (StudentPO) object;
            RedisTokenDTO redisTokenDTO = new RedisTokenDTO();
            redisTokenDTO.setAccount(studentPO.getAccount());
            redisTokenDTO.setUnionCode(studentPO.getStuCode());
            redisTokenDTO.setHeadImg(studentPO.getHeadImg());
            redisTokenDTO.setTel(studentPO.getTel());
            redisTokenDTO.setEmail(studentPO.getEmail());
            return redisTokenDTO;
        } else if (object instanceof TeacherPO) {
            TeacherPO teacherPO = (TeacherPO) object;
            RedisTokenDTO redisTokenDTO = new RedisTokenDTO();
            redisTokenDTO.setAccount(teacherPO.getName());
            redisTokenDTO.setUnionCode(teacherPO.getCode());
            redisTokenDTO.setHeadImg(teacherPO.getPicture());
            return redisTokenDTO;
        }
        return null;
    }
}
