package cn.afterturn.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * DAO成注解类,不推荐使用,推荐spring
 * @author JueYue
 * @date 2014年12月7日 上午11:59:39
 */
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface IDao {

    public String value() default "";

}
