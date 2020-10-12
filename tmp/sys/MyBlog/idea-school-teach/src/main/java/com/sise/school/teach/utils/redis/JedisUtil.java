package com.sise.school.teach.utils.redis;

import com.sise.school.teach.bussiness.student.po.StudentPO;
import com.sise.school.teach.utils.DecodeUtil;
import com.sise.school.teach.utils.TokenUtil;
import com.sise.school.teach.utils.redis.dto.CommentDTO;
import com.sise.school.teach.utils.redis.dto.RedisTokenDTO;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.sise.school.teach.constants.RedisConstants.*;

/**
 * 专门用于redis的工具类
 *
 * @author idea
 * @data 2018/12/1
 */
public class JedisUtil {

    public boolean addDialogGoodLog(String usercode, String dialogCode) {
        Jedis jedis = JedisFactory.getJedisConn();
        Map<String, String> redisHashMap = jedis.hgetAll(usercode);
        if (redisHashMap.size() == 0) {
            //第一次插入
            jedis.hset(usercode, USER_GOOD_DIALOG_LOG, dialogCode);
        } else {
            String goodField = jedis.hget(usercode, USER_GOOD_DIALOG_LOG);
            if (goodField == null) {
                goodField = dialogCode;
            } else {
                goodField = goodField + SPLITE_CODE + dialogCode;
            }
            jedis.hset(usercode, USER_GOOD_DIALOG_LOG, goodField);
        }
        jedis.disconnect();
        return true;
    }

    public boolean updateUserInCache(String token, String account,String stuCode, String tel, String email) {
        Jedis jedis = JedisFactory.getJedisConn();
        byte[] obj = jedis.get(DecodeUtil.decodeStr(token).getBytes());
        Object object=SerializeUtil.unserialize(obj);
        if (object instanceof StudentPO) {
            StudentPO studentPO= (StudentPO) object;
            studentPO.setEmail(email);
            studentPO.setTel(tel);
            studentPO.setAccount(account);
            jedis.setex(DecodeUtil.decodeStr(token).getBytes(),3600, SerializeUtil.serialize(studentPO));
        }
        jedis.disconnect();
        return true;
    }

    /**
     * 创建评论点赞dto
     *
     * @param token
     * @return
     */
    public CommentDTO createCommentDTO(String token) {
        byte[] bytes = TokenUtil.getToken(token);
        RedisTokenDTO redisTokenDTO = TokenUtil.getObjMessageFromSerializeObj(SerializeUtil.unserialize(bytes));
        Jedis jedis = JedisFactory.getJedisConn();
        CommentDTO commentDTO = new CommentDTO();
        if (redisTokenDTO != null) {
            Map<String, String> redisHashMap = jedis.hgetAll(redisTokenDTO.getUnionCode());
            String goodDialog = redisHashMap.get(USER_GOOD_DIALOG_LOG);
            String badDialog = redisHashMap.get(USER_BAD_DIALOG_LOG);
            String goodReview = redisHashMap.get(USER_GOOD_REVIEW_LOG);
            String badReview = redisHashMap.get(USER_BAD_REVIEW_LOG);
            commentDTO.setUnionCode(redisTokenDTO.getUnionCode());
            commentDTO.setDialogGoodCodeList(stringToList(goodDialog));
            commentDTO.setDialogBadCodeList(stringToList(badDialog));
            commentDTO.setReviewBadCodeList(stringToList(badReview));
            commentDTO.setReviewGoodCodeList(stringToList(goodReview));
            jedis.disconnect();
            return commentDTO;
        }
        return null;
    }


    /**
     * 字符串转为集合类型
     *
     * @param str
     * @return
     */
    private List<String> stringToList(String str) {
        if (str == null) {
            return Collections.emptyList();
        }
        String[] arr = str.split(SPLITE_CODE);
        if (arr.length == 0) {
            return Collections.emptyList();
        } else {
            List<String> resultList = new ArrayList<>();
            for (String item : arr) {
                resultList.add(item);
            }
            return resultList;
        }
    }

    public static void main(String[] args) {
        JedisUtil jedisUtil = new JedisUtil();
        System.out.println(jedisUtil.isDialogGoodCodeExist("usercode1", "112"));
    }

    public boolean addDialogBadLog(String usercode, String dialogCode) {
        Jedis jedis = JedisFactory.getJedisConn();
        Map<String, String> redisHashMap = jedis.hgetAll(usercode);
        if (redisHashMap.size() == 0) {
            //第一次插入
            jedis.hset(usercode, USER_BAD_DIALOG_LOG, dialogCode);
        } else {
            String badField = jedis.hget(usercode, USER_BAD_DIALOG_LOG);
            if (badField == null) {
                badField = dialogCode;
            } else {
                badField = badField + SPLITE_CODE + dialogCode;
            }
            jedis.hset(usercode, USER_BAD_DIALOG_LOG, badField);
        }
        jedis.disconnect();
        return true;
    }

    public boolean addReviewBadLog(String usercode, String reviewCode) {
        Jedis jedis = JedisFactory.getJedisConn();
        Map<String, String> redisHashMap = jedis.hgetAll(usercode);
        if (redisHashMap.size() == 0) {
            //第一次插入
            jedis.hset(usercode, USER_BAD_REVIEW_LOG, reviewCode);
        } else {
            String badField = jedis.hget(usercode, USER_BAD_REVIEW_LOG);
            if (badField == null) {
                badField = reviewCode;
            } else {
                badField = badField + SPLITE_CODE + reviewCode;
            }
            jedis.hset(usercode, USER_BAD_REVIEW_LOG, badField);
        }
        jedis.disconnect();
        return true;
    }

    public boolean addReviewGoodLog(String usercode, String reviewCode) {
        Jedis jedis = JedisFactory.getJedisConn();
        Map<String, String> redisHashMap = jedis.hgetAll(usercode);
        if (redisHashMap.size() == 0) {
            //第一次插入
            jedis.hset(usercode, USER_GOOD_REVIEW_LOG, reviewCode);
        } else {
            String goodField = jedis.hget(usercode, USER_GOOD_REVIEW_LOG);
            if (goodField == null) {
                goodField = reviewCode;
            } else {
                goodField = goodField + SPLITE_CODE + reviewCode;
            }
            jedis.hset(usercode, USER_GOOD_REVIEW_LOG, goodField);
        }
        jedis.disconnect();
        return true;
    }


    /**
     * 是否该代码在记录中存在过
     *
     * @param usercode
     * @param code
     * @param fieldName
     * @return
     */
    private boolean isCodeExist(String usercode, String code, String fieldName) {
        boolean status = false;
        Jedis jedis = JedisFactory.getJedisConn();
        Map<String, String> redisHashMap = jedis.hgetAll(usercode);
        if (redisHashMap.size() == 0) {
            return false;
        } else {
            String field = jedis.hget(usercode, fieldName);
            if (field == null) {
                status = false;
            } else {
                String[] stringArr = field.split(SPLITE_CODE);
                for (String item : stringArr) {
                    if (item.equals(code)) {
                        status = true;
                    }
                }
            }
        }
        jedis.disconnect();
        return status;
    }

    public boolean isDialogGoodCodeExist(String usercode, String dialogCode) {
        return isCodeExist(usercode, dialogCode, USER_GOOD_DIALOG_LOG);
    }

    public boolean isDialogBadCodeExist(String usercode, String dialogCode) {
        return isCodeExist(usercode, dialogCode, USER_BAD_DIALOG_LOG);
    }

    public boolean isReviewGoodCodeExist(String usercode, String reviewCode) {
        return isCodeExist(usercode, reviewCode, USER_GOOD_REVIEW_LOG);
    }

    public boolean isReviewBadCodeExist(String usercode, String reviewCode) {
        return isCodeExist(usercode, reviewCode, USER_BAD_REVIEW_LOG);
    }
}
