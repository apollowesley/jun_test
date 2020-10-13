package name.liushiyao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 北岭山下 on 2017/7/19.
 */

//PC注解
public @interface PCAnnotation {


        @Target ( ElementType.FIELD )
        @Retention ( RetentionPolicy.RUNTIME )
        //计算机类型
        public @interface type{
                String value() default "";
        }
        @Target ( ElementType.FIELD )
        @Retention ( RetentionPolicy.RUNTIME )
        @interface cpu{

                enum type {
                        INTEL,AMD
                }
                type cpu() default type.INTEL;

        }
        @Target ( ElementType.FIELD )
        @Retention ( RetentionPolicy.RUNTIME )
        @interface country{

                String value() default "";
        }
}
