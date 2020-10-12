package com.sise.school.teach.bussiness.student.service;

import com.sise.school.teach.bussiness.course.dao.CourseDao;
import com.sise.school.teach.bussiness.course.po.CoursePO;
import com.sise.school.teach.bussiness.course.vo.resp.CourseRespVO;
import com.sise.school.teach.bussiness.student.dao.SignDao;
import com.sise.school.teach.bussiness.student.dao.StudentDao;
import com.sise.school.teach.bussiness.student.po.SignPO;
import com.sise.school.teach.bussiness.student.po.StudentPO;
import com.sise.school.teach.bussiness.student.vo.req.StudentReqVO;
import com.sise.school.teach.bussiness.student.vo.resp.StudentRespVO;
import com.sise.school.teach.common.GenerateUnionCode;
import com.sise.school.teach.utils.DecodeUtil;
import com.sise.school.teach.utils.EncodeUtil;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.utils.redis.JedisFactory;
import com.sise.school.teach.utils.redis.JedisUtil;
import com.sise.school.teach.utils.redis.SerializeUtil;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.sise.school.teach.constants.UnionCodePrefix.*;
import static com.sise.school.teach.utils.CodeGenerateUtil.buildRandNumber;
import static com.sise.school.teach.utils.EncodeUtil.creatSalt;

/**
 * @author idea
 * @data 2018/10/3
 */
@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private SignDao signDao;
    @Autowired
    private CourseDao courseDao;

    private static String LOG_HEAD = "[学生服务]";

    public ResponseMsgVO<Boolean> updateByCode(String token, StudentReqVO studentReqVO) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("account", studentReqVO.getAccount());
        hashMap.put("tel", studentReqVO.getTel());
        hashMap.put("email", studentReqVO.getEmail());
        hashMap.put("stuCode", studentReqVO.getStuCode());
        studentDao.updateByCode(hashMap);
        JedisUtil jedisUtil = new JedisUtil();
        //更新redis缓存
        jedisUtil.updateUserInCache(token, studentReqVO.getAccount(), studentReqVO.getStuCode(), studentReqVO.getTel(), studentReqVO.getEmail());
        return ResponseBuilder.buildSuccessResponVO(true);
    }


    public ResponseMsgVO<String> login(String account, String password, HttpServletResponse response) {
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            log.info(LOG_HEAD + "登录失败，账号为{}", account);
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            StudentPO studentPO = studentDao.selectOne("account", account);
            if (studentPO != null) {
                String decodePassword = DecodeUtil.decodeStrWithSalt(studentPO.getPassword(), studentPO.getSalt());
                boolean loginSuccess = password.equals(decodePassword);
                if (loginSuccess) {
                    String token = createToken(studentPO);
                    return ResponseBuilder.buildSuccessResponVO(token);
                }
            } else {
                log.info(LOG_HEAD + "登录失败，账号为{}", account);
            }
            return ResponseBuilder.buildSuccessResponVO(null);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    public ResponseMsgVO<Boolean> register(StudentReqVO studentReqVO) {
        try {
            if (StringUtils.isBlank(studentReqVO.getPassword())
                    || StringUtils.isBlank(studentReqVO.getEmail())
                    || StringUtils.isBlank(studentReqVO.getTel())) {
                log.info(LOG_HEAD + "注册失败，参数异常{}", studentReqVO.toString());
                return ResponseBuilder.buildErrorParamResponVO();
            }
            StudentPO studentPO = new StudentPO();
            BeanUtils.copyProperties(studentReqVO, studentPO);
            String salt = creatSalt();
            studentPO.setPassword(EncodeUtil.encodeStrWithSalt(studentReqVO.getPassword(), salt));
            studentPO.setSalt(salt);
            studentPO.setStuCode(GenerateUnionCode.generateUnionCode(STU_PREFIS));
            studentPO.setCreateTime(new Date());
            studentPO.setUpdateTime(new Date());
            studentDao.insert(studentPO);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库查询异常，异常为{}", e);
        }
        return ResponseBuilder.buildUnkownErrorResponseVO();
    }

    public ResponseMsgVO<StudentRespVO> selectOne(String key, String value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            log.error(LOG_HEAD + "查询失败，key，value为{}", key + ":" + value);
            return ResponseBuilder.buildErrorParamResponVO();
        }
        try {
            StudentPO studentPO = studentDao.selectOne(key, value);
            if (studentPO == null) {
                return ResponseBuilder.buildSuccessResponVO();
            }
            StudentRespVO studentRespVO = new StudentRespVO();
            BeanUtils.copyProperties(studentPO, studentRespVO);
            return ResponseBuilder.buildSuccessResponVO(studentRespVO);
        } catch (Exception e) {
            log.error(LOG_HEAD + "数据库查询异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    public ResponseMsgVO<List<CourseRespVO>> selectMyCourse(String no) {
        if (StringUtils.isBlank(no)) {
            return ResponseBuilder.buildErrorParamResponVO();
        }
        List<Integer> courseNoS = signDao.selectAllByNo(no).stream().map((SignPO::getCourseNo)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(courseNoS)) {
            List<CoursePO> coursePOS = courseDao.selectAllInId(courseNoS);
            if (courseNoS != null) {
                List<CourseRespVO> courseRespVOS = new ArrayList<>();
                coursePOS.forEach((coursePO -> {
                    CourseRespVO courseRespVO = new CourseRespVO();
                    BeanUtils.copyProperties(coursePO, courseRespVO);
                    courseRespVOS.add(courseRespVO);
                }));
                return ResponseBuilder.buildSuccessResponVO(courseRespVOS);
            }
        }
        return ResponseBuilder.buildSuccessResponVO(null);
    }


    public static String createToken(Object obj) {
        String token = TOKEN_KEY + buildRandNumber(TOKEN_LENGTH);
        Jedis jedis = JedisFactory.getJedisConn();
        jedis.setex(token.getBytes(), 3600, SerializeUtil.serialize(obj));
        jedis.disconnect();
        return token;
    }

}
