package cn.kiwipeach.demo.anotation;

import java.lang.annotation.*;

/**
 * 测试注解,能作用于类上面和成员变量上面，在运行期间生效
 *
 * @author liuburu
 * @create 2018/01/14
 **/
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Kiwipeach {

    String value() default "kiwipeach";

}
