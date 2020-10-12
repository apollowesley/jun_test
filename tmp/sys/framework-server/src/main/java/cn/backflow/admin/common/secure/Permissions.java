package cn.backflow.admin.common.secure;

import java.lang.annotation.*;

/**
 * 权限验证注解
 * Created by hunan on 2017/5/21.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permissions {

    String value();
}
