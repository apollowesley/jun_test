/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 逻辑删除注解.<br>
 * 作用在字段上进行逻辑删除,当调用dao.del(obj)时,实际触发update语句<br>
 * 
 * <pre>
 * <code>
public class User {
    {@literal @LogicDelete}
    private Byte isDeleted;
}
</code>
也可以指定保存的值
<code>
public class Address {
    {@literal @LogicDelete(deleteValue = "t", notDeleteValue = "f")}
    private String isdel;
}
</code>
 * </pre>
 * 
 * @author tanghc
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface LogicDelete {

    /**
     * 未删除数据库保存的值,不指定默认为0
     * 
     * @return
     */
    String notDeleteValue() default "";

    /**
     * 删除后数据库保存的值,不指定默认为1
     * 
     * @return
     */
    String deleteValue() default "";

}
