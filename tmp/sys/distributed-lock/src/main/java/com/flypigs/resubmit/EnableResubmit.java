package com.flypigs.resubmit;

import com.flypigs.lock.EnableDistributedLock;
import com.flypigs.lock.LockConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableDistributedLock//依赖分布式事务
@Import({ResubmitConfig.class})
public @interface EnableResubmit {
    Class<? extends ResubmitResolver> defaultResolver() default ExceptionResolver.class;
}
