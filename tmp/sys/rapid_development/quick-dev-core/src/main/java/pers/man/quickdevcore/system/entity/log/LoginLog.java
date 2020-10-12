package pers.man.quickdevcore.system.entity.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pers.man.quickdevcore.system.entity.BaseEntity;
import pers.man.quickdevcore.system.entity.user.User;

import java.time.LocalDateTime;

/**
 * 登录日志
 * @author MAN
 * @version 1.0
 * @date 2020-03-31 20:17
 * @project quick-dev
 */
@Getter
@Setter
@ToString
public class LoginLog extends BaseEntity {
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
    /**
     * 登录ip
     */
    private String loginIp;
    /**
     * 登录地点
     */
    private String loginAddress;
    /**
     * 登录用户
     */
    private User user;
}
