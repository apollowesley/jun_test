package common.framework.dsb.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DSBLog {
	// file or console
	String log() default "file";
}