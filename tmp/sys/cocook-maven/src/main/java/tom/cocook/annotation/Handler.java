package tom.cocook.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tom.cocook.handler.DefaultAnnotationHandler;

public class Handler {

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Resource {
		public Class<?> value();

	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Controller {

	}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Interceptor {
		public Class<? extends tom.cocook.handler.Handler>[] value() default DefaultAnnotationHandler.class;
	}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Initialize {
		
	}
	
	/**
	 * 执行方法, 只有当controller 为 
	 * 	defaultAnnotationController 的时候初始化执行
	 * @author Administrator
	 */
	@Target(java.lang.annotation.ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface ExecMethod{
	}
	
	@Target(java.lang.annotation.ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Path{
		public String value() ;
		public HttpMethod method() default HttpMethod.GET ;
	}
	

}
