package org.neuedu.his.service;

import org.neuedu.his.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.neuedu.his.response.ResponseEntity;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
public interface IUserService extends IService<User> {
    /**
     * @apiNote 生成验证码
     * @return
     */
    ResponseEntity captcha() throws Exception;

    /**
     * @apiNote : 登录方法
     * @param logName 用户名
     * @param logPwd 密码
     * @param key redis 存储key
     * @param verifyCode 验证码
     * @return
     */
    ResponseEntity login(String logName,String logPwd,String key,String verifyCode) throws Exception;
}
