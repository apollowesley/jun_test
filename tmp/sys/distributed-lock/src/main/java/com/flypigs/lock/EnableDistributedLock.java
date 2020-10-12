package com.flypigs.lock;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LockConfig.class})
public @interface EnableDistributedLock {
}
