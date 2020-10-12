package pers.man.quickdevcore.system.entity;

import lombok.Getter;
import lombok.Setter;
import pers.man.quickdevcore.system.entity.user.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 所有实体类的基类
 * @author MAN
 * @version 1.0
 * @date 2020-03-31 20:01
 * @project quick-dev
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable {
    /**
     * 主键,唯一id
     */
    private String id;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人
     */
    private User creator;
    /**
     * 修改人
     */
    private User updater;
}
