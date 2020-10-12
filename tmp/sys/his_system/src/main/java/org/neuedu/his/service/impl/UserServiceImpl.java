package org.neuedu.his.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.neuedu.his.entity.User;
import org.neuedu.his.mapper.UserMapper;
import org.neuedu.his.response.ResponseEntity;
import org.neuedu.his.service.IUserService;
import org.neuedu.his.util.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public ResponseEntity captcha() throws Exception{
        //生成一个指定 宽度，高度，验证位数
        SpecCaptcha specCaptcha = new SpecCaptcha(144, 40, 6);
        // 设置内置字体
        specCaptcha.setFont(Captcha.FONT_8);
        //获取生成的验证码文本
        String verCode = specCaptcha.text().toLowerCase();
        //生成一个存储在 redis 中的唯一 key 值
        String key = UUID.randomUUID().toString();
        log.debug("验证码文本：{}，保存redis 的 key {}",verCode,key);
        // 存入redis并设置过期时间为30分钟
        redisTemplate.opsForValue().set(key,verCode,30, TimeUnit.MINUTES);
        // 将key和base64封装为 Map 对象 返回给前端
        Map<String,String> verifyInfo = new HashMap<>();
        verifyInfo.put("key",key);
        verifyInfo.put("img",specCaptcha.toBase64());
        return ResponseEntity.data(verifyInfo);
    }

    @Override
    public ResponseEntity login(String logName, String logPwd, String key, String verifyCode)  throws Exception{
        //先判断验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(key);
        if(Objects.isNull(redisCode)){
            //验证码已过期，请重新获取
            return ResponseEntity.error("验证码已过期，请重新获取");
        }
        if(!Objects.equals(redisCode,verifyCode.trim().toLowerCase())){
            //获取前端验证码去前后空格转小写，比对redis
            return ResponseEntity.error("验证码输入有误，请重新输入");
        }

        //通过用户名和密码登录
        User user = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(User::getLogName, logName).eq(User::getLogPwd, logPwd).one();

        //用户名和密码验证
        if(Objects.isNull(user)){
            return ResponseEntity.error("用户名或密码输入有误，请重新输入");
        }
        //验证账号是否启用
        if(user.getUserStatus().equals(ConstUtil.USER_STATUS_DISABLED)){
            return ResponseEntity.error("你的账号已被禁用，请联系管理员");
        }

        //TODO 查询菜单权限,传递到前端生成菜单树

        //返回信息
        user.setLogPwd(null);
        return ResponseEntity.ok("登录成功",user);
    }
}
