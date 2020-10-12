package com.jfast.web.common.core.annotation;

import java.lang.annotation.*;

/**
 * map实体类 注解注入
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/11/30 18:24
 * public void test(@RequestModelMapParam ModeBean modeBean) {
 *
 * }
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestModelMapParam {


}
