package com.zlkj.shiro.dyprem.bind;
import java.lang.annotation.*;
import com.zlkj.shiro.dyprem.Constants;
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
    String value() default Constants.CURRENT_USER;

}
